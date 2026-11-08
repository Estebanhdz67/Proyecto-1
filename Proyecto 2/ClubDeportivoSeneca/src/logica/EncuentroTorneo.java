package logica;

import java.io.Serializable;
import java.sql.Date;

public class EncuentroTorneo implements Serializable {
	private Date fecha;
	private ParticipanteTorneo participante1;
	private ParticipanteTorneo participante2;
	private String resultado;

	public EncuentroTorneo(Date fecha, ParticipanteTorneo participante1, ParticipanteTorneo participante2) throws Exception {
		if(participante1 == null || participante2 == null) {
			throw new Exception("Los participantes son obligatorios");
		}

		if(participante1 == participante2) {
			throw new Exception("Los participantes deben ser diferentes");
		}

		this.fecha = fecha;
		this.participante1 = participante1;
		this.participante2 = participante2;
		this.resultado = null;
	}

	public Date getFecha() {
		return fecha;
	}

	public ParticipanteTorneo getParticipante1() {
		return participante1;
	}

	public ParticipanteTorneo getParticipante2() {
		return participante2;
	}

	public String getResultado() {
		return resultado;
	}

	public void registrarResultado(String resultado) throws Exception {
		if(this.resultado != null) {
			throw new Exception("El encuentro ya tiene resultado");
		}

		if(resultado == null || resultado.equals("")) {
			throw new Exception("Resultado invalido");
		}

		this.resultado = resultado;
	}

	public boolean tieneResultado() {
		if(this.resultado != null) {
			return true;
		}

		return false;
	}
}
