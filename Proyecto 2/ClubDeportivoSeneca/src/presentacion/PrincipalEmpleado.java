package presentacion;

import java.util.Scanner;

import logica.ClubDeportivoSeneca;
import logica.Empleado;

public class PrincipalEmpleado {
	private ClubDeportivoSeneca club;
	private Scanner sc;
	private Empleado empleado;

	public PrincipalEmpleado() {
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
		String idEmpleado;

		System.out.println("Digite login");
		login = this.sc.nextLine();

		System.out.println("Digite password");
		password = this.sc.nextLine();

		System.out.println("Digite id del empleado");
		idEmpleado = this.sc.nextLine();

		try {
			this.club.iniciarSesion(login, password);
			this.empleado = this.club.getEmpleado(idEmpleado);

			if(!this.empleado.getLogin().equals(login)) {
				throw new Exception("El usuario no corresponde al empleado");
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
					+ "1. Consultar turnos\n"
					+ "2. Consultar torneos\n"
					+ "3. Inscribirse en torneo\n"
					+ "4. Retirarse de torneo\n"
					+ "5. Consultar historial de torneo\n"
					+ "6. Sugerir producto\n"
					+ "----\n");

			op = Integer.parseInt(this.sc.nextLine());

			if(op == 1) {
				this.consultarTurnos();
			}else if(op == 2) {
				this.consultarTorneos();
			}else if(op == 3) {
				this.inscribirseTorneo();
			}else if(op == 4) {
				this.retirarseTorneo();
			}else if(op == 5) {
				this.consultarHistorialTorneo();
			}else if(op == 6) {
				this.sugerirProducto();
			}

		}while(op != 0);

		this.club.cerrarSesion();
	}

	private void consultarTurnos() {
		try {
			System.out.println(this.club.consultarTurnos(this.empleado.getIdEmpleado()));
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
			this.club.inscribirParticipanteTorneo(idTorneo, this.empleado, preferencial);
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
			this.club.desinscribirParticipanteTorneo(idTorneo, this.empleado);
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

	private void sugerirProducto() {
		String descripcion;

		System.out.println("Digite descripcion del producto");
		descripcion = this.sc.nextLine();

		try {
			this.club.sugerirProducto(this.empleado.getIdEmpleado(), descripcion);
			System.out.println("Sugerencia registrada");
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
	}

	public static void main(String[] args) {
		new PrincipalEmpleado();
	}
}
