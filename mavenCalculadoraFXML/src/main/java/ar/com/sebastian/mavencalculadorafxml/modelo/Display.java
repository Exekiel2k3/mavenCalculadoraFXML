/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ar.com.sebastian.mavencalculadorafxml.modelo;


/**
 *
 * @author sscardig
 */
public class Display {
    
    private String display;

    public Display() {
        this.clearDisplay();
    }

    public void clearDisplay(){
        this.display = "";
    }
    
    public void updateDisplay(String value){
        this.display = this.display + value;
    }

    public String getValue() {
        return display;
    }

    public void setValue(String display) {
        this.display = display;
    }
    
    public boolean isEmpty(){
        return this.display.isEmpty();
    }
    
    public boolean isZero(){
        return this.display.equals("0");
    }
}
