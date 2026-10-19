package logica;

import java.io.Serializable;

public abstract class Usuario implements Serializable {
	private String login;
	private String password;
	private String nombre;

	public Usuario(String login, String password, String nombre) {
		this.login = login;
		this.password = password;
		this.nombre = nombre;
	}

	public String getLogin() {
		return login;
	}

	public void setLogin(String login) {
		this.login = login;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public boolean validarCredenciales(String login, String password) {
		return this.login.equals(login) && this.password.equals(password);
	}

	@Override
	public String toString() {
		return this.login + "\t" + this.nombre;
	}
}
