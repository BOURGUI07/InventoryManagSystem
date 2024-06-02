/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package domain;

import java.util.Objects;

/**
 *
 * @author lenovo
 */
public class Admin {

  @Override
  public int hashCode() {
    int hash = 3;
    hash = 67 * hash + Objects.hashCode(this.userName);
    hash = 67 * hash + Objects.hashCode(this.password);
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
    final Admin other = (Admin) obj;
    if (!Objects.equals(this.userName, other.userName)) {
      return false;
    }
    return Objects.equals(this.password, other.password);
  }

  public String getUserName() {
    return userName;
  }

  public String getPassword() {
    return password;
  }

  public String getEmail() {
    return email;
  }
  
  public boolean getRememberStatus(){
    return this.toBeRemembered;
  }
  
  public void rememberAdmin(){
    this.toBeRemembered=true;
  }
  private String userName;
  private String password;
  private String email;
  private boolean toBeRemembered;
  
  public Admin(String name, String password, String email){
    this.email=email;
    this.password=password;
    this.userName=name;
    this.toBeRemembered=false;
  }
  
  
}
