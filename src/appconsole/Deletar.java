package appconsole;
/**********************************
 * IFPB - SI
 * Persistencia de Objetos
 * Prof. Fausto Ayres
 **********************************/

import regras_negocio.Fachada;

public class Deletar {

    public Deletar() {
        Fachada.inicializar();
        try {
            // Excluir médico pelo CRM
            Fachada.excluirMedico("123332");
            System.out.println("Médico com CRM '123332' apagado com sucesso.");

            // Excluir paciente pelo CPF
            Fachada.excluirPaciente("1234567889");
            System.out.println("Paciente com CPF '1234567889' apagado com sucesso.");

            // Excluir consulta pelo ID
            Fachada.excluirConsulta(1);
            System.out.println("Consulta com ID '1' apagada com sucesso.");

        } catch (Exception e) {
            System.out.println("Erro ao tentar excluir: " + e.getMessage());
        }

        Fachada.finalizar();
        System.out.println("Fim do programa");
    }

    //=================================================
    public static void main(String[] args) {
        new Deletar();
    }
}
