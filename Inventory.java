import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Manages the collection of inventory products, lookups,
 * and stock valuation.
 */
public class Inventory {
    private final List<Product> products;

    public Inventory() {
        this.products = new ArrayList<>();
    }

    /**
     * Adds a new product to the inventory registry.
     */
    public void addProduct(Product product) {
        if (product == null) {
            throw new IllegalArgumentException("Cannot add a null product.");
        }
        for (Product p : products) {
            if (p.getId().equalsIgnoreCase(product.getId())) {
                throw new IllegalArgumentException("Product ID '" + product.getId() + "' already exists.");
            }
        }
        products.add(product);
    }

    /**
     * Searches for a product by exact ID (case-insensitive).
     */
    public Product findProductById(String id) {
        if (id == null) {
            return null;
        }
        String cleanId = id.trim().toUpperCase();
        for (Product p : products) {
            if (p.getId().equalsIgnoreCase(cleanId)) {
                return p;
            }
        }
        return null;
    }

    /**
     * Searches for products whose name contains the query keyword.
     */
    public List<Product> searchByName(String keyword) {
        if (keyword == null || keyword.trim().isEmpty()) {
            return Collections.emptyList();
        }
        String cleanKeyword = keyword.trim().toLowerCase();
        List<Product> matches = new ArrayList<>();
        for (Product p : products) {
            if (p.getName().toLowerCase().contains(cleanKeyword)) {
                matches.add(p);
            }
        }
        return matches;
    }

    public List<Product> getAllProducts() {
        return Collections.unmodifiableList(products);
    }

    public int getTotalProductCount() {
        return products.size();
    }

    /**
     * Calculates the aggregate inventory valuation across all items.
     */
    public double calculateTotalInventoryValue() {
        double total = 0.0;
        for (Product p : products) {
            total += p.calculateTotalValue();
        }
        return total;
    }
}
