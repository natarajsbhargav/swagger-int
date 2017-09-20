/**
 * 
 */
package com.bhargav.swagger.beans;

/**
 * @author nsrikantaiah
 *
 */
public class SystemProperty {
  
  private String variable;
  private String value;
  
  /**
   * Parameterized constructor
   * @param variable
   * @param value
   */
  public SystemProperty(String variable, String value) {
    this.variable = variable;
    this.value = value;
  }
  
  public String getVariable() {
    return variable;
  }
  
  public void setVariable(String variable) {
    this.variable = variable;
  }
  
  public String getValue() {
    return value;
  }

  public void setValue(String value) {
    this.value = value;
  }

}
