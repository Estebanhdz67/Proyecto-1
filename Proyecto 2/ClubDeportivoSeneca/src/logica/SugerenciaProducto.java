package logica;

import java.io.Serializable;

public class SugerenciaProducto implements Serializable {
	private String descripcion;
	private String estado;
	private Empleado empleado;
	private Administrador administrador;

	public SugerenciaProducto(String descripcion, Empleado empleado) {
		this.descripcion = descripcion;
		this.empleado = empleado;
		this.estado = "Pendiente";
	}

	public String getDescripcion() {
		return descripcion;
	}

	public String getEstado() {
		return estado;
	}

	public void setEstado(String estado) {
		this.estado = estado;
	}

	public void setAdministrador(Administrador administrador) {
		this.administrador = administrador;
	}
}
