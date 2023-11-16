package ar.edu.unlu.teocomp1.grupo3;

public class Regla {

    private String descripcion;

    public Regla(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getDescripcion() {
        return descripcion;
    }

	public boolean isError() {
		return descripcion != null && descripcion.startsWith("[ERROR]") ;
	}

}
