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
        numeros.add(38);
        numeros.add(55);
        numeros.add(46);
        numeros.add(15);
        numeros.add(37);
        numeros.add(25);
        numeros.add(190);
        ArrayList<Operadores> operadores = new ArrayList<Operadores>();
        operadores.add(Operadores.SUMA);
        operadores.add(Operadores.MULTI);
        operadores.add(Operadores.RESTA);

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