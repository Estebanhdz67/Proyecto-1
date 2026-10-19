package logica;

import java.io.Serializable;

public class EstadisticaBaloncesto extends EstadisticaConjunto implements Serializable {
	private int puntos;
	private int rebotes;

	public EstadisticaBaloncesto(Socio socio, Partido partido, int puntos, int rebotes) {
		super(socio, partido);
		this.puntos = puntos;
		this.rebotes = rebotes;
	}

	public int getPuntos() {
		return puntos;
	}

	public int getRebotes() {
		return rebotes;
	}
}
