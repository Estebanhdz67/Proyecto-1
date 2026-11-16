package test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.sql.Date;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import logica.ClubDeportivoSeneca;
import logica.Usuario;

public class UsuarioTest {
	private ClubDeportivoSeneca club;

	@BeforeEach
	public void setUp() throws Exception {
		this.club = new ClubDeportivoSeneca();
		this.club.crearSocio("pau", "123", "Paula", "1", Date.valueOf("2005-10-19"));
	}

	@Test
	public void iniciarSesionTest1() throws Exception {
		Usuario usuario = this.club.iniciarSesion("pau", "123");

		assertEquals("Paula", usuario.getNombre());
		assertEquals(usuario, this.club.getUsuarioActual());
	}

	@Test
	public void iniciarSesionTest2() {
		Exception excepcion = assertThrows(
				Exception.class,
				() -> this.club.iniciarSesion("pau", "999")
				);

		assertEquals("Password incorrecto", excepcion.getMessage());
		assertNull(this.club.getUsuarioActual());
	}

	@Test
	public void iniciarSesionTest3() {
		Exception excepcion = assertThrows(
				Exception.class,
				() -> this.club.iniciarSesion("nadie", "123")
				);

		assertEquals("El usuario no existe", excepcion.getMessage());
	}

	@Test
	public void cerrarSesionTest() throws Exception {
		this.club.iniciarSesion("pau", "123");
		this.club.cerrarSesion();

		assertNull(this.club.getUsuarioActual());
	}

	@Test
	public void crearUsuarioTest1() {
		Exception excepcion = assertThrows(
				Exception.class,
				() -> this.club.crearSocio("pau", "123", "Otra Paula", "2", Date.valueOf("2005-10-19"))
				);

		assertEquals("El login ya existe", excepcion.getMessage());
	}

	@Test
	public void crearUsuarioTest2() {
		Exception excepcion = assertThrows(
				Exception.class,
				() -> this.club.crearSocio("paula2", "123", "Otra Paula", "1", Date.valueOf("2005-10-19"))
				);

		assertEquals("El socio ya existe", excepcion.getMessage());
	}
}
