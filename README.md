# Simple Product Inventory Management System

An expanded yet beginner-friendly Object-Oriented Java console application managing retail products, inventory stock levels, restock replenishments, sales billing, dynamic markdown discounts, electronic warranty handling, fragile packaging surcharges, department category audits, CSV exports, sales audit ledgers, and low-stock alerts.

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
|   (Catalog, Category Audits, Stock Sorting, Bulk Restock, CSV Export, Ledger) |
+-------------------+-------------------+-------------------+-------------------+
                    |                   |                   |
                    v                   v                   v
         +--------------------+  +--------------+  +--------------------+
         |      Product       |  |  SaleRecord  |  |  ProductCategory   |
         | (Standard Goods)   |  | (Receipt/Log)|  |  (Dept & Tax Rate) |
         +---------+----------+  +--------------+  +--------------------+
                   |
     +-------------+-------------+
     |             |             |
     v             v             v
+----------+ +-----------+ +------------+
|Perishable| |Electronic | |  Fragile   |
| (Expiry) | | (Warranty)| | (Packaging)|
+----------+ +-----------+ +------------+
                   |
                   v
+-------------------------------------------------------------------------------+
|                              Exceptions Hierarchy                             |
|  + InvalidQuantityException  (Validates positive numbers & stock availability)|
|  + ProductNotFoundException  (Handles lookup failures in catalog)            |
+-------------------------------------------------------------------------------+
```

## Department Categories & Tax Rates

| Category Key | Department Name | Applicable Sales Tax Rate |
| :--- | :--- | :---: |
| **GROCERY** | Grocery & Perishables | 0.0% (Essential) |
| **ELECTRONICS** | Consumer Electronics | 7.5% |
| **HARDWARE** | Tools & Hardware | 5.0% |
| **APPAREL** | Clothing & Apparel | 4.0% |
| **STATIONERY** | Office & School Supplies | 3.0% |
| **GENERAL** | General Merchandise | 6.0% |

## Pricing, Surcharges & Markdown Rules

| Product Category | Special Rule / Multiplier | Effective Selling Price |
| :--- | :--- | :--- |
| **Standard Product** | Base Price | 100% of Base Price |
| **Electronic Product** | Manufacturer Warranty + 5% Handling Fee | Base Price * 1.05 |
| **Fragile Product** | Specialized Protective Packaging | Base Price + Packaging Surcharge |
| **Perishable (> 7 days)** | Fresh Stock (0% Markdown) | 100% of Base Price |
| **Perishable (4 - 7 days)**| Approaching Expiry (15% Clearance) | 85% of Base Price |
| **Perishable (1 - 3 days)**| Imminent Expiry (30% Clearance) | 70% of Base Price |
| **Perishable (0 days)** | Expired / Spoiled | $0.00 (Unsellable) |

## Key Object-Oriented Concepts Illustrated

1. **Encapsulation:** Protected/private fields across all models with validation checks on non-negative values.
2. **Inheritance & Polymorphism:** `PerishableProduct`, `ElectronicProduct`, and `FragileProduct` extend base `Product`, dynamically overriding effective prices and total valuations.
3. **Enum Domain Modeling:** `ProductCategory` encapsulates retail departments and applicable sales tax rates.
4. **Transaction History & Auditing:** `SaleRecord` logs completed purchase orders with unique IDs, quantities, timestamps, and ASCII itemized receipts.
5. **Advanced Inventory Operations:** Price and stock level sorting, category-wide promotional markdowns, bulk batch restocking, and raw CSV catalog export.
6. **Custom Exception Handling:** `InvalidQuantityException` and `ProductNotFoundException` enforce business rules during restocking and sales.
7. **Automated Test Suite:** Built-in verification test runner executing 13 distinct unit test scenarios covering all OOP models and exception boundaries.

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
Select Option `[9]` from the main menu to run all 13 automated unit verification tests.
