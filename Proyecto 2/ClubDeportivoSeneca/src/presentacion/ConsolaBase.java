package presentacion;

import java.util.ArrayList;
import java.util.HashMap;

import logica.BonoDescuento;
import logica.ClubDeportivoSeneca;
import logica.ParticipanteTorneo;
import logica.ProductoCafeteria;
import logica.ProductoVenta;
import logica.Usuario;
import logica.Venta;

/**
 * Clase padre de las tres consolas. Tiene el flujo que todas comparten:
 * cargar datos -> iniciar sesion -> menu -> guardar datos.
 * Cada consola hija solo define que usuarios pueden entrar, sus opciones
 * y que hace cada opcion.
 */
public abstract class ConsolaBase {
	private static final int INTENTOS_LOGIN = 3;

	protected ClubDeportivoSeneca club;
	protected LectorConsola lector;

	public ConsolaBase() {
		this.club = new ClubDeportivoSeneca();
		this.lector = new LectorConsola();
	}

	// ------------------------------------------------------------------
	// Metodos que cada consola debe implementar
	// ------------------------------------------------------------------

	protected abstract String getNombreConsola();

	protected abstract boolean puedeIngresar(Usuario usuario);

	protected abstract void prepararSesion(Usuario usuario) throws Exception;

	protected abstract String[] getOpcionesMenu();

	protected abstract void ejecutarOpcion(String opcion) throws Exception;

	// ------------------------------------------------------------------
	// Flujo general
	// ------------------------------------------------------------------

	public void ejecutar() {
		this.cargarDatos();

		System.out.println("==============================================");
		System.out.println("  CLUB DEPORTIVO SENECA - " + this.getNombreConsola());
		System.out.println("==============================================");

		try {
			if(this.iniciarSesion()) {
				this.mostrarMenu("MENU PRINCIPAL", this.getOpcionesMenu(), "Salir");
				this.club.cerrarSesion();
				System.out.println("Sesion cerrada.");
			}
		} catch (EntradaCerradaException e) {
			System.out.println("\n" + e.getMessage() + ".");
		}

		this.guardarDatos();
		this.lector.cerrar();
	}

	private void cargarDatos() {
		this.club.leerSerializable();

		if(this.club.getUsuarios().isEmpty()) {
			DatosIniciales.cargar(this.club);
			System.out.println("No habia datos guardados: se cargaron los datos iniciales del club.");
		}
	}

	private void guardarDatos() {
		this.club.escribirSerializable();
		this.club.escribirUsuarios();
		System.out.println("Cambios guardados. Hasta pronto.");
	}

	private boolean iniciarSesion() {
		for(int intento = 1; intento <= INTENTOS_LOGIN; intento++) {
			System.out.println("\nINICIAR SESION (intento " + intento + " de " + INTENTOS_LOGIN + ")");
			String login = this.lector.leerTexto("Login");
			String password = this.lector.leerTexto("Password");

			try {
				Usuario usuario = this.club.iniciarSesion(login, password);

				if(!this.puedeIngresar(usuario)) {
					this.club.cerrarSesion();
					System.out.println("Este usuario no tiene acceso a la consola de " + this.getNombreConsola().toLowerCase() + ".");
				}else {
					this.prepararSesion(usuario);
					System.out.println("Bienvenido(a), " + usuario.getNombre() + ".");
					return true;
				}
			} catch (Exception e) {
				System.out.println("No se pudo iniciar sesion: " + e.getMessage());
			}
		}

		System.out.println("Se agotaron los intentos de inicio de sesion.");
		return false;
	}

	/**
	 * Muestra un menu y ejecuta la opcion elegida hasta que el usuario digite 0.
	 * Se usa tanto para el menu principal como para los submenus.
	 */
	protected void mostrarMenu(String titulo, String[] opciones, String textoSalida) {
		int seleccion;

		do {
			System.out.println("\n---------- " + titulo + " ----------");
			for(int i = 0; i < opciones.length; i++) {
				System.out.println((i + 1) + ". " + opciones[i]);
			}
			System.out.println("0. " + textoSalida);

			seleccion = this.lector.leerEntero("Seleccione una opcion", 0, opciones.length);

			if(seleccion != 0) {
				this.ejecutarOpcionSegura(opciones[seleccion - 1]);
			}
		}while(seleccion != 0);
	}

	/**
	 * Ejecuta una opcion y, si la logica rechaza la operacion, muestra el motivo
	 * en lugar de dejar que el programa se caiga.
	 */
	private void ejecutarOpcionSegura(String opcion) {
		try {
			System.out.println("\n>> " + opcion);
			this.ejecutarOpcion(opcion);
		} catch (EntradaCerradaException e) {
			throw e;
		} catch (Exception e) {
			String mensaje = e.getMessage();

			if(mensaje == null || mensaje.isEmpty()) {
				mensaje = "No se pudo completar la operacion.";
			}

			System.out.println("[!] Operacion rechazada: " + mensaje);
		}
	}

	protected void mostrarExito(String mensaje) {
		System.out.println("[OK] " + mensaje);
	}

	// ------------------------------------------------------------------
	// Opciones de torneos que comparten las tres consolas
	// ------------------------------------------------------------------

	protected void consultarTorneos() {
		if(this.club.getTorneos().isEmpty()) {
			System.out.println("Todavia no hay torneos registrados.");
		}else {
			System.out.println(this.club.consultarTorneos());
		}
	}

	protected String leerIdTorneo() throws Exception {
		this.consultarTorneos();
		String idTorneo = this.lector.leerTexto("Id del torneo");
		this.club.getTorneo(idTorneo);
		return idTorneo;
	}

	protected void consultarHistorialTorneo() throws Exception {
		String idTorneo = this.leerIdTorneo();
		System.out.println(this.club.consultarHistorialTorneo(idTorneo));
	}

	protected void inscribirseTorneo(ParticipanteTorneo participante) throws Exception {
		String idTorneo = this.leerIdTorneo();
		boolean preferencial = this.lector.leerSiNo("Desea usar un cupo preferencial (por ranking)?");
		this.club.inscribirParticipanteTorneo(idTorneo, participante, preferencial);
		this.mostrarExito(participante.getNombreParticipante() + " quedo inscrito en el torneo " + idTorneo + ".");
	}

	protected void retirarseTorneo(ParticipanteTorneo participante) throws Exception {
		String idTorneo = this.leerIdTorneo();
		this.club.desinscribirParticipanteTorneo(idTorneo, participante);
		this.mostrarExito(participante.getNombreParticipante() + " se retiro del torneo " + idTorneo + " y el cupo quedo libre.");
	}

	// ------------------------------------------------------------------
	// Compras: las usan socios y empleados
	// ------------------------------------------------------------------

	private void mostrarProductos(boolean cafeteria) {
		System.out.println(cafeteria ? "Productos de cafeteria:" : "Articulos de tienda:");

		for(ProductoVenta producto : this.club.getProductos().values()) {
			if((producto instanceof ProductoCafeteria) == cafeteria) {
				System.out.println("  - " + producto.getNombre() + " ($" + producto.getPrecio() + ")");
			}
		}
	}

	/**
	 * Arma el carrito de compra. Valida que cada producto exista y sea del tipo
	 * correcto (tienda o cafeteria) antes de agregarlo.
	 */
	protected HashMap<String, Integer> leerCarrito(boolean cafeteria) {
		HashMap<String, Integer> carrito = new HashMap<String, Integer>();
		boolean seguir = true;

		this.mostrarProductos(cafeteria);

		while(seguir) {
			String nombre = this.lector.leerTexto("Nombre del producto");

			try {
				ProductoVenta producto = this.club.getProducto(nombre);

				if((producto instanceof ProductoCafeteria) != cafeteria) {
					System.out.println("  -> Ese producto no se vende en " + (cafeteria ? "la cafeteria." : "la tienda."));
				}else {
					int cantidad = this.lector.leerEntero("Cantidad", 1, 999);
					int anterior = carrito.containsKey(nombre) ? carrito.get(nombre) : 0;
					carrito.put(nombre, anterior + cantidad);
				}
			} catch (Exception e) {
				System.out.println("  -> " + e.getMessage() + ".");
			}

			seguir = carrito.isEmpty() || this.lector.leerSiNo("Desea agregar otro producto?");
		}

		return carrito;
	}

	// ------------------------------------------------------------------
	// Bonos de torneos recreativos: los usan socios y empleados
	// ------------------------------------------------------------------

	protected void consultarBonos(ParticipanteTorneo participante) {
		if(this.club.getBonos(participante).isEmpty()) {
			System.out.println("No tiene bonos. Los bonos se ganan al quedar primero en un torneo recreativo.");
			return;
		}

		System.out.println("Codigo\tDescuento\tEstado");
		for(BonoDescuento bono : this.club.getBonos(participante)) {
			System.out.println(bono.getCodigo() + "\t" + Math.round(bono.getPorcentaje() * 100) + "%\t"
					+ (bono.estaUsado() ? "Usado" : "Disponible"));
		}
	}

	/**
	 * Si el participante tiene bonos sin usar, le pregunta si quiere usar uno.
	 * Retorna el codigo del bono elegido o null si no usa ninguno.
	 */
	protected String leerBono(ParticipanteTorneo participante) {
		ArrayList<BonoDescuento> disponibles = new ArrayList<BonoDescuento>();
		ArrayList<String> descripciones = new ArrayList<String>();

		for(BonoDescuento bono : this.club.getBonos(participante)) {
			if(!bono.estaUsado()) {
				disponibles.add(bono);
				descripciones.add(bono.getCodigo() + " (" + Math.round(bono.getPorcentaje() * 100) + "% de descuento)");
			}
		}

		if(disponibles.isEmpty() || !this.lector.leerSiNo("Tiene bonos de torneo disponibles. Desea usar uno?")) {
			return null;
		}

		String codigo = disponibles.get(this.lector.leerPosicion("Bono", descripciones)).getCodigo();
		System.out.println("El bono no se acumula con otros descuentos: no se usara codigo de empleado ni puntos.");
		return codigo;
	}

	protected void mostrarVenta(Venta venta) {
		System.out.println("Productos: " + venta.getDetalles().size());
		System.out.printf("Total a pagar: $%,.2f%n", venta.getTotal());
	}
}
