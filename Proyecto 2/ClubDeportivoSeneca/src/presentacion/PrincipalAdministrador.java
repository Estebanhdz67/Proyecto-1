package presentacion;

import java.sql.Date;
import java.util.Scanner;

import logica.Administrador;
import logica.Baloncesto;
import logica.ClubDeportivoSeneca;
import logica.DeporteConjunto;
import logica.DisciplinaIndividual;
import logica.Futbol;
import logica.Voleibol;

public class PrincipalAdministrador {
	private ClubDeportivoSeneca club;
	private Scanner sc;
	private Administrador administrador;

	public PrincipalAdministrador() {
		this.club = new ClubDeportivoSeneca();
		this.sc = new Scanner(System.in);

		this.club.leerSerializable();

		if(this.iniciarSesion()) {
			this.menu();
		}

		this.club.escribirSerializable();
		this.sc.close();
	}

	private boolean iniciarSesion() {
		String login;
		String password;
		String idAdministrador;

		System.out.println("Digite login");
		login = this.sc.nextLine();

		System.out.println("Digite password");
		password = this.sc.nextLine();

		System.out.println("Digite id del administrador");
		idAdministrador = this.sc.nextLine();

		try {
			this.club.iniciarSesion(login, password);
			this.administrador = this.club.getAdministrador(idAdministrador);

			if(!this.administrador.getLogin().equals(login)) {
				throw new Exception("El usuario no corresponde al administrador");
			}

			return true;
		} catch (Exception e) {
			System.out.println(e.getMessage());
			return false;
		}
	}

	private void menu() {
		int op = 0;

		do {
			System.out.println("----\n"
					+ "Digite\n"
					+ "0. Salir\n"
					+ "1. Consultar torneos\n"
					+ "2. Crear torneo\n"
					+ "3. Liberar cupos preferenciales\n"
					+ "4. Iniciar torneo\n"
					+ "5. Finalizar torneo\n"
					+ "6. Consultar historial de torneo\n"
					+ "7. Consultar inventario\n"
					+ "8. Consultar competencias\n"
					+ "9. Consultar reporte de ventas\n"
					+ "10. Consultar reporte deportivo\n"
					+ "----\n");

			op = Integer.parseInt(this.sc.nextLine());

			if(op == 1) {
				this.consultarTorneos();
			}else if(op == 2) {
				this.crearTorneo();
			}else if(op == 3) {
				this.liberarCuposPreferenciales();
			}else if(op == 4) {
				this.iniciarTorneo();
			}else if(op == 5) {
				this.finalizarTorneo();
			}else if(op == 6) {
				this.consultarHistorialTorneo();
			}else if(op == 7) {
				this.consultarInventario();
			}else if(op == 8) {
				this.consultarCompetencias();
			}else if(op == 9) {
				this.consultarReporteVentas();
			}else if(op == 10) {
				this.consultarReporteDeportivo();
			}

		}while(op != 0);

		this.club.cerrarSesion();
	}

	private void consultarTorneos() {
		System.out.println(this.club.consultarTorneos());
	}

	private void crearTorneo() {
		String idTorneo;
		String nombre;
		String categoriaNivel;
		int cuposTotales;
		Date fecha;
		String tipo;
		String formato;
		int tipoActividad;
		double valor;
		DeporteConjunto deporte = null;
		DisciplinaIndividual disciplina = null;

		System.out.println("Digite id del torneo");
		idTorneo = this.sc.nextLine();

		System.out.println("Digite nombre del torneo");
		nombre = this.sc.nextLine();

		System.out.println("Digite categoria o nivel");
		categoriaNivel = this.sc.nextLine();

		System.out.println("Digite cupos totales");
		cuposTotales = Integer.parseInt(this.sc.nextLine());

		System.out.println("Digite fecha AAAA-MM-DD");
		fecha = Date.valueOf(this.sc.nextLine());

		System.out.println("Digite tipo: Recreativo o Competitivo");
		tipo = this.sc.nextLine();

		System.out.println("Digite formato: Liga, Fase de grupos, Eliminacion directa o ASCUN");
		formato = this.sc.nextLine();

		System.out.println("Digite 1 para deporte de conjunto o 2 para disciplina individual");
		tipoActividad = Integer.parseInt(this.sc.nextLine());

		try {
			if(tipoActividad == 1) {
				deporte = this.seleccionarDeporte();
			}else if(tipoActividad == 2) {
				System.out.println("Digite nombre de la disciplina");
				disciplina = this.club.getDisciplina(this.sc.nextLine());
			}else {
				throw new Exception("Tipo de actividad invalido");
			}

			System.out.println("Digite valor. En recreativo corresponde al porcentaje del bono y en competitivo a la tarifa");
			valor = Double.parseDouble(this.sc.nextLine());

			this.club.crearTorneo(idTorneo, nombre, categoriaNivel, cuposTotales, fecha, tipo, formato, deporte, disciplina, valor);
			System.out.println("Torneo creado");
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
	}

	private DeporteConjunto seleccionarDeporte() throws Exception {
		int op;

		System.out.println("Digite\n"
				+ "1. Futbol\n"
				+ "2. Baloncesto\n"
				+ "3. Voleibol");

		op = Integer.parseInt(this.sc.nextLine());

		if(op == 1) {
			return new Futbol();
		}else if(op == 2) {
			return new Baloncesto();
		}else if(op == 3) {
			return new Voleibol();
		}else {
			throw new Exception("Deporte invalido");
		}
	}

	private void liberarCuposPreferenciales() {
		String idTorneo;

		System.out.println("Digite id del torneo");
		idTorneo = this.sc.nextLine();

		try {
			this.club.liberarCuposPreferencialesTorneo(idTorneo);
			System.out.println("Cupos preferenciales liberados");
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
	}

	private void iniciarTorneo() {
		String idTorneo;

		System.out.println("Digite id del torneo");
		idTorneo = this.sc.nextLine();

		try {
			this.club.iniciarTorneo(idTorneo);
			System.out.println("Torneo iniciado");
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
	}

	private void finalizarTorneo() {
		String idTorneo;

		System.out.println("Digite id del torneo");
		idTorneo = this.sc.nextLine();

		try {
			this.club.finalizarTorneo(idTorneo);
			System.out.println("Torneo finalizado");
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
	}

	private void consultarHistorialTorneo() {
		String idTorneo;

		System.out.println("Digite id del torneo");
		idTorneo = this.sc.nextLine();

		try {
			System.out.println(this.club.consultarHistorialTorneo(idTorneo));
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
	}

	private void consultarInventario() {
		System.out.println(this.club.consultarInventario());
	}

	private void consultarCompetencias() {
		System.out.println(this.club.consultarCompetencias());
	}

	private void consultarReporteVentas() {
		System.out.println(this.club.generarReporteVentas());
	}

	private void consultarReporteDeportivo() {
		System.out.println(this.club.generarReporteDeportivo());
	}

	public static void main(String[] args) {
		new PrincipalAdministrador();
	}
}
