package daodb4o;

import java.util.List;

import com.db4o.query.Query;

import modelo.Consulta;
import modelo.Medico;

public class DAOMedico extends DAO<Medico> {
	
	public Medico read (String crm) {
		Query q = manager.query();
		q.constrain(Medico.class);
		q.descend("crm").constrain(crm);
		List<Medico> resultados = q.execute();
		if (resultados.size()>0)
			return resultados.get(0);
		else
			return null;
	}
	
	public List<Medico> readAll(String caracteres) {
		Query q = manager.query();
		q.constrain(Medico.class);
		q.descend("crm").constrain(caracteres).like();		//insensitive
		List<Medico> result = q.execute(); 
		return result;
	}
}
