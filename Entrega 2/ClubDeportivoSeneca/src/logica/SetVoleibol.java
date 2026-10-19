package logica;

import java.io.Serializable;

public class SetVoleibol implements Serializable {
	private int numero;
	private int puntosEquipo;
	private int puntosRival;

	public SetVoleibol(int numero, int puntosEquipo, int puntosRival) {
		this.numero = numero;
		this.puntosEquipo = puntosEquipo;
		this.puntosRival = puntosRival;
	}

	public int getNumero() {
		return numero;
	}

	public int getPuntosEquipo() {
		return puntosEquipo;
	}

	public int getPuntosRival() {
		return puntosRival;
	}
}
