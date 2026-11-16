package presentacion;

import java.sql.Date;
import java.sql.Time;
import java.util.ArrayList;
import java.util.HashMap;

import logica.AsistenciaEntrenamiento;
import logica.DisciplinaIndividual;
import logica.Empleado;
import logica.Entrenador;
import logica.Equipo;
import logica.SesionEntrenamiento;
import logica.Turno;
import logica.Usuario;
import logica.VentaCafeteria;
import logica.VentaTienda;

/**
 * Consola para los empleados (entrenadores y fisioterapeutas).
 * Los entrenadores ven ademas las opciones de entrenamiento.
 */
public class PrincipalEmpleado extends ConsolaBase {
	private static final String MIS_TURNOS = "Consultar mis turnos";
	private static final String CAMBIO_TURNO = "Solicitar cambio de turno";
	private static final String COMPRAR_TIENDA = "Comprar en la tienda";
	private static final String COMPRAR_CAFETERIA = "Comprar en la cafeteria";
	private static final String SUGERIR = "Sugerir un producto";
	private static final String MIS_BONOS = "Consultar mis bonos de torneos";
	private static final String ENTRENAMIENTOS = "Entrenamientos";
	private static final String TORNEOS = "Torneos";

	private static final String CREAR_SESION = "Crear sesion de entrenamiento";
	private static final String MIS_SESIONES = "Consultar mis sesiones";
	private static final String ASISTENCIA = "Registrar asistencia";
	private static final String EVALUACION = "Evaluar a un jugador";

	private static final String VER_TORNEOS = "Consultar torneos y su estado";
	private static final String INSCRIBIRSE_TORNEO = "Inscribirme en un torneo individual";
	private static final String RETIRARSE_TORNEO = "Retirarme de un torneo";
	private static final String HISTORIAL_TORNEO = "Consultar historial de un torneo";

	private static final String[] DIAS = {"Lunes", "Martes", "Miercoles", "Jueves", "Viernes", "Sabado", "Domingo"};
	private static final String[] DISCIPLINAS = {"Tenis", "Padel", "TenisMesa"};

	private Empleado empleado;

	@Override
	protected String getNombreConsola() {
		return "EMPLEADOS";
	}

	@Override
	protected boolean puedeIngresar(Usuario usuario) {
		return usuario instanceof Empleado;
	}

	@Override
	protected void prepararSesion(Usuario usuario) {
		this.empleado = (Empleado) usuario;
	}

	@Override
	protected String[] getOpcionesMenu() {
		if(this.empleado instanceof Entrenador) {
			return new String[] {MIS_TURNOS, CAMBIO_TURNO, COMPRAR_TIENDA, COMPRAR_CAFETERIA, MIS_BONOS, SUGERIR, ENTRENAMIENTOS, TORNEOS};
		}
		return new String[] {MIS_TURNOS, CAMBIO_TURNO, COMPRAR_TIENDA, COMPRAR_CAFETERIA, MIS_BONOS, SUGERIR, TORNEOS};
	}

	@Override
	protected void ejecutarOpcion(String opcion) throws Exception {
		switch(opcion) {
		case MIS_TURNOS:
			System.out.println(this.club.consultarTurnos(this.empleado.getIdEmpleado()));
			break;
		case CAMBIO_TURNO:
			this.solicitarCambioTurno();
			break;
		case COMPRAR_TIENDA:
			this.comprarTienda();
			break;
		case COMPRAR_CAFETERIA:
			this.comprarCafeteria();
			break;
		case MIS_BONOS:
			this.consultarBonos(this.empleado);
			break;
		case SUGERIR:
			this.sugerirProducto();
			break;
		case ENTRENAMIENTOS:
			this.mostrarMenu("ENTRENAMIENTOS", new String[] {CREAR_SESION, MIS_SESIONES, ASISTENCIA, EVALUACION}, "Volver");
			break;
		case CREAR_SESION:
			this.crearSesion();
			break;
		case MIS_SESIONES:
			this.consultarSesiones();
			break;
		case ASISTENCIA:
			this.registrarAsistencia();
			break;
		case EVALUACION:
			this.evaluarJugador();
			break;
		case TORNEOS:
			this.mostrarMenu("TORNEOS", new String[] {VER_TORNEOS, INSCRIBIRSE_TORNEO, RETIRARSE_TORNEO, HISTORIAL_TORNEO}, "Volver");
			break;
		case VER_TORNEOS:
			this.consultarTorneos();
			break;
		case INSCRIBIRSE_TORNEO:
			System.out.println("Recuerde: solo torneos individuales, el dia del torneo no puede estar de turno.");
			this.inscribirseTorneo(this.empleado);
			break;
		case RETIRARSE_TORNEO:
			this.retirarseTorneo(this.empleado);
			break;
		case HISTORIAL_TORNEO:
			this.consultarHistorialTorneo();
			break;
		default:
			System.out.println("Opcion no disponible.");
		}
	}

	private void solicitarCambioTurno() throws Exception {
		ArrayList<Turno> turnos = this.empleado.getTurnos();

		if(turnos.isEmpty()) {
			throw new Exception("No tiene turnos asignados");
		}

		ArrayList<String> descripciones = new ArrayList<String>();
		for(Turno turno : turnos) {
			descripciones.add(turno.getDiaSemana() + " " + turno.getHoraInicio() + " - " + turno.getHoraFin());
		}

		Turno actual = turnos.get(this.lector.leerPosicion("Turno que quiere cambiar", descripciones));

		System.out.println("Nuevo turno:");
		String dia = this.lector.leerOpcion("Dia", DIAS);
		Time inicio = this.lector.leerHora("Hora de inicio");
		Time fin = this.lector.leerHora("Hora de fin");

		while(!fin.after(inicio)) {
			System.out.println("  -> La hora de fin debe ser posterior a la de inicio.");
			fin = this.lector.leerHora("Hora de fin");
		}

		this.club.solicitarCambio(this.empleado.getIdEmpleado(), actual, new Turno(dia, inicio, fin));
		this.mostrarExito("Solicitud enviada. Queda pendiente de aprobacion del administrador.");
	}

	private void comprarTienda() throws Exception {
		System.out.println("Ubicaciones: " + this.club.getUbicaciones().keySet());
		String ubicacion = this.lector.leerTexto("Ubicacion donde compra");
		this.club.getUbicacion(ubicacion);

		HashMap<String, Integer> carrito = this.leerCarrito(false);
		String bono = this.leerBono(this.empleado);
		String codigo = null;

		if(bono == null && this.lector.leerSiNo("Desea usar su codigo de descuento de empleado (15%)?")) {
			codigo = this.empleado.getCodigoDescuento();
		}

		VentaTienda venta = this.club.registrarVentaTienda(this.empleado.getIdEmpleado(), ubicacion, carrito, codigo, 0, bono);
		this.mostrarExito("Compra registrada.");
		this.mostrarVenta(venta);
	}

	private void comprarCafeteria() throws Exception {
		HashMap<String, Integer> carrito = this.leerCarrito(true);
		double propina = this.lector.leerDecimal("Porcentaje de propina (0 a 20)", 0, 20) / 100;
		String bono = this.leerBono(this.empleado);

		VentaCafeteria venta = this.club.registrarVentaCafeteria(this.empleado.getIdEmpleado(), carrito, propina, 0, bono);
		this.mostrarExito("Compra registrada.");
		this.mostrarVenta(venta);
	}

	private void sugerirProducto() throws Exception {
		String descripcion = this.lector.leerTexto("Describa el producto que sugiere");
		this.club.sugerirProducto(this.empleado.getIdEmpleado(), descripcion);
		this.mostrarExito("Sugerencia registrada. El administrador la revisara.");
	}

	private void crearSesion() throws Exception {
		Date fecha = this.lector.leerFecha("Fecha de la sesion");
		Time hora = this.lector.leerHora("Hora de inicio");
		int duracion = this.lector.leerEntero("Duracion en minutos (30 a 240)", 30, 240);
		String idInstalacion = this.lector.leerTexto("Id de la instalacion");
		this.club.getInstalacion(idInstalacion);

		String tipo = this.lector.leerOpcion("La sesion es para", new String[] {"Un equipo", "Una academia individual"});
		Equipo equipo = null;
		DisciplinaIndividual disciplina = null;

		if(tipo.equals("Un equipo")) {
			System.out.println(this.club.consultarEquipos());
			equipo = this.club.getEquipo(this.lector.leerTexto("Nombre del equipo"));
		}else {
			disciplina = this.club.getDisciplina(this.lector.leerOpcion("Disciplina", DISCIPLINAS));
		}

		SesionEntrenamiento sesion = this.club.crearSesion(fecha, hora, duracion, this.empleado.getIdEmpleado(), idInstalacion, equipo, disciplina);

		if(equipo != null) {
			this.club.convocarEquipo(sesion, equipo);
			this.mostrarExito("Sesion creada y jugadores activos del equipo " + equipo.getNombre() + " convocados.");
		}else {
			this.mostrarExito("Sesion de " + disciplina.getNombre() + " creada.");
		}
	}

	private ArrayList<SesionEntrenamiento> getMisSesiones() {
		ArrayList<SesionEntrenamiento> misSesiones = new ArrayList<SesionEntrenamiento>();

		for(SesionEntrenamiento sesion : this.club.getSesiones()) {
			if(sesion.getEntrenador().getIdEmpleado().equals(this.empleado.getIdEmpleado())) {
				misSesiones.add(sesion);
			}
		}

		return misSesiones;
	}

	private String describirSesion(SesionEntrenamiento sesion) {
		String grupo;

		if(sesion.getEquipo() != null) {
			grupo = "Equipo " + sesion.getEquipo().getNombre();
		}else {
			grupo = "Academia de " + sesion.getDisciplina().getNombre();
		}

		return sesion.getFecha() + " " + sesion.getHora() + " | " + sesion.getDuracion() + " min | "
				+ sesion.getInstalacion().getIdInstalacion() + " | " + grupo;
	}

	private String describirAsistencia(AsistenciaEntrenamiento asistencia) {
		String texto = asistencia.getSocio().getNombre() + " (" + asistencia.getSocio().getIdSocio() + ") - "
				+ asistencia.getEstado();

		if(asistencia.getEvaluacion() != null) {
			texto += " - Nota: " + asistencia.getEvaluacion().getNota();
		}

		return texto;
	}

	private void consultarSesiones() {
		ArrayList<SesionEntrenamiento> misSesiones = this.getMisSesiones();

		if(misSesiones.isEmpty()) {
			System.out.println("No tiene sesiones creadas.");
			return;
		}

		for(SesionEntrenamiento sesion : misSesiones) {
			System.out.println("* " + this.describirSesion(sesion));

			for(AsistenciaEntrenamiento asistencia : sesion.getAsistencias()) {
				System.out.println("    - " + this.describirAsistencia(asistencia));
			}
		}
	}

	/**
	 * Pide la sesion y luego el jugador convocado. Retorna la asistencia elegida.
	 */
	private AsistenciaEntrenamiento leerConvocado() throws Exception {
		ArrayList<SesionEntrenamiento> misSesiones = this.getMisSesiones();

		if(misSesiones.isEmpty()) {
			throw new Exception("No tiene sesiones creadas");
		}

		ArrayList<String> descripciones = new ArrayList<String>();
		for(SesionEntrenamiento sesion : misSesiones) {
			descripciones.add(this.describirSesion(sesion));
		}

		SesionEntrenamiento sesion = misSesiones.get(this.lector.leerPosicion("Sesion", descripciones));

		if(sesion.getAsistencias().isEmpty()) {
			throw new Exception("La sesion no tiene jugadores convocados");
		}

		ArrayList<String> convocados = new ArrayList<String>();
		for(AsistenciaEntrenamiento asistencia : sesion.getAsistencias()) {
			convocados.add(this.describirAsistencia(asistencia));
		}

		return sesion.getAsistencias().get(this.lector.leerPosicion("Jugador", convocados));
	}

	private void registrarAsistencia() throws Exception {
		AsistenciaEntrenamiento asistencia = this.leerConvocado();
		String estado = this.lector.leerOpcion("Estado", new String[] {"Asistio", "Tarde", "Falto"});

		this.club.registrarAsistencia(asistencia.getSesion(), asistencia.getSocio().getIdSocio(), estado);
		this.mostrarExito("Asistencia de " + asistencia.getSocio().getNombre() + ": " + estado + ".");
	}

	private void evaluarJugador() throws Exception {
		AsistenciaEntrenamiento asistencia = this.leerConvocado();
		double nota = this.lector.leerDecimal("Nota (1 a 5)", 1, 5);
		String comentario = this.lector.leerTexto("Comentario");

		this.club.registrarEvaluacion(asistencia.getSesion(), asistencia.getSocio().getIdSocio(), nota, comentario);
		this.mostrarExito("Evaluacion registrada para " + asistencia.getSocio().getNombre() + ".");
	}

	public static void main(String[] args) {
		new PrincipalEmpleado().ejecutar();
	}
}
