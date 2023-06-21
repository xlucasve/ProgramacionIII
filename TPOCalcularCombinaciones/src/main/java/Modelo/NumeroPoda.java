package Modelo;

public class NumeroPoda {
    private Integer numero;

    public NumeroPoda(Integer numero) {
        this.numero = numero;
    }

    public Integer getNumero() {
        return numero;
    }

    public void incrementar(){
        this.numero++;
    }
}
