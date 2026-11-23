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

    /**
     * Formats an itemized ASCII customer receipt.
     */
    public String generateItemizedReceipt() {
        StringBuilder sb = new StringBuilder();
        sb.append("========================================================\n");
        sb.append("               OFFICIAL SALES CHECKOUT RECEIPT          \n");
        sb.append("========================================================\n");
        sb.append(String.format(" Transaction ID : %-37s\n", transactionId));
        sb.append(String.format(" Timestamp      : %-37s\n", timestamp));
        sb.append("--------------------------------------------------------\n");
        sb.append(String.format(" Product ID     : %-37s\n", productId));
        sb.append(String.format(" Product Name   : %-37s\n", productName));
        sb.append(String.format(" Unit Price     : $%10.2f\n", unitPriceCharged));
        sb.append(String.format(" Quantity Sold  : %10d unit(s)\n", quantitySold));
        sb.append("--------------------------------------------------------\n");
        sb.append(String.format(" TOTAL BILLED   : $%10.2f\n", totalAmount));
        sb.append("========================================================\n");
        sb.append("               ||| ||||| |||| || |||||| ||              \n");
        sb.append(String.format("                   *%s*                         \n", transactionId));
        sb.append("           Thank you for your business!                 \n");
        sb.append("========================================================\n");
        return sb.toString();
    }
}
