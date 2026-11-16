package logica;

import java.io.Serializable;

public class ExistenciaInventario implements Serializable {
	private ArticuloTienda articulo;
	private UbicacionInventario ubicacion;
	private int cantidad;

	public ExistenciaInventario(ArticuloTienda articulo, UbicacionInventario ubicacion, int cantidad) {
		this.articulo = articulo;
		this.ubicacion = ubicacion;
		this.cantidad = cantidad;
	}

	public ArticuloTienda getArticulo() {
		return articulo;
	}

	public UbicacionInventario getUbicacion() {
		return ubicacion;
	}

	public int getCantidad() {
		return cantidad;
	}

	public void aumentar(int cantidadAgregar) throws Exception {
		if(cantidadAgregar < 0) {
			throw new Exception("Cantidad invalida");
		}

		this.cantidad += cantidadAgregar;
	}

	public void disminuir(int cantidadRetirar) throws Exception {
		if(cantidadRetirar < 0 || cantidadRetirar > this.cantidad) {
			throw new Exception("No hay suficientes unidades");
		}

		this.cantidad -= cantidadRetirar;
	}
}
