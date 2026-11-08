package logica;

import java.io.Serializable;
import java.sql.Date;
import java.util.ArrayList;

public abstract class Torneo implements Serializable {
	protected String idTorneo;
	protected String nombre;
	protected String categoriaNivel;
	protected int cuposTotales;
	protected int cuposPreferenciales;
	protected boolean cuposPreferencialesLiberados;
	protected Date fecha;
	protected String estado;
	protected ArrayList<InscripcionTorneo> inscripciones;
	protected ArrayList<EncuentroTorneo> encuentros;
	protected FormatoCompetencia formato;
	protected DeporteConjunto deporte;
	protected DisciplinaIndividual disciplina;

	public Torneo(String idTorneo, String nombre, String categoriaNivel, int cuposTotales, Date fecha, FormatoCompetencia formato, DeporteConjunto deporte, DisciplinaIndividual disciplina) throws Exception {
		if(cuposTotales <= 0) {
			throw new Exception("Los cupos deben ser mayores a cero");
		}

		if(deporte == null && disciplina == null) {
			throw new Exception("El torneo debe tener un deporte o una disciplina");
		}

		if(deporte != null && disciplina != null) {
			throw new Exception("El torneo no puede tener deporte y disciplina al mismo tiempo");
		}

		this.idTorneo = idTorneo;
		this.nombre = nombre;
		this.categoriaNivel = categoriaNivel;
		this.cuposTotales = cuposTotales;
		this.fecha = fecha;
		this.formato = formato;
		this.deporte = deporte;
		this.disciplina = disciplina;
		this.estado = "Disponible";
		this.inscripciones = new ArrayList<InscripcionTorneo>();
		this.encuentros = new ArrayList<EncuentroTorneo>();
		this.cuposPreferenciales = this.calcularCuposPreferenciales();
		this.cuposPreferencialesLiberados = false;
	}

	public String getIdTorneo() {
		return idTorneo;
	}

	public String getNombre() {
		return nombre;
	}

	public String getCategoriaNivel() {
		return categoriaNivel;
	}

	public int getCuposTotales() {
		return cuposTotales;
	}

	public int getCuposPreferenciales() {
		return cuposPreferenciales;
	}

	public Date getFecha() {
		return fecha;
	}

	public String getEstado() {
		return estado;
	}

	public ArrayList<InscripcionTorneo> getInscripciones() {
		return inscripciones;
	}

	public ArrayList<EncuentroTorneo> getEncuentros() {
		return encuentros;
	}

	public FormatoCompetencia getFormato() {
		return formato;
	}

	public DeporteConjunto getDeporte() {
		return deporte;
	}

	public DisciplinaIndividual getDisciplina() {
		return disciplina;
	}

	public int calcularCuposPreferenciales() {
		return (int) Math.ceil(this.cuposTotales * 0.20);
	}

	public int cuposDisponibles() {
		return this.cuposTotales - this.inscripciones.size();
	}

	public InscripcionTorneo inscribir(ParticipanteTorneo participante, boolean preferencial) throws Exception {
		if(!this.estado.equals("Disponible")) {
			throw new Exception("El torneo ya inicio");
		}

		if(participante == null) {
			throw new Exception("Participante invalido");
		}

		if(this.deporte != null && !participante.getTipoParticipante().equals("Equipo")) {
			throw new Exception("En el torneo de equipo solo se pueden inscribir equipos");
		}

		if(this.disciplina != null && participante.getTipoParticipante().equals("Equipo")) {
			throw new Exception("En el torneo individual no se pueden inscribir equipos");
		}

		if(this.cuposDisponibles() <= 0) {
			throw new Exception("No hay cupos disponibles");
		}

		for(InscripcionTorneo inscripcionTorneo : this.inscripciones) {
			if(inscripcionTorneo.getParticipante() == participante) {
				throw new Exception("El participante ya esta inscrito");
			}
		}

		if(preferencial && participante.getRankingTorneo() <= 0) {
			throw new Exception("El participante no cumple el criterio preferencial");
		}

		if(!preferencial && this.cuposDisponibles() <= this.cuposPreferenciales) {
			throw new Exception("Los cupos disponibles son preferenciales");
		}

		InscripcionTorneo inscripcionTorneo = new InscripcionTorneo(new Date(System.currentTimeMillis()), preferencial, participante, this);
		this.inscripciones.add(inscripcionTorneo);

		if(preferencial && this.cuposPreferenciales > 0) {
			this.cuposPreferenciales--;
		}

		return inscripcionTorneo;
	}

	public void desinscribir(ParticipanteTorneo participante) throws Exception {
		if(!this.estado.equals("Disponible")) {
			throw new Exception("El torneo ya inicio");
		}

		InscripcionTorneo inscripcionEncontrada = null;

		for(InscripcionTorneo inscripcionTorneo : this.inscripciones) {
			if(inscripcionTorneo.getParticipante() == participante) {
				inscripcionEncontrada = inscripcionTorneo;
			}
		}

		if(inscripcionEncontrada == null) {
			throw new Exception("El participante no esta inscrito");
		}

		this.inscripciones.remove(inscripcionEncontrada);
		if(inscripcionEncontrada.esPreferencial() && !this.cuposPreferencialesLiberados) {
			this.cuposPreferenciales++;
		}
	}

	public void liberarCuposPreferenciales() {
		this.cuposPreferenciales = 0;
		this.cuposPreferencialesLiberados = true;
	}

	public void iniciarTorneo() throws Exception {
		if(!this.estado.equals("Disponible")) {
			throw new Exception("El torneo no esta disponible");
		}

		this.estado = "En curso";
	}

	public void finalizarTorneo() throws Exception {
		if(!this.estado.equals("En curso")) {
			throw new Exception("El torneo no esta en curso");
		}

		this.estado = "Finalizado";
	}

	public void agregarEncuentro(EncuentroTorneo encuentroTorneo) {
		this.encuentros.add(encuentroTorneo);
	}

	public abstract String getTipoTorneo();

	public String consultarHistorial() {
		String texto = "Participantes\n";

		for(InscripcionTorneo inscripcionTorneo : this.inscripciones) {
			texto += inscripcionTorneo.getParticipante().getNombreParticipante() + "\n";
		}

		texto += "Encuentros\n";

		for(EncuentroTorneo encuentroTorneo : this.encuentros) {
			texto += encuentroTorneo.getParticipante1().getNombreParticipante() + " - ";
			texto += encuentroTorneo.getParticipante2().getNombreParticipante() + " - ";

			if(encuentroTorneo.getResultado() == null) {
				texto += "Sin resultado\n";
			}else {
				texto += encuentroTorneo.getResultado() + "\n";
			}
		}

		texto += "Posiciones\n";
		int posicion = 1;
		for(InscripcionTorneo inscripcionTorneo : this.inscripciones) {
			texto += posicion + ". " + inscripcionTorneo.getParticipante().getNombreParticipante() + "\n";
			posicion++;
		}

		return texto;
	}
}
