package ar.com.sebastian.mavencalculadorafxml;

import ar.com.sebastian.mavencalculadorafxml.CalculateExpression.CalculateExpression;
import ar.com.sebastian.mavencalculadorafxml.entidad.PrintDisplay;
import ar.com.sebastian.mavencalculadorafxml.modelo.Display;
import ar.com.sebastian.mavencalculadorafxml.entidad.CalculateExpressionAdapter;

import java.net.URL;
import java.util.LinkedList;
import java.util.Queue;
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

public class FXMLController implements Initializable, PrintDisplay, CalculateExpressionAdapter {
    
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
    
    private Display display;
    
    private boolean canBeUpdated() {
        return !buttonAdd.isDisabled() && !buttonSubstract.isDisable() &&
               !buttonDivide.isDisable() && !buttonMultiply.isDisable() && !display.isEmpty();
    }
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
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
            buttonComma.setDisable(false);
        }
    };

    private final EventHandler<ActionEvent> eventHandleEqual = new EventHandler<ActionEvent>() {
     
        @Override
        public void handle(ActionEvent event) {

            if(!display.isEmpty() & !display.isZero()){
                processExpression(display.getValue());
                buttonComma.setDisable(true);
            }
        }
    };

    private final EventHandler<ActionEvent> eventHandleComma = new EventHandler<ActionEvent>() {
     
        @Override
        public void handle(ActionEvent event) {
            
            if(!buttonComma.isDisable()){
                
                if(display.isEmpty()){
                    display.setValue(ZERO);
                    printDisplay();
                }
                updateAndPrintDisplay(event);
            }
            buttonComma.setDisable(true);
        }
    };
    
    private final EventHandler<ActionEvent> eventHandleAdd = new EventHandler<ActionEvent>() {
     
        @Override
        public void handle(ActionEvent event) {
            
            if(canBeUpdated()){    
                updateAndPrintDisplay(event);
            }
            
            buttonAdd.setDisable(true);
            buttonComma.setDisable(false);
        }
    };
    
    private final EventHandler<ActionEvent> eventHandleSubstract = new EventHandler<ActionEvent>() {
     
        @Override
        public void handle(ActionEvent event) {
            
            if(canBeUpdated()){
                updateAndPrintDisplay(event);
            }
            buttonSubstract.setDisable(true);
            buttonComma.setDisable(false);
        }
    };
    
    private final EventHandler<ActionEvent> eventHandleMultiply = new EventHandler<ActionEvent>() {
     
        @Override
        public void handle(ActionEvent event) {
            
            if(canBeUpdated()){
                updateAndPrintDisplay(event);
            }
            buttonMultiply.setDisable(true);
            buttonComma.setDisable(false);
        }
    };
    
    private final EventHandler<ActionEvent> eventHandleDivide = new EventHandler<ActionEvent>() {
     
        @Override
        public void handle(ActionEvent event) {
            
            if(canBeUpdated()){
                updateAndPrintDisplay(event);
            }
            buttonDivide.setDisable(true);
            buttonComma.setDisable(false);
        }
    };
    
    private final EventHandler<ActionEvent> eventHandleNegate = new EventHandler<ActionEvent>() {
     
        @Override
        public void handle(ActionEvent event) {
            
            if(!display.isEmpty() & !display.isZero()){
                display.setValue(negate(display.getValue()));
                printDisplay();
            }
        }
    };
    
    private final EventHandler<ActionEvent> eventHandleNumber = new EventHandler<ActionEvent>() {
     
        @Override
        public void handle(ActionEvent event) {
            
            updateAndPrintDisplay(event);

            buttonAdd.setDisable(false);
            buttonSubstract.setDisable(false);
            buttonMultiply.setDisable(false);
            buttonDivide.setDisable(false);
        }
    };
    
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
           String value = event.getCharacter();
           
           switch(value){
             case COMMA:
                 buttonComma.fire();
                 break;
             case PLUS:  
                 buttonAdd.fire();
                 break;
             case MINUS:
                 buttonSubstract.fire();
                 break;
             case MULTIPLY:
                 buttonMultiply.fire();
                 break;
             case DIVIDE:
                 buttonDivide.fire();
                 break;
             case NEGATE:
                 buttonNegate.fire();
                 break;
             case PERCENTAGE:
                 break;
             case ZERO:
                 buttonCero.fire();
                 break;    
             case ONE:
                 buttonOne.fire();
                 break;
             case TWO:
                 buttonTwo.fire();
                 break;
             case THREE:
                 buttonThree.fire();
                 break;
             case FOUR:
                 buttonFour.fire();
                 break;
             case FIVE:
                 buttonFive.fire();
                 break;
             case SIX:
                 buttonSix.fire();
                 break;
             case SEVEN:
                 buttonSeven.fire();
                 break;
             case EIGHT:
                 buttonEight.fire();
                 break;
             case NINE:
                 buttonNine.fire();
                 break;    
             default:
                 break;
           }
        }
        
    };
    
    private void updateAndPrintDisplay(ActionEvent event) {
        
        display.updateDisplay(((Button)event.getSource()).getText());
        printDisplay();
    }
    
    private void processExpression(String expression) {
        
        CalculateExpression calculateExpression = new CalculateExpression();
        double result = 0;

        try {
            result = calculateExpression.calculate(expressionAdapter(expression));
            display.clearDisplay();
            
            if(result == 0){
                display.setValue(ZERO);
                printDisplay();
            }else{
                display.updateDisplay(Double.toString(result).replace(DOT, COMMA));
                printDisplay();
            }
        } catch (Exception ex) {
            Logger.getLogger(FXMLController.class.getName()).log(Level.SEVERE, null, ex);
            display.setValue("Error");
            printDisplay();
        }
    }
        
    @Override
    public String expressionAdapter(String expression) {
        
        String newExpression = "";
        
        newExpression = expression;
        newExpression = newExpression.replace(PLUS, " " + PLUS + " ");
        newExpression = newExpression.replace(MULTIPLY, " " + MULTIPLY + " ");
        newExpression = newExpression.replace(MINUS, " " + MINUS + " ");
        newExpression = newExpression.replace(DIVIDE, " " + DIVIDE + " ");
        newExpression = newExpression.replace(COMMA, DOT);
        return newExpression;
    }

    @Override
    public String resultAdapter(String result) {
        return null;
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
            
            if(!value.contains(PLUS) & !value.contains(MINUS) & !value.contains(MULTIPLY) & !value.contains(DIVIDE)){
                valueNegate = MINUS + value;
            }else{
                int length = value.length();
                for(int index = length; index>0 ;index--){

                    String aux = value.substring(index-1, index);
                    if(aux.matches(operatorRegex)){
                        valueNegate = value.substring(0, index) + MINUS + value.substring(index, length);
                        break;
                    }
                }
            }
        }
        return valueNegate;
    }
    
    private static final String DOT = ".";
    private static final String DIVIDE = "/";
    private static final String MULTIPLY = "*";
    private static final String MINUS = "-";
    private static final String PLUS = "+";
    private static final String COMMA = ",";
    private static final String NINE = "9";
    private static final String EIGHT = "8";
    private static final String SEVEN = "7";
    private static final String SIX = "6";
    private static final String FIVE = "5";
    private static final String FOUR = "4";
    private static final String THREE = "3";
    private static final String TWO = "2";
    private static final String ONE = "1";
    private static final String ZERO = "0";
    private static final String PERCENTAGE = "%";
    private static final String NEGATE = "+/-";
}