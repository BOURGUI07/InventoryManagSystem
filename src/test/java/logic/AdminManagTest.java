/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/UnitTests/JUnit5TestClass.java to edit this template
 */
package logic;

import domain.Admin;
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
public class AdminManagTest {
  private Admin admin;
  private AdminManag manag;
  
  public AdminManagTest() {
  }
  
  @BeforeAll
  public static void setUpClass() {
  }
  
  @AfterAll
  public static void tearDownClass() {
  }
  
  @BeforeEach
  public void setUp() {
    this.manag = new AdminManag();
    this.manag.registerAdmin("name", "MARADONA86@regate", "younessbourgui07@gmail.com");
    this.admin = this.manag.getAdmins().get(0);
  }
  
  @Test
  void test1(){
    assertTrue(this.manag.getAdmins().contains(this.admin));
    assertTrue(this.manag.getPasswordList().contains("MARADONA86@regate"));
    assertTrue(this.manag.getUsernameList().contains("name"));
  }
  
  @Test
  void test2(){
    this.manag.rememberUser(admin);
    assertTrue(this.admin.getRememberStatus());
    assertTrue(this.manag.getRememberList().containsKey("name"));
    assertTrue(this.manag.getRememberList().containsValue(admin));
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
