/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package domain;

import java.time.Period;
import java.util.Objects;

/**
 *
 * @author lenovo
 */
public class Supplier {
  private final String name;
  private final String email;
  private final String phone;
  private final int minimumOrderQty;
  private final String address;
  private final Period leadTime;
  
  public Supplier(String name, String address, String email, String phone, int minOrderQty, Period leadtime){
    this.name=name;
    this.address=address;
    this.email=email;
    this.phone=phone;
    this.minimumOrderQty=minOrderQty;
    this.leadTime=leadtime;
  }
  
  public String getSuuplierName(){
    return this.name;
  }
  
  public String getSupplierAddress(){
    return this.address;
  }
  
   public String getEmail() {
    return email;
  }

  public String getPhone() {
    return phone;
  }

  public int getMinimumOrderQty() {
    return minimumOrderQty;
  }

  public Period getLeadTime() {
    return leadTime;
  }
  
  @Override
  public int hashCode() {
    int hash = 5;
    hash = 97 * hash + Objects.hashCode(this.name);
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
    final Supplier other = (Supplier) obj;
    return Objects.equals(this.name, other.name);
  }
  
}
