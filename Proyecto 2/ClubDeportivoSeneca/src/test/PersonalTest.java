package test;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.sql.Time;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import logica.ClubDeportivoSeneca;

public class PersonalTest {
	private ClubDeportivoSeneca club;

	@BeforeEach
	public void setUp() throws Exception {
		this.club = new ClubDeportivoSeneca();
	}

	@Test
	public void minimoPersonalTest1() {
		assertFalse(this.club.cumpleMinimoPersonal());
	}

	@Test
	public void minimoPersonalTest2() throws Exception {
		this.club.crearEntrenador("e1", "123", "Entrenador 1", "E1", "D1", "Futbol");
		this.club.crearEntrenador("e2", "123", "Entrenador 2", "E2", "D2", "Futbol");
		this.club.crearFisioterapeuta("f1", "123", "Fisio 1", "F1", "D3", "General");

		String[] dias = {"Lunes", "Martes", "Miercoles", "Jueves", "Viernes", "Sabado", "Domingo"};

		for(String dia : dias) {
			this.club.asignarTurno("E1", dia, Time.valueOf("06:00:00"), Time.valueOf("22:00:00"));
			this.club.asignarTurno("E2", dia, Time.valueOf("06:00:00"), Time.valueOf("22:00:00"));
			this.club.asignarTurno("F1", dia, Time.valueOf("06:00:00"), Time.valueOf("22:00:00"));
		}

		assertTrue(this.club.cumpleMinimoPersonal());
	}
}
