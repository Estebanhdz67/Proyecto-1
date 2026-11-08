package logica;

import java.io.Serializable;

public abstract class EstadisticaConjunto implements Serializable {
	private Socio socio;
	private Partido partido;

	public EstadisticaConjunto(Socio socio, Partido partido) {
		this.socio = socio;
		this.partido = partido;
	}

	public Socio getSocio() {
		return socio;
	}

	public Partido getPartido() {
		return partido;
	}
}
