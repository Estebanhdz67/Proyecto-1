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
import logica.VentaTienda;

public class VentaTiendaTest {
	private ClubDeportivoSeneca club;
	private ExistenciaInventario existencia1;
	private ExistenciaInventario existencia2;

	@BeforeEach
	public void setUp() throws Exception {
		this.club = new ClubDeportivoSeneca();

		this.club.crearSocio("s1", "123", "Paula", "1", Date.valueOf("2005-10-19"));
		this.club.crearEntrenador("e1", "123", "Entrenador 1", "E1", "DESC10", "Futbol");

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

	@Test
	public void ventaTiendaTest2() throws Exception {
		HashMap<String, Integer> cantidades = new HashMap<String, Integer>();
		cantidades.put("Camiseta", 2);
		cantidades.put("Gorra", 1);

		VentaTienda venta = this.club.registrarVentaTienda("1", "Tienda", cantidades, null, 0);

		assertEquals(2, venta.getDetalles().size());
		assertEquals(130000, venta.calcularSubtotal(), 0.01);
		assertEquals(0, venta.getDescuento(), 0.01);
		assertEquals(154700, venta.getTotal(), 0.01);
		assertEquals(8, this.existencia1.getCantidad());
		assertEquals(0, this.existencia2.getCantidad());
		assertEquals(1, this.club.getVentas().size());
	}

	@Test
	public void codigoEmpleadoTest1() throws Exception {
		HashMap<String, Integer> cantidades = new HashMap<String, Integer>();
		cantidades.put("Camiseta", 1);

		VentaTienda venta = this.club.registrarVentaTienda("E1", "Tienda", cantidades, "DESC10", 0);

		assertEquals(7500, venta.getDescuento(), 0.01);
		assertEquals(50575, venta.getTotal(), 0.01);
	}

	@Test
	public void codigoEmpleadoTest2() throws Exception {
		HashMap<String, Integer> cantidades = new HashMap<String, Integer>();
		cantidades.put("Camiseta", 1);

		VentaTienda venta = this.club.registrarVentaTienda("1", "Tienda", cantidades, "DESC10", 0);

		assertEquals(4000, venta.getDescuento(), 0.01);
		assertEquals(54740, venta.getTotal(), 0.01);
	}

	@Test
	public void codigoEmpleadoTest3() throws Exception {
		HashMap<String, Integer> cantidades = new HashMap<String, Integer>();
		cantidades.put("Camiseta", 1);

		VentaTienda venta = this.club.registrarVentaTienda("1", "Tienda", cantidades, "CODIGOFALSO", 0);

		assertEquals(0, venta.getDescuento(), 0.01);
		assertEquals(59500, venta.getTotal(), 0.01);
	}

	@Test
	public void puntosTest1() throws Exception {
		HashMap<String, Integer> cantidades = new HashMap<String, Integer>();
		cantidades.put("Camiseta", 2);
		cantidades.put("Gorra", 1);

		this.club.registrarVentaTienda("1", "Tienda", cantidades, null, 0);

		assertEquals(3094, this.club.getSocio("1").getCuentaFidelidad().getPuntos());
		assertEquals(1, this.club.getSocio("1").getCuentaFidelidad().getMovimientos().size());
	}

	@Test
	public void puntosTest2() throws Exception {
		HashMap<String, Integer> primeraCompra = new HashMap<String, Integer>();
		primeraCompra.put("Camiseta", 2);
		primeraCompra.put("Gorra", 1);
		this.club.registrarVentaTienda("1", "Tienda", primeraCompra, null, 0);

		HashMap<String, Integer> segundaCompra = new HashMap<String, Integer>();
		segundaCompra.put("Camiseta", 1);
		VentaTienda venta = this.club.registrarVentaTienda("1", "Tienda", segundaCompra, null, 1000);

		assertEquals(1000, venta.getPuntosRedimidos());
		assertEquals(58310, venta.getTotal(), 0.01);
		assertEquals(3260, this.club.getSocio("1").getCuentaFidelidad().getPuntos());
	}

	@Test
	public void puntosTest3() throws Exception {
		HashMap<String, Integer> cantidades = new HashMap<String, Integer>();
		cantidades.put("Camiseta", 1);

		Exception excepcion = assertThrows(
				Exception.class,
				() -> this.club.registrarVentaTienda("1", "Tienda", cantidades, null, 500)
				);

		assertEquals("Puntos insuficientes", excepcion.getMessage());
		assertEquals(10, this.existencia1.getCantidad());
		assertEquals(0, this.club.getVentas().size());
	}
}
