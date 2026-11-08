package logica;

import java.io.Serializable;

public class ParticipanteExterno implements ParticipanteTorneo, Serializable {
	private String identificacion;
	private String nombre;
	private double rankingTorneo;

	public ParticipanteExterno(String identificacion, String nombre, double rankingTorneo) {
		this.identificacion = identificacion;
		this.nombre = nombre;
		this.rankingTorneo = rankingTorneo;
	}

	public String getIdentificacion() {
		return identificacion;
	}

	public String getNombre() {
		return nombre;
	}

	@Override
	public String getNombreParticipante() {
		return this.nombre;
	}

	@Override
	public double getRankingTorneo() {
		return this.rankingTorneo;
	}
	@Override
	public String getTipoParticipante() {
		return "Externo";
	}

}
