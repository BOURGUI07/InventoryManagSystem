/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/UnitTests/JUnit5TestClass.java to edit this template
 */
package logic;

import domain.Customer;
import domain.OrderStatus;
import domain.PaymentMethod;
import domain.Product;
import domain.Supplier;
import domain.Warehouse;
import java.time.Period;
import java.util.HashMap;
import java.util.Map;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

/**
 *
 * @author lenovo
 */
public class InventoryTest {
  private Inventory inv;
  private Warehouse wh;
  private Product p;
  private Supplier s;
  private Customer c;
  
  public InventoryTest() {
  }
  
  @BeforeAll
  public static void setUpClass() {
  }
  
  @AfterAll
  public static void tearDownClass() {
  }
  
  @BeforeEach
  public void setUp() {
    this.inv = new Inventory();
    this.inv.addCustomer("Youness", "Marrakech", "email");
    this.inv.addSupplier("suppName", "address", "email", "phone", 100, Period.ofDays(5));
    this.inv.addWarehouse("name", "address", 150);
    this.c = this.inv.getCustomers().get(0);
    this.s = this.inv.getSuppliers().get(0);
    this.wh = this.inv.getWarehouses().get(0);
    this.inv.addProduct("product", s, wh, 50, 25, 5.0, 2.5);
    this.p  = this.inv.getProducts().get(0);
  }
  
  @Test
  void test1(){
    assertTrue(this.inv.getTrails().size()==4);
    assertFalse(this.inv.getPayMap().isEmpty());
  }
  
  @Test
  void test2(){
    assertTrue(this.wh.getWarehouseProducts().size()==1);
    assertTrue(this.wh.getQty()==50);
    assertTrue(this.wh.getEmptySpace()==100);
    assertTrue(this.inv.getWarehouseMap().get("name")==wh);
  }
  
  @Test
  void test3(){
    Map<Product, Integer> map = new HashMap<>();
    map.put(p, 10);
    this.inv.placeOrder(map, c.getID().toString(), PaymentMethod.DEBIT);
    Order o = this.inv.getOrders().get(0);
    assertTrue(o.getCust()==c);
    assertTrue(o.getMethod()==PaymentMethod.DEBIT);
    assertTrue(o.getNumberOfOrderQty()==10);
    assertTrue(o.getStatus()==OrderStatus.PENDING);
    assertTrue(this.inv.getTrails().size()==5);
    assertTrue(o.getTotalPriceForOrder()==50);
    assertTrue(p.getQuantity()==40);
  }
  
  @Test
  void test4(){
    Map<Product, Integer> map = new HashMap<>();
    map.put(p, 10);
    this.inv.placeOrder(map, c.getID().toString(), PaymentMethod.DEBIT);
    Order o = this.inv.getOrders().get(0);
    this.inv.processOrder(o.getId().toString());
    assertTrue(o.getStatus()==OrderStatus.PROCESSED);
    assertTrue(this.inv.getTrails().size()==6);
  }
  
  @Test
  void test5(){
    Map<Product, Integer> map = new HashMap<>();
    map.put(p, 10);
    this.inv.placeOrder(map, c.getID().toString(), PaymentMethod.DEBIT);
    Order o = this.inv.getOrders().get(0);
    this.inv.deliverOrder(o.getId().toString());
    assertTrue(o.getStatus()==OrderStatus.DELIVERED);
    assertTrue(this.inv.getTrails().size()==6);
    assertTrue(this.inv.getPayMap().get(PaymentMethod.DEBIT)==1);
    assertTrue(p.getSales()==10);
  }
  
  
  
  @AfterEach
  public void tearDown() {
  }

  // TODO add test methods here.
  // The methods must be annotated with annotation @Test. For example:
  //
  // @Test
  // public void hello() {}
}
