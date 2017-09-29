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
import java.util.ArrayList;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.event.EventType;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.input.InputEvent;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javax.swing.event.DocumentEvent;

public class FXMLController implements Initializable, PrintDisplay {
    
    @FXML
    private Label displayCalc;
    @FXML
    AnchorPane pane;

    // Buttons
    @FXML
    private Button buttonAC;
    @FXML
    private Button buttonEqual;
    @FXML
    private Button buttonComma;
    @FXML
    private Button buttonAdd;
    @FXML
    private Button buttonSubstract;
    @FXML
    private Button buttonMultiply;
    @FXML
    private Button buttonDivide;
    @FXML
    private Button buttonNegate;
    @FXML
    private Button buttonCero;
    @FXML
    private Button buttonOne;
    @FXML
    private Button buttonTwo;
    @FXML
    private Button buttonThree;
    @FXML
    private Button buttonFour;
    @FXML
    private Button buttonFive;
    @FXML
    private Button buttonSix;
    @FXML
    private Button buttonSeven;
    @FXML
    private Button buttonEight;
    @FXML
    private Button buttonNine;
    
    private Key keyAC;
    private Key keyComma;
    private Key keyAdd;
    private Key keySubstract;
    private Key keyMultiply;
    private Key keyDivide;
   
    private Display display;
    
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
    
        buttonAC.setOnAction(eventHandleAC);
        buttonEqual.setOnAction(eventHandleEqual);
        buttonComma.setOnAction(eventHandleComma);
        buttonAdd.setOnAction(eventHandleAdd);
        buttonSubstract.setOnAction(eventHandleSubstract);
        buttonMultiply.setOnAction(eventHandleMultiply);
        buttonDivide.setOnAction(eventHandleDivide);
        buttonNegate.setOnAction(eventHandleNegate);
        buttonCero.setOnAction(eventHandleNumber);
        buttonOne.setOnAction(eventHandleNumber);
        buttonTwo.setOnAction(eventHandleNumber);
        buttonThree.setOnAction(eventHandleNumber);
        buttonFour.setOnAction(eventHandleNumber);
        buttonFive.setOnAction(eventHandleNumber);
        buttonSix.setOnAction(eventHandleNumber);
        buttonSeven.setOnAction(eventHandleNumber);
        buttonEight.setOnAction(eventHandleNumber);
        buttonNine.setOnAction(eventHandleNumber);
    }
    
    private final EventHandler<ActionEvent> eventHandleAC = new EventHandler<ActionEvent>() {
     
        @Override
        public void handle(ActionEvent event) {
            
            display.clearDisplay();
            printDisplay();
            
            keyComma.setActive(false);
            keyAC.setActive(true);
        }
    };

    private final EventHandler<ActionEvent> eventHandleEqual = new EventHandler<ActionEvent>() {
     
        @Override
        public void handle(ActionEvent event) {
            
            if(!display.isEmpty() & display.getValue()!="0"){
                processExpression(display.getValue());
                
                keyComma.setActive(true);
            }
        }
    };

    private final EventHandler<ActionEvent> eventHandleComma = new EventHandler<ActionEvent>() {
     
        @Override
        public void handle(ActionEvent event) {
            
            if(!keyComma.isActive()){
                
                if(display.isEmpty()){
                    display.setValue("0");
                    printDisplay();
                }
                
                updateAndPrintDisplay(event);
            }
        }
    };
    
    private final EventHandler<ActionEvent> eventHandleAdd = new EventHandler<ActionEvent>() {
     
        @Override
        public void handle(ActionEvent event) {
            
            if(canBeUpdated()){    
                updateAndPrintDisplay(event);
            }
            keyAdd.setActive(true);
            keyComma.setActive(false);
        }
    };
    
    private final EventHandler<ActionEvent> eventHandleSubstract = new EventHandler<ActionEvent>() {
     
        @Override
        public void handle(ActionEvent event) {
            
            if(canBeUpdated()){
                updateAndPrintDisplay(event);
            }
            keySubstract.setActive(true);
            keyComma.setActive(false);
        }
    };
    
    private final EventHandler<ActionEvent> eventHandleMultiply = new EventHandler<ActionEvent>() {
     
        @Override
        public void handle(ActionEvent event) {
            
            if(canBeUpdated()){
                updateAndPrintDisplay(event);
            }
            keyMultiply.setActive(true);
            keyComma.setActive(false);
        }
    };
    
    private final EventHandler<ActionEvent> eventHandleDivide = new EventHandler<ActionEvent>() {
     
        @Override
        public void handle(ActionEvent event) {
            
            if(canBeUpdated()){
                updateAndPrintDisplay(event);
            }
            keyDivide.setActive(true);
            keyComma.setActive(false);
        }
    };
    
    private final EventHandler<ActionEvent> eventHandleNegate = new EventHandler<ActionEvent>() {
     
        @Override
        public void handle(ActionEvent event) {
            
            if(!display.isEmpty() & display.getValue()!="0"){
                display.setValue(negate(display.getValue()));
                printDisplay();
            }
        }
    };
    
    private final EventHandler<ActionEvent> eventHandleNumber = new EventHandler<ActionEvent>() {
     
        @Override
        public void handle(ActionEvent event) {
            
            updateAndPrintDisplay(event);

            keyAdd.setActive(false);
            keySubstract.setActive(false);
            keyMultiply.setActive(false);
            keyDivide.setActive(false);
        }
    };
    
    private void updateAndPrintDisplay(ActionEvent event) {
        display.updateDisplay(((Button)event.getSource()).getText());
        printDisplay();
    }
    //Pata los botones AC(DELETE), =(ENTER)
    EventHandler<KeyEvent> eventHandlerReleased = new EventHandler<KeyEvent>() {
        
        @Override
        public void handle(KeyEvent event){
            switch (event.getCode()){
                case DELETE:
                    buttonAC.fire();
                    break;
                case ENTER:
                    buttonEqual.fire();
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
            
            switch(value){
             case ",":
                 buttonComma.fire();
                 break;
             case "+":
                 buttonAdd.fire();
                 break;
             case "-":
                 buttonSubstract.fire();
                 break;
             case "*":
                 buttonMultiply.fire();
                 break;
             case "/":
                 buttonDivide.fire();
                 break;
             case "+/-":
                 buttonNegate.fire();
                 break;
             case "%":
                 break;
             case "0":
                 buttonCero.fire();
                 break;    
             case "1":
                 buttonOne.fire();
                 break;
             case "2":
                 buttonTwo.fire();
                 break;
             case "3":
                 buttonThree.fire();
                 break;
             case "4":
                 buttonFour.fire();
                 break;
             case "5":
                 buttonFive.fire();
                 break;
             case "6":
                 buttonSix.fire();
                 break;
             case "7":
                 buttonSeven.fire();
                 break;
             case "8":
                 buttonEight.fire();
                 break;
             case "9":
                 buttonNine.fire();
                 break;    
             default:
                 break;
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
                printDisplay();
            }else{
                display.updateDisplay(Double.toString(result).replace(".", ","));
                printDisplay();
            }
        } catch (Exception ex) {
            Logger.getLogger(FXMLController.class.getName()).log(Level.SEVERE, null, ex);
            display.setValue("Error");
            printDisplay();
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
    public void printDisplay() {
        this.displayCalc.setText(display.getValue());
    }

    private String negate(String value) {
        
        String valueNegate = value;
        if(value.contains("(") | value.contains(")")){
            
        }else{
            String operatorRegex = "([\\+|\\-|\\/|\\*])";
            
            if(!value.contains("+") & !value.contains("-") & !value.contains("*") & !value.contains("/")){
                valueNegate = "-" + value;
            }else{
                int length = value.length();
                for(int index = length; index>0 ;index--){

                    String aux = value.substring(index-1, index);
                    if(aux.matches(operatorRegex)){
                        valueNegate = value.substring(0, index) + "-" + value.substring(index, length);
                        break;
                    }

                }
            }
        }
        return valueNegate;
    }
}