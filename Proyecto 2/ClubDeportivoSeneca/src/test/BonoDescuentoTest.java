package test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.sql.Date;
import java.util.HashMap;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import logica.ArticuloTienda;
import logica.Bebida;
import logica.BonoDescuento;
import logica.ClubDeportivoSeneca;
import logica.Padel;
import logica.Socio;
import logica.UbicacionInventario;
import logica.VentaCafeteria;
import logica.VentaTienda;

public class BonoDescuentoTest {
	private ClubDeportivoSeneca club;
	private Socio ganador;
	private BonoDescuento bono;
	private HashMap<String, Integer> compraTienda;
	private HashMap<String, Integer> compraCafeteria;

	@BeforeEach
	public void setUp() throws Exception {
		this.club = new ClubDeportivoSeneca();

		this.club.crearSocio("s1", "123", "Paula", "S1", Date.valueOf("2005-10-19"));
		this.club.crearSocio("s2", "123", "Juan", "S2", Date.valueOf("2005-01-10"));
		this.club.crearEntrenador("e1", "123", "Entrenador 1", "E1", "DESC10", "Futbol");
		this.ganador = this.club.getSocio("S1");

		this.club.agregarProducto(new ArticuloTienda("Camiseta", 50000, "Ropa"));
		this.club.agregarProducto(new Bebida("Cafe", 5000, "Caliente"));
		this.club.agregarUbicacion(new UbicacionInventario("Tienda"));
		this.club.crearExistencia("Camiseta", "Tienda", 10);

		this.club.crearTorneo("T1", "Torneo Padel", "Intermedio", 5, Date.valueOf("2026-11-09"), "Recreativo", "Liga", null, new Padel(4), 0.10);
		this.club.inscribirParticipanteTorneo("T1", this.ganador, false);
		this.club.iniciarTorneo("T1");
		this.club.finalizarTorneo("T1");
		this.bono = this.club.generarBonoTorneo("T1", this.ganador);

		this.compraTienda = new HashMap<String, Integer>();
		this.compraTienda.put("Camiseta", 1);

		this.compraCafeteria = new HashMap<String, Integer>();
		this.compraCafeteria.put("Cafe", 2);
	}

	@Test
	public void bonoGuardadoTest() throws Exception {
		assertEquals(1, this.club.getBonos(this.ganador).size());
		assertEquals(this.bono, this.club.getBono("BONO-T1-1"));
		assertTrue(this.club.consultarBonos(this.ganador).contains("BONO-T1-1\t0.1\tDisponible"));
		assertEquals(0, this.club.getBonos(this.club.getSocio("S2")).size());
	}

	@Test
	public void bonoTiendaTest() throws Exception {
		VentaTienda venta = this.club.registrarVentaTienda("S1", "Tienda", this.compraTienda, null, 0, "BONO-T1-1");

		assertEquals(5000, venta.getDescuento(), 0.01);
		assertEquals(53550, venta.getTotal(), 0.01);
		assertEquals("BONO-T1-1", venta.getCodigoDescuentoUsado());
		assertTrue(this.bono.estaUsado());
		assertTrue(this.club.consultarBonos(this.ganador).contains("Usado"));
		assertEquals(9, this.club.buscarExistencia("Camiseta", "Tienda").getCantidad());
	}

	@Test
	public void bonoCafeteriaTest() throws Exception {
		VentaCafeteria venta = this.club.registrarVentaCafeteria("S1", this.compraCafeteria, 0, 0, "BONO-T1-1");

		assertEquals(1000, venta.getDescuento(), 0.01);
		assertEquals(9720, venta.getTotal(), 0.01);
		assertTrue(this.bono.estaUsado());
	}

	@Test
	public void bonoCodigoEmpleadoTest() throws Exception {
		Exception excepcion = assertThrows(
				Exception.class,
				() -> this.club.registrarVentaTienda("S1", "Tienda", this.compraTienda, "DESC10", 0, "BONO-T1-1")
				);

		assertEquals("El bono no se puede acumular con otra promocion", excepcion.getMessage());
		assertFalse(this.bono.estaUsado());
		assertEquals(10, this.club.buscarExistencia("Camiseta", "Tienda").getCantidad());
		assertEquals(0, this.club.getVentas().size());
	}

	@Test
	public void bonoPuntosTiendaTest() throws Exception {
		this.ganador.getCuentaFidelidad().generarPuntos(500);

		Exception excepcion = assertThrows(
				Exception.class,
				() -> this.club.registrarVentaTienda("S1", "Tienda", this.compraTienda, null, 100, "BONO-T1-1")
				);

		assertEquals("El bono no se puede acumular con otra promocion", excepcion.getMessage());
		assertFalse(this.bono.estaUsado());
		assertEquals(500, this.ganador.getCuentaFidelidad().getPuntos());
	}

	@Test
	public void bonoPuntosCafeteriaTest() throws Exception {
		this.ganador.getCuentaFidelidad().generarPuntos(500);

		Exception excepcion = assertThrows(
				Exception.class,
				() -> this.club.registrarVentaCafeteria("S1", this.compraCafeteria, 0, 100, "BONO-T1-1")
				);

		assertEquals("El bono no se puede acumular con otra promocion", excepcion.getMessage());
		assertFalse(this.bono.estaUsado());
		assertEquals(500, this.ganador.getCuentaFidelidad().getPuntos());
	}

	@Test
	public void bonoUsadoTest() throws Exception {
		this.club.registrarVentaTienda("S1", "Tienda", this.compraTienda, null, 0, "BONO-T1-1");

		Exception excepcion = assertThrows(
				Exception.class,
				() -> this.club.registrarVentaCafeteria("S1", this.compraCafeteria, 0, 0, "BONO-T1-1")
				);

		assertEquals("El bono ya fue usado", excepcion.getMessage());
		assertEquals(1, this.club.getVentas().size());
	}

	@Test
	public void bonoOtroUsuarioTest() {
		Exception excepcion = assertThrows(
				Exception.class,
				() -> this.club.registrarVentaTienda("S2", "Tienda", this.compraTienda, null, 0, "BONO-T1-1")
				);

		assertEquals("El bono no pertenece al participante", excepcion.getMessage());
		assertFalse(this.bono.estaUsado());
	}

	@Test
	public void bonoInexistenteTest() {
		Exception excepcion = assertThrows(
				Exception.class,
				() -> this.club.registrarVentaCafeteria("S1", this.compraCafeteria, 0, 0, "BONO-X")
				);

		assertEquals("Bono no existe", excepcion.getMessage());
	}

	@Test
	public void ventaSinBonoTest() throws Exception {
		VentaTienda venta = this.club.registrarVentaTienda("E1", "Tienda", this.compraTienda, "DESC10", 0);

		assertEquals(7500, venta.getDescuento(), 0.01);
		assertFalse(this.bono.estaUsado());
	}
}
