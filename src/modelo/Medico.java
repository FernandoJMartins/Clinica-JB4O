package modelo;

public class Medico {
	
	private int crm;
	private String nome;
	private String especialidade;
	
	public Medico(int crm, String nome, String especialidade) {
		this.crm = crm;
		this.nome = nome;
		this.especialidade = especialidade;
	}
	
	public Medico() {
		
	}
	
	public int getId() {
		return crm;
	}
	public void setId(int crm) {
		this.crm = crm;
	}
	public String getNome() {
		return nome;
	}
	public void setNome(String nome) {
		this.nome = nome;
	}
	public String getEspecialidade() {
		return especialidade;
	}
	public void setEspecialidade(String especialidade) {
		this.especialidade = especialidade;
	}
	
	
}
