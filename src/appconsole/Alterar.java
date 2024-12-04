package appconsole;
/**********************************
 * IFPB - SI
 * Persistencia de Objetos
 * Prof. Fausto Ayres
 **********************************/

import regras_negocio.Fachada;

public class Alterar {

	public Alterar(){
		Fachada.inicializar();
		//altera�ao 1
		try {
			Fachada.alterarData(1, "12/12/2024");
			System.out.println("alterado data da consulta");
			System.out.println(Fachada.listarConsultas());
		}
		catch (Exception e) {
			System.out.println(e.getMessage());
		}
		
		//altera�ao 2
		try {
			Fachada.alterarPaciente("001", "novoNome");
			System.out.println("alterado nome do paciente");
			System.out.println(Fachada.localizarPaciente("001"));
		}
		catch (Exception e) {
			System.out.println(e.getMessage());
		}
		
		Fachada.finalizar();
		System.out.println("fim do programa");
	}



	//=================================================
	public static void main(String[] args) {
		new Alterar();
	}
}

