package logica;

import java.io.Serializable;

public class ArticuloTienda extends ProductoVenta implements Serializable {
	private String categoria;

	public ArticuloTienda(String nombre, double precio, String categoria) {
		super(nombre, precio);
		this.categoria = categoria;
	}

	public String getCategoria() {
		return categoria;
	}
}
