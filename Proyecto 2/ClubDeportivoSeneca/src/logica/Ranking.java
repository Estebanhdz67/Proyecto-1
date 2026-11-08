package logica;

import java.io.Serializable;

import java.sql.Date;

public class Ranking implements Serializable {
	private double puntajeELO;
	private Date fechaActualizacion;
	private Socio socio;
	private DisciplinaIndividual disciplina;

	public Ranking(double puntajeELO, Date fechaActualizacion, Socio socio, DisciplinaIndividual disciplina) {
		this.puntajeELO = puntajeELO;
		this.fechaActualizacion = fechaActualizacion;
		this.socio = socio;
		this.disciplina = disciplina;
	}

	public double getPuntajeELO() {
		return puntajeELO;
	}

	public Date getFechaActualizacion() {
		return fechaActualizacion;
	}

	public Socio getSocio() {
		return socio;
	}

	public DisciplinaIndividual getDisciplina() {
		return disciplina;
	}

	public void sumarPuntos(double puntos) {
		this.puntajeELO+=puntos;
		this.fechaActualizacion = new Date(System.currentTimeMillis());
	}

	public void restarPuntos(double puntos) {
		this.puntajeELO-=puntos;
		this.fechaActualizacion = new Date(System.currentTimeMillis());
	}

	@Override
	public String toString() {
		return this.socio.getNombre()+"\t"+this.disciplina.getNombre()+"\t"+this.puntajeELO;
	}
}
