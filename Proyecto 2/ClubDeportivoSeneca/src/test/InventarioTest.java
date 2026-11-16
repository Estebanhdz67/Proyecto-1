package test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import logica.ArticuloTienda;
import logica.ClubDeportivoSeneca;
import logica.UbicacionInventario;

public class InventarioTest {
	private ClubDeportivoSeneca club;

	@BeforeEach
	public void setUp() throws Exception {
		this.club = new ClubDeportivoSeneca();

		this.club.crearAdministrador("admin", "123", "Admin", "A1");

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
}
