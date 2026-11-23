/**
 * Subclass representing perishable goods (dairy, produce, bakery)
 * that have an expiration threshold and dynamic markdown discounts.
 */
public class PerishableProduct extends Product {
    private int daysUntilExpiration;

    public PerishableProduct(String id, String name, double unitPrice, int initialStock, int daysUntilExpiration) {
        super(id, name, unitPrice, initialStock, ProductCategory.GROCERY);
        this.daysUntilExpiration = Math.max(0, daysUntilExpiration);
    }

    public int getDaysUntilExpiration() {
        return daysUntilExpiration;
    }

    public void setDaysUntilExpiration(int daysUntilExpiration) {
        this.daysUntilExpiration = Math.max(0, daysUntilExpiration);
    }

    /**
     * Calculates the unit price after applying expiration-based clearance markdowns.
     * <= 3 days: 30% discount
     * <= 7 days: 15% discount
     * > 7 days: Full price
     */
    public double getEffectivePrice() {
        if (daysUntilExpiration <= 0) {
            return 0.0; // Expired / spoiled
        } else if (daysUntilExpiration <= 3) {
            return unitPrice * 0.70; // 30% discount
        } else if (daysUntilExpiration <= 7) {
            return unitPrice * 0.85; // 15% discount
        }
        return unitPrice;
    }

    @Override
    public double calculateTotalValue() {
        return this.quantityInStock * getEffectivePrice();
    }

    @Override
    public String getProductType() {
        return String.format("Perishable (Expires in %d day%s)",
                daysUntilExpiration, daysUntilExpiration == 1 ? "" : "s");
    }
}
