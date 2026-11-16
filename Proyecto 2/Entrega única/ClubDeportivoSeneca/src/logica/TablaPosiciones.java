package logica;

import java.io.Serializable;

public class TablaPosiciones implements Serializable {
	private InscripcionCompetencia inscripcion;
	private int puntos;
	private int posicion;

	public TablaPosiciones(InscripcionCompetencia inscripcion) {
		this.inscripcion = inscripcion;
		this.puntos = 0;
		this.posicion = 0;
	}

	public InscripcionCompetencia getInscripcion() {
		return inscripcion;
	}

	public int getPuntos() {
		return puntos;
	}

	public int getPosicion() {
		return posicion;
	}

	public void setPosicion(int posicion) {
		this.posicion = posicion;
	}

	public void sumarPuntos(int puntos) {
		this.puntos += puntos;
	}
}
