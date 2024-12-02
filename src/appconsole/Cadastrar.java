package appconsole;
/**********************************
 * IFPB - SI
 * Persistencia de Objetos
 * Prof. Fausto Ayres
 **********************************/

//import java.util.ArrayList;
//import java.util.List;

import regras_negocio.Fachada;

public class Cadastrar {

	public Cadastrar(){
		try {
			System.out.println("cadastrando pessoas...");
			Fachada.inicializar();
			
			//Fachada.criarMedico("123", "Bruno", "UROLOGISTA");
		
			Fachada.criarPaciente("1234567", "Caio");
			System.out.println("cadastrando medico...");

			
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


