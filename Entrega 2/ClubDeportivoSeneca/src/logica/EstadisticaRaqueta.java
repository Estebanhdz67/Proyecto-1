package logica;

import java.io.Serializable;

public abstract class EstadisticaRaqueta implements Serializable {
	private Socio socio;
	private PartidoPractica partido;

	public EstadisticaRaqueta(Socio socio, PartidoPractica partido) {
		this.socio = socio;
		this.partido = partido;
	}

	public Socio getSocio() {
		return socio;
	}

	public PartidoPractica getPartido() {
		return partido;
	}
}
