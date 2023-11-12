package ar.edu.unlu.teocomp1.grupo3;

public class Token {
    private String lexema;
    private String token;
    private int linea;
    private int columna;
    private int inicio;
    private int tamanio;

    public Token(String token, String lexema, int linea, int columna, int inicio, int tamanio) {
        this(lexema, linea, columna, inicio, tamanio);
        this.token = token;
    }

    public Token(String lexema, int linea, int columna, int inicio, int tamanio) {
        this.lexema = lexema;
        this.linea = linea;
        this.columna = columna;
        this.inicio = inicio;
        this.tamanio = tamanio;
    }

    public String getLexema() {
        return lexema;
    }

    public void setLexema(String lexema) {
        this.lexema = lexema;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public int getLinea() {
        return linea;
    }

    public void setLinea(int linea) {
        this.linea = linea;
    }

    public int getColumna() {
        return columna;
    }

    public void setColumna(int columna) {
        this.columna = columna;
    }

    public int getInicio() {
        return inicio;
    }

    public void setInicio(int inicio) {
        this.inicio = inicio;
    }

    public int getTamanio() {
        return tamanio;
    }

    public void setTamanio(int tamanio) {
        this.tamanio = tamanio;
    }

    public boolean isError() {
        return this.token == null;
    }
}
