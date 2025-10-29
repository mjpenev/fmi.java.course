package bg.sofia.uni.fmi.mjt.frauddetector.rule;

import bg.sofia.uni.fmi.mjt.frauddetector.transaction.Transaction;

import java.util.List;

public class FrequencyRule implements Rule {
    @Override
    public boolean applicable(List<Transaction> transactions) {
        throw new IllegalArgumentException("Method is not implemented yet.");
    }

    @Override
    public double weight() {
        throw new IllegalArgumentException("Method is not implemented yet.");
    }
}
