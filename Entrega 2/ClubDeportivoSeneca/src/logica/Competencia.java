package logica;

import java.io.Serializable;

import java.util.ArrayList;

public class Competencia implements Serializable {
	private String nombre;
	private String entidadOrganizadora;
	private String temporada;
	private DeporteConjunto deporte;
	private String categoriaHabilitada;
	private String formato;
	private ArrayList<InscripcionCompetencia> inscripciones;
	private ArrayList<PartidoCompetencia> partidos;
	private ArrayList<TablaPosiciones> tabla;

	public Competencia(String nombre, String entidad, String temporada, DeporteConjunto deporte, String categoria, String formato) {
		this.nombre = nombre;
		this.entidadOrganizadora = entidad;
		this.temporada = temporada;
		this.deporte = deporte;
		this.categoriaHabilitada = categoria;
		this.formato = formato;
		this.inscripciones = new ArrayList<InscripcionCompetencia>();
		this.partidos = new ArrayList<PartidoCompetencia>();
		this.tabla = new ArrayList<TablaPosiciones>();
	}

	public String getNombre() {
		return nombre;
	}

	public DeporteConjunto getDeporte() {
		return deporte;
	}

	public String getCategoriaHabilitada() {
		return categoriaHabilitada;
	}

	public String getFormato() {
		return formato;
	}

	public ArrayList<InscripcionCompetencia> getInscripciones() {
		return inscripciones;
	}

	public ArrayList<PartidoCompetencia> getPartidos() {
		return partidos;
	}

	public ArrayList<TablaPosiciones> getTabla() {
		return tabla;
	}

	public void agregarInscripcion(InscripcionCompetencia inscripcionCompetencia) {
		this.inscripciones.add(inscripcionCompetencia);

		if(this.formato.equals("Liga")) {
			this.tabla.add(new TablaPosiciones(inscripcionCompetencia));
		}
	}

	public void agregarPartido(PartidoCompetencia partidoCompetencia) {
		this.partidos.add(partidoCompetencia);
	}

	public TablaPosiciones buscarFila(Equipo equipo) {
		for(TablaPosiciones filaTabla : this.tabla) {
			if(filaTabla.getInscripcion().getEquipo().getNombre().equals(equipo.getNombre())) {
				return filaTabla;
			}
		}

		return null;
	}
}
