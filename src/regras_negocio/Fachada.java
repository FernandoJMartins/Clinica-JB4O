package regras_negocio;
/**********************************
 * IFPB - SI
 * POB - Persistencia de Objetos
 * Prof. Fausto Ayres
 **********************************/

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.List;

import daodb4o.DAO;
import daodb4o.DAOConsulta;
import daodb4o.DAOMedico;
import daodb4o.DAOPaciente;
import modelo.Consulta;
import modelo.Medico;
import modelo.Paciente;


public class Fachada {
	private Fachada() {}

	private static DAOMedico daoMedico = new DAOMedico();
	private static DAOPaciente daoPaciente = new DAOPaciente();
	private static DAOConsulta daoConsulta = new DAOConsulta();

	public static void inicializar() {
		DAO.open();
	}

	public static void finalizar() {
		DAO.close();
	}

	public static Medico localizarMedico(String crm) throws Exception {
		Medico p = daoMedico.read(crm);
		if (p == null) {
			throw new Exception("pessoa inexistente:" + crm);
		}
		return p;
	}
	
	public static Paciente localizarPaciente(String cpf) throws Exception {
		Paciente a = daoPaciente.read(cpf);
		if (a == null) {
			throw new Exception("paciente inexistente:" + cpf);
		}
		return a;
	}
	
	public static Consulta (int id) throws Exception {
		Consulta a = daoConsulta.read(id);
		if (a == null) {
			throw new Exception("consulta inexistente:" + id);
		}
		return a;
	}

	//public static void criarConsulta(String data, Paciente paciente, Medico medico, String tipo ) throws Exception {
	public static void criarConsulta(String data, String paciente, String medico, String tipo ) throws Exception {

		// ADICIONAR REGRA DE NEGOCIO AQUI
		DAO.begin();
		try {
			LocalDate.parse(data, DateTimeFormatter.ofPattern("dd/MM/yyyy"));
		} catch (DateTimeParseException e) {
			DAO.rollback();
			throw new Exception("formato data invalido:" + data);
		}
		Paciente pacientelocalizado = localizarPaciente(paciente);
		Consulta consulta = new Consulta();
		consulta.setData(data);
		consulta.setPaciente(pacientelocalizado);
		consulta.setMedico(localizarMedico(medico));
		consulta.setTipo(tipo);
		pacientelocalizado.addConsulta(consulta);
		
		daoConsulta.create(consulta);
		daoPaciente.update(pacientelocalizado);
		DAO.commit();
	}

	public static void criarPaciente(String cpf, String nome) throws Exception {
		Paciente p = daoPaciente.read(cpf);
		if (p != null) {
			DAO.rollback();
			throw new Exception("criar paciente - nome ja existe:" + nome);
		}
		Paciente a = new Paciente(cpf);
		a.setNome(nome);
		
		daoPaciente.create(a);
		DAO.commit();
	}
	
	public static void criarMedico(String nome, String crm, String especialidade) throws Exception {
		Medico m = daoMedico.read(crm);
		if (m != null) {
			DAO.rollback();
			throw new Exception("criar Medico - Medico ja existe:" + nome);
		}
		Medico medico = new Medico(crm);
		medico.setNome(nome);
		medico.setEspecialidade(especialidade);
		daoMedico.create(medico);
		DAO.commit();
	}

	//public static void alterarPaciente(String cpf, String nome, ArrayList<Consulta> consultas) throws Exception {
	public static void alterarPaciente(String cpf, String nome) throws Exception {
	
		// permite alterar data, foto e apelidos
		DAO.begin();
		Paciente p = daoPaciente.read(cpf);
		if (p == null) {
			DAO.rollback();
			throw new Exception("alterar pessoa - pessoa inexistente:" + nome);
		}
		
		//p.setCpf(cpf);
		p.setNome(nome);
		//p.setConsultas(consultas);

		daoPaciente.update(p);
		DAO.commit();
	}

	public static void alterarMedico(String nome, String crm, String especialidade) throws Exception {
		// permite alterar data, foto e apelidos
		DAO.begin();
		Medico m = daoMedico.read(crm);
		if (m == null) {
			DAO.rollback();
			throw new Exception("alterar medico - crm inexistente:" + crm);
		}

		m.setNome(nome);
		//m.setCrm(crm);
		m.setEspecialidade(especialidade);
		
		daoMedico.update(m);
		DAO.commit();
	}

	public static void alterarData(int id, String data) throws Exception {
		DAO.begin();
		Consulta c = daoConsulta.read(id);
		if (c == null) {
			DAO.rollback();
			throw new Exception("alterar data - consulta inexistente:" + id);
		}
		
		if (data != null) {
			try {
				LocalDate.parse(data, DateTimeFormatter.ofPattern("dd/MM/yyyy"));
			} catch (DateTimeParseException e) {
				DAO.rollback();;
				throw new Exception("formato data invalido:" + data);
			}
		}
		c.setData(data);
		daoConsulta.update(c);
		DAO.commit();
	}

	public static void excluirMedico(String crm) throws Exception {
		DAO.begin();
		Medico m = daoMedico.read(crm);
		if (m == null) {
			DAO.rollback();
			throw new Exception("excluir Medico - CRM inexistente:" + crm);
		}

		daoMedico.delete(m); // apaga o MEDICO pelo CRM
		DAO.commit();
	}
	
	public static void excluirPaciente(String cpf) throws Exception {
		DAO.begin();
		Paciente p = daoPaciente.read(cpf);
		if (p == null) {
			DAO.rollback();
			throw new Exception("excluir Paciente - cpf inexistente:" + cpf);
		}

		daoPaciente.delete(p); // apaga o PACIENTE pelo CPF ( essas duas classes poderiam ser uma classe só )
		DAO.commit();
	}
	

	public static void excluirConsulta(int numero) throws Exception {
		DAO.begin();
		Consulta c = daoConsulta.read(numero);
		if (c == null) {
			DAO.rollback();
			throw new Exception("excluir consulta - numero inexistente:" + numero);
		}
		Paciente p = c.getPaciente();
		p.removeConsulta(c);
		
		c.setPaciente(null);
		c.setMedico(null);
		
		daoPaciente.update(p);
		daoConsulta.delete(c);
		DAO.commit();
	}

	public static void alterarTipo(int id, String novoTipo) throws Exception {
		DAO.begin();
		Consulta c = daoConsulta.read(id);
		if (c == null) {
			DAO.rollback();
			throw new Exception("alterar tipo - Consulta inexistente:" + id);
		}
		Consulta c2 = daoConsulta.read(id);
//		if (c2 != null) {
//			DAO.rollback();
//			throw new Exception("alterar numero - novo numero ja existe:" + id);
//		}
		if (novoTipo.isEmpty()) {
			DAO.rollback();
			throw new Exception("alterar tipo - novo tipo vazio:");
		}

		c.setTipo(novoTipo); // substituir
		daoConsulta.update(c);
		DAO.commit();
	}

	public static List<Medico> listarMedicos() {
		List<Medico> result = daoMedico.readAll();
		return result;
	}

	public static List<Paciente> listarPacientes() {
		List<Paciente> result = daoPaciente.readAll();
		return result;
	}

	public static List<Consulta> listarConsultas() {
		List<Consulta> result = daoConsulta.readAll();
		return result;
	}

	/**********************************************************
	 * 
	 * CONSULTAS IMPLEMENTADAS NOS DAO
	 * 
	 **********************************************************/
	public static List<Medico> consultarMedicos(String caracteres) {
		List<Medico> result;
		if (caracteres.isEmpty())
			result = daoMedico.readAll();
		else
			result = daoMedico.readAll(caracteres);
		return result;
	}


	public static List<Consulta> consultarConsultas(String digitos) {
		List<Consulta> result;
		if (digitos.isEmpty())
			result = daoConsulta.readAll();
		else
			result = daoConsulta.readAll(digitos);
		return result;
	}

	public static List<Consulta> consultarPacientes(String digitos) {
		List<Consulta> result;
		if (digitos.isEmpty())
			result = daoConsulta.readAll();
		else
			result = daoConsulta.readAll(digitos);
		return result;
	}
	
	public static List<Consulta> consultasDoPlanoNaData(String data){
		List<Consulta> result;
		result = daoConsulta.readAllPlano(data);
		return result;
	}
	
//	public static List<Paciente> consultaPacientesSeConsultaramComMedico(String crm){
//		List<Paciente> result;
//		result = daoPaciente.readAllPlano(data);
//		return result;
//	}
	
	public static List<Paciente> consultaNumeroConsultasMaiorQue(int n){
		List<Paciente> result;
		result = daoPaciente.readAllMaiorQue(n);
		return result;
	}
	
}
