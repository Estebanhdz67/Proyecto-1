package presentacion;

import java.util.Scanner;

import logica.ClubDeportivoSeneca;
import logica.Socio;

public class PrincipalSocio {
	private ClubDeportivoSeneca club;
	private Scanner sc;
	private Socio socio;

	public PrincipalSocio() {
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
		String idSocio;

		System.out.println("Digite login");
		login = this.sc.nextLine();

		System.out.println("Digite password");
		password = this.sc.nextLine();

		System.out.println("Digite id del socio");
		idSocio = this.sc.nextLine();

		try {
			this.club.iniciarSesion(login, password);
			this.socio = this.club.getSocio(idSocio);

			if(!this.socio.getLogin().equals(login)) {
				throw new Exception("El usuario no corresponde al socio");
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
					+ "1. Consultar equipos\n"
					+ "2. Consultar entrenamientos\n"
					+ "3. Consultar mensualidades\n"
					+ "4. Consultar torneos\n"
					+ "5. Inscribirse en torneo\n"
					+ "6. Retirarse de torneo\n"
					+ "7. Consultar historial de torneo\n"
					+ "----\n");

			op = Integer.parseInt(this.sc.nextLine());

			if(op == 1) {
				this.consultarEquipos();
			}else if(op == 2) {
				this.consultarEntrenamientos();
			}else if(op == 3) {
				this.consultarMensualidades();
			}else if(op == 4) {
				this.consultarTorneos();
			}else if(op == 5) {
				this.inscribirseTorneo();
			}else if(op == 6) {
				this.retirarseTorneo();
			}else if(op == 7) {
				this.consultarHistorialTorneo();
			}

		}while(op != 0);

		this.club.cerrarSesion();
	}

	private void consultarEquipos() {
		try {
			System.out.println(this.club.consultarEquiposSocio(this.socio.getIdSocio()));
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
	}

	private void consultarEntrenamientos() {
		try {
			System.out.println(this.club.consultarEntrenamientos(this.socio.getIdSocio()));
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
	}

	private void consultarMensualidades() {
		try {
			System.out.println(this.club.consultarMensualidades(this.socio.getIdSocio()));
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
	}

	private void consultarTorneos() {
		System.out.println(this.club.consultarTorneos());
	}

	private void inscribirseTorneo() {
		String idTorneo;
		String respuesta;
		boolean preferencial;

		System.out.println("Digite id del torneo");
		idTorneo = this.sc.nextLine();

		System.out.println("Desea usar cupo preferencial? Si/No");
		respuesta = this.sc.nextLine();
		preferencial = respuesta.equals("Si");

		try {
			this.club.inscribirParticipanteTorneo(idTorneo, this.socio, preferencial);
			System.out.println("Inscripcion realizada");
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
	}

	private void retirarseTorneo() {
		String idTorneo;

		System.out.println("Digite id del torneo");
		idTorneo = this.sc.nextLine();

		try {
			this.club.desinscribirParticipanteTorneo(idTorneo, this.socio);
			System.out.println("Retiro realizado");
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

	public static void main(String[] args) {
		new PrincipalSocio();
	}
}
