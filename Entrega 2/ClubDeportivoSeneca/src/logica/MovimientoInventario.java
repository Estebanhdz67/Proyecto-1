package logica;

import java.io.Serializable;

import java.sql.Date;

public class MovimientoInventario implements Serializable {
	private Date fecha;
	private int cantidad;
	private ArticuloTienda articulo;
	private UbicacionInventario origen;
	private UbicacionInventario destino;
	private Administrador administrador;

	public MovimientoInventario(Date fecha, int cantidad, ArticuloTienda articulo, UbicacionInventario origen, UbicacionInventario destino, Administrador administrador) {
		this.fecha = fecha;
		this.cantidad = cantidad;
		this.articulo = articulo;
		this.origen = origen;
		this.destino = destino;
		this.administrador = administrador;
	}

	public Date getFecha() {
		return fecha;
	}

	public int getCantidad() {
		return cantidad;
	}
}
