package logica;

public class InscripcionEquipo {

	private int id;
	private Socio socio;
	private int numeroCamiseta;
	private String posicion;
	
	public InscripcionEquipo(int id, Socio socio, int numeroCamiseta, String posicion) {
		this.id = id;
		this.socio = socio;
		this.numeroCamiseta = numeroCamiseta;
		this.posicion = posicion;
	}

	public int getId() {
		return id;
	}

	public Socio getSocio() {
		return socio;
	}

	public int getNumeroCamiseta() {
		return numeroCamiseta;
	}

	public String getPosicion() {
		return posicion;
	}
	
	@Override
	public String toString() {
		return "\t" + this.id + "\t" + this.socio.getNombre() + "\t" +
				this.numeroCamiseta + "\t" + this.posicion;
	}
}