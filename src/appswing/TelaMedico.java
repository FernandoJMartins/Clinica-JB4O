/**********************************
 * IFPB - Curso Superior de Tec. em Sist. para Internet
 * Pesist~encia de Objetos
 * Prof. Fausto Maranh�o Ayres
 **********************************/

package appswing;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
//import java.util.ArrayList;
//import java.util.Arrays;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.SwingConstants;
import javax.swing.border.LineBorder;
import javax.swing.table.DefaultTableModel;


import modelo.Medico;
import regras_negocio.Fachada;

public class TelaMedico {
	private JDialog frame;
	private JTable table;
	private JScrollPane scrollPane;
	private JButton button;
	private JButton button_1;
	private JButton button_3;
	private JButton button_4;
	private JButton button_5;
	private JLabel label;
	private JLabel label_2;
	private JLabel label_3;
	private JLabel label_4;
	private JLabel label_6;
//	private JLabel label_7;
	private JTextField textField;
	private JTextField textField_1;
	private JTextField textField_2;
	private JTextField textField_3;
	private JLabel label_1;
	//private JTextField textField_4;


	/**
	 * Create the application.
	 */
	public TelaMedico() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JDialog();
		frame.setModal(true);		//janela modal
		
		frame.addWindowListener(new WindowAdapter() {
			@Override
			public void windowOpened(WindowEvent arg0) {
				Fachada.inicializar();
				listagem();
			}
			@Override
			public void windowClosing(WindowEvent e) {
				Fachada.finalizar();
				frame.dispose();
			}
		});
		frame.setTitle("Medicos");
		frame.setBounds(100, 100, 744, 428);
		frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		frame.setResizable(false);
		frame.getContentPane().setLayout(null);
		
		scrollPane = new JScrollPane();
		scrollPane.setBounds(21, 63, 685, 155);
		frame.getContentPane().add(scrollPane);

		table = new JTable();
		// evento de selecao de uma linha da tabela
		table.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				try {
					if (table.getSelectedRow() >= 0) {
						// pegar o nome, crm e consultas do Medico selecionado
						String crm = (String) table.getValueAt(table.getSelectedRow(), 1);
						Medico m = Fachada.localizarMedico(crm);
						String nome = m.getNome();
						String especialidade = m.getEspecialidade();
						//String consulta = c.getConsultas()+"";
						textField_1.setText(nome);
						textField_2.setText(crm);
						textField_3.setText(especialidade);
						//textField_4.setText("");
						//textField_5.setText(nota);
						label.setText("");
					}
				} catch (Exception erro) {
					label.setText(erro.getMessage());
				}

			}
		});

		table.setGridColor(Color.BLACK);
		table.setRequestFocusEnabled(false);
		table.setFocusable(false);
		table.setBackground(Color.WHITE);
		table.setFillsViewportHeight(true);
		table.setRowSelectionAllowed(true);
		table.setFont(new Font("Tahoma", Font.PLAIN, 14));
		scrollPane.setViewportView(table);
		table.setBorder(new LineBorder(new Color(0, 0, 0)));
		table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		table.setShowGrid(true);
		table.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);

		button_4 = new JButton("Apagar");
		button_4.setBounds(169, 340, 74, 23);
		button_4.setToolTipText("apagar Medico e seus dados");
		button_4.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					if (textField_2.getText().isEmpty()) {
						label.setText("crm vazio");
						return;
					}
					// pegar o nome na linha selecionada
					String crm = textField_2.getText();
					Object[] options = { "Confirmar", "Cancelar" };
					int escolha = JOptionPane.showOptionDialog(null,
							"Esta operacao apagara os dados de " + crm, "Alerta",
							JOptionPane.DEFAULT_OPTION, JOptionPane.WARNING_MESSAGE, null, options, options[1]);
					if (escolha == 0) {
						Fachada.excluirMedico(crm);
						label.setText("Medico excluido");
						listagem(); // listagem
					} else
						label.setText("operacao cancelada");

				} catch (Exception erro) {
					label.setText(erro.getMessage());
				}
			}
		});
		button_4.setFont(new Font("Tahoma", Font.PLAIN, 11));
		frame.getContentPane().add(button_4);

		label = new JLabel("");
		label.setBounds(21, 372, 697, 14);
		label.setForeground(Color.RED);
		frame.getContentPane().add(label);

		button = new JButton("Buscar por crm");
		button.setBounds(175, 27, 149, 23);
		button.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				listagem();
			}
		});
		button.setFont(new Font("Tahoma", Font.PLAIN, 12));
		frame.getContentPane().add(button);

		textField = new JTextField();
		textField.setBounds(62, 28, 106, 20);
		textField.setFont(new Font("Tahoma", Font.PLAIN, 12));
		textField.setColumns(10);
		frame.getContentPane().add(textField);

		label_2 = new JLabel("selecione um Medico para editar");
		label_2.setBounds(21, 216, 394, 14);
		frame.getContentPane().add(label_2);

		label_3 = new JLabel("nome:");
		label_3.setBounds(31, 239, 62, 14);
		label_3.setHorizontalAlignment(SwingConstants.LEFT);
		label_3.setFont(new Font("Tahoma", Font.PLAIN, 11));
		frame.getContentPane().add(label_3);

		textField_1 = new JTextField();
		textField_1.setBounds(101, 235, 165, 20);
		textField_1.setFont(new Font("Dialog", Font.PLAIN, 12));
		textField_1.setColumns(10);
		textField_1.setBackground(Color.WHITE);
		frame.getContentPane().add(textField_1);

		label_4 = new JLabel("crm:");
		label_4.setBounds(31, 264, 62, 14);
		label_4.setHorizontalAlignment(SwingConstants.LEFT);
		label_4.setFont(new Font("Tahoma", Font.PLAIN, 11));
		frame.getContentPane().add(label_4);

		textField_2 = new JTextField();
		textField_2.setBounds(101, 260, 86, 20);
		textField_2.setFont(new Font("Dialog", Font.PLAIN, 12));
		textField_2.setColumns(10);
		frame.getContentPane().add(textField_2);

		button_1 = new JButton("Criar");
		button_1.setBounds(21, 340, 62, 23);
		button_1.setToolTipText("cadastrar novo Medico");
		button_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					if (textField_1.getText().isEmpty()) {
						label.setText("nome vazio");
						return;
					}
					String nome = textField_1.getText().trim();
					String crm = textField_2.getText().trim();
					String especialidade = textField_3.getText().trim();
					//String[] apelidos = textField_3.getText().trim().split(",");
					//double nota = Double.parseDouble(textField_5.getText().trim());
					Fachada.criarMedico(nome,crm, especialidade);
//					Fachada.criarAluno(nome, nascimento,new ArrayList<>( Arrays.asList(apelidos)), nota);
//					String numero = textField_4.getText();
//					if (!numero.isEmpty())
//						Fachada.criarTelefone(nome, numero);

					label.setText("Medico criado");
					listagem();
				} catch (Exception ex) {
					label.setText(ex.getMessage());
				}
			}
		});
		button_1.setFont(new Font("Tahoma", Font.PLAIN, 11));
		frame.getContentPane().add(button_1);

		button_5 = new JButton("Atualizar");
		button_5.setBounds(82, 340, 87, 23);
		button_5.setToolTipText("atualizar Medico ");
		button_5.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					if (textField_2.getText().trim().isEmpty()) {
						label.setText("crm vazio");
						return;
					}
					String nome = textField_1.getText().trim();
					String crm = textField_2.getText().trim();
					String especialidade = textField_3.getText().trim();
//					String[] apelidos = textField_3.getText().trim().split(",");
//					double nota = Double.parseDouble(textField_5.getText().trim());
//					Fachada.alterarAluno(nome, nascimento, new ArrayList<>(Arrays.asList(apelidos)), nota);
					Fachada.alterarMedico(nome,crm,especialidade);

//					String numero = textField_4.getText();
//					if (!numero.isEmpty())
//						Fachada.criarTelefone(nome, numero);
					label.setText("Medico alterado");
					listagem();
				} catch (Exception ex2) {
					label.setText(ex2.getMessage());
				}
			}
		});
		button_5.setFont(new Font("Tahoma", Font.PLAIN, 11));
		frame.getContentPane().add(button_5);

		label_6 = new JLabel("Texto:");
		label_6.setBounds(21, 32, 46, 14);
		frame.getContentPane().add(label_6);

		button_3 = new JButton("Limpar");
		button_3.setBounds(276, 234, 89, 23);
		button_3.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				textField_1.setText("");
				textField_2.setText("");
				//textField_3.setText("");
				//textField_4.setText("");
				//textField_5.setText("");
			}
		});
		frame.getContentPane().add(button_3);
		
		textField_3 = new JTextField();
		textField_3.setFont(new Font("Dialog", Font.PLAIN, 12));
		textField_3.setColumns(10);
		textField_3.setBounds(101, 290, 86, 20);
		frame.getContentPane().add(textField_3);
		
		label_1 = new JLabel("especialidade");
		label_1.setHorizontalAlignment(SwingConstants.LEFT);
		label_1.setFont(new Font("Tahoma", Font.PLAIN, 11));
		label_1.setBounds(21, 289, 72, 14);
		frame.getContentPane().add(label_1);

//		label_7 = new JLabel("nova consulta");
//		label_7.setBounds(21, 295, 74, 14);
//		label_7.setHorizontalAlignment(SwingConstants.LEFT);
//		label_7.setFont(new Font("Tahoma", Font.PLAIN, 11));
//		frame.getContentPane().add(label_7);

//		textField_4 = new JTextField();
//		textField_4.setBounds(101, 291, 86, 20);
//		textField_4.setFont(new Font("Dialog", Font.PLAIN, 12));
//		textField_4.setColumns(10);
//		frame.getContentPane().add(textField_4);


		frame.setVisible(true);
	}

	public void listagem() {
		try {
			List<Medico> lista = Fachada.consultarMedicos(textField.getText());

			// objeto model contem todas as linhas e colunas da tabela
			DefaultTableModel model = new DefaultTableModel();
			table.setModel(model);

			// criar as colunas (0,1,2) da tabela
			model.addColumn("Nome");
			model.addColumn("CRM");
			model.addColumn("Especialidade");


			// criar as linhas da tabela
			//String texto2;
			for (Medico m : lista) {
				

				model.addRow(new Object[] { m.getNome(), m.getCrm(), m.getEspecialidade() });

			}
			label_2.setText("resultados: " + lista.size() + " Medicos   - selecione uma linha para editar");
			table.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS); // desabilita

		} catch (Exception erro) {
			label.setText(erro.getMessage());
		}
	}
}
