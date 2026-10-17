package logica;

public class Socio extends Usuario {

	private int edad;
	
	public Socio(int id, String nombre, String apellido, int edad) {
		super(id, nombre, apellido);
		this.edad = edad;
	}

	public int getEdad() {
		return edad;
	}

	public void setEdad(int edad) {
		this.edad = edad;
	}
	
	@Override
	public String toString() {
		return super.toString() + "\t" + this.edad;
	}
}