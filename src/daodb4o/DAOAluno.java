/**********************************
 * IFPB - SI
 * POB - Persistencia de Objetos
 * Prof. Fausto Ayres
 **********************************/
package daodb4o;

import java.util.List;

import com.db4o.query.Query;

import modelo.Aluno;
import modelo.Pessoa;

public class DAOAluno  extends DAO<Aluno>{
	
	//nome é usado como campo unico 
	public Aluno read (String nome) {
		Query q = manager.query();
		q.constrain(Pessoa.class);
		q.descend("nome").constrain(nome);
		List<Aluno> resultados = q.execute();
		if (resultados.size()>0)
			return resultados.get(0);
		else
			return null;
	}
	
	public void create(Aluno obj){
		int novoid = super.gerarId(Aluno.class);  	//gerar novo id da classe
		obj.setId(novoid);				//atualizar id do objeto antes de grava-lo no banco
		manager.store( obj );
	}
	
}

