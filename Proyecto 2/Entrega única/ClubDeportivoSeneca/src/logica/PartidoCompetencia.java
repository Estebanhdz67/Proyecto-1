package logica;

import java.io.Serializable;

import java.sql.Date;

public class PartidoCompetencia extends Partido implements Serializable {
	private Equipo equipo;
	private Competencia competencia;
	private String institucionRival;
	private String escenario;

	public PartidoCompetencia(Date fecha, Equipo equipo, Competencia competencia, String rival, String escenario) {
		super(fecha);
		this.equipo = equipo;
		this.competencia = competencia;
		this.institucionRival = rival;
		this.escenario = escenario;
	}

	public Equipo getEquipo() {
		return equipo;
	}

	public Competencia getCompetencia() {
		return competencia;
	}

	public String getInstitucionRival() {
		return institucionRival;
	}

	public String getEscenario() {
		return escenario;
	}
}
