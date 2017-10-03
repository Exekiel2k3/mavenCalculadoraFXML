/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ar.com.sebastian.mavencalculadorafxml;

/**
 *
 * @author sscardig
 */
public class ValidatorNumpad {

    public ValidatorNumpad() {
    }
    
    public boolean isOperator(String operator) {
        String operatorRegex = "([\\+|\\-|\\/|\\*])";
        return operator.matches(operatorRegex);
    }
    
    public boolean isNumber(String number) {
        String numberRegex = "([0-9])";
        return number.matches(numberRegex);
    }
//    
//    public boolean isComma(String number) {
//        String numberRegex = "([,])";
//        return number.matches(numberRegex);
//    }
    
}
