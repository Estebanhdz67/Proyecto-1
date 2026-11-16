package logica;

import java.io.Serializable;
import java.util.ArrayList;

public abstract class FormatoCompetencia implements Serializable {
	protected String nombre;

	public FormatoCompetencia(String nombre) {
		this.nombre = nombre;
	}

	public String getNombre() {
		return nombre;
	}

	public abstract ArrayList<String> generarEnfrentamientos(ArrayList<InscripcionCompetencia> inscripciones);

	public abstract ArrayList<InscripcionCompetencia> clasificarParticipantes(ArrayList<InscripcionCompetencia> inscripciones);
}
