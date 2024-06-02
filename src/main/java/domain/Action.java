/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Enum.java to edit this template
 */
package domain;

/**
 *
 * @author lenovo
 */
public enum Action {
  ADD_PRODUCT("Added a product"),
  ADD_CUST("Added a Customer"),
  ADD_WH("Added a Warehouse"),
  ADD_SUPP("Added a Supplier"),
  PLACEORDER("Placed an Order"),
  CONFIRMPURCH("Confirmed a Purchase"),
  PLACEPURCH("Placed a Purchase"),
  PROCESSORDER("Processed an Order"),
  SHIPORDER("Shipper an Order"),
  DELIVERORDER("Delivered an Order"),
  CANCELORDER("Cancelled an Order");
  
  private final String name;
  Action(String name){
    this.name=name;
  }
  
  @Override
  public String toString(){
    return this.name;
  }
          
}
