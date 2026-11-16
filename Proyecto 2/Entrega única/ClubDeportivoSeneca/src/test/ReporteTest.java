package test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.sql.Date;
import java.sql.Time;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import logica.Bebida;
import logica.CanchaConjunto;
import logica.CanchaTenis;
import logica.ClubDeportivoSeneca;
import logica.Futbol;
import logica.PartidoInterno;
import logica.PartidoPractica;
import logica.Reserva;
import logica.SesionEntrenamiento;
import logica.Socio;
import logica.Tenis;

public class ReporteTest {
	private ClubDeportivoSeneca club;
	private SesionEntrenamiento sesion;
	private PartidoInterno partidoInterno;
	private PartidoPractica partidoPractica;

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

		this.sesion = this.club.crearSesion(Date.valueOf("2026-12-01"), Time.valueOf("08:00:00"), 90, "E1", "CC1", this.club.getEquipo("Equipo A"), null);
		this.partidoInterno = this.club.programarPartidoInterno(Date.valueOf("2026-12-02"), "Equipo A", "Equipo B");

		this.club.crearCompetencia("Liga ASCUN", "ASCUN", "2026-2", new Futbol(), "Mayores", "Liga");
		this.club.inscribirEquipoCompetencia("Liga ASCUN", "Equipo A", nomina);
		this.club.programarPartidoCompetencia(Date.valueOf("2026-12-05"), "Equipo A", "Liga ASCUN", "Rival FC", "Estadio");

		this.club.inscribirAcademia("A1", "Tenis", "Intermedio");
		this.club.inscribirAcademia("B1", "Tenis", "Intermedio");
		Reserva reserva = this.club.reservarInstalacion("A1", "Tenis", "CT1", Date.valueOf("2026-12-03"), Time.valueOf("10:00:00"), Time.valueOf("11:00:00"), 2, "Sencillos");
		jugadoresPractica.add(this.club.getSocio("A1"));
		jugadoresPractica.add(this.club.getSocio("B1"));
		this.partidoPractica = this.club.programarPartidoPractica(reserva, jugadoresPractica);
	}

	@Test
	public void reporteDeportivoTest1() {
		String reporte = this.club.generarReporteDeportivo();

		assertTrue(reporte.contains("Sesiones\t0"));
		assertTrue(reporte.contains("Partidos internos\t0"));
		assertTrue(reporte.contains("Partidos de competencia\t0"));
		assertTrue(reporte.contains("Partidos de practica\t0"));
		assertTrue(reporte.contains("Asistencia\nNo hay registros"));
		assertTrue(reporte.contains("Resultados\nNo hay registros"));
		assertTrue(reporte.contains("Rankings\nNo hay registros"));
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
	public void reporteAsistenciaTest() throws Exception {
		this.crearActividades();
		this.club.convocarEquipo(this.sesion, this.club.getEquipo("Equipo A"));
		this.club.registrarAsistencia(this.sesion, "A1", "Asistio");
		this.club.registrarAsistencia(this.sesion, "A2", "Tarde");
		this.club.registrarAsistencia(this.sesion, "A3", "Falto");

		String reporte = this.club.generarReporteDeportivo();

		assertTrue(reporte.contains("Asistio\t1"));
		assertTrue(reporte.contains("Tarde\t1"));
		assertTrue(reporte.contains("Falto\t1"));
		assertTrue(reporte.contains("Pendiente\t4"));
		assertTrue(reporte.contains("Porcentaje de asistencia\t67%"));
	}

	@Test
	public void reporteResultadosTest() throws Exception {
		this.crearActividades();
		this.club.registrarResultadoInterno(this.partidoInterno, "2-1");
		this.club.registrarResultadoPractica(this.partidoPractica, "6-4", this.club.getSocio("A1"), this.club.getSocio("B1"));

		String reporte = this.club.generarReporteDeportivo();

		assertTrue(reporte.contains("Interno\tEquipo A vs Equipo B\t2-1"));
		assertTrue(reporte.contains("Competencia\tEquipo A vs Rival FC\tSin resultado"));
		assertTrue(reporte.contains("Practica\tJugador A1, Jugador B1\t6-4"));
	}

	@Test
	public void reporteRankingsTest() throws Exception {
		this.crearActividades();
		this.club.registrarResultadoPractica(this.partidoPractica, "6-4", this.club.getSocio("A1"), this.club.getSocio("B1"));

		String reporte = this.club.generarReporteDeportivo();

		assertTrue(reporte.contains("Tenis\tJugador A1\t1020.0"));
		assertTrue(reporte.contains("Tenis\tJugador B1\t980.0"));
		assertTrue(reporte.indexOf("Jugador A1\t1020.0") < reporte.indexOf("Jugador B1\t980.0"));
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

	@Test
	public void reporteVentasPeriodoTest1() throws Exception {
		this.club.crearSocio("s1", "123", "Paula", "1", Date.valueOf("2005-10-19"));
		this.club.registrarMensualidad("1", "Enero", 150000, Date.valueOf("2026-01-31"));
		this.club.registrarMensualidad("1", "Febrero", 150000, Date.valueOf("2026-02-01"));
		this.club.registrarMensualidad("1", "Febrero extra", 50000, Date.valueOf("2026-02-28"));
		this.club.registrarMensualidad("1", "Marzo", 150000, Date.valueOf("2026-03-01"));

		String reporte = this.club.generarReporteVentas(Date.valueOf("2026-02-01"), Date.valueOf("2026-02-28"));

		assertTrue(reporte.contains("Periodo\t2026-02-01 a 2026-02-28"));
		assertTrue(reporte.contains("Mensualidades\t200000.0"));
		assertTrue(reporte.contains("Total\t200000.0"));
	}

	@Test
	public void reporteVentasPeriodoTest2() throws Exception {
		HashMap<String, Integer> cantidades = new HashMap<String, Integer>();
		cantidades.put("Cafe", 2);
		Date hoy = Date.valueOf(LocalDate.now());

		this.club.crearSocio("s1", "123", "Paula", "1", Date.valueOf("2005-10-19"));
		this.club.agregarProducto(new Bebida("Cafe", 5000, "Caliente"));
		this.club.registrarVentaCafeteria("1", cantidades, 0, 0);

		String reporteHoy = this.club.generarReporteVentas(hoy, hoy);
		String reporteAnterior = this.club.generarReporteVentas(Date.valueOf("2020-01-01"), Date.valueOf("2020-12-31"));

		assertTrue(reporteHoy.contains("Ventas\t1"));
		assertTrue(reporteHoy.contains("Cafeteria\t10800.0"));
		assertTrue(reporteAnterior.contains("Ventas\t0"));
		assertTrue(reporteAnterior.contains("Cafeteria\t0.0"));
	}

	@Test
	public void reporteVentasPeriodoTest3() {
		Exception excepcion = assertThrows(
				Exception.class,
				() -> this.club.generarReporteVentas(Date.valueOf("2026-03-01"), Date.valueOf("2026-02-01"))
				);

		assertEquals("Periodo invalido", excepcion.getMessage());
	}

	@Test
	public void historialTest1() {
		assertEquals("No hay registros\n", this.club.consultarHistorial());
	}

	@Test
	public void historialTest2() throws Exception {
		this.crearActividades();

		String historial = this.club.consultarHistorial();

		assertTrue(historial.contains("Reservas\t1\n2026-12-03\t10:00:00-11:00:00\tCT1\tJugador A1\tTenis\tSencillos"));
		assertTrue(historial.contains("Partidos de practica\t1\n2026-12-03\tTenis\tSencillos\tJugador A1, Jugador B1\tSin resultado"));
		assertTrue(historial.contains("Partidos internos\t1\n2026-12-02\tEquipo A vs Equipo B\tSin resultado"));
		assertTrue(historial.contains("Partidos de competencia\t1\n2026-12-05\tLiga ASCUN\tEquipo A vs Rival FC\tEstadio\tSin resultado"));
		assertTrue(historial.contains("Sesiones de entrenamiento\t1\n2026-12-01\t08:00:00\t90 min\tEntrenador 1\tCC1\tEquipo A"));
	}

	@Test
	public void historialTest3() throws Exception {
		this.club.crearEntrenador("e1", "123", "Entrenador 1", "E1", "D1", "Tenis");
		this.club.agregarInstalacion(new CanchaTenis("CT1", 4, false));
		this.club.agregarDisciplina(new Tenis(2));
		this.club.crearSesion(Date.valueOf("2026-12-01"), Time.valueOf("08:00:00"), 60, "E1", "CT1", null, this.club.getDisciplina("Tenis"));

		String historial = this.club.consultarHistorial();

		assertTrue(historial.contains("Reservas\t0\nNo hay registros"));
		assertTrue(historial.contains("Partidos internos\t0\nNo hay registros"));
		assertTrue(historial.contains("Sesiones de entrenamiento\t1\n2026-12-01\t08:00:00\t60 min\tEntrenador 1\tCT1\tTenis"));
	}
}
