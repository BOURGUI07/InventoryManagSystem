/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Enum.java to edit this template
 */
package domain;

/**
 *
 * @author lenovo
 */
public enum PaymentMethod {
  CREDIT("Credit Card"), DEBIT("Debit Card"), PAYPAL("Paypal"), BANK_TRANSFER("Bank Transfer");
  
  private final String method;
  
  PaymentMethod(String method){
    this.method=method;
  }
  
  @Override
  public String toString(){
    return this.method;
  }
}
