package logica;

import java.io.Serializable;

import java.sql.Date;

public class MovimientoPuntos implements Serializable {
	private Date fecha;
	private int cantidad;
	private String tipo;

	public MovimientoPuntos(Date fecha, int cantidad, String tipo) {
		this.fecha = fecha;
		this.cantidad = cantidad;
		this.tipo = tipo;
	}

	public Date getFecha() {
		return fecha;
	}

	public int getCantidad() {
		return cantidad;
	}

	public String getTipo() {
		return tipo;
	}
}
