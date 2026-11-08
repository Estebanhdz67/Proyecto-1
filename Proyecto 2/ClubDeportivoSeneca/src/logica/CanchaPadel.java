package logica;

import java.io.Serializable;

public class CanchaPadel extends Instalacion implements Serializable {
	public CanchaPadel(String id, int capacidad, boolean techada) {
		super(id, capacidad, techada);
	}
}
