/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package logic;
import domain.*;
import java.time.*;
import java.util.*;

/**
 *
 * @author lenovo
 */
public class Order implements Comparable<Order>{
  private Customer cust;
  private LocalDate date;
  private PaymentMethod method;
  private Map<Product, Integer> map;
  private UUID id;
  private OrderStatus status;
  
  public Order(Customer cust, PaymentMethod method){
    this.date = LocalDate.now();
    this.cust=cust;
    this.method=method;
    this.map=new HashMap<>();
    this.id=UUID.randomUUID();
    this.status=null;
  }

  @Override
  public int hashCode() {
    int hash = 3;
    hash = 17 * hash + Objects.hashCode(this.cust);
    hash = 17 * hash + Objects.hashCode(this.date);
    return hash;
  }

  @Override
  public boolean equals(Object obj) {
    if (this == obj) {
      return true;
    }
    if (obj == null) {
      return false;
    }
    if (getClass() != obj.getClass()) {
      return false;
    }
    final Order other = (Order) obj;
    if (!Objects.equals(this.cust, other.cust)) {
      return false;
    }
    return Objects.equals(this.date, other.date);
  }
  
  public OrderStatus getStatus(){
    return this.status;
  }
  
  public void setOrderStatus(OrderStatus status){
    if(!(this.status==OrderStatus.DELIVERED && status==OrderStatus.CANCELED)){
      this.status=status;
    }else{
      throw new IllegalArgumentException("You can't cancel a delivered order!");
    } 
  }

  public Customer getCust() {
    return cust;
  }

  public LocalDate getDate() {
    return date;
  }

  public PaymentMethod getMethod() {
    return method;
  }

  public Map<Product, Integer> getMap() {
    return map;
  }

  public UUID getId() {
    return id;
  }
  
  public void addProductToOrder(Product p, int qty){
    if(this.map.containsKey(p)){
      throw new IllegalArgumentException("Product Already Added to The Order");
    }else{
      if(p.isQtyAvailable(qty)){
        this.map.put(p, qty);
        p.decreaseProductQty(qty);
      }else{
        int x = p.whatMinQtyCanBeBought(qty);
        if(x!=0){
          this.map.put(p, x);
          p.decreaseProductQty(x);
        }else{
          throw new IllegalArgumentException("The product currently have 0 units!");
        }
      }
    }
  }
  
  public double getTotalPriceOfSingleProduct(Product p){
    return this.map.get(p)*p.getSellingPricePerunit();
  }
  
  public double getTotalPriceForOrder(){
    double price = 0;
    for(Product p:this.map.keySet()){
      price+=this.getTotalPriceOfSingleProduct(p);
    }
    return price;
  }
  
  public int getNumberOfOrderQty(){
    int qty=0;
    for(Product p:this.map.keySet()){
      qty+=this.map.get(p);
    }
    return qty;
  }
  
  @Override
  public String toString(){
    return "Order id: " + this.id + "\n" + 
            "Customer id: " + this.cust.getID() + "\n" + 
            "Customer name: " + this.cust.getName() + "\n" +
            "Date: " + this.date + "\n" + 
            "Order Satus: " + this.status;
  }
  
  @Override
  public int compareTo(Order order){
    return (int) (order.getTotalPriceForOrder() - this.getTotalPriceForOrder());
  }
}
