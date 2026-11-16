package test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.sql.Date;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import logica.ClubDeportivoSeneca;
import logica.Equipo;
import logica.Futbol;
import logica.InscripcionEquipo;

public class EquipoTest {
	private ClubDeportivoSeneca club;

	@BeforeEach
	public void setUp() throws Exception {
		this.club = new ClubDeportivoSeneca();

		this.club.crearEquipo("Seneca FC", 7, new Futbol(), "Mayores");
		this.club.crearSocio("s1", "123", "Paula", "1", Date.valueOf("2000-10-19"));
		this.club.crearSocio("s2", "123", "Juan", "2", Date.valueOf("2000-01-10"));
	}

	@Test
	public void crearEquipoTest1() {
		Exception excepcion = assertThrows(
				Exception.class,
				() -> this.club.crearEquipo("Equipo Corto", 5, new Futbol(), "Mayores")
				);

		assertEquals("Cupo menor al minimo del deporte", excepcion.getMessage());
	}

	@Test
	public void crearEquipoTest2() {
		Exception excepcion = assertThrows(
				Exception.class,
				() -> this.club.crearEquipo("Seneca FC", 10, new Futbol(), "Mayores")
				);

		assertEquals("El equipo ya existe", excepcion.getMessage());
	}

	@Test
	public void inscribirJugadorTest1() throws Exception {
		this.club.inscribirJugadorEquipo("1", "Seneca FC", "Delantera", 9);

		assertTrue(this.club.getEquipo("Seneca FC").contieneSocio(this.club.getSocio("1")));
		assertTrue(this.club.consultarEquiposSocio("1").contains("Seneca FC\tFutbol\tMayores\tDelantera\t9"));
	}

	@Test
	public void inscribirJugadorTest2() throws Exception {
		this.club.inscribirJugadorEquipo("1", "Seneca FC", "Delantera", 9);

		Exception excepcion = assertThrows(
				Exception.class,
				() -> this.club.inscribirJugadorEquipo("2", "Seneca FC", "Defensa", 9)
				);

		assertEquals("El numero de camiseta ya esta ocupado", excepcion.getMessage());
	}

	@Test
	public void inscribirJugadorTest3() throws Exception {
		this.club.crearEquipo("Seneca Sub15", 10, new Futbol(), "Sub15");

		Exception excepcion = assertThrows(
				Exception.class,
				() -> this.club.inscribirJugadorEquipo("1", "Seneca Sub15", "Delantera", 9)
				);

		assertEquals("La edad del socio no corresponde a la categoria", excepcion.getMessage());
	}

	@Test
	public void inscribirJugadorTest4() throws Exception {
		this.club.crearEquipo("Seneca FC B", 10, new Futbol(), "Mayores");
		this.club.inscribirJugadorEquipo("1", "Seneca FC", "Delantera", 9);

		Exception excepcion = assertThrows(
				Exception.class,
				() -> this.club.inscribirJugadorEquipo("1", "Seneca FC B", "Delantera", 10)
				);

		assertEquals("El socio ya esta en un equipo del mismo deporte", excepcion.getMessage());
	}

	@Test
	public void inscribirJugadorTest5() throws Exception {
		for(int i = 1; i <= 7; i++) {
			this.club.crearSocio("j" + i, "123", "Jugador " + i, "J" + i, Date.valueOf("2000-01-10"));
			this.club.inscribirJugadorEquipo("J" + i, "Seneca FC", "Campo", i);
		}

		Exception excepcion = assertThrows(
				Exception.class,
				() -> this.club.inscribirJugadorEquipo("1", "Seneca FC", "Delantera", 20)
				);

		assertEquals("El equipo no tiene cupos disponibles", excepcion.getMessage());
	}

	@Test
	public void darBajaSocioTest() throws Exception {
		this.club.inscribirJugadorEquipo("1", "Seneca FC", "Delantera", 9);
		InscripcionEquipo inscripcion = this.club.getSocio("1").getInscripcionesEquipo().get(0);

		this.club.darBajaSocio("1");

		assertEquals("Inactiva", inscripcion.getEstado());
		assertFalse(this.club.getSocios().containsKey("1"));
		assertThrows(Exception.class, () -> this.club.getSocio("1"));
	}

	@Test
	public void darBajaEquipoTest() throws Exception {
		this.club.inscribirJugadorEquipo("1", "Seneca FC", "Delantera", 9);
		Equipo equipo = this.club.getEquipo("Seneca FC");

		this.club.darBajaEquipo("Seneca FC");

		assertEquals("Inactiva", equipo.getInscripciones().get(0).getEstado());
		assertFalse(this.club.getEquipos().containsKey("Seneca FC"));
	}
}
