/**
 * Exception thrown when an invalid quantity (e.g. negative, zero,
 * or exceeding available inventory) is supplied for a stock operation.
 */
public class InvalidQuantityException extends Exception {
    private static final long serialVersionUID = 1L;

    private final int requestedQuantity;
    private final String reason;

    public InvalidQuantityException(String message) {
        super(message);
        this.requestedQuantity = -1;
        this.reason = message;
    }

    public InvalidQuantityException(int requestedQuantity, String reason) {
        super(String.format("Invalid quantity (%d): %s", requestedQuantity, reason));
        this.requestedQuantity = requestedQuantity;
        this.reason = reason;
    }

    public int getRequestedQuantity() {
        return requestedQuantity;
    }

    public String getReason() {
        return reason;
    }
}
