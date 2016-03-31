package sample;

import javafx.beans.binding.DoubleBinding;
import javafx.scene.control.TextField;
import sample.pila.EmptyStackException;
import sample.pila.Stack;

/**
 * Created by Giovanni on 26/03/2016.
 */
public class Calculator {

    private String exp;

    private Stack pilaNum;

    private TextField tfBar;

    public Calculator(String exp, TextField tfBar) throws Exception {
        this.exp = exp;
        this.tfBar = tfBar;
        pilaNum = new Stack(exp.length());
        exp = risolviEquazione(exp);
        tfBar.setText(exp);

    }

    private String risolviEquazione(String exp) throws Exception{
        exp = solveParentesi(exp);
        exp = solveComplexOp(exp);
        if(exp.equals("Espressione non valutabile")){
            return exp;
        } else {
             return resolveLowDifficultExp(exp);
        }
    }

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



    private String solveParentesi(String exp) throws Exception {
        if(exp.contains("(") && exp.contains(")")) {
            exp = exp.substring(0, exp.indexOf(")"))+exp.substring(exp.indexOf(")") + 1);
            exp = exp.substring(0, exp.indexOf("(")) + risolviEquazione(exp.substring(exp.indexOf("(") + 1));
        } else if (exp.contains("(") || exp.contains(")")){
            return "Espressione non valutabile";
        }
        return exp;
    }

    private String resolveLowDifficultExp(String exp){
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
                            exp = (prov * Integer.parseInt(numb)) + exp;
                            break;
                        case '/':
                            exp = (prov / Integer.parseInt(numb)) + exp ;
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

    private boolean isOperand(char a){
        return a == '*' || a == '/' || a == '+' || a == '-' ;
    }

    private boolean priorityOperand(char a){
        return a == '*' || a == '/';
    }

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

    private double factorial(double num){
        double res = num;
        while(num > 1){
            num = num - 1;
            res = res * num;
        }
        return res;
    }

    private String precNumb(String exp, int index){
        String numb = "";
        char a = exp.charAt(index - 1);
        while (index > 0 && isNumber(a)){
            numb = a + numb;
            index--;
        }
        return numb;
    }

    private boolean isNumber(char a){
        if(a == '.') return true;
        try{
            Integer.parseInt(String.valueOf(a));
            return true;
        }catch(Exception e){
            return false;
        }
    }

    private boolean containsComplex(String exp){
        return exp.contains("COS") || exp.contains("SIN") || exp.contains("TAN") || exp.contains("COTAN") || exp.contains("LN") || exp.contains("^") || exp.contains("!") ||
                exp.contains("e") || exp.contains("π") || exp.contains("√");
    }
}
