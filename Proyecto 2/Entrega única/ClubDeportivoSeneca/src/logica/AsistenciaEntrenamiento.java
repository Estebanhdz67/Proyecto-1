package logica;

import java.io.Serializable;

public class AsistenciaEntrenamiento implements Serializable {
	private Socio socio;
	private SesionEntrenamiento sesion;
	private String estado;
	private EvaluacionDesempeno evaluacion;

	public AsistenciaEntrenamiento(Socio socio, SesionEntrenamiento sesion, String estado) {
		this.socio = socio;
		this.sesion = sesion;
		this.estado = estado;
	}

	public Socio getSocio() {
		return socio;
	}

	public SesionEntrenamiento getSesion() {
		return sesion;
	}

	public String getEstado() {
		return estado;
	}

	public void setEstado(String estado) throws Exception {
		if(!estado.equals("Asistio")&&!estado.equals("Falto")&&!estado.equals("Tarde")) {
			throw new Exception("Estado invalido");
		}
		this.estado = estado;
	}

	public EvaluacionDesempeno getEvaluacion() {
		return evaluacion;
	}

	public void setEvaluacion(EvaluacionDesempeno evaluacion) {
		this.evaluacion = evaluacion;
	}
}
