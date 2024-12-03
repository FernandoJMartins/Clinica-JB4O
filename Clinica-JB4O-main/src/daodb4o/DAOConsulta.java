package daodb4o;

import java.util.List;

import com.db4o.query.Query;
import modelo.Consulta;
import modelo.Medico;

public class DAOConsulta extends DAO<Consulta>{
	
	public Consulta read (int id) {
		Query q = manager.query();
		q.constrain(Consulta.class);
		q.descend("id").constrain(id);
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
	
	public List<Consulta> readAll(String data) {
		Query q = manager.query();
		q.constrain(Consulta.class);
		q.descend("data").constrain(data).like();		//insensitive
		List<Consulta> result = q.execute(); 
		return result;
	}
	
	public List<Consulta> readAllPlanoPorData(String data) { // metodo especial
		Query q = manager.query();
		q.constrain(Consulta.class);
		q.descend("data").constrain(data).equal();	//insensitive
		q.descend("tipo").constrain("plano").like(); //insensitive
		List<Consulta> result = q.execute(); 
		return result;
	}
	
	public List<Consulta> consultaPacientesSeConsultaramComMedico(String crm){
		Query q = manager.query();
		q.constrain(Consulta.class);
		q.descend("medico").constrain(crm).equal();	//insensitive
		List<Consulta> result = q.execute(); 
		return result;
	}
}
