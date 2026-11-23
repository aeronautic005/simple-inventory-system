/**
 * Subclass representing electronic appliances and consumer hardware
 * that require manufacturer warranty tracking and electronic handling fees.
 */
public class ElectronicProduct extends Product {
    private int warrantyMonths;
    private final double warrantyFeeRate;

    public ElectronicProduct(String id, String name, double unitPrice, int initialStock, int warrantyMonths) {
        this(id, name, unitPrice, initialStock, warrantyMonths, 0.05); // Default 5% handling/certification fee
    }

    public ElectronicProduct(String id, String name, double unitPrice, int initialStock,
                             int warrantyMonths, double warrantyFeeRate) {
        super(id, name, unitPrice, initialStock);
        this.warrantyMonths = Math.max(0, warrantyMonths);
        this.warrantyFeeRate = Math.max(0.0, warrantyFeeRate);
    }

    public int getWarrantyMonths() {
        return warrantyMonths;
    }

    public void setWarrantyMonths(int warrantyMonths) {
        this.warrantyMonths = Math.max(0, warrantyMonths);
    }

    public double getWarrantyFeeRate() {
        return warrantyFeeRate;
    }

    /**
     * Calculates the unit selling price including the electronic handling and warranty fee.
     */
    public double getEffectivePrice() {
        return unitPrice * (1.0 + warrantyFeeRate);
    }

    @Override
    public double calculateTotalValue() {
        return this.quantityInStock * getEffectivePrice();
    }

    @Override
    public String getProductType() {
        return String.format("Electronic (%d-mo warranty)", warrantyMonths);
    }
}
