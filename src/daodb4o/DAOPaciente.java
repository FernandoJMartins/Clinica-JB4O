package daodb4o;

import java.util.List;

import com.db4o.query.Query;

import modelo.Paciente;

public class DAOPaciente extends DAO<Paciente>{
		
		public Paciente read (String cpf) {
			Query q = manager.query();
			q.constrain(Paciente.class);
			q.descend("nome").constrain(cpf);
			List<Paciente> resultados = q.execute();
			if (resultados.size()>0)
				return resultados.get(0);
			else
				return null;
		}
	
}
