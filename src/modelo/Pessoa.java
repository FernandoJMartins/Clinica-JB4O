package modelo;
/**********************************
 * IFPB - SI
 * POB - Persistencia de Objetos
 * Prof. Fausto Ayres
 **********************************/
import java.util.ArrayList;
import java.util.List;


public class Pessoa {
	private int id;
	private String nome;
	private String dtnascimento ;	
	private List<Telefone> telefones = new ArrayList<>();
	private List<String>  apelidos = new ArrayList<>();


	public Pessoa(String nome) {
		this.nome = nome;
	}
	public String getNome() {
		return nome;
	}
	public void setNome(String nome) {
		this.nome = nome;
	}
	public String getDtNascimento() {
		return dtnascimento;
	}

	public void setDtNascimento(String dt) {
		dtnascimento = dt ; 
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}

	public void adicionar(Telefone t){
		telefones.add(t);
		t.setPessoa(this);
	}
	public void remover(Telefone t){
		telefones.remove(t);
		t.setPessoa(null);
	}
	public Telefone localizar(String num){
		for(Telefone t : telefones)
			if (t.getNumero().equals(num))
				return t;
		return null;
	}

	public List<String> getApelidos() {
		return apelidos;
	}

	public void setApelidos(List<String> lista){
		this.apelidos = lista;
	}

	public String toString() {
		String texto = id+" nome=" +  nome + ", nascimento=" + getDtNascimento();

		texto += "  telefones: ";
		for(Telefone t : telefones)
			texto += t.getNumero() + ",";

		texto += "  apelidos: ";
		for(String a : apelidos)
			texto += a + ",";

		return texto;
	}

	public List<Telefone> getTelefones() {
		return telefones;
	}

}
