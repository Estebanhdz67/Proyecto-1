package test;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.sql.Date;
import java.util.ArrayList;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import logica.Competencia;
import logica.EliminacionDirecta;
import logica.Equipo;
import logica.FaseGrupos;
import logica.FormatoASCUN;
import logica.Futbol;
import logica.InscripcionCompetencia;
import logica.Liga;

public class FormatoCompetenciaTest {
	private ArrayList<InscripcionCompetencia> inscripciones;

	@BeforeEach
	public void setUp() {
		Futbol futbol = new Futbol();
		Liga liga = new Liga();
		Competencia competencia = new Competencia("Competencia", "Universidad", "2026", futbol, "Mayores", liga);

		Equipo equipo1 = new Equipo("Equipo A", 15, futbol, "Mayores");
		Equipo equipo2 = new Equipo("Equipo B", 15, futbol, "Mayores");
		Equipo equipo3 = new Equipo("Equipo C", 15, futbol, "Mayores");
		Equipo equipo4 = new Equipo("Equipo D", 15, futbol, "Mayores");

		this.inscripciones = new ArrayList<InscripcionCompetencia>();
		this.inscripciones.add(new InscripcionCompetencia(Date.valueOf("2026-11-01"), "Activa", equipo1, competencia));
		this.inscripciones.add(new InscripcionCompetencia(Date.valueOf("2026-11-01"), "Activa", equipo2, competencia));
		this.inscripciones.add(new InscripcionCompetencia(Date.valueOf("2026-11-01"), "Activa", equipo3, competencia));
		this.inscripciones.add(new InscripcionCompetencia(Date.valueOf("2026-11-01"), "Activa", equipo4, competencia));
	}

	@Test
	public void ligaTest() {
		Liga liga = new Liga();

		assertEquals("Liga", liga.getNombre());
		assertEquals(6, liga.generarEnfrentamientos(this.inscripciones).size());
	}

	@Test
	public void faseGruposTest() {
		FaseGrupos faseGrupos = new FaseGrupos();

		assertEquals("Fase de grupos", faseGrupos.getNombre());
		assertEquals(6, faseGrupos.generarEnfrentamientos(this.inscripciones).size());
	}

	@Test
	public void eliminacionDirectaTest() {
		EliminacionDirecta eliminacionDirecta = new EliminacionDirecta();

		assertEquals("Eliminacion directa", eliminacionDirecta.getNombre());
		assertEquals(2, eliminacionDirecta.generarEnfrentamientos(this.inscripciones).size());
	}

	@Test
	public void formatoASCUNTest() {
		FormatoASCUN formatoASCUN = new FormatoASCUN();

		assertEquals("ASCUN", formatoASCUN.getNombre());
		assertEquals(6, formatoASCUN.generarEnfrentamientos(this.inscripciones).size());
	}
}
