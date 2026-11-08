package logica;

import java.io.Serializable;

import java.sql.Date;
import java.util.ArrayList;

public class InscripcionCompetencia extends Inscripcion implements Serializable {
	private Equipo equipo;
	private Competencia competencia;
	private ArrayList<RegistroNomina> nomina;

	public InscripcionCompetencia(Date fecha, String estado, Equipo equipo, Competencia competencia) {
		super(fecha, estado);
		this.equipo = equipo;
		this.competencia = competencia;
		this.nomina = new ArrayList<RegistroNomina>();
	}

	public Equipo getEquipo() {
		return equipo;
	}

	public Competencia getCompetencia() {
		return competencia;
	}

	public ArrayList<RegistroNomina> getNomina() {
		return nomina;
	}

	public void agregarJugador(Socio socio) throws Exception {
		if(!this.equipo.contieneSocio(socio)) {
			throw new Exception("El socio no pertenece al equipo");
		}
		for(RegistroNomina registroNomina : this.nomina) {
			if(registroNomina.getSocio().getIdSocio().equals(socio.getIdSocio())) {
				throw new Exception("El socio ya esta en la nomina");
			}
		}
		this.nomina.add(new RegistroNomina(socio, true));
	}

	public boolean jugadorHabilitado(Socio socio) {
		for(RegistroNomina registroNomina : this.nomina) {
			if(registroNomina.getSocio().getIdSocio().equals(socio.getIdSocio()) && registroNomina.isHabilitado() && !socio.tieneSancionActiva()) {
				return true;
			}
		}
		return false;
	}
}
