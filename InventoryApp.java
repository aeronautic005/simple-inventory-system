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
            System.out.print("Enter option (1-7): ");
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
                    runAutomatedTestSuite();
                    break;
                case "7":
                    System.out.println("\nExiting Inventory Management System. Have a great day!\n");
                    running = false;
                    break;
                default:
                    System.out.println("\n[!] Invalid choice. Please enter a number between 1 and 7.\n");
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
        System.out.println(" [6] Run Automated OOP Verification Tests");
        System.out.println(" [7] Exit");
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
        System.out.println("\n[Automated Test Suite will be finalized in the next commit]\n");
    }
}
