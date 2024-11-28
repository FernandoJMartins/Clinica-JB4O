package daodb4o;

import java.util.List;

import com.db4o.query.Query;
import modelo.Consulta;

public class DAOConsulta extends DAO<Consulta>{
	public Consulta read (String nome) {
		Query q = manager.query();
		q.constrain(Consulta.class);
		q.descend("nome").constrain(nome);
		List<Consulta> resultados = q.execute();
		if (resultados.size()>0)
			return resultados.get(0);
		else
			return null;
	}
	
	public void create(Consulta obj){
		int novoid = super.gerarId(Consulta.class);  	//gerar novo id da classe
		obj.setId(novoid);				//atualizar id do objeto antes de grava-lo no banco
		manager.store( obj );
	}
}
