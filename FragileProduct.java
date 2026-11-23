/**
 * Subclass representing fragile items (glassware, ceramics, delicate instruments)
 * that mandate protective packaging fees and specialized handling.
 */
public class FragileProduct extends Product {
    private final double packagingFee;
    private final String handlingInstructions;

    public FragileProduct(String id, String name, double unitPrice, int initialStock,
                          double packagingFee, String handlingInstructions) {
        super(id, name, unitPrice, initialStock, ProductCategory.GENERAL);
        this.packagingFee = Math.max(0.0, packagingFee);
        this.handlingInstructions = (handlingInstructions == null || handlingInstructions.trim().isEmpty())
                ? "Handle With Care" : handlingInstructions.trim();
    }

    public double getPackagingFee() {
        return packagingFee;
    }

    public String getHandlingInstructions() {
        return handlingInstructions;
    }

    public double getEffectivePrice() {
        return unitPrice + packagingFee;
    }

    @Override
    public double calculateTotalValue() {
        return this.quantityInStock * getEffectivePrice();
    }

    @Override
    public String getProductType() {
        return String.format("Fragile [%s, Fee: +$%.2f]", handlingInstructions, packagingFee);
    }
}
