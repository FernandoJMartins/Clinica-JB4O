package modelo;

import java.time.LocalDate;

public class Consulta {
	private int id;
	private LocalDate data;
	private Paciente paciente;
	private Medico medico;
	private String tipo;
	
	public Consulta(LocalDate data, Paciente paciente, Medico medico, String tipo) {
		this.data = data;
		this.paciente = paciente;
		this.medico = medico;
		this.tipo = tipo;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public LocalDate getData() {
		return data;
	}

	public void setData(LocalDate data) {
		this.data = data;
	}

	public Medico getMedico() {
		return medico;
	}

	public void setMedico(Medico medico) {
		this.medico = medico;
	}

	public Paciente getPaciente() {
		return paciente;
	}

	public void setPaciente(Paciente paciente) {
		this.paciente = paciente;
	}

	public String getTipo() {
		return tipo;
	}

	public void setTipo(String tipo) {
		this.tipo = tipo;
	}

	@Override
	public String toString() {
		return "Consulta [id=" + id + ", data=" + data + ", paciente=" + paciente + ", medico=" + medico + ", tipo="
				+ tipo + "]";
	}
	
}
