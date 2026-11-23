# Simple Product Inventory Management System

A beginner-friendly Object-Oriented Java console application managing retail products, inventory stock levels, restock replenishments, sales billing, dynamic markdown discounts, electronic warranty handling, sales audit ledgers, and low-stock alerts.

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
|         (Product Registry, Valuation, Low Stock Alerts, Sales Ledger)         |
+-------------------+-------------------+-------------------+-------------------+
                    |                   |                   |
                    v                   v                   v
         +--------------------+  +--------------+  +--------------------+
         |      Product       |  |  SaleRecord  |  | PerishableProduct  |
         | (Standard Goods)   |  |  (Audit Log) |  | (Expiration Logic) |
         +---------+----------+  +--------------+  +--------------------+
                   |
                   v
         +--------------------+
         | ElectronicProduct  |
         | (Warranty & Fees)  |
         +--------------------+
                   |
                   v
+-------------------------------------------------------------------------------+
|                              Exceptions Hierarchy                             |
|  + InvalidQuantityException  (Validates positive numbers & stock availability)|
|  + ProductNotFoundException  (Handles lookup failures in catalog)            |
+-------------------------------------------------------------------------------+
```

## Pricing & Clearance Rules

| Product Category | Special Rule / Multiplier | Effective Selling Price |
| :--- | :--- | :--- |
| **Standard Product** | Base Price | 100% of Base Price |
| **Electronic Product** | Manufacturer Warranty + 5% Handling Fee | Base Price * 1.05 |
| **Perishable (> 7 days)** | Fresh Stock (0% Markdown) | 100% of Base Price |
| **Perishable (4 - 7 days)**| Approaching Expiry (15% Clearance) | 85% of Base Price |
| **Perishable (1 - 3 days)**| Imminent Expiry (30% Clearance) | 70% of Base Price |
| **Perishable (0 days)** | Expired / Spoiled | $0.00 (Unsellable) |

## Key Object-Oriented Concepts Illustrated

1. **Encapsulation:** Protected/private fields across all models with validation checks on non-negative values.
2. **Inheritance & Polymorphism:** `PerishableProduct` and `ElectronicProduct` extend base `Product`, dynamically overriding effective prices and total valuations.
3. **Transaction History & Auditing:** `SaleRecord` logs completed purchase orders with unique IDs, quantities, and timestamps.
4. **Custom Exception Handling:** `InvalidQuantityException` and `ProductNotFoundException` enforce business rules during restocking and sales.
5. **Console UI & Built-in Unit Tests:** Interactive menu with real-time ASCII table formatting, stock replenishments, sales receipts, sales audit ledger, and 9 automated verification tests.

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
Select Option `[7]` from the main menu to run the 9 automated unit verification tests.
