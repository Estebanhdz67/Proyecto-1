package logica;

import java.io.Serializable;

import java.sql.Date;

public class PartidoInterno extends Partido implements Serializable {
	private Equipo local;
	private Equipo visitante;

	public PartidoInterno(Date fecha, Equipo local, Equipo visitante) throws Exception {
		super(fecha);
		if(!local.getDeporte().getNombre().equals(visitante.getDeporte().getNombre())) {
			throw new Exception("Los equipos deben ser del mismo deporte");
		}
		if(!local.getCategoria().equals(visitante.getCategoria())) {
			throw new Exception("Los equipos deben ser de la misma categoria");
		}
		if(local.cantidadJugadoresHabilitados()<local.getDeporte().getMinimoJugadores()||visitante.cantidadJugadoresHabilitados()<visitante.getDeporte().getMinimoJugadores()) {
			throw new Exception("No se cumple el minimo de jugadores habilitados");
		}
		this.local = local;
		this.visitante = visitante;
	}

	public Equipo getLocal() {
		return local;
	}

	public Equipo getVisitante() {
		return visitante;
	}
}
