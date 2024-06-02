/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package logic;
import domain.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Period;
import java.util.*;
import java.util.regex.Pattern;

/**
 *
 * @author lenovo
 */
public class Inventory {
  private List<Product> products;
  private Map<UUID, Product> productMap;
  private List<Supplier> suppliers;
  private Map<String, Supplier> supplierMap;
  private List<Warehouse> warehouses;
  private Map<String, Warehouse> warehouseMap;
  private List<Customer> customers;
  private Map<UUID, Customer> customerMap;
  private List<Order> orders; 
  private Map<UUID, Order> orderMap;
  private List<Purchase> purchases;
  private Map<UUID, Purchase> purchaseMap;
  private List<String> notifications;
  private List<String> trails;
  private Map<PaymentMethod, Integer> payMap;
  
  public Inventory(){
    this.products=new ArrayList<>();
    this.suppliers=new ArrayList<>();
    this.warehouses = new ArrayList<>();
    this.productMap = new HashMap<>();
    this.supplierMap=new HashMap<>();
    this.warehouseMap=new HashMap<>();
    this.customerMap=new HashMap<>();
    this.customers=new ArrayList<>();
    this.orders=new ArrayList<>();
    this.orderMap=new HashMap<>();
    this.purchases=new ArrayList<>();
    this.purchaseMap=new HashMap<>();
    this.notifications=new ArrayList<>();
    this.trails=new ArrayList<>();
    this.payMap=new HashMap<>();
    this.payMap.put(PaymentMethod.BANK_TRANSFER,0);
    this.payMap.put(PaymentMethod.CREDIT,0);
    this.payMap.put(PaymentMethod.DEBIT,0);
    this.payMap.put(PaymentMethod.PAYPAL,0);
  }
  
  public Map<PaymentMethod, Integer> getPayMap(){
    return this.payMap;
  }
  
  public List<Customer> getCustomers(){
    return this.customers;
  }
  
  public List<Supplier> getSuppliers(){
    return this.suppliers;
  }
  
  public List<Warehouse> getWarehouses(){
    return this.warehouses;
  }
  
  public Map<String, Warehouse> getWarehouseMap(){
    return this.warehouseMap;
  }
  
  public Map<String, Supplier> getSupplierMap(){
    return  this.supplierMap;
  }
  
  public void usePaymentMethod(PaymentMethod pm){
    if(this.payMap.get(pm)==0){
      this.payMap.put(pm,1);
    }else{
      int x = this.payMap.get(pm);
      this.payMap.put(pm, x++);
    }
  }
  
  public List<String> getTrails(){
    return this.trails;
  }
  
  public List<Order> getOrders(){
    return this.orders;
  }
  
  public void viewTrails(){
    this.viewInsideList(trails);
  }
  
  public List<Product> getProducts(){
    return this.products;
  }
  
  public void addSupplier(String name, String address, String email, String phone, int minOrderQty, Period leadtime){
    Supplier supp = new Supplier( name,  address,  email,  phone,  minOrderQty,  leadtime);
    if(this.suppliers.contains(supp)){
      throw new IllegalArgumentException("Supplier Already registrated!");
    }else{
      this.suppliers.add(supp);
      this.supplierMap.put(supp.getSuuplierName(), supp);
      this.trails.add(this.trail(Action.ADD_SUPP, name));
    }
  }
  
  public void addWarehouse(String name, String address, int capacity){
    Warehouse wh = new Warehouse(name, address, capacity);
    if(this.warehouses.contains(wh)){
      throw new IllegalArgumentException("Warehouse Already Registrated");
    }else{
      this.warehouses.add(wh);
      this.warehouseMap.put(wh.getName(), wh);
      this.trails.add(this.trail(Action.ADD_WH, name));
    }
  }
  
  public void addProduct(String name, Supplier supplier, Warehouse warehouse, int qty, int treeshold,  double price, double cost){
    Product product = new Product( name,  supplier,  warehouse,  qty,  treeshold,   price,  cost);
    if(this.products.contains(product)){
      throw new IllegalArgumentException("Product Already Registrated");
    }else{
      warehouse.addProduct(product);
      this.products.add(product);
      this.productMap.put(product.getId(), product);
      this.trails.add(this.trail(product.getId().toString(), Action.ADD_PRODUCT));
    }
  }
  
  public Product getProductForName(String name){
    for(Product p:this.products){
      if(p.getName().equals(name)){
        return p;
      }
    }
    return null;
  }
  
  public void removeProduct(String id){
    Product p = this.getProductForID(id);
    if(p!=null){
      this.productMap.remove(p.getId());
      this.products.remove(p);
    }
  }
  
  public Product getProductForID(String id){
    if(this.isValidUUID(id)){
      UUID idd = UUID.fromString(id);
      if(this.productMap.containsKey(idd)){
        return this.productMap.get(idd);
      }else{
        return null;
      }
    }else{
      throw new IllegalArgumentException("ID NOT VALID");
    }
  }
  
  public void addCustomer(String name, String address, String email){
    Customer cust = new Customer(name, address, email);
    if(this.customers.contains(cust)){
      throw new IllegalArgumentException("Customer Already Registrated");
    }else{
      this.customers.add(cust);
      this.customerMap.put(cust.getID(), cust);
      this.trails.add(this.trail(cust.getID().toString(), Action.ADD_CUST));
    }
  }
  
  public Customer getCustForName(String name){
    for(Customer c:this.customers){
      if(c.getName().equals(name)){
        return c;
      }
    }
    return null;
  }
  
  public Customer getCustomerForID(String id){
    if(this.isValidUUID(id)){
      UUID idd = UUID.fromString(id);
      if(this.customerMap.containsKey(idd)){
        return this.customerMap.get(idd);
      }else{
        throw new IllegalArgumentException("ID NOT IN THE LIST!");
      }
    }else{
      throw new IllegalArgumentException("ID NOT VALID");
    }
  }
  
  public Supplier getSupplierForName(String name){
    for(Supplier supp:this.suppliers){
      if(supp.getSuuplierName().equals(name)){
        return supp;
      }
    }
    return null;
  }
  
  public Warehouse getWarehouseForName(String name){
    for(Warehouse wh:this.warehouses){
      if(wh.getName().equals(name)){
        return wh;
      }
    }
    return null;
  }
  
  public void placeOrder(Map<Product,Integer> map, String id, PaymentMethod method){
    if(map.isEmpty()){
      throw new IllegalArgumentException("The List of Order Products is Empty!");
    }
    Customer cust = this.getCustomerForID(id);
    if(cust==null){
      throw new IllegalArgumentException("No customer was found for the ID");
    }
    
    Order order = new Order(cust, method);
    if(this.orders.contains(order)){
      throw new IllegalArgumentException("the order already recorded!");
    }else{
      this.orders.add(order);
      this.orderMap.put(order.getId(), order);
      cust.addOrder(order);
    }
    for(Product p:map.keySet()){
      order.addProductToOrder(p, map.get(p));
    }
    if(!order.getMap().isEmpty()){
      order.setOrderStatus(OrderStatus.PENDING);
      this.trails.add(this.trail(order.getId().toString(), Action.PLACEORDER));
    }else{
      throw new IllegalArgumentException("Failed to Place the Order! Maybe Not enough requested qty was found in the stock to add any product to the order");
    }
  }
  
  public void processOrder(String id){
    Order order = this.getOrderForID(id);
    order.setOrderStatus(OrderStatus.PROCESSED);
    this.trails.add(this.trail(order.getId().toString(), Action.PROCESSORDER));
  }
  
  public void shipOrder(String id){
    Order order = this.getOrderForID(id);
    order.setOrderStatus(OrderStatus.SHIPPED);
    this.trails.add(this.trail(order.getId().toString(), Action.SHIPORDER));
  }
  
  public void deliverOrder(String id){
    Order order = this.getOrderForID(id);
    order.setOrderStatus(OrderStatus.DELIVERED);
    this.trails.add(this.trail(order.getId().toString(), Action.DELIVERORDER));
    for(Product p:order.getMap().keySet()){
      p.increaseSales(order.getMap().get(p));
    }
    this.usePaymentMethod(order.getMethod());
  }
  
  public void cancellOrder(String id){
    Order order = this.getOrderForID(id);
    order.setOrderStatus(OrderStatus.CANCELED);
    this.applyOrderCancellChanges(order);
    this.trails.add(this.trail(order.getId().toString(), Action.CANCELORDER));
  }
  
  public void applyOrderCancellChanges(Order order){
    for(Product p:order.getMap().keySet()){
      p.increaseProductQty(order.getMap().get(p));
    }
  }
  
  public OrderStatus getOrderStatusForID(String id){
    Order order = this.getOrderForID(id);
    if(order==null){
      throw new IllegalArgumentException("No order was found for the id");
    }else{
      return order.getStatus();
    }
  }
  
  public Order getOrderForID(String id){
    if(this.isValidUUID(id)){
      UUID idd = UUID.fromString(id);
      if(this.orderMap.containsKey(idd)){
        return this.orderMap.get(idd);
      }else{
        return null;
      }
    }else{
      throw new IllegalArgumentException("ID NOT VALID");
    }
  }
  
  public boolean isValidUUID(String uuidString) {
    String uuidPattern = 
            "^\\h*\\b[0-9a-fA-F]{8}\\b-\\b[0-9a-fA-F]{4}\\b-\\b[0-9a-fA-F]{4}\\b-\\b[0-9a-fA-F]{4}\\b-\\b[0-9a-fA-F]{12}\\b\\h*$";
    return Pattern.matches(uuidPattern, uuidString);
  }
  
  public void viewProductDetails(String id){
    Product p = this.getProductForID(id);
    if(p!=null){
      System.out.println(p);
    }else{
      throw new IllegalArgumentException("No product was found for the ID!");
    }
  }
  
  public void viewInsideList(List list){
    list.stream().forEach(r -> System.out.println(r));
  }
  
  public void viewAllProducts(){
    this.viewInsideList(products);
  }
  
  public void viewAllOrders(){
    this.viewInsideList(orders);
  }
  
  public void viewAllCustomers(){
    this.viewInsideList(customers);
  }
  
  public void viewAllSuppliers(){
    this.viewInsideList(this.suppliers);
  }
  
  public void viewAllWarehouses(){
    this.viewInsideList(this.warehouses);
  }
  
  public void viewOrderDetails(String id){
    Order order =this.getOrderForID(id);
    if(order!=null){
      if(order.getStatus()==null){
        System.out.println("The order has no products!");
      }else{
        System.out.println(order);
      }
    }else{
      throw new IllegalArgumentException("No Product was found for the ID");
    }
  }
  
  public void sortOrderBasedOnTotalPrice(){
    Collections.sort(this.orders);
    var x = new StringBuilder("OrderID\tOrderQty\tOrderTotalPrice\n");
    for(Order o:this.orders){
      x.append(o.getId()).append("\t").append(o.getNumberOfOrderQty()).append("\t").append(o.getTotalPriceForOrder()).append("\n");
    }
    System.out.println(x);
  }
  
  public void sortCustomersByNumberOfOrders(){
    Collections.sort(this.customers);
    var x = new StringBuilder("CustomerID\tCustomerName\tNumberOfOrders\n");
    for(Customer c:this.customers){
      x.append(c.getID()).append("\t").append(c.getName()).append("\t").append(c.getOrders().size()).append("\n");
    }
    System.out.println(x);
  }
  
  public Purchase placePurchase(String supplierName, PaymentMethod med){
    Supplier supp = this.getSupplierForName(supplierName);
    Purchase p = new Purchase(supp, med);
    if(this.purchases.contains(p)){
      throw new IllegalArgumentException("The purchase already recorded");
    }else{
      this.purchases.add(p);
      this.purchaseMap.put(p.getId(), p);
      this.trails.add(this.trail(p.getId().toString(), Action.PLACEPURCH));
      return p;
    }
  }
  
  public void buyProduct(Purchase pur, String productID, int qty){
    Product p = this.getProductForID(productID);
    if(p!=null && pur!=null){
      pur.addProductToPurchase(p, qty);
    }else if(p==null){
      throw new IllegalArgumentException("No product was found for the id");
    }else if(pur==null){
      throw new IllegalArgumentException("No purchase was found for the id");
    }
  }
  
  public Purchase getPurchaseForID(String id){
    if(this.isValidUUID(id)){
      UUID idd = UUID.fromString(id);
      if(this.purchaseMap.containsKey(idd)){
        return this.purchaseMap.get(idd);
      }else{
        throw new IllegalArgumentException("ID not in the list");
      }
    }else{
      throw new IllegalArgumentException("The id isn't valid");
    }
  }
  
  public void confirmPurchase(String purchID){
    var p = this.getPurchaseForID(purchID);
    p.confirmPurchase();
    this.trails.add(this.trail(p.getId().toString(), Action.CONFIRMPURCH));
    if(p.isConfirmed()){
      p.applyPurchaseChanges();
      this.usePaymentMethod(p.getMethod());
    }
  }
  
  public String purchaseDueDateNot(Purchase p){
    return "Purchase with ID: " + p.getId() + " Date is due today!";
  }
  
  public List<String> getNotifications(){
    for(var p:this.purchases){
      if(p.isPurchaseDateDue()){
        this.notifications.add(purchaseDueDateNot(p));
      }
    }
    return this.notifications;
  }
  
  public void viewNotifications(){
    this.viewInsideList(this.notifications);
  }
  
  public String trail(Action action, String name){
    LocalDateTime time = LocalDateTime.now();
    return action + " With name: " +name+ " On Date: " + time;
  }
  
  public String trail(String id, Action action){
    var time= LocalDateTime.now();
    return action + " with id: " + id + " On Date: " + time;
  }
  
  public void viewProductDirectory(){
    var x = new StringBuilder("Product_Name\tID\tQty\tStock_Level\tWarehouse\tPrice\tCost\n");
    for(Product p:this.products){
      x.append(p.getName()).append("\t").append(p.getId().toString()).append("\t").append(p.getQuantity()).append("\t").append(p.getStockLevelStatus()).append("\t").append(p.getWarehouse().getName()).append("\t").append(p.getSellingPricePerunit()).append("\t").append(p.getCostPerUnit()).append("\n");
    }
    System.out.println(x);
  }
  
  public void sortProductsBasedOnSales(){
    Collections.sort(this.products);
    var x = new StringBuilder("Product_Name\tProduct_ID\tNumber_Of_Sold_Units\n");
    for(Product p:this.products){
      x.append(p.getName()).append("\t").append(p.getId()).append("\t").append(p.getSales()).append("\n");
    }
    System.out.println(x);
  }
  
  public void viewPurchaseDetails(String id){
    Purchase p = this.getPurchaseForID(id);
    if(p!=null){
      System.out.println(p);
    }else{
      throw new IllegalArgumentException("No purchase found for the ID");
    }
  }
  
  public void mostUsedPaymentMethods(){
    var s = new StringBuilder("Payment_Method\tTimes_Of_Use\n");
    List <Integer> x = (List) this.payMap.values();
    Collections.sort(x);
    Collections.reverse(x);
    for(int a:x){
      s.append(this.getPayementMethForQty(a)).append("\t").append(a).append("\n");
    }
    System.out.println(s);
  }
  
  public PaymentMethod getPayementMethForQty(int x){
    for(PaymentMethod pm:this.payMap.keySet()){
      if(this.payMap.get(pm)==x){
        return pm;
      }
    }
    return null;
  }
  
  public void sortWarehousesBasedOnEmptySpace(){
    Collections.sort(this.warehouses);
    var s = new StringBuilder("Warehouse_Name\tMax_Capacity\tCurrent_Ocupied_Space\tCurrent_Empty_Space\n");
    for(Warehouse w:this.warehouses){
      s.append(w.getName()).append("\t").append(w.getCapacity()).append("\t").append(w.getQty()).append("\t").append(w.getEmptySpace()).append("\n");
    }
    System.out.println(s);
  }

}
