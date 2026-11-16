package test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.sql.Date;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import logica.ClubDeportivoSeneca;
import logica.InscripcionAcademia;
import logica.Socio;
import logica.Tenis;

public class AcademiaTest {
	private ClubDeportivoSeneca club;
	private Tenis tenis;

	@BeforeEach
	public void setUp() throws Exception {
		this.club = new ClubDeportivoSeneca();
		this.tenis = new Tenis(2);

		this.club.crearSocio("s1", "123", "Paula", "1", Date.valueOf("2005-10-19"));
		this.club.agregarDisciplina(this.tenis);
	}

	@Test
	public void inscribirAcademiaTest1() throws Exception {
		InscripcionAcademia inscripcion = this.club.inscribirAcademia("1", "Tenis", "Principiante");
		Socio socio = this.club.getSocio("1");

		assertEquals("Activa", inscripcion.getEstado());
		assertEquals("Principiante", socio.consultarNivel(this.tenis));
		assertEquals(1000, socio.consultarRanking(this.tenis).getPuntajeELO());
	}

	@Test
	public void inscribirAcademiaTest2() throws Exception {
		this.club.inscribirAcademia("1", "Tenis", "Principiante");

		Exception excepcion = assertThrows(
				Exception.class,
				() -> this.club.inscribirAcademia("1", "Tenis", "Avanzado")
				);

		assertEquals("Ya existe inscripcion", excepcion.getMessage());
		assertEquals(1, this.club.getSocio("1").getInscripcionesAcademia().size());
	}

	@Test
	public void inscribirAcademiaTest3() {
		Exception excepcion = assertThrows(
				Exception.class,
				() -> this.club.inscribirAcademia("1", "Squash", "Principiante")
				);

		assertEquals("Disciplina no existe", excepcion.getMessage());
	}

	@Test
	public void asignarNivelTest1() throws Exception {
		this.club.inscribirAcademia("1", "Tenis", "Principiante");
		this.club.asignarNivel("1", "Tenis", "Avanzado");

		assertEquals("Avanzado", this.club.getSocio("1").consultarNivel(this.tenis));
	}

	@Test
	public void asignarNivelTest2() {
		Exception excepcion = assertThrows(
				Exception.class,
				() -> this.club.asignarNivel("1", "Tenis", "Avanzado")
				);

		assertEquals("No existe inscripcion activa", excepcion.getMessage());
	}
}
