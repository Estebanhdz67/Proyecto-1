package logica;

import java.io.Serializable;

public class EstadisticaTenisPadel extends EstadisticaRaqueta implements Serializable {
	private int setsGanados;
	private int gamesGanados;
	private int aces;
	private int erroresNoForzados;

	public EstadisticaTenisPadel(Socio socio, PartidoPractica partidoPractica, int sets, int games, int aces, int errores) {
		super(socio, partidoPractica);
		this.setsGanados = sets;
		this.gamesGanados = games;
		this.aces = aces;
		this.erroresNoForzados = errores;
	}

	public int getSetsGanados() {
		return setsGanados;
	}

	public int getGamesGanados() {
		return gamesGanados;
	}

	public int getAces() {
		return aces;
	}

	public int getErroresNoForzados() {
		return erroresNoForzados;
	}
}
