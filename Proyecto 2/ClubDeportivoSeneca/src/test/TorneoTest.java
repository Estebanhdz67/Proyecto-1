package test;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.sql.Date;
import java.sql.Time;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import logica.BonoDescuento;
import logica.ClubDeportivoSeneca;
import logica.EncuentroTorneo;
import logica.Entrenador;
import logica.Futbol;
import logica.Padel;
import logica.ParticipanteExterno;
import logica.Torneo;

public class TorneoTest {
	private ClubDeportivoSeneca club;
	private Padel padel;
	private Futbol futbol;
	private ParticipanteExterno participante;

	@BeforeEach
	public void setUp() {
		this.club = new ClubDeportivoSeneca();
		this.padel = new Padel(4);
		this.futbol = new Futbol();
		this.participante = new ParticipanteExterno("100", "Paula", 1200);
	}

	@Test
	public void crearTorneoTest1() throws Exception {
		Torneo torneo = this.club.crearTorneo(
				"T1",
				"Torneo Padel",
				"Intermedio",
				10,
				Date.valueOf("2026-11-09"),
				"Recreativo",
				"Liga",
				null,
				this.padel,
				0.10
				);

		assertEquals("Torneo Padel", torneo.getNombre());
		assertEquals("Disponible", torneo.getEstado());
		assertEquals(2, torneo.getCuposPreferenciales());
	}

	@Test
	public void crearTorneoTest2() throws Exception {
		Exception excepcion = assertThrows(
				Exception.class,
				() -> this.club.crearTorneo(
						"T1",
						"Torneo Padel",
						"Intermedio",
						0,
						Date.valueOf("2026-11-09"),
						"Recreativo",
						"Liga",
						null,
						this.padel,
						0.10
						)
				);

		assertEquals("Los cupos deben ser mayores a cero", excepcion.getMessage());
	}

	@Test
	public void inscribirParticipanteTest1() throws Exception {
		this.club.crearTorneo(
				"T1",
				"Torneo Padel",
				"Intermedio",
				5,
				Date.valueOf("2026-11-09"),
				"Recreativo",
				"Liga",
				null,
				this.padel,
				0.10
				);

		this.club.inscribirParticipanteTorneo("T1", this.participante, false);

		assertEquals(1, this.club.getTorneo("T1").getInscripciones().size());
	}

	@Test
	public void inscribirParticipanteTest2() throws Exception {
		this.club.crearTorneo(
				"T1",
				"Torneo Futbol",
				"Mayores",
				8,
				Date.valueOf("2026-11-09"),
				"Recreativo",
				"Liga",
				this.futbol,
				null,
				0.10
				);

		Exception excepcion = assertThrows(
				Exception.class,
				() -> this.club.inscribirParticipanteTorneo("T1", this.participante, false)
				);

		assertEquals("En el torneo de equipo solo se pueden inscribir equipos", excepcion.getMessage());
	}

	@Test
	public void cupoPreferencialTest() throws Exception {
		ParticipanteExterno participanteSinRanking = new ParticipanteExterno("200", "Juan", 0);

		this.club.crearTorneo(
				"T1",
				"Torneo Padel",
				"Intermedio",
				5,
				Date.valueOf("2026-11-09"),
				"Recreativo",
				"Liga",
				null,
				this.padel,
				0.10
				);

		Exception excepcion = assertThrows(
				Exception.class,
				() -> this.club.inscribirParticipanteTorneo("T1", participanteSinRanking, true)
				);

		assertEquals("El participante no cumple el criterio preferencial", excepcion.getMessage());
	}

	@Test
	public void retirarParticipanteTest1() throws Exception {
		this.club.crearTorneo(
				"T1",
				"Torneo Padel",
				"Intermedio",
				5,
				Date.valueOf("2026-11-09"),
				"Recreativo",
				"Liga",
				null,
				this.padel,
				0.10
				);

		this.club.inscribirParticipanteTorneo("T1", this.participante, false);
		this.club.desinscribirParticipanteTorneo("T1", this.participante);

		assertEquals(0, this.club.getTorneo("T1").getInscripciones().size());
	}

	@Test
	public void retirarParticipanteTest2() throws Exception {
		this.club.crearTorneo(
				"T1",
				"Torneo Padel",
				"Intermedio",
				5,
				Date.valueOf("2026-11-09"),
				"Recreativo",
				"Liga",
				null,
				this.padel,
				0.10
				);

		this.club.inscribirParticipanteTorneo("T1", this.participante, false);
		this.club.iniciarTorneo("T1");

		Exception excepcion = assertThrows(
				Exception.class,
				() -> this.club.desinscribirParticipanteTorneo("T1", this.participante)
				);

		assertEquals("El torneo ya inicio", excepcion.getMessage());
	}

	@Test
	public void empleadoTurnoTest() throws Exception {
		Entrenador entrenador = new Entrenador("ent1", "123", "Carlos", "E1", "DESC", "Padel");
		entrenador.agregarTurno(new logica.Turno("Lunes", Time.valueOf("06:00:00"), Time.valueOf("22:00:00")));

		this.club.crearTorneo(
				"T1",
				"Torneo Padel",
				"Intermedio",
				5,
				Date.valueOf("2026-11-09"),
				"Competitivo",
				"Liga",
				null,
				this.padel,
				50000
				);

		Exception excepcion = assertThrows(
				Exception.class,
				() -> this.club.inscribirParticipanteTorneo("T1", entrenador, false)
				);

		assertEquals("El empleado esta de turno el dia del torneo", excepcion.getMessage());
	}

	@Test
	public void empleadoPagoTest() throws Exception {
		Entrenador entrenador = new Entrenador("ent1", "123", "Carlos", "E1", "DESC", "Padel");

		this.club.crearTorneo(
				"T1",
				"Torneo Padel",
				"Intermedio",
				5,
				Date.valueOf("2026-11-09"),
				"Competitivo",
				"Liga",
				null,
				this.padel,
				50000
				);

		this.club.inscribirParticipanteTorneo("T1", entrenador, false);

		Exception excepcion = assertThrows(
				Exception.class,
				() -> this.club.registrarPagoTorneo("T1", entrenador, 50000)
				);

		assertEquals("El empleado no paga inscripcion", excepcion.getMessage());
	}

	@Test
	public void premioCompetitivoTest1() throws Exception {
		this.club.crearTorneo(
				"T1",
				"Torneo Padel",
				"Intermedio",
				5,
				Date.valueOf("2026-11-09"),
				"Competitivo",
				"Liga",
				null,
				this.padel,
				50000
				);

		this.club.inscribirParticipanteTorneo("T1", this.participante, false);
		this.club.registrarPagoTorneo("T1", this.participante, 50000);

		assertEquals(50000, this.club.consultarPremioTorneo("T1", this.participante));
	}

	@Test
	public void premioCompetitivoTest2() throws Exception {
		Entrenador entrenador = new Entrenador("ent1", "123", "Carlos", "E1", "DESC", "Padel");

		this.club.crearTorneo(
				"T1",
				"Torneo Padel",
				"Intermedio",
				5,
				Date.valueOf("2026-11-09"),
				"Competitivo",
				"Liga",
				null,
				this.padel,
				50000
				);

		assertEquals(0, this.club.consultarPremioTorneo("T1", entrenador));
	}

	@Test
	public void bonoRecreativoTest() throws Exception {
		this.club.crearTorneo(
				"T1",
				"Torneo Padel",
				"Intermedio",
				5,
				Date.valueOf("2026-11-09"),
				"Recreativo",
				"Liga",
				null,
				this.padel,
				0.10
				);

		this.club.iniciarTorneo("T1");
		this.club.finalizarTorneo("T1");

		BonoDescuento bono = this.club.generarBonoTorneo("T1", this.participante);

		assertEquals(0.10, bono.getPorcentaje());
		assertFalse(bono.estaUsado());

		bono.usarBono(this.participante, false);

		assertTrue(bono.estaUsado());
	}

	@Test
	public void encuentroTorneoTest() throws Exception {
		ParticipanteExterno participante2 = new ParticipanteExterno("200", "Juan", 1000);

		this.club.crearTorneo(
				"T1",
				"Torneo Padel",
				"Intermedio",
				5,
				Date.valueOf("2026-11-09"),
				"Recreativo",
				"Liga",
				null,
				this.padel,
				0.10
				);

		EncuentroTorneo encuentro = this.club.crearEncuentroTorneo(
				"T1",
				Date.valueOf("2026-11-09"),
				this.participante,
				participante2
				);

		this.club.registrarResultadoTorneo(encuentro, "6-4");

		assertEquals("6-4", encuentro.getResultado());
		assertTrue(encuentro.tieneResultado());
	}

	@Test
	public void historialTorneoTest() throws Exception {
		this.club.crearTorneo(
				"T1",
				"Torneo Padel",
				"Intermedio",
				5,
				Date.valueOf("2026-11-09"),
				"Recreativo",
				"Liga",
				null,
				this.padel,
				0.10
				);

		this.club.inscribirParticipanteTorneo("T1", this.participante, false);

		assertTrue(this.club.consultarHistorialTorneo("T1").contains("Paula"));
		assertTrue(this.club.consultarTorneos().contains("Disponible"));
	}
}
