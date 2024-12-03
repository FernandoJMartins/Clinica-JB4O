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
		
			Fachada.criarPaciente("12345678891", "Caioooo");
			Fachada.criarMedico("Medico 1", "123332", "Cardiologista");
			Fachada.criarMedico("Medico 2", "1233321", "Cardiologista");
			Fachada.criarConsulta("11/02/2000", "12345678891", "123332", "Plano");
			Fachada.criarConsulta("11/02/2000", "12345678891", "1233321", "Plano");
			

			System.out.println(Fachada.localizarPaciente("12345678891"));
			System.out.println(Fachada.localizarMedico("1233321"));
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


