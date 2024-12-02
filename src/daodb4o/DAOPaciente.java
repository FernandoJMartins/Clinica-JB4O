package daodb4o;

import java.util.List;

import com.db4o.query.Query;

import modelo.Medico;
import modelo.Paciente;

public class DAOPaciente extends DAO<Paciente>{
		
		public Paciente read (String cpf) {
			Query q = manager.query();
			q.constrain(Paciente.class);
			q.descend("cpf").constrain(cpf);
			List<Paciente> resultados = q.execute();
			if (resultados.size()>0)
				return resultados.get(0);
			else
				return null;
		}
		
		
		public List<Paciente> readAll(String caracteres) {
			Query q = manager.query();
			q.constrain(Paciente.class);
			q.descend("cpf").constrain(caracteres).like();		//insensitive
			List<Paciente> result = q.execute(); 
			return result;
		}
	
}
