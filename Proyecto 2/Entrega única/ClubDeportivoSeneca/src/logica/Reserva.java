package logica;

import java.io.Serializable;

import java.sql.Date;
import java.sql.Time;

public class Reserva implements Serializable {
	private Date fecha;
	private Time horaInicio;
	private Time horaFin;
	private int cantidadJugadores;
	private String modalidad;
	private Socio socio;
	private Instalacion instalacion;
	private DisciplinaIndividual disciplina;
	private PartidoPractica partidoPractica;

	public Reserva(Date fecha, Time horaInicio, Time horaFin, int cantidadJugadores, String modalidad, Socio socio, Instalacion instalacion, DisciplinaIndividual disciplina) {
		this.fecha = fecha;
		this.horaInicio = horaInicio;
		this.horaFin = horaFin;
		this.cantidadJugadores = cantidadJugadores;
		this.modalidad = modalidad;
		this.socio = socio;
		this.instalacion = instalacion;
		this.disciplina = disciplina;
	}

	public Date getFecha() {
		return fecha;
	}

	public Time getHoraInicio() {
		return horaInicio;
	}

	public Time getHoraFin() {
		return horaFin;
	}

	public int getCantidadJugadores() {
		return cantidadJugadores;
	}

	public String getModalidad() {
		return modalidad;
	}

	public Socio getSocio() {
		return socio;
	}

	public Instalacion getInstalacion() {
		return instalacion;
	}

	public DisciplinaIndividual getDisciplina() {
		return disciplina;
	}

	public PartidoPractica getPartidoPractica() {
		return partidoPractica;
	}

	public void setPartidoPractica(PartidoPractica partidoPractica) {
		this.partidoPractica = partidoPractica;
	}

	public void validarReserva() throws Exception {
		if(this.horaInicio.compareTo(this.horaFin)>=0) {
			throw new Exception("Horario invalido");
		}
		if(this.cantidadJugadores==3) {
			throw new Exception("No se permiten 3 jugadores");
		}
		if(this.instalacion instanceof CanchaPadel && this.cantidadJugadores!=4) {
			throw new Exception("Padel requiere 4 jugadores");
		}
		if(!(this.cantidadJugadores==2||this.cantidadJugadores==4)) {
			throw new Exception("La cantidad debe ser 2 o 4");
		}
		if(this.cantidadJugadores>this.instalacion.getCapacidad()) {
			throw new Exception("Supera la capacidad de la instalacion");
		}
	}

	public boolean seCruza(Reserva otra) {
		if(!this.fecha.equals(otra.getFecha())) {
			return false;
		}
		return this.horaInicio.before(otra.getHoraFin())&&otra.getHoraInicio().before(this.horaFin);
	}
}
