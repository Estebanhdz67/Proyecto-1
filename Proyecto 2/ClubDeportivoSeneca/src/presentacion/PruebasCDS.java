package presentacion;

import java.sql.Date;
import java.sql.Time;
import java.util.ArrayList;
import java.util.HashMap;

import logica.ArticuloTienda;
import logica.Bebida;
import logica.CanchaConjunto;
import logica.CanchaTenis;
import logica.ClubDeportivoSeneca;
import logica.Competencia;
import logica.Futbol;
import logica.PartidoCompetencia;
import logica.PartidoInterno;
import logica.PartidoPractica;
import logica.Reserva;
import logica.SesionEntrenamiento;
import logica.Snack;
import logica.Socio;
import logica.Tenis;
import logica.UbicacionInventario;
import logica.VentaCafeteria;
import logica.VentaTienda;

public class PruebasCDS {

	public static void main(String[] args) throws Exception {
		ClubDeportivoSeneca club = new ClubDeportivoSeneca();

		club.crearAdministrador(
				"admin",
				"123",
				"Administrador CDS",
				"A1"
		);

		club.crearEntrenador(
				"ancelotti",
				"123",
				"Carlo Ancelotti",
				"E1",
				"ANCE10",
				"Futbol"
		);

		club.crearEntrenador(
				"guardiola",
				"123",
				"Pep Guardiola",
				"E2",
				"PEP10",
				"Futbol"
		);

		club.crearFisioterapeuta(
				"fisio",
				"123",
				"Maria Fisio",
				"F1",
				"FISIO10",
				"General"
		);

		club.crearSocio(
				"pau",
				"123",
				"Paula",
				"S1",
				Date.valueOf("2010-01-01")
		);

		club.crearSocio(
				"messi",
				"123",
				"Lionel Messi",
				"S2",
				Date.valueOf("2010-02-01")
		);

		System.out.println("PRUEBA LOGIN");

		System.out.println(
				club.iniciarSesion(
						"pau",
						"123"
				)
		);

		try {
			club.iniciarSesion(
					"pau",
					"mal"
			);
		}catch(Exception e) {
			System.out.println(e.getMessage());
		}

		System.out.println("\nPRUEBA EQUIPOS");

		Futbol futbol = new Futbol();

		club.crearEquipo(
				"Real Madrid Sub17",
				10,
				futbol,
				"Sub17"
		);

		club.inscribirJugadorEquipo(
				"S1",
				"Real Madrid Sub17",
				"Delantera",
				10
		);

		club.inscribirJugadorEquipo(
				"S2",
				"Real Madrid Sub17",
				"Delantero",
				30
		);

		System.out.println(
				club.consultarEquipos()
		);

		try {
			club.inscribirJugadorEquipo(
					"S2",
					"Real Madrid Sub17",
					"Delantero",
					10
			);
		}catch(Exception e) {
			System.out.println(e.getMessage());
		}

		System.out.println("\nPRUEBA ACADEMIA Y RESERVA");

		Tenis tenis = new Tenis(2);

		club.agregarDisciplina(tenis);

		club.inscribirAcademia(
				"S1",
				"Tenis",
				"Intermedio"
		);

		club.inscribirAcademia(
				"S2",
				"Tenis",
				"Avanzado"
		);

		CanchaTenis canchaTenis = new CanchaTenis(
				"Cancha Rafa Nadal",
				4,
				false
		);

		club.agregarInstalacion(
				canchaTenis
		);

		Reserva reserva = club.reservarInstalacion(
				"S1",
				"Tenis",
				"Cancha Rafa Nadal",
				Date.valueOf("2026-10-20"),
				Time.valueOf("10:00:00"),
				Time.valueOf("11:00:00"),
				2,
				"Sencillos"
		);

		ArrayList<Socio> jugadoresTenis = new ArrayList<Socio>();

		jugadoresTenis.add(
				club.getSocio("S1")
		);

		jugadoresTenis.add(
				club.getSocio("S2")
		);

		PartidoPractica partidoPractica = club.programarPartidoPractica(
				reserva,
				jugadoresTenis
		);

		club.registrarResultadoPractica(
				partidoPractica,
				"2-0",
				club.getSocio("S1"),
				club.getSocio("S2")
		);

		System.out.println(
				"Ranking Paula: "
				+ club.getSocio("S1").consultarRanking(tenis).getPuntajeELO()
		);

		System.out.println("\nPRUEBA TIENDA");

		ArticuloTienda camisetaRealMadrid = new ArticuloTienda(
				"Camiseta Real Madrid",
				50000,
				"Ropa deportiva"
		);

		club.agregarProducto(
				camisetaRealMadrid
		);

		UbicacionInventario tiendaPrincipal = new UbicacionInventario(
				"Tienda Principal"
		);

		club.agregarUbicacion(
				tiendaPrincipal
		);

		club.crearExistencia(
				"Camiseta Real Madrid",
				"Tienda Principal",
				10
		);

		HashMap<String, Integer> cantidadesTienda =
				new HashMap<String, Integer>();

		cantidadesTienda.put(
				"Camiseta Real Madrid",
				2
		);

		VentaTienda ventaTienda = club.registrarVentaTienda(
				"S1",
				"Tienda Principal",
				cantidadesTienda,
				"ANCE10",
				0
		);

		System.out.println(
				"Total venta: "
				+ ventaTienda.getTotal()
		);

		System.out.println(
				"Inventario restante: "
				+ club.buscarExistencia(
						"Camiseta Real Madrid",
						"Tienda Principal"
				).getCantidad()
		);

		System.out.println("\nPRUEBA TURNOS");

		club.asignarTurno(
				"E1",
				"Lunes",
				Time.valueOf("08:00:00"),
				Time.valueOf("12:00:00")
		);

		club.asignarTurno(
				"E2",
				"Lunes",
				Time.valueOf("08:00:00"),
				Time.valueOf("12:00:00")
		);

		club.asignarTurno(
				"F1",
				"Lunes",
				Time.valueOf("08:00:00"),
				Time.valueOf("12:00:00")
		);

		System.out.println(
				"Cumple minimo personal: "
				+ club.cumpleMinimoPersonal()
		);

		System.out.println("\nPRUEBA JUGADORES REAL MADRID");

		club.crearSocio(
				"cr7",
				"123",
				"Cristiano Ronaldo",
				"S3",
				Date.valueOf("2010-03-01")
		);

		club.crearSocio(
				"neymar",
				"123",
				"Neymar Jr",
				"S4",
				Date.valueOf("2010-04-01")
		);

		club.crearSocio(
				"mbappe",
				"123",
				"Kylian Mbappe",
				"S5",
				Date.valueOf("2010-05-01")
		);

		club.crearSocio(
				"bellingham",
				"123",
				"Jude Bellingham",
				"S6",
				Date.valueOf("2010-06-01")
		);

		club.crearSocio(
				"vinicius",
				"123",
				"Vinicius Jr",
				"S7",
				Date.valueOf("2010-07-01")
		);

		club.inscribirJugadorEquipo(
				"S3",
				"Real Madrid Sub17",
				"Delantero",
				7
		);

		club.inscribirJugadorEquipo(
				"S4",
				"Real Madrid Sub17",
				"Extremo",
				11
		);

		club.inscribirJugadorEquipo(
				"S5",
				"Real Madrid Sub17",
				"Delantero",
				9
		);

		club.inscribirJugadorEquipo(
				"S6",
				"Real Madrid Sub17",
				"Volante",
				5
		);

		club.inscribirJugadorEquipo(
				"S7",
				"Real Madrid Sub17",
				"Extremo",
				20
		);

		System.out.println("\nPRUEBA JUGADORES BARCELONA");

		club.crearEquipo(
				"Barcelona Sub17",
				10,
				futbol,
				"Sub17"
		);

		club.crearSocio(
				"lamine",
				"123",
				"Lamine Yamal",
				"S8",
				Date.valueOf("2010-01-10")
		);

		club.crearSocio(
				"pedri",
				"123",
				"Pedri",
				"S9",
				Date.valueOf("2010-02-10")
		);

		club.crearSocio(
				"gavi",
				"123",
				"Gavi",
				"S10",
				Date.valueOf("2010-03-10")
		);

		club.crearSocio(
				"lewandowski",
				"123",
				"Robert Lewandowski",
				"S11",
				Date.valueOf("2010-04-10")
		);

		club.crearSocio(
				"raphinha",
				"123",
				"Raphinha",
				"S12",
				Date.valueOf("2010-05-10")
		);

		club.crearSocio(
				"dejong",
				"123",
				"Frenkie de Jong",
				"S13",
				Date.valueOf("2010-06-10")
		);

		club.crearSocio(
				"terstegen",
				"123",
				"Ter Stegen",
				"S14",
				Date.valueOf("2010-07-10")
		);

		club.inscribirJugadorEquipo(
				"S8",
				"Barcelona Sub17",
				"Extremo",
				19
		);

		club.inscribirJugadorEquipo(
				"S9",
				"Barcelona Sub17",
				"Volante",
				8
		);

		club.inscribirJugadorEquipo(
				"S10",
				"Barcelona Sub17",
				"Volante",
				6
		);

		club.inscribirJugadorEquipo(
				"S11",
				"Barcelona Sub17",
				"Delantero",
				9
		);

		club.inscribirJugadorEquipo(
				"S12",
				"Barcelona Sub17",
				"Extremo",
				11
		);

		club.inscribirJugadorEquipo(
				"S13",
				"Barcelona Sub17",
				"Volante",
				21
		);

		club.inscribirJugadorEquipo(
				"S14",
				"Barcelona Sub17",
				"Arquero",
				1
		);

		System.out.println(
				club.consultarEquipos()
		);

		System.out.println("\nPRUEBA PARTIDO INTERNO");

		PartidoInterno clasico = club.programarPartidoInterno(
				Date.valueOf("2026-10-21"),
				"Real Madrid Sub17",
				"Barcelona Sub17"
		);

		club.registrarResultadoInterno(
				clasico,
				"3-2"
		);

		System.out.println(
				"Real Madrid 3 - 2 Barcelona"
		);

		System.out.println(
				"Marcador guardado: "
				+ clasico.getMarcador()
		);

		System.out.println("\nPRUEBA ESTADISTICAS Y SANCION");

		club.registrarEstadisticaFutbol(
				clasico,
				club.getSocio("S3"),
				2,
				1,
				2,
				0
		);

		System.out.println(
				"Cristiano Ronaldo tiene sancion activa: "
				+ club.getSocio("S3").tieneSancionActiva()
		);

		System.out.println("\nPRUEBA ENTRENAMIENTO");

		CanchaConjunto santiagoBernabeu = new CanchaConjunto(
				"Santiago Bernabeu",
				20,
				false
		);

		club.agregarInstalacion(
				santiagoBernabeu
		);

		SesionEntrenamiento entrenamientoBarcelona = club.crearSesion(
				Date.valueOf("2026-10-23"),
				Time.valueOf("15:00:00"),
				90,
				"E2",
				"Santiago Bernabeu",
				club.getEquipo("Barcelona Sub17"),
				null
		);

		club.convocarEquipo(
				entrenamientoBarcelona,
				club.getEquipo("Barcelona Sub17")
		);

		club.registrarAsistencia(
				entrenamientoBarcelona,
				"S8",
				"Asistio"
		);

		club.registrarEvaluacion(
				entrenamientoBarcelona,
				"S8",
				4.5,
				"Excelente entrenamiento"
		);

		System.out.println(
				club.consultarEntrenamientos("S8")
		);

		try {
			club.registrarEvaluacion(
					entrenamientoBarcelona,
					"S9",
					6,
					"Nota invalida"
			);
		}catch(Exception e) {
			System.out.println(e.getMessage());
		}

		System.out.println("\nPRUEBA COMPETENCIA");

		Competencia championsJuvenil = club.crearCompetencia(
				"Champions Juvenil",
				"UEFA",
				"2026",
				futbol,
				"Sub17",
				"Liga"
		);

		ArrayList<Socio> nominaBarcelona = new ArrayList<Socio>();

		nominaBarcelona.add(
				club.getSocio("S8")
		);

		nominaBarcelona.add(
				club.getSocio("S9")
		);

		nominaBarcelona.add(
				club.getSocio("S10")
		);

		nominaBarcelona.add(
				club.getSocio("S11")
		);

		nominaBarcelona.add(
				club.getSocio("S12")
		);

		nominaBarcelona.add(
				club.getSocio("S13")
		);

		nominaBarcelona.add(
				club.getSocio("S14")
		);

		club.inscribirEquipoCompetencia(
				"Champions Juvenil",
				"Barcelona Sub17",
				nominaBarcelona
		);

		PartidoCompetencia partidoChampions = club.programarPartidoCompetencia(
				Date.valueOf("2026-10-25"),
				"Barcelona Sub17",
				"Champions Juvenil",
				"Manchester City Academy",
				"Wembley"
		);

		club.registrarResultadoCompetencia(
				partidoChampions,
				"2-1"
		);

		System.out.println(
				club.consultarCompetencias()
		);

		System.out.println(
				"Puntos Barcelona: "
				+ championsJuvenil.buscarFila(
						club.getEquipo("Barcelona Sub17")
				).getPuntos()
		);

		System.out.println("\nPRUEBA CAFETERIA");

		Bebida cafe = new Bebida(
				"Cafe Colombiano",
				5000,
				"Caliente"
		);

		Snack galletas = new Snack(
				"Galletas Oreo",
				15000,
				"Gluten"
		);

		club.agregarProducto(
				cafe
		);

		club.agregarProducto(
				galletas
		);

		HashMap<String, Integer> cantidadesCafeteria =
				new HashMap<String, Integer>();

		cantidadesCafeteria.put(
				"Cafe Colombiano",
				1
		);

		cantidadesCafeteria.put(
				"Galletas Oreo",
				1
		);

		System.out.println(
				"Alergenos de las galletas: "
				+ galletas.getAlergenos()
		);

		VentaCafeteria ventaCafeteria = club.registrarVentaCafeteria(
				"S2",
				cantidadesCafeteria,
				0.10,
				0
		);

		System.out.println(
				"Total cafeteria: "
				+ ventaCafeteria.getTotal()
		);

		System.out.println("\nPRUEBA CONSULTAR EQUIPOS DE PAULA");

		System.out.println(
				club.consultarEquiposSocio("S1")
		);

		System.out.println("\nPRUEBA CONSULTAR TURNOS DE ANCELOTTI");

		System.out.println(
				club.consultarTurnos("E1")
		);

		System.out.println("\nPRUEBA MENSUALIDAD PAULA");

		club.registrarMensualidad(
				"S1",
				"Octubre",
				250000,
				Date.valueOf("2026-10-18")
		);

		System.out.println(
				club.consultarMensualidades("S1")
		);

		System.out.println("\nPRUEBA INVENTARIO");

		System.out.println(
				club.consultarInventario()
		);

		System.out.println("\nPRUEBA HISTORIAL");

		System.out.println(
				club.consultarHistorial()
		);

		System.out.println("\nPRUEBA REPORTE DE VENTAS");

		System.out.println(
				club.generarReporteVentas()
		);

		System.out.println("\nPRUEBA REPORTE DEPORTIVO");

		System.out.println(
				club.generarReporteDeportivo()
		);

		System.out.println("\nPRUEBA CERRAR SESION");

		club.iniciarSesion(
				"pau",
				"123"
		);

		System.out.println(
				"Usuario antes de cerrar sesion: "
				+ club.getUsuarioActual().getNombre()
		);

		club.cerrarSesion();

		if(club.getUsuarioActual() == null) {
			System.out.println("Sesion cerrada correctamente");
		}else {
			System.out.println("Error al cerrar sesion");
		}

		System.out.println("\nPRUEBA PERSISTENCIA");

		club.escribirUsuarios();
		club.escribirSerializable();

		ClubDeportivoSeneca clubCargado =
				new ClubDeportivoSeneca();

		clubCargado.leerSerializable();

		System.out.println(
				"Usuarios guardados: "
				+ club.getUsuarios().size()
		);

		System.out.println(
				"Usuarios cargados: "
				+ clubCargado.getUsuarios().size()
		);

		System.out.println(
				clubCargado.consultarUsuarios()
		);
	}
}