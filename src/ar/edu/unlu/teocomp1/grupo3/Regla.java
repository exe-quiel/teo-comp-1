package ar.edu.unlu.teocomp1.grupo3;

public class Regla {
    private String antecedente;
    private String numero;

    public Regla(String numero) {
        this.numero = numero;
    }

    public Regla(String numero, String antecedente) {
        this.numero = numero;
        this.antecedente = antecedente;
    }

    public String getAntecedente() {
        return antecedente;
    }

    public String getNumero() {
        return numero;
    }

}
