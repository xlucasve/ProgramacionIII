package Modelo;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class EncontrarOperacionesImplementacion implements EncontrarOperacionesInterface {

    @Override
    /*
     * La salida es un vector con String porque se espera cada operaci�n encontrada que se pase a un string, "15+5/9*4"
     */
    public ArrayList<String> obtenerOperaciones(ArrayList<Integer> listaNumeros, ArrayList<Operadores> listaOperadores, int numerosUsar,
                                                int valorBuscado) {
        ArrayList<Combinacion> combinacionesObtenidas = new ArrayList<>();
        if (numerosUsar <= listaNumeros.size()){
            if(listaOperadores.size()+1 <= numerosUsar){
                ArrayList<Double> numerosDouble = new ArrayList<>();
                ArrayList<Integer> numerosUsados = new ArrayList<>();
                for (int i = 0; i < listaNumeros.size(); i++){
                    numerosDouble.add(Double.valueOf(listaNumeros.get(i)));
                }
                for (int j = 0; j < listaNumeros.size(); j++){
                    numerosUsados.add(0);
                }
                ArrayList<Double> ordenNumeros = new ArrayList<>();
                ArrayList<Integer> operadoresUsados = new ArrayList<>();
                for (int k = 0; k < listaOperadores.size(); k++){
                    operadoresUsados.add(0);
                }
                ArrayList<Operadores> ordenOperadores = new ArrayList<>();
                Integer etapaOperadores = 0;
                Integer etapaNumeros = 0;

                combinacionesObtenidas = buscarCombinaciones(combinacionesObtenidas, numerosUsar, valorBuscado, listaOperadores, operadoresUsados, ordenOperadores,
                        numerosDouble, numerosUsados, ordenNumeros, etapaOperadores, etapaNumeros);
            }
        }
        ArrayList<String> combinacionesObtenidasString = pasarCombinacionAString(combinacionesObtenidas);
        return combinacionesObtenidasString;
    }

    private ArrayList<String> pasarCombinacionAString(ArrayList<Combinacion> combinaciones){
        ArrayList<String> combinacionesString;
        combinacionesString = obtenerCadenasDeCombinaciones(combinaciones);
        return combinacionesString;

    }

    private ArrayList<String> obtenerCadenasDeCombinaciones(ArrayList<Combinacion> combinaciones) {
        ArrayList<String> cadenasDeCombinaciones = new ArrayList<>();
        for (Combinacion combinacion: combinaciones) {
            int i = 0;
            int numeroActual = 0;
            int operadorActual = 0;
            String cadenaOperacion = "";
            ArrayList<Double> numeros = combinacion.getNumeros();
            ArrayList<Operadores> operadores = combinacion.getOperadores();
            while (i < numeros.size() + operadores.size()) {
                if (i % 2 == 0) {
                    cadenaOperacion += numeros.get(numeroActual).intValue() + " ";
                    numeroActual += 1;
                } else {
                    cadenaOperacion += obtenerCaracter(String.valueOf(operadores.get(operadorActual))) + " ";
                    operadorActual += 1;
                }
                i += 1;
            }
            cadenasDeCombinaciones.add(cadenaOperacion);
        }
        return cadenasDeCombinaciones;
    }

    private String obtenerCaracter(String operador) {
        String caracter = "";
        switch(operador) {
            case "SUMA":
                caracter = "+";
                break;
            case "RESTA":
                caracter = "-";
                break;
            case "DIV":
                caracter = "/";
                break;
            case "MULTI":
                caracter = "*";
                break;
        }
        return caracter;
    }

    private ArrayList<Combinacion> buscarCombinaciones(ArrayList<Combinacion> combinaciones, Integer numerosUsar, Integer valorBuscado, ArrayList<Operadores> listaOperadores,
                                                       ArrayList<Integer> listaOperadoresUsados, ArrayList<Operadores> ordenOperadores,
                                                       ArrayList<Double> listaNumeros, ArrayList<Integer> listaNumerosUsados, ArrayList<Double> ordenNumeros,
                                                       Integer etapaOperadores, Integer etapaNumeros) {


        if (etapaOperadores == listaOperadores.size()) {
            //if (!podar(valorBuscado, listaOperadores, ordenOperadores, listaNumeros, ordenNumeros, etapaNumeros)) {
                if (Objects.equals(etapaNumeros, numerosUsar)) {
                    Double valorEncontrado = calcularCombinacion(valorBuscado, ordenOperadores, ordenNumeros);
                    if (valorEncontrado.intValue() == valorBuscado) {
                        ArrayList<Operadores> guardarOp = new ArrayList<>();
                        ArrayList<Double> guardarNum = new ArrayList<>();
                        for (int op = 0; op < ordenOperadores.size(); op++) {
                            guardarOp.add(ordenOperadores.get(op));
                        }
                        for (int num = 0; num < ordenNumeros.size(); num++) {
                            guardarNum.add(ordenNumeros.get(num));
                        }
                        Combinacion combinacion = new Combinacion(guardarOp, guardarNum);
                        combinaciones.add(combinacion);
                    }
               /* }
                //Camino de poda
                else {
                    return combinaciones;
                }*/
            } else {
                for (int j = 0; j < listaNumeros.size(); j++) {
                    if (puedeUsarse(listaNumerosUsados, j)) {
                        marcarUsadoNumeros(j, listaNumeros, listaNumerosUsados, ordenNumeros);
                        buscarCombinaciones(combinaciones, numerosUsar, valorBuscado, listaOperadores, listaOperadoresUsados,
                                ordenOperadores, listaNumeros, listaNumerosUsados, ordenNumeros,
                                etapaOperadores, etapaNumeros + 1);
                        desmarcarUsadoNumeros(j, listaNumerosUsados, ordenNumeros);
                    }
                }
            }
        }
        else{
            for (int x = 0; x < listaOperadores.size(); x++) {
                if (puedeUsarse(listaOperadoresUsados, x)) {
                    marcarUsadoOperadores(x, listaOperadores, listaOperadoresUsados, ordenOperadores);
                    buscarCombinaciones(combinaciones, numerosUsar, valorBuscado, listaOperadores, listaOperadoresUsados,
                            ordenOperadores, listaNumeros, listaNumerosUsados, ordenNumeros,
                            etapaOperadores + 1, etapaNumeros);
                    desmarcarUsadoOperadores(x, listaOperadoresUsados, ordenOperadores);
                }
            }
        }
        //Camino de no poda
        return combinaciones;

    }

    private boolean puedeUsarse(ArrayList<Integer> usados, int quieroUsar){
        return usados.get(quieroUsar) == 0;
    }

    private void marcarUsadoNumeros(Integer x, ArrayList<Double> listaNumeros, ArrayList<Integer> listaNumerosUsados,
                                    ArrayList<Double> ordenNumeros){
        Double numeroUsado = listaNumeros.get(x);
        listaNumerosUsados.set(x, 1);
        ordenNumeros.add(numeroUsado);

    }

    private void desmarcarUsadoNumeros(Integer x, ArrayList<Integer> listaNumerosUsados,
                                       ArrayList<Double> ordenNumeros){
        int indiceNumero = ordenNumeros.size()-1;
        listaNumerosUsados.set(x, 0);
        ordenNumeros.remove(indiceNumero);

    }

    private void marcarUsadoOperadores(Integer x, ArrayList<Operadores> listaOperadores, ArrayList<Integer> listaOperadoresUsados,
                                       ArrayList<Operadores> ordenOperadores){
        Operadores operadorUsado = listaOperadores.get(x);
        listaOperadoresUsados.set(x, 1);
        ordenOperadores.add(operadorUsado);
    }

    private void desmarcarUsadoOperadores(Integer x, ArrayList<Integer> listaOperadoresUsados,
                                          ArrayList<Operadores> ordenOperadores){
        int indiceOperador = ordenOperadores.size()-1;
        listaOperadoresUsados.set(x, 0);
        ordenOperadores.remove(indiceOperador);

    }

    private Double calcularCombinacion(int valorBuscado, ArrayList<Operadores> operadores, ArrayList<Double> numeros){
        ArrayList<Double> numerosAux = new ArrayList<>();
        for (int i = 0; i < numeros.size(); i++){
            numerosAux.add(Double.valueOf(numeros.get(i)));
        }

        ArrayList<Operadores> operadoresAux = new ArrayList<>();
        for (int k = 0; k < operadores.size(); k++) {
            operadoresAux.add(operadores.get(k));
        }

        int j = 0;
        int i = 0;

        while(i < 2){
            while (j < operadoresAux.size()){
                if (i == 0) {
                    if (operadoresAux.get(j) == Operadores.MULTI || operadoresAux.get(j) == Operadores.DIV) {
                        Double calculoAux = calculoSimple(operadoresAux.get(j), numerosAux.get(j), numerosAux.get(j + 1));
                        operadoresAux.remove(j);
                        numerosAux.set(j, calculoAux);
                        numerosAux.remove(j + 1);
                    }
                    else{
                        j++;
                    }
                }
                else{
                    Double calculoAux = calculoSimple(operadoresAux.get(j), numerosAux.get(j), numerosAux.get(j + 1));
                    operadoresAux.remove(j);
                    numerosAux.set(j, calculoAux);
                    numerosAux.remove(j + 1);
                }
            }
            j = 0;
            i++;
        }
        return numerosAux.get(0);
    }

    private Double calculoSimple(Operadores operador, Double numero1, Double numero2){
        switch (operador){
            case MULTI -> {
                return numero1 * numero2;
            }
            case DIV -> {
                return numero1 / numero2;
            }
            case SUMA -> {
                return numero1 + numero2;
            }
            default -> {
                return numero1 - numero2;
            }
        }
    }

/*    private boolean podar(int valorBuscado, ArrayList<Operadores> operadores, ArrayList<Operadores> ordenOperadores,
                          ArrayList<Double> numeros, ArrayList<Double> ordenNumeros, int etapaNum) {
        if (etapaNum > 0) {
            ArrayList<Operadores> operadoresAux = new ArrayList<>();
            for (int i = 0; i < ordenNumeros.size() - 1; i++) {
                operadoresAux.add(ordenOperadores.get(i));
            }
            Double x = calcularCombinacion(valorBuscado, operadoresAux, ordenNumeros);
            System.out.println("X : " + x);
            //Realizar calculo del valor Actual calcularCombinacion(op, num)
            return true;
        }
        return true;
    }*/
}