package logica;

import java.io.Serializable;

public class ParejaTorneo implements ParticipanteTorneo, Serializable {
	private ParticipanteTorneo integrante1;
	private ParticipanteTorneo integrante2;

	public ParejaTorneo(ParticipanteTorneo integrante1, ParticipanteTorneo integrante2) throws Exception {
		this.integrante1 = integrante1;
		this.integrante2 = integrante2;
		this.validarIntegrantes();
	}

	public ParticipanteTorneo getIntegrante1() {
		return integrante1;
	}

	public ParticipanteTorneo getIntegrante2() {
		return integrante2;
	}

	public void validarIntegrantes() throws Exception {
		if(this.integrante1 == null || this.integrante2 == null) {
			throw new Exception("La pareja debe tener dos integrantes");
		}

		if(this.integrante1 == this.integrante2) {
			throw new Exception("Los integrantes deben ser diferentes");
		}
	}

	@Override
	public String getNombreParticipante() {
		return this.integrante1.getNombreParticipante() + " - " + this.integrante2.getNombreParticipante();
	}

	@Override
	public double getRankingTorneo() {
		return (this.integrante1.getRankingTorneo() + this.integrante2.getRankingTorneo()) / 2;
	}
	@Override
	public String getTipoParticipante() {
		return "Pareja";
	}

}
