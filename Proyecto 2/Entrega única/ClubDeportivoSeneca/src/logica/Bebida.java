package logica;

import java.io.Serializable;

public class Bebida extends ProductoCafeteria implements Serializable {
	private String temperatura;

	public Bebida(String nombre, double precio, String temperatura) {
		super(nombre, precio);
		this.temperatura = temperatura;
	}

	public String getTemperatura() {
		return temperatura;
	}
}
