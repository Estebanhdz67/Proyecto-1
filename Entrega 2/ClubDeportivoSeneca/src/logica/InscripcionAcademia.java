package logica;

import java.io.Serializable;

import java.sql.Date;

public class InscripcionAcademia extends Inscripcion implements Serializable {
	private Socio socio;
	private DisciplinaIndividual disciplina;
	private String nivel;

	public InscripcionAcademia(Date fecha, String estado, Socio socio, DisciplinaIndividual disciplina, String nivel) {
		super(fecha, estado);
		this.socio = socio;
		this.disciplina = disciplina;
		this.nivel = nivel;
	}

	public Socio getSocio() {
		return socio;
	}

	public DisciplinaIndividual getDisciplina() {
		return disciplina;
	}

	public String getNivel() {
		return nivel;
	}

	public void setNivel(String nivel) {
		this.nivel = nivel;
	}
}
