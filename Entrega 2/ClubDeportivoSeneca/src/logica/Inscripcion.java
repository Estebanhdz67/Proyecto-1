package logica;

import java.io.Serializable;

import java.sql.Date;

public abstract class Inscripcion implements Serializable {
	private Date fecha;
	private String estado;

	public Inscripcion(Date fecha, String estado) {
		this.fecha = fecha;
		this.estado = estado;
	}

	public Date getFecha() {
		return fecha;
	}

	public String getEstado() {
		return estado;
	}

	public void setEstado(String estado) {
		this.estado = estado;
	}
}
