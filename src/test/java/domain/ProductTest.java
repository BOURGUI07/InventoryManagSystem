/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/UnitTests/JUnit5TestClass.java to edit this template
 */
package domain;

import java.time.Period;
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
public class ProductTest {
  private Supplier supp;
  private Warehouse wh;
  private Product p;
  
  public ProductTest() {
  }
  
  @BeforeAll
  public static void setUpClass() {
  }
  
  @AfterAll
  public static void tearDownClass() {
  }
  
  @BeforeEach
  public void setUp() {
    supp = new Supplier("name", "address", "email", "phone", 500, Period.ofDays(20));
    wh = new Warehouse("name", "address", 10000);
    p = new Product("name", supp, wh, 100, 25, 9.5, 6.5);
  }
  
  @Test
  void test1(){
    assertFalse(p.isLowStock());
  }
  
  @Test
  void test2(){
    p.decreaseProductQty(50);
    assertEquals(50, p.getQuantity());
    assertTrue(!p.isLowStock());
  }
  
  @Test
  void test3(){
    p.decreaseProductQty(75);
    assertTrue(p.getQuantity()==25);
    assertTrue(p.isLowStock());
  }
  
  @Test
  void test4(){
    p.increaseProductQty(40);
    assertTrue(p.getQuantity()==140);
  }
  
  @Test
  void test5(){
    p.decreaseProductQty(141);
    assertTrue(p.getQuantity()==0);
  }
  
  @Test
  void test6(){
    assertTrue(p.isQtyAvailable(14));
  }
  
  @Test
  void test7(){
    assertTrue(p.whatMinQtyCanBeBought(140)==100);
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
