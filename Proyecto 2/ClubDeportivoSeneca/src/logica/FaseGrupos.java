package logica;

import java.util.ArrayList;

public class FaseGrupos extends FormatoCompetencia {

	public FaseGrupos() {
		super("Fase de grupos");
	}

	@Override
	public ArrayList<String> generarEnfrentamientos(ArrayList<InscripcionCompetencia> inscripciones) {
		ArrayList<String> enfrentamientos = new ArrayList<String>();

		for(int i = 0; i < inscripciones.size(); i++) {
			for(int j = i + 1; j < inscripciones.size(); j++) {
				String equipoUno = inscripciones.get(i).getEquipo().getNombre();
				String equipoDos = inscripciones.get(j).getEquipo().getNombre();
				enfrentamientos.add(equipoUno + " - " + equipoDos);
			}
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
