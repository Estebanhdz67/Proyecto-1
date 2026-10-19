package logica;

import java.io.Serializable;

import java.sql.Time;

public class Turno implements Serializable {
	private String diaSemana;
	private Time horaInicio;
	private Time horaFin;

	public Turno(String diaSemana, Time horaInicio, Time horaFin) {
		this.diaSemana = diaSemana;
		this.horaInicio = horaInicio;
		this.horaFin = horaFin;
	}

	public String getDiaSemana() {
		return diaSemana;
	}

	public Time getHoraInicio() {
		return horaInicio;
	}

	public Time getHoraFin() {
		return horaFin;
	}

	public boolean cubre(Time hora) {
		return !hora.before(this.horaInicio)&&hora.before(this.horaFin);
	}
}
