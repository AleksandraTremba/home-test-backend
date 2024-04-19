package ee.taltech.inbankbackend.exceptions;

/**
 * Thrown when unexpected error occurs.
 */
public class UnexpectedException extends Throwable {
    private final String message;
    private final Throwable cause;

    public UnexpectedException(String message) {
        this(message, null);
    }

    public UnexpectedException(String message, Throwable cause) {
        this.message = message;
        this.cause = cause;
    }

    @Override
    public Throwable getCause() {
        return cause;
    }

    @Override
    public String getMessage() {
        return message;
    }
}
