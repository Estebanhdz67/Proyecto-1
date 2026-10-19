package logica;

import java.io.Serializable;

public abstract class Instalacion implements Serializable {
	private String idInstalacion;
	private int capacidad;
	private boolean techada;

	public Instalacion(String idInstalacion, int capacidad, boolean techada) {
		this.idInstalacion = idInstalacion;
		this.capacidad = capacidad;
		this.techada = techada;
	}

	public String getIdInstalacion() {
		return idInstalacion;
	}

	public int getCapacidad() {
		return capacidad;
	}

	public boolean isTechada() {
		return this.techada;
	}
}
