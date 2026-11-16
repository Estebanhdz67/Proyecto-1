package logica;

import java.io.Serializable;

public class Sancion implements Serializable {
	private String motivo;
	private boolean activa;
	private int partidosPendientes;
	private Socio socio;
	private Partido partidoOrigen;

	public Sancion(String motivo, int partidosPendientes, Socio socio, Partido partidoOrigen) {
		this.motivo = motivo;
		this.partidosPendientes = partidosPendientes;
		this.socio = socio;
		this.partidoOrigen = partidoOrigen;
		this.activa = true;
	}

	public String getMotivo() {
		return motivo;
	}

	public boolean isActiva() {
		return this.activa;
	}

	public int getPartidosPendientes() {
		return partidosPendientes;
	}

	public Socio getSocio() {
		return socio;
	}

	public Partido getPartidoOrigen() {
		return partidoOrigen;
	}

	public void cumplirPartido() {
		if(this.partidosPendientes>0) {
			this.partidosPendientes--;
		}
		if(this.partidosPendientes==0) {
			this.activa = false;
		}
	}
}
