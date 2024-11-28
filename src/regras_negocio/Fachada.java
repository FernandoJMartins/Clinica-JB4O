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
import daodb4o.DAOAluno;
import daodb4o.DAOConsulta;
import daodb4o.DAOMedico;
import daodb4o.DAOPaciente;
import daodb4o.DAOPessoa;
import daodb4o.DAOTelefone;
import modelo.Aluno;
import modelo.Consulta;
import modelo.Medico;
import modelo.Paciente;
import modelo.Pessoa;
import modelo.Telefone;

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
		
		Consulta consulta = new Consulta(dataFormatada, paciente, medico, tipo);
		
		daoConsulta.create(consulta);
		DAO.commit();
	}

	public static void criarPaciente(String cpf, String nome) throws Exception {
		Paciente p = daoPaciente.read(cpf);
		if (p != null) {
			DAO.rollback();
			throw new Exception("criar paciente - nome ja existe:" + nome);
		}
		Paciente a = new Paciente(cpf, nome);
		
		daoPaciente.create(a);
		DAO.commit();
	}
	
	public static void criarMedico(String crm, String nome, String especialidade) throws Exception {
		Medico m = daoMedico.read(crm);
		if (m != null) {
			DAO.rollback();
			throw new Exception("criar Medico - Medico ja existe:" + nome);
		}
		Medico medico = new Medico(crm, nome, especialidade);
		daoMedico.create(medico);
		DAO.commit();
	}

	public static void alterarPaciente(String cpf, String nome, ArrayList<Consulta> consultas) throws Exception {
		// permite alterar data, foto e apelidos
		DAO.begin();
		Paciente p = daoPaciente.read(cpf);
		if (p == null) {
			DAO.rollback();
			throw new Exception("alterar pessoa - pessoa inexistente:" + nome);
		}
		
		p.setCpf(cpf);
		p.setNome(nome);
		p.setConsultas(consultas);

		daoPaciente.update(p);
		DAO.commit();
	}

	public static void alterarMedico(String nome, String crm, String Especialidade) throws Exception {
		// permite alterar data, foto e apelidos
		DAO.begin();
		Medico m = daoMedico.read(crm);
		if (m == null) {
			DAO.rollback();
			throw new Exception("alterar medico - crm inexistente:" + crm);
		}

		m.setNome(nome);
		m.setCrm(crm);
		m.setEspecialidade(Especialidade);
		
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

	public static void alterarNome(String nome, String novonome) throws Exception {
		DAO.begin();
		Pessoa p = daopessoa.read(nome); // usando chave primaria
		if (p == null) {
			DAO.rollback();
			throw new Exception("alterar nome - nome inexistente:" + nome);
		}
		p.setNome(novonome);
		daopessoa.update(p);
		DAO.commit();
	}

	public static void excluirPessoa(String nome) throws Exception {
		DAO.begin();
		Pessoa p = daopessoa.read(nome);
		if (p == null) {
			DAO.rollback();
			throw new Exception("excluir pessoa - nome inexistente:" + nome);
		}

		// desligar a pessoa de seus telefones orfaos e apaga-los do banco
		for (Telefone t : p.getTelefones()) {
			daotelefone.delete(t); // deletar o telefone orfao
		}

		daopessoa.delete(p); // apagar a pessoa
		DAO.commit();
	}

	public static void criarTelefone(String nome, String numero) throws Exception {
		DAO.begin();
		Pessoa p = daopessoa.read(nome);
		if (p == null) {
			DAO.rollback();
			throw new Exception("criar telefone - nome inexistente" + nome + numero);
		}
		Telefone t = daotelefone.read(numero);
		if (t != null) {
			DAO.rollback();
			throw new Exception("criar telefone - numero ja cadastrado:" + numero);
		}
		if (numero.isEmpty()) {
			DAO.rollback();
			throw new Exception("criar telefone - numero vazio:" + numero);
		}

		t = new Telefone(numero);
		p.adicionar(t);
		daotelefone.create(t);
		DAO.commit();
	}

	public static void excluirTelefone(String numero) throws Exception {
		DAO.begin();
		Telefone t = daotelefone.read(numero);
		if (t == null) {
			DAO.rollback();
			throw new Exception("excluir telefone - numero inexistente:" + numero);
		}
		Pessoa p = t.getPessoa();
		p.remover(t);
		t.setPessoa(null);
		daopessoa.update(p);
		daotelefone.delete(t);
		DAO.commit();
	}

	public static void alterarNumero(String numero, String novonumero) throws Exception {
		DAO.begin();
		Telefone t1 = daotelefone.read(numero);
		if (t1 == null) {
			DAO.rollback();
			throw new Exception("alterar numero - numero inexistente:" + numero);
		}
		Telefone t2 = daotelefone.read(novonumero);
		if (t2 != null) {
			DAO.rollback();
			throw new Exception("alterar numero - novo numero ja existe:" + novonumero);
		}
		if (novonumero.isEmpty()) {
			DAO.rollback();
			throw new Exception("alterar numero - novo numero vazio:");
		}

		t1.setNumero(novonumero); // substituir
		daotelefone.update(t1);
		DAO.commit();
	}

	public static List<Pessoa> listarPessoas() {
		List<Pessoa> result = daopessoa.readAll();
		return result;
	}

	public static List<Aluno> listarAlunos() {
		List<Aluno> result = daoaluno.readAll();
		return result;
	}

	public static List<Telefone> listarTelefones() {
		List<Telefone> result = daotelefone.readAll();
		return result;
	}

	/**********************************************************
	 * 
	 * CONSULTAS IMPLEMENTADAS NOS DAO
	 * 
	 **********************************************************/
	public static List<Pessoa> consultarPessoas(String caracteres) {
		List<Pessoa> result;
		if (caracteres.isEmpty())
			result = daopessoa.readAll();
		else
			result = daopessoa.readAll(caracteres);
		return result;
	}


	public static List<Telefone> consultarTelefones(String digitos) {
		List<Telefone> result;
		if (digitos.isEmpty())
			result = daotelefone.readAll();
		else
			result = daotelefone.readAll(digitos);
		return result;
	}

	public static List<Pessoa> consultarMesNascimento(String mes) {
		List<Pessoa> result;
		result = daopessoa.readByMes(mes);
		return result;
	}

	public static List<Pessoa> consultarPessoasNTelefones(int n) {
		List<Pessoa> result;
		DAO.begin();
		result = daopessoa.readByNTelefones(n);
		DAO.commit();
		return result;
	}

	public static boolean temTelefoneFixo(String nome) {
		return daopessoa.temTelefoneFixo(nome);
	}

	public static List<Pessoa> consultarApelido(String ap) {
		return daopessoa.consultarApelido(ap);
	}

}
