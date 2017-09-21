package ar.com.sebastian.mavencalculadorafxml;

import ar.com.sebastian.mathgametdd.modelo.Calculator;
import ar.com.sebastian.mathgametdd.modelo.CalculatorProxy;
import ar.com.sebastian.mathgametdd.modelo.ExpressionFixer;
import ar.com.sebastian.mathgametdd.modelo.MathLexer;
import ar.com.sebastian.mathgametdd.modelo.MathParser;
import ar.com.sebastian.mathgametdd.modelo.Precedence;
import ar.com.sebastian.mathgametdd.modelo.Resolver;
import ar.com.sebastian.mavencalculadorafxml.entidad.Key;
import ar.com.sebastian.mavencalculadorafxml.entidad.PrintDisplay;
import ar.com.sebastian.mavencalculadorafxml.modelo.Display;
import ar.com.sebastian.mavencalculadorafxml.modelo.KeyAC;
import ar.com.sebastian.mavencalculadorafxml.modelo.KeyAdd;
import ar.com.sebastian.mavencalculadorafxml.modelo.KeyComma;
import ar.com.sebastian.mavencalculadorafxml.modelo.KeyDivide;
import ar.com.sebastian.mavencalculadorafxml.modelo.KeyMultiply;
import ar.com.sebastian.mavencalculadorafxml.modelo.KeySubstract;

import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;

public class FXMLController implements Initializable, PrintDisplay {
    
    @FXML
    private Label displayCalc;
    @FXML
    AnchorPane pane;
    
    private Key keyAC;
    private Key keyComma;
    private Key keyAdd;
    private Key keySubstract;
    private Key keyMultiply;
    private Key keyDivide;
    private Display display;
    
    @FXML
    private void onActionNumpad(ActionEvent event){
        String value = ((Button)event.getSource()).getText();
        actionNumpad(value);
    }

    private void actionNumpad(String value) {
        switch(value){
            case "AC":
                keyComma.setActive(false);
                display.clearDisplay();
                printDisplay(display.getValue());
                keyAC.setActive(true);
                break;
            case ",":
                
                if(!keyComma.isActive()){
                    if(display.isEmpty()){
                        display.setValue("0");
                        printDisplay(display.getValue());
                    }
                    display.updateDisplay(value);
                    printDisplay(display.getValue());
                }
                keyComma.setActive(true);
                break;
            case "+":
                if(canBeUpdated()){    
                    display.updateDisplay(value);
                    printDisplay(display.getValue());
                }
                keyAdd.setActive(true);
                keyComma.setActive(false);
                break;
            case "-":
                if(canBeUpdated()){
                    display.updateDisplay(value);
                    printDisplay(display.getValue());
                }
                keySubstract.setActive(true);
                keyComma.setActive(false);
                break;
            case "*":
                if(canBeUpdated()){
                    display.updateDisplay(value);
                    printDisplay(display.getValue());
                }
                keyMultiply.setActive(true);
                keyComma.setActive(false);
                break;
            case "/":
                if(canBeUpdated()){
                    display.updateDisplay(value);
                    printDisplay(display.getValue());
                }
                keyDivide.setActive(true);
                keyComma.setActive(false);
                break;
            case "=":
                if(!display.isEmpty() & display.getValue()!="0"){
                    processExpression(display.getValue());
                    keyComma.setActive(true);
                }
                
                break;
            case "+/-":
                if(!display.isEmpty() & display.getValue()!="0"){
                    display.setValue(negate(display.getValue()));
                    printDisplay(display.getValue());
                }
                break;
            case "%":
                break;
            default:
                display.updateDisplay(value);
                printDisplay(display.getValue());
                
                keyAdd.setActive(false);
                keySubstract.setActive(false);
                keyMultiply.setActive(false);
                keyDivide.setActive(false);
                break;
        }        
    }

    private boolean canBeUpdated() {
        return !keyAdd.isActive() && !keySubstract.isActive() && !keyDivide.isActive() && !keyMultiply.isActive() && !display.isEmpty();
    }
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
        keyAC = new KeyAC();
        keyComma = new KeyComma();
        keyAdd = new KeyAdd();
        keySubstract = new KeySubstract();
        keyMultiply = new KeyMultiply();
        keyDivide = new KeyDivide();
        display = new Display();
        
        pane.setOnKeyReleased(eventHandlerReleased);
        pane.setOnKeyTyped(eventHandlerTyped);
    }    

    //Pata los botones AC(DELETE), =(ENTER)
    EventHandler<KeyEvent> eventHandlerReleased = new EventHandler<KeyEvent>() {
        
        @Override
        public void handle(KeyEvent event){
            switch (event.getCode()){
                case DELETE:
                    actionNumpad("AC");
                    break;
                case ENTER:
                    actionNumpad("=");
                    break;    
                default:
                    break;
            }
        }
    };
        
    //Para los numeros y operadores.
    EventHandler<KeyEvent> eventHandlerTyped = new EventHandler<KeyEvent>() {

        @Override
        public void handle(KeyEvent event){
           ValidatorNumpad validatorNumpad = new ValidatorNumpad();
           String value = event.getCharacter();
            
           if(validatorNumpad.isNumber(value) | validatorNumpad.isOperator(value) | validatorNumpad.isComma(value)){
               actionNumpad(value);
           }
        }
    };

    private void processExpression(String expression) {
        Precedence precedence = new Precedence();
        Calculator calculator = new Calculator();
        CalculatorProxy calculatorProxy = new CalculatorProxy(calculator);
        ExpressionFixer fixer = new ExpressionFixer();
        MathLexer lexer = new MathLexer(fixer);
        Resolver resolver = new Resolver(lexer, calculatorProxy, precedence);
        MathParser parser = new MathParser(lexer,resolver);
        
        double result = 0;
        
        adapter(expression);
        
        try {
            result = parser.processExpression(adapter(expression));
            display.clearDisplay();
            if(result == 0){
                display.setValue("0");
                printDisplay(display.getValue());
            }else{
                display.updateDisplay(Double.toString(result).replace(".", ","));
                printDisplay(display.getValue());
            }
        } catch (Exception ex) {
            Logger.getLogger(FXMLController.class.getName()).log(Level.SEVERE, null, ex);
            display.setValue("Error");
            printDisplay(display.getValue());
        }
    }
    
    private String adapter(String expression){
        
        String newExpression = "";
        newExpression = expression;
        newExpression = newExpression.replace("+", " + ");
        newExpression = newExpression.replace("*", " * ");
        newExpression = newExpression.replace("-", " - ");
        newExpression = newExpression.replace("/", " / ");
        newExpression = newExpression.replace(",", ".");
        return newExpression;
    }

    @Override
    public void printDisplay(String display) {
        this.displayCalc.setText(display);
    }

    private String negate(String value) {
        
        if(value.charAt(0)=='-'){
            value = value.substring(1);
        }else{
            value = "-" + value;
        }
        
        return value;
    }
}