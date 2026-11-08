package logica;

import java.io.Serializable;

public class EstadisticaTenisMesa extends EstadisticaRaqueta implements Serializable {
	private int setsGanados;
	private int puntosTotales;

	public EstadisticaTenisMesa(Socio socio, PartidoPractica partidoPractica, int sets, int puntos) {
		super(socio, partidoPractica);
		this.setsGanados = sets;
		this.puntosTotales = puntos;
	}

	public int getSetsGanados() {
		return setsGanados;
	}

	public int getPuntosTotales() {
		return puntosTotales;
	}
}
