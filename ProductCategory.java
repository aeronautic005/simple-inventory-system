/**
 * Enumeration defining retail product categories, departments,
 * and applicable sales tax rates.
 */
public enum ProductCategory {
    GROCERY("Grocery & Perishables", 0.0),        // Tax-exempt essentials
    ELECTRONICS("Consumer Electronics", 7.5),     // Standard tech tax
    HARDWARE("Tools & Hardware", 5.0),
    APPAREL("Clothing & Apparel", 4.0),
    STATIONERY("Office & School Supplies", 3.0),
    GENERAL("General Merchandise", 6.0);

    private final String displayName;
    private final double taxRatePercent;

    ProductCategory(String displayName, double taxRatePercent) {
        this.displayName = displayName;
        this.taxRatePercent = taxRatePercent;
    }

    public String getDisplayName() {
        return displayName;
    }

    public double getTaxRatePercent() {
        return taxRatePercent;
    }

    public static ProductCategory fromString(String text) {
        if (text == null) {
            return GENERAL;
        }
        String clean = text.trim().toUpperCase();
        for (ProductCategory cat : values()) {
            if (cat.name().equals(clean) || cat.displayName.equalsIgnoreCase(clean)) {
                return cat;
            }
        }
        return GENERAL;
    }
}
