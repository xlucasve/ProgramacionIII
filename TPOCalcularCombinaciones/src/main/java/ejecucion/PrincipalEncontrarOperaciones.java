package ejecucion;

import Modelo.EncontrarOperacionesImplementacion;
import Modelo.Operadores;

import java.util.ArrayList;

public class PrincipalEncontrarOperaciones {

    public static void main(String[] args) {
        // TODO Auto-generated method stub
        EncontrarOperacionesImplementacion operaciones = new EncontrarOperacionesImplementacion();
        ArrayList<Integer> numeros = new ArrayList<Integer>();
        numeros.add(4);
        numeros.add(2);
        numeros.add(3);
        numeros.add(8);
        numeros.add(-5);
        numeros.add(0);
        numeros.add(5);
        numeros.add(7);
        numeros.add(5);
        numeros.add(10);
        ArrayList<Operadores> operadores = new ArrayList<Operadores>();
        operadores.add(Operadores.SUMA);
        operadores.add(Operadores.MULTI);
        operadores.add(Operadores.DIV);

        int cantNum = 4;
        int resultadoABuscar = 16;
        ArrayList<String> resultado = operaciones.obtenerOperaciones(numeros, operadores, cantNum, resultadoABuscar);

        if (resultado != null) {
            int i = 0;
            System.out.println("Las operaciones resultantes que cumplan con " + resultadoABuscar + " son:");
            while (i < resultado.size()) {
                System.out.println(resultado.get(i));
                i++;
            }
        }
        else {
            System.out.println("El resultado arrojado es nulo");
        }
    }
}