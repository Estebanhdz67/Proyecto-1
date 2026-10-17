package logica;

import java.util.ArrayList;

public class Equipo {

	private String nombre;
	private int cupoMaximo;
	private CategoriaEquipo categoria;
	private ArrayList<InscripcionEquipo> jugadores;
	
	public Equipo(String nombre, int cupoMaximo, CategoriaEquipo categoria) {
		this.nombre = nombre;
		this.cupoMaximo = cupoMaximo;
		this.categoria = categoria;
		this.jugadores = new ArrayList<InscripcionEquipo>();
	}

	public String getNombre() {
		return nombre;
	}

	public int getCupoMaximo() {
		return cupoMaximo;
	}

	public CategoriaEquipo getCategoria() {
		return categoria;
	}
	
	public void inscribirJugador(Socio socio, int numeroCamiseta, String posicion) throws Exception {
		if(this.jugadores.size() >= this.cupoMaximo) {
			throw new Exception("El equipo no tiene cupos disponibles");
		}else {
			boolean numeroOcupado = false;
			
			for(InscripcionEquipo inscripcion : this.jugadores) {
				if(inscripcion.getNumeroCamiseta() == numeroCamiseta) {
					numeroOcupado = true;
				}
			}
			
			if(numeroOcupado) {
				throw new Exception("El numero de camiseta " + numeroCamiseta + " ya esta ocupado");
			}else {
				this.jugadores.add(
						new InscripcionEquipo(
								this.jugadores.size()+1,
								socio,
								numeroCamiseta,
								posicion
						)
				);
			}
		}
	}
	
	public String consultarJugadores() {
		String texto = "\tId\tSocio\tCamiseta\tPosicion\n";
		
		for(InscripcionEquipo inscripcion : this.jugadores) {
			texto += inscripcion + "\n";
		}
		
		return texto;
	}
	
	@Override
	public String toString() {
		return this.nombre + "\t" + this.categoria + "\t" + 
				this.jugadores.size() + "/" + this.cupoMaximo;
	}
}