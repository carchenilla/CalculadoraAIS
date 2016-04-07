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
        // 5 + 4 * 2
        operators = new Stack<>();
        operands = new Stack<>();
        
        boolean finished = false;
        while(!finished){
            int i = operation.length()-1;
            while(i>=0 && (operation.charAt(i)>='0' && operation.charAt(i)<='9' || operation.charAt(i)=='.')){
                i--;
            }
            if(i==-1){
                //Caso último número
                operands.push(Double.parseDouble(operation));
                finished = true;
            }if(operation.charAt(i)=='('){
            
            }else{
                operands.push(Double.parseDouble(operation.substring(i+1)));
                char op = operation.charAt(i);
                if(!operators.isEmpty()){
                    char stackOp = operators.peek();
                    switch(stackOp){
                        case '*': case '÷':
                            // 5 / 6
                            operators.pop();
                            double opA = operands.pop();
                            double opB = operands.pop();
                            double result = doOperation(opA,opB,stackOp);
                            operands.push(result);
                            break;
                        default:
                            break;
                    }
                }
                operators.push(op);
                operation = operation.substring(0,i);
            }
        }
        return operands.pop();
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
