package test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.sql.Time;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import logica.ClubDeportivoSeneca;
import logica.SolicitudCambioTurno;
import logica.Turno;

public class CambioTurnoTest {
	private ClubDeportivoSeneca club;
	private Turno turnoLunesE1;
	private Turno turnoLunesE3;

	@BeforeEach
	public void setUp() throws Exception {
		this.club = new ClubDeportivoSeneca();

		this.club.crearAdministrador("admin", "123", "Admin", "A1");
		this.club.crearEntrenador("e1", "123", "Entrenador 1", "E1", "D1", "Futbol");
		this.club.crearEntrenador("e2", "123", "Entrenador 2", "E2", "D2", "Futbol");
		this.club.crearEntrenador("e3", "123", "Entrenador 3", "E3", "D3", "Tenis");
		this.club.crearFisioterapeuta("f1", "123", "Fisio 1", "F1", "D4", "General");

		String[] dias = {"Lunes", "Martes", "Miercoles", "Jueves", "Viernes", "Sabado", "Domingo"};

		for(String dia : dias) {
			Turno turno = this.club.asignarTurno("E1", dia, Time.valueOf("06:00:00"), Time.valueOf("22:00:00"));
			if(dia.equals("Lunes")) {
				this.turnoLunesE1 = turno;
			}
			this.club.asignarTurno("E2", dia, Time.valueOf("06:00:00"), Time.valueOf("22:00:00"));
			this.club.asignarTurno("F1", dia, Time.valueOf("06:00:00"), Time.valueOf("22:00:00"));
		}

		this.turnoLunesE3 = this.club.asignarTurno("E3", "Lunes", Time.valueOf("06:00:00"), Time.valueOf("14:00:00"));
	}

	@Test
	public void solicitarCambioTest() throws Exception {
		Turno nuevo = new Turno("Martes", Time.valueOf("06:00:00"), Time.valueOf("14:00:00"));
		SolicitudCambioTurno solicitud = this.club.solicitarCambio("E3", this.turnoLunesE3, nuevo);

		assertEquals("Pendiente", solicitud.getEstado());
		assertEquals(1, this.club.getEmpleado("E3").getSolicitudes().size());
	}

	@Test
	public void revisarCambioTest1() throws Exception {
		Turno nuevo = new Turno("Martes", Time.valueOf("06:00:00"), Time.valueOf("14:00:00"));
		SolicitudCambioTurno solicitud = this.club.solicitarCambio("E3", this.turnoLunesE3, nuevo);

		this.club.revisarCambio(solicitud, "A1", false);

		assertEquals("Rechazada", solicitud.getEstado());
		assertTrue(this.club.getEmpleado("E3").getTurnos().contains(this.turnoLunesE3));
	}

	@Test
	public void revisarCambioTest2() throws Exception {
		Turno nuevo = new Turno("Martes", Time.valueOf("06:00:00"), Time.valueOf("14:00:00"));
		SolicitudCambioTurno solicitud = this.club.solicitarCambio("E3", this.turnoLunesE3, nuevo);

		this.club.revisarCambio(solicitud, "A1", true);

		assertEquals("Aprobada", solicitud.getEstado());
		assertTrue(this.club.getEmpleado("E3").getTurnos().contains(nuevo));
		assertFalse(this.club.getEmpleado("E3").getTurnos().contains(this.turnoLunesE3));
	}

	@Test
	public void revisarCambioTest3() throws Exception {
		Turno nuevo = new Turno("Lunes", Time.valueOf("06:00:00"), Time.valueOf("10:00:00"));
		SolicitudCambioTurno solicitud = this.club.solicitarCambio("E1", this.turnoLunesE1, nuevo);

		Exception excepcion = assertThrows(
				Exception.class,
				() -> this.club.revisarCambio(solicitud, "A1", true)
				);

		assertEquals("El cambio no cumple el minimo de personal", excepcion.getMessage());
		assertEquals("Rechazada", solicitud.getEstado());
		assertTrue(this.club.getEmpleado("E1").getTurnos().contains(this.turnoLunesE1));
		assertTrue(this.club.cumpleMinimoPersonal());
	}

	@Test
	public void revisarCambioTest4() throws Exception {
		Turno nuevo = new Turno("Martes", Time.valueOf("06:00:00"), Time.valueOf("14:00:00"));
		SolicitudCambioTurno solicitud = this.club.solicitarCambio("E3", this.turnoLunesE3, nuevo);

		Exception excepcion = assertThrows(
				Exception.class,
				() -> this.club.revisarCambio(solicitud, "A99", true)
				);

		assertEquals("Administrador no existe", excepcion.getMessage());
		assertEquals("Pendiente", solicitud.getEstado());
	}

	@Test
	public void consultarTurnosTest() throws Exception {
		assertTrue(this.club.consultarTurnos("E3").contains("Lunes\t06:00:00\t14:00:00"));
	}
}
