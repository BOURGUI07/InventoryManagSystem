/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package domain;

import java.util.*;
import java.util.Objects;
import java.util.UUID;
import logic.Order;

/**
 *
 * @author lenovo
 */
public class Customer implements Comparable<Customer>{
  private final String name;
  private final String address;
  private final String email;
  private final UUID id;
  private List<Order> orders;
  
  public Customer(String name, String address, String email){
    
    this.name=name;
    this.address=address;
    this.email=email;
    this.id=UUID.randomUUID();
    this.orders=new ArrayList<>();
  }
  
  public List<Order> getOrders(){
    return this.orders;
  }
  
  public void addOrder(Order order){
    if(order.getCust().equals(this)){
      this.orders.add(order);
    }else{
      throw new IllegalArgumentException("The order's customer doesn't equal to this customer");
    }
  }

  @Override
  public int hashCode() {
    int hash = 7;
    hash = 67 * hash + Objects.hashCode(this.name);
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
    final Customer other = (Customer) obj;
    return Objects.equals(this.name, other.name);
  }
  
  public UUID getID(){
    return this.id;
  }
  
  public String getName() {
    return name;
  }

  public String getAddress() {
    return address;
  }

  public String getEmail() {
    return email;
  }
  
  @Override
  public String toString(){
    return "Customer id: " + this.id + "\n" + 
            "Customer name: " + this.name + "\n" + 
            "Customer address: " + this.address + "\n" +
            "Customer email: " + this.email;
    
  }
  
  @Override
  public int compareTo(Customer c){
    return c.getOrders().size()-this.orders.size();
  }
}
