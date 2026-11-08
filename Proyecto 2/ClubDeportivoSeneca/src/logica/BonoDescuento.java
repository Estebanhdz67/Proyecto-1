package logica;

import java.io.Serializable;

public class BonoDescuento implements Serializable {
	private String codigo;
	private double porcentaje;
	private boolean usado;
	private ParticipanteTorneo beneficiario;

	public BonoDescuento(String codigo, double porcentaje, ParticipanteTorneo beneficiario) {
		this.codigo = codigo;
		this.porcentaje = porcentaje;
		this.beneficiario = beneficiario;
		this.usado = false;
	}

	public String getCodigo() {
		return codigo;
	}

	public double getPorcentaje() {
		return porcentaje;
	}

	public ParticipanteTorneo getBeneficiario() {
		return beneficiario;
	}

	public boolean estaUsado() {
		return usado;
	}

	public void usarBono(ParticipanteTorneo participante, boolean otraPromocion) throws Exception {
		if(this.usado) {
			throw new Exception("El bono ya fue usado");
		}

		if(this.beneficiario != participante) {
			throw new Exception("El bono no pertenece al participante");
		}

		if(otraPromocion) {
			throw new Exception("El bono no se puede acumular con otra promocion");
		}

		this.usado = true;
	}
}
