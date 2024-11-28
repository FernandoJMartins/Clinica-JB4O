package daodb4o;

import java.util.List;

import com.db4o.query.Query;
import modelo.Medico;

public class DAOMedico extends DAO<Medico> {
	
	public Medico read (String nome) {
		Query q = manager.query();
		q.constrain(Medico.class);
		q.descend("nome").constrain(nome);
		List<Medico> resultados = q.execute();
		if (resultados.size()>0)
			return resultados.get(0);
		else
			return null;
	}
}
