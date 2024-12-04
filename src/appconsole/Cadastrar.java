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
		
			Fachada.criarPaciente("001", "Caioooo");
			Fachada.criarMedico("Medico 1", "ABC1", "Cardiologista");
			Fachada.criarMedico("Medico 2", "ABC2", "Cardiologista");
			Fachada.criarConsulta("11/02/2000", "001", "ABC1", "Plano");
			Fachada.criarConsulta("11/02/2000", "001", "ABC2", "Plano");
			

			System.out.println(Fachada.localizarPaciente("001"));
			System.out.println(Fachada.localizarMedico("ABC2"));
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


