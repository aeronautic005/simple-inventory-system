import java.util.Scanner;

/**
 * Interactive Command-Line Console Application for managing
 * retail inventory, restocking, sales, and stock alerts.
 */
public class InventoryApp {
    private static final Scanner scanner = new Scanner(System.in);
    private static final Inventory inventory = new Inventory();

    public static void main(String[] args) {
        seedSampleInventory();
        boolean running = true;

        while (running) {
            printMainMenu();
            System.out.print("Enter option (1-8): ");
            String choice = scanner.nextLine().trim();

            switch (choice) {
                case "1":
                    inventory.displayInventoryTable();
                    break;
                case "2":
                    handleRestock();
                    break;
                case "3":
                    handleSale();
                    break;
                case "4":
                    handleSearch();
                    break;
                case "5":
                    handleLowStockCheck();
                    break;
                case "6":
                    inventory.displaySalesHistory();
                    break;
                case "7":
                    runAutomatedTestSuite();
                    break;
                case "8":
                    System.out.println("\nExiting Inventory Management System. Have a great day!\n");
                    running = false;
                    break;
                default:
                    System.out.println("\n[!] Invalid choice. Please enter a number between 1 and 8.\n");
            }
        }
    }

    private static void printMainMenu() {
        System.out.println("==================================================================");
        System.out.println("              SIMPLE INVENTORY MANAGEMENT SYSTEM                  ");
        System.out.println("==================================================================");
        System.out.println(" [1] View Current Inventory Overview");
        System.out.println(" [2] Restock Product Units");
        System.out.println(" [3] Process Product Sale & Bill Customer");
        System.out.println(" [4] Search Product by Keyword");
        System.out.println(" [5] Check Low Stock Alerts");
        System.out.println(" [6] View Sales History & Audit Ledger");
        System.out.println(" [7] Run Automated OOP Verification Tests");
        System.out.println(" [8] Exit");
        System.out.println("==================================================================");
    }

    private static void seedSampleInventory() {
        inventory.addProduct(new Product("P-101", "Wireless Optical Mouse", 15.50, 40));
        inventory.addProduct(new Product("P-102", "Mechanical Keyboard", 65.00, 15));
        inventory.addProduct(new Product("P-103", "USB-C Flash Drive 64GB", 12.00, 5));

        // Perishable goods with varying expiration days
        inventory.addProduct(new PerishableProduct("PER-201", "Organic Whole Milk 1L", 3.50, 20, 2)); // 30% discount
        inventory.addProduct(new PerishableProduct("PER-202", "Artisan Sourdough Bread", 4.50, 8, 5)); // 15% discount
        inventory.addProduct(new PerishableProduct("PER-203", "Greek Yogurt 500g", 2.80, 25, 14));   // Full price

        // Electronic items with manufacturer warranties and handling fees
        inventory.addProduct(new ElectronicProduct("ELE-301", "27-inch 4K Gaming Monitor", 300.00, 10, 24));
        inventory.addProduct(new ElectronicProduct("ELE-302", "Noise-Cancelling Headphones", 120.00, 18, 12));
    }

    private static void handleRestock() {
        System.out.println("\n--------------------- RESTOCK PRODUCT ---------------------");
        System.out.print("Enter Product ID to restock: ");
        String id = scanner.nextLine().trim();

        System.out.print("Enter quantity to add: ");
        String qtyInput = scanner.nextLine().trim();

        int quantity;
        try {
            quantity = Integer.parseInt(qtyInput);
        } catch (NumberFormatException e) {
            System.out.println("[!] Quantity must be an integer.\n");
            return;
        }

        try {
            inventory.restockProduct(id, quantity);
            Product p = inventory.findProductById(id);
            System.out.printf("[+] Successfully restocked '%s'. New Stock: %d units.\n\n",
                    p.getName(), p.getQuantityInStock());
        } catch (ProductNotFoundException | InvalidQuantityException e) {
            System.out.println("[!] RESTOCK ERROR: " + e.getMessage() + "\n");
        }
    }

    private static void handleSale() {
        System.out.println("\n--------------------- PROCESS SALE ------------------------");
        System.out.print("Enter Product ID to purchase: ");
        String id = scanner.nextLine().trim();

        System.out.print("Enter quantity to purchase: ");
        String qtyInput = scanner.nextLine().trim();

        int quantity;
        try {
            quantity = Integer.parseInt(qtyInput);
        } catch (NumberFormatException e) {
            System.out.println("[!] Quantity must be an integer.\n");
            return;
        }

        try {
            double totalBill = inventory.sellProduct(id, quantity);
            Product p = inventory.findProductById(id);
            System.out.println("\n====================== SALE RECEIPT ======================");
            System.out.printf(" Product       : %s [%s]\n", p.getName(), p.getId());
            System.out.printf(" Quantity Sold : %d unit(s)\n", quantity);
            System.out.printf(" Total Amount  : $%7.2f\n", totalBill);
            System.out.printf(" Remaining Stk : %d unit(s)\n", p.getQuantityInStock());
            System.out.println("==========================================================\n");
        } catch (ProductNotFoundException | InvalidQuantityException e) {
            System.out.println("[!] SALE REJECTED: " + e.getMessage() + "\n");
        }
    }

    private static void handleSearch() {
        System.out.println("\n--------------------- SEARCH CATALOG ----------------------");
        System.out.print("Enter product name keyword: ");
        String keyword = scanner.nextLine().trim();

        java.util.List<Product> matches = inventory.searchByName(keyword);
        if (matches.isEmpty()) {
            System.out.println("[-] No products matched '" + keyword + "'.\n");
        } else {
            System.out.printf("[+] Found %d match(es):\n", matches.size());
            for (Product p : matches) {
                System.out.println(" -> " + p);
            }
            System.out.println();
        }
    }

    private static void handleLowStockCheck() {
        System.out.print("Enter low-stock warning threshold (default 10): ");
        String input = scanner.nextLine().trim();
        int threshold = 10;
        if (!input.isEmpty()) {
            try {
                threshold = Integer.parseInt(input);
            } catch (NumberFormatException ignored) {}
        }
        inventory.displayLowStockAlerts(threshold);
    }

    private static void runAutomatedTestSuite() {
        System.out.println("\n==================================================================");
        System.out.println("            RUNNING AUTOMATED OOP VERIFICATION TESTS              ");
        System.out.println("==================================================================");

        int passed = 0;
        int total = 0;

        // Test 1: Stock Addition & Valuation
        total++;
        try {
            Inventory testInv = new Inventory();
            Product p = new Product("T-01", "Test Widget", 10.00, 5);
            testInv.addProduct(p);
            testInv.restockProduct("T-01", 10);
            if (p.getQuantityInStock() == 15 && Math.abs(testInv.calculateTotalInventoryValue() - 150.00) < 0.001) {
                System.out.println(" [PASS] Test 1: Restocking increases units and updates total valuation ($150.00).");
                passed++;
            } else {
                System.out.println(" [FAIL] Test 1: Restock count or valuation calculation mismatch.");
            }
        } catch (Exception e) {
            System.out.println(" [FAIL] Test 1 encountered exception: " + e.getMessage());
        }

        // Test 2: Product Sale & Bill Calculation
        total++;
        try {
            Inventory testInv = new Inventory();
            Product p = new Product("T-02", "Gadget", 20.00, 10);
            testInv.addProduct(p);
            double bill = testInv.sellProduct("T-02", 3);
            if (p.getQuantityInStock() == 7 && Math.abs(bill - 60.00) < 0.001) {
                System.out.println(" [PASS] Test 2: Product sale deducts stock and bills exact subtotal ($60.00 for 3 units).");
                passed++;
            } else {
                System.out.println(" [FAIL] Test 2: Stock deduction or sale bill mismatch.");
            }
        } catch (Exception e) {
            System.out.println(" [FAIL] Test 2 encountered exception: " + e.getMessage());
        }

        // Test 3: InvalidQuantityException on Negative Restock
        total++;
        try {
            Inventory testInv = new Inventory();
            Product p = new Product("T-03", "Component", 5.00, 10);
            testInv.addProduct(p);
            testInv.restockProduct("T-03", -5);
            System.out.println(" [FAIL] Test 3: Allowed negative restock quantity.");
        } catch (InvalidQuantityException e) {
            System.out.println(" [PASS] Test 3: Intercepted InvalidQuantityException on negative restock: " + e.getMessage());
            passed++;
        } catch (Exception e) {
            System.out.println(" [FAIL] Test 3: Threw unexpected exception type: " + e.getClass().getName());
        }

        // Test 4: InvalidQuantityException on Insufficient Stock
        total++;
        try {
            Inventory testInv = new Inventory();
            Product p = new Product("T-04", "Battery", 8.00, 4);
            testInv.addProduct(p);
            testInv.sellProduct("T-04", 10); // Attempting to purchase 10 when only 4 exist
            System.out.println(" [FAIL] Test 4: Allowed overselling stock.");
        } catch (InvalidQuantityException e) {
            System.out.println(" [PASS] Test 4: Intercepted InvalidQuantityException on insufficient stock: " + e.getMessage());
            passed++;
        } catch (Exception e) {
            System.out.println(" [FAIL] Test 4: Threw unexpected exception type: " + e.getClass().getName());
        }

        // Test 5: ProductNotFoundException on Unregistered Item
        total++;
        try {
            Inventory testInv = new Inventory();
            testInv.sellProduct("NON-EXISTENT", 1);
            System.out.println(" [FAIL] Test 5: Sold unregistered product.");
        } catch (ProductNotFoundException e) {
            System.out.println(" [PASS] Test 5: Intercepted ProductNotFoundException: " + e.getMessage());
            passed++;
        } catch (Exception e) {
            System.out.println(" [FAIL] Test 5: Threw unexpected exception type: " + e.getClass().getName());
        }

        // Test 6: Perishable Product Markdown Discount
        total++;
        try {
            PerishableProduct milk = new PerishableProduct("PER-01", "Milk", 10.00, 5, 2); // 2 days -> 30% off ($7.00)
            if (Math.abs(milk.getEffectivePrice() - 7.00) < 0.001 && Math.abs(milk.calculateTotalValue() - 35.00) < 0.001) {
                System.out.println(" [PASS] Test 6: Perishable item expiring in 2 days applies 30% markdown ($7.00/unit).");
                passed++;
            } else {
                System.out.printf(" [FAIL] Test 6: Discount calculation error. Got $%.2f\n", milk.getEffectivePrice());
            }
        } catch (Exception e) {
            System.out.println(" [FAIL] Test 6 encountered exception: " + e.getMessage());
        }

        // Test 7: Low Stock Detection
        total++;
        try {
            Inventory testInv = new Inventory();
            testInv.addProduct(new Product("L-01", "Item A", 5.0, 3));
            testInv.addProduct(new Product("L-02", "Item B", 5.0, 20));
            java.util.List<Product> low = testInv.getLowStockProducts(5);
            if (low.size() == 1 && low.get(0).getId().equals("L-01")) {
                System.out.println(" [PASS] Test 7: Successfully detected low-stock items below threshold.");
                passed++;
            } else {
                System.out.println(" [FAIL] Test 7: Low stock detection mismatch.");
            }
        } catch (Exception e) {
            System.out.println(" [FAIL] Test 7 encountered exception: " + e.getMessage());
        }

        // Test 8: Electronic Product Effective Price & Handling Fee
        total++;
        try {
            ElectronicProduct monitor = new ElectronicProduct("T-ELE1", "Gaming Monitor", 200.00, 4, 12, 0.05); // 5% fee ($210.00)
            if (Math.abs(monitor.getEffectivePrice() - 210.00) < 0.001 && Math.abs(monitor.calculateTotalValue() - 840.00) < 0.001) {
                System.out.println(" [PASS] Test 8: Electronic product calculates 5% warranty handling fee ($210.00 unit, $840.00 total).");
                passed++;
            } else {
                System.out.printf(" [FAIL] Test 8: Effective price mismatch. Got $%.2f\n", monitor.getEffectivePrice());
            }
        } catch (Exception e) {
            System.out.println(" [FAIL] Test 8 encountered exception: " + e.getMessage());
        }

        // Test 9: Sales History Recording and Revenue Accumulation
        total++;
        try {
            Inventory testInv = new Inventory();
            testInv.addProduct(new Product("T-S1", "Item S1", 25.00, 10));
            testInv.sellProduct("T-S1", 2); // $50.00
            testInv.sellProduct("T-S1", 1); // $25.00
            if (testInv.getSalesHistory().size() == 2 && Math.abs(testInv.getTotalRevenueCollected() - 75.00) < 0.001) {
                System.out.println(" [PASS] Test 9: Sales ledger records transactions and accumulates revenue ($75.00 across 2 sales).");
                passed++;
            } else {
                System.out.printf(" [FAIL] Test 9: Revenue ledger mismatch. Got $%.2f\n", testInv.getTotalRevenueCollected());
            }
        } catch (Exception e) {
            System.out.println(" [FAIL] Test 9 encountered exception: " + e.getMessage());
        }

        System.out.println("------------------------------------------------------------------");
        System.out.printf(" Verification Summary: %d / %d PASSED (%.1f%%)\n",
                passed, total, ((double) passed / total) * 100);
        System.out.println("==================================================================\n");
    }
}
