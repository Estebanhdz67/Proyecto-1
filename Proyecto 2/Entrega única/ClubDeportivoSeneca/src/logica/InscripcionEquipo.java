package logica;

import java.io.Serializable;

import java.sql.Date;

public class InscripcionEquipo extends Inscripcion implements Serializable {
	private Socio socio;
	private Equipo equipo;
	private String posicion;
	private int numeroCamiseta;

	public InscripcionEquipo(Date fecha, String estado, Socio socio, Equipo equipo, String posicion, int numeroCamiseta) {
		super(fecha, estado);
		this.socio = socio;
		this.equipo = equipo;
		this.posicion = posicion;
		this.numeroCamiseta = numeroCamiseta;
	}

	public Socio getSocio() {
		return socio;
	}

	public Equipo getEquipo() {
		return equipo;
	}

	public String getPosicion() {
		return posicion;
	}

	public int getNumeroCamiseta() {
		return numeroCamiseta;
	}

	@Override
	public String toString() {
		return this.socio.getNombre()+"\t"+this.posicion+"\t"+this.numeroCamiseta+"\t"+getEstado();
	}
}
