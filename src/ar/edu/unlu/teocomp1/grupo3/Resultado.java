package ar.edu.unlu.teocomp1.grupo3;

public class Resultado {

	private String lexema;
	private String token;
	private int linea;
	private int columna;
	private boolean error;
	private long inicio;
	private int largo;

	public Resultado(String lexema, String token, int linea, int columna, long inicio, int largo) {
		super();
		this.lexema = lexema;
		this.token = token;
		this.linea = linea;
		this.columna = columna;
		this.error = false;
		this.inicio = inicio;
		this.largo = largo;
	}

	public Resultado(String lexema, int linea, int columna, long inicio, int largo) {
		super();
		this.lexema = lexema;
		this.token = null;
		this.linea = linea;
		this.columna = columna;
		this.error = true;
		this.inicio = inicio;
		this.largo = largo;
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

	public boolean isError() {
		return error;
	}

	public void setError(boolean error) {
		this.error = error;
	}

	public long getInicio() {
		return inicio;
	}

	public void setInicio(long inicio) {
		this.inicio = inicio;
	}

	public int getLargo() {
		return largo;
	}

	public void setLargo(int largo) {
		this.largo = largo;
	}
}
