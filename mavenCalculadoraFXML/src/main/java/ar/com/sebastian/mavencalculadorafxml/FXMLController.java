package ar.com.sebastian.mavencalculadorafxml;

import ar.com.sebastian.mavencalculadorafxml.CalculateExpression.CalculateExpression;
import ar.com.sebastian.mavencalculadorafxml.entidad.PrintDisplay;
import ar.com.sebastian.mavencalculadorafxml.modelo.Display;
import ar.com.sebastian.mavencalculadorafxml.entidad.CalculateExpressionAdapter;

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
    private String number = "";
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
        display = new Display();
        
        pane.setOnKeyReleased(eventHandlerReleased);
        pane.setOnKeyTyped(eventHandlerTyped);
    
        buttonAC.setOnAction(eventHandleAC);
        buttonEqual.setOnAction(eventHandleEqual);
        buttonComma.setOnAction(eventHandleComma);
        buttonAdd.setOnAction(eventHandleOperator);
        buttonSubstract.setOnAction(eventHandleOperator);
        buttonMultiply.setOnAction(eventHandleOperator);
        buttonDivide.setOnAction(eventHandleOperator);
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
            number = "";
        }
    };

    private final EventHandler<ActionEvent> eventHandleEqual = new EventHandler<ActionEvent>() {
     
        @Override
        public void handle(ActionEvent event) {
            
            if(!display.isEmpty() & !display.isZero()){
                processExpression(display.getValue());
                buttonComma.setDisable(true);
                number = "";
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
                
                number = display.removeLast() + ((Button)event.getSource()).getText();
                display.add(number);
                printDisplay();
            }
            buttonComma.setDisable(true);
        }
    };
    
    private final EventHandler<ActionEvent> eventHandleOperator = new EventHandler<ActionEvent>() {
     
        @Override
        public void handle(ActionEvent event) {
            
            if(canBeUpdated()){
                display.add(((Button)event.getSource()).getText());
                printDisplay();
            }
            ((Button)event.getSource()).setDisable(true);
            buttonComma.setDisable(false);

            number = "";
        }
    };
    
    private boolean canBeUpdated() {
        return !buttonAdd.isDisabled() && !buttonSubstract.isDisable() &&
               !buttonDivide.isDisable() && !buttonMultiply.isDisable() && !display.isEmpty();
               
    }
    
    private final EventHandler<ActionEvent> eventHandleNegate = new EventHandler<ActionEvent>() {
     
        @Override
        public void handle(ActionEvent event) {
            
            if(!display.isEmpty() & !display.isZero()){
                
                String auxNumber;
                if(display.contains("(") | display.contains(")")){
            
                }else{

                    if(display.getLast().matches("([\\+|\\-|\\/|\\*])")){
                        auxNumber = display.get(display.size()-2);
                        display.set(display.size()-2,"-" + auxNumber);
                    }else{
                        auxNumber = display.removeLast();
                        display.addLast("-" + auxNumber);
                    }
                }
                printDisplay();
            }
        }
    };
    
    private final EventHandler<ActionEvent> eventHandleNumber = new EventHandler<ActionEvent>() {
     
        @Override
        public void handle(ActionEvent event) {
            
            if(buttonAdd.isDisable() | buttonSubstract.isDisable() | buttonDivide.isDisable() | buttonMultiply.isDisable()){
                number = ((Button)event.getSource()).getText();
                if(buttonComma.isDisable()){
                    number = display.removeLast() + ((Button)event.getSource()).getText();
                }
            }else{
                if(display.isEmpty()){
                    number = ((Button)event.getSource()).getText();
                }else{
                    number = display.removeLast() + ((Button)event.getSource()).getText();
                }
            }
            
            display.add(number);
            printDisplay();
            
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
    
    private void processExpression(String expression) {
        
        String newExpression = "";
        CalculateExpression calculateExpression = new CalculateExpression();
        double result = 0;
        
        newExpression = display.getValue(" ");
        
        System.out.println(newExpression);

        try {
            result = calculateExpression.calculate(expressionAdapter(newExpression));
            display.clearDisplay();
            
            if(result == 0){
                display.setValue(ZERO);
                printDisplay();
            }else{
                display.setValue(resultAdapter(Double.toString(result).replace(DOT, COMMA)));
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
        newExpression = newExpression.replace(COMMA, DOT);
        return newExpression;
    }

    @Override
    public String resultAdapter(String result) {
        
        int initialIndex;
        String resultAux;
        
        initialIndex = result.indexOf(COMMA);
        
        if(initialIndex != -1){
            
            resultAux = result.substring(initialIndex+1);
            
            if(Integer.parseInt(resultAux) != 0){
                return result;
           }else{
                return result.substring(0, initialIndex);
            }
            
        }else{
            return result;
        }
    }

    @Override
    public void printDisplay() {
        this.displayCalc.setText(display.getValue());
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