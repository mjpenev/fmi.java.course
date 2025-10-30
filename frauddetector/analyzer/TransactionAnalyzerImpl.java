package bg.sofia.uni.fmi.mjt.frauddetector.analyzer;

import bg.sofia.uni.fmi.mjt.frauddetector.rule.FrequencyRule;
import bg.sofia.uni.fmi.mjt.frauddetector.rule.Rule;
import bg.sofia.uni.fmi.mjt.frauddetector.transaction.Channel;
import bg.sofia.uni.fmi.mjt.frauddetector.transaction.Transaction;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.Reader;
import java.util.*;
import java.util.stream.Collectors;
import java.io.UncheckedIOException;

public class TransactionAnalyzerImpl implements TransactionAnalyzer {
    private final List<Transaction> transactions;
    private final List<Rule> rules;

    public TransactionAnalyzerImpl(Reader reader, List<Rule> rules) {
        var data = new BufferedReader(reader);
        transactions = data.lines().map(Transaction::of).toList();
        this.rules = rules;
    }

    @Override
    public List<Transaction> allTransactions() {
        return this.transactions;
    }

    @Override
    public List<String> allAccountIDs() {
        return this.transactions.stream()
                .map(Transaction::accountID)
                .distinct()
                .toList();
    }

    @Override
    public Map<Channel, Integer> transactionCountByChannel() {
        Map<Channel, Long> result =
                this.transactions.stream()
                        .collect(Collectors.groupingBy(Transaction::channel, Collectors.counting()));
        return result.entrySet().stream()
                .collect(Collectors.toMap(Map.Entry::getKey, e -> e.getValue().intValue()));
    }

    @Override
    public double amountSpentByUser(String accountID) {
        return this.transactions.stream()
                .filter(t -> t.accountID().equals(accountID))
                .mapToDouble(Transaction::transactionAmount)
                .reduce(Double::sum)
                .orElseThrow(() -> new IllegalArgumentException("No transactions found for account ID: " + accountID));
    }

    @Override
    public List<Transaction> allTransactionsByUser(String accountId) {
        if (accountId == null || accountId.isEmpty()) {
            throw new IllegalArgumentException("Arguments shall not be null or empty");
        }

        List<Transaction> filtered =  this.transactions.stream()
                .filter(t -> t.accountID().equals(accountId))
                .toList();

        return Optional.of(filtered)
                .filter(list -> !list.isEmpty())
                .orElseThrow(() -> new IllegalArgumentException("No transactions found for account ID: " + accountId));
    }

    @Override
    public double accountRating(String accountId) {
        if (accountId == null) {
            throw new IllegalArgumentException("AccountID argument shall not be null");
        }
        double resultRating = 0.0;
        List<Transaction> transactionList = this.transactions.stream()
                .filter(t -> t.accountID().equals(accountId))
                .toList();
        for (Rule rule : this.rules) {
            if(rule.applicable(transactionList)) {
                resultRating += rule.weight();
            }
        }
        return resultRating;
    }

    @Override
    public SortedMap<String, Double> accountsRisk() {
        Map<String, Double> result = new HashMap<>();
        for (String id : allAccountIDs()) {
            result.put(id, accountRating(id));
        }

        Comparator<String> byRiskDescending = (a1, a2) -> {
            int cmp = result.get(a2).compareTo(result.get(a1));
            if (cmp == 0) return a1.compareTo(a2);
            return cmp;
        };

        SortedMap<String, Double> sorted = new TreeMap<>(byRiskDescending);
        sorted.putAll(result);

        return sorted;
    }
}
