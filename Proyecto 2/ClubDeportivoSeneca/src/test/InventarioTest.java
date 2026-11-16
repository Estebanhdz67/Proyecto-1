package test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.sql.Date;
import java.util.HashMap;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import logica.ArticuloTienda;
import logica.ClubDeportivoSeneca;
import logica.UbicacionInventario;
import logica.VentaTienda;

public class InventarioTest {
	private ClubDeportivoSeneca club;

	@BeforeEach
	public void setUp() throws Exception {
		this.club = new ClubDeportivoSeneca();

		this.club.crearAdministrador("admin", "123", "Admin", "A1");
		this.club.crearSocio("s1", "123", "Paula", "1", Date.valueOf("2005-10-19"));
		this.club.crearEntrenador("e1", "123", "Entrenador 1", "E1", "DESC10", "Futbol");

		this.club.agregarProducto(new ArticuloTienda("Camiseta", 50000, "Ropa"));
		this.club.agregarUbicacion(new UbicacionInventario("Tienda"));
		this.club.agregarUbicacion(new UbicacionInventario("Bodega"));

		this.club.crearExistencia("Camiseta", "Tienda", 10);
	}

	@Test
	public void moverInventarioTest1() throws Exception {
		this.club.moverInventario("Camiseta", "Tienda", "Bodega", 4, "A1");

		assertEquals(6, this.club.buscarExistencia("Camiseta", "Tienda").getCantidad());
		assertEquals(4, this.club.buscarExistencia("Camiseta", "Bodega").getCantidad());
	}

	@Test
	public void moverInventarioTest2() throws Exception {
		Exception excepcion = assertThrows(
				Exception.class,
				() -> this.club.moverInventario("Camiseta", "Tienda", "Bodega", 20, "A1")
				);

		assertEquals("No hay suficientes unidades", excepcion.getMessage());
		assertEquals(10, this.club.buscarExistencia("Camiseta", "Tienda").getCantidad());
	}

	@Test
	public void reabastecerTest1() throws Exception {
		this.club.reabastecer("Camiseta", "Tienda", 5, "A1");

		assertEquals(15, this.club.buscarExistencia("Camiseta", "Tienda").getCantidad());
	}

	@Test
	public void reabastecerTest2() throws Exception {
		this.club.reabastecer("Camiseta", "Bodega", 3, "A1");

		assertEquals(3, this.club.buscarExistencia("Camiseta", "Bodega").getCantidad());
	}

	@Test
	public void ventaTiendaEmpleadoTest() throws Exception {
		HashMap<String, Integer> cantidades = new HashMap<String, Integer>();
		cantidades.put("Camiseta", 1);

		VentaTienda venta = this.club.registrarVentaTienda("E1", "Tienda", cantidades, "DESC10", 0);

		assertEquals(7500, venta.getDescuento(), 0.01);
		assertEquals(50575, venta.getTotal(), 0.01);
		assertEquals(9, this.club.buscarExistencia("Camiseta", "Tienda").getCantidad());
	}

	@Test
	public void ventaTiendaSocioCodigoTest() throws Exception {
		HashMap<String, Integer> cantidades = new HashMap<String, Integer>();
		cantidades.put("Camiseta", 1);

		VentaTienda venta = this.club.registrarVentaTienda("1", "Tienda", cantidades, "DESC10", 0);

		assertEquals(4000, venta.getDescuento(), 0.01);
		assertEquals(54740, venta.getTotal(), 0.01);
		assertEquals(1094, this.club.getSocio("1").getCuentaFidelidad().getPuntos());
	}
}
