package logica;

import java.io.Serializable;

public class MesaTenisMesa extends Instalacion implements Serializable {
	public MesaTenisMesa(String id, int capacidad, boolean techada) {
		super(id, capacidad, techada);
	}
}
