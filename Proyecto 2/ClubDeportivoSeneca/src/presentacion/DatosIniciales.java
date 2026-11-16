package presentacion;

import java.sql.Date;
import java.sql.Time;

import logica.ArticuloTienda;
import logica.Bebida;
import logica.CanchaConjunto;
import logica.CanchaPadel;
import logica.CanchaTenis;
import logica.ClubDeportivoSeneca;
import logica.Futbol;
import logica.MesaTenisMesa;
import logica.Padel;
import logica.Snack;
import logica.Tenis;
import logica.TenisMesa;
import logica.UbicacionInventario;

/**
 * Datos con los que arranca el club cuando no existe el archivo
 * datos/clubDeportivoSeneca.bin (por ejemplo, la primera vez que se ejecuta).
 * Todos los usuarios tienen password 123.
 */
public class DatosIniciales {

	public static void cargar(ClubDeportivoSeneca club) {
		try {
			cargarUsuarios(club);
			cargarDisciplinasEInstalaciones(club);
			cargarEquipos(club);
			cargarProductos(club);
			cargarTurnos(club);
			cargarTorneos(club);
		} catch (Exception e) {
			System.out.println("No se pudieron cargar todos los datos iniciales: " + e.getMessage());
		}
	}

	private static void cargarUsuarios(ClubDeportivoSeneca club) throws Exception {
		club.crearAdministrador("admin", "123", "Administrador CDS", "A1");

		club.crearEntrenador("ancelotti", "123", "Carlo Ancelotti", "E1", "ANCE10", "Futbol");
		club.crearEntrenador("guardiola", "123", "Pep Guardiola", "E2", "PEP10", "Futbol");
		club.crearFisioterapeuta("fisio", "123", "Maria Fisio", "F1", "FISIO10", "General");

		club.crearSocio("pau", "123", "Paula", "S1", Date.valueOf("2010-01-01"));
		club.crearSocio("messi", "123", "Lionel Messi", "S2", Date.valueOf("2010-02-01"));
		club.crearSocio("cr7", "123", "Cristiano Ronaldo", "S3", Date.valueOf("2010-03-01"));
		club.crearSocio("neymar", "123", "Neymar Jr", "S4", Date.valueOf("2010-04-01"));
		club.crearSocio("mbappe", "123", "Kylian Mbappe", "S5", Date.valueOf("2010-05-01"));
		club.crearSocio("bellingham", "123", "Jude Bellingham", "S6", Date.valueOf("2010-06-01"));
		club.crearSocio("vinicius", "123", "Vinicius Jr", "S7", Date.valueOf("2010-07-01"));
		club.crearSocio("lamine", "123", "Lamine Yamal", "S8", Date.valueOf("2010-01-10"));
		club.crearSocio("pedri", "123", "Pedri", "S9", Date.valueOf("2010-02-10"));
		club.crearSocio("gavi", "123", "Gavi", "S10", Date.valueOf("2010-03-10"));
		club.crearSocio("lewandowski", "123", "Robert Lewandowski", "S11", Date.valueOf("2010-04-10"));
		club.crearSocio("raphinha", "123", "Raphinha", "S12", Date.valueOf("2010-05-10"));
		club.crearSocio("dejong", "123", "Frenkie de Jong", "S13", Date.valueOf("2010-06-10"));
		club.crearSocio("terstegen", "123", "Ter Stegen", "S14", Date.valueOf("2010-07-10"));
	}

	private static void cargarDisciplinasEInstalaciones(ClubDeportivoSeneca club) throws Exception {
		club.agregarDisciplina(new Tenis(2));
		club.agregarDisciplina(new Padel(2));
		club.agregarDisciplina(new TenisMesa(3));

		club.agregarInstalacion(new CanchaTenis("Cancha Rafa Nadal", 4, false));
		club.agregarInstalacion(new CanchaTenis("Cancha Federer", 4, true));
		club.agregarInstalacion(new CanchaPadel("Cancha Padel 1", 4, true));
		club.agregarInstalacion(new MesaTenisMesa("Mesa 1", 4, true));
		club.agregarInstalacion(new CanchaConjunto("Santiago Bernabeu", 22, false));

		club.inscribirAcademia("S1", "Tenis", "Intermedio");
		club.inscribirAcademia("S2", "Tenis", "Avanzado");
		club.inscribirAcademia("S3", "Tenis", "Intermedio");
		club.inscribirAcademia("S4", "Padel", "Principiante");
	}

	private static void cargarEquipos(ClubDeportivoSeneca club) throws Exception {
		club.crearEquipo("Real Madrid Sub17", 10, new Futbol(), "Sub17");
		club.inscribirJugadorEquipo("S1", "Real Madrid Sub17", "Delantera", 10);
		club.inscribirJugadorEquipo("S2", "Real Madrid Sub17", "Delantero", 30);
		club.inscribirJugadorEquipo("S3", "Real Madrid Sub17", "Delantero", 7);
		club.inscribirJugadorEquipo("S4", "Real Madrid Sub17", "Extremo", 11);
		club.inscribirJugadorEquipo("S5", "Real Madrid Sub17", "Delantero", 9);
		club.inscribirJugadorEquipo("S6", "Real Madrid Sub17", "Volante", 5);
		club.inscribirJugadorEquipo("S7", "Real Madrid Sub17", "Extremo", 20);

		club.crearEquipo("Barcelona Sub17", 10, new Futbol(), "Sub17");
		club.inscribirJugadorEquipo("S8", "Barcelona Sub17", "Extremo", 19);
		club.inscribirJugadorEquipo("S9", "Barcelona Sub17", "Volante", 8);
		club.inscribirJugadorEquipo("S10", "Barcelona Sub17", "Volante", 6);
		club.inscribirJugadorEquipo("S11", "Barcelona Sub17", "Delantero", 9);
		club.inscribirJugadorEquipo("S12", "Barcelona Sub17", "Extremo", 11);
		club.inscribirJugadorEquipo("S13", "Barcelona Sub17", "Volante", 21);
		club.inscribirJugadorEquipo("S14", "Barcelona Sub17", "Arquero", 1);
	}

	private static void cargarProductos(ClubDeportivoSeneca club) throws Exception {
		club.agregarProducto(new ArticuloTienda("Camiseta Real Madrid", 50000, "Ropa deportiva"));
		club.agregarProducto(new ArticuloTienda("Raqueta Wilson", 180000, "Implementos"));
		club.agregarProducto(new ArticuloTienda("Balon Adidas", 90000, "Implementos"));

		club.agregarProducto(new Bebida("Cafe Colombiano", 5000, "Caliente"));
		club.agregarProducto(new Bebida("Gatorade", 6000, "Fria"));
		club.agregarProducto(new Snack("Galletas Oreo", 15000, "Gluten"));

		club.agregarUbicacion(new UbicacionInventario("Tienda Principal"));
		club.agregarUbicacion(new UbicacionInventario("Bodega"));

		club.crearExistencia("Camiseta Real Madrid", "Tienda Principal", 10);
		club.crearExistencia("Raqueta Wilson", "Tienda Principal", 5);
		club.crearExistencia("Balon Adidas", "Tienda Principal", 8);
		club.crearExistencia("Camiseta Real Madrid", "Bodega", 30);
		club.crearExistencia("Balon Adidas", "Bodega", 20);
	}

	private static void cargarTurnos(ClubDeportivoSeneca club) throws Exception {
		club.asignarTurno("E1", "Lunes", Time.valueOf("08:00:00"), Time.valueOf("12:00:00"));
		club.asignarTurno("E2", "Lunes", Time.valueOf("08:00:00"), Time.valueOf("12:00:00"));
		club.asignarTurno("F1", "Lunes", Time.valueOf("08:00:00"), Time.valueOf("12:00:00"));
		club.asignarTurno("E1", "Miercoles", Time.valueOf("14:00:00"), Time.valueOf("18:00:00"));
	}

	private static void cargarTorneos(ClubDeportivoSeneca club) throws Exception {
		club.crearTorneo("T1", "Copa Recreativa de Tenis", "Intermedio", 8, Date.valueOf("2026-12-05"),
				"Recreativo", "Eliminacion directa", null, club.getDisciplina("Tenis"), 20);
		club.crearTorneo("T2", "Champions League Seneca", "Sub17", 8, Date.valueOf("2026-12-12"),
				"Competitivo", "Liga", new Futbol(), null, 100000);
	}
}
