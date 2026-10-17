package logica;

import java.util.HashMap;

public class ClubDeportivoSeneca {

	private HashMap<Integer, Socio> socios;
	private HashMap<String, Equipo> equipos;
	
	public ClubDeportivoSeneca() {
		this.socios = new HashMap<Integer, Socio>();
		this.equipos = new HashMap<String, Equipo>();
	}
	
	public void crearSocio(int id, String nombre, String apellido, int edad) throws Exception {
		if(this.socios.containsKey(id)) {
			throw new Exception("El socio con id " + id + " ya existe");
		}else {
			this.socios.put(id, new Socio(id, nombre, apellido, edad));
		}
	}
	
	public void crearEquipo(String nombre, int cupoMaximo, CategoriaEquipo categoria) throws Exception {
		if(this.equipos.containsKey(nombre)) {
			throw new Exception("El equipo " + nombre + " ya existe");
		}else {
			this.equipos.put(nombre, new Equipo(nombre, cupoMaximo, categoria));
		}
	}
	
	public void inscribirJugadorEquipo(int idSocio, String nombreEquipo, int numeroCamiseta, String posicion) throws Exception {
		if(this.socios.containsKey(idSocio)) {
			if(this.equipos.containsKey(nombreEquipo)) {
				this.equipos.get(nombreEquipo).inscribirJugador(
						this.socios.get(idSocio), numeroCamiseta, posicion);
			}else {
				throw new Exception("El equipo " + nombreEquipo + " no existe");
			}
		}else {
			throw new Exception("El socio con id " + idSocio + " no existe");
		}
	}
	
	public String consultarSocios() {
		String texto = "Id\tNombre\tApellido\tEdad\n";
		
		for(Socio socio : this.socios.values()) {
			texto += socio + "\n";
		}
		
		return texto;
	}
	
	public String consultarEquipos() {
		String texto = "Nombre\tCategoria\tCupo\n";
		
		for(Equipo equipo : this.equipos.values()) {
			texto += equipo + "\n";
			texto += equipo.consultarJugadores();
		}
		
		return texto;
	}
}