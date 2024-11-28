package modelo;

import java.util.ArrayList;

public class Paciente {
	
	private String cpf;
	private String nome;
	private ArrayList<Consulta> consultas = new ArrayList<>();
	
	public Paciente(String cpf, String nome) {
		this.cpf = cpf;
		this.nome = nome;
	}

	public String getCpf() {
		return cpf;
	}

	public void setCpf(String cpf) {
		this.cpf = cpf;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public ArrayList<Consulta> getConsultas() {
		return consultas;
	}

	public void addConsulta(Consulta consulta) {
		this.consultas.add(consulta);
	}
	
	public void removeConsulta(Consulta consulta) {
		this.consultas.remove(consulta);
	}
	
	
	
	
}
