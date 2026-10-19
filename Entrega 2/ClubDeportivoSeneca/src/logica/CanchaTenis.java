package logica;

import java.io.Serializable;

public class CanchaTenis extends Instalacion implements Serializable {
	public CanchaTenis(String id, int capacidad, boolean techada) {
		super(id, capacidad, techada);
	}
}
