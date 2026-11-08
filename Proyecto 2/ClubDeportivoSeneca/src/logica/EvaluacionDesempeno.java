package logica;

import java.io.Serializable;

public class EvaluacionDesempeno implements Serializable {
	private double nota;
	private String comentario;

	public EvaluacionDesempeno(double nota, String comentario) throws Exception {
		if(nota<1||nota>5) {
			throw new Exception("La nota debe estar entre 1 y 5");
		}
		this.nota = nota;
		this.comentario = comentario;
	}

	public double getNota() {
		return nota;
	}

	public String getComentario() {
		return comentario;
	}
}
