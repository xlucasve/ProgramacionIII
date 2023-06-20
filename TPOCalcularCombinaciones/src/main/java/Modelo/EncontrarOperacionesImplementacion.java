package Modelo;
import java.util.ArrayList;
import java.util.Objects;

public class EncontrarOperacionesImplementacion implements EncontrarOperacionesInterface {

    @Override
    /*
     * La salida es un vector con String porque se espera cada operaci�n encontrada que se pase a un string, "15+5/9*4"
     */
    public ArrayList<String> obtenerOperaciones(ArrayList<Integer> listaNumeros, ArrayList<Operadores> listaOperadores, int numerosUsar,
                                                int valorBuscado) {
        ArrayList<Combinacion> combinacionesObtenidas = new ArrayList<>();
        ArrayList<String> combinacionesObtenidasString = new ArrayList<>();

        if (numerosUsar <= listaNumeros.size()){
            if(listaOperadores.size()+1 == numerosUsar){
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
                combinacionesObtenidasString = pasarCombinacionAString(combinacionesObtenidas);

            }
            else{
                System.out.println("No hay suficientes operadores para comenzar");
            }
        }else{
            System.out.println("No hay suficientes numeros para comenzar");
        }
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
                } else {
                    //if (!realizarPoda(valorBuscado, ordenOperadores, listaNumeros, listaNumerosUsados, ordenNumeros, etapaNumeros)) {

                        for (int j = 0; j < listaNumeros.size(); j++) {
                        if (puedeUsarseNumeros(listaNumerosUsados, listaNumeros, etapaNumeros, j, ordenOperadores)) {
                            marcarUsadoNumeros(j, listaNumeros, listaNumerosUsados, ordenNumeros);
                            buscarCombinaciones(combinaciones, numerosUsar, valorBuscado, listaOperadores, listaOperadoresUsados,
                                    ordenOperadores, listaNumeros, listaNumerosUsados, ordenNumeros,
                                    etapaOperadores, etapaNumeros + 1);
                            desmarcarUsadoNumeros(j, listaNumerosUsados, ordenNumeros);
                        }
                    }
                /*}else {
                        return combinaciones;
                    }*/
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

    private boolean realizarPoda (int valorBuscado, ArrayList<Operadores> ordenOperadores,
                          ArrayList<Double> numeros, ArrayList<Integer> numerosUsados, ArrayList<Double> ordenNumeros, Integer etapaNum) {
        if (etapaNum > 0) {
            ArrayList<Operadores> operadoresAux = new ArrayList<>();
            ArrayList<Operadores> operadoresAux2 = new ArrayList<>();
            ArrayList<Operadores> operadoresAuxCalculo = new ArrayList<>();

            //Copiar operadores a array auxiliar para el calculo de la combinacion
            //Otra copia de operadores es para el calculo del mayor o minimo valor
            for (int i = 0; i < ordenOperadores.size(); i++) {
                if( i < etapaNum-1){
                    operadoresAuxCalculo.add(ordenOperadores.get(i));
                }
                operadoresAux.add(ordenOperadores.get(i));
                operadoresAux2.add(ordenOperadores.get(i));
            }
            ArrayList<Double> ordenNumerosAux = new ArrayList<>();
            ArrayList<Double> ordenNumerosAux2 = new ArrayList<>();

            for (int i = 0; i < ordenNumeros.size(); i++){
                ordenNumerosAux.add(ordenNumeros.get(i));
                ordenNumerosAux2.add(ordenNumeros.get(i));
            }

            //Copio los numeros que todavia puedo usar
            ArrayList<Double> numerosAux = new ArrayList<>();
            for (int i = 0; i < numeros.size(); i++) {
                if (numerosUsados.get(i) != 1) {
                    numerosAux.add(numeros.get(i));
                }
            }
            //Decido si tengo que maximizar o minimizar
            Double x = calcularCombinacion(operadoresAuxCalculo, ordenNumerosAux);

            if (x.intValue() < valorBuscado){
                return maximizarPoda(valorBuscado, operadoresAux2, ordenNumerosAux2, etapaNum, numerosAux);
            }
            else {
                return minimizarPoda(valorBuscado, operadoresAux2, ordenNumerosAux2, etapaNum, numerosAux);
            }

        }
        return false;
    }

    private boolean maximizarPoda(int valorBuscado, ArrayList<Operadores> operadores, ArrayList<Double> ordenNumeros, Integer etapaNumeros, ArrayList<Double> numerosUsar){
        mergeSortMaxAMin(numerosUsar, 0, numerosUsar.size()-1);
        //TODO: NUNCA DIVIDIR O MULTIPLICAR POR UN NUMERO NEGATIVO
        int ultimo = numerosUsar.size()-1;
        for (int i = etapaNumeros-1; i < operadores.size(); i++){
            switch (operadores.get(i)){
                case MULTI, SUMA -> {
                    ordenNumeros.add(numerosUsar.get(i));
                }
                case DIV, RESTA -> {
                    ordenNumeros.add(numerosUsar.get(ultimo));
                    ultimo--;
                }
            }
        }
        Double valorMaximo = calcularCombinacion(operadores, ordenNumeros);
        return (valorMaximo > valorBuscado);
    }

    private boolean minimizarPoda(int valorBuscado, ArrayList<Operadores> operadores, ArrayList<Double> ordenNumeros, Integer etapaNumeros, ArrayList<Double> numerosUsar){
        mergeSortMaxAMin(numerosUsar, 0, numerosUsar.size()-1);
        //TODO: NUNCA DIVIDIR O MULTIPLICAR POR UN NUMERO NEGATIVO
        int ultimo = numerosUsar.size()-1;
        for (int i = etapaNumeros-1; i < operadores.size(); i++){
            switch (operadores.get(i)){
                case DIV, RESTA -> {
                    ordenNumeros.add(numerosUsar.get(i));
                }
                case MULTI, SUMA -> {
                    ordenNumeros.add(numerosUsar.get(ultimo));
                    ultimo--;
                }
            }
        }
        Double valorMinimo = calcularCombinacion(operadores, ordenNumeros);
        return (valorMinimo < valorBuscado);
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
