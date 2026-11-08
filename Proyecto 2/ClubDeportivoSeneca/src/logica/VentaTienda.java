package logica;

import java.io.Serializable;

import java.sql.Date;

public class VentaTienda extends Venta implements Serializable {
	private double iva;

	public VentaTienda(Date fecha, Comprador comprador) {
		super(fecha, comprador);
		this.iva = 0.19;
	}

	public double getIva() {
		return iva;
	}
}
