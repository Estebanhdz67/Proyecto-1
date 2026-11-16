package presentacion;

import java.sql.Date;
import java.sql.Time;

import java.util.ArrayList;

import logica.Administrador;
import logica.ArticuloTienda;
import logica.Bebida;
import logica.EncuentroTorneo;
import logica.Empleado;
import logica.InscripcionTorneo;
import logica.ProductoCafeteria;
import logica.ProductoVenta;
import logica.Snack;
import logica.SolicitudCambioTurno;
import logica.SugerenciaProducto;
import logica.Torneo;
import logica.Baloncesto;
import logica.BonoDescuento;
import logica.DeporteConjunto;
import logica.DisciplinaIndividual;
import logica.Futbol;
import logica.ParticipanteTorneo;
import logica.Usuario;
import logica.Voleibol;

/**
 * Consola del administrador. Como tiene muchas opciones, se organizan en submenus.
 */
public class PrincipalAdministrador extends ConsolaBase {
	// Menu principal
	private static final String USUARIOS_EQUIPOS = "Usuarios y equipos";
	private static final String INVENTARIO = "Inventario y productos";
	private static final String PERSONAL = "Personal y turnos";
	private static final String MENSUALIDADES = "Mensualidades";
	private static final String REPORTES = "Reportes e historial";
	private static final String TORNEOS = "Torneos";

	// Usuarios y equipos
	private static final String VER_USUARIOS = "Consultar usuarios";
	private static final String CREAR_SOCIO = "Registrar socio";
	private static final String CREAR_ENTRENADOR = "Registrar entrenador";
	private static final String CREAR_FISIO = "Registrar fisioterapeuta";
	private static final String BAJA_SOCIO = "Dar de baja a un socio";
	private static final String VER_EQUIPOS = "Consultar equipos";
	private static final String CREAR_EQUIPO = "Crear equipo";
	private static final String INSCRIBIR_JUGADOR = "Inscribir jugador en un equipo";
	private static final String BAJA_EQUIPO = "Dar de baja un equipo";

	// Inventario
	private static final String VER_INVENTARIO = "Consultar inventario";
	private static final String MOVER = "Mover articulos entre ubicaciones";
	private static final String REABASTECER = "Reabastecer articulo";
	private static final String VER_PRODUCTOS = "Consultar productos";
	private static final String REVISAR_SUGERENCIAS = "Revisar sugerencias de productos";

	// Personal
	private static final String ASIGNAR_TURNO = "Asignar turno a un empleado";
	private static final String VER_TURNOS = "Consultar turnos de un empleado";
	private static final String VERIFICAR_PERSONAL = "Verificar minimo de personal";
	private static final String REVISAR_CAMBIOS = "Revisar solicitudes de cambio de turno";

	// Mensualidades
	private static final String REGISTRAR_MENSUALIDAD = "Registrar pago de mensualidad";
	private static final String VER_MENSUALIDADES = "Consultar mensualidades de un socio";

	// Reportes
	private static final String REPORTE_VENTAS = "Reporte de ventas";
	private static final String REPORTE_DEPORTIVO = "Reporte deportivo";
	private static final String HISTORIAL = "Historial de actividades";
	private static final String VER_COMPETENCIAS = "Consultar competencias";

	// Torneos
	private static final String VER_TORNEOS = "Consultar torneos y su estado";
	private static final String CREAR_TORNEO = "Crear torneo";
	private static final String INSCRIBIR_TORNEO = "Inscribir participante en un torneo";
	private static final String RETIRAR_TORNEO = "Retirar participante de un torneo";
	private static final String LIBERAR_CUPOS = "Liberar cupos preferenciales";
	private static final String INICIAR_TORNEO = "Iniciar torneo";
	private static final String FINALIZAR_TORNEO = "Finalizar torneo";
	private static final String PROGRAMAR_ENCUENTRO = "Programar encuentro";
	private static final String RESULTADO_ENCUENTRO = "Registrar resultado de un encuentro";
	private static final String REGISTRAR_PAGO = "Registrar pago de inscripcion (competitivo)";
	private static final String CONSULTAR_PREMIO = "Consultar premio del ganador (competitivo)";
	private static final String GENERAR_BONO = "Generar bono al ganador (recreativo)";
	private static final String HISTORIAL_TORNEO = "Consultar historial de un torneo";

	private static final String[] CATEGORIAS = {"Sub13", "Sub15", "Sub17", "Mayores"};
	private static final String[] NIVELES = {"Principiante", "Intermedio", "Avanzado"};
	private static final String[] DISCIPLINAS = {"Tenis", "Padel", "TenisMesa"};
	private static final String[] DIAS = {"Lunes", "Martes", "Miercoles", "Jueves", "Viernes", "Sabado", "Domingo"};
	private static final String[] MESES = {"Enero", "Febrero", "Marzo", "Abril", "Mayo", "Junio", "Julio",
			"Agosto", "Septiembre", "Octubre", "Noviembre", "Diciembre"};

	private Administrador administrador;

	@Override
	protected String getNombreConsola() {
		return "ADMINISTRADOR";
	}

	@Override
	protected boolean puedeIngresar(Usuario usuario) {
		return usuario instanceof Administrador;
	}

	@Override
	protected void prepararSesion(Usuario usuario) {
		this.administrador = (Administrador) usuario;
	}

	@Override
	protected String[] getOpcionesMenu() {
		return new String[] {USUARIOS_EQUIPOS, INVENTARIO, PERSONAL, MENSUALIDADES, REPORTES, TORNEOS};
	}

	@Override
	protected void ejecutarOpcion(String opcion) throws Exception {
		switch(opcion) {
		// ---------- Submenus ----------
		case USUARIOS_EQUIPOS:
			this.mostrarMenu("USUARIOS Y EQUIPOS", new String[] {VER_USUARIOS, CREAR_SOCIO, CREAR_ENTRENADOR, CREAR_FISIO,
					BAJA_SOCIO, VER_EQUIPOS, CREAR_EQUIPO, INSCRIBIR_JUGADOR, BAJA_EQUIPO}, "Volver");
			break;
		case INVENTARIO:
			this.mostrarMenu("INVENTARIO", new String[] {VER_INVENTARIO, MOVER, REABASTECER, VER_PRODUCTOS, REVISAR_SUGERENCIAS}, "Volver");
			break;
		case PERSONAL:
			this.mostrarMenu("PERSONAL Y TURNOS", new String[] {ASIGNAR_TURNO, VER_TURNOS, VERIFICAR_PERSONAL, REVISAR_CAMBIOS}, "Volver");
			break;
		case MENSUALIDADES:
			this.mostrarMenu("MENSUALIDADES", new String[] {REGISTRAR_MENSUALIDAD, VER_MENSUALIDADES}, "Volver");
			break;
		case REPORTES:
			this.mostrarMenu("REPORTES E HISTORIAL", new String[] {REPORTE_VENTAS, REPORTE_DEPORTIVO, HISTORIAL, VER_COMPETENCIAS}, "Volver");
			break;
		case TORNEOS:
			this.mostrarMenu("TORNEOS", new String[] {VER_TORNEOS, CREAR_TORNEO, INSCRIBIR_TORNEO, RETIRAR_TORNEO, LIBERAR_CUPOS,
					INICIAR_TORNEO, PROGRAMAR_ENCUENTRO, RESULTADO_ENCUENTRO, FINALIZAR_TORNEO, REGISTRAR_PAGO, CONSULTAR_PREMIO, GENERAR_BONO, HISTORIAL_TORNEO}, "Volver");
			break;

		// ---------- Usuarios y equipos ----------
		case VER_USUARIOS:
			System.out.println(this.club.consultarUsuarios());
			break;
		case CREAR_SOCIO:
			this.crearSocio();
			break;
		case CREAR_ENTRENADOR:
			this.crearEmpleado(true);
			break;
		case CREAR_FISIO:
			this.crearEmpleado(false);
			break;
		case BAJA_SOCIO:
			this.darBajaSocio();
			break;
		case VER_EQUIPOS:
			System.out.println(this.club.consultarEquipos());
			break;
		case CREAR_EQUIPO:
			this.crearEquipo();
			break;
		case INSCRIBIR_JUGADOR:
			this.inscribirJugador();
			break;
		case BAJA_EQUIPO:
			this.darBajaEquipo();
			break;

		// ---------- Inventario ----------
		case VER_INVENTARIO:
			System.out.println(this.club.consultarInventario());
			break;
		case MOVER:
			this.moverInventario();
			break;
		case REABASTECER:
			this.reabastecer();
			break;
		case VER_PRODUCTOS:
			this.consultarProductos();
			break;
		case REVISAR_SUGERENCIAS:
			this.revisarSugerencias();
			break;

		// ---------- Personal ----------
		case ASIGNAR_TURNO:
			this.asignarTurno();
			break;
		case VER_TURNOS:
			System.out.println(this.club.consultarTurnos(this.lector.leerTexto("Id del empleado")));
			break;
		case VERIFICAR_PERSONAL:
			this.verificarPersonal();
			break;
		case REVISAR_CAMBIOS:
			this.revisarCambiosTurno();
			break;

		// ---------- Mensualidades ----------
		case REGISTRAR_MENSUALIDAD:
			this.registrarMensualidad();
			break;
		case VER_MENSUALIDADES:
			System.out.println(this.club.consultarMensualidades(this.lector.leerTexto("Id del socio")));
			break;

		// ---------- Reportes ----------
		case REPORTE_VENTAS:
			System.out.println(this.club.generarReporteVentas());
			break;
		case REPORTE_DEPORTIVO:
			System.out.println(this.club.generarReporteDeportivo());
			break;
		case HISTORIAL:
			System.out.println(this.club.consultarHistorial());
			break;
		case VER_COMPETENCIAS:
			System.out.println(this.club.consultarCompetencias());
			break;

		// ---------- Torneos ----------
		case VER_TORNEOS:
			this.consultarTorneos();
			break;
		case CREAR_TORNEO:
			this.crearTorneo();
			break;
		case INSCRIBIR_TORNEO:
			this.inscribirEnTorneo();
			break;
		case RETIRAR_TORNEO:
			this.retirarDeTorneo();
			break;
		case LIBERAR_CUPOS:
			this.club.liberarCuposPreferencialesTorneo(this.leerIdTorneo());
			this.mostrarExito("Los cupos preferenciales sin usar ahora son cupos regulares.");
			break;
		case INICIAR_TORNEO:
			this.club.iniciarTorneo(this.leerIdTorneo());
			this.mostrarExito("Torneo iniciado. Ya no se permiten inscripciones ni retiros.");
			break;
		case FINALIZAR_TORNEO:
			this.club.finalizarTorneo(this.leerIdTorneo());
			this.mostrarExito("Torneo finalizado.");
			break;
		case PROGRAMAR_ENCUENTRO:
			this.programarEncuentro();
			break;
		case RESULTADO_ENCUENTRO:
			this.registrarResultadoEncuentro();
			break;
		case REGISTRAR_PAGO:
			this.registrarPagoTorneo();
			break;
		case CONSULTAR_PREMIO:
			this.consultarPremio();
			break;
		case GENERAR_BONO:
			this.generarBono();
			break;
		case HISTORIAL_TORNEO:
			this.consultarHistorialTorneo();
			break;
		default:
			System.out.println("Opcion no disponible.");
		}
	}

	// ------------------------------------------------------------------
	// Usuarios y equipos
	// ------------------------------------------------------------------

	private void crearSocio() throws Exception {
		String login = this.lector.leerTexto("Login");
		String password = this.lector.leerTexto("Password");
		String nombre = this.lector.leerTexto("Nombre completo");
		String idSocio = this.lector.leerTexto("Id del socio (ej. S20)");
		Date nacimiento = this.lector.leerFecha("Fecha de nacimiento");

		if(nacimiento.after(new Date(System.currentTimeMillis()))) {
			throw new Exception("La fecha de nacimiento no puede ser futura");
		}

		this.club.crearSocio(login, password, nombre, idSocio, nacimiento);
		this.mostrarExito("Socio " + nombre + " registrado con id " + idSocio + ".");
	}

	private void crearEmpleado(boolean entrenador) throws Exception {
		String login = this.lector.leerTexto("Login");
		String password = this.lector.leerTexto("Password");
		String nombre = this.lector.leerTexto("Nombre completo");
		String idEmpleado = this.lector.leerTexto("Id del empleado (ej. E5 o F2)");
		String codigo = this.lector.leerTexto("Codigo de descuento del empleado");
		String especialidad = this.lector.leerTexto("Especialidad");

		if(entrenador) {
			this.club.crearEntrenador(login, password, nombre, idEmpleado, codigo, especialidad);
		}else {
			this.club.crearFisioterapeuta(login, password, nombre, idEmpleado, codigo, especialidad);
		}

		this.mostrarExito((entrenador ? "Entrenador " : "Fisioterapeuta ") + nombre + " registrado.");
	}

	private void darBajaSocio() throws Exception {
		String idSocio = this.lector.leerTexto("Id del socio");
		String nombre = this.club.getSocio(idSocio).getNombre();

		if(this.lector.leerSiNo("Seguro que desea dar de baja a " + nombre + "?")) {
			this.club.darBajaSocio(idSocio);
			this.mostrarExito(nombre + " fue dado de baja y sus inscripciones quedaron inactivas.");
		}else {
			System.out.println("Operacion cancelada.");
		}
	}

	private DeporteConjunto leerDeporteConjunto() {
		String deporte = this.lector.leerOpcion("Deporte", new String[] {"Futbol", "Baloncesto", "Voleibol"});

		if(deporte.equals("Futbol")) {
			return new Futbol();
		}else if(deporte.equals("Baloncesto")) {
			return new Baloncesto();
		}
		return new Voleibol();
	}

	private void crearEquipo() throws Exception {
		String nombre = this.lector.leerTexto("Nombre del equipo");
		DeporteConjunto deporte = this.leerDeporteConjunto();
		String categoria = this.lector.leerOpcion("Categoria", CATEGORIAS);
		int cupo = this.lector.leerEntero("Cupo maximo de jugadores (minimo " + deporte.getMinimoJugadores() + ")",
				deporte.getMinimoJugadores(), 40);

		this.club.crearEquipo(nombre, cupo, deporte, categoria);
		this.mostrarExito("Equipo " + nombre + " creado.");
	}

	private void inscribirJugador() throws Exception {
		String idSocio = this.lector.leerTexto("Id del socio");
		this.club.getSocio(idSocio);
		System.out.println(this.club.consultarEquipos());
		String equipo = this.lector.leerTexto("Nombre del equipo");
		this.club.getEquipo(equipo);
		String posicion = this.lector.leerTexto("Posicion");
		int camiseta = this.lector.leerEntero("Numero de camiseta (1 a 99)", 1, 99);

		this.club.inscribirJugadorEquipo(idSocio, equipo, posicion, camiseta);
		this.mostrarExito("Jugador inscrito en " + equipo + " con la camiseta " + camiseta + ".");
	}

	private void darBajaEquipo() throws Exception {
		System.out.println(this.club.consultarEquipos());
		String equipo = this.lector.leerTexto("Nombre del equipo");
		this.club.getEquipo(equipo);

		if(this.lector.leerSiNo("Seguro que desea dar de baja el equipo " + equipo + "?")) {
			this.club.darBajaEquipo(equipo);
			this.mostrarExito("Equipo dado de baja.");
		}else {
			System.out.println("Operacion cancelada.");
		}
	}

	// ------------------------------------------------------------------
	// Inventario
	// ------------------------------------------------------------------

	private void moverInventario() throws Exception {
		System.out.println(this.club.consultarInventario());
		System.out.println("Ubicaciones: " + this.club.getUbicaciones().keySet());
		String articulo = this.lector.leerTexto("Articulo");
		this.club.getArticulo(articulo);
		String origen = this.lector.leerTexto("Ubicacion de origen");
		this.club.getUbicacion(origen);
		String destino = this.lector.leerTexto("Ubicacion de destino");
		this.club.getUbicacion(destino);

		if(origen.equals(destino)) {
			throw new Exception("El origen y el destino deben ser diferentes");
		}

		int cantidad = this.lector.leerEntero("Cantidad", 1, 100000);
		this.club.moverInventario(articulo, origen, destino, cantidad, this.administrador.getIdAdministrador());
		this.mostrarExito(cantidad + " unidades de " + articulo + " movidas de " + origen + " a " + destino + ".");
	}

	private void reabastecer() throws Exception {
		System.out.println("Ubicaciones: " + this.club.getUbicaciones().keySet());
		String articulo = this.lector.leerTexto("Articulo");
		this.club.getArticulo(articulo);
		String ubicacion = this.lector.leerTexto("Ubicacion");
		this.club.getUbicacion(ubicacion);
		int cantidad = this.lector.leerEntero("Cantidad que llega", 1, 100000);

		this.club.reabastecer(articulo, ubicacion, cantidad, this.administrador.getIdAdministrador());
		this.mostrarExito("Reabastecimiento registrado. Ahora hay "
				+ this.club.buscarExistencia(articulo, ubicacion).getCantidad() + " unidades en " + ubicacion + ".");
	}

	private void consultarProductos() {
		System.out.println("Producto\tTipo\tPrecio");

		for(ProductoVenta producto : this.club.getProductos().values()) {
			String tipo = (producto instanceof ProductoCafeteria) ? "Cafeteria" : "Tienda";
			System.out.println(producto.getNombre() + "\t" + tipo + "\t$" + producto.getPrecio());
		}
	}

	private void revisarSugerencias() throws Exception {
		ArrayList<SugerenciaProducto> pendientes = new ArrayList<SugerenciaProducto>();
		ArrayList<String> descripciones = new ArrayList<String>();

		for(Empleado empleado : this.club.getEmpleados().values()) {
			for(SugerenciaProducto sugerencia : empleado.getSugerencias()) {
				if(sugerencia.getEstado().equals("Pendiente")) {
					pendientes.add(sugerencia);
					descripciones.add(empleado.getNombre() + ": " + sugerencia.getDescripcion());
				}
			}
		}

		if(pendientes.isEmpty()) {
			throw new Exception("No hay sugerencias pendientes");
		}

		SugerenciaProducto sugerencia = pendientes.get(this.lector.leerPosicion("Sugerencia", descripciones));
		boolean aprobar = this.lector.leerSiNo("Aprobar la sugerencia?");
		ProductoVenta producto = null;

		if(aprobar && this.lector.leerSiNo("Desea agregar el producto al catalogo ahora?")) {
			producto = this.leerProductoNuevo();
		}

		this.club.revisarSugerencia(sugerencia, this.administrador.getIdAdministrador(), aprobar, producto);
		this.mostrarExito("Sugerencia " + (aprobar ? "aprobada" : "rechazada") + ".");
	}

	private ProductoVenta leerProductoNuevo() throws Exception {
		String tipo = this.lector.leerOpcion("Tipo de producto", new String[] {"Articulo de tienda", "Bebida", "Snack"});
		String nombre = this.lector.leerTexto("Nombre del producto");

		if(this.club.getProductos().containsKey(nombre)) {
			throw new Exception("Ya existe un producto con ese nombre");
		}

		double precio = this.lector.leerDecimal("Precio", 1, 100000000);

		if(tipo.equals("Articulo de tienda")) {
			return new ArticuloTienda(nombre, precio, this.lector.leerTexto("Categoria"));
		}else if(tipo.equals("Bebida")) {
			return new Bebida(nombre, precio, this.lector.leerOpcion("Temperatura", new String[] {"Caliente", "Fria"}));
		}
		return new Snack(nombre, precio, this.lector.leerTexto("Alergenos (o Ninguno)"));
	}

	// ------------------------------------------------------------------
	// Personal
	// ------------------------------------------------------------------

	private void asignarTurno() throws Exception {
		String idEmpleado = this.lector.leerTexto("Id del empleado");
		this.club.getEmpleado(idEmpleado);
		String dia = this.lector.leerOpcion("Dia", DIAS);
		Time inicio = this.lector.leerHora("Hora de inicio");
		Time fin = this.lector.leerHora("Hora de fin");

		while(!fin.after(inicio)) {
			System.out.println("  -> La hora de fin debe ser posterior a la de inicio.");
			fin = this.lector.leerHora("Hora de fin");
		}

		this.club.asignarTurno(idEmpleado, dia, inicio, fin);
		this.mostrarExito("Turno asignado: " + dia + " " + inicio + " - " + fin + ".");
	}

	private void verificarPersonal() {
		if(this.club.cumpleMinimoPersonal()) {
			this.mostrarExito("El club cumple el minimo de 2 entrenadores y 1 fisioterapeuta en todo el horario.");
		}else {
			System.out.println("[!] Hay franjas sin el minimo de 2 entrenadores y 1 fisioterapeuta.");
		}
	}

	private void revisarCambiosTurno() throws Exception {
		ArrayList<SolicitudCambioTurno> pendientes = new ArrayList<SolicitudCambioTurno>();
		ArrayList<String> descripciones = new ArrayList<String>();

		for(Empleado empleado : this.club.getEmpleados().values()) {
			for(SolicitudCambioTurno solicitud : empleado.getSolicitudes()) {
				if(solicitud.getEstado().equals("Pendiente")) {
					pendientes.add(solicitud);
					descripciones.add(empleado.getNombre() + ": "
							+ solicitud.getTurnoActual().getDiaSemana() + " " + solicitud.getTurnoActual().getHoraInicio()
							+ "-" + solicitud.getTurnoActual().getHoraFin() + "  ->  "
							+ solicitud.getTurnoNuevo().getDiaSemana() + " " + solicitud.getTurnoNuevo().getHoraInicio()
							+ "-" + solicitud.getTurnoNuevo().getHoraFin());
				}
			}
		}

		if(pendientes.isEmpty()) {
			throw new Exception("No hay solicitudes de cambio de turno pendientes");
		}

		SolicitudCambioTurno solicitud = pendientes.get(this.lector.leerPosicion("Solicitud", descripciones));
		boolean aprobar = this.lector.leerSiNo("Aprobar el cambio?");

		this.club.revisarCambio(solicitud, this.administrador.getIdAdministrador(), aprobar);
		this.mostrarExito("Solicitud " + solicitud.getEstado().toLowerCase() + ".");
	}

	// ------------------------------------------------------------------
	// Mensualidades
	// ------------------------------------------------------------------

	private void registrarMensualidad() throws Exception {
		String idSocio = this.lector.leerTexto("Id del socio");
		this.club.getSocio(idSocio);
		String mes = this.lector.leerOpcion("Mes", MESES);
		double valor = this.lector.leerDecimal("Valor pagado", 1, 100000000);
		Date fecha = this.lector.leerFecha("Fecha de pago");

		this.club.registrarMensualidad(idSocio, mes, valor, fecha);
		this.mostrarExito("Mensualidad de " + mes + " registrada.");
	}

	// ------------------------------------------------------------------
	// Torneos
	// ------------------------------------------------------------------

	private void crearTorneo() throws Exception {
		String idTorneo = this.lector.leerTexto("Id del torneo (ej. T3)");

		if(this.club.getTorneos().containsKey(idTorneo)) {
			throw new Exception("Ya existe un torneo con ese id");
		}

		String nombre = this.lector.leerTexto("Nombre del torneo");
		String tipo = this.lector.leerOpcion("Tipo de torneo", new String[] {"Recreativo", "Competitivo"});
		String actividad = this.lector.leerOpcion("Tipo de actividad", new String[] {"Deporte de conjunto", "Disciplina individual"});

		DeporteConjunto deporte = null;
		DisciplinaIndividual disciplina = null;
		String categoriaNivel;

		if(actividad.equals("Deporte de conjunto")) {
			deporte = this.leerDeporteConjunto();
			categoriaNivel = this.lector.leerOpcion("Categoria", CATEGORIAS);
		}else {
			disciplina = this.club.getDisciplina(this.lector.leerOpcion("Disciplina", DISCIPLINAS));
			categoriaNivel = this.lector.leerOpcion("Nivel", NIVELES);
		}

		int cupos = this.lector.leerEntero("Numero de cupos (2 a 128)", 2, 128);
		Date fecha = this.lector.leerFecha("Fecha de inicio del torneo");
		String formato = this.lector.leerOpcion("Formato", new String[] {"Liga", "Fase de grupos", "Eliminacion directa", "ASCUN"});

		double valor;
		if(tipo.equals("Recreativo")) {
			valor = this.lector.leerDecimal("Porcentaje del bono de descuento para el ganador (1 a 100)", 1, 100);
		}else {
			valor = this.lector.leerDecimal("Tarifa de inscripcion", 1, 100000000);
		}

		this.club.crearTorneo(idTorneo, nombre, categoriaNivel, cupos, fecha, tipo, formato, deporte, disciplina, valor);
		int preferenciales = (int) Math.ceil(cupos * 0.2);
		this.mostrarExito("Torneo " + nombre + " creado con " + cupos + " cupos (" + preferenciales + " cupos preferenciales).");
	}

	/**
	 * Pide el tipo de participante y lo busca en el club.
	 */
	private ParticipanteTorneo leerParticipante() throws Exception {
		String tipo = this.lector.leerOpcion("Tipo de participante", new String[] {"Equipo", "Socio", "Empleado"});

		if(tipo.equals("Equipo")) {
			System.out.println(this.club.consultarEquipos());
			return this.club.getEquipo(this.lector.leerTexto("Nombre del equipo"));
		}else if(tipo.equals("Socio")) {
			return this.club.getSocio(this.lector.leerTexto("Id del socio"));
		}
		return this.club.getEmpleado(this.lector.leerTexto("Id del empleado"));
	}

	private void inscribirEnTorneo() throws Exception {
		String idTorneo = this.leerIdTorneo();
		ParticipanteTorneo participante = this.leerParticipante();
		boolean preferencial = this.lector.leerSiNo("Usar cupo preferencial (por ranking)?");

		this.club.inscribirParticipanteTorneo(idTorneo, participante, preferencial);
		this.mostrarExito(participante.getNombreParticipante() + " inscrito en el torneo " + idTorneo + ".");
	}

	private void retirarDeTorneo() throws Exception {
		String idTorneo = this.leerIdTorneo();
		ParticipanteTorneo participante = this.leerParticipante();

		this.club.desinscribirParticipanteTorneo(idTorneo, participante);
		this.mostrarExito(participante.getNombreParticipante() + " retirado del torneo " + idTorneo + ". El cupo quedo libre.");
	}

	private void programarEncuentro() throws Exception {
		String idTorneo = this.leerIdTorneo();
		Torneo torneo = this.club.getTorneo(idTorneo);
		ArrayList<InscripcionTorneo> inscripciones = torneo.getInscripciones();

		if(inscripciones.size() < 2) {
			throw new Exception("El torneo necesita al menos 2 participantes inscritos");
		}

		ArrayList<String> nombres = new ArrayList<String>();
		for(InscripcionTorneo inscripcion : inscripciones) {
			nombres.add(inscripcion.getParticipante().getNombreParticipante());
		}

		ParticipanteTorneo participante1 = inscripciones.get(this.lector.leerPosicion("Participante 1", nombres)).getParticipante();
		ParticipanteTorneo participante2 = inscripciones.get(this.lector.leerPosicion("Participante 2", nombres)).getParticipante();

		while(participante1 == participante2) {
			System.out.println("  -> El participante 2 debe ser diferente al participante 1.");
			participante2 = inscripciones.get(this.lector.leerPosicion("Participante 2", nombres)).getParticipante();
		}

		Date fecha = this.lector.leerFecha("Fecha del encuentro");

		this.club.crearEncuentroTorneo(idTorneo, fecha, participante1, participante2);
		this.mostrarExito("Encuentro programado: " + participante1.getNombreParticipante() + " vs "
				+ participante2.getNombreParticipante() + " el " + fecha + ".");
	}

	private void registrarResultadoEncuentro() throws Exception {
		Torneo torneo = this.club.getTorneo(this.leerIdTorneo());
		ArrayList<EncuentroTorneo> pendientes = new ArrayList<EncuentroTorneo>();
		ArrayList<String> descripciones = new ArrayList<String>();

		for(EncuentroTorneo encuentro : torneo.getEncuentros()) {
			if(!encuentro.tieneResultado()) {
				pendientes.add(encuentro);
				descripciones.add(encuentro.getFecha() + ": " + encuentro.getParticipante1().getNombreParticipante()
						+ " vs " + encuentro.getParticipante2().getNombreParticipante());
			}
		}

		if(pendientes.isEmpty()) {
			throw new Exception("El torneo no tiene encuentros pendientes de resultado");
		}

		EncuentroTorneo encuentro = pendientes.get(this.lector.leerPosicion("Encuentro", descripciones));
		String resultado = this.lector.leerTexto("Resultado (ej. 3-1)");

		this.club.registrarResultadoTorneo(encuentro, resultado);
		this.mostrarExito("Resultado registrado.");
	}

	private void registrarPagoTorneo() throws Exception {
		String idTorneo = this.leerIdTorneo();
		ParticipanteTorneo participante = this.leerParticipante();
		double valor = this.lector.leerDecimal("Valor pagado", 1, 100000000);

		this.club.registrarPagoTorneo(idTorneo, participante, valor);
		this.mostrarExito("Pago registrado.");
	}

	private void consultarPremio() throws Exception {
		String idTorneo = this.leerIdTorneo();
		System.out.println("Ganador:");
		ParticipanteTorneo ganador = this.leerParticipante();
		double premio = this.club.consultarPremioTorneo(idTorneo, ganador);

		if(premio == 0) {
			System.out.println("El ganador no recibe premio en efectivo (es empleado o no hay recaudo).");
		}else {
			System.out.printf("Premio para %s: $%,.2f%n", ganador.getNombreParticipante(), premio);
		}
	}

	private void generarBono() throws Exception {
		String idTorneo = this.leerIdTorneo();
		System.out.println("Ganador:");
		ParticipanteTorneo ganador = this.leerParticipante();
		BonoDescuento bono = this.club.generarBonoTorneo(idTorneo, ganador);

		this.mostrarExito("Bono " + bono.getCodigo() + " del " + bono.getPorcentaje() + "% generado para "
				+ ganador.getNombreParticipante() + ".");
	}

	public static void main(String[] args) {
		new PrincipalAdministrador().ejecutar();
	}
}
