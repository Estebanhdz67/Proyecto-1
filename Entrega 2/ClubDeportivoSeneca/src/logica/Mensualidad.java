package logica;

import java.io.Serializable;

import java.sql.Date;

public class Mensualidad implements Serializable {
	private String mes;
	private double valor;
	private Date fechaPago;

	public Mensualidad(String mes, double valor, Date fechaPago) {
		this.mes = mes;
		this.valor = valor;
		this.fechaPago = fechaPago;
	}

	public String getMes() {
		return mes;
	}

	public double getValor() {
		return valor;
	}

	public Date getFechaPago() {
		return fechaPago;
	}
}
