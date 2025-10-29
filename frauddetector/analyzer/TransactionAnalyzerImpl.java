package bg.sofia.uni.fmi.mjt.frauddetector.analyzer;

import bg.sofia.uni.fmi.mjt.frauddetector.rule.Rule;
import bg.sofia.uni.fmi.mjt.frauddetector.transaction.Channel;
import bg.sofia.uni.fmi.mjt.frauddetector.transaction.Transaction;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.Reader;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.SortedMap;
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
        throw new IllegalArgumentException("Method is not implemented yet.");
    }

    @Override
    public SortedMap<String, Double> accountsRisk() {
        throw new IllegalArgumentException("Method is not implemented yet.");
    }
}
