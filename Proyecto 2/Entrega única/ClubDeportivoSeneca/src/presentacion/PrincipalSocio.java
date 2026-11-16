package presentacion;

import java.sql.Date;
import java.sql.Time;
import java.util.ArrayList;
import java.util.HashMap;

import logica.PartidoPractica;
import logica.Reserva;
import logica.Socio;
import logica.Usuario;
import logica.VentaCafeteria;
import logica.VentaTienda;

/**
 * Consola para los socios del club.
 */
public class PrincipalSocio extends ConsolaBase {
	// Menu principal
	private static final String MIS_EQUIPOS = "Consultar mis equipos";
	private static final String ACADEMIAS_RESERVAS = "Academias, reservas y partidos de practica";
	private static final String COMPRAS = "Compras y puntos de fidelidad";
	private static final String MIS_ENTRENAMIENTOS = "Consultar mis entrenamientos";
	private static final String MIS_MENSUALIDADES = "Consultar mis mensualidades";
	private static final String TORNEOS = "Torneos";

	// Academias, reservas y partidos
	private static final String INSCRIBIR_ACADEMIA = "Inscribirme en una academia";
	private static final String RESERVAR = "Reservar una instalacion";
	private static final String MIS_RESERVAS = "Consultar mis reservas";
	private static final String PROGRAMAR_PARTIDO = "Programar partido de practica en una reserva";
	private static final String RESULTADO_PARTIDO = "Registrar resultado de un partido de practica";

	// Compras
	private static final String COMPRAR_TIENDA = "Comprar en la tienda";
	private static final String COMPRAR_CAFETERIA = "Comprar en la cafeteria";
	private static final String MIS_PUNTOS = "Consultar mis puntos de fidelidad";
	private static final String MIS_BONOS = "Consultar mis bonos de torneos";

	// Torneos
	private static final String VER_TORNEOS = "Consultar torneos y su estado";
	private static final String INSCRIBIRSE_TORNEO = "Inscribirme en un torneo";
	private static final String RETIRARSE_TORNEO = "Retirarme de un torneo";
	private static final String HISTORIAL_TORNEO = "Consultar historial de un torneo";

	private static final String[] DISCIPLINAS = {"Tenis", "Padel", "TenisMesa"};
	private static final String[] NIVELES = {"Principiante", "Intermedio", "Avanzado"};
	private static final String[] MODALIDADES = {"Sencillos", "Dobles"};

	private Socio socio;

	@Override
	protected String getNombreConsola() {
		return "SOCIOS";
	}

	@Override
	protected boolean puedeIngresar(Usuario usuario) {
		return usuario instanceof Socio;
	}

	@Override
	protected void prepararSesion(Usuario usuario) {
		this.socio = (Socio) usuario;
	}

	@Override
	protected String[] getOpcionesMenu() {
		return new String[] {MIS_EQUIPOS, ACADEMIAS_RESERVAS, COMPRAS, MIS_ENTRENAMIENTOS, MIS_MENSUALIDADES, TORNEOS};
	}

	@Override
	protected void ejecutarOpcion(String opcion) throws Exception {
		switch(opcion) {
		// ---------- Submenus ----------
		case ACADEMIAS_RESERVAS:
			this.mostrarMenu("ACADEMIAS, RESERVAS Y PARTIDOS", new String[] {INSCRIBIR_ACADEMIA, RESERVAR, MIS_RESERVAS,
					PROGRAMAR_PARTIDO, RESULTADO_PARTIDO}, "Volver");
			break;
		case COMPRAS:
			this.mostrarMenu("COMPRAS", new String[] {COMPRAR_TIENDA, COMPRAR_CAFETERIA, MIS_PUNTOS, MIS_BONOS}, "Volver");
			break;
		case TORNEOS:
			this.mostrarMenu("TORNEOS", new String[] {VER_TORNEOS, INSCRIBIRSE_TORNEO, RETIRARSE_TORNEO, HISTORIAL_TORNEO}, "Volver");
			break;

		// ---------- Consultas ----------
		case MIS_EQUIPOS:
			System.out.println(this.club.consultarEquiposSocio(this.socio.getIdSocio()));
			break;
		case MIS_ENTRENAMIENTOS:
			System.out.println(this.club.consultarEntrenamientos(this.socio.getIdSocio()));
			break;
		case MIS_MENSUALIDADES:
			System.out.println(this.club.consultarMensualidades(this.socio.getIdSocio()));
			break;

		// ---------- Academias, reservas y partidos ----------
		case INSCRIBIR_ACADEMIA:
			this.inscribirAcademia();
			break;
		case RESERVAR:
			this.reservarInstalacion();
			break;
		case MIS_RESERVAS:
			this.consultarReservas();
			break;
		case PROGRAMAR_PARTIDO:
			this.programarPartido();
			break;
		case RESULTADO_PARTIDO:
			this.registrarResultadoPartido();
			break;

		// ---------- Compras ----------
		case COMPRAR_TIENDA:
			this.comprarTienda();
			break;
		case COMPRAR_CAFETERIA:
			this.comprarCafeteria();
			break;
		case MIS_PUNTOS:
			System.out.println("Puntos disponibles: " + this.socio.getCuentaFidelidad().getPuntos() + " (1 punto = $1)");
			break;

		case MIS_BONOS:
			this.consultarBonos(this.socio);
			break;

		// ---------- Torneos ----------
		case VER_TORNEOS:
			this.consultarTorneos();
			break;
		case INSCRIBIRSE_TORNEO:
			this.inscribirseTorneo(this.socio);
			break;
		case RETIRARSE_TORNEO:
			this.retirarseTorneo(this.socio);
			break;
		case HISTORIAL_TORNEO:
			this.consultarHistorialTorneo();
			break;
		default:
			System.out.println("Opcion no disponible.");
		}
	}

	// ------------------------------------------------------------------
	// Academias, reservas y partidos de practica
	// ------------------------------------------------------------------

	private void inscribirAcademia() throws Exception {
		String disciplina = this.lector.leerOpcion("Disciplina", DISCIPLINAS);
		String nivel = this.lector.leerOpcion("Nivel", NIVELES);

		this.club.inscribirAcademia(this.socio.getIdSocio(), disciplina, nivel);
		this.mostrarExito("Quedo inscrito en la academia de " + disciplina + " con nivel " + nivel + ".");
	}

	private void reservarInstalacion() throws Exception {
		String disciplina = this.lector.leerOpcion("Disciplina", DISCIPLINAS);
		String idInstalacion = this.lector.leerTexto("Id de la instalacion");
		this.club.getInstalacion(idInstalacion);
		Date fecha = this.lector.leerFecha("Fecha de la reserva");
		Time inicio = this.lector.leerHora("Hora de inicio");
		Time fin = this.lector.leerHora("Hora de fin");

		while(!fin.after(inicio)) {
			System.out.println("  -> La hora de fin debe ser posterior a la de inicio.");
			fin = this.lector.leerHora("Hora de fin");
		}

		String modalidad = this.lector.leerOpcion("Modalidad", MODALIDADES);
		int jugadores = modalidad.equals("Sencillos") ? 2 : 4;

		this.club.reservarInstalacion(this.socio.getIdSocio(), disciplina, idInstalacion, fecha, inicio, fin, jugadores, modalidad);
		this.mostrarExito("Reserva creada: " + idInstalacion + " el " + fecha + " de " + inicio + " a " + fin + ".");
	}

	private ArrayList<Reserva> getMisReservas() {
		ArrayList<Reserva> misReservas = new ArrayList<Reserva>();

		for(Reserva reserva : this.club.getReservas()) {
			if(reserva.getSocio().getIdSocio().equals(this.socio.getIdSocio())) {
				misReservas.add(reserva);
			}
		}

		return misReservas;
	}

	private String describirReserva(Reserva reserva) {
		String texto = reserva.getDisciplina().getNombre() + " | " + reserva.getInstalacion().getIdInstalacion()
				+ " | " + reserva.getFecha() + " " + reserva.getHoraInicio() + "-" + reserva.getHoraFin()
				+ " | " + reserva.getModalidad();
		PartidoPractica partido = reserva.getPartidoPractica();

		if(partido == null) {
			texto += " | Sin partido";
		}else if(!partido.tieneResultado()) {
			texto += " | Partido programado";
		}else {
			texto += " | Resultado: " + partido.getResultado();
		}

		return texto;
	}

	private void consultarReservas() {
		ArrayList<Reserva> misReservas = this.getMisReservas();

		if(misReservas.isEmpty()) {
			System.out.println("No tiene reservas.");
			return;
		}

		for(Reserva reserva : misReservas) {
			System.out.println("  - " + this.describirReserva(reserva));
		}
	}

	private void programarPartido() throws Exception {
		ArrayList<Reserva> disponibles = new ArrayList<Reserva>();
		ArrayList<String> descripciones = new ArrayList<String>();

		for(Reserva reserva : this.getMisReservas()) {
			if(reserva.getPartidoPractica() == null) {
				disponibles.add(reserva);
				descripciones.add(this.describirReserva(reserva));
			}
		}

		if(disponibles.isEmpty()) {
			throw new Exception("No tiene reservas sin partido. Primero reserve una instalacion");
		}

		Reserva reserva = disponibles.get(this.lector.leerPosicion("Reserva", descripciones));
		ArrayList<Socio> jugadores = new ArrayList<Socio>();
		jugadores.add(this.socio);

		System.out.println("Usted ya esta incluido. Faltan " + (reserva.getCantidadJugadores() - 1) + " jugadores.");

		while(jugadores.size() < reserva.getCantidadJugadores()) {
			String idSocio = this.lector.leerTexto("Id del jugador " + (jugadores.size() + 1));

			try {
				Socio jugador = this.club.getSocio(idSocio);

				if(jugadores.contains(jugador)) {
					System.out.println("  -> Ese jugador ya esta en el partido.");
				}else {
					jugadores.add(jugador);
				}
			} catch (Exception e) {
				System.out.println("  -> " + e.getMessage() + ".");
			}
		}

		this.club.programarPartidoPractica(reserva, jugadores);
		this.mostrarExito("Partido de practica programado. Los niveles de los jugadores son compatibles.");
	}

	private void registrarResultadoPartido() throws Exception {
		ArrayList<PartidoPractica> pendientes = new ArrayList<PartidoPractica>();
		ArrayList<String> descripciones = new ArrayList<String>();

		for(Reserva reserva : this.getMisReservas()) {
			PartidoPractica partido = reserva.getPartidoPractica();

			if(partido != null && !partido.tieneResultado()) {
				pendientes.add(partido);
				descripciones.add(this.describirReserva(reserva));
			}
		}

		if(pendientes.isEmpty()) {
			throw new Exception("No tiene partidos de practica pendientes de resultado");
		}

		PartidoPractica partido = pendientes.get(this.lector.leerPosicion("Partido", descripciones));
		ArrayList<String> nombres = new ArrayList<String>();

		for(Socio jugador : partido.getJugadores()) {
			nombres.add(jugador.getNombre() + " (" + jugador.getIdSocio() + ")");
		}

		String resultado = this.lector.leerTexto("Marcador (ej. 6-4 6-3)");
		Socio ganador = partido.getJugadores().get(this.lector.leerPosicion("Ganador", nombres));
		Socio perdedor = partido.getJugadores().get(this.lector.leerPosicion("Perdedor", nombres));

		while(ganador == perdedor) {
			System.out.println("  -> El perdedor debe ser diferente al ganador.");
			perdedor = partido.getJugadores().get(this.lector.leerPosicion("Perdedor", nombres));
		}

		this.club.registrarResultadoPractica(partido, resultado, ganador, perdedor);
		this.mostrarExito("Resultado registrado y ranking actualizado.");
	}

	// ------------------------------------------------------------------
	// Compras
	// ------------------------------------------------------------------

	private int leerPuntosARedimir() {
		int disponibles = this.socio.getCuentaFidelidad().getPuntos();

		if(disponibles == 0) {
			return 0;
		}

		System.out.println("Tiene " + disponibles + " puntos disponibles.");
		return this.lector.leerEntero("Puntos a usar (0 para no usar)", 0, disponibles);
	}

	private void comprarTienda() throws Exception {
		System.out.println("Ubicaciones: " + this.club.getUbicaciones().keySet());
		String ubicacion = this.lector.leerTexto("Ubicacion donde compra");
		this.club.getUbicacion(ubicacion);

		HashMap<String, Integer> carrito = this.leerCarrito(false);
		String bono = this.leerBono(this.socio);
		String codigo = null;
		int puntos = 0;

		if(bono == null) {
			codigo = this.lector.leerTextoOpcional("Codigo de descuento de un empleado");
			puntos = this.leerPuntosARedimir();
		}

		VentaTienda venta = this.club.registrarVentaTienda(this.socio.getIdSocio(), ubicacion, carrito, codigo, puntos, bono);
		this.mostrarExito("Compra registrada.");
		this.mostrarVenta(venta);
		System.out.println("Puntos actuales: " + this.socio.getCuentaFidelidad().getPuntos());
	}

	private void comprarCafeteria() throws Exception {
		HashMap<String, Integer> carrito = this.leerCarrito(true);
		double propina = this.lector.leerDecimal("Porcentaje de propina (0 a 20)", 0, 20) / 100;
		String bono = this.leerBono(this.socio);
		int puntos = 0;

		if(bono == null) {
			puntos = this.leerPuntosARedimir();
		}

		VentaCafeteria venta = this.club.registrarVentaCafeteria(this.socio.getIdSocio(), carrito, propina, puntos, bono);
		this.mostrarExito("Compra registrada.");
		this.mostrarVenta(venta);
		System.out.println("Puntos actuales: " + this.socio.getCuentaFidelidad().getPuntos());
	}

	public static void main(String[] args) {
		new PrincipalSocio().ejecutar();
	}
}
