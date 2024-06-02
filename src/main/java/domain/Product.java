/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package domain;

import java.util.Objects;
import java.util.UUID;

/**
 *
 * @author lenovo
 */
public class Product implements Comparable<Product>{
  private final UUID id;
  private String name;
  private Warehouse warehouse;
  private int quantity;
  private int treeshold;
  private double sellingPricePerunit;
  private boolean isLowStock;
  private double costPerUnit;
  private Supplier supplier;
  private int sales;
  
  public Product(String name, Supplier supplier, Warehouse warehouse, int qty, int treeshold,  double price, double cost){
    if(treeshold>=qty){
      throw new IllegalArgumentException("the treeshold is greater than or equal the product quantity! Illogical!");
    }
    this.id = UUID.randomUUID();
    this.costPerUnit=cost;
    this.isLowStock=false;
    this.treeshold=treeshold;
    this.quantity=qty;
    this.sellingPricePerunit=price;
    this.supplier=supplier;
    this.warehouse=warehouse;
    this.name=name;
    this.sales=0;
  }
  
  public void increaseSales(int x){
    this.sales+=x;
  }
  
  public int getSales(){
    return this.sales;
  }

  public void setName(String name) {
    this.name = name;
  }

  public void setWarehouse(Warehouse warehouse) {
    this.warehouse = warehouse;
  }

  public void setTreeshold(int treeshold) {
    if(treeshold>=this.quantity){
      throw new IllegalArgumentException("Treeshold is greater than or equal the product quantity!");
    }
    this.treeshold = treeshold;
  }

  public void setSellingPricePerunit(double sellingPricePerunit) {
    this.sellingPricePerunit = sellingPricePerunit;
  }

  public void setCostPerUnit(double costPerUnit) {
    this.costPerUnit = costPerUnit;
  }

  public void setSupplier(Supplier supplier) {
    this.supplier = supplier;
  }

  public UUID getId() {
    return id;
  }

  public String getName() {
    return name;
  }

  public Warehouse getWarehouse() {
    return warehouse;
  }

  public int getQuantity() {
    return quantity;
  }

  public int getTreeshold() {
    return treeshold;
  }

  public double getSellingPricePerunit() {
    return sellingPricePerunit;
  }

  public boolean isLowStock() {
    return isLowStock;
  }

  public double getCostPerUnit() {
    return costPerUnit;
  }

  public Supplier getSupplier() {
    return supplier;
  }
 
  public void decreaseProductQty(int value){
    if(value<0){
      throw new IllegalArgumentException("You decrease the product qty by a negative number!");
    }
    if(this.quantity>=value){
      this.quantity-=value;
    }else{
      this.quantity=0;
    }
    if(this.quantity<=this.treeshold){
      this.isLowStock=true;
    }
  }
  
  public void increaseProductQty(int value){
    if(value<0){
      throw new IllegalArgumentException("You increased product qty by a negative number!");
    }
    this.quantity+=value;
  }
  
  public double getProfitPerUnit(){
    return this.sellingPricePerunit-this.costPerUnit;
  }
  
  public StockLevel getStockLevelStatus(){
    if(this.isLowStock){
      return StockLevel.LOW_STOCK;
    }else{
      return StockLevel.ENOUGH_STOCK;
    }
  }
  
  @Override
  public String toString(){
    return "\tProduct name: " + this.name + "\n" + 
            "\tProduct ID: " + this.id + "\n" + 
            "\tProduct Stock Level Status: " + this.getStockLevelStatus() + "\n"+
            "\tAvailable Quantity: " + this.quantity + " units" + "\n" + 
            "\tCost Per Unit: $" + this.costPerUnit + "\n"+
            "\tSelling Price Per Unit: $" + this.sellingPricePerunit + "\n"+
            "\tProfit Per Unit: $" + this.getProfitPerUnit() + "\n" + 
            "\tThe Product Was Supplied From: " + this.supplier.getSuuplierName() + "\n" +
            "\tThe Product Currently Stocked At Warehouse: " + this.warehouse.getName() + " Located in :" + this.warehouse.getAddress();
  }
  
  @Override
  public int hashCode() {
    int hash = 7;
    hash = 41 * hash + Objects.hashCode(this.id);
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
    final Product other = (Product) obj;
    return Objects.equals(this.name, other.getName());
  }
  
  public boolean isQtyAvailable(int qty){
    return this.quantity>=qty;
  }
  
  public int whatMinQtyCanBeBought(int qty){
    if(!this.isQtyAvailable(qty)){
      int diff = qty - this.quantity;
      return qty-diff;
    }
    return 0;
  }
  
  @Override
  public int compareTo(Product p){
    return p.getSales()-this.sales;
  }
}
