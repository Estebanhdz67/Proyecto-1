package test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.sql.Date;
import java.util.HashMap;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import logica.ArticuloTienda;
import logica.Bebida;
import logica.ClubDeportivoSeneca;
import logica.Snack;
import logica.VentaCafeteria;

public class VentaCafeteriaTest {
	private ClubDeportivoSeneca club;

	@BeforeEach
	public void setUp() throws Exception {
		this.club = new ClubDeportivoSeneca();

		this.club.crearSocio("s1", "123", "Paula", "1", Date.valueOf("2005-10-19"));

		this.club.agregarProducto(new Bebida("Cafe", 5000, "Caliente"));
		this.club.agregarProducto(new Snack("Galleta", 3000, "Gluten"));
		this.club.agregarProducto(new ArticuloTienda("Camiseta", 50000, "Ropa"));
	}

	@Test
	public void ventaCafeteriaTest1() throws Exception {
		HashMap<String, Integer> cantidades = new HashMap<String, Integer>();
		cantidades.put("Cafe", 2);
		cantidades.put("Galleta", 1);

		VentaCafeteria venta = this.club.registrarVentaCafeteria("1", cantidades, 0.10, 0);

		assertEquals(15340, venta.getTotal(), 0.01);
		assertEquals(1, this.club.getVentas().size());
	}

	@Test
	public void ventaCafeteriaTest2() throws Exception {
		HashMap<String, Integer> cantidades = new HashMap<String, Integer>();
		cantidades.put("Cafe", 2);
		cantidades.put("Galleta", 1);

		this.club.registrarVentaCafeteria("1", cantidades, 0.10, 0);

		assertEquals(280, this.club.getSocio("1").getCuentaFidelidad().getPuntos());
	}

	@Test
	public void ventaCafeteriaTest3() throws Exception {
		HashMap<String, Integer> primeraCompra = new HashMap<String, Integer>();
		primeraCompra.put("Cafe", 2);
		primeraCompra.put("Galleta", 1);
		this.club.registrarVentaCafeteria("1", primeraCompra, 0.10, 0);

		HashMap<String, Integer> segundaCompra = new HashMap<String, Integer>();
		segundaCompra.put("Cafe", 1);
		VentaCafeteria venta = this.club.registrarVentaCafeteria("1", segundaCompra, 0, 200);

		assertEquals(5184, venta.getTotal(), 0.01);
		assertEquals(200, venta.getPuntosRedimidos());
		assertEquals(183, this.club.getSocio("1").getCuentaFidelidad().getPuntos());
	}

	@Test
	public void ventaCafeteriaTest4() throws Exception {
		HashMap<String, Integer> cantidades = new HashMap<String, Integer>();
		cantidades.put("Cafe", 1);

		Exception excepcion = assertThrows(
				Exception.class,
				() -> this.club.registrarVentaCafeteria("1", cantidades, 0, 500)
				);

		assertEquals("Puntos insuficientes", excepcion.getMessage());
		assertEquals(0, this.club.getVentas().size());
	}

	@Test
	public void ventaCafeteriaTest5() {
		HashMap<String, Integer> cantidades = new HashMap<String, Integer>();
		cantidades.put("Camiseta", 1);

		Exception excepcion = assertThrows(
				Exception.class,
				() -> this.club.registrarVentaCafeteria("1", cantidades, 0, 0)
				);

		assertEquals("No es producto de cafeteria", excepcion.getMessage());
	}

	@Test
	public void ventaCafeteriaTest6() {
		HashMap<String, Integer> cantidades = new HashMap<String, Integer>();
		cantidades.put("Cafe", 1);

		Exception excepcion = assertThrows(
				Exception.class,
				() -> this.club.registrarVentaCafeteria("99", cantidades, 0, 0)
				);

		assertEquals("Comprador no existe", excepcion.getMessage());
	}
}
