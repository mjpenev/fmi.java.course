package bg.sofia.uni.fmi.mjt.frauddetector.rule;

import bg.sofia.uni.fmi.mjt.frauddetector.transaction.Transaction;

import java.util.List;

public class SmallTransactionsRule implements Rule {
    private int countThreshold;
    private double amountThreshold;
    private double weight;

    public SmallTransactionsRule(int countThreshold, double amountThreshold, double weight) {
        if (weight < 0 || weight > 1) {
            throw new IllegalArgumentException("Weight of the rule shall be in [0, 1]");
        }
        this.countThreshold = countThreshold;
        this.amountThreshold = amountThreshold;
        this.weight = weight;
    }

    @Override
    public boolean applicable(List<Transaction> transactions) {
        return transactions.stream()
                .filter(t -> t.transactionAmount() < amountThreshold)
                .count() > countThreshold;
    }

    @Override
    public double weight() {
        return this.weight;
    }
}
