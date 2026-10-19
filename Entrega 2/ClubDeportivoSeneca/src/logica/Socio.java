package logica;

import java.io.Serializable;

import java.sql.Date;
import java.util.ArrayList;

public class Socio extends Usuario implements Comprador, Serializable {
	private String idSocio;
	private Date fechaNacimiento;
	private ArrayList<InscripcionEquipo> inscripcionesEquipo;
	private ArrayList<InscripcionAcademia> inscripcionesAcademia;
	private ArrayList<Ranking> rankings;
	private ArrayList<Sancion> sanciones;
	private ArrayList<Mensualidad> mensualidades;
	private CuentaFidelidad cuentaFidelidad;
	private boolean entrenadorAsociado;

	public Socio(String login, String password, String nombre, String idSocio, Date fechaNacimiento) {
		super(login, password, nombre);
		this.idSocio = idSocio;
		this.fechaNacimiento = fechaNacimiento;
		this.inscripcionesEquipo = new ArrayList<InscripcionEquipo>();
		this.inscripcionesAcademia = new ArrayList<InscripcionAcademia>();
		this.rankings = new ArrayList<Ranking>();
		this.sanciones = new ArrayList<Sancion>();
		this.mensualidades = new ArrayList<Mensualidad>();
		this.cuentaFidelidad = new CuentaFidelidad();
		this.entrenadorAsociado = false;
	}

	public String getIdSocio() {
		return idSocio;
	}

	public Date getFechaNacimiento() {
		return fechaNacimiento;
	}

	public ArrayList<InscripcionEquipo> getInscripcionesEquipo() {
		return inscripcionesEquipo;
	}

	public ArrayList<InscripcionAcademia> getInscripcionesAcademia() {
		return inscripcionesAcademia;
	}

	public ArrayList<Ranking> getRankings() {
		return rankings;
	}

	public ArrayList<Sancion> getSanciones() {
		return sanciones;
	}

	public ArrayList<Mensualidad> getMensualidades() {
		return mensualidades;
	}

	public CuentaFidelidad getCuentaFidelidad() {
		return cuentaFidelidad;
	}

	public boolean isEntrenadorAsociado() {
		return this.entrenadorAsociado;
	}

	public void setEntrenadorAsociado(boolean entrenadorAsociado) {
		this.entrenadorAsociado = entrenadorAsociado;
	}

	public void agregarInscripcionEquipo(InscripcionEquipo inscripcion) {
		this.inscripcionesEquipo.add(inscripcion);
	}

	public void agregarInscripcionAcademia(InscripcionAcademia inscripcion) {
		this.inscripcionesAcademia.add(inscripcion);
	}

	public void agregarRanking(Ranking ranking) {
		this.rankings.add(ranking);
	}

	public void agregarSancion(Sancion sancion) {
		this.sanciones.add(sancion);
	}

	public void agregarMensualidad(Mensualidad mensualidad) {
		this.mensualidades.add(mensualidad);
	}

	public int calcularEdad(Date fecha) {
		int edad = fecha.getYear() - this.fechaNacimiento.getYear();
		if(fecha.getMonth() < this.fechaNacimiento.getMonth()) {
			edad--;
		} else if(fecha.getMonth() == this.fechaNacimiento.getMonth() && fecha.getDate() < this.fechaNacimiento.getDate()) {
			edad--;
		}
		return edad;
	}

	public boolean tieneSancionActiva() {
		for(Sancion sancion : this.sanciones) {
			if(sancion.isActiva()) {
				return true;
			}
		}
		return false;
	}

	public String consultarNivel(DisciplinaIndividual disciplina) {
		for(InscripcionAcademia inscripcion : this.inscripcionesAcademia) {
			if(inscripcion.getDisciplina().getNombre().equals(disciplina.getNombre()) && inscripcion.getEstado().equals("Activa")) {
				return inscripcion.getNivel();
			}
		}
		return null;
	}

	public Ranking consultarRanking(DisciplinaIndividual disciplina) {
		for(Ranking ranking : this.rankings) {
			if(ranking.getDisciplina().getNombre().equals(disciplina.getNombre())) {
				return ranking;
			}
		}
		return null;
	}

	@Override
	public String toString() {
		return this.idSocio + "\t" + super.toString();
	}
}
