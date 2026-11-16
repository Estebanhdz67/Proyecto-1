package test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.sql.Date;
import java.sql.Time;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import logica.CanchaConjunto;
import logica.ClubDeportivoSeneca;
import logica.Equipo;
import logica.Futbol;
import logica.SesionEntrenamiento;

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
	public void registrarEvaluacionTest2() {
		Exception excepcion = assertThrows(
				Exception.class,
				() -> this.club.registrarEvaluacion(this.sesion, "S1", 6, "Nota fuera de rango")
				);

		assertEquals("La nota debe estar entre 1 y 5", excepcion.getMessage());
	}
}
