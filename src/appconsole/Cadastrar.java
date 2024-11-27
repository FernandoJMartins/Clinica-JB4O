package appconsole;
/**********************************
 * IFPB - SI
 * Persistencia de Objetos
 * Prof. Fausto Ayres
 **********************************/

import java.util.ArrayList;
import java.util.List;

import regras_negocio.Fachada;

public class Cadastrar {

	public Cadastrar(){
		try {
			System.out.println("cadastrando pessoas...");
			Fachada.inicializar();
			
			Fachada.criarPessoa("joao", "01/01/1990",new ArrayList<>(List.of("jo", "joaozinho", "jojo")));
			Fachada.criarPessoa("maria","01/01/1980",new ArrayList<>(List.of("mary", "mar")));
			Fachada.criarPessoa("jose", "01/01/1990",new ArrayList<>(List.of("zezinho", "zezao")));
			Fachada.criarPessoa("paulo","01/01/1990",new ArrayList<>(List.of("paulao")));
			
			
			System.out.println("cadastrando aluno...");
			Fachada.criarAluno("ana","01/01/1990",new ArrayList<>(List.of("aninha")),	10.0);
			Fachada.criarAluno("marta","01/02/1990",new ArrayList<>(), 9.0);
			
		} catch (Exception e) 	{
			System.out.println(e.getMessage());
		}

		try {
			System.out.println("cadastrando telefones...");
			Fachada.criarTelefone("joao","988880000");
			Fachada.criarTelefone("joao","988881111");	
			Fachada.criarTelefone("maria","987882222");
			Fachada.criarTelefone("maria","988883333");
			Fachada.criarTelefone("maria","32471234");
			Fachada.criarTelefone("jose","987884444");
			Fachada.criarTelefone("paulo","988885555");
		} catch (Exception e) 	{
			System.out.println(e.getMessage());
		}

		Fachada.finalizar();
		System.out.println("fim do programa");
	}

	
	//=================================================
	public static void main(String[] args) {
		new Cadastrar();
	}
}


