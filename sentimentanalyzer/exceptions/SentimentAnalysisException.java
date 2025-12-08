package bg.sofia.uni.fmi.mjt.sentimentanalyzer.exceptions;

import java.io.UncheckedIOException;

public class SentimentAnalysisException extends RuntimeException {
    public SentimentAnalysisException() {
        super();
    }
    public SentimentAnalysisException(String message) {
        super(message);
    }
    public SentimentAnalysisException(Throwable cause) {
        super(cause);
    }
    public SentimentAnalysisException(String message, Throwable cause) {
        super(message, cause);
    }
}
