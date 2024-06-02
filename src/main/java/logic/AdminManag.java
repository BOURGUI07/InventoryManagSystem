/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package logic;

import domain.Admin;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 *
 * @author lenovo
 */
public class AdminManag {
  private List<Admin> admins;
  private Map<String, Admin> rememberList;
  private List<String> passwordList;
  private List<String> usernameList;
  private static final String EMAIL_PATTERN = "^[a-zA-Z0-9_+&*-]+(?:\\.[a-zA-Z0-9_+&*-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,7}$";
  
  public AdminManag(){
    this.admins=new ArrayList<>();
    this.passwordList=new ArrayList<>();
    this.usernameList=new ArrayList<>();
    this.rememberList=new HashMap<>();
  }
  
  public void registerAdmin(String name, String code, String email){
    if(this.isValidEmail(email) && this.isSecure(code)){
      Admin a = new Admin(name, code, email);
      this.admins.add(a);
      this.passwordList.add(code);
      this.usernameList.add(name);
    }
  }

  public List<Admin> getAdmins() {
    return admins;
  }

  public Map<String, Admin> getRememberList() {
    return rememberList;
  }

  public List<String> getPasswordList() {
    return passwordList;
  }

  public List<String> getUsernameList() {
    return usernameList;
  }

  public static String getEMAIL_PATTERN() {
    return EMAIL_PATTERN;
  }
  
  public boolean isAdminAmongRemembered(String userName){
    return this.rememberList.containsKey(userName);
  }
  
  public boolean isAdminRegistrated(String username, String password){
    return this.passwordList.contains(password) && this.usernameList.contains(username);
  }
  
  public void rememberUser(Admin a){
    this.rememberList.put(a.getUserName(), a);
    a.rememberAdmin();
  }
  
  public boolean isValidEmail(String email){
        Pattern pattern = Pattern.compile(EMAIL_PATTERN);
        Matcher matcher = pattern.matcher(email);
        return matcher.matches();
    }
  
  public boolean isSecure(String password) {
    if (password == null || password.isEmpty()) {
        return false;
    }
    String digitRegex = ".*\\d.*";
    String uppercaseRegex = ".*[A-Z].*";
    String lowercaseRegex = ".*[a-z].*";
    String specialCharRegex = ".*[@#$%^&+=].*";
    boolean hasDigit = password.matches(digitRegex);
    boolean hasUppercase = password.matches(uppercaseRegex);
    boolean hasLowercase = password.matches(lowercaseRegex);
    boolean hasSpecialChar = password.matches(specialCharRegex);
    boolean isLengthValid = password.length() >= 8;
    return hasDigit && hasUppercase && hasLowercase && hasSpecialChar && isLengthValid;
  }

}
