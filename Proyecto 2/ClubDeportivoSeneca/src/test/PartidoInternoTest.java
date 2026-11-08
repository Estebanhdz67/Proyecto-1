package test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.sql.Date;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import logica.Equipo;
import logica.Futbol;
import logica.PartidoInterno;

public class PartidoInternoTest {
	private Futbol futbol;
	private Equipo equipo;

	@BeforeEach
	public void setUp() {
		this.futbol = new Futbol();
		this.equipo = new Equipo("Equipo A", 15, this.futbol, "Mayores");
	}

	@Test
	public void partidoInternoTest1() {
		Exception excepcion = assertThrows(
				Exception.class,
				() -> new PartidoInterno(Date.valueOf("2026-11-09"), this.equipo, this.equipo)
				);

		assertEquals("Los equipos deben ser diferentes", excepcion.getMessage());
	}
}
