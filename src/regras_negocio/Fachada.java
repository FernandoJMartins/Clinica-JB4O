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
			throw new Exception("aluno inexistente:" + cpf);
		}
		return a;
	}
	
	public static Consulta localizarConsulta(int id) throws Exception {
		Consulta a = daoConsulta.read(id);
		if (a == null) {
			throw new Exception("aluno inexistente:" + id);
		}
		return a;
	}

	public static void criarConsulta(String data, Medico medico, Paciente paciente, String tipo ) throws Exception {
		// ADICIONAR REGRA DE NEGOCIO AQUI
		DAO.begin();
		LocalDate dataFormatada = null;
		try {
			dataFormatada = LocalDate.parse(data, DateTimeFormatter.ofPattern("dd/MM/yyyy"));
		} catch (DateTimeParseException e) {
			DAO.rollback();
			throw new Exception("formato data invalido:" + data);
		} catch (Exception e ) {
			
		}
		
		Consulta consulta = new Consulta();
		consulta.setData(dataFormatada);
		consulta.setPaciente(paciente);
		consulta.setMedico(medico);
		consulta.setTipo(tipo);
		daoConsulta.create(consulta);
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
			throw new Exception("alterar pessoa - consulta inexistente:" + id);
		}

		if (data != null) {
			try {
				LocalDate dataFormatada = LocalDate.parse(data, DateTimeFormatter.ofPattern("dd/MM/yyyy"));
				c.setData(dataFormatada);
			} catch (DateTimeParseException e) {
				DAO.rollback();
				throw new Exception("alterar data - formato data invalido:" + data);
			}
		}

		daoConsulta.update(c);
		DAO.commit();
	}
//
//	public static void alterarNome(String nome, String novonome) throws Exception {
//		DAO.begin();
//		Pessoa p = daopessoa.read(nome); // usando chave primaria
//		if (p == null) {
//			DAO.rollback();
//			throw new Exception("alterar nome - nome inexistente:" + nome);
//		}
//		p.setNome(novonome);
//		daopessoa.update(p);
//		DAO.commit();
//	}

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
			throw new Exception("excluir Paciente - nome inexistente:" + cpf);
		}

		daoPaciente.delete(p); // apaga o PACIENTE pelo CPF ( essas duas classes poderiam ser uma classe só )
		DAO.commit();
	}
	
	
	

//	public static void criarTelefone(String nome, String numero) throws Exception {
//		DAO.begin();
//		Pessoa p = daopessoa.read(nome);
//		if (p == null) {
//			DAO.rollback();
//			throw new Exception("criar telefone - nome inexistente" + nome + numero);
//		}
//		Telefone t = daotelefone.read(numero);
//		if (t != null) {
//			DAO.rollback();
//			throw new Exception("criar telefone - numero ja cadastrado:" + numero);
//		}
//		if (numero.isEmpty()) {
//			DAO.rollback();
//			throw new Exception("criar telefone - numero vazio:" + numero);
//		}
//
//		t = new Telefone(numero);
//		p.adicionar(t);
//		daotelefone.create(t);
//		DAO.commit();
//	}

	public static void excluirConsulta(int numero) throws Exception {
		DAO.begin();
		Consulta c = daoConsulta.read(numero);
		if (c == null) {
			DAO.rollback();
			throw new Exception("excluir telefone - numero inexistente:" + numero);
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
//	public static List<Pessoa> consultarPessoasNTelefones(int n) {
//		List<Pessoa> result;
//		DAO.begin();
//		result = daopessoa.readByNTelefones(n);
//		DAO.commit();
//		return result;
//	}

//	public static boolean temTelefoneFixo(String nome) {
//		return daopessoa.temTelefoneFixo(nome);
//	}
//
//	public static List<Pessoa> consultarApelido(String ap) {
//		return daopessoa.consultarApelido(ap);
//	}

}
