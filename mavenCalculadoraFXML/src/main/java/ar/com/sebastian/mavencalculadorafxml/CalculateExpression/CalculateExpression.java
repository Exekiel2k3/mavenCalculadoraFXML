/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ar.com.sebastian.mavencalculadorafxml.CalculateExpression;

import ar.com.sebastian.mathgametdd.modelo.Calculator;
import ar.com.sebastian.mathgametdd.modelo.CalculatorProxy;
import ar.com.sebastian.mathgametdd.modelo.ExpressionFixer;
import ar.com.sebastian.mathgametdd.modelo.MathLexer;
import ar.com.sebastian.mathgametdd.modelo.MathParser;
import ar.com.sebastian.mathgametdd.modelo.Precedence;
import ar.com.sebastian.mathgametdd.modelo.Resolver;

/**
 *
 * @author sscardig
 */
public class CalculateExpression {
    
    private Precedence precedence;
    private Calculator calculator;
    private CalculatorProxy calculatorProxy;
    private ExpressionFixer fixer;
    private MathLexer lexer;
    private Resolver resolver;
    private MathParser parser;

    public CalculateExpression() {
        this.precedence = new Precedence();
        this.calculator = new Calculator();
        this.calculatorProxy = new CalculatorProxy(calculator);
        this.fixer = new ExpressionFixer();
        this.lexer = new MathLexer(fixer);
        this.resolver = new Resolver(lexer, calculatorProxy, precedence);
        this.parser = new MathParser(lexer,resolver);
    }
    
    public double calculate(String expression) throws Exception{
        return parser.processExpression(expression);
    }
}
