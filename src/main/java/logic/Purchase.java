/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package logic;
import domain.*;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.*;

/**
 *
 * @author lenovo
 */
public class Purchase {

  private Supplier supplier;
  private LocalDate date;
  private UUID id;
  private Map<Product, Integer> map;
  private boolean isConfirmed;
  private PaymentMethod method;
  
  public Purchase(Supplier supp, PaymentMethod method){
    this.date=LocalDate.now();
    this.supplier=supp;
    this.id=UUID.randomUUID();
    this.map=new HashMap<>();
    this.isConfirmed=false;
    this.method=method;
  }
  
  public void addProductToPurchase(Product p , int qty){
    if(this.map.containsKey(p)){
      throw new IllegalArgumentException("The product is already added to the Purchase");
    }else{
      this.map.put(p, qty);
    }
  }
  
  public int getPurchaseTotalQty(){
    int qty=0;
    for(Product p:this.map.keySet()){
      qty+=this.map.get(p);
    }
    return qty;
  }
  
  public boolean isPurchaseValidForConfirmation(){
    return this.isPurchaseDateDue() && this.isPurchaseQtyEnough();
  }
  
  public void applyPurchaseChanges(){
    for(Product p:this.map.keySet()){
      p.increaseProductQty(this.map.get(p));
    }
  }
  
  public Supplier getSupplier() {
    return supplier;
  }

  public LocalDate getDate() {
    return date;
  }

  public UUID getId() {
    return id;
  }

  public Map<Product, Integer> getMap() {
    return map;
  }

  public boolean isConfirmed() {
    return isConfirmed;
  }

  public PaymentMethod getMethod() {
    return method;
  }
  
  public void confirmPurchase(){
    if(this.isPurchaseValidForConfirmation()){
      this.isConfirmed=true;
    }else if(!this.isPurchaseDateDue()){
      int daysDifference = (int) ChronoUnit.DAYS.between(this.date, LocalDate.now());
      throw new IllegalArgumentException("Due Date is " + daysDifference + " days away from today");
    }else if(!this.isPurchaseQtyEnough()){
      throw new IllegalArgumentException("The Quantity isn't enough to make the transaction");
    }
  }
  
  public boolean isPurchaseDateDue(){
    return LocalDate.now().equals(this.purchaseArrivalDate());
  }
  
  public boolean isPurchaseQtyEnough(){
    return this.getPurchaseTotalQty()>=this.supplier.getMinimumOrderQty();
  }
  
  public LocalDate purchaseArrivalDate(){
    return this.date.plus(this.supplier.getLeadTime());
  }

  @Override
  public int hashCode() {
    int hash = 3;
    hash = 31 * hash + Objects.hashCode(this.supplier);
    hash = 31 * hash + Objects.hashCode(this.date);
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
    final Purchase other = (Purchase) obj;
    if (!Objects.equals(this.supplier, other.supplier)) {
      return false;
    }
    return Objects.equals(this.date, other.date);
  }
  
  public String purchaseStatus(){
    if(this.isConfirmed){
      return "Confirmed";
    }else{
      return "Not yet Confirmed";
    }
  }
  
  @Override
  public String toString(){
    return "Purchase submitted on Date: " + this.date + " With ID: " + this.id + "\n" + 
            "Purchase current status: " + this.purchaseStatus() + "\n" + 
            "Purchase was purchased from Supplier: " + this.supplier.getSuuplierName() + "\n" + 
            "Purchase Expected Arrival Date: " + this.purchaseArrivalDate();
  }
}
