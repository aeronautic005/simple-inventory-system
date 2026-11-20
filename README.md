# Simple Product Inventory Management System

A beginner-friendly Object-Oriented Java console application managing retail products, inventory stock levels, restock replenishments, sales billing, dynamic markdown discounts, and low-stock alerts.

## System Architecture

```text
+-------------------------------------------------------------------------------+
|                                 InventoryApp                                  |
|                         (CLI Kiosk & Interactive UI)                          |
+---------------------------------------+---------------------------------------+
                                        |
                                        v
+-------------------------------------------------------------------------------+
|                                  Inventory                                    |
|                (Product Registry, Valuation, Low Stock Alerts)                |
+---------------------------------------+---------------------------------------+
                                        |
                   +--------------------+--------------------+
                   |                                         |
                   v                                         v
         +--------------------+                    +--------------------+
         |      Product       |                    |  PerishableProduct |
         | (Standard Goods)   |                    | (Expiration Logic) |
         +--------------------+                    +--------------------+
                   |
                   v
+-------------------------------------------------------------------------------+
|                              Exceptions Hierarchy                             |
|  + InvalidQuantityException  (Validates positive numbers & stock availability)|
|  + ProductNotFoundException  (Handles lookup failures in catalog)            |
+-------------------------------------------------------------------------------+
```

## Perishable Clearance Discount Rules

| Expiration Threshold | Clearance Discount | Effective Selling Price |
| :--- | :---: | :---: |
| **> 7 Days Remaining** | 0% (Standard) | 100% of Base Price |
| **4 to 7 Days Remaining** | 15% Clearance | 85% of Base Price |
| **1 to 3 Days Remaining** | 30% Clearance | 70% of Base Price |
| **0 Days (Expired)** | 100% Markdown | $0.00 (Unsellable / Discard) |

## Key Object-Oriented Concepts Illustrated

1. **Encapsulation:** All fields (`id`, `name`, `unitPrice`, `quantityInStock`) are protected/private with validation checks.
2. **Inheritance & Polymorphism:** `PerishableProduct` extends `Product`, dynamically overriding `calculateTotalValue()` and price calculation based on days left until expiration.
3. **Custom Exception Handling:** `InvalidQuantityException` and `ProductNotFoundException` provide informative error messages.
4. **Console UI & Built-in Unit Tests:** Interactive menu with real-time ASCII table formatting, stock replenishments, sales receipts, and an automated verification test runner.

## How to Compile & Run

```bash
# Navigate into the project folder
cd simple-inventory-system

# Compile all Java files
javac *.java

# Run the interactive Inventory system
java InventoryApp
```

### Running the Test Suite
Select Option `[6]` from the main menu to run the 7 automated unit verification tests.
