package test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.sql.Date;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import logica.ClubDeportivoSeneca;

public class MensualidadTest {
	private ClubDeportivoSeneca club;

	@BeforeEach
	public void setUp() throws Exception {
		this.club = new ClubDeportivoSeneca();
		this.club.crearSocio("s1", "123", "Paula", "1", Date.valueOf("2005-10-19"));
	}

	@Test
	public void registrarMensualidadTest1() throws Exception {
		this.club.registrarMensualidad("1", "Enero", 150000, Date.valueOf("2026-01-05"));
		this.club.registrarMensualidad("1", "Febrero", 150000, Date.valueOf("2026-02-05"));

		String consulta = this.club.consultarMensualidades("1");

		assertEquals(2, this.club.getSocio("1").getMensualidades().size());
		assertTrue(consulta.contains("Enero\t150000.0\t2026-01-05"));
		assertTrue(consulta.contains("Febrero\t150000.0\t2026-02-05"));
	}

	@Test
	public void registrarMensualidadTest2() throws Exception {
		Exception excepcion = assertThrows(
				Exception.class,
				() -> this.club.registrarMensualidad("1", "Enero", -1000, Date.valueOf("2026-01-05"))
				);

		assertEquals("Valor invalido", excepcion.getMessage());
		assertEquals(0, this.club.getSocio("1").getMensualidades().size());
	}

	@Test
	public void registrarMensualidadTest3() {
		Exception excepcion = assertThrows(
				Exception.class,
				() -> this.club.registrarMensualidad("99", "Enero", 150000, Date.valueOf("2026-01-05"))
				);

		assertEquals("Socio no existe", excepcion.getMessage());
	}

	@Test
	public void reporteMensualidadesTest() throws Exception {
		this.club.registrarMensualidad("1", "Enero", 150000, Date.valueOf("2026-01-05"));
		this.club.registrarMensualidad("1", "Febrero", 150000, Date.valueOf("2026-02-05"));

		assertTrue(this.club.generarReporteVentas().contains("Mensualidades\t300000.0"));
	}
}
