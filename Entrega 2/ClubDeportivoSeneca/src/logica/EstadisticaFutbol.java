package logica;

import java.io.Serializable;

public class EstadisticaFutbol extends EstadisticaConjunto implements Serializable {
	private int goles;
	private int asistencias;
	private int tarjetasAmarillas;
	private int tarjetasRojas;

	public EstadisticaFutbol(Socio socio, Partido partido, int goles, int asistencias, int amarillas, int rojas) {
		super(socio, partido);
		this.goles = goles;
		this.asistencias = asistencias;
		this.tarjetasAmarillas = amarillas;
		this.tarjetasRojas = rojas;
	}

	public int getGoles() {
		return goles;
	}

	public int getAsistencias() {
		return asistencias;
	}

	public int getTarjetasAmarillas() {
		return tarjetasAmarillas;
	}

	public int getTarjetasRojas() {
		return tarjetasRojas;
	}
}
