/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/UnitTests/JUnit5TestClass.java to edit this template
 */
package logic;

import domain.Customer;
import domain.PaymentMethod;
import domain.Product;
import java.time.LocalDate;
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
public class OrderTest {
  private Order o;
  private Product p;
  private Customer c;
  private PaymentMethod m;
  
  public OrderTest() {
  }
  
  @BeforeEach
  public void setUp() {
    this.m=PaymentMethod.PAYPAL;
    this.c=new Customer("", "","");
    this.p=new Product(null, null, null, 100, 25, 10.0, 5.0);
    this.o = new Order(c, m);
  }
  
  @Test
  void test1(){
    assertNull(o.getStatus());
    assertTrue(o.getDate().equals(LocalDate.now()));
    assertTrue(o.getMap().isEmpty());
    assertNotNull(o.getId());
  }
  
  @Test
  void test2(){
    o.addProductToOrder(p, 75);
    assertFalse(o.getMap().isEmpty());
    assertTrue(p.isLowStock());
    assertTrue(o.getNumberOfOrderQty()==75);
    assertTrue(o.getTotalPriceOfSingleProduct(p)==(750.0));
    assertTrue(o.getTotalPriceForOrder()==750.0);
  }
  
  @Test
  void test3(){
    o.addProductToOrder(p, 140);
    assertTrue(p.isLowStock());
    assertTrue(o.getNumberOfOrderQty()==100);
    assertTrue(o.getTotalPriceForOrder()==1000.0);
  }
  @BeforeAll
  public static void setUpClass() {
  }
  
  @AfterAll
  public static void tearDownClass() {
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
