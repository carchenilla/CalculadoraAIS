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
public class CarcheOperationSolver {
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
                if (!operation.equals("")){
                    operands.push(Double.parseDouble(operation));
                }
                finished = true;
            }else if(operation.charAt(i)==')'){
                String newString = operation.substring(0,i);
                Double newNum = CarcheOperationSolver.solve(newString);
                int simbolPos = findLeftPar(i,operation)-1;
                char newOp;
                operands.push(newNum);
                try{
                    newOp = operation.charAt(simbolPos);
                    operators.push(newOp);
                    operation = operation.substring(0,simbolPos);
                }
                catch (Exception ex){
                    operation = "";
                }
            }else{
                operands.push(Double.parseDouble(operation.substring(i+1)));
                char op = operation.charAt(i);
                if (op=='('){
                    while (operands.size()>1){
                        double result = doOperation();
                        operands.push(result);
                    }
                    return operands.pop();
                }
                else{
                    if(!operators.isEmpty()){
                        char stackOp = operators.peek();
                        switch(stackOp){
                            case '*': case '÷':
                                // 5 / 6
                                double result = doOperation();
                                operands.push(result);
                                break;
                            default:
                                break;
                        }
                    }
                }
                operators.push(op);
                operation = operation.substring(0,i);
            }
        }
        while (operands.size()>1){
            double result = doOperation();
            operands.push(result);
        }
        return operands.pop();
    }
    
    private static double doOperation(){
        double a = operands.pop();
        double b = operands.pop();
        char op = operators.pop();
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
    
    private static int findLeftPar(int beginPos, String s){
        int i = beginPos-1;
        int counter = 0;
        while (i>=0){
            if (s.charAt(i)=='('){
                if (counter==0){
                    return i;
                }
                else{
                    counter--;
                }
            }
            else if (s.charAt(i)==')'){
                counter++;
            }
            i--;
        }
        return -1;
    }
    
    
    
    public static void main(String[] args){
        double a = CarcheOperationSolver.solve("3+6*4-6");
        System.out.println(a);
    }
}
