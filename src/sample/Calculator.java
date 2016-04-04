package sample;

import javafx.scene.control.TextField;
import sample.pila.EmptyStackException;
import sample.pila.Stack;

/**
 * Created by Giovanni on 26/03/2016.
 */
public class Calculator {

    private Stack pilaNum;

    public Calculator() throws Exception {
        pilaNum = new Stack(100);

    }


    public String execute(String exp){
        exp = solveParentesi(exp);
        exp = solveExpression(exp);
        return exp;
    }

    /**
     * Risolve l'espressione
     * @param exp espressione
     * @return
     * @throws Exception
     */
    private String solveExpression(String exp){
        exp = solveParentesi(exp);
        exp = solveComplexOp(exp);
        if(exp.equals("Espressione non valutabile")){
            return exp;
        } else {
             return solveLowDifficultExp(exp);
        }
    }

    /**
     * Risolve i calcoli più complessi dell'espressione passatagli
     * @param exp espressione
     * @return
     */
    private String solveComplexOp(String exp){
        while (containsComplex(exp)){
        if(exp.contains("COS")){
            String numb = nextNumb(exp.substring(exp.indexOf("COS") + 3, exp.length()))[1];
            double num = Double.parseDouble(numb);
            double toDeg = Math.cos(Math.toRadians(num));
            String toRep = Double.toString(toDeg);
            exp = exp.replace(("COS"+numb), toRep);
        }
        if(exp.contains("SIN")){
            String numb = nextNumb(exp.substring(exp.indexOf("SIN")+3, exp.length()))[1];
            double num = Double.parseDouble(numb);
            double toDeg = Math.sin(Math.toRadians(num));
            String toRep = Double.toString(toDeg);
            exp = exp.replace(("SIN"+numb), toRep);
        }
        if(exp.contains("TAN")){
            String numb = nextNumb(exp.substring(exp.indexOf("TAN")+3, exp.length()))[1];
            double num = Double.parseDouble(numb);
            double toDeg = Math.tan(Math.toRadians(num));
            String toRep = Double.toString(toDeg);
            exp = exp.replace(("TAN"+numb), toRep);
        }
        if(exp.contains("COTAN")){
            String numb = nextNumb(exp.substring(exp.indexOf("COTAN")+5, exp.length()))[1];
            double num = Double.parseDouble(numb);
            double toDeg = Math.cos(Math.toRadians(num))/Math.sin(Math.toRadians(num));
            String toRep = Double.toString(toDeg);
            exp = exp.replace(("COS"+numb), toRep);
        }
        if(exp.contains("LN")){
            String numb = nextNumb(exp.substring(exp.indexOf("LN")+2))[1];
            exp = exp.replace(("LN"+numb),String.valueOf(Math.log(Double.parseDouble(numb))));
        }
        if(exp.contains("^")){
            double pow = Double.parseDouble(nextNumb(exp.substring(exp.indexOf("^")+1))[1]);
            double numb = Double.parseDouble(precNumb(exp, exp.indexOf("^")));
            String value = String.valueOf(Math.pow(numb, pow));
            String toRep = (int)numb + "^" + (int)pow;
            exp = exp.replace(toRep, value);
        }
        if(exp.contains("!")){
            double numb = Double.parseDouble(precNumb(exp, exp.indexOf("!")));
            String value = String.valueOf(factorial(numb));
            String toRep = (int)numb + "!";
            exp = exp.replace(toRep, value);
        } if(exp.contains("√")){
            String numb = nextNumb(exp.substring(exp.indexOf("√")+1))[1];
            exp = exp.replace(("√"+numb),String.valueOf(Math.sqrt(Double.parseDouble(numb))));
        } if(exp.contains("π")){
            double pi = Math.PI;
            exp = exp.replace("π", String.valueOf(pi));
        } if(exp.contains("e")){
                double e = Math.E;
                exp = exp.replace("e", String.valueOf(e));
        }}
        return exp;
    }


    /**
     * Avvia la risoluzione di ogni parentesi
     * @param exp espressione
     * @return
     * @throws Exception
     */
    private String solveParentesi(String exp){
        int open;
        int close;
        if(!exp.contains("(") && !exp.contains(")")) return exp;
        else while (exp.contains("(") && exp.contains(")")){
            open = -1;
            close = -1;

            for (int i = 0; i < exp.length() && close == -1; i++){
                if(exp.charAt(i) == '('){
                    open = i;
                } else if (exp.charAt(i) == ')'){
                    close = i;
                }
            }

            if(open == -1 || close == -1) return "Espressione non valutabile";
            else exp = exp.substring(0, open) + solveExpression(exp.substring(open+1, close)) + exp.substring(close+1);
        }
        return exp;
    }

    /**
     * Risolve l'espressione a basso livello, cioè solo con le operazioni base
     * @param exp espressione
     * @return
     */
    private String solveLowDifficultExp(String exp){
        while (exp != "") {
            try{
                String[] info = nextNumb(exp);
                exp = info[0];
                String numb = info[1];
                pilaNum.push(Double.parseDouble(numb));
                char op = exp.charAt(0);
                if (priorityOperand(op)) {
                    exp = exp.substring(1);
                    double prov = pilaNum.pop();
                    info = nextNumb(exp);
                    exp = info[0];
                    numb = info[1];
                    switch (op) {
                        case '*':
                            exp = (prov * Double.parseDouble(numb)) + exp;
                            break;
                        case '/':
                            exp = (prov / Double.parseDouble(numb)) + exp ;
                            break;
                    }
                }}catch (Exception e){}
        }

        double num = 0;
        while (!pilaNum.isEmpty()){
            try {
                num = num + pilaNum.pop();
            } catch (EmptyStackException e) {
                e.printStackTrace();
            }
        }
        exp = String.valueOf(num);
        return exp;
    }

    /**
     * Ritorna true se è un operando
     * @param a
     * @return
     */
    private boolean isOperand(char a){
        return a == '*' || a == '/' || a == '+' || a == '-' ;
    }

    /**
     * Ritorna true se la priorità dell'operando è maggiore
     * @param a
     * @return
     */
    private boolean priorityOperand(char a){
        return a == '*' || a == '/';
    }

    /**
     * Ritorna un array contenente in prima posizione l'espressione senza il primo numero
     * in seconda posizione il numero scelto, il numero può avere valori positivi o negativi
     * @param exp
     * @return
     */
    private String[] nextNumb(String exp) {
        String[] prov = new String[2];
        String numb = "";
        for (int i = 0; i < exp.length(); i++) {
            if (isOperand(exp.charAt(i)) && i != 0) {
                prov[0] = exp.substring(i);
                prov[1] = numb;
                return prov;
            } else {
                numb = numb + exp.charAt(i);
            }
        }
        prov[0] = "";
        prov[1] = numb;
        return prov;
    }

    /**
     * Risolve il fattoriale del numero
     * @param num
     * @return
     */
    private double factorial(double num){
        double res = num;
        while(num > 1){
            num = num - 1;
            res = res * num;
        }
        return res;
    }

    /**
     * Restituisce il numero precedente a partire dall'index
     * @param exp espressione
     * @param index posizione
     * @return
     */
    private String precNumb(String exp, int index){
        String numb = "";
        char a = exp.charAt(index - 1);
        while (index > 0 && isNumber(a)){
            numb = a + numb;
            index--;
        }
        return numb;
    }

    /**
     * Restituisce true se il carattere è un numero
     * @param a
     * @return
     */
    private boolean isNumber(char a){
        if(a == '.') return true;
        try{
            Integer.parseInt(String.valueOf(a));
            return true;
        }catch(Exception e){
            return false;
        }
    }

    /**
     * Restituisce true se l'espressione contiene operazioni complesse
     * @param exp espressione
     * @return
     */
    private boolean containsComplex(String exp){
        return exp.contains("COS") || exp.contains("SIN") || exp.contains("TAN") || exp.contains("COTAN") || exp.contains("LN") || exp.contains("^") || exp.contains("!") ||
                exp.contains("e") || exp.contains("π") || exp.contains("√");
    }
}
