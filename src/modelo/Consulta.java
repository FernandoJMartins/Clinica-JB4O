package modelo;

import java.util.Date;

public class Consulta {
	private int id;
	private Date data;
	private Paciente paciente;
	private Medico medico;
	private String tipo;
	
	public Consulta(int id, Date data, Paciente paciente, Medico medico, String tipo) {
		this.id = id;
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

	public Date getData() {
		return data;
	}

	public void setData(Date data) {
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
}
