package bg.sofia.uni.fmi.mjt.frauddetector.transaction;

import java.time.LocalDateTime;

import static java.time.format.DateTimeFormatter.ISO_LOCAL_DATE_TIME;

public record Transaction(String transactionID, String accountID, double transactionAmount,
                          LocalDateTime transactionDate, String location, Channel channel) {
    private static final String TRANSACTION_ATTRIBUTE_DELIMITER = ", ";

    public static Transaction of(String line) {
        String[] tokens = line.split(TRANSACTION_ATTRIBUTE_DELIMITER);
        return new Transaction(tokens[0],
                tokens[1],
                Double.parseDouble(tokens[2]),
                LocalDateTime.parse(tokens[3], ISO_LOCAL_DATE_TIME),
                tokens[4],
                Channel.valueOf(tokens[5]));
    }
}
