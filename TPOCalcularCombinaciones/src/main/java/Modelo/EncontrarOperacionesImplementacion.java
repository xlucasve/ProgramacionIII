package Modelo;
import java.util.ArrayList;
import java.util.Objects;

public class EncontrarOperacionesImplementacion implements EncontrarOperacionesInterface {

    @Override
    /**
     * Funcion auxiliar que recibe listaNumeros, listaOperadores, numerosUsar y valorBuscado.
     * Valida la cantidad de numeros a usar para la cantidad de numeros proveida.
     * Valida la cantidad de operadores a usar para la cantidad de numeros proveida.
     * Finalmente invoca buscarCombinaciones con las estructuras necesarias inicializadas.
     * @param listaNumeros La lista de numeros que se pueden utilizar.
     * @param listaOperadores La lista de operadores a utilizar.
     * @param numerosUsar La cantidad de numeros maxima a usar.
     * @param valorBuscado El valor al cual la expresion matematica debe equivaler para formar parte de la solucion.
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

    /**
     * Dada una lista de combinaciones, muestra por pantalla los operadores y luego los numeros que la componen.
     * Finalmente devuelve una lista que contiene las mismas combinaciones representadas en cadena.
     * @param combinaciones La lista de combinaciones.
     * @return combinacionesString Lista de combinaciones representadas en cadena.
     */
    private ArrayList<String> pasarCombinacionAString(ArrayList<Combinacion> combinaciones){
        ArrayList<String> combinacionesString;
        combinacionesString = obtenerCadenasDeCombinaciones(combinaciones);
        return combinacionesString;

    }

    /**
     * Dada una lista de combinaciones, devuelve una lista de combinaciones representada en cadena para ser mostrada
     * en pantalla.
     * @param combinaciones La lista de combinaciones.
     * @return cadenasDeCombinaciones La lista de combinaciones en cadena.
     */
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

    /**
     * Funcion que devuelve todas las expresiones matematicas que al combinar los numeros y operadores dados
     * resultan igual a un valor buscado.
     * Inicialmente por cada etapa de operador, si no es la ultima,
     * @param combinaciones La lista de combinaciones actual que resultan igual al numero buscado.
     * @param numerosUsar La cantidad de numeros maxima a usar.
     * @param valorBuscado El valor al cual la expresion matematica debe equivaler para formar parte de la solucion.
     * @param listaOperadores La lista de operadores a utilizar.
     * @param listaOperadoresUsados La lista de operadores que ya fueron utilizados.
     * @param ordenOperadores Lista de operadores en el orden a utilizarse.
     * @param listaNumeros La lista de numeros que se pueden utilizar.
     * @param listaNumerosUsados La lista de numeros que ya fueron utilizados.
     * @param ordenNumeros Lista de numeros en el orden a utilizarse.
     * @param etapaOperadores La etapa actual (nivel) de operadores.
     * @param etapaNumeros La etapa actual (nivel) de numeros.
     * @return
     */
    private ArrayList<Combinacion> buscarCombinaciones(ArrayList<Combinacion> combinaciones, Integer numerosUsar, Integer valorBuscado, ArrayList<Operadores> listaOperadores,
                                                       ArrayList<Integer> listaOperadoresUsados, ArrayList<Operadores> ordenOperadores,
                                                       ArrayList<Double> listaNumeros, ArrayList<Integer> listaNumerosUsados, ArrayList<Double> ordenNumeros,
                                                       Integer etapaOperadores, Integer etapaNumeros) {


        if (etapaOperadores == listaOperadores.size()) {
            //if (!podar(valorBuscado, ordenOperadores, listaNumeros, ordenNumeros, etapaNumeros)) {
                if (Objects.equals(etapaNumeros, numerosUsar)) {
                    Double valorEncontrado = calcularCombinacion(ordenOperadores, ordenNumeros);
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
                    if (puedeUsarseNumeros(listaNumerosUsados, listaNumeros, etapaNumeros, j, ordenOperadores)) {
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

    /**
     * Dada una lista de enteros que representa si cada numero fue utilizado (0 = no usado, 1 = usado)
     * devuelve true si puede usarse, false si ya fue usado.
     * @param usados La lista de numeros ya utilizados
     * @param quieroUsar El indice del numero que quiero saber si puede ser utilizado.
     * @return boolean puedeUsarse
     */
    private boolean puedeUsarse(ArrayList<Integer> usados, int quieroUsar){
        return usados.get(quieroUsar) == 0;
    }

    private boolean puedeUsarseNumeros(ArrayList<Integer> usados, ArrayList<Double> numeros, int etapaNumeros,  int quieroUsar, ArrayList<Operadores> ordenOperadores){
        if (usados.get(quieroUsar) == 0){
            if (etapaNumeros == 0){
                return true;
            }
            else if(numeros.get(quieroUsar) == 0){ //PODA DE 0
                return ordenOperadores.get(etapaNumeros-1) != Operadores.DIV;
            }
            else{
                return true;
            }
        }
        return false;
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

    private Double calcularCombinacion(ArrayList<Operadores> operadores, ArrayList<Double> numeros){
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


    //TODO: REALIZAR ALGORITMO PODA
    //TODO: CASO RESULTADO ACTUAL DA MENOR A BUSCADO: MAXIMIZAR (ORDENAR NUMEROS)
    //TODO: CASO RESULTADO ACTUAL DA MAYOR A BUSCADO: MINIMIZAR (ORDENAR OPERADORES)
    private boolean podar(int valorBuscado, ArrayList<Operadores> ordenOperadores,
                          ArrayList<Double> numeros, ArrayList<Double> ordenNumeros, int etapaNum) {
        if (etapaNum > 0) {
            ArrayList<Operadores> operadoresAux = new ArrayList<>();
            for (int i = 0; i < ordenNumeros.size() - 1; i++) {
                operadoresAux.add(ordenOperadores.get(i));
            }
            Double x = calcularCombinacion(operadoresAux, ordenNumeros);
            if (x < valorBuscado){
                return  maximizarPoda(valorBuscado, ordenOperadores, ordenNumeros);
            }
            else{
                return minimizarPoda(valorBuscado, ordenOperadores, ordenNumeros);
            }
        }
        return false;
    }

    private boolean maximizarPoda(int valorBuscado, ArrayList<Operadores> operadores, ArrayList<Double> numeros){
        mergeSortMaxAMin(numeros, 0, numeros.size()-1);
        return false;
    }

    private boolean minimizarPoda(int valorBuscado, ArrayList<Operadores> operadores, ArrayList<Double> numeros){
        //TODO: ORDENAR OPERADORES (/ -  + *)
        mergeSortMaxAMin(numeros, 0, numeros.size()-1);
        return false;
    }

    public void mergeSortMaxAMin(ArrayList<Double> arrayToSort, int start, int end) {

        if (start < end && (end - start) >= 1) {
            int midElement = (end + start) / 2;

            mergeSortMaxAMin(arrayToSort, start, midElement);
            mergeSortMaxAMin(arrayToSort, midElement + 1, end);

            mergeElementsMaxAMin(arrayToSort, start, midElement, end);
        }
    }

    public void mergeElementsMaxAMin(ArrayList<Double> arrayToSort, int start, int mid, int end) {

        ArrayList<Double> tempArr = new ArrayList<>();

        int getLeftIndex = start;
        int getRightIndex = mid + 1;

        while (getLeftIndex <= mid && getRightIndex <= end) {

            if (arrayToSort.get(getLeftIndex) >= arrayToSort.get(getRightIndex)) {

                tempArr.add(arrayToSort.get(getLeftIndex));
                getLeftIndex++;

            } else {

                tempArr.add(arrayToSort.get(getRightIndex));
                getRightIndex++;

            }
        }

        while (getLeftIndex <= mid) {
            tempArr.add(arrayToSort.get(getLeftIndex));
            getLeftIndex++;
        }

        while (getRightIndex <= end) {
            tempArr.add(arrayToSort.get(getRightIndex));
            getRightIndex++;
        }


        for (int i = 0; i < tempArr.size(); start++) {
            arrayToSort.set(start, tempArr.get(i++));

        }

    }
}
