package Modelo;
import java.util.ArrayList;

public class EncontrarOperacionesImplementacion implements EncontrarOperacionesInterface {

    @Override
    /*
     * La salida es un vector con String porque se espera cada operaci�n encontrada que se pase a un string, "15+5/9*4"
     */
    public ArrayList<String> obtenerOperaciones(ArrayList<Integer> listaNumeros, ArrayList<Operadores> listaOperadores, int numerosUsar,
                                                int valorBuscado) {
        ArrayList<Combinacion> combinacionesObtenidas = new ArrayList<>();
        if (numerosUsar <= listaNumeros.size()){
            if(listaOperadores.size()+1 <= listaNumeros.size()){
                ArrayList<Double> numerosDouble = new ArrayList<>();
                ArrayList<Integer> numerosUsados = new ArrayList<>();
                for (int i = 0; i < listaNumeros.size(); i++){
                    numerosDouble.add(Double.valueOf(listaNumeros.get(i)));
                    System.out.println(numerosDouble.get(i));
                }
                for (int j = 0; j < numerosUsar; j++){
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

                combinacionesObtenidas = buscarCombinaciones(numerosUsar, valorBuscado, listaOperadores, operadoresUsados, ordenOperadores,
                        numerosDouble, numerosUsados, ordenNumeros, etapaOperadores, etapaNumeros);
            }
        }
        ArrayList<String> combinacionesObtenidasString = pasarCombinacionAString();
        return null;
    }
    private ArrayList<Combinacion> buscarCombinaciones(Integer numerosUsar, Integer valorBuscado, ArrayList<Operadores> listaOperadores,
                                                       ArrayList<Integer> listaOperadoresUsados, ArrayList<Operadores> ordenOperadores,
                                                       ArrayList<Double> listaNumeros, ArrayList<Integer> listaNumerosUsados, ArrayList<Double> ordenNumeros,
                                                       Integer etapaOperadores, Integer etapaNumeros){
        return null;

    }

    private ArrayList<String> pasarCombinacionAString(){
        return null;
    }

}