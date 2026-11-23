import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Model capturing completed sales transactions, units sold,
 * prices charged, and timestamps for accounting and auditing.
 */
public class SaleRecord {
    private static int counter = 1000;

    private final String transactionId;
    private final String productId;
    private final String productName;
    private final int quantitySold;
    private final double unitPriceCharged;
    private final double totalAmount;
    private final String timestamp;

    public SaleRecord(String productId, String productName, int quantitySold,
                      double unitPriceCharged, double totalAmount) {
        this.transactionId = "TXN-" + (++counter);
        this.productId = productId;
        this.productName = productName;
        this.quantitySold = quantitySold;
        this.unitPriceCharged = unitPriceCharged;
        this.totalAmount = totalAmount;

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        this.timestamp = sdf.format(new Date());
    }

    public String getTransactionId() {
        return transactionId;
    }

    public String getProductId() {
        return productId;
    }

    public String getProductName() {
        return productName;
    }

    public int getQuantitySold() {
        return quantitySold;
    }

    public double getUnitPriceCharged() {
        return unitPriceCharged;
    }

    public double getTotalAmount() {
        return totalAmount;
    }

    public String getTimestamp() {
        return timestamp;
    }

    @Override
    public String toString() {
        return String.format("[%s] %s (%d units @ $%.2f) = $%.2f at %s",
                transactionId, productName, quantitySold, unitPriceCharged, totalAmount, timestamp);
    }
}
