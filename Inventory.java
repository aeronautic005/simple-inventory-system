import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Manages the collection of inventory products, lookups,
 * and stock valuation.
 */
public class Inventory {
    private final List<Product> products;
    private final List<SaleRecord> salesHistory;

    public Inventory() {
        this.products = new ArrayList<>();
        this.salesHistory = new ArrayList<>();
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

    /**
     * Returns a new list of inventory products sorted by unit price.
     * @param ascending true for lowest to highest, false for highest to lowest
     */
    public List<Product> getProductsSortedByPrice(boolean ascending) {
        List<Product> sorted = new ArrayList<>(products);
        sorted.sort((p1, p2) -> {
            int cmp = Double.compare(p1.getUnitPrice(), p2.getUnitPrice());
            return ascending ? cmp : -cmp;
        });
        return sorted;
    }

    /**
     * Returns a new list of inventory products sorted by stock count on hand.
     * Useful for prioritizing depleted items for warehouse restocking.
     * @param ascending true for lowest stock first, false for highest stock first
     */
    public List<Product> getProductsSortedByStock(boolean ascending) {
        List<Product> sorted = new ArrayList<>(products);
        sorted.sort((p1, p2) -> {
            int cmp = Integer.compare(p1.getQuantityInStock(), p2.getQuantityInStock());
            return ascending ? cmp : -cmp;
        });
        return sorted;
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
        } else if (product instanceof ElectronicProduct) {
            unitPrice = ((ElectronicProduct) product).getEffectivePrice();
        }

        double totalBill = unitPrice * quantity;
        SaleRecord record = new SaleRecord(product.getId(), product.getName(), quantity, unitPrice, totalBill);
        salesHistory.add(record);
        return totalBill;
    }

    public List<SaleRecord> getSalesHistory() {
        return Collections.unmodifiableList(salesHistory);
    }

    /**
     * Calculates the cumulative revenue generated from all recorded sales.
     */
    public double getTotalRevenueCollected() {
        double revenue = 0.0;
        for (SaleRecord record : salesHistory) {
            revenue += record.getTotalAmount();
        }
        return revenue;
    }

    /**
     * Prints an itemized ASCII ledger of all recorded sales transactions.
     */
    public void displaySalesHistory() {
        System.out.println("\n==========================================================================================");
        System.out.println("                              SALES TRANSACTION AUDIT LEDGER                              ");
        System.out.println("==========================================================================================");
        System.out.printf("| %-10s | %-8s | %-24s | %-5s | %-10s | %-19s |\n",
                "TXN ID", "PROD ID", "PRODUCT NAME", "QTY", "TOTAL PAID", "TIMESTAMP");
        System.out.println("+------------+----------+--------------------------+-------+------------+---------------------+");

        if (salesHistory.isEmpty()) {
            System.out.println("|                              No sales recorded yet.                                     |");
        } else {
            for (SaleRecord record : salesHistory) {
                String nameCut = record.getProductName();
                if (nameCut.length() > 24) {
                    nameCut = nameCut.substring(0, 21) + "...";
                }
                System.out.printf("| %-10s | %-8s | %-24s | %5d | $%9.2f | %-19s |\n",
                        record.getTransactionId(), record.getProductId(), nameCut,
                        record.getQuantitySold(), record.getTotalAmount(), record.getTimestamp());
            }
        }

        System.out.println("+------------+----------+--------------------------+-------+------------+---------------------+");
        System.out.printf("| Total Completed Transactions: %-3d                    Total Revenue Collected: $%11.2f |\n",
                salesHistory.size(), getTotalRevenueCollected());
        System.out.println("==========================================================================================\n");
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

    /**
     * Prints an ASCII formatted table of all items in inventory.
     */
    public void displayInventoryTable() {
        System.out.println("\n+-----------------------------------------------------------------------------------------------+");
        System.out.println("|                                   CURRENT INVENTORY OVERVIEW                                  |");
        System.out.println("+---------+---------------------------+-----------+-------+--------------------------+----------+");
        System.out.printf("| %-7s | %-25s | %-9s | %-5s | %-24s | %-8s |\n",
                "ID", "PRODUCT NAME", "UNIT PRICE", "STOCK", "CATEGORY / STATUS", "TOTAL VAL");
        System.out.println("+---------+---------------------------+-----------+-------+--------------------------+----------+");

        if (products.isEmpty()) {
            System.out.println("|                            No products registered in inventory.                              |");
        } else {
            for (Product p : products) {
                String displayName = p.getName();
                if (displayName.length() > 25) {
                    displayName = displayName.substring(0, 22) + "...";
                }
                String displayCategory = p.getProductType();
                if (displayCategory.length() > 24) {
                    displayCategory = displayCategory.substring(0, 21) + "...";
                }
                System.out.printf("| %-7s | %-25s | $%8.2f | %5d | %-24s | $%7.2f |\n",
                        p.getId(), displayName, p.getUnitPrice(), p.getQuantityInStock(),
                        displayCategory, p.calculateTotalValue());
            }
        }

        System.out.println("+---------+---------------------------+-----------+-------+--------------------------+----------+");
        System.out.printf("| Total Registered Products: %-3d                               Total Inventory Value: $%9.2f |\n",
                getTotalProductCount(), calculateTotalInventoryValue());
        System.out.println("+-----------------------------------------------------------------------------------------------+\n");
    }

    /**
     * Displays a targeted alert list of products requiring restocking.
     */
    public void displayLowStockAlerts(int threshold) {
        List<Product> lowStock = getLowStockProducts(threshold);
        System.out.println("\n------------------------- LOW STOCK ALERTS -------------------------");
        System.out.printf("Threshold: Stock <= %d units\n", threshold);
        if (lowStock.isEmpty()) {
            System.out.println("[+] All inventory levels are healthy. No items require immediate restock.");
        } else {
            System.out.printf("[!] Found %d product(s) running low on stock:\n", lowStock.size());
            for (Product p : lowStock) {
                System.out.printf("  * [%s] %-20s -> Current Stock: %d (Restock Recommended)\n",
                        p.getId(), p.getName(), p.getQuantityInStock());
            }
        }
        System.out.println("--------------------------------------------------------------------\n");
    }
}
