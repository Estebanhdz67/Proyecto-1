package logica;

import java.io.Serializable;

import java.sql.Date;

public class SolicitudCambioTurno implements Serializable {
	private Date fechaSolicitud;
	private String estado;
	private Empleado empleado;
	private Turno turnoActual;
	private Turno turnoNuevo;
	private Administrador administrador;

	public SolicitudCambioTurno(Date fecha, Empleado empleado, Turno actual, Turno nuevo) {
		this.fechaSolicitud = fecha;
		this.empleado = empleado;
		this.turnoActual = actual;
		this.turnoNuevo = nuevo;
		this.estado = "Pendiente";
	}

	public String getEstado() {
		return estado;
	}

	public Empleado getEmpleado() {
		return empleado;
	}

	public Turno getTurnoActual() {
		return turnoActual;
	}

	public Turno getTurnoNuevo() {
		return turnoNuevo;
	}

	public void setEstado(String estado) {
		this.estado = estado;
	}

	public void setAdministrador(Administrador administrador) {
		this.administrador = administrador;
	}
}
