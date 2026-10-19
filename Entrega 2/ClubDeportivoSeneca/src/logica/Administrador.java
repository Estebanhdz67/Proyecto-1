package logica;

import java.io.Serializable;

public class Administrador extends Usuario implements Serializable {
	private String idAdministrador;

	public Administrador(String login, String password, String nombre, String idAdministrador) {
		super(login, password, nombre);
		this.idAdministrador = idAdministrador;
	}

	public String getIdAdministrador() {
		return idAdministrador;
	}
}
