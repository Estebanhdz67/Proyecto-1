package logica;

import java.io.Serializable;

public class Entrenador extends Empleado implements Serializable {
	private String especialidad;
	private Socio perfilSocio;

	public Entrenador(String login, String password, String nombre, String idEmpleado, String codigoDescuento, String especialidad) {
		super(login, password, nombre, idEmpleado, codigoDescuento);
		this.especialidad = especialidad;
		this.perfilSocio = null;
	}

	public String getEspecialidad() {
		return especialidad;
	}

	public Socio getPerfilSocio() {
		return perfilSocio;
	}

	public void setPerfilSocio(Socio perfilSocio) {
		this.perfilSocio = perfilSocio;
	}
}
