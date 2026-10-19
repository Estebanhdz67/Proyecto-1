package persistencia;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

public class ArchivoPlano {

	public void escribir(String nombreArchivo, ArrayList<String> datos) {
		try {
			BufferedWriter escritor = new BufferedWriter(new FileWriter("datos/" + nombreArchivo));

			for(String dato : datos) {
				escritor.write(dato);
				escritor.newLine();
			}

			escritor.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public ArrayList<String> leer(String nombreArchivo) {
		ArrayList<String> datos = new ArrayList<String>();

		try {
			BufferedReader lector = new BufferedReader(new FileReader("datos/" + nombreArchivo));
			String linea;

			while((linea = lector.readLine()) != null) {
				datos.add(linea);
			}

			lector.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

		return datos;
	}
}
