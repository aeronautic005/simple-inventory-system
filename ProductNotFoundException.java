/**
 * Exception thrown when a requested product ID or name does not exist
 * in the inventory catalog.
 */
public class ProductNotFoundException extends Exception {
    private static final long serialVersionUID = 1L;

    private final String searchIdentifier;

    public ProductNotFoundException(String message) {
        super(message);
        this.searchIdentifier = "N/A";
    }

    public ProductNotFoundException(String identifier, String reason) {
        super(String.format("Product '%s' not found: %s", identifier, reason));
        this.searchIdentifier = identifier;
    }

    public String getSearchIdentifier() {
        return searchIdentifier;
    }
}
