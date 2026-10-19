package logica;

import java.io.Serializable;

public class Snack extends ProductoCafeteria implements Serializable {
	private String alergenos;

	public Snack(String nombre, double precio, String alergenos) {
		super(nombre, precio);
		this.alergenos = alergenos;
	}

	public String getAlergenos() {
		return alergenos;
	}
}
