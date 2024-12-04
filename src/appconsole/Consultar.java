package appconsole;
/**********************************
 * IFPB - SI
 * Persistencia de Objetos
 * Prof. Fausto Ayres
 **********************************/

import modelo.Consulta;
import modelo.Medico;
import modelo.Paciente;
import regras_negocio.Fachada;

public class Consultar {

    public Consultar() {

        try {
            Fachada.inicializar();

            System.out.println("\nListando todos os médicos:");
            for (Medico m : Fachada.listarMedicos()) {
                System.out.println(m);
            }

//            System.out.println("\nListando todos os pacientes:");
//            for (Paciente p : Fachada.listarPacientes()) {
//                System.out.println(p);
//            }

            System.out.println("\nListando todas as consultas:");
            for (Consulta c : Fachada.listarConsultas()) {
                System.out.println(c);
            }

            System.out.println("\nConsultando médicos com caracteres 'Cardio':");
            for (Medico m : Fachada.consultarMedicos("Cardio")) {
                System.out.println(m);
            }

            System.out.println("\nConsultando consultas que sejam do ano de '2024':");
            for (Consulta c : Fachada.consultarConsultas("2024")) {
                System.out.println(c);
            }

//            System.out.println("\nConsultando consultas de pacientes:");
//            for (Consulta c : Fachada.consultarPacientes("02")) {
//                System.out.println(c);
//            }
//
            System.out.println("\nConsultas realizadas no plano em data específica (11/02/2000):");
            for (Consulta c : Fachada.consultasDoPlanoNaData("11/02/2000")) {
                System.out.println(c);
            }
//
             System.out.println("\nPacientes com mais de 1 consultas:");
             for (Paciente p : Fachada.consultaNumeroConsultasMaiorQue(1)) {
             	System.out.println(p);
            }
//            
            System.out.println("\nPacientes que Se Consultaram com o Medico de crm ABC1:");
            for (Paciente p : Fachada.consultaPacientesSeConsultaramComMedico("ABC1")) {
                System.out.println(p);
            }

        } catch (Exception e) {
            System.out.println("Erro: " + e.getMessage());
        }

        Fachada.finalizar();
        System.out.println("\nFim do programa");
    }

    //=================================================
    public static void main(String[] args) {
        new Consultar();
    }
}
