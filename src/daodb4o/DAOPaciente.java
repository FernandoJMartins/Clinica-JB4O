package daodb4o;

import java.util.ArrayList;
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
		
		
		public List<Paciente> readAll(String cpf) {
			Query q = manager.query();
			q.constrain(Paciente.class);
			q.descend("cpf").constrain(cpf).like();		//insensitive
			List<Paciente> result = q.execute(); 
			return result;
		}
		
		public List<Paciente> readAllMaiorQue(int n) {
			Query q = manager.query();
			q.constrain(Paciente.class);
			
			List<Paciente> todosPacientes = q.execute();
			List<Paciente> result = new ArrayList<>();
			
			for (Paciente p : todosPacientes) {
				if (p.getConsultas().size() > n) {
					result.add(p);
				}
			}
			return result;
		}
	
}
