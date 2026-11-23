/**
 * Represents a standard inventory product with identification,
 * pricing, stock levels, and inventory valuation.
 */
public class Product {
    protected final String id;
    protected String name;
    protected double unitPrice;
    protected int quantityInStock;
    protected ProductCategory category;

    public Product(String id, String name, double unitPrice, int initialStock) {
        this(id, name, unitPrice, initialStock, ProductCategory.GENERAL);
    }

    public Product(String id, String name, double unitPrice, int initialStock, ProductCategory category) {
        if (id == null || id.trim().isEmpty()) {
            throw new IllegalArgumentException("Product ID cannot be null or empty.");
        }
        if (name == null || name.trim().isEmpty()) {
            throw new IllegalArgumentException("Product name cannot be null or empty.");
        }
        if (unitPrice < 0.0) {
            throw new IllegalArgumentException("Unit price cannot be negative.");
        }
        if (initialStock < 0) {
            throw new IllegalArgumentException("Initial stock cannot be negative.");
        }

        this.id = id.trim().toUpperCase();
        this.name = name.trim();
        this.unitPrice = unitPrice;
        this.quantityInStock = initialStock;
        this.category = (category == null) ? ProductCategory.GENERAL : category;
    }

    public ProductCategory getCategory() {
        return category;
    }

    public void setCategory(ProductCategory category) {
        this.category = (category == null) ? ProductCategory.GENERAL : category;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public double getUnitPrice() {
        return unitPrice;
    }

    public void setUnitPrice(double unitPrice) {
        if (unitPrice < 0.0) {
            throw new IllegalArgumentException("Unit price cannot be negative.");
        }
        this.unitPrice = unitPrice;
    }

    public int getQuantityInStock() {
        return quantityInStock;
    }

    /**
     * Increases stock level by a positive quantity.
     */
    public void addStock(int quantity) throws InvalidQuantityException {
        if (quantity <= 0) {
            throw new InvalidQuantityException(quantity, "Restock quantity must be greater than zero.");
        }
        this.quantityInStock += quantity;
    }

    /**
     * Deducts stock level when items are sold.
     */
    public void reduceStock(int quantity) throws InvalidQuantityException {
        if (quantity <= 0) {
            throw new InvalidQuantityException(quantity, "Purchase quantity must be greater than zero.");
        }
        if (quantity > this.quantityInStock) {
            throw new InvalidQuantityException(quantity,
                    String.format("Insufficient stock. Requested: %d, Available: %d", quantity, this.quantityInStock));
        }
        this.quantityInStock -= quantity;
    }

    /**
     * Calculates the total inventory value of this product on hand.
     */
    public double calculateTotalValue() {
        return this.quantityInStock * this.unitPrice;
    }

    public String getProductType() {
        return "Standard Product";
    }

    @Override
    public String toString() {
        return String.format("[%s] %-20s | Price: $%7.2f | Stock: %4d | Type: %s",
                id, name, unitPrice, quantityInStock, getProductType());
    }
}
