package logica;

import java.util.ArrayList;

public class EliminacionDirecta extends FormatoCompetencia {

	public EliminacionDirecta() {
		super("Eliminacion directa");
	}

	@Override
	public ArrayList<String> generarEnfrentamientos(ArrayList<InscripcionCompetencia> inscripciones) {
		ArrayList<String> enfrentamientos = new ArrayList<String>();

		for(int i = 0; i + 1 < inscripciones.size(); i += 2) {
			String equipoUno = inscripciones.get(i).getEquipo().getNombre();
			String equipoDos = inscripciones.get(i + 1).getEquipo().getNombre();
			enfrentamientos.add(equipoUno + " - " + equipoDos);
		}

		return enfrentamientos;
	}

	@Override
	public ArrayList<InscripcionCompetencia> clasificarParticipantes(ArrayList<InscripcionCompetencia> inscripciones) {
		ArrayList<InscripcionCompetencia> clasificados = new ArrayList<InscripcionCompetencia>();

		for(InscripcionCompetencia inscripcionCompetencia : inscripciones) {
			clasificados.add(inscripcionCompetencia);
		}

		return clasificados;
	}
}
