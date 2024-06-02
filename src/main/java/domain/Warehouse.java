/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package domain;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 *
 * @author lenovo
 */
public class Warehouse implements Comparable<Warehouse>{
  private final String name;
  private final String address;
  private final int capacity;
  private List<Product> list;
  private int qty;
  
  public Warehouse(String name, String address, int capacity){
    this.name=name;
    this.address=address;
    this.capacity=capacity;
    this.list=new ArrayList<>();
    this.qty=0;
  }
  
  public int getQty(){
    return this.qty;
  }
  
  public void addQty(int x){
    this.qty+=x;
  }
  
  public List<Product> getWarehouseProducts(){
    return this.list;
  }
  
  public void addProduct(Product p){
    if(!this.list.contains(p) && this.canAddProduct(p)){
      this.list.add(p);
      this.addQty(p.getQuantity());
    }
  }
  
  
  
  public boolean canAddProduct(Product p){
    return (this.qty + p.getQuantity())<=this.capacity;
  }
  
  @Override
  public int hashCode() {
    int hash = 7;
    hash = 29 * hash + Objects.hashCode(this.name);
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
    final Warehouse other = (Warehouse) obj;
    return Objects.equals(this.name, other.name);
  }
  
  @Override
  public int compareTo(Warehouse w){
    return w.getEmptySpace()-this.getEmptySpace();
  }

  public String getName() {
    return name;
  }

  public String getAddress() {
    return address;
  }

  public int getCapacity() {
    return capacity;
  }
  
  public int getEmptySpace(){
    return this.capacity-this.qty;
  }
  
}
