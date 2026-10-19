package logica;

import java.io.Serializable;

public class RegistroNomina implements Serializable {
	private Socio socio;
	private boolean habilitado;

	public RegistroNomina(Socio socio, boolean habilitado) {
		this.socio = socio;
		this.habilitado = habilitado;
	}

	public Socio getSocio() {
		return socio;
	}

	public boolean isHabilitado() {
		return this.habilitado;
	}

	public void setHabilitado(boolean habilitado) {
		this.habilitado = habilitado;
	}
}
