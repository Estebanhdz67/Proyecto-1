package logica;

import java.io.Serializable;

import java.sql.Date;
import java.util.ArrayList;

public class PartidoPractica implements Serializable {
	private Date fecha;
	private String resultado;
	private String modalidad;
	private DisciplinaIndividual disciplina;
	private ArrayList<Socio> jugadores;
	private ArrayList<EstadisticaRaqueta> estadisticas;

	public PartidoPractica(Date fecha, String resultado, String modalidad, DisciplinaIndividual disciplina) {
		this.fecha = fecha;
		this.resultado = resultado;
		this.modalidad = modalidad;
		this.disciplina = disciplina;
		this.jugadores = new ArrayList<Socio>();
		this.estadisticas = new ArrayList<EstadisticaRaqueta>();
	}

	public Date getFecha() {
		return fecha;
	}

	public String getResultado() {
		return resultado;
	}

	public void setResultado(String resultado) {
		this.resultado = resultado;
	}

	public String getModalidad() {
		return modalidad;
	}

	public DisciplinaIndividual getDisciplina() {
		return disciplina;
	}

	public ArrayList<Socio> getJugadores() {
		return jugadores;
	}

	public ArrayList<EstadisticaRaqueta> getEstadisticas() {
		return estadisticas;
	}

	public void agregarJugador(Socio socio) {
		this.jugadores.add(socio);
	}

	public void agregarEstadistica(EstadisticaRaqueta estadisticaRaqueta) {
		this.estadisticas.add(estadisticaRaqueta);
	}

	public void validarJugadores() throws Exception {
		if(this.jugadores.size()!=2&&this.jugadores.size()!=4) {
			throw new Exception("El partido debe tener 2 o 4 jugadores");
		}
		if(this.disciplina instanceof Padel&&this.jugadores.size()!=4) {
			throw new Exception("Padel siempre se juega con 4 jugadores");
		}
		for(int indiceJugadorUno = 0; indiceJugadorUno < this.jugadores.size(); indiceJugadorUno++) {
			for(int indiceJugadorDos = indiceJugadorUno + 1; indiceJugadorDos < this.jugadores.size(); indiceJugadorDos++) {
				String nivelJugadorUno = this.jugadores.get(indiceJugadorUno).consultarNivel(this.disciplina);
				String nivelJugadorDos = this.jugadores.get(indiceJugadorDos).consultarNivel(this.disciplina);
				if(nivelJugadorUno == null || nivelJugadorDos == null) {
					throw new Exception("Todos deben estar inscritos en la disciplina");
				}
				if(!nivelesCompatibles(nivelJugadorUno, nivelJugadorDos) &&
						!esEntrenadorSocio(this.jugadores.get(indiceJugadorUno)) &&
						!esEntrenadorSocio(this.jugadores.get(indiceJugadorDos))) {
					throw new Exception("Los niveles no son compatibles");
				}
			}
		}
	}

	private boolean nivelesCompatibles(String nivelUno, String nivelDos) {
		if(nivelUno.equals(nivelDos)) {
			return true;
		}
		if((nivelUno.equals("Principiante") && nivelDos.equals("Intermedio")) ||
				(nivelUno.equals("Intermedio") && nivelDos.equals("Principiante"))) {
			return true;
		}
		if((nivelUno.equals("Intermedio") && nivelDos.equals("Avanzado")) ||
				(nivelUno.equals("Avanzado") && nivelDos.equals("Intermedio"))) {
			return true;
		}
		return false;
	}

	private boolean esEntrenadorSocio(Socio socio) {
		return socio.isEntrenadorAsociado();
	}

	public void actualizarRankingSencillos(Socio ganador, Socio perdedor) throws Exception {
		Ranking rg = ganador.consultarRanking(this.disciplina);
		Ranking rp = perdedor.consultarRanking(this.disciplina);
		if(rg==null||rp==null) {
			throw new Exception("No existe ranking");
		}
		rg.sumarPuntos(20);
		rp.restarPuntos(20);
	}
}
