package logica;

import java.io.Serializable;

import java.sql.Date;
import java.sql.Time;
import java.util.ArrayList;

public class SesionEntrenamiento implements Serializable {
	private Date fecha;
	private Time hora;
	private int duracion;
	private Entrenador entrenador;
	private Instalacion instalacion;
	private Equipo equipo;
	private DisciplinaIndividual disciplina;
	private ArrayList<AsistenciaEntrenamiento> asistencias;

	public SesionEntrenamiento(Date fecha, Time hora, int duracion, Entrenador entrenador, Instalacion instalacion, Equipo equipo, DisciplinaIndividual disciplina) {
		this.fecha = fecha;
		this.hora = hora;
		this.duracion = duracion;
		this.entrenador = entrenador;
		this.instalacion = instalacion;
		this.equipo = equipo;
		this.disciplina = disciplina;
		this.asistencias = new ArrayList<AsistenciaEntrenamiento>();
	}

	public Date getFecha() {
		return fecha;
	}

	public Time getHora() {
		return hora;
	}

	public int getDuracion() {
		return duracion;
	}

	public Entrenador getEntrenador() {
		return entrenador;
	}

	public Instalacion getInstalacion() {
		return instalacion;
	}

	public Equipo getEquipo() {
		return equipo;
	}

	public DisciplinaIndividual getDisciplina() {
		return disciplina;
	}

	public ArrayList<AsistenciaEntrenamiento> getAsistencias() {
		return asistencias;
	}

	public void convocarSocio(Socio socio) {
		for(AsistenciaEntrenamiento asistenciaEntrenamiento : this.asistencias) {
			if(asistenciaEntrenamiento.getSocio().getIdSocio().equals(socio.getIdSocio())) {
				return;
			}
		}
		this.asistencias.add(new AsistenciaEntrenamiento(socio, this, "Pendiente"));
	}

	public AsistenciaEntrenamiento buscarAsistencia(String idSocio) throws Exception {
		for(AsistenciaEntrenamiento asistenciaEntrenamiento : this.asistencias) {
			if(asistenciaEntrenamiento.getSocio().getIdSocio().equals(idSocio)) {
				return asistenciaEntrenamiento;
			}
		}
		throw new Exception("El socio no esta convocado");
	}
}
