package bg.sofia.uni.fmi.mjt.frauddetector.rule;

import bg.sofia.uni.fmi.mjt.frauddetector.transaction.Transaction;

import java.util.List;

public class LocationsRule implements Rule {
    private int treshold;
    private double weight;

    public LocationsRule(int threshold, double weight) {
        if (weight < 0 || weight > 1) {
            throw new IllegalArgumentException("Weight of the rule shall be in [0, 1]");
        }
        this.treshold = threshold;
        this.weight = weight;
    }

    @Override
    public boolean applicable(List<Transaction> transactions) {
        return transactions.stream()
                .map(Transaction::location)
                .distinct()
                .count() > this.treshold;
    }

    @Override
    public double weight() {
        return this.weight;
    }
}
