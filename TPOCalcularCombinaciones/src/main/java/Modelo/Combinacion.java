package Modelo;

import java.util.ArrayList;

public class Combinacion {
    private ArrayList<Operadores> operadores;
    private ArrayList<Integer> numeros;

    public Combinacion(ArrayList<Operadores> operadores, ArrayList<Integer> numeros) {
        this.operadores = operadores;
        this.numeros = numeros;
    }

    public ArrayList<Operadores> getOperadores() {
        return operadores;
    }

    public ArrayList<Integer> getNumeros() {
        return numeros;
    }
}
