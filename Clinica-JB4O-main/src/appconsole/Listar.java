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

public class Listar {

    public Listar() {
        try {
            Fachada.inicializar();

            // Listar médicos
            System.out.println("*** Listagem de médicos:");
            for (Medico m : Fachada.listarMedicos()) {
                System.out.println(m);
            }

            // Listar pacientes
            System.out.println("\n*** Listagem de pacientes:");
            for (Paciente p : Fachada.listarPacientes()) {
                System.out.println(p);
            }

            // Listar consultas
            System.out.println("\n*** Listagem de consultas:");
            for (Consulta c : Fachada.listarConsultas()) {
                System.out.println(c);
            }

        } catch (Exception e) {
            System.out.println("Erro: " + e.getMessage());
        } finally {
            Fachada.finalizar();
        }
    }

    //=================================================
    public static void main(String[] args) {
        new Listar();
    }
}
