package bg.sofia.uni.fmi.mjt.frauddetector.rule;

import bg.sofia.uni.fmi.mjt.frauddetector.transaction.Transaction;

import java.util.List;
import java.util.stream.Collectors;

public class ZScoreRule implements Rule {
    private double zScoreThreshold;
    private double weight;

    public ZScoreRule(double zScoreThreshold, double weight) {
        if (weight < 0 || weight > 1) {
            throw new IllegalArgumentException("Weight of the rule shall be in [0, 1]");
        }
        this.zScoreThreshold = zScoreThreshold;
        this.weight = weight;
    }

    @Override
    public boolean applicable(List<Transaction> transactions) {
        double transactionsCount = transactions.size();
        double mu = transactions.stream()
                .mapToDouble(Transaction::transactionAmount)
                .sum() * (1 / transactionsCount);
        double dispersion = transactions.stream()
                .mapToDouble(t -> {
                    double diff = t.transactionAmount() - mu;
                    return diff * diff;
                })
                .sum() * (1 / transactionsCount);
        double standardDeviation = Math.sqrt(dispersion);
        double zScore = (transactions.stream()
                .mapToDouble(Transaction::transactionAmount)
                .sum() - mu) / standardDeviation;
        return zScore > this.zScoreThreshold;
    }

    @Override
    public double weight() {
        return this.weight;
    }
}
