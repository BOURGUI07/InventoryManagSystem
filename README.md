# Inventory Management System

## Description

This project is a console-based application developed in Core Java, designed to manage inventory operations. It serves as a management system where administrators can perform various tasks related to inventory management, including product management, inventory tracking, order processing, reporting, and more. The application is built to demonstrate object-oriented programming principles, console I/O operations, and unit testing with JUnit in Java, tailored specifically for inventory management.

## Features

1. **User Authentication and Authorization**
   - Secure login system to ensure that only authorized personnel can access the system.
   - Different roles such as admin and manager with specific permissions.

   ```
   Inventory Management System
   
   Login
   Username: ___________
   Password: ___________
   
   [Login]
   ```

2. **Product Management**
   - Add, edit, and delete product details in the inventory.
   - Capture essential product information such as Product ID, Name, Description, Price, and Quantity.

   ```
   Inventory Management System
   
   Product Management
   
   1. Add Product
   2. Edit Product
   3. Delete Product
   4. Back
   
   Enter your choice: _
   ```

3. **Inventory Tracking**
   - Track the quantity of products in the inventory.
   - Provides a detailed view of current inventory levels.

   ```
   Inventory Management System
   
   Inventory Tracking
   
   Product Name       Quantity
   ------------------------------
   Product A          100
   Product B          50
   Product C          75
   ...
   ```

4. **Order Processing**
   - Create, track, and update orders.
   - Record order details including Order ID, Product ID, Quantity, Order Date, and Status.

   ```
   Inventory Management System
   
   Order Processing
   
   1. Create New Order
   2. Track Order Status
   3. Update Order Status
   4. Back
   
   Enter your choice: _
   ```

5. **Reporting**
   - Generate reports on inventory levels, sales trends, top-selling products, and pending orders.

   ```
   Inventory Management System
   
   Reporting
   
   1. Inventory Levels
   2. Sales Trends
   3. Top Selling Products
   4. Pending Orders
   5. Back
   
   Enter your choice: _
   ```

6. **Search and Filtering**
   - Search for products and filter inventory and orders based on various criteria.

   ```
   Inventory Management System
   
   Search and Filtering
   
   1. Search Products
   2. Filter Inventory
   3. Filter Orders
   4. Back
   
   Enter your choice: _
   ```

7. **Audit Trail**
   - Maintain an audit trail of all actions performed in the system for accountability and traceability.

   ```
   Inventory Management System
   
   Audit Trail
   
   Date                  User              Action
   --------------------------------------------------------
   2024-05-13 09:30:00   admin            Added Product A
   2024-05-13 10:15:00   manager          Updated Quantity of Product B
   ...
   ```

8. **Notifications**
   - Notify users about important events such as low inventory alerts and order status updates.

   ```
   Inventory Management System
   
   Notifications
   
   - Low Inventory Alert: Product A
   - Order Shipped: Order #1234
   ...
   ```

9. **User Interface**
    - Main menu interface to navigate through different features of the system.

    ```
    Inventory Management System
   
    Main Menu
   
    1. Product Management
    2. Inventory Tracking
    3. Order Processing
    4. Reporting
    5. Search and Filtering
    6. Data Backup and Restore
    7. Audit Trail
    8. Notifications
    9. Logout
   
    Enter your choice: _
    ```

## Technologies Used

- **Java SE (Standard Edition)**: Core language for developing the application.
- **Programming Paradigm Used: OOP.
- **JUnit Framework**: For unit testing of the application's components.
- **Console I/O**: For interaction with the user via the console.
- **Collections Framework**: Utilized for managing products, orders, and inventory information efficiently.
