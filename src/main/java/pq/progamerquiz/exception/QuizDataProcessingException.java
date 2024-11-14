package pq.progamerquiz.exception;

public class QuizDataProcessingException extends RuntimeException {

    public QuizDataProcessingException(String message) {
        super(message);
    }

    public QuizDataProcessingException(String message, Throwable cause) {
        super(message, cause);
    }
}