package logica;

import java.io.Serializable;

public class CanchaConjunto extends Instalacion implements Serializable {
	public CanchaConjunto(String id, int capacidad, boolean techada) {
		super(id, capacidad, techada);
	}
}
