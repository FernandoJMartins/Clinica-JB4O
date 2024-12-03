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
		
			Fachada.criarPaciente("1234567889", "Cai2o");
			Fachada.criarMedico("Medico 1", "123332", "Cardiologista");
			Fachada.criarConsulta("11/02/2000", "1234567889", "123332", "Plano");
			

			System.out.println(Fachada.localizarPaciente("1234567889"));
			System.out.println(Fachada.localizarMedico("123332"));
			System.out.println(Fachada.listarConsultas());

			
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


