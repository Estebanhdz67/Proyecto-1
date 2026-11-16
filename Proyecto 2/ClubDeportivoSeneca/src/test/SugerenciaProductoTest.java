package test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import logica.Bebida;
import logica.ClubDeportivoSeneca;
import logica.SugerenciaProducto;

public class SugerenciaProductoTest {
	private ClubDeportivoSeneca club;

	@BeforeEach
	public void setUp() throws Exception {
		this.club = new ClubDeportivoSeneca();

		this.club.crearAdministrador("admin", "123", "Admin", "A1");
		this.club.crearEntrenador("e1", "123", "Entrenador 1", "E1", "D1", "Futbol");
	}

	@Test
	public void sugerirProductoTest1() throws Exception {
		SugerenciaProducto sugerencia = this.club.sugerirProducto("E1", "Bebida isotonica");

		assertEquals("Pendiente", sugerencia.getEstado());
		assertEquals(1, this.club.getEmpleado("E1").getSugerencias().size());
	}

	@Test
	public void sugerirProductoTest2() {
		Exception excepcion = assertThrows(
				Exception.class,
				() -> this.club.sugerirProducto("E99", "Bebida isotonica")
				);

		assertEquals("Empleado no existe", excepcion.getMessage());
	}

	@Test
	public void revisarSugerenciaTest1() throws Exception {
		SugerenciaProducto sugerencia = this.club.sugerirProducto("E1", "Bebida isotonica");

		this.club.revisarSugerencia(sugerencia, "A1", true, new Bebida("Isotonica", 4000, "Fria"));

		assertEquals("Aprobada", sugerencia.getEstado());
		assertTrue(this.club.getProductos().containsKey("Isotonica"));
	}

	@Test
	public void revisarSugerenciaTest2() throws Exception {
		SugerenciaProducto sugerencia = this.club.sugerirProducto("E1", "Bebida isotonica");

		this.club.revisarSugerencia(sugerencia, "A1", false, null);

		assertEquals("Rechazada", sugerencia.getEstado());
		assertEquals(0, this.club.getProductos().size());
	}
}
