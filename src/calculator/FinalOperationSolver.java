/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package calculator;

import java.util.Stack;

/**
 *
 * @author Carche
 */
public class FinalOperationSolver {
    private Stack<Character> operators;
    private Stack<Double> operands;
    
    public FinalOperationSolver(){
        // Pilas de operandos y operadores
        operators = new Stack<>();
        operands = new Stack<>();
    }
    
    public double solve(String operation){
                
        //booleano de que ya hemos procesado toda la cadena de entrada
        boolean finished = false;
        while(!finished){//mientras que quede por leer
            int i = operation.length()-1;   //recorremos la cadena desde el final hacia el comienzo hasta encontrar algo que no es un nº o un pto o terminar
            while(i>=0 && (operation.charAt(i)>='0' && operation.charAt(i)<='9' || operation.charAt(i)=='.')){
                i--;
            }
            if(i==-1){  //si hemos acabado de leer, metemos el operando que quede y finished
                if (!operation.equals("")){
                    operands.push(Double.parseDouble(operation));
                }
                finished = true;
            //Si es un parentesis cerrado, recursivamente empezamos a resover lo que haya dentro
            }else if(operation.charAt(i)==')'){
                String newString = operation.substring(0,i);
                Double newNum = new FinalOperationSolver().solve(newString);
                int simbolPos = findLeftPar(i,operation)-1; //Una vez obtenido lo de dentro, buscamos el simbolo de operacion que precede a los parentesis 
                char newOp;
                operands.push(newNum);  //Si tira excepcion al coger el simbolo es que los parentesis es lo que hay más a la izquierda de toda la cadena de entrada
                try{
                    newOp = operation.charAt(simbolPos);
                    operators.push(newOp);
                    operation = operation.substring(0,simbolPos);   //recortamos la entrada quitando todo el parentesis que acabamos de procesar
                }
                catch (Exception ex){
                    operation = "";
                }
            //Si no es el final o el parentesis eso es que es o un parentesis abierto o una operacion
            }else{
                operands.push(Double.parseDouble(operation.substring(i+1)));
                char op = operation.charAt(i);
                //si es un parentesis abierto es que estamos en una llamada recursiva y tenemos que salir 
                if (op=='('){
                    //vaciamos las pilas haciendo operaciones y devolvemos el resultado
                    while (operands.size()>1){
                        double result = doOperation();
                        operands.push(result);
                    }
                    return operands.pop();
                }
                else{
                    //si es una operacion, echamos un ojo a la pila de operandos para ver si hay una multiplicación o division que tengan prioridad
                    if(!operators.isEmpty()){
                        char stackOp = operators.peek();
                        switch(stackOp){
                            case '*': case '÷':
                                // Si la hay la hacemos y luego metemos el resultado con el 2º operando
                                double result = doOperation();
                                operands.push(result);
                                break;
                            default:
                                break;
                        }
                    }
                }
                //metemos el nuevo operador y recortamos operation
                operators.push(op);
                operation = operation.substring(0,i);
            }
        }
        //Una vez procesada toda la cadena de entrada operamos hasta vaciar la pila
        while (operands.size()>1){
            double result = doOperation();
            operands.push(result);
        }
        //y devolvemos el resultado a la interfaz de la calculadora
        return operands.pop();
    }
    
    //Este metodo coge los operandos y el operador de las pilas y hace la operacion
    private double doOperation(){
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
    
    //Este metodo dado un indice y una cadena busca donde se abrió el parentesis que se cierra en la posicion dada
    private int findLeftPar(int beginPos, String s){
        int i = beginPos-1;
        int counter = 0; //contar si hay otros parentesis en medio
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
        double a = new FinalOperationSolver().solve("3+6*4-6");
        System.out.println(a);
    }
}
