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

    /**
     * Replenishes stock for a registered product.
     * @param productId product unique barcode or ID
     * @param quantity number of units to add
     * @throws ProductNotFoundException if product does not exist
     * @throws InvalidQuantityException if quantity is non-positive
     */
    public void restockProduct(String productId, int quantity)
            throws ProductNotFoundException, InvalidQuantityException {
        Product product = findProductById(productId);
        if (product == null) {
            throw new ProductNotFoundException(productId, "Cannot restock unregistered product.");
        }
        product.addStock(quantity);
    }

    /**
     * Processes customer purchase and deducts inventory stock.
     * @param productId product unique barcode or ID
     * @param quantity number of units being purchased
     * @return total purchase price
     * @throws ProductNotFoundException if product does not exist
     * @throws InvalidQuantityException if quantity is non-positive or exceeds available stock
     */
    public double sellProduct(String productId, int quantity)
            throws ProductNotFoundException, InvalidQuantityException {
        Product product = findProductById(productId);
        if (product == null) {
            throw new ProductNotFoundException(productId, "Cannot sell unregistered product.");
        }

        product.reduceStock(quantity);

        double unitPrice = product.getUnitPrice();
        if (product instanceof PerishableProduct) {
            unitPrice = ((PerishableProduct) product).getEffectivePrice();
        }
        return unitPrice * quantity;
    }

    /**
     * Filters and returns products whose stock falls at or below a given threshold.
     */
    public List<Product> getLowStockProducts(int threshold) {
        List<Product> lowStock = new ArrayList<>();
        for (Product p : products) {
            if (p.getQuantityInStock() <= threshold) {
                lowStock.add(p);
            }
        }
        return lowStock;
    }
}
