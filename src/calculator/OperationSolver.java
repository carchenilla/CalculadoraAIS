/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package calculator;

import java.util.Stack;

/**
 *
 * @author Andres
 */
public class OperationSolver {
    private static Stack<Character> operators;
    private static Stack<Double> operands;
    
    public static double solve(String operation){
        operators = new Stack<>();
        operands = new Stack<>();
        
        stackThings(operation);
        
        while(operands.size()>1){
            double opB = operands.pop();
            double opA = operands.pop();
            char operator = operators.pop();
            double result = doOperation(opA,opB,operator);
            operands.push(result);
        }
        return operands.pop();
    }
    
    private static void stackThings(String operation){
        boolean finished = false;
        while(!finished){
            int i = 0;
            while(i<operation.length() && (operation.charAt(i)>='0' && operation.charAt(i)<='9' || operation.charAt(i)=='.')){
                i++;
            }
            double num = Double.parseDouble(operation.substring(0, i));
            operands.push(num);
            try{
                char op = operation.charAt(i);
                operators.push(op);
            }catch(IndexOutOfBoundsException ex){
                //No hace falta recuperarse
            }
            if(i!=operation.length()){
                operation = operation.substring(i+1,operation.length());
            }else{
                finished = true;
            }
        }
    }
    
    private static double doOperation(double a, double b, char op){
        switch(op){
            case '+':
                return a+b;
            case '-':
                return a-b;
            case '*':
                return a*b;
            case '÷':
                return a/b;
            default:
                return 0;
        }
    }
    
    public static void main(String[] args){
        double a = OperationSolver.solve("3+6*4-6");
        System.out.println(a);
    }
}
