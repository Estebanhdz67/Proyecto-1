package logica;

import java.io.Serializable;

import java.sql.Date;

public class Reabastecimiento implements Serializable {
	private Date fecha;
	private int cantidad;
	private ArticuloTienda articulo;
	private Administrador administrador;

	public Reabastecimiento(Date fecha, int cantidad, ArticuloTienda articulo, Administrador administrador) {
		this.fecha = fecha;
		this.cantidad = cantidad;
		this.articulo = articulo;
		this.administrador = administrador;
	}

	public Date getFecha() {
		return fecha;
	}

	public int getCantidad() {
		return cantidad;
	}
}
