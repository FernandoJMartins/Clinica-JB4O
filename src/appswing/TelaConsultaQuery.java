/**********************************
 * IFPB - Curso Superior de Tec. em Sist. para Internet
 * POB - Persistencia de Objetos
 * Prof. Fausto Ayres
 *
 */
package appswing;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.List;

import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;
import javax.swing.border.LineBorder;
import javax.swing.table.DefaultTableModel;

import modelo.Consulta;
import modelo.Paciente;
import regras_negocio.Fachada;

public class TelaConsultaQuery {
	private JDialog frame;
	private JTable table;
	private JScrollPane scrollPane;
	private JButton button;
	private JLabel label;
	private JLabel label_4;

	private JComboBox<String> comboBox;

	/**
	 * Launch the application.
	 */
	//	public static void main(String[] args) {
	//		EventQueue.invokeLater(new Runnable() {
	//			public void run() {
	//				try {
	//					new TelaConsulta();
	//				} catch (Exception e) {
	//					e.printStackTrace();
	//				}
	//			}
	//		});
	//	}

	/**
	 * Create the application.
	 */
	public TelaConsultaQuery() {
		initialize();
		frame.setVisible(true);
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JDialog();
		frame.setModal(true);

		frame.setResizable(false);
		frame.setTitle("Consultas");
		frame.setBounds(100, 100, 729, 385);
		frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		frame.addWindowListener(new WindowAdapter() {
			@Override
			public void windowOpened(WindowEvent e) {
				Fachada.inicializar();
			}
			@Override
			public void windowClosing(WindowEvent e) {
				Fachada.finalizar();
			}
		});

		scrollPane = new JScrollPane();
		scrollPane.setBounds(21, 43, 674, 148);
		frame.getContentPane().add(scrollPane);

		table = new JTable() {
			public boolean isCellEditable(int rowIndex, int vColIndex) {
				return false;
			}
		};

		table.setGridColor(Color.BLACK);
		table.setRequestFocusEnabled(false);
		table.setFocusable(false);
		table.setBackground(Color.LIGHT_GRAY);
		table.setFillsViewportHeight(true);
		table.setRowSelectionAllowed(true);
		table.setFont(new Font("Tahoma", Font.PLAIN, 14));
		scrollPane.setViewportView(table);
		table.setBorder(new LineBorder(new Color(0, 0, 0)));
		table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		table.setShowGrid(true);
		table.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);

		label = new JLabel("");		//label de mensagem
		label.setForeground(Color.BLUE);
		label.setBounds(21, 321, 688, 14);
		frame.getContentPane().add(label);

		label_4 = new JLabel("resultados:");
		label_4.setBounds(21, 190, 431, 14);
		frame.getContentPane().add(label_4);

		button = new JButton("Consultar");
		button.setFont(new Font("Tahoma", Font.PLAIN, 12));
		button.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int index = comboBox.getSelectedIndex();
				if(index<0)
					label_4.setText("consulta nao selecionada");
				else {
					label_4.setText("");
					switch(index) {
					
					case 0: 
						String n = JOptionPane.showInputDialog("digite a quantidade n");
						int numero = Integer.parseInt(n);
						//List<Pessoa> resultado1 = Fachada.consultarMesNascimento(mes) ;
						listagemPacienteComNConsultas(numero);
						break;
						
					case 1: 
						String crm = JOptionPane.showInputDialog("digite o medico X");
						//List<Pessoa> resultado2 = Fachada.consultarApelido(modelo);
						listagemPacientesSeConsultaramComMedico(crm);		
						break;
						
					case 2: 
						String data = JOptionPane.showInputDialog("digite a data");
						//List<Consulta> resultado3 = Fachada.consultasDoPlanoNaData(data);
						listagemConsultaPorData(data);
						break;
					}
				}

			}
		});
		button.setBounds(606, 10, 89, 23);
		frame.getContentPane().add(button);

		comboBox = new JComboBox<String>();
		comboBox.setToolTipText("selecione a consulta");
		
		comboBox.setModel(new DefaultComboBoxModel<>(
				new String[] {"quais os pacientes com mais de N consultas",
						"quais os pacientes que se consultaram com medico X", 
						"quais as consultas do tipo plano na data X" }));
		
		comboBox.setBounds(21, 10, 513, 22);
		frame.getContentPane().add(comboBox);
	}
	
	public void listagemConsultaPorData(String data) {
		try {
			List<Consulta> lista = Fachada.consultasDoPlanoNaData(data);

			// objeto model contem todas as linhas e colunas da tabela
			DefaultTableModel model = new DefaultTableModel();
			table.setModel(model);

			// criar as colunas (0,1,2,3) da tabela
			model.addColumn("ID");
			model.addColumn("Data");
			model.addColumn("Paciente");
			model.addColumn("Medico");
			model.addColumn("Tipo");

			// criar as linhas da tabela
			for (Consulta c : lista) {
				model.addRow(new Object[] { c.getId(),c.getData(), c.getPaciente(), c.getMedico(),c.getTipo() });
			}
			//label_2.setText("resultados: " + lista.size() + " consultas   - selecione uma linha para editar");

			table.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS); // desabilita

		} catch (Exception erro) {
			label.setText(erro.getMessage());
		}
	}
	
	public void listagemPacientesSeConsultaramComMedico(String crm) {
		try {
			List<Paciente> lista = Fachada.consultaPacientesSeConsultaramComMedico(crm);

			// objeto model contem todas as linhas e colunas da tabela
			DefaultTableModel model = new DefaultTableModel();
			table.setModel(model);

			// criar as colunas (0,1,2) da tabela
			model.addColumn("Nome");
			model.addColumn("CPF");
			model.addColumn("Consultas");


			// criar as linhas da tabela
			String texto2;
			for (Paciente p : lista) {
				if (p.getConsultas().size() > 0) {
					texto2 = "";
					for (Consulta c : p.getConsultas())
						texto2 += c.getId() + " ";
				} else
					texto2 = "sem consultas";

				model.addRow(new Object[] { p.getNome(), p.getCpf(), texto2});

			}
			table.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS); // desabilita

		} catch (Exception erro) {
			label.setText(erro.getMessage());
		}
	}

	public void listagemPacienteComNConsultas(int n) {
		try {
			List<Paciente> lista = Fachada.consultaNumeroConsultasMaiorQue(n);

			// objeto model contem todas as linhas e colunas da tabela
			DefaultTableModel model = new DefaultTableModel();
			table.setModel(model);

			// criar as colunas (0,1,2) da tabela
			model.addColumn("Nome");
			model.addColumn("CPF");
			model.addColumn("Consultas");
			


			// criar as linhas da tabela
			String texto2;
			for (Paciente p : lista) {
				if (p.getConsultas().size() > 0) {
					texto2 = "";
					for (Consulta c : p.getConsultas())
						texto2 += c.getId() + " ";
				} else
					texto2 = "sem consultas";

				model.addRow(new Object[] { p.getNome(), p.getCpf(), texto2});

			}
			table.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS); // desabilita

		} catch (Exception erro) {
			label.setText(erro.getMessage());
		}
	}
	
	


}
