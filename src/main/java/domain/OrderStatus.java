/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Enum.java to edit this template
 */
package domain;

/**
 *
 * @author lenovo
 */
public enum OrderStatus {
  PENDING("Pending"),
  PROCESSED("Processed"),
  SHIPPED("Shipped"),
  DELIVERED("Delivered"),
  CANCELED("Canceled");
  
  private final String name;
  
  OrderStatus(String name){
    this.name=name;
  }
  
  @Override
  public String toString(){
    return this.name;
  }
}
