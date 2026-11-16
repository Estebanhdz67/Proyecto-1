package logica;

import java.io.Serializable;

import java.util.ArrayList;

public abstract class Empleado extends Usuario implements Comprador, ParticipanteTorneo, Serializable {
	private String idEmpleado;
	private String codigoDescuento;
	private ArrayList<Turno> turnos;
	private ArrayList<SolicitudCambioTurno> solicitudes;
	private ArrayList<SugerenciaProducto> sugerencias;

	public Empleado(String login, String password, String nombre, String idEmpleado, String codigoDescuento) {
		super(login, password, nombre);
		this.idEmpleado = idEmpleado;
		this.codigoDescuento = codigoDescuento;
		this.turnos = new ArrayList<Turno>();
		this.solicitudes = new ArrayList<SolicitudCambioTurno>();
		this.sugerencias = new ArrayList<SugerenciaProducto>();
	}

	public String getIdEmpleado() {
		return idEmpleado;
	}

	public String getCodigoDescuento() {
		return codigoDescuento;
	}

	public ArrayList<Turno> getTurnos() {
		return turnos;
	}

	public ArrayList<SolicitudCambioTurno> getSolicitudes() {
		return solicitudes;
	}

	public ArrayList<SugerenciaProducto> getSugerencias() {
		return sugerencias;
	}

	public void agregarTurno(Turno turno) {
		this.turnos.add(turno);
	}

	public void agregarSolicitud(SolicitudCambioTurno solicitud) {
		this.solicitudes.add(solicitud);
	}

	public void agregarSugerencia(SugerenciaProducto sugerencia) {
		this.sugerencias.add(sugerencia);
	}


	@Override
	public String getNombreParticipante() {
		return this.getNombre();
	}

	@Override
	public double getRankingTorneo() {
		return 0;
	}

	@Override
	public String getTipoParticipante() {
		return "Empleado";
	}

	public boolean estaDeTurno(java.sql.Date fecha) {
		String[] dias = {"Domingo", "Lunes", "Martes", "Miercoles", "Jueves", "Viernes", "Sabado"};
		String dia = dias[fecha.getDay()];
		for(Turno turno : this.turnos) {
			if(turno.getDiaSemana().equals(dia)) {
				return true;
			}
		}
		return false;
	}

	@Override
	public String toString() {
		return this.idEmpleado + "\t" + super.toString();
	}
}
