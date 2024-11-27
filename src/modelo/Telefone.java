package modelo;
/**********************************
 * IFPB - SI
 * POB - Persistencia de Objetos
 * Prof. Fausto Ayres
 **********************************/

public class Telefone {
	private int id;
	private String numero;	
	private Pessoa pessoa;			//lado inverso do relacionamento


	public Telefone (){}
	public Telefone(String numero) {
		this.numero = numero;
	}

	public String getNumero() {
		return numero;
	}
	public void setNumero(String numero) {
		this.numero = numero;
	}


	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	//	--------------------RELACIONAMENTO--------------------------------
	public Pessoa getPessoa() {
		return pessoa;
	}
	public void setPessoa(Pessoa pessoa) {
		this.pessoa = pessoa;
	}


	@Override
	public String toString() {
		return id + " numero=" + numero + (pessoa!=null ? ", pessoa="+pessoa.getNome() : "pessoa=") ;	
	}

	public boolean ehCelular() {
		return numero.startsWith("9");
	}

	public boolean ehFixo() {
		return numero.startsWith("3");
	}


}
