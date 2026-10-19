package logica;

import java.io.Serializable;

import java.sql.Date;
import java.util.ArrayList;

public class Equipo implements Serializable {
	private String nombre;
	private int cupoMaximo;
	private DeporteConjunto deporte;
	private String categoria;
	private ArrayList<InscripcionEquipo> inscripciones;

	public Equipo(String nombre, int cupoMaximo, DeporteConjunto deporte, String categoria) {
		this.nombre = nombre;
		this.cupoMaximo = cupoMaximo;
		this.deporte = deporte;
		this.categoria = categoria;
		this.inscripciones = new ArrayList<InscripcionEquipo>();
	}

	public String getNombre() {
		return nombre;
	}

	public int getCupoMaximo() {
		return cupoMaximo;
	}

	public DeporteConjunto getDeporte() {
		return deporte;
	}

	public String getCategoria() {
		return categoria;
	}

	public ArrayList<InscripcionEquipo> getInscripciones() {
		return inscripciones;
	}

	public boolean correspondeEdad(int edad) {
		if(this.categoria.equals("Sub13")) {
			return edad < 13;
		}
		if(this.categoria.equals("Sub15")) {
			return edad >= 13 && edad < 15;
		}
		if(this.categoria.equals("Sub17")) {
			return edad >= 15 && edad < 17;
		}
		if(this.categoria.equals("Mayores")) {
			return edad >= 17;
		}
		return false;
	}

	public void inscribirJugador(Socio socio, String posicion, int numeroCamiseta) throws Exception {
		if(cantidadJugadoresActivos() >= this.cupoMaximo) {
			throw new Exception("El equipo no tiene cupos disponibles");
		}
		int edad = socio.calcularEdad(new Date(System.currentTimeMillis()));
		if(!correspondeEdad(edad)) {
			throw new Exception("La edad del socio no corresponde a la categoria");
		}
		for(InscripcionEquipo inscripcion : this.inscripciones) {
			if(inscripcion.getEstado().equals("Activa") && inscripcion.getNumeroCamiseta() == numeroCamiseta) {
				throw new Exception("El numero de camiseta ya esta ocupado");
			}
			if(inscripcion.getEstado().equals("Activa") && inscripcion.getSocio().getIdSocio().equals(socio.getIdSocio())) {
				throw new Exception("El socio ya esta inscrito en el equipo");
			}
		}
		InscripcionEquipo nueva = new InscripcionEquipo(new Date(System.currentTimeMillis()), "Activa", socio, this, posicion, numeroCamiseta);
		this.inscripciones.add(nueva);
		socio.agregarInscripcionEquipo(nueva);
	}

	public int cantidadJugadoresActivos() {
		int cantidad = 0;
		for(InscripcionEquipo inscripcionEquipo : this.inscripciones) {
			if(inscripcionEquipo.getEstado().equals("Activa")) {
				cantidad++;
			}
		}
		return cantidad;
	}

	public int cantidadJugadoresHabilitados() {
		int cantidad = 0;
		for(InscripcionEquipo inscripcionEquipo : this.inscripciones) {
			if(inscripcionEquipo.getEstado().equals("Activa") && !inscripcionEquipo.getSocio().tieneSancionActiva()) {
				cantidad++;
			}
		}
		return cantidad;
	}

	public boolean contieneSocio(Socio socio) {
		for(InscripcionEquipo inscripcionEquipo : this.inscripciones) {
			if(inscripcionEquipo.getEstado().equals("Activa") && inscripcionEquipo.getSocio().getIdSocio().equals(socio.getIdSocio())) {
				return true;
			}
		}
		return false;
	}

	public String consultarJugadores() {
		String texto = "Nombre\tPosicion\tCamiseta\n";
		for(InscripcionEquipo inscripcionEquipo : this.inscripciones) {
			if(inscripcionEquipo.getEstado().equals("Activa")) {
				texto += inscripcionEquipo + "\n";
			}
		}
		return texto;
	}

	@Override
	public String toString() {
		return this.nombre+"\t"+this.deporte+"\t"+this.categoria+"\t"+cantidadJugadoresActivos()+"/"+this.cupoMaximo;
	}
}
