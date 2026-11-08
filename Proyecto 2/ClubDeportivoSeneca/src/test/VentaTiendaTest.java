package test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.sql.Date;
import java.util.HashMap;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import logica.ArticuloTienda;
import logica.ClubDeportivoSeneca;
import logica.ExistenciaInventario;
import logica.UbicacionInventario;

public class VentaTiendaTest {
	private ClubDeportivoSeneca club;
	private ExistenciaInventario existencia1;
	private ExistenciaInventario existencia2;

	@BeforeEach
	public void setUp() throws Exception {
		this.club = new ClubDeportivoSeneca();

		this.club.crearSocio("s1", "123", "Paula", "1", Date.valueOf("2005-10-19"));

		ArticuloTienda articulo1 = new ArticuloTienda("Camiseta", 50000, "Ropa");
		ArticuloTienda articulo2 = new ArticuloTienda("Gorra", 30000, "Accesorios");
		UbicacionInventario ubicacion = new UbicacionInventario("Tienda");

		this.club.agregarProducto(articulo1);
		this.club.agregarProducto(articulo2);
		this.club.agregarUbicacion(ubicacion);

		this.existencia1 = this.club.crearExistencia("Camiseta", "Tienda", 10);
		this.existencia2 = this.club.crearExistencia("Gorra", "Tienda", 1);
	}

	@Test
	public void ventaTiendaTest1() throws Exception {
		HashMap<String, Integer> cantidades = new HashMap<String, Integer>();
		cantidades.put("Camiseta", 2);
		cantidades.put("Gorra", 2);

		assertThrows(
				Exception.class,
				() -> this.club.registrarVentaTienda("1", "Tienda", cantidades, null, 0)
				);

		assertEquals(10, this.existencia1.getCantidad());
		assertEquals(1, this.existencia2.getCantidad());
	}
}
