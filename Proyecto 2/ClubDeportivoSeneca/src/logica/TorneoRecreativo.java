package logica;

import java.sql.Date;
import java.util.ArrayList;

public class TorneoRecreativo extends Torneo {
	private double porcentajeBono;
	private ArrayList<BonoDescuento> bonos;

	public TorneoRecreativo(String idTorneo, String nombre, String categoriaNivel, int cuposTotales, Date fecha, FormatoCompetencia formato, DeporteConjunto deporte, DisciplinaIndividual disciplina, double porcentajeBono) throws Exception {
		super(idTorneo, nombre, categoriaNivel, cuposTotales, fecha, formato, deporte, disciplina);
		this.porcentajeBono = porcentajeBono;
		this.bonos = new ArrayList<BonoDescuento>();
	}

	public double getPorcentajeBono() {
		return porcentajeBono;
	}

	public ArrayList<BonoDescuento> getBonos() {
		return bonos;
	}

	public BonoDescuento generarBono(ParticipanteTorneo ganador) throws Exception {
		if(!this.estado.equals("Finalizado")) {
			throw new Exception("El torneo no ha finalizado");
		}

		String codigo = "BONO-" + this.idTorneo + "-" + (this.bonos.size() + 1);
		BonoDescuento bonoDescuento = new BonoDescuento(codigo, this.porcentajeBono, ganador);
		this.bonos.add(bonoDescuento);
		return bonoDescuento;
	}
	@Override
	public String getTipoTorneo() {
		return "Recreativo";
	}

}
