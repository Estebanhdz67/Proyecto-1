package test;

import static org.junit.jupiter.api.Assertions.assertTrue;

import java.sql.Date;
import java.sql.Time;
import java.util.ArrayList;
import java.util.HashMap;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import logica.Bebida;
import logica.CanchaConjunto;
import logica.CanchaTenis;
import logica.ClubDeportivoSeneca;
import logica.Futbol;
import logica.Reserva;
import logica.Socio;
import logica.Tenis;

public class ReporteTest {
	private ClubDeportivoSeneca club;

	@BeforeEach
	public void setUp() throws Exception {
		this.club = new ClubDeportivoSeneca();
	}

	private void crearActividades() throws Exception {
		ArrayList<Socio> nomina = new ArrayList<Socio>();
		ArrayList<Socio> jugadoresPractica = new ArrayList<Socio>();

		this.club.crearEntrenador("e1", "123", "Entrenador 1", "E1", "D1", "Futbol");
		this.club.agregarInstalacion(new CanchaConjunto("CC1", 30, false));
		this.club.agregarInstalacion(new CanchaTenis("CT1", 4, false));
		this.club.agregarDisciplina(new Tenis(2));

		this.club.crearEquipo("Equipo A", 10, new Futbol(), "Mayores");
		this.club.crearEquipo("Equipo B", 10, new Futbol(), "Mayores");

		for(int i = 1; i <= 7; i++) {
			this.club.crearSocio("a" + i, "123", "Jugador A" + i, "A" + i, Date.valueOf("2000-01-10"));
			this.club.inscribirJugadorEquipo("A" + i, "Equipo A", "Campo", i);
			nomina.add(this.club.getSocio("A" + i));
			this.club.crearSocio("b" + i, "123", "Jugador B" + i, "B" + i, Date.valueOf("2000-01-10"));
			this.club.inscribirJugadorEquipo("B" + i, "Equipo B", "Campo", i);
		}

		this.club.crearSesion(Date.valueOf("2026-12-01"), Time.valueOf("08:00:00"), 90, "E1", "CC1", this.club.getEquipo("Equipo A"), null);
		this.club.programarPartidoInterno(Date.valueOf("2026-12-02"), "Equipo A", "Equipo B");

		this.club.crearCompetencia("Liga ASCUN", "ASCUN", "2026-2", new Futbol(), "Mayores", "Liga");
		this.club.inscribirEquipoCompetencia("Liga ASCUN", "Equipo A", nomina);
		this.club.programarPartidoCompetencia(Date.valueOf("2026-12-05"), "Equipo A", "Liga ASCUN", "Rival FC", "Estadio");

		this.club.inscribirAcademia("A1", "Tenis", "Intermedio");
		this.club.inscribirAcademia("B1", "Tenis", "Intermedio");
		Reserva reserva = this.club.reservarInstalacion("A1", "Tenis", "CT1", Date.valueOf("2026-12-03"), Time.valueOf("10:00:00"), Time.valueOf("11:00:00"), 2, "Sencillos");
		jugadoresPractica.add(this.club.getSocio("A1"));
		jugadoresPractica.add(this.club.getSocio("B1"));
		this.club.programarPartidoPractica(reserva, jugadoresPractica);
	}

	@Test
	public void reporteDeportivoTest1() {
		String reporte = this.club.generarReporteDeportivo();

		assertTrue(reporte.contains("Sesiones\t0"));
		assertTrue(reporte.contains("Partidos internos\t0"));
		assertTrue(reporte.contains("Partidos de competencia\t0"));
		assertTrue(reporte.contains("Partidos de practica\t0"));
	}

	@Test
	public void reporteDeportivoTest2() throws Exception {
		this.crearActividades();

		String reporte = this.club.generarReporteDeportivo();

		assertTrue(reporte.contains("Sesiones\t1"));
		assertTrue(reporte.contains("Partidos internos\t1"));
		assertTrue(reporte.contains("Partidos de competencia\t1"));
		assertTrue(reporte.contains("Partidos de practica\t1"));
	}

	@Test
	public void historialTest1() {
		String historial = this.club.consultarHistorial();

		assertTrue(historial.contains("Reservas\t0"));
		assertTrue(historial.contains("Sesiones de entrenamiento\t0"));
	}

	@Test
	public void historialTest2() throws Exception {
		this.crearActividades();

		String historial = this.club.consultarHistorial();

		assertTrue(historial.contains("Reservas\t1"));
		assertTrue(historial.contains("Partidos de practica\t1"));
		assertTrue(historial.contains("Partidos internos\t1"));
		assertTrue(historial.contains("Partidos de competencia\t1"));
		assertTrue(historial.contains("Sesiones de entrenamiento\t1"));
	}

	@Test
	public void reporteVentasTest() throws Exception {
		HashMap<String, Integer> cantidades = new HashMap<String, Integer>();
		cantidades.put("Cafe", 2);

		this.club.crearSocio("s1", "123", "Paula", "1", Date.valueOf("2005-10-19"));
		this.club.agregarProducto(new Bebida("Cafe", 5000, "Caliente"));
		this.club.registrarVentaCafeteria("1", cantidades, 0, 0);

		String reporte = this.club.generarReporteVentas();

		assertTrue(reporte.contains("Tienda\t0.0"));
		assertTrue(reporte.contains("Cafeteria\t10800.0"));
	}
}
