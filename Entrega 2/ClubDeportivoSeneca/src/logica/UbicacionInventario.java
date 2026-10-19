package logica;

import java.io.Serializable;

public class UbicacionInventario implements Serializable {
	private String nombre;

	public UbicacionInventario(String nombre) {
		this.nombre = nombre;
	}

	public String getNombre() {
		return nombre;
	}

	@Override
	public String toString() {
		return this.nombre;
	}
}
