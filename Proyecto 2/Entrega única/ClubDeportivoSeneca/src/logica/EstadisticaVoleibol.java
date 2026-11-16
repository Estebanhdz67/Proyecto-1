package logica;

import java.io.Serializable;

public class EstadisticaVoleibol extends EstadisticaConjunto implements Serializable {
	private int aces;
	private int bloqueos;

	public EstadisticaVoleibol(Socio socio, Partido partido, int aces, int bloqueos) {
		super(socio, partido);
		this.aces = aces;
		this.bloqueos = bloqueos;
	}

	public int getAces() {
		return aces;
	}

	public int getBloqueos() {
		return bloqueos;
	}
}
