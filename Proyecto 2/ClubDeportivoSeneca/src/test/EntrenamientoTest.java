package test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.sql.Date;
import java.sql.Time;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import logica.CanchaConjunto;
import logica.CanchaTenis;
import logica.ClubDeportivoSeneca;
import logica.Equipo;
import logica.Futbol;
import logica.SesionEntrenamiento;
import logica.Tenis;

public class EntrenamientoTest {
	private ClubDeportivoSeneca club;
	private Equipo equipo;
	private SesionEntrenamiento sesion;

	@BeforeEach
	public void setUp() throws Exception {
		this.club = new ClubDeportivoSeneca();

		this.club.crearEntrenador("e1", "123", "Entrenador 1", "E1", "D1", "Futbol");
		this.club.crearFisioterapeuta("f1", "123", "Fisio 1", "FI1", "D2", "General");
		this.club.agregarInstalacion(new CanchaConjunto("CC1", 30, false));

		this.club.crearEquipo("Seneca FC", 10, new Futbol(), "Mayores");
		this.equipo = this.club.getEquipo("Seneca FC");

		for(int i = 1; i <= 7; i++) {
			this.club.crearSocio("s" + i, "123", "Jugador " + i, "S" + i, Date.valueOf("2000-01-10"));
			this.club.inscribirJugadorEquipo("S" + i, "Seneca FC", "Campo", i);
		}

		this.sesion = this.club.crearSesion(Date.valueOf("2026-12-01"), Time.valueOf("08:00:00"), 90, "E1", "CC1", this.equipo, null);
		this.club.convocarEquipo(this.sesion, this.equipo);
	}

	@Test
	public void crearSesionTest1() {
		assertEquals(1, this.club.getSesiones().size());
		assertEquals(7, this.sesion.getAsistencias().size());
		assertEquals("Pendiente", this.sesion.getAsistencias().get(0).getEstado());
	}

	@Test
	public void crearSesionTest2() {
		Exception excepcion = assertThrows(
				Exception.class,
				() -> this.club.crearSesion(Date.valueOf("2026-12-01"), Time.valueOf("08:00:00"), 90, "FI1", "CC1", this.equipo, null)
				);

		assertEquals("No es entrenador", excepcion.getMessage());
	}

	@Test
	public void convocarEquipoTest() {
		this.club.convocarEquipo(this.sesion, this.equipo);

		assertEquals(7, this.sesion.getAsistencias().size());
	}

	@Test
	public void registrarAsistenciaTest1() throws Exception {
		this.club.registrarAsistencia(this.sesion, "S1", "Asistio");

		assertEquals("Asistio", this.sesion.buscarAsistencia("S1").getEstado());
	}

	@Test
	public void registrarAsistenciaTest2() {
		Exception excepcion = assertThrows(
				Exception.class,
				() -> this.club.registrarAsistencia(this.sesion, "S1", "Vino")
				);

		assertEquals("Estado invalido", excepcion.getMessage());
	}

	@Test
	public void registrarAsistenciaTest3() {
		Exception excepcion = assertThrows(
				Exception.class,
				() -> this.club.registrarAsistencia(this.sesion, "S99", "Asistio")
				);

		assertEquals("El socio no esta convocado", excepcion.getMessage());
	}

	@Test
	public void registrarEvaluacionTest1() throws Exception {
		this.club.registrarAsistencia(this.sesion, "S1", "Asistio");
		this.club.registrarEvaluacion(this.sesion, "S1", 4.5, "Buen entrenamiento");

		assertEquals(4.5, this.sesion.buscarAsistencia("S1").getEvaluacion().getNota());
		assertTrue(this.club.consultarEntrenamientos("S1").contains("Asistio\t4.5"));
	}

	@Test
	public void registrarEvaluacionTest2() throws Exception {
		this.club.registrarAsistencia(this.sesion, "S1", "Asistio");

		Exception excepcion = assertThrows(
				Exception.class,
				() -> this.club.registrarEvaluacion(this.sesion, "S1", 6, "Nota fuera de rango")
				);

		assertEquals("La nota debe estar entre 1 y 5", excepcion.getMessage());
	}

	@Test
	public void registrarEvaluacionTest3() throws Exception {
		this.club.registrarAsistencia(this.sesion, "S2", "Tarde");
		this.club.registrarEvaluacion(this.sesion, "S2", 3.5, "Llego tarde pero cumplio");

		assertEquals(3.5, this.sesion.buscarAsistencia("S2").getEvaluacion().getNota());
	}

	@Test
	public void registrarEvaluacionTest4() throws Exception {
		this.club.registrarAsistencia(this.sesion, "S3", "Falto");

		Exception excepcion = assertThrows(
				Exception.class,
				() -> this.club.registrarEvaluacion(this.sesion, "S3", 4, "No vino")
				);

		assertEquals("Solo se puede evaluar a un jugador que asistio o llego tarde", excepcion.getMessage());
		assertNull(this.sesion.buscarAsistencia("S3").getEvaluacion());
	}

	@Test
	public void registrarEvaluacionTest5() {
		Exception excepcion = assertThrows(
				Exception.class,
				() -> this.club.registrarEvaluacion(this.sesion, "S4", 4, "Sin asistencia registrada")
				);

		assertEquals("Solo se puede evaluar a un jugador que asistio o llego tarde", excepcion.getMessage());
	}

	@Test
	public void instalacionOcupadaTest1() {
		Exception excepcion = assertThrows(
				Exception.class,
				() -> this.club.crearSesion(Date.valueOf("2026-12-01"), Time.valueOf("09:00:00"), 60, "E1", "CC1", this.equipo, null)
				);

		assertEquals("La instalacion esta ocupada por otra sesion", excepcion.getMessage());
		assertEquals(1, this.club.getSesiones().size());
	}

	@Test
	public void instalacionOcupadaTest2() throws Exception {
		this.club.crearSesion(Date.valueOf("2026-12-01"), Time.valueOf("09:30:00"), 60, "E1", "CC1", this.equipo, null);
		this.club.crearSesion(Date.valueOf("2026-12-02"), Time.valueOf("08:00:00"), 90, "E1", "CC1", this.equipo, null);

		assertEquals(3, this.club.getSesiones().size());
	}

	@Test
	public void instalacionOcupadaTest3() throws Exception {
		this.club.agregarDisciplina(new Tenis(2));
		this.club.agregarInstalacion(new CanchaTenis("CT1", 4, false));
		this.club.inscribirAcademia("S1", "Tenis", "Intermedio");
		this.club.reservarInstalacion("S1", "Tenis", "CT1", Date.valueOf("2026-12-03"), Time.valueOf("10:00:00"), Time.valueOf("11:00:00"), 2, "Sencillos");

		Exception excepcion = assertThrows(
				Exception.class,
				() -> this.club.crearSesion(Date.valueOf("2026-12-03"), Time.valueOf("09:30:00"), 60, "E1", "CT1", null, this.club.getDisciplina("Tenis"))
				);

		assertEquals("La instalacion esta ocupada por una reserva", excepcion.getMessage());
	}

	@Test
	public void instalacionOcupadaTest4() throws Exception {
		this.club.agregarDisciplina(new Tenis(2));
		this.club.agregarInstalacion(new CanchaTenis("CT1", 4, false));
		this.club.inscribirAcademia("S1", "Tenis", "Intermedio");
		this.club.reservarInstalacion("S1", "Tenis", "CT1", Date.valueOf("2026-12-03"), Time.valueOf("10:00:00"), Time.valueOf("11:00:00"), 2, "Sencillos");

		this.club.crearSesion(Date.valueOf("2026-12-03"), Time.valueOf("11:00:00"), 60, "E1", "CT1", null, this.club.getDisciplina("Tenis"));

		assertEquals(2, this.club.getSesiones().size());
	}

	@Test
	public void crearSesionTest3() {
		Exception excepcion = assertThrows(
				Exception.class,
				() -> this.club.crearSesion(Date.valueOf("2026-12-04"), Time.valueOf("08:00:00"), 0, "E1", "CC1", this.equipo, null)
				);

		assertEquals("Duracion invalida", excepcion.getMessage());
	}

	@Test
	public void crearSesionTest4() {
		Exception excepcion = assertThrows(
				Exception.class,
				() -> this.club.crearSesion(Date.valueOf("2026-12-04"), Time.valueOf("23:00:00"), 120, "E1", "CC1", this.equipo, null)
				);

		assertEquals("La sesion debe terminar el mismo dia", excepcion.getMessage());
	}
}
