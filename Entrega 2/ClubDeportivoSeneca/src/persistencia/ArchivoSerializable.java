package persistencia;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
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
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}

		return objeto;
	}
}
