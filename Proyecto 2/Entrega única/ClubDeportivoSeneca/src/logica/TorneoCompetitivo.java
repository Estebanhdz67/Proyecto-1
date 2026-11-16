package logica;

import java.sql.Date;

public class TorneoCompetitivo extends Torneo {
	private double tarifaInscripcion;
	private double totalRecaudado;

	public TorneoCompetitivo(String idTorneo, String nombre, String categoriaNivel, int cuposTotales, Date fecha, FormatoCompetencia formato, DeporteConjunto deporte, DisciplinaIndividual disciplina, double tarifaInscripcion) throws Exception {
		super(idTorneo, nombre, categoriaNivel, cuposTotales, fecha, formato, deporte, disciplina);

		if(tarifaInscripcion < 0) {
			throw new Exception("Tarifa invalida");
		}

		this.tarifaInscripcion = tarifaInscripcion;
		this.totalRecaudado = 0;
	}

	public double getTarifaInscripcion() {
		return tarifaInscripcion;
	}

	public double getTotalRecaudado() {
		return totalRecaudado;
	}

	public void registrarPago(double valor) throws Exception {
		if(valor < 0) {
			throw new Exception("Valor invalido");
		}

		this.totalRecaudado += valor;
	}

	public double calcularPremio() {
		return this.totalRecaudado;
	}

	public boolean puedeRecibirPremio(boolean empleado) {
		if(empleado) {
			return false;
		}

		return true;
	}
	@Override
	public String getTipoTorneo() {
		return "Competitivo";
	}

}
