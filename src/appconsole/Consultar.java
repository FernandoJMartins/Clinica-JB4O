package appconsole;
/**********************************
 * IFPB - SI
 * Persistencia de Objetos
 * Prof. Fausto Ayres
 **********************************/

import modelo.Pessoa;
import modelo.Telefone;
import regras_negocio.Fachada;


public class Consultar {

	public Consultar(){

		try {
			Fachada.inicializar();
			System.out.println("\npessoas com nome jo ");
			for(Pessoa p : Fachada.consultarPessoas("jo")) 
				System.out.println(p);

			System.out.println("\ntelefones com numero 987 ");
			for(Telefone t : Fachada.consultarTelefones("987")) 
				System.out.println(t);

			System.out.println("\npessoas que nasceram no mes 02");
			for(Pessoa p : Fachada.consultarMesNascimento("02")) 
				System.out.println(p);

			System.out.println("\npessoas com apelido mary");
			for(Pessoa p : Fachada.consultarApelido("mary")) 
				System.out.println(p);


			System.out.println("\npessoas com dois telefones " );
			for(Pessoa p : Fachada.consultarPessoasNTelefones(2) ) 
				System.out.println(p);

			System.out.println("\nmaria tem telefone fixo?\n"+
					Fachada.temTelefoneFixo("maria") );



		} catch (Exception e) {
			System.out.println(e.getMessage());
		}

		Fachada.finalizar();
		System.out.println("\nfim do programa");
	}


	//=================================================
	public static void main(String[] args) {
		new Consultar();
	}
}

