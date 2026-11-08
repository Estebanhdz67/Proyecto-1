package logica;

import java.io.Serializable;
import java.sql.Date;

public class InscripcionTorneo implements Serializable {
	private Date fechaInscripcion;
	private boolean preferencial;
	private ParticipanteTorneo participante;
	private Torneo torneo;

	public InscripcionTorneo(Date fechaInscripcion, boolean preferencial, ParticipanteTorneo participante, Torneo torneo) {
		this.fechaInscripcion = fechaInscripcion;
		this.preferencial = preferencial;
		this.participante = participante;
		this.torneo = torneo;
	}

	public Date getFechaInscripcion() {
		return fechaInscripcion;
	}

	public ParticipanteTorneo getParticipante() {
		return participante;
	}

	public Torneo getTorneo() {
		return torneo;
	}

	public boolean esPreferencial() {
		return preferencial;
	}
}
