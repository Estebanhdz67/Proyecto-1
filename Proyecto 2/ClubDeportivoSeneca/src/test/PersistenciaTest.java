package test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.Date;
import java.util.ArrayList;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import logica.ArticuloTienda;
import logica.ClubDeportivoSeneca;
import logica.Futbol;
import logica.Padel;
import logica.UbicacionInventario;
import persistencia.ArchivoPlano;

public class PersistenciaTest {
	private ClubDeportivoSeneca club;
	private Path archivoDatos;
	private Path archivoUsuarios;
	private byte[] copiaDatos;
	private byte[] copiaUsuarios;

	@BeforeEach
	public void setUp() throws Exception {
		this.archivoDatos = Paths.get("datos", "clubDeportivoSeneca.bin");
		this.archivoUsuarios = Paths.get("datos", "usuarios.csv");
		this.copiaDatos = null;
		this.copiaUsuarios = null;

		Files.createDirectories(Paths.get("datos"));

		if(Files.exists(this.archivoDatos)) {
			this.copiaDatos = Files.readAllBytes(this.archivoDatos);
		}

		if(Files.exists(this.archivoUsuarios)) {
			this.copiaUsuarios = Files.readAllBytes(this.archivoUsuarios);
		}

		this.club = new ClubDeportivoSeneca();

		this.club.crearAdministrador("admin", "123", "Admin", "A1");
		this.club.crearSocio("pau", "123", "Paula", "S1", Date.valueOf("2000-10-19"));
		this.club.crearEquipo("Seneca FC", 10, new Futbol(), "Mayores");
		this.club.inscribirJugadorEquipo("S1", "Seneca FC", "Delantera", 9);
		this.club.registrarMensualidad("S1", "Enero", 150000, Date.valueOf("2026-01-05"));

		this.club.agregarProducto(new ArticuloTienda("Camiseta", 50000, "Ropa"));
		this.club.agregarUbicacion(new UbicacionInventario("Tienda"));
		this.club.crearExistencia("Camiseta", "Tienda", 10);

		this.club.crearTorneo("T1", "Torneo Padel", "Intermedio", 5, Date.valueOf("2026-11-09"), "Recreativo", "Liga", null, new Padel(4), 0.10);
		this.club.inscribirParticipanteTorneo("T1", this.club.getSocio("S1"), false);
		this.club.registrarEstudiante("100", "Laura", 1100);
		this.club.registrarExterno("CC200", "Carlos", 950);
	}

	@AfterEach
	public void tearDown() throws Exception {
		if(this.copiaDatos != null) {
			Files.write(this.archivoDatos, this.copiaDatos);
		}else {
			Files.deleteIfExists(this.archivoDatos);
		}

		if(this.copiaUsuarios != null) {
			Files.write(this.archivoUsuarios, this.copiaUsuarios);
		}else {
			Files.deleteIfExists(this.archivoUsuarios);
		}
	}

	@Test
	public void guardarCargarTest1() throws Exception {
		this.club.escribirSerializable();

		ClubDeportivoSeneca clubCargado = new ClubDeportivoSeneca();
		clubCargado.leerSerializable();

		assertEquals("Paula", clubCargado.getSocio("S1").getNombre());
		assertEquals("Admin", clubCargado.getAdministrador("A1").getNombre());
		assertEquals(1, clubCargado.getEquipo("Seneca FC").cantidadJugadoresActivos());
		assertEquals(1, clubCargado.getSocio("S1").getMensualidades().size());
		assertEquals(10, clubCargado.buscarExistencia("Camiseta", "Tienda").getCantidad());
		assertEquals("Torneo Padel", clubCargado.getTorneo("T1").getNombre());
		assertEquals("Laura", clubCargado.getEstudiante("100").getNombre());
		assertEquals("Carlos", clubCargado.getExterno("CC200").getNombre());
	}

	@Test
	public void guardarCargarTest2() throws Exception {
		this.club.escribirSerializable();

		ClubDeportivoSeneca clubCargado = new ClubDeportivoSeneca();
		clubCargado.leerSerializable();

		assertTrue(clubCargado.getEquipo("Seneca FC").contieneSocio(clubCargado.getSocio("S1")));
		assertTrue(clubCargado.estaInscritoTorneo("T1", clubCargado.getSocio("S1")));
		assertEquals(clubCargado.getUsuarios().get("pau"), clubCargado.getSocio("S1"));
	}

	@Test
	public void guardarCargarTest3() throws Exception {
		this.club.escribirSerializable();

		ClubDeportivoSeneca clubCargado = new ClubDeportivoSeneca();
		clubCargado.leerSerializable();
		clubCargado.crearSocio("juan", "123", "Juan", "S2", Date.valueOf("2001-03-15"));
		clubCargado.escribirSerializable();

		ClubDeportivoSeneca clubRecargado = new ClubDeportivoSeneca();
		clubRecargado.leerSerializable();

		assertEquals(2, clubRecargado.getSocios().size());
		assertEquals("Juan", clubRecargado.iniciarSesion("juan", "123").getNombre());
	}

	@Test
	public void escribirUsuariosTest() throws Exception {
		this.club.escribirUsuarios();

		ArrayList<String> lineas = new ArchivoPlano().leer("usuarios.csv");

		assertEquals(2, lineas.size());
		assertTrue(lineas.contains("pau,Paula"));
		assertTrue(lineas.contains("admin,Admin"));
	}

	@Test
	public void cargarSinArchivoTest() throws Exception {
		Files.deleteIfExists(this.archivoDatos);

		ClubDeportivoSeneca clubVacio = new ClubDeportivoSeneca();
		clubVacio.leerSerializable();

		assertEquals(0, clubVacio.getSocios().size());
		assertEquals(0, clubVacio.getTorneos().size());
	}
}
