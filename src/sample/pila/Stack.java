/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sample.pila;

public class Stack {

    private double pila[];
    int testa;

    public Stack(int dim) {
        pila = new double[dim];
    }

    public boolean isEmpty() {
        return testa == 0;
    }

    public boolean isFull() {
        return testa == pila.length;
    }

    public boolean push(double x) {
        boolean eseguito = !isFull();
        if (eseguito) {
            pila[testa] = x;
            testa++;
    }
    return eseguito;
}

    public double top() throws EmptyStackException {
        if (isEmpty()) {
            throw new EmptyStackException("Pila vuota");
        }
        return pila[testa - 1];
    }

    public double pop() throws EmptyStackException {
        if (isEmpty()) {
            throw new EmptyStackException("Pila vuota");
        }
        testa--;
        return pila[testa];
    }
}
