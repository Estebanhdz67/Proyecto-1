package persistencia;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InvalidClassException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

public class ArchivoSerializable {

	public void escribir(Object objeto, String nombreArchivo) {
		try {
			ObjectOutputStream salida = new ObjectOutputStream(new FileOutputStream("datos/" + nombreArchivo));
			salida.writeObject(objeto);
			salida.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public Object leer(String nombreArchivo) {
		Object objeto = null;

		try {
			ObjectInputStream entrada = new ObjectInputStream(new FileInputStream("datos/" + nombreArchivo));
			objeto = entrada.readObject();
			entrada.close();
		} catch (FileNotFoundException e) {
			System.out.println("No existe el archivo de datos " + nombreArchivo + ".");
		} catch (InvalidClassException e) {
			System.out.println("Los datos guardados no son compatibles con la version actual del programa.");
		} catch (IOException e) {
			System.out.println("No se pudo leer el archivo de datos " + nombreArchivo + ".");
		} catch (ClassNotFoundException e) {
			System.out.println("No se pudo cargar la informacion guardada.");
		}

		return objeto;
	}
}
