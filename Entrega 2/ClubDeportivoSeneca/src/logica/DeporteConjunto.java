package logica;

import java.io.Serializable;

public abstract class DeporteConjunto implements Serializable {
	private String nombre;
	private int minimoJugadores;

	public DeporteConjunto(String nombre, int minimoJugadores) {
		this.nombre = nombre;
		this.minimoJugadores = minimoJugadores;
	}

	public String getNombre() {
		return nombre;
	}

	public int getMinimoJugadores() {
		return minimoJugadores;
	}

	@Override
	public String toString() {
		return this.nombre;
	}
}
