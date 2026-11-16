package test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.sql.Date;
import java.sql.Time;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import logica.CanchaPadel;
import logica.CanchaTenis;
import logica.ClubDeportivoSeneca;
import logica.Padel;
import logica.Reserva;
import logica.Tenis;

public class ReservaTest {
	private ClubDeportivoSeneca club;
	private Date fecha;

	@BeforeEach
	public void setUp() throws Exception {
		this.club = new ClubDeportivoSeneca();
		this.fecha = Date.valueOf("2026-12-01");

		this.club.crearSocio("s1", "123", "Paula", "1", Date.valueOf("2000-10-19"));
		this.club.crearSocio("s2", "123", "Juan", "2", Date.valueOf("2000-01-10"));

		this.club.agregarDisciplina(new Tenis(1));
		this.club.agregarDisciplina(new Padel(2));

		this.club.agregarInstalacion(new CanchaTenis("C1", 4, false));
		this.club.agregarInstalacion(new CanchaTenis("C2", 4, true));
		this.club.agregarInstalacion(new CanchaPadel("P1", 4, true));

		this.club.inscribirAcademia("1", "Tenis", "Intermedio");
		this.club.inscribirAcademia("1", "Padel", "Principiante");
	}

	@Test
	public void reservarInstalacionTest1() throws Exception {
		Reserva reserva = this.club.reservarInstalacion("1", "Tenis", "C1", this.fecha,
				Time.valueOf("10:00:00"), Time.valueOf("11:00:00"), 2, "Sencillos");

		assertEquals("C1", reserva.getInstalacion().getIdInstalacion());
		assertEquals(1, this.club.getReservas().size());
	}

	@Test
	public void reservarInstalacionTest2() {
		Exception excepcion = assertThrows(
				Exception.class,
				() -> this.club.reservarInstalacion("1", "Tenis", "C1", this.fecha,
						Time.valueOf("10:00:00"), Time.valueOf("11:00:00"), 3, "Sencillos")
				);

		assertEquals("No se permiten 3 jugadores", excepcion.getMessage());
		assertEquals(0, this.club.getReservas().size());
	}

	@Test
	public void reservarInstalacionTest3() {
		Exception excepcion = assertThrows(
				Exception.class,
				() -> this.club.reservarInstalacion("1", "Tenis", "C1", this.fecha,
						Time.valueOf("11:00:00"), Time.valueOf("10:00:00"), 2, "Sencillos")
				);

		assertEquals("Horario invalido", excepcion.getMessage());
	}

	@Test
	public void reservarInstalacionTest4() throws Exception {
		this.club.reservarInstalacion("1", "Tenis", "C1", this.fecha,
				Time.valueOf("10:00:00"), Time.valueOf("11:00:00"), 2, "Sencillos");

		Exception excepcion = assertThrows(
				Exception.class,
				() -> this.club.reservarInstalacion("1", "Tenis", "C1", this.fecha,
						Time.valueOf("10:30:00"), Time.valueOf("11:30:00"), 2, "Sencillos")
				);

		assertEquals("La instalacion ya esta reservada", excepcion.getMessage());
		assertEquals(1, this.club.getReservas().size());
	}

	@Test
	public void reservarInstalacionTest5() throws Exception {
		this.club.reservarInstalacion("1", "Tenis", "C1", this.fecha,
				Time.valueOf("10:00:00"), Time.valueOf("11:00:00"), 2, "Sencillos");

		Exception excepcion = assertThrows(
				Exception.class,
				() -> this.club.reservarInstalacion("1", "Tenis", "C2", this.fecha,
						Time.valueOf("10:30:00"), Time.valueOf("11:30:00"), 2, "Sencillos")
				);

		assertEquals("Se alcanzo el maximo simultaneo", excepcion.getMessage());
	}

	@Test
	public void reservarInstalacionTest6() throws Exception {
		this.club.reservarInstalacion("1", "Tenis", "C1", this.fecha,
				Time.valueOf("10:00:00"), Time.valueOf("11:00:00"), 2, "Sencillos");
		this.club.reservarInstalacion("1", "Tenis", "C1", this.fecha,
				Time.valueOf("11:00:00"), Time.valueOf("12:00:00"), 2, "Sencillos");

		assertEquals(2, this.club.getReservas().size());
	}

	@Test
	public void reservarInstalacionTest7() {
		Exception excepcion = assertThrows(
				Exception.class,
				() -> this.club.reservarInstalacion("2", "Tenis", "C1", this.fecha,
						Time.valueOf("10:00:00"), Time.valueOf("11:00:00"), 2, "Sencillos")
				);

		assertEquals("El socio no esta inscrito en la disciplina", excepcion.getMessage());
	}

	@Test
	public void reservarInstalacionTest8() {
		Exception excepcion = assertThrows(
				Exception.class,
				() -> this.club.reservarInstalacion("1", "Padel", "P1", this.fecha,
						Time.valueOf("10:00:00"), Time.valueOf("11:00:00"), 2, "Dobles")
				);

		assertEquals("Padel requiere 4 jugadores", excepcion.getMessage());
	}

	@Test
	public void reservarInstalacionTest9() {
		Exception excepcion = assertThrows(
				Exception.class,
				() -> this.club.reservarInstalacion("1", "Tenis", "X9", this.fecha,
						Time.valueOf("10:00:00"), Time.valueOf("11:00:00"), 2, "Sencillos")
				);

		assertEquals("Instalacion no existe", excepcion.getMessage());
	}
}
