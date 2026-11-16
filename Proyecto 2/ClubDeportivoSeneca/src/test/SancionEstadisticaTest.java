package test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.sql.Date;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import logica.Baloncesto;
import logica.ClubDeportivoSeneca;
import logica.EstadisticaFutbol;
import logica.Futbol;
import logica.PartidoInterno;
import logica.Sancion;
import logica.Socio;

public class SancionEstadisticaTest {
	private ClubDeportivoSeneca club;
	private PartidoInterno partido;
	private Socio jugador;

	@BeforeEach
	public void setUp() throws Exception {
		this.club = new ClubDeportivoSeneca();

		this.club.crearEquipo("Equipo A", 10, new Futbol(), "Mayores");
		this.club.crearEquipo("Equipo B", 10, new Futbol(), "Mayores");

		for(int i = 1; i <= 7; i++) {
			this.club.crearSocio("a" + i, "123", "Jugador A" + i, "A" + i, Date.valueOf("2000-01-10"));
			this.club.inscribirJugadorEquipo("A" + i, "Equipo A", "Campo", i);
			this.club.crearSocio("b" + i, "123", "Jugador B" + i, "B" + i, Date.valueOf("2000-01-10"));
			this.club.inscribirJugadorEquipo("B" + i, "Equipo B", "Campo", i);
		}

		this.partido = this.club.programarPartidoInterno(Date.valueOf("2026-12-01"), "Equipo A", "Equipo B");
		this.jugador = this.club.getSocio("A1");
	}

	@Test
	public void estadisticaFutbolTest() {
		this.club.registrarEstadisticaFutbol(this.partido, this.jugador, 2, 1, 0, 0);

		EstadisticaFutbol estadistica = (EstadisticaFutbol) this.partido.getEstadisticas().get(0);
		assertEquals(2, estadistica.getGoles());
		assertEquals(1, estadistica.getAsistencias());
		assertFalse(this.jugador.tieneSancionActiva());
	}

	@Test
	public void estadisticaBaloncestoTest() throws Exception {
		this.club.crearEquipo("BC A", 10, new Baloncesto(), "Mayores");
		this.club.crearEquipo("BC B", 10, new Baloncesto(), "Mayores");

		for(int i = 1; i <= 5; i++) {
			this.club.crearSocio("ba" + i, "123", "Base A" + i, "BA" + i, Date.valueOf("2000-01-10"));
			this.club.inscribirJugadorEquipo("BA" + i, "BC A", "Base", i);
			this.club.crearSocio("bb" + i, "123", "Base B" + i, "BB" + i, Date.valueOf("2000-01-10"));
			this.club.inscribirJugadorEquipo("BB" + i, "BC B", "Base", i);
		}

		PartidoInterno partidoBaloncesto = this.club.programarPartidoInterno(Date.valueOf("2026-12-02"), "BC A", "BC B");
		this.club.registrarEstadisticaBaloncesto(partidoBaloncesto, this.club.getSocio("BA1"), 20, 8);
		this.club.registrarResultadoInterno(partidoBaloncesto, "80-75");

		assertEquals(1, partidoBaloncesto.getEstadisticas().size());
		assertEquals("80-75", partidoBaloncesto.getMarcador());
	}

	@Test
	public void sancionTest1() {
		this.club.registrarEstadisticaFutbol(this.partido, this.jugador, 0, 0, 0, 1);

		assertTrue(this.jugador.tieneSancionActiva());
		assertEquals(1, this.jugador.getSanciones().size());
		assertEquals(1, this.jugador.getSanciones().get(0).getPartidosPendientes());
	}

	@Test
	public void sancionTest2() {
		this.club.registrarEstadisticaFutbol(this.partido, this.jugador, 0, 0, 2, 0);

		assertTrue(this.jugador.tieneSancionActiva());
	}

	@Test
	public void sancionTest3() {
		this.club.registrarEstadisticaFutbol(this.partido, this.jugador, 0, 0, 1, 0);

		assertFalse(this.jugador.tieneSancionActiva());
		assertEquals(0, this.jugador.getSanciones().size());
	}

	@Test
	public void sancionTest4() {
		this.club.registrarEstadisticaFutbol(this.partido, this.jugador, 0, 0, 0, 1);
		Sancion sancion = this.jugador.getSanciones().get(0);

		sancion.cumplirPartido();

		assertFalse(sancion.isActiva());
		assertFalse(this.jugador.tieneSancionActiva());
	}

	@Test
	public void sancionTest5() throws Exception {
		this.club.registrarEstadisticaFutbol(this.partido, this.jugador, 0, 0, 0, 1);

		assertEquals(6, this.club.getEquipo("Equipo A").cantidadJugadoresHabilitados());

		Exception excepcion = assertThrows(
				Exception.class,
				() -> this.club.programarPartidoInterno(Date.valueOf("2026-12-08"), "Equipo A", "Equipo B")
				);

		assertEquals("No se cumple el minimo de jugadores habilitados", excepcion.getMessage());
	}
}
