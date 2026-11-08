package logica;

import java.io.Serializable;

import java.sql.Date;

public class VentaCafeteria extends Venta implements Serializable {
	private double impuestoConsumo;
	private double propina;

	public VentaCafeteria(Date fecha, Comprador comprador, double propina) {
		super(fecha, comprador);
		this.impuestoConsumo = 0.08;
		this.propina = propina;
	}

	public double getImpuestoConsumo() {
		return impuestoConsumo;
	}

	public double getPropina() {
		return propina;
	}
}
