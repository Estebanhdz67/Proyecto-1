package test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.sql.Date;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import logica.ClubDeportivoSeneca;
import logica.PartidoPractica;
import logica.Ranking;
import logica.Socio;
import logica.Tenis;

public class PartidoPracticaTest {
	private ClubDeportivoSeneca club;
	private Tenis tenis;
	private Socio socio1;
	private Socio socio2;
	private PartidoPractica partido;

	@BeforeEach
	public void setUp() {
		this.club = new ClubDeportivoSeneca();
		this.tenis = new Tenis(4);
		this.socio1 = new Socio("s1", "123", "Paula", "1", Date.valueOf("2005-10-19"));
		this.socio2 = new Socio("s2", "123", "Juan", "2", Date.valueOf("2005-01-10"));

		this.socio1.agregarRanking(new Ranking(1000, Date.valueOf("2026-11-01"), this.socio1, this.tenis));
		this.socio2.agregarRanking(new Ranking(1000, Date.valueOf("2026-11-01"), this.socio2, this.tenis));

		this.partido = new PartidoPractica(Date.valueOf("2026-11-09"), null, "Sencillos", this.tenis);
		this.partido.agregarJugador(this.socio1);
		this.partido.agregarJugador(this.socio2);
	}

	@Test
	public void resultadoPracticaTest1() throws Exception {
		this.club.registrarResultadoPractica(this.partido, "6-4", this.socio1, this.socio2);

		assertEquals("6-4", this.partido.getResultado());
		assertEquals(1020, this.socio1.consultarRanking(this.tenis).getPuntajeELO());
		assertEquals(980, this.socio2.consultarRanking(this.tenis).getPuntajeELO());
	}

	@Test
	public void resultadoPracticaTest2() throws Exception {
		this.club.registrarResultadoPractica(this.partido, "6-4", this.socio1, this.socio2);

		Exception excepcion = assertThrows(
				Exception.class,
				() -> this.club.registrarResultadoPractica(this.partido, "6-3", this.socio1, this.socio2)
				);

		assertEquals("El partido ya tiene resultado", excepcion.getMessage());
	}

	@Test
	public void resultadoPracticaTest3() throws Exception {
		Socio socio3 = new Socio("s3", "123", "Maria", "3", Date.valueOf("2005-02-10"));

		Exception excepcion = assertThrows(
				Exception.class,
				() -> this.club.registrarResultadoPractica(this.partido, "6-4", this.socio1, socio3)
				);

		assertEquals("Ganador y perdedor deben pertenecer al partido", excepcion.getMessage());
	}
}
