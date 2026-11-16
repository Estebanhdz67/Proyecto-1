package logica;

import java.io.Serializable;

import java.sql.Date;
import java.util.ArrayList;

public class CuentaFidelidad implements Serializable {
	private int puntos;
	private ArrayList<MovimientoPuntos> movimientos;

	public CuentaFidelidad() {
		this.puntos = 0;
		this.movimientos = new ArrayList<MovimientoPuntos>();
	}

	public int getPuntos() {
		return puntos;
	}

	public ArrayList<MovimientoPuntos> getMovimientos() {
		return movimientos;
	}

	public void generarPuntos(int cantidad) {
		if(cantidad>0) {
			this.puntos+=cantidad;
			this.movimientos.add(new MovimientoPuntos(new Date(System.currentTimeMillis()), cantidad, "Generado"));
		}
	}

	public void redimirPuntos(int cantidad) throws Exception {
		if(cantidad<0||cantidad>this.puntos) {
			throw new Exception("Puntos insuficientes");
		}
		this.puntos-=cantidad;
		this.movimientos.add(new MovimientoPuntos(new Date(System.currentTimeMillis()), cantidad, "Redimido"));
	}
}
