/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Enum.java to edit this template
 */
package domain;

/**
 *
 * @author lenovo
 */
public enum StockLevel {
  LOW_STOCK("Low Stock"), ENOUGH_STOCK("Enough Stock");
  private final String name;
  
  StockLevel(String name){
    this.name=name;
  }
  
  @Override
  public String toString(){
    return this.name;
  }
}
