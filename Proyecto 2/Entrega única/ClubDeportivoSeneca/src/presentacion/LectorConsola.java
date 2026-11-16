package presentacion;

import java.sql.Date;
import java.sql.Time;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.Scanner;

/**
 * Lee y valida todo lo que el usuario digita en consola.
 * Ningun metodo deja pasar un dato invalido: si el dato esta mal se muestra
 * un mensaje y se vuelve a pedir, asi los menus nunca se caen.
 */
public class LectorConsola {
	private Scanner sc;

	public LectorConsola() {
		this.sc = new Scanner(System.in);
	}

	/**
	 * Lee una linea cruda. Si la entrada se cierra (por ejemplo, se detiene la
	 * consola), lanza una excepcion para que la aplicacion guarde y termine.
	 */
	private String leerLinea(String mensaje) {
		System.out.print(mensaje + ": ");
		if(!this.sc.hasNextLine()) {
			throw new EntradaCerradaException();
		}
		return this.sc.nextLine().trim();
	}

	public String leerTexto(String mensaje) {
		String texto = this.leerLinea(mensaje);

		while(texto.isEmpty()) {
			System.out.println("  -> El dato no puede estar vacio.");
			texto = this.leerLinea(mensaje);
		}

		return texto;
	}

	/**
	 * Lee un texto que puede quedar vacio. Si el usuario no escribe nada retorna null.
	 */
	public String leerTextoOpcional(String mensaje) {
		String texto = this.leerLinea(mensaje + " (Enter para omitir)");

		if(texto.isEmpty()) {
			return null;
		}

		return texto;
	}

	public int leerEntero(String mensaje, int minimo, int maximo) {
		while(true) {
			String texto = this.leerLinea(mensaje);

			try {
				int numero = Integer.parseInt(texto);

				if(numero < minimo || numero > maximo) {
					System.out.println("  -> Debe ser un numero entre " + minimo + " y " + maximo + ".");
				}else {
					return numero;
				}
			} catch (NumberFormatException e) {
				System.out.println("  -> Debe digitar un numero entero.");
			}
		}
	}

	public double leerDecimal(String mensaje, double minimo, double maximo) {
		while(true) {
			String texto = this.leerLinea(mensaje).replace(",", ".");

			try {
				double numero = Double.parseDouble(texto);

				if(Double.isNaN(numero) || numero < minimo || numero > maximo) {
					System.out.println("  -> Debe ser un numero entre " + minimo + " y " + maximo + ".");
				}else {
					return numero;
				}
			} catch (NumberFormatException e) {
				System.out.println("  -> Debe digitar un numero. Ejemplo: 4.5");
			}
		}
	}

	/**
	 * Lee una fecha con formato AAAA-MM-DD y verifica que exista
	 * (por ejemplo, rechaza 2026-02-30).
	 */
	public Date leerFecha(String mensaje) {
		while(true) {
			String texto = this.leerLinea(mensaje + " (AAAA-MM-DD)");

			try {
				LocalDate fecha = LocalDate.parse(texto);
				return Date.valueOf(fecha);
			} catch (DateTimeParseException e) {
				System.out.println("  -> Fecha invalida. Ejemplo: 2026-11-20");
			}
		}
	}

	/**
	 * Lee una hora con formato HH:MM en formato de 24 horas.
	 */
	public Time leerHora(String mensaje) {
		while(true) {
			String texto = this.leerLinea(mensaje + " (HH:MM)");

			try {
				LocalTime hora = LocalTime.parse(texto);
				return Time.valueOf(hora);
			} catch (DateTimeParseException e) {
				System.out.println("  -> Hora invalida. Use formato de 24 horas, ejemplo: 14:30");
			}
		}
	}

	public boolean leerSiNo(String mensaje) {
		while(true) {
			String texto = this.leerLinea(mensaje + " (S/N)").toUpperCase();

			if(texto.equals("S") || texto.equals("SI")) {
				return true;
			}
			if(texto.equals("N") || texto.equals("NO")) {
				return false;
			}

			System.out.println("  -> Responda S o N.");
		}
	}

	/**
	 * Muestra una lista numerada y retorna el texto de la opcion elegida.
	 */
	public String leerOpcion(String mensaje, String[] opciones) {
		System.out.println(mensaje + ":");

		for(int i = 0; i < opciones.length; i++) {
			System.out.println("  " + (i + 1) + ". " + opciones[i]);
		}

		int seleccion = this.leerEntero("Seleccione", 1, opciones.length);
		return opciones[seleccion - 1];
	}

	/**
	 * Igual que leerOpcion pero recibe una lista. Retorna la posicion elegida (desde 0).
	 */
	public int leerPosicion(String mensaje, ArrayList<String> opciones) {
		System.out.println(mensaje + ":");

		for(int i = 0; i < opciones.size(); i++) {
			System.out.println("  " + (i + 1) + ". " + opciones.get(i));
		}

		return this.leerEntero("Seleccione", 1, opciones.size()) - 1;
	}

	public void cerrar() {
		this.sc.close();
	}
}
