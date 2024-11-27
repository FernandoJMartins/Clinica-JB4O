package regras_negocio;
/**********************************
 * IFPB - SI
 * POB - Persistencia de Objetos
 * Prof. Fausto Ayres
 **********************************/

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.List;

import daodb4o.DAO;
import daodb4o.DAOAluno;
import daodb4o.DAOPessoa;
import daodb4o.DAOTelefone;
import modelo.Aluno;
import modelo.Pessoa;
import modelo.Telefone;

public class Fachada {
	private Fachada() {}

	private static DAOPessoa daopessoa = new DAOPessoa();
	private static DAOAluno daoaluno = new DAOAluno();
	private static DAOTelefone daotelefone = new DAOTelefone();

	public static void inicializar() {
		DAO.open();
	}

	public static void finalizar() {
		DAO.close();
	}

	public static Pessoa localizarPessoa(String nome) throws Exception {
		Pessoa p = daopessoa.read(nome);
		if (p == null) {
			throw new Exception("pessoa inexistente:" + nome);
		}
		return p;
	}
	public static Aluno localizarAluno(String nome) throws Exception {
		Aluno a = daoaluno.read(nome);
		if (a == null) {
			throw new Exception("aluno inexistente:" + nome);
		}
		return a;
	}

	public static void criarPessoa(String nome, String data, List<String> apelidos) throws Exception {
		DAO.begin();
		try {
			LocalDate.parse(data, DateTimeFormatter.ofPattern("dd/MM/yyyy"));
		} catch (DateTimeParseException e) {
			DAO.rollback();
			throw new Exception("formato data invalido:" + data);
		}
		Pessoa p = daopessoa.read(nome);
		if (p != null) {
			DAO.rollback();
			throw new Exception("criar pessoa - nome ja existe:" + nome);
		}
		p = new Pessoa(nome);
		p.setDtNascimento(data);
		p.setApelidos(apelidos);
		daopessoa.create(p);
		DAO.commit();
	}

	public static void criarAluno(String nome, String data, List<String>  apelidos, double nota) throws Exception {
		DAO.begin();
		try {
			LocalDate.parse(data, DateTimeFormatter.ofPattern("dd/MM/yyyy"));
		} catch (DateTimeParseException e) {
			DAO.rollback();
			throw new Exception("formato data invalido:" + data);
		}

		Pessoa p = daopessoa.read(nome); // nome de qualquer pessoa
		if (p != null) {
			DAO.rollback();
			throw new Exception("criar aluno - nome ja existe:" + nome);
		}

		Aluno a = new Aluno(nome, nota);
		a.setDtNascimento(data);
		a.setApelidos(apelidos);
		daoaluno.create(a);
		DAO.commit();
	}

	public static void alterarPessoa(String nome, String data, List<String> apelidos) throws Exception {
		// permite alterar data, foto e apelidos
		DAO.begin();
		Pessoa p = daopessoa.read(nome);
		if (p == null) {
			DAO.rollback();
			throw new Exception("alterar pessoa - pessoa inexistente:" + nome);
		}

		p.setApelidos(apelidos);
		if (data != null) {
			try {
				LocalDate.parse(data, DateTimeFormatter.ofPattern("dd/MM/yyyy"));
				p.setDtNascimento(data);
			} catch (DateTimeParseException e) {
				DAO.rollback();
				throw new Exception("alterar pessoa - formato data invalido:" + data);
			}
		}

		daopessoa.update(p);
		DAO.commit();
	}

	public static void alterarAluno(String nome, String data, List<String>  apelidos, double nota) throws Exception {
		// permite alterar data, foto e apelidos
		DAO.begin();
		Aluno a = daoaluno.read(nome);
		if (a == null) {
			DAO.rollback();
			throw new Exception("alterar aluno - nome inexistente:" + nome);
		}

		a.setApelidos(apelidos);
		if (data != null) {
			try {
				LocalDate.parse(data, DateTimeFormatter.ofPattern("dd/MM/yyyy"));
				a.setDtNascimento(data);
			} catch (DateTimeParseException e) {
				DAO.rollback();
				throw new Exception("alterar aluno - formato data invalido:" + data);
			}
		}
		a.setNota(nota);
		daopessoa.update(a);
		DAO.commit();
	}

	public static void alterarData(String nome, String data) throws Exception {
		DAO.begin();
		Pessoa p = daopessoa.read(nome);
		if (p == null) {
			DAO.rollback();
			throw new Exception("alterar pessoa - pessoa inexistente:" + nome);
		}

		if (data != null) {
			try {
				LocalDate.parse(data, DateTimeFormatter.ofPattern("dd/MM/yyyy"));
				p.setDtNascimento(data);
			} catch (DateTimeParseException e) {
				DAO.rollback();
				throw new Exception("alterar data - formato data invalido:" + data);
			}
		}

		daopessoa.update(p);
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
