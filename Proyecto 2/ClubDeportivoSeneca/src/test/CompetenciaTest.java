package test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.sql.Date;
import java.util.ArrayList;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import logica.Baloncesto;
import logica.ClubDeportivoSeneca;
import logica.Competencia;
import logica.Futbol;
import logica.InscripcionCompetencia;
import logica.PartidoCompetencia;
import logica.Socio;

public class CompetenciaTest {
	private ClubDeportivoSeneca club;
	private ArrayList<Socio> nominaFutbol;
	private ArrayList<Socio> nominaBaloncesto;

	@BeforeEach
	public void setUp() throws Exception {
		this.club = new ClubDeportivoSeneca();
		this.nominaFutbol = new ArrayList<Socio>();
		this.nominaBaloncesto = new ArrayList<Socio>();

		this.club.crearEquipo("Seneca FC", 10, new Futbol(), "Mayores");
		this.club.crearEquipo("Seneca BC", 10, new Baloncesto(), "Mayores");

		for(int i = 1; i <= 7; i++) {
			this.club.crearSocio("f" + i, "123", "Futbolista " + i, "F" + i, Date.valueOf("2000-01-10"));
			this.club.inscribirJugadorEquipo("F" + i, "Seneca FC", "Campo", i);
			this.nominaFutbol.add(this.club.getSocio("F" + i));
		}

		for(int i = 1; i <= 5; i++) {
			this.club.crearSocio("b" + i, "123", "Basquetbolista " + i, "B" + i, Date.valueOf("2000-01-10"));
			this.club.inscribirJugadorEquipo("B" + i, "Seneca BC", "Base", i);
			this.nominaBaloncesto.add(this.club.getSocio("B" + i));
		}

		this.club.crearCompetencia("Liga ASCUN", "ASCUN", "2026-2", new Futbol(), "Mayores", "Liga");
	}

	@Test
	public void crearCompetenciaTest1() throws Exception {
		Competencia competencia = this.club.crearCompetencia("Copa Bogota", "Liga Bogota", "2026-2", new Futbol(), "Mayores", "Eliminacion directa");

		assertEquals("Copa Bogota", competencia.getNombre());
		assertEquals("Eliminacion directa", competencia.getFormato().getNombre());
	}

	@Test
	public void crearCompetenciaTest2() {
		Exception excepcion = assertThrows(
				Exception.class,
				() -> this.club.crearCompetencia("Copa X", "Liga Bogota", "2026-2", new Futbol(), "Mayores", "Todos contra todos")
				);

		assertEquals("Formato no valido", excepcion.getMessage());
	}

	@Test
	public void crearCompetenciaTest3() {
		Exception excepcion = assertThrows(
				Exception.class,
				() -> this.club.crearCompetencia("Liga ASCUN", "ASCUN", "2026-2", new Futbol(), "Mayores", "Liga")
				);

		assertEquals("La competencia ya existe", excepcion.getMessage());
	}

	@Test
	public void inscribirEquipoCompetenciaTest1() throws Exception {
		InscripcionCompetencia inscripcion = this.club.inscribirEquipoCompetencia("Liga ASCUN", "Seneca FC", this.nominaFutbol);
		Competencia competencia = this.club.getCompetencia("Liga ASCUN");

		assertEquals("Activa", inscripcion.getEstado());
		assertEquals(7, inscripcion.getNomina().size());
		assertEquals(1, competencia.getInscripciones().size());
		assertEquals(1, competencia.getTabla().size());
	}

	@Test
	public void inscribirEquipoCompetenciaTest2() {
		Exception excepcion = assertThrows(
				Exception.class,
				() -> this.club.inscribirEquipoCompetencia("Liga ASCUN", "Seneca BC", this.nominaBaloncesto)
				);

		assertEquals("Deporte no habilitado", excepcion.getMessage());
	}

	@Test
	public void inscribirEquipoCompetenciaTest3() throws Exception {
		this.club.crearEquipo("Seneca Sub17", 10, new Futbol(), "Sub17");

		Exception excepcion = assertThrows(
				Exception.class,
				() -> this.club.inscribirEquipoCompetencia("Liga ASCUN", "Seneca Sub17", new ArrayList<Socio>())
				);

		assertEquals("Categoria no habilitada", excepcion.getMessage());
	}

	@Test
	public void inscribirEquipoCompetenciaTest4() throws Exception {
		ArrayList<Socio> nominaCorta = new ArrayList<Socio>();
		nominaCorta.add(this.club.getSocio("F1"));
		nominaCorta.add(this.club.getSocio("F2"));
		nominaCorta.add(this.club.getSocio("F3"));

		Exception excepcion = assertThrows(
				Exception.class,
				() -> this.club.inscribirEquipoCompetencia("Liga ASCUN", "Seneca FC", nominaCorta)
				);

		assertEquals("Nomina insuficiente", excepcion.getMessage());
		assertEquals(0, this.club.getCompetencia("Liga ASCUN").getInscripciones().size());
	}

	@Test
	public void inscribirEquipoCompetenciaTest5() throws Exception {
		this.club.inscribirEquipoCompetencia("Liga ASCUN", "Seneca FC", this.nominaFutbol);

		Exception excepcion = assertThrows(
				Exception.class,
				() -> this.club.inscribirEquipoCompetencia("Liga ASCUN", "Seneca FC", this.nominaFutbol)
				);

		assertEquals("El equipo ya esta inscrito", excepcion.getMessage());
	}

	@Test
	public void programarPartidoCompetenciaTest1() {
		Exception excepcion = assertThrows(
				Exception.class,
				() -> this.club.programarPartidoCompetencia(Date.valueOf("2026-12-05"), "Seneca FC", "Liga ASCUN", "Rival FC", "Estadio")
				);

		assertEquals("El equipo no esta inscrito en la competencia", excepcion.getMessage());
	}

	@Test
	public void registrarResultadoCompetenciaTest1() throws Exception {
		this.club.inscribirEquipoCompetencia("Liga ASCUN", "Seneca FC", this.nominaFutbol);
		PartidoCompetencia partido = this.club.programarPartidoCompetencia(Date.valueOf("2026-12-05"), "Seneca FC", "Liga ASCUN", "Rival FC", "Estadio");

		this.club.registrarResultadoCompetencia(partido, "2-1");

		Competencia competencia = this.club.getCompetencia("Liga ASCUN");
		assertEquals("2-1", partido.getMarcador());
		assertEquals(3, competencia.buscarFila(this.club.getEquipo("Seneca FC")).getPuntos());
	}

	@Test
	public void registrarResultadoCompetenciaTest2() throws Exception {
		this.club.inscribirEquipoCompetencia("Liga ASCUN", "Seneca FC", this.nominaFutbol);
		PartidoCompetencia partido = this.club.programarPartidoCompetencia(Date.valueOf("2026-12-05"), "Seneca FC", "Liga ASCUN", "Rival FC", "Estadio");

		this.club.registrarResultadoCompetencia(partido, "1-1");

		Competencia competencia = this.club.getCompetencia("Liga ASCUN");
		assertEquals(1, competencia.buscarFila(this.club.getEquipo("Seneca FC")).getPuntos());
	}

	@Test
	public void registrarResultadoCompetenciaTest3() throws Exception {
		this.club.crearCompetencia("Liga Baloncesto", "ASCUN", "2026-2", new Baloncesto(), "Mayores", "Liga");
		this.club.inscribirEquipoCompetencia("Liga Baloncesto", "Seneca BC", this.nominaBaloncesto);
		PartidoCompetencia partido = this.club.programarPartidoCompetencia(Date.valueOf("2026-12-05"), "Seneca BC", "Liga Baloncesto", "Rival BC", "Coliseo");

		Exception excepcion = assertThrows(
				Exception.class,
				() -> this.club.registrarResultadoCompetencia(partido, "80-80")
				);

		assertEquals("En este deporte no puede haber empate", excepcion.getMessage());
	}
}
