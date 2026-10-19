package logica;

import java.sql.Date;
import java.sql.Time;
import java.util.ArrayList;
import java.util.HashMap;

import persistencia.ArchivoPlano;
import persistencia.ArchivoSerializable;

public class ClubDeportivoSeneca {
	private HashMap<String, Usuario> usuarios;
	private HashMap<String, Socio> socios;
	private HashMap<String, Empleado> empleados;
	private HashMap<String, Administrador> administradores;
	private HashMap<String, Equipo> equipos;
	private HashMap<String, Competencia> competencias;
	private HashMap<String, DisciplinaIndividual> disciplinas;
	private HashMap<String, Instalacion> instalaciones;
	private HashMap<String, ProductoVenta> productos;
	private HashMap<String, UbicacionInventario> ubicaciones;
	private ArrayList<Venta> ventas;
	private ArrayList<Reserva> reservas;
	private ArrayList<PartidoPractica> partidosPractica;
	private ArrayList<PartidoInterno> partidosInternos;
	private ArrayList<PartidoCompetencia> partidosCompetencia;
	private ArrayList<SesionEntrenamiento> sesiones;
	private ArrayList<ExistenciaInventario> existencias;
	private ArrayList<MovimientoInventario> movimientos;
	private ArrayList<Reabastecimiento> reabastecimientos;
	private ArrayList<SolicitudCambioTurno> solicitudes;
	private ArrayList<SugerenciaProducto> sugerencias;
	private ArchivoPlano archivoPlano;
	private ArchivoSerializable archivoSerializable;
	private Usuario usuarioActual;

	public ClubDeportivoSeneca() {
		this.usuarios = new HashMap<String, Usuario>();
		this.socios = new HashMap<String, Socio>();
		this.empleados = new HashMap<String, Empleado>();
		this.administradores = new HashMap<String, Administrador>();
		this.equipos = new HashMap<String, Equipo>();
		this.competencias = new HashMap<String, Competencia>();
		this.disciplinas = new HashMap<String, DisciplinaIndividual>();
		this.instalaciones = new HashMap<String, Instalacion>();
		this.productos = new HashMap<String, ProductoVenta>();
		this.ubicaciones = new HashMap<String, UbicacionInventario>();
		this.ventas = new ArrayList<Venta>();
		this.reservas = new ArrayList<Reserva>();
		this.partidosPractica = new ArrayList<PartidoPractica>();
		this.partidosInternos = new ArrayList<PartidoInterno>();
		this.partidosCompetencia = new ArrayList<PartidoCompetencia>();
		this.sesiones = new ArrayList<SesionEntrenamiento>();
		this.existencias = new ArrayList<ExistenciaInventario>();
		this.movimientos = new ArrayList<MovimientoInventario>();
		this.reabastecimientos = new ArrayList<Reabastecimiento>();
		this.solicitudes = new ArrayList<SolicitudCambioTurno>();
		this.sugerencias = new ArrayList<SugerenciaProducto>();
		this.archivoPlano = new ArchivoPlano();
		this.archivoSerializable = new ArchivoSerializable();
		this.usuarioActual = null;
	}

	public void crearSocio(String login, String password, String nombre, String idSocio, Date fechaNacimiento) throws Exception {
		if(this.usuarios.containsKey(login)) {
			throw new Exception("El login ya existe");
		}
		if(this.socios.containsKey(idSocio)) {
			throw new Exception("El socio ya existe");
		}
		Socio socio = new Socio(login, password, nombre, idSocio, fechaNacimiento);
		this.usuarios.put(login, socio);
		this.socios.put(idSocio, socio);
	}

	public void crearEntrenador(String login, String password, String nombre, String idEmpleado, String codigo, String especialidad) throws Exception {
		if(this.usuarios.containsKey(login)) {
			throw new Exception("El login ya existe");
		}
		if(this.empleados.containsKey(idEmpleado)) {
			throw new Exception("El empleado ya existe");
		}
		Entrenador entrenador = new Entrenador(login, password, nombre, idEmpleado, codigo, especialidad);
		this.usuarios.put(login, entrenador);
		this.empleados.put(idEmpleado, entrenador);
	}

	public void crearFisioterapeuta(String login, String password, String nombre, String idEmpleado, String codigo, String especialidad) throws Exception {
		if(this.usuarios.containsKey(login)) {
			throw new Exception("El login ya existe");
		}
		Fisioterapeuta fisioterapeuta = new Fisioterapeuta(login, password, nombre, idEmpleado, codigo, especialidad);
		this.usuarios.put(login, fisioterapeuta);
		this.empleados.put(idEmpleado, fisioterapeuta);
	}

	public void crearAdministrador(String login, String password, String nombre, String idAdministrador) throws Exception {
		if(this.usuarios.containsKey(login)) {
			throw new Exception("El login ya existe");
		}
		Administrador administrador = new Administrador(login, password, nombre, idAdministrador);
		this.usuarios.put(login, administrador);
		this.administradores.put(idAdministrador, administrador);
	}

	public Usuario iniciarSesion(String login, String password) throws Exception {
		Usuario usuario = this.usuarios.get(login);
		if(usuario == null) {
			throw new Exception("El usuario no existe");
		}
		if(!usuario.validarCredenciales(login, password)) {
			throw new Exception("Password incorrecto");
		}
		this.usuarioActual = usuario;
		return usuario;
	}
	public void cerrarSesion() {
		this.usuarioActual = null;
	}

	public Usuario getUsuarioActual() {
		return usuarioActual;
	}

	public void asociarEntrenadorSocio(String idEmpleado, String idSocio) throws Exception {
		Empleado empleado = getEmpleado(idEmpleado);
		if(!(empleado instanceof Entrenador)) {
			throw new Exception("No es entrenador");
		}
		Socio socio = getSocio(idSocio);
		((Entrenador) empleado).setPerfilSocio(socio);
		socio.setEntrenadorAsociado(true);
	}

	public void crearEquipo(String nombre, int cupo, DeporteConjunto deporte, String categoria) throws Exception {
		if(this.equipos.containsKey(nombre)) {
			throw new Exception("El equipo ya existe");
		}
		if(cupo < deporte.getMinimoJugadores()) {
			throw new Exception("Cupo menor al minimo del deporte");
		}
		this.equipos.put(nombre, new Equipo(nombre, cupo, deporte, categoria));
	}

	public void inscribirJugadorEquipo(String idSocio, String equipo, String posicion, int camiseta) throws Exception {
		Socio socio = getSocio(idSocio);
		Equipo destino = getEquipo(equipo);
		for(Equipo equipoActual : this.equipos.values()) {
			if(equipoActual != destino && equipoActual.getDeporte().getNombre().equals(destino.getDeporte().getNombre()) && equipoActual.contieneSocio(socio)) {
				throw new Exception("El socio ya esta en un equipo del mismo deporte");
			}
		}
		destino.inscribirJugador(socio, posicion, camiseta);
	}

	public void agregarDisciplina(DisciplinaIndividual disciplinaIndividual) throws Exception {
		if(this.disciplinas.containsKey(disciplinaIndividual.getNombre())) {
			throw new Exception("La disciplina ya existe");
		}
		this.disciplinas.put(disciplinaIndividual.getNombre(), disciplinaIndividual);
	}

	public InscripcionAcademia inscribirAcademia(String idSocio, String disciplina, String nivel) throws Exception {
		Socio socio = getSocio(idSocio);
		DisciplinaIndividual disciplinaIndividual = getDisciplina(disciplina);
		if(socio.consultarNivel(disciplinaIndividual) != null) {
			throw new Exception("Ya existe inscripcion");
		}
		InscripcionAcademia inscripcionAcademia = new InscripcionAcademia(new Date(System.currentTimeMillis()), "Activa", socio, disciplinaIndividual, nivel);
		socio.agregarInscripcionAcademia(inscripcionAcademia);
		socio.agregarRanking(new Ranking(1000, new Date(System.currentTimeMillis()), socio, disciplinaIndividual));
		return inscripcionAcademia;
	}

	public void asignarNivel(String idSocio, String disciplina, String nivel) throws Exception {
		Socio socio = getSocio(idSocio);
		for(InscripcionAcademia inscripcionAcademia : socio.getInscripcionesAcademia()) {
			if(inscripcionAcademia.getDisciplina().getNombre().equals(disciplina) && inscripcionAcademia.getEstado().equals("Activa")) {
				inscripcionAcademia.setNivel(nivel);
				return;
			}
		}
		throw new Exception("No existe inscripcion activa");
	}

	public void agregarInstalacion(Instalacion instalacion) throws Exception {
		if(this.instalaciones.containsKey(instalacion.getIdInstalacion())) {
			throw new Exception("La instalacion ya existe");
		}
		this.instalaciones.put(instalacion.getIdInstalacion(), instalacion);
	}

	public Reserva reservarInstalacion(String idSocio, String disciplina, String idInstalacion, Date fecha, Time inicio, Time fin, int jugadores, String modalidad) throws Exception {
		Socio socio = getSocio(idSocio);
		DisciplinaIndividual disciplinaIndividual = getDisciplina(disciplina);
		if(socio.consultarNivel(disciplinaIndividual) == null) {
			throw new Exception("El socio no esta inscrito en la disciplina");
		}
		Instalacion instalacion = getInstalacion(idInstalacion);
		Reserva reserva = new Reserva(fecha, inicio, fin, jugadores, modalidad, socio, instalacion, disciplinaIndividual);
		reserva.validarReserva();
		for(Reserva reservaExistente : this.reservas) {
			if(reservaExistente.getInstalacion().getIdInstalacion().equals(idInstalacion) && reserva.seCruza(reservaExistente)) {
				throw new Exception("La instalacion ya esta reservada");
			}
		}
		int simultaneas = 0;
		for(Reserva reservaExistente : this.reservas) {
			if(reservaExistente.getDisciplina().getNombre().equals(disciplinaIndividual.getNombre()) && reserva.seCruza(reservaExistente)) {
				simultaneas++;
			}
		}
		if(simultaneas >= disciplinaIndividual.getMaxInstalacionesSimultaneas()) {
			throw new Exception("Se alcanzo el maximo simultaneo");
		}
		this.reservas.add(reserva);
		return reserva;
	}

	public PartidoPractica programarPartidoPractica(Reserva reserva, ArrayList<Socio> jugadores) throws Exception {
		PartidoPractica partidoPractica = new PartidoPractica(reserva.getFecha(), null, reserva.getModalidad(), reserva.getDisciplina());
		for(Socio socio : jugadores) {
			partidoPractica.agregarJugador(socio);
		}
		partidoPractica.validarJugadores();
		reserva.setPartidoPractica(partidoPractica);
		this.partidosPractica.add(partidoPractica);
		return partidoPractica;
	}

	public void registrarResultadoPractica(PartidoPractica partidoPractica, String resultado, Socio ganador, Socio perdedor) throws Exception {
		partidoPractica.setResultado(resultado);
		partidoPractica.actualizarRankingSencillos(ganador, perdedor);
	}

	public SesionEntrenamiento crearSesion(Date fecha, Time hora, int duracion, String idEntrenador, String idInstalacion, Equipo equipo, DisciplinaIndividual disciplina) throws Exception {
		Empleado empleado = getEmpleado(idEntrenador);
		if(!(empleado instanceof Entrenador)) {
			throw new Exception("No es entrenador");
		}
		SesionEntrenamiento sesionEntrenamiento = new SesionEntrenamiento(fecha, hora, duracion, (Entrenador) empleado, getInstalacion(idInstalacion), equipo, disciplina);
		this.sesiones.add(sesionEntrenamiento);
		return sesionEntrenamiento;
	}

	public void convocarEquipo(SesionEntrenamiento sesion, Equipo equipo) {
		for(InscripcionEquipo inscripcionEquipo : equipo.getInscripciones()) {
			if(inscripcionEquipo.getEstado().equals("Activa")) {
				sesion.convocarSocio(inscripcionEquipo.getSocio());
			}
		}
	}

	public void registrarAsistencia(SesionEntrenamiento sesion, String idSocio, String estado) throws Exception {
		sesion.buscarAsistencia(idSocio).setEstado(estado);
	}

	public void registrarEvaluacion(SesionEntrenamiento sesion, String idSocio, double nota, String comentario) throws Exception {
		AsistenciaEntrenamiento asistenciaEntrenamiento = sesion.buscarAsistencia(idSocio);
		asistenciaEntrenamiento.setEvaluacion(new EvaluacionDesempeno(nota, comentario));
	}

	public PartidoInterno programarPartidoInterno(Date fecha, String local, String visitante) throws Exception {
		PartidoInterno partidoInterno = new PartidoInterno(fecha, getEquipo(local), getEquipo(visitante));
		this.partidosInternos.add(partidoInterno);
		return partidoInterno;
	}

	public void registrarEstadisticaFutbol(Partido partidoInterno, Socio socio, int goles, int asistencias, int amarillas, int rojas) {
		EstadisticaFutbol estadisticaFutbol = new EstadisticaFutbol(socio, partidoInterno, goles, asistencias, amarillas, rojas);
		partidoInterno.agregarEstadistica(estadisticaFutbol);
		if(rojas >= 1 || amarillas >= 2) {
			Sancion sancion = new Sancion("Sancion por tarjetas", 1, socio, partidoInterno);
			socio.agregarSancion(sancion);
		}
	}

	public void registrarEstadisticaBaloncesto(Partido partido, Socio socio, int puntos, int rebotes) {
		partido.agregarEstadistica(new EstadisticaBaloncesto(socio, partido, puntos, rebotes));
	}

	public void registrarEstadisticaVoleibol(Partido partido, Socio socio, int aces, int bloqueos) {
		partido.agregarEstadistica(new EstadisticaVoleibol(socio, partido, aces, bloqueos));
	}

	public Competencia crearCompetencia(String nombre, String entidad, String temporada, DeporteConjunto deporte, String categoria, String formato) throws Exception {
		if(this.competencias.containsKey(nombre)) {
			throw new Exception("La competencia ya existe");
		}
		Competencia competenciaCreada = new Competencia(nombre, entidad, temporada, deporte, categoria, formato);
		this.competencias.put(nombre, competenciaCreada);
		return competenciaCreada;
	}

	public InscripcionCompetencia inscribirEquipoCompetencia(String competencia, String equipo, ArrayList<Socio> nomina) throws Exception {
		Competencia competenciaSeleccionada = getCompetencia(competencia);
		Equipo equipoSeleccionado = getEquipo(equipo);
		if(!equipoSeleccionado.getCategoria().equals(competenciaSeleccionada.getCategoriaHabilitada())) {
			throw new Exception("Categoria no habilitada");
		}
		InscripcionCompetencia inscripcionCompetencia = new InscripcionCompetencia(new Date(System.currentTimeMillis()), "Activa", equipoSeleccionado, competenciaSeleccionada);
		for(Socio socio : nomina) {
			inscripcionCompetencia.agregarJugador(socio);
		}
		if(inscripcionCompetencia.getNomina().size() < equipoSeleccionado.getDeporte().getMinimoJugadores()) {
			throw new Exception("Nomina insuficiente");
		}
		competenciaSeleccionada.agregarInscripcion(inscripcionCompetencia);
		return inscripcionCompetencia;
	}

	public void agregarProducto(ProductoVenta producto) throws Exception {
		if(this.productos.containsKey(producto.getNombre())) {
			throw new Exception("El producto ya existe");
		}

		this.productos.put(producto.getNombre(), producto);
	}

	public void agregarUbicacion(UbicacionInventario ubicacion) throws Exception {
		if(this.ubicaciones.containsKey(ubicacion.getNombre())) {
			throw new Exception("La ubicacion ya existe");
		}

		this.ubicaciones.put(ubicacion.getNombre(), ubicacion);
	}

	public ExistenciaInventario crearExistencia(String articulo, String ubicacion, int cantidad) throws Exception {
		ExistenciaInventario existenciaInventario = new ExistenciaInventario(getArticulo(articulo), getUbicacion(ubicacion), cantidad);
		this.existencias.add(existenciaInventario);
		return existenciaInventario;
	}

	public ExistenciaInventario buscarExistencia(String articulo, String ubicacion) throws Exception {
		for(ExistenciaInventario existenciaInventario : this.existencias) {
			if(existenciaInventario.getArticulo().getNombre().equals(articulo) &&
					existenciaInventario.getUbicacion().getNombre().equals(ubicacion)) {
				return existenciaInventario;
			}
		}
		throw new Exception("No existe inventario para ese articulo y ubicacion");
	}

	public MovimientoInventario moverInventario(String articulo, String origen, String destino, int cantidad, String idAdmin) throws Exception {
		ExistenciaInventario existenciaOrigen = buscarExistencia(articulo, origen);
		ExistenciaInventario existenciaDestino;
		try {
			existenciaDestino = buscarExistencia(articulo, destino);
		} catch(Exception ex) {
			existenciaDestino = crearExistencia(articulo, destino, 0);
		}
		existenciaOrigen.disminuir(cantidad);
		existenciaDestino.aumentar(cantidad);
		MovimientoInventario movimientoInventario = new MovimientoInventario(new Date(System.currentTimeMillis()), cantidad, getArticulo(articulo), getUbicacion(origen), getUbicacion(destino), getAdministrador(idAdmin));
		this.movimientos.add(movimientoInventario);
		return movimientoInventario;
	}

	public Reabastecimiento reabastecer(String articulo, String ubicacion, int cantidad, String idAdmin) throws Exception {
		ExistenciaInventario existenciaInventario;
		try {
			existenciaInventario = buscarExistencia(articulo, ubicacion);
		} catch(Exception ex) {
			existenciaInventario = crearExistencia(articulo, ubicacion, 0);
		}
		existenciaInventario.aumentar(cantidad);
		Reabastecimiento reabastecimiento = new Reabastecimiento(new Date(System.currentTimeMillis()), cantidad, getArticulo(articulo), getAdministrador(idAdmin));
		this.reabastecimientos.add(reabastecimiento);
		return reabastecimiento;
	}

	public VentaTienda registrarVentaTienda(String comprador, String ubicacion, HashMap<String, Integer> cantidades, String codigo, int puntos) throws Exception {
		Comprador compradorVenta = buscarComprador(comprador);
		VentaTienda ventaTienda = new VentaTienda(new Date(System.currentTimeMillis()), compradorVenta);
		double descuento = 0;
		for(String nombreArticulo : cantidades.keySet()) {
			ArticuloTienda articuloTienda = getArticulo(nombreArticulo);
			int cantidadArticulo = cantidades.get(nombreArticulo);
			buscarExistencia(nombreArticulo, ubicacion).disminuir(cantidadArticulo);
			ventaTienda.agregarDetalle(new DetalleVenta(articuloTienda, cantidadArticulo, articuloTienda.getPrecio()));
		}
		double subtotal = ventaTienda.calcularSubtotal();
		if(compradorVenta instanceof Empleado && ((Empleado) compradorVenta).getCodigoDescuento().equals(codigo)) {
			descuento = subtotal*0.15;
		}
		else if(compradorVenta instanceof Socio && codigoValidoEmpleado(codigo)) {
			descuento = subtotal * 0.08;
		}
		ventaTienda.setDescuento(descuento);
		ventaTienda.setCodigoDescuentoUsado(codigo);
		double base = subtotal-descuento;
		if(compradorVenta instanceof Socio && puntos > 0) {
			((Socio) compradorVenta).getCuentaFidelidad().redimirPuntos(puntos);
			ventaTienda.setPuntosRedimidos(puntos);
			base-=puntos;
			if(base < 0) {
				base = 0;
			}
		}
		double total = base+(base*0.19);
		ventaTienda.setTotal(total);
		if(compradorVenta instanceof Socio) {
			((Socio) compradorVenta).getCuentaFidelidad().generarPuntos((int)(total*0.02));
		}
		this.ventas.add(ventaTienda);
		return ventaTienda;
	}

	public VentaCafeteria registrarVentaCafeteria(String comprador, HashMap<String, Integer> cantidades, double porcentajePropina, int puntos) throws Exception {
		Comprador compradorVenta = buscarComprador(comprador);
		VentaCafeteria ventaCafeteria = new VentaCafeteria(new Date(System.currentTimeMillis()), compradorVenta, porcentajePropina);
		for(String nombreProducto : cantidades.keySet()) {
			ProductoVenta productoVenta = getProducto(nombreProducto);
			if(!(productoVenta instanceof ProductoCafeteria)) {
				throw new Exception("No es producto de cafeteria");
			}
			ventaCafeteria.agregarDetalle(new DetalleVenta(productoVenta, cantidades.get(nombreProducto), productoVenta.getPrecio()));
		}
		double subtotal = ventaCafeteria.calcularSubtotal();
		double base = subtotal;
		if(compradorVenta instanceof Socio && puntos > 0) {
			((Socio) compradorVenta).getCuentaFidelidad().redimirPuntos(puntos);
			ventaCafeteria.setPuntosRedimidos(puntos);
			base-=puntos;
			if(base < 0) {
				base = 0;
			}
		}
		double impuesto = base*0.08;
		double propina = subtotal*porcentajePropina;
		double total = base+impuesto+propina;
		ventaCafeteria.setTotal(total);
		if(compradorVenta instanceof Socio) {
			((Socio) compradorVenta).getCuentaFidelidad().generarPuntos((int)((base+impuesto)*0.02));
		}
		this.ventas.add(ventaCafeteria);
		return ventaCafeteria;
	}

	private boolean codigoValidoEmpleado(String codigo) {
		if(codigo == null) {
			return false;
		}
		for(Empleado empleado : this.empleados.values()) {
			if(empleado.getCodigoDescuento().equals(codigo)) {
				return true;
			}
		}
		return false;
	}

	private Comprador buscarComprador(String id) throws Exception {
		if(this.socios.containsKey(id)) {
			return this.socios.get(id);
		}
		if(this.empleados.containsKey(id)) {
			return this.empleados.get(id);
		}
		throw new Exception("Comprador no existe");
	}

	public Turno asignarTurno(String idEmpleado, String dia, Time inicio, Time fin) throws Exception {
		Turno turno = new Turno(dia, inicio, fin);
		getEmpleado(idEmpleado).agregarTurno(turno);
		return turno;
	}

	public SolicitudCambioTurno solicitarCambio(String idEmpleado, Turno actual, Turno nuevo) throws Exception {
		Empleado empleado = getEmpleado(idEmpleado);
		SolicitudCambioTurno solicitudCambio = new SolicitudCambioTurno(new Date(System.currentTimeMillis()), empleado, actual, nuevo);
		empleado.agregarSolicitud(solicitudCambio);
		this.solicitudes.add(solicitudCambio);
		return solicitudCambio;
	}

	public void revisarCambio(SolicitudCambioTurno solicitud, String idAdmin, boolean aprobar) throws Exception {
		solicitud.setAdministrador(getAdministrador(idAdmin));
		if(!aprobar) {
			solicitud.setEstado("Rechazada");
			return;
		}
		Empleado empleado = solicitud.getEmpleado();
		empleado.getTurnos().remove(solicitud.getTurnoActual());
		empleado.getTurnos().add(solicitud.getTurnoNuevo());
		if(!cumpleMinimoPersonal()) {
			empleado.getTurnos().remove(solicitud.getTurnoNuevo());
			empleado.getTurnos().add(solicitud.getTurnoActual());
			solicitud.setEstado("Rechazada");
			throw new Exception("El cambio no cumple el minimo de personal");
		}
		solicitud.setEstado("Aprobada");
	}

	public boolean cumpleMinimoPersonal() {
		String[] dias = {"Lunes", "Martes", "Miercoles", "Jueves", "Viernes", "Sabado", "Domingo"};

		for(String dia : dias) {
			for(int horaNumero = 6; horaNumero < 22; horaNumero++) {
				String horaTexto;

				if(horaNumero < 10) {
					horaTexto = "0" + horaNumero + ":00:00";
				} else {
					horaTexto = horaNumero + ":00:00";
				}

				Time hora = Time.valueOf(horaTexto);
				int cantidadEntrenadores = 0;
				int cantidadFisioterapeutas = 0;

				for(Empleado empleado : this.empleados.values()) {
					for(Turno turno : empleado.getTurnos()) {
						if(turno.getDiaSemana().equals(dia) && turno.cubre(hora)) {
							if(empleado instanceof Entrenador) {
								cantidadEntrenadores++;
							}

							if(empleado instanceof Fisioterapeuta) {
								cantidadFisioterapeutas++;
							}
						}
					}
				}

				if((cantidadEntrenadores > 0 || cantidadFisioterapeutas > 0) &&
						(cantidadEntrenadores < 2 || cantidadFisioterapeutas < 1)) {
					return false;
				}
			}
		}

		return true;
	}

	public SugerenciaProducto sugerirProducto(String idEmpleado, String descripcion) throws Exception {
		Empleado empleado = getEmpleado(idEmpleado);
		SugerenciaProducto sugerenciaProducto = new SugerenciaProducto(descripcion, empleado);
		empleado.agregarSugerencia(sugerenciaProducto);
		this.sugerencias.add(sugerenciaProducto);
		return sugerenciaProducto;
	}

	public void revisarSugerencia(SugerenciaProducto sugerenciaProducto, String idAdmin, boolean aprobar, ProductoVenta producto) throws Exception {
		sugerenciaProducto.setAdministrador(getAdministrador(idAdmin));
		if(aprobar) {
			sugerenciaProducto.setEstado("Aprobada");
			if(producto != null) {
				agregarProducto(producto);
			}
		} else {
			sugerenciaProducto.setEstado("Rechazada");
		}
	}

	public void registrarMensualidad(String idSocio, String mes, double valor, Date fecha) throws Exception {
		if(valor < 0) {
			throw new Exception("Valor invalido");
		}
		getSocio(idSocio).agregarMensualidad(new Mensualidad(mes, valor, fecha));
	}
	public void darBajaSocio(String idSocio) throws Exception {
		Socio socio = getSocio(idSocio);
		
		for(Equipo equipo : this.equipos.values()) {
			
			for(InscripcionEquipo inscripcionEquipo : equipo.getInscripciones()) {
				
				if(inscripcionEquipo.getSocio().getIdSocio().equals(idSocio)
						&& inscripcionEquipo.getEstado().equals("Activa")) {
					inscripcionEquipo.setEstado("Inactiva");
				}
			}
		}

		this.usuarios.remove(socio.getLogin());
		this.socios.remove(idSocio);
	}
	public void darBajaEquipo(String nombreEquipo) throws Exception {
		Equipo equipo = getEquipo(nombreEquipo);
		
		for(InscripcionEquipo inscripcionEquipo : equipo.getInscripciones()) {
			
			if(inscripcionEquipo.getEstado().equals("Activa")) {
				inscripcionEquipo.setEstado("Inactiva");
			}
		}
		this.equipos.remove(nombreEquipo);
	}
	
	public String consultarEquiposSocio(String idSocio) throws Exception {
		Socio socio = getSocio(idSocio);
		String texto = "Equipo\tDeporte\tCategoria\tPosicion\tCamiseta\n";
		
		for(InscripcionEquipo inscripcionEquipo : socio.getInscripcionesEquipo()) {
			
			if(inscripcionEquipo.getEstado().equals("Activa")) {
				texto += inscripcionEquipo.getEquipo().getNombre() + "\t";
				texto += inscripcionEquipo.getEquipo().getDeporte().getNombre() + "\t";
				texto += inscripcionEquipo.getEquipo().getCategoria() + "\t";
				texto += inscripcionEquipo.getPosicion() + "\t";
				texto += inscripcionEquipo.getNumeroCamiseta() + "\n";
			}
		}
		return texto;
	}
	
	public String consultarEntrenamientos(String idSocio) throws Exception {
		Socio socio = this.getSocio(idSocio);
		String texto = "Fecha\tHora\tEntrenador\tEstado\tNota\n";

		for(SesionEntrenamiento sesionEntrenamiento : this.sesiones) {
			for(AsistenciaEntrenamiento asistenciaEntrenamiento : sesionEntrenamiento.getAsistencias()) {
				if(asistenciaEntrenamiento.getSocio().getIdSocio().equals(socio.getIdSocio())) {
					texto += sesionEntrenamiento.getFecha() + "\t";
					texto += sesionEntrenamiento.getHora() + "\t";
					texto += sesionEntrenamiento.getEntrenador().getNombre() + "\t";
					texto += asistenciaEntrenamiento.getEstado() + "\t";

					if(asistenciaEntrenamiento.getEvaluacion() != null) {
						texto += asistenciaEntrenamiento.getEvaluacion().getNota();
					}else {
						texto += "Sin evaluacion";
					}
					texto += "\n";
				}
			}
		}

		return texto;
	}

	public String consultarTurnos(String idEmpleado) throws Exception {
		Empleado empleado = this.getEmpleado(idEmpleado);
		String texto = "Dia\tInicio\tFin\n";

		for(Turno turno : empleado.getTurnos()) {
			texto += turno.getDiaSemana() + "\t";
			texto += turno.getHoraInicio() + "\t";
			texto += turno.getHoraFin() + "\n";
		}

		return texto;
	}

	public String consultarMensualidades(String idSocio) throws Exception {
		Socio socio = this.getSocio(idSocio);
		String texto = "Mes\tValor\tFecha de pago\n";

		for(Mensualidad mensualidad : socio.getMensualidades()) {
			texto += mensualidad.getMes() + "\t";
			texto += mensualidad.getValor() + "\t";
			texto += mensualidad.getFechaPago() + "\n";
		}

		return texto;
	}

	public void registrarResultadoInterno(PartidoInterno partidoInterno, String marcador) {
		partidoInterno.setMarcador(marcador);
	}

	public PartidoCompetencia programarPartidoCompetencia(Date fecha, String nombreEquipo, String nombreCompetencia, String rival, String escenario) throws Exception {
		Equipo equipo = this.getEquipo(nombreEquipo);
		Competencia competencia = this.getCompetencia(nombreCompetencia);
		boolean equipoInscrito = false;

		for(InscripcionCompetencia inscripcionCompetencia : competencia.getInscripciones()) {
			if(inscripcionCompetencia.getEquipo().getNombre().equals(nombreEquipo) && inscripcionCompetencia.getEstado().equals("Activa")) {
				equipoInscrito = true;
			}
		}

		if(equipoInscrito) {
			PartidoCompetencia partidoCompetencia = new PartidoCompetencia(fecha, equipo, competencia, rival, escenario);
			competencia.agregarPartido(partidoCompetencia);
			this.partidosCompetencia.add(partidoCompetencia);
			return partidoCompetencia;
		}else {
			throw new Exception("El equipo no esta inscrito en la competencia");
		}
	}

	public void registrarResultadoCompetencia(PartidoCompetencia partidoCompetencia, String marcador) throws Exception {
		partidoCompetencia.setMarcador(marcador);

		if(partidoCompetencia.getCompetencia().getFormato().equals("Liga")) {
			String[] resultado = marcador.split("-");
			int puntosEquipo = Integer.parseInt(resultado[0]);
			int puntosRival = Integer.parseInt(resultado[1]);

			TablaPosiciones tablaPosiciones = partidoCompetencia.getCompetencia().buscarFila(partidoCompetencia.getEquipo());

			if(tablaPosiciones != null) {
				if(puntosEquipo > puntosRival) {
					tablaPosiciones.sumarPuntos(3);
				}else if(puntosEquipo == puntosRival) {
					if(partidoCompetencia.getEquipo().getDeporte() instanceof Futbol) {
						tablaPosiciones.sumarPuntos(1);
					}else {
						throw new Exception("En este deporte no puede haber empate");
					}
				}
			}
		}
	}

	public String consultarCompetencias() {
		String texto = "Competencia\tDeporte\tCategoria\tFormato\n";

		for(Competencia competencia : this.competencias.values()) {
			texto += competencia.getNombre() + "\t";
			texto += competencia.getDeporte().getNombre() + "\t";
			texto += competencia.getCategoriaHabilitada() + "\t";
			texto += competencia.getFormato() + "\n";
		}

		return texto;
	}

	public String consultarInventario() {
		String texto = "Articulo\tUbicacion\tCantidad\n";

		for(ExistenciaInventario existenciaInventario : this.existencias) {
			texto += existenciaInventario.getArticulo().getNombre() + "\t";
			texto += existenciaInventario.getUbicacion().getNombre() + "\t";
			texto += existenciaInventario.getCantidad() + "\n";
		}

		return texto;
	}

	public String consultarHistorial() {
		String texto = "Reservas\t" + this.reservas.size() + "\n";
		texto += "Partidos de practica\t" + this.partidosPractica.size() + "\n";
		texto += "Partidos internos\t" + this.partidosInternos.size() + "\n";
		texto += "Partidos de competencia\t" + this.partidosCompetencia.size() + "\n";
		texto += "Sesiones de entrenamiento\t" + this.sesiones.size() + "\n";

		return texto;
	}

	public String generarReporteVentas() {
		double totalTienda = 0;
		double totalCafeteria = 0;
		double totalMensualidades = 0;

		for(Venta venta : this.ventas) {
			if(venta instanceof VentaTienda) {
				totalTienda += venta.getTotal();
			}else if(venta instanceof VentaCafeteria) {
				totalCafeteria += venta.getTotal();
			}
		}

		for(Socio socio : this.socios.values()) {
			for(Mensualidad mensualidad : socio.getMensualidades()) {
				totalMensualidades += mensualidad.getValor();
			}
		}

		String texto = "Tienda\t" + totalTienda + "\n";
		texto += "Cafeteria\t" + totalCafeteria + "\n";
		texto += "Mensualidades\t" + totalMensualidades + "\n";

		return texto;
	}

	public String generarReporteDeportivo() {
		String texto = "Sesiones\t" + this.sesiones.size() + "\n";
		texto += "Partidos internos\t" + this.partidosInternos.size() + "\n";
		texto += "Partidos de competencia\t" + this.partidosCompetencia.size() + "\n";
		texto += "Partidos de practica\t" + this.partidosPractica.size() + "\n";

		return texto;
	}
	
	public Socio getSocio(String idSocio) throws Exception {
		Socio socioEncontrado = this.socios.get(idSocio);

		if(socioEncontrado == null) {
			throw new Exception("Socio no existe");
		}

		return socioEncontrado;
	}

	public Empleado getEmpleado(String idEmpleado) throws Exception {
		Empleado empleadoEncontrado = this.empleados.get(idEmpleado);

		if(empleadoEncontrado == null) {
			throw new Exception("Empleado no existe");
		}

		return empleadoEncontrado;
	}

	public Administrador getAdministrador(String idAdministrador) throws Exception {
		Administrador administradorEncontrado = this.administradores.get(idAdministrador);

		if(administradorEncontrado == null) {
			throw new Exception("Administrador no existe");
		}

		return administradorEncontrado;
	}

	public Equipo getEquipo(String nombre) throws Exception {
		Equipo equipoEncontrado = this.equipos.get(nombre);

		if(equipoEncontrado == null) {
			throw new Exception("Equipo no existe");
		}

		return equipoEncontrado;
	}

	public Competencia getCompetencia(String nombre) throws Exception {
		Competencia competenciaEncontrada = this.competencias.get(nombre);

		if(competenciaEncontrada == null) {
			throw new Exception("Competencia no existe");
		}

		return competenciaEncontrada;
	}

	public DisciplinaIndividual getDisciplina(String nombre) throws Exception {
		DisciplinaIndividual disciplinaEncontrada = this.disciplinas.get(nombre);

		if(disciplinaEncontrada == null) {
			throw new Exception("Disciplina no existe");
		}

		return disciplinaEncontrada;
	}

	public Instalacion getInstalacion(String idInstalacion) throws Exception {
		Instalacion instalacionEncontrada = this.instalaciones.get(idInstalacion);

		if(instalacionEncontrada == null) {
			throw new Exception("Instalacion no existe");
		}

		return instalacionEncontrada;
	}

	public ProductoVenta getProducto(String nombre) throws Exception {
		ProductoVenta productoEncontrado = this.productos.get(nombre);

		if(productoEncontrado == null) {
			throw new Exception("Producto no existe");
		}

		return productoEncontrado;
	}

	public ArticuloTienda getArticulo(String nombre) throws Exception {
		ProductoVenta productoEncontrado = getProducto(nombre);

		if(!(productoEncontrado instanceof ArticuloTienda)) {
			throw new Exception("No es articulo de tienda");
		}

		return (ArticuloTienda) productoEncontrado;
	}

	public UbicacionInventario getUbicacion(String nombre) throws Exception {
		UbicacionInventario ubicacionEncontrada = this.ubicaciones.get(nombre);

		if(ubicacionEncontrada == null) {
			throw new Exception("Ubicacion no existe");
		}

		return ubicacionEncontrada;
	}

	public HashMap<String, Usuario> getUsuarios() {
		return usuarios;
	}

	public HashMap<String, Socio> getSocios() {
		return socios;
	}

	public HashMap<String, Empleado> getEmpleados() {
		return empleados;
	}

	public HashMap<String, Equipo> getEquipos() {
		return equipos;
	}

	public HashMap<String, ProductoVenta> getProductos() {
		return productos;
	}

	public HashMap<String, UbicacionInventario> getUbicaciones() {
		return ubicaciones;
	}

	public ArrayList<Venta> getVentas() {
		return ventas;
	}

	public ArrayList<Reserva> getReservas() {
		return reservas;
	}

	public ArrayList<SesionEntrenamiento> getSesiones() {
		return sesiones;
	}

	public ArrayList<ExistenciaInventario> getExistencias() {
		return existencias;
	}

	public ArchivoPlano getArchivoPlano() {
		return archivoPlano;
	}

	public ArchivoSerializable getArchivoSerializable() {
		return archivoSerializable;
	}

	public void escribirUsuarios() {
		ArrayList<String> datos = new ArrayList<String>();

		for(Usuario usuario : this.usuarios.values()) {
			datos.add(usuario.getLogin() + "," + usuario.getNombre());
		}

		this.archivoPlano.escribir("usuarios.csv", datos);
	}

	public void escribirSerializable() {
		ArrayList<Object> datos = new ArrayList<Object>();

		datos.add(this.usuarios);
		datos.add(this.socios);
		datos.add(this.empleados);
		datos.add(this.administradores);
		datos.add(this.equipos);
		datos.add(this.competencias);
		datos.add(this.disciplinas);
		datos.add(this.instalaciones);
		datos.add(this.productos);
		datos.add(this.ubicaciones);
		datos.add(this.ventas);
		datos.add(this.reservas);
		datos.add(this.partidosPractica);
		datos.add(this.partidosInternos);
		datos.add(this.partidosCompetencia);
		datos.add(this.sesiones);
		datos.add(this.existencias);
		datos.add(this.movimientos);
		datos.add(this.reabastecimientos);
		datos.add(this.solicitudes);
		datos.add(this.sugerencias);

		this.archivoSerializable.escribir(datos, "clubDeportivoSeneca.bin");
	}

	public void leerSerializable() {
		ArrayList<Object> datos = (ArrayList<Object>) this.archivoSerializable.leer("clubDeportivoSeneca.bin");

		if(datos != null) {
			this.usuarios = (HashMap<String, Usuario>) datos.get(0);
			this.socios = (HashMap<String, Socio>) datos.get(1);
			this.empleados = (HashMap<String, Empleado>) datos.get(2);
			this.administradores = (HashMap<String, Administrador>) datos.get(3);
			this.equipos = (HashMap<String, Equipo>) datos.get(4);
			this.competencias = (HashMap<String, Competencia>) datos.get(5);
			this.disciplinas = (HashMap<String, DisciplinaIndividual>) datos.get(6);
			this.instalaciones = (HashMap<String, Instalacion>) datos.get(7);
			this.productos = (HashMap<String, ProductoVenta>) datos.get(8);
			this.ubicaciones = (HashMap<String, UbicacionInventario>) datos.get(9);
			this.ventas = (ArrayList<Venta>) datos.get(10);
			this.reservas = (ArrayList<Reserva>) datos.get(11);
			this.partidosPractica = (ArrayList<PartidoPractica>) datos.get(12);
			this.partidosInternos = (ArrayList<PartidoInterno>) datos.get(13);
			this.partidosCompetencia = (ArrayList<PartidoCompetencia>) datos.get(14);
			this.sesiones = (ArrayList<SesionEntrenamiento>) datos.get(15);
			this.existencias = (ArrayList<ExistenciaInventario>) datos.get(16);
			this.movimientos = (ArrayList<MovimientoInventario>) datos.get(17);
			this.reabastecimientos = (ArrayList<Reabastecimiento>) datos.get(18);
			this.solicitudes = (ArrayList<SolicitudCambioTurno>) datos.get(19);
			this.sugerencias = (ArrayList<SugerenciaProducto>) datos.get(20);
		}
	}

	public String consultarUsuarios() {
		String texto = "Login\tNombre\n";
		for(Usuario usuario : this.usuarios.values()) {
			texto+=usuario+"\n";
		}
		return texto;
	}

	public String consultarEquipos() {
		String texto = "Nombre\tDeporte\tCategoria\tJugadores\n";
		for(Equipo equipo : this.equipos.values()) {
			texto+=equipo+"\n";
		}
		return texto;
	}
}
