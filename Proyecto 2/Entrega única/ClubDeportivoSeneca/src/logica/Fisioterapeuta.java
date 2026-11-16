package logica;

import java.io.Serializable;

public class Fisioterapeuta extends Empleado implements Serializable {
	private String especialidad;

	public Fisioterapeuta(String login, String password, String nombre, String idEmpleado, String codigoDescuento, String especialidad) {
		super(login, password, nombre, idEmpleado, codigoDescuento);
		this.especialidad = especialidad;
	}

	public String getEspecialidad() {
		return especialidad;
	}
}
