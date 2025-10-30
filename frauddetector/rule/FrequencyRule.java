package bg.sofia.uni.fmi.mjt.frauddetector.rule;

import bg.sofia.uni.fmi.mjt.frauddetector.transaction.Transaction;

import java.time.LocalDateTime;
import java.time.temporal.TemporalAmount;
import java.util.List;

public class FrequencyRule implements Rule {
    private int transactionCountTreshold;
    private TemporalAmount timeWindow;
    private double weight;

    public FrequencyRule(int transactionCountThreshold, TemporalAmount timeWindow, double weight) {
        if (weight < 0 || weight > 1) {
            throw new IllegalArgumentException("Weight of the rule shall be in [0, 1]");
        }

        this.transactionCountTreshold = transactionCountThreshold;
        this.timeWindow = timeWindow;
        this.weight = weight;
    }

    @Override
    public boolean applicable(List<Transaction> transactions) {
        LocalDateTime latestTransaction = transactions.stream()
                .map(Transaction::transactionDate)
                .max(LocalDateTime::compareTo)
                .orElseThrow();
        LocalDateTime windowStart = latestTransaction.minus(this.timeWindow);

        return transactions.stream()
                .filter(t -> t.transactionDate().isAfter(windowStart) &&
                        t.transactionDate().isBefore(latestTransaction))
                .count() > transactionCountTreshold;
    }

    @Override
    public double weight() {
        return this.weight;
    }
}
