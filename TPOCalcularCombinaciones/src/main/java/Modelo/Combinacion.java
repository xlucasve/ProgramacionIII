package Modelo;

import java.util.ArrayList;

public class Combinacion {
    private ArrayList<Operadores> operadores;
    private ArrayList<Double> numeros;

    public Combinacion(ArrayList<Operadores> operadores, ArrayList<Double> numeros) {
        this.operadores = operadores;
        this.numeros = numeros;
    }

    public ArrayList<Operadores> getOperadores() {
        return operadores;
    }

    public ArrayList<Double> getNumeros() {
        return numeros;
    }
}
