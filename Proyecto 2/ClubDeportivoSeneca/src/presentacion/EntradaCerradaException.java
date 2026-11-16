package presentacion;

/**
 * Se lanza cuando la entrada de la consola se cierra antes de tiempo.
 * Permite que la aplicacion guarde los datos antes de terminar.
 */
public class EntradaCerradaException extends RuntimeException {
	private static final long serialVersionUID = 1L;

	public EntradaCerradaException() {
		super("La entrada de datos se cerro");
	}
}
