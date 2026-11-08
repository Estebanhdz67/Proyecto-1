package logica;

import java.io.Serializable;

public class DetalleVenta implements Serializable {
	private ProductoVenta producto;
	private int cantidad;
	private double precioUnitario;

	public DetalleVenta(ProductoVenta producto, int cantidad, double precioUnitario) {
		this.producto = producto;
		this.cantidad = cantidad;
		this.precioUnitario = precioUnitario;
	}

	public ProductoVenta getProducto() {
		return producto;
	}

	public int getCantidad() {
		return cantidad;
	}

	public double getPrecioUnitario() {
		return precioUnitario;
	}

	public double calcularSubtotal() {
		return this.cantidad*this.precioUnitario;
	}
}
