/**********************************
 * IFPB - SI
 * POB - Persistencia de Objetos
 * Prof. Fausto Ayres
 **********************************/
package daodb4o;


import java.util.List;

import com.db4o.query.Query;

import modelo.Telefone;

public class DAOTelefone  extends DAO<Telefone>{
	public Telefone read (String num) {
		Query q = manager.query();
		q.constrain(Telefone.class);
		q.descend("numero").constrain(num);
		List<Telefone> resultados = q.execute();
		if (resultados.size()>0)
			return resultados.get(0);
		else
			return null;
	}
	
	public void create(Telefone obj){
		int novoid = super.gerarId(Telefone.class);  	//gerar novo id da classe
		obj.setId(novoid);				//atualizar id do objeto antes de grava-lo no banco
		manager.store( obj );
	}
	
	/**********************************************************
	 * 
	 * TODAS AS CONSULTAS DE TELEFONE
	 * 
	 **********************************************************/

	public List<Telefone> readAll(String digitos) {
		Query q = manager.query();
		q.constrain(Telefone.class);
		q.descend("numero").constrain(digitos).like();
		return q.execute(); 
	}

	public List<Telefone> readByNome(String nome) {
		Query q = manager.query();
		q.constrain(Telefone.class);
		q.descend("pessoa").descend("nome").constrain(nome);
		return q.execute(); 
	}

}
