package logica;

import java.io.Serializable;

public abstract class ProductoCafeteria extends ProductoVenta implements Serializable {
	public ProductoCafeteria(String nombre, double precio) {
		super(nombre, precio);
	}
}
