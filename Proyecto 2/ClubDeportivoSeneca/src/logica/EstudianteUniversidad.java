package logica;

import java.io.Serializable;

public class EstudianteUniversidad implements ParticipanteTorneo, Serializable {
	private String idEstudiante;
	private String nombre;
	private double rankingTorneo;

	public EstudianteUniversidad(String idEstudiante, String nombre, double rankingTorneo) {
		this.idEstudiante = idEstudiante;
		this.nombre = nombre;
		this.rankingTorneo = rankingTorneo;
	}

	public String getIdEstudiante() {
		return idEstudiante;
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
		return "Estudiante";
	}

}
