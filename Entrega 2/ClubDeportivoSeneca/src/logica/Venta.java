package logica;

import java.io.Serializable;

import java.sql.Date;
import java.util.ArrayList;

public abstract class Venta implements Serializable {
	private Date fecha;
	private double subtotal;
	private double descuento;
	private int puntosRedimidos;
	private String codigoDescuentoUsado;
	private double total;
	private Comprador comprador;
	private ArrayList<DetalleVenta> detalles;

	public Venta(Date fecha, Comprador comprador) {
		this.fecha = fecha;
		this.comprador = comprador;
		this.detalles = new ArrayList<DetalleVenta>();
	}

	public Date getFecha() {
		return fecha;
	}

	public double getSubtotal() {
		return subtotal;
	}

	public double getDescuento() {
		return descuento;
	}

	public int getPuntosRedimidos() {
		return puntosRedimidos;
	}

	public String getCodigoDescuentoUsado() {
		return codigoDescuentoUsado;
	}

	public double getTotal() {
		return total;
	}

	public Comprador getComprador() {
		return comprador;
	}

	public ArrayList<DetalleVenta> getDetalles() {
		return detalles;
	}

	public void agregarDetalle(DetalleVenta detalleVenta) {
		this.detalles.add(detalleVenta);
	}

	public double calcularSubtotal() {
		double subtotal = 0;
		for(DetalleVenta detalleVenta : this.detalles) {
			subtotal += detalleVenta.calcularSubtotal();
		}
		this.subtotal = subtotal;
		return subtotal;
	}

	public void setDescuento(double descuento) {
		this.descuento = descuento;
	}

	public void setPuntosRedimidos(int puntosRedimidos) {
		this.puntosRedimidos = puntosRedimidos;
	}

	public void setCodigoDescuentoUsado(String codigoDescuentoUsado) {
		this.codigoDescuentoUsado = codigoDescuentoUsado;
	}

	public void setTotal(double total) {
		this.total = total;
	}
}
