/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package UI;

import domain.Admin;
import domain.PaymentMethod;
import domain.Product;
import domain.Supplier;
import domain.Warehouse;
import java.time.Period;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;
import logic.AdminManag;
import logic.Inventory;
import logic.Order;
import logic.Purchase;

/**
 *
 * @author lenovo
 */
public class UserInterface {
  private Scanner scan;
  private Inventory inv;
  private AdminManag adminManag;
  
  public UserInterface(Scanner s){
    this.scan=s;
    this.inv=new Inventory();
    this.adminManag=new AdminManag();
  }
  
  public void start(){
    System.out.println("[R]: Register");
    System.out.println("[L]: Login");
    String answer = this.scan.nextLine().toUpperCase();
    if(answer.equals("R")){
      this.registerPage();
    }else{
      this.loginPage();
    }
  }
  
  private void registerPage(){
    System.out.println("Enter the username");
    String name = this.scan.nextLine();
    while(name.isBlank()){
      System.out.println("Username is blank! Try again");
      name = this.scan.nextLine();
    }
    System.out.println("Enter the Password");
    String code = this.scan.nextLine();
    while(!this.adminManag.isSecure(code)){
      System.out.println("The password isn't secure! Try again");
      code = this.scan.nextLine();
    }
    System.out.println("Confirm Password");
    String confirmed = this.scan.nextLine();
    while(!confirmed.equals(code)){
      System.out.println("The confirmed password doesn't equal the original! Try again");
      confirmed = this.scan.nextLine();
    }
    System.out.println("Enter Email");
    String email = this.scan.nextLine();
    while(!this.adminManag.isValidEmail(email)){
      System.out.println("The email isn't valid");
      email = this.scan.nextLine();
    }
    var admin = new Admin(name, code, email);
    this.adminManag.registerAdmin(name, code, email);
    if(this.adminManag.getAdmins().contains(admin) && this.adminManag.getPasswordList().contains(code) && this.adminManag.getUsernameList().contains(name)){
      System.out.println("You're successfully registrated");
      System.out.println("Next Time you login, Do you want to be remembered?");
      System.out.println("[Y]: Yes");
      System.out.println("[N]: No");
      String ans = this.scan.nextLine().toUpperCase();
      if(ans.equals("Y")){
        this.adminManag.rememberUser(admin);
        this.loginPage();
      }else{
        this.loginPage();
      }
    }else{
      System.out.println("Failed to register the account. Try again");
      this.registerPage();
    }
  }
  
  private void loginPage(){
    System.out.println("Enter the username");
    String name = this.scan.nextLine();
    if(this.adminManag.isAdminAmongRemembered(name)){
      this.homePage();
    }else{
      if(this.adminManag.getUsernameList().contains(name)){
        System.out.println("Enter the password (3 wrong tries will quit!)");
        String code = this.scan.nextLine();
        int count = 1;
        while(!this.adminManag.getPasswordList().contains(code) && count<=3){
          System.out.println("Invalid Try. Try again. " + (3-count) + " tries to quit!");
          code = this.scan.nextLine();
          count++;
        }
        if(this.adminManag.isAdminRegistrated(name, code)){
          this.homePage();
        }else{
          System.out.println("You need to register!");
          this.registerPage();
        }
      }else{
        System.out.println("Username nowhere to be found. You have to register first!");
        this.registerPage();
      }
    }
  }
  
  private void homePage(){
    while(true){
      System.out.println("Welcome To our Home Page");
      System.out.println("[P]: Product Management");
      System.out.println("[O]: Order Management");
      System.out.println("[H]: Purchase Management");
      System.out.println("[T]: Manage Other Things");
      System.out.println("[R]: Generate Reports");
      System.out.println("[X]: Quit");
      String answer = this.scan.nextLine().toUpperCase();
      if(answer.equals("X")){
        break;
      }
      if(answer.equals("P")){
        this.productManagement();
      }else if(answer.equals("O")){
        this.orderManagement();
      }else if(answer.equals("H")){
        this.purchaseManagement();
      }else if(answer.equals("T")){
        this.otherManagement();
      }else{
        this.generateReports();
      }
    }
  }
  
  private void productManagement(){
    while(true){
      System.out.println("[A]: Add Product");
      System.out.println("[D]: View Product Directory");
      System.out.println("[E]: Edit Product");
      System.out.println("[R]: Delete Product");
      System.out.println("[V]: View Product Details");
      System.out.println("[X]: Quit");
      String ans = this.scan.nextLine().toUpperCase();
      if(ans.equals("X")){
        break;
      }
      if(ans.equals("A")){
        this.addProductPage();
      }else if(ans.equals("E")){
        this.editProductPage();
      }else if(ans.equals("V")){
        this.viewProductDetails();
      }else if(ans.equals("R")){
        this.deleteProductPage();
      }else{
        this.inv.viewProductDirectory();
      }
    }
  }
  
  private Supplier getSupplierPage(){
    System.out.println("Enter the supplier name");
    String name = this.scan.nextLine();
    return this.inv.getSupplierForName(name);
  }
  
  private Warehouse getWarehousePage(){
    System.out.println("Enter the warehouse name");
    String name = this.scan.nextLine();
    return this.inv.getWarehouseForName(name);
  }
  
  private void addProductPage(){
    System.out.println("Enter the Product name");
    String name = this.scan.nextLine();
    Supplier supp = this.getSupplierPage();
    Warehouse wh = this.getWarehousePage();
    System.out.println("Enter the product qty");
    int qty = Integer.parseInt(this.scan.nextLine());
    System.out.println("Enter the product treeshold");
    int treeshold = Integer.parseInt(this.scan.nextLine());
    System.out.println("Enter the product selling price in dollars");
    double price = Double.parseDouble(this.scan.nextLine());
    System.out.println("Enter the product cost in dollars");
    double cost = Double.parseDouble(this.scan.nextLine());
    this.inv.addProduct(name, supp, wh, qty, treeshold, price, cost);
    if(this.inv.getProductForName(name)!=null){
      System.out.println("Product Added Successfully!");
    }else{
      System.out.println("We couldn't add the product! Try again");
      this.addProductPage();
    }
  }
  
  private Product getProductPage(){
    System.out.println("Enter the Product id: ");
    String id = this.scan.nextLine();
    return this.inv.getProductForID(id);
  }
  
  private void editProductPage(){
    while(true){
      System.out.println("[N]: Edit Product Name");
      System.out.println("[T]: Edit Product Treeshold");
      System.out.println("[P]: Edit Product Price");
      System.out.println("[C]: Edit Product Cost");
      System.out.println("[X]: Quit");
      String ans = this.scan.nextLine().toUpperCase();
      if(ans.equals("X")){
        break;
      }
      if(ans.equals("N")){
        Product p = this.getProductPage();
        System.out.println("Enter the new Name");
        String name = this.scan.nextLine();
        p.setName(name);
        if(p.getName().equals(name)){
          System.out.println("Product Name Changed Successfully");
        }
      }else if(ans.equals("T")){
        Product p = this.getProductPage();
        System.out.println("Enter the new Treeshold");
        int t = Integer.parseInt(this.scan.nextLine());
        p.setTreeshold(t);
        if(p.getTreeshold()==t){
          System.out.println("Product Treeshold Changed Successfully");
        }
      }else if(ans.equals("P")){
        Product p = this.getProductPage();
        System.out.println("Enter the new Price");
        double price = Double.parseDouble(this.scan.nextLine());
        p.setSellingPricePerunit(price);
        if(p.getSellingPricePerunit()==price){
          System.out.println("Product Price Changed Successfully");
        }
      }else{
        Product p = this.getProductPage();
        System.out.println("Enter the new Cost");
        double cost = Double.parseDouble(this.scan.nextLine());
        p.setCostPerUnit(cost);
        if(p.getCostPerUnit()==cost){
          System.out.println("Product Cost Changed Successfully");
        }
      }
    }
  }
  
  private void viewProductDetails(){
    System.out.println("Enter the Product id");
    String id = this.scan.nextLine();
    this.inv.viewProductDetails(id);
  }
  
  private void deleteProductPage(){
    System.out.println("Enter the Product id");
    String id = this.scan.nextLine();
    this.inv.removeProduct(id);
    if(this.inv.getProductForID(id)==null){
      System.out.println("Product Successfully Removed");
    }
  }
  
  private void orderManagement(){
    while(true){
      System.out.println("[P]: Create New Order");
      System.out.println("[T]: Track Order");
      System.out.println("[U]: Update Order Status");
      System.out.println("[V]: View Order Details");
      System.out.println("[X]: Quit");
      String ans = this.scan.nextLine().toUpperCase();
      if(ans.equals("X")){
        break;
      }
      if(ans.equals("T")){
        this.trackOrderStatus();
      }else if(ans.equals("U")){
        this.updateOrderStatusPage();
      }else if(ans.equals("V")){
        this.viewOrderDetailsPage();
      }else{
        this.placeOrderPage();
      }
    }
  }
  
  private Order getOrderPage(){
    System.out.println("Enter the Order id");
    String id = this.scan.nextLine();
    return this.inv.getOrderForID(id);
  }
  
  private void trackOrderStatus(){
    Order o = this.getOrderPage();
    if(o!=null){
      System.out.println("The Status of order: " + o.getStatus());
    }else{
      System.out.println("No Order was found for the id");
    }
  }
  
  public Map<String, PaymentMethod> methodMap(){
    Map<String, PaymentMethod> map = new HashMap<>();
    map.put("D", PaymentMethod.DEBIT);
    map.put("C", PaymentMethod.CREDIT);
    map.put("P", PaymentMethod.PAYPAL);
    map.put("B", PaymentMethod.BANK_TRANSFER);
    return map;
  }
  
  private void placeOrderPage(){
    Map<Product, Integer> map = new HashMap<>();
    System.out.println("Enter the product ID (Empty line for the product id will stop)");
    String pid = this.scan.nextLine();
    while(!pid.isBlank()){
      System.out.println("Enter the Qty");
      int qty = Integer.parseInt(this.scan.nextLine());
      map.put(this.inv.getProductForID(pid), qty);
      System.out.println("Enter the product ID (Empty line for the product id will stop)");
      pid = this.scan.nextLine();
    }
    System.out.println("Enter the customer id");
    String custID = this.scan.nextLine();
    System.out.println("Enter the Payment Method");
    System.out.println("[B]: Bank Transfer");
    System.out.println("[C]: Credit Card");
    System.out.println("[D]: Debit Card");
    System.out.println("[P]: Paypal");
    String ans = this.scan.nextLine();
    var meth = this.methodMap().get(ans);
    this.inv.placeOrder(map, pid, meth);
  }
  
  private void viewOrderDetailsPage(){
    System.out.println("Enter the Order id");
    String id = this.scan.nextLine();
    this.inv.viewOrderDetails(id);
  }
  
  private void updateOrderStatusPage(){
    Order o = this.getOrderPage();
    String id = o.getId().toString();
    System.out.println("The order with id: " + id + " is Currently: " + o.getStatus());
    System.out.println("[P]: Proccess Order");
    System.out.println("[S]: Ship Order");
    System.out.println("[D]: Deliver Order");
    System.out.println("[C]: Cancel Order");
    String ans = this.scan.nextLine().toUpperCase();
    if(ans.equals("P")){
      this.inv.processOrder(id);
    }else if(ans.equals("D")){
      this.inv.deliverOrder(id);
    }else if(ans.equals("S")){
      this.inv.shipOrder(id);
    }else{
      this.inv.cancellOrder(id);
    }
  }
  
  private void purchaseManagement(){
    while(true){
      System.out.println("[B]: Submit Purchase");
      System.out.println("[N]: View Purchase Notifications");
      System.out.println("[C]: Confirm Purchase Arrival");
      System.out.println("[V]: View Purchase Details");
      System.out.println("[X]: Quit");
      String ans = this.scan.nextLine().toUpperCase();
      if(ans.equals("X")){
        break;
      }
      if(ans.equals("B")){
        this.submitPurchasePage();
      }else if(ans.equals("N")){
        this.inv.viewNotifications();
      }else if(ans.equals("C")){
        this.confirmPurchasePage();
      }else{
        this.viewPurchaseDetailsPage();
      }
    }
  }
  
  private Purchase getPurchasePage(){
    System.out.println("Enter the Purchase ID");
    String id = this.scan.nextLine();
    return this.inv.getPurchaseForID(id);
  }
  
  private void submitPurchasePage(){
    System.out.println("Enter the Supplier name");
    String suppName = this.scan.nextLine();
    System.out.println("Enter the Payment Method");
    System.out.println("[B]: Bank Transfer");
    System.out.println("[C]: Credit Card");
    System.out.println("[D]: Debit Card");
    System.out.println("[P]: Paypal");
    String ans = this.scan.nextLine();
    var meth = this.methodMap().get(ans);
    Purchase pur = this.inv.placePurchase(suppName, meth);
    System.out.println("Enter the product id you'd like to purchase (Empty line of product id will stop)");
    String id = this.scan.nextLine();
    while(!id.isBlank()){
      System.out.println("Enter the quantity desired");
      int qty = Integer.parseInt(this.scan.nextLine());
      this.inv.buyProduct(pur, id, qty);
      System.out.println("Enter the product id you'd like to purchase (Empty line of product id will stop)");
      id = this.scan.nextLine();
    }
  }
  
  private void confirmPurchasePage(){
    System.out.println("Enter the purchase id");
    String id = this.scan.nextLine();
    this.inv.confirmPurchase(id);
  }
  
  private void viewPurchaseDetailsPage(){
    Purchase p = this.getPurchasePage();
    System.out.println(p);
  }
  
  private void otherManagement(){
    while(true){
      System.out.println("[W]: Add Warehouse");
      System.out.println("[S]: Add Supplier");
      System.out.println("[C]: Add Customer");
      System.out.println("[VW]: View Warehouses");
      System.out.println("[VS]: View Suppliers");
      System.out.println("[VC]: View Customers");
      System.out.println("[X]: Quit");
      String ans = this.scan.nextLine().toUpperCase();
      if(ans.equals("X")){
        break;
      }
      if(ans.equals("W")){
        this.addWarehousePage();
      }else if(ans.equals("C")){
        this.addCustomerPage();
      }else if(ans.equals("S")){
        this.addSupplierPage();
      }else if(ans.equals("VC")){
        this.inv.viewAllCustomers();
      }else if(ans.equals("VW")){
        this.inv.viewAllWarehouses();
      }else{
        this.inv.viewAllSuppliers();
      }
    }
    
  }
  
  private void addWarehousePage(){
    System.out.println("Enter Warehouse Name");
    String name = this.scan.nextLine();
    System.out.println("Enter the Customer Address");
    String address = this.scan.nextLine();
    System.out.println("Enter the Customer Capacity");
    int cap = Integer.parseInt(this.scan.nextLine());
    this.inv.addWarehouse(name, address, cap);
    if(this.inv.getWarehouseForName(name)!=null){
      System.out.println("Warehouse Successfully added!");
    }
  }
  
  private void addCustomerPage(){
    System.out.println("Enter Customer Name");
    String name = this.scan.nextLine();
    System.out.println("Enter the Customer Address");
    String address = this.scan.nextLine();
    System.out.println("Enter the Customer Email");
    String email = this.scan.nextLine();
    this.inv.addCustomer(name, address, email);
    if(this.inv.getCustForName(name)!=null){
      System.out.println("Customer Successfully added!");
    }
  }
  
  private void addSupplierPage(){
    System.out.println("Enter Supplier Name");
    String name = this.scan.nextLine();
    System.out.println("Enter the Supplier Address");
    String address = this.scan.nextLine();
    System.out.println("Enter the Supplier Email");
    String email = this.scan.nextLine();
    System.out.println("Enter the Supplier Phone");
    String phone = this.scan.nextLine();
    System.out.println("Enter the Supplier Minimum Order Qty");
    int qty = Integer.parseInt(this.scan.nextLine());
    System.out.println("Enter the Supplier Lead Time in Days");
    Period p = Period.ofDays(Integer.parseInt(this.scan.nextLine()));
    this.inv.addSupplier(name, address, email, phone, qty, p);
    if(this.inv.getSupplierForName(name)!=null){
      System.out.println("Supplier Successfully Added!");
    }
  }
  
  private void generateReports(){
    while(true){
      System.out.println("[T]: View Audit Trails");
      System.out.println("[M]: Most Used Payement Methods");
      System.out.println("[P]: Most Sold Products");
      System.out.println("[O]: Orders With Highest Prices");
      System.out.println("[C]: Customers With Most Orders");
      System.out.println("[W]: Warehouses With Most Empty Space");
      System.out.println("[X]: Quit");
      String ans = this.scan.nextLine().toUpperCase();
      if(ans.equals("X")){
        break;
      }
      if(ans.equals("M")){
        this.inv.mostUsedPaymentMethods();
      }else if(ans.equals("O")){
        this.inv.sortOrderBasedOnTotalPrice();
      }else if(ans.equals("P")){
        this.inv.sortProductsBasedOnSales();
      }else if(ans.equals("C")){
        this.inv.sortCustomersByNumberOfOrders();
      }else if(ans.equals("T")){
        this.inv.viewTrails();
      }else{
        this.inv.sortWarehousesBasedOnEmptySpace();
      }
    }
  }
}
