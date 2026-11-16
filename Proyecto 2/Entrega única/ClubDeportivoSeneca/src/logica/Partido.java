package logica;

import java.io.Serializable;

import java.sql.Date;
import java.util.ArrayList;

public abstract class Partido implements Serializable {
	private Date fecha;
	private String marcador;
	private ArrayList<EstadisticaConjunto> estadisticas;

	public Partido(Date fecha) {
		this.fecha = fecha;
		this.estadisticas = new ArrayList<EstadisticaConjunto>();
	}

	public Date getFecha() {
		return fecha;
	}

	public String getMarcador() {
		return marcador;
	}

	public void setMarcador(String marcador) {
		this.marcador = marcador;
	}

	public ArrayList<EstadisticaConjunto> getEstadisticas() {
		return estadisticas;
	}

	public void agregarEstadistica(EstadisticaConjunto estadisticaConjunto) {
		this.estadisticas.add(estadisticaConjunto);
	}
}
