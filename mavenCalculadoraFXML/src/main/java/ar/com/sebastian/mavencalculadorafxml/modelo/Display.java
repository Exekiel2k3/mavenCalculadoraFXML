/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ar.com.sebastian.mavencalculadorafxml.modelo;

import java.util.LinkedList;


/**
 *
 * @author sscardig
 */
public class Display {
    
    private LinkedList<String> display;

    public Display() {
        this.display = new LinkedList<>();
    }

    public void clearDisplay(){
        this.display.clear();
        this.display.add("");
    }
    
    public void add(String value){
        this.display.add(value);
    }

    public String getValue() {
    
        if(this.display.isEmpty()){
            return "";
        }

        String strDisplay = "";
        for (String element : display) {
            strDisplay = strDisplay.concat(element);
        }
        return strDisplay;
    }

    public String getValue(String separator) {
   
        if(this.display.isEmpty()){
            return "";
        }

        String strDisplay = "";
        for (String element : display) {
            strDisplay = strDisplay.concat(element) + separator;
        }
        return strDisplay;
    }
    
    public void setValue(String value) {
        this.display.clear();
        this.display.add(value);
    }
    
    public boolean isEmpty(){
        return this.display.isEmpty();
    }
    
    public boolean isZero() {
        
        if(this.display.size() == 1){
            return this.display.get(0).equals("0");
        }
        return false;
    }
    
    public String removeLast() {
        return this.display.removeLast();
    }
    
    public String getLast() {
        return this.display.getLast();
    }

    public boolean contains(String element) {
        return this.display.contains(element);
    }

    public int size() {
        return this.display.size();
    }

    public String get(int index) {
        return this.display.get(index);
    }

    public void set(int index, String element) {
        this.display.set(index, element);
    }

    public void addLast(String element) {
        this.display.addLast(element);
    }
}
