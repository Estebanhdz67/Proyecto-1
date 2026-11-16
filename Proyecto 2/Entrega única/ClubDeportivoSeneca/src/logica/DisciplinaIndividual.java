package logica;

import java.io.Serializable;

public abstract class DisciplinaIndividual implements Serializable {
	private String nombre;
	private int maxInstalacionesSimultaneas;

	public DisciplinaIndividual(String nombre, int maxInstalacionesSimultaneas) {
		this.nombre = nombre;
		this.maxInstalacionesSimultaneas = maxInstalacionesSimultaneas;
	}

	public String getNombre() {
		return nombre;
	}

	public int getMaxInstalacionesSimultaneas() {
		return maxInstalacionesSimultaneas;
	}

	@Override
	public String toString() {
		return this.nombre;
	}
}
