package test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.sql.Date;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import logica.ClubDeportivoSeneca;
import logica.EncuentroTorneo;
import logica.EstudianteUniversidad;
import logica.Futbol;
import logica.Padel;
import logica.ParticipanteExterno;

public class ParticipanteTorneoTest {
	private ClubDeportivoSeneca club;

	@BeforeEach
	public void setUp() throws Exception {
		this.club = new ClubDeportivoSeneca();

		this.club.crearTorneo("T1", "Torneo Padel", "Intermedio", 10, Date.valueOf("2026-11-09"), "Competitivo", "Liga", null, new Padel(4), 20000);
	}

	@Test
	public void registrarEstudianteTest1() throws Exception {
		EstudianteUniversidad estudiante = this.club.registrarEstudiante("100", "Laura", 1100);

		assertEquals(estudiante, this.club.getEstudiante("100"));
		assertEquals("Estudiante", estudiante.getTipoParticipante());
		assertTrue(this.club.consultarEstudiantes().contains("100\tLaura\t1100.0"));
	}

	@Test
	public void registrarEstudianteTest2() throws Exception {
		this.club.registrarEstudiante("100", "Laura", 1100);

		Exception excepcion = assertThrows(
				Exception.class,
				() -> this.club.registrarEstudiante("100", "Otra Laura", 900)
				);

		assertEquals("El estudiante ya existe", excepcion.getMessage());
		assertEquals(1, this.club.getEstudiantes().size());
	}

	@Test
	public void registrarEstudianteTest3() {
		Exception excepcion = assertThrows(
				Exception.class,
				() -> this.club.registrarEstudiante("", "Laura", 1100)
				);

		assertEquals("Datos del estudiante incompletos", excepcion.getMessage());
	}

	@Test
	public void registrarEstudianteTest4() {
		Exception excepcion = assertThrows(
				Exception.class,
				() -> this.club.registrarEstudiante("100", "Laura", -5)
				);

		assertEquals("Ranking invalido", excepcion.getMessage());
	}

	@Test
	public void registrarExternoTest1() throws Exception {
		ParticipanteExterno externo = this.club.registrarExterno("CC200", "Carlos", 950);

		assertEquals(externo, this.club.getExterno("CC200"));
		assertEquals("Externo", externo.getTipoParticipante());
		assertTrue(this.club.consultarExternos().contains("CC200\tCarlos\t950.0"));
	}

	@Test
	public void registrarExternoTest2() throws Exception {
		this.club.registrarExterno("CC200", "Carlos", 950);

		Exception excepcion = assertThrows(
				Exception.class,
				() -> this.club.registrarExterno("CC200", "Carlos", 950)
				);

		assertEquals("El participante externo ya existe", excepcion.getMessage());
	}

	@Test
	public void registrarExternoTest3() {
		Exception excepcion = assertThrows(
				Exception.class,
				() -> this.club.getExterno("CC999")
				);

		assertEquals("Participante externo no existe", excepcion.getMessage());
	}

	@Test
	public void inscribirEstudianteTest1() throws Exception {
		this.club.registrarEstudiante("100", "Laura", 1100);

		this.club.inscribirParticipanteTorneo("T1", this.club.getEstudiante("100"), true);

		assertTrue(this.club.estaInscritoTorneo("T1", this.club.getEstudiante("100")));
		assertTrue(this.club.consultarHistorialTorneo("T1").contains("Laura"));
	}

	@Test
	public void inscribirEstudianteTest2() throws Exception {
		this.club.registrarEstudiante("100", "Laura", 0);

		Exception excepcion = assertThrows(
				Exception.class,
				() -> this.club.inscribirParticipanteTorneo("T1", this.club.getEstudiante("100"), true)
				);

		assertEquals("El participante no cumple el criterio preferencial", excepcion.getMessage());
	}

	@Test
	public void inscribirExternoTest() throws Exception {
		this.club.crearTorneo("T2", "Torneo Futbol", "Mayores", 8, Date.valueOf("2026-11-09"), "Recreativo", "Liga", new Futbol(), null, 0.10);
		this.club.registrarExterno("CC200", "Carlos", 950);

		Exception excepcion = assertThrows(
				Exception.class,
				() -> this.club.inscribirParticipanteTorneo("T2", this.club.getExterno("CC200"), false)
				);

		assertEquals("En el torneo de equipo solo se pueden inscribir equipos", excepcion.getMessage());
	}

	@Test
	public void encuentroEstudianteExternoTest() throws Exception {
		this.club.registrarEstudiante("100", "Laura", 1100);
		this.club.registrarExterno("CC200", "Carlos", 950);
		this.club.inscribirParticipanteTorneo("T1", this.club.getEstudiante("100"), false);
		this.club.inscribirParticipanteTorneo("T1", this.club.getExterno("CC200"), false);
		this.club.iniciarTorneo("T1");

		EncuentroTorneo encuentro = this.club.crearEncuentroTorneo("T1", Date.valueOf("2026-11-09"), this.club.getEstudiante("100"), this.club.getExterno("CC200"));

		assertEquals("Laura", encuentro.getParticipante1().getNombreParticipante());
		assertTrue(this.club.consultarHistorialTorneo("T1").contains("Laura - Carlos - Sin resultado"));
	}
}
