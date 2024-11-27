/**********************************
 * IFPB - Curso Superior de Tec. em Sist. para Internet
 * Pesist~encia de Objetos
 * Prof. Fausto Maranhão Ayres
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
import java.util.ArrayList;
import java.util.Arrays;
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

import modelo.Aluno;
import modelo.Telefone;
import regras_negocio.Fachada;

public class TelaAluno {
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
	private JLabel label_5;
	private JLabel label_6;
	private JLabel label_7;
	private JTextField textField;
	private JTextField textField_1;
	private JTextField textField_2;
	private JTextField textField_3;
	private JTextField textField_4;
	private JLabel label_1;
	private JTextField textField_5;


	/**
	 * Create the application.
	 */
	public TelaAluno() {
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
			}
		});
		frame.setTitle("Alunos");
		frame.setBounds(100, 100, 744, 428);
		frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		frame.setResizable(false);
		
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
						// pegar o nome, data nascimento e apelidos da pessoa selecionada
						String nome = (String) table.getValueAt(table.getSelectedRow(), 0);
						Aluno a = Fachada.localizarAluno(nome);
						String data = a.getDtNascimento();
						String nota = a.getNota()+"";
						textField_1.setText(nome);
						textField_2.setText(data);
						textField_3.setText(String.join(",", a.getApelidos()));
						textField_4.setText("");
						textField_5.setText(nota);
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
		button_4.setToolTipText("apagar aluno e seus dados");
		button_4.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					if (textField_1.getText().isEmpty()) {
						label.setText("nome vazio");
						return;
					}
					// pegar o nome na linha selecionada
					String nome = textField_1.getText();
					Object[] options = { "Confirmar", "Cancelar" };
					int escolha = JOptionPane.showOptionDialog(null,
							"Esta operação apagará os telefones e removerá as reunioes de " + nome, "Alerta",
							JOptionPane.DEFAULT_OPTION, JOptionPane.WARNING_MESSAGE, null, options, options[1]);
					if (escolha == 0) {
						Fachada.excluirPessoa(nome);
						label.setText("aluno excluido");
						listagem(); // listagem
					} else
						label.setText("exclusão cancelada");

				} catch (Exception erro) {
					label.setText(erro.getMessage());
				}
			}
		});
		button_4.setFont(new Font("Tahoma", Font.PLAIN, 11));
		button_4.setBounds(169, 340, 74, 23);
		frame.getContentPane().add(button_4);

		label = new JLabel("");
		label.setForeground(Color.RED);
		label.setBounds(21, 372, 607, 14);
		frame.getContentPane().add(label);

		button = new JButton("Buscar por nome");
		button.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				listagem();
			}
		});
		button.setFont(new Font("Tahoma", Font.PLAIN, 12));
		button.setBounds(175, 27, 149, 23);
		frame.getContentPane().add(button);

		textField = new JTextField();
		textField.setFont(new Font("Tahoma", Font.PLAIN, 12));
		textField.setColumns(10);
		textField.setBounds(62, 28, 106, 20);
		frame.getContentPane().add(textField);

		label_2 = new JLabel("selecione um aluno para editar");
		label_2.setBounds(21, 216, 394, 14);
		frame.getContentPane().add(label_2);

		label_3 = new JLabel("nome:");
		label_3.setHorizontalAlignment(SwingConstants.LEFT);
		label_3.setFont(new Font("Tahoma", Font.PLAIN, 11));
		label_3.setBounds(31, 239, 62, 14);
		frame.getContentPane().add(label_3);

		textField_1 = new JTextField();
		textField_1.setFont(new Font("Dialog", Font.PLAIN, 12));
		textField_1.setColumns(10);
		textField_1.setBackground(Color.WHITE);
		textField_1.setBounds(101, 235, 165, 20);
		frame.getContentPane().add(textField_1);

		label_4 = new JLabel("nascimento");
		label_4.setHorizontalAlignment(SwingConstants.LEFT);
		label_4.setFont(new Font("Tahoma", Font.PLAIN, 11));
		label_4.setBounds(31, 264, 62, 14);
		frame.getContentPane().add(label_4);

		textField_2 = new JTextField();
		textField_2.setFont(new Font("Dialog", Font.PLAIN, 12));
		textField_2.setColumns(10);
		textField_2.setBounds(101, 260, 86, 20);
		frame.getContentPane().add(textField_2);

		label_5 = new JLabel("apelidos");
		label_5.setFont(new Font("Tahoma", Font.PLAIN, 11));
		label_5.setHorizontalAlignment(SwingConstants.LEFT);
		label_5.setBounds(31, 289, 62, 14);
		frame.getContentPane().add(label_5);

		textField_3 = new JTextField();
		textField_3.setFont(new Font("Dialog", Font.PLAIN, 12));
		textField_3.setColumns(10);
		textField_3.setBounds(101, 284, 264, 20);
		frame.getContentPane().add(textField_3);

		button_1 = new JButton("Criar");
		button_1.setToolTipText("cadastrar novo aluno");
		button_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					if (textField_1.getText().isEmpty()) {
						label.setText("nome vazio");
						return;
					}
					String nome = textField_1.getText().trim();
					String nascimento = textField_2.getText().trim();
					String[] apelidos = textField_3.getText().trim().split(",");
					double nota = Double.parseDouble(textField_5.getText().trim());

					Fachada.criarAluno(nome, nascimento,new ArrayList<>( Arrays.asList(apelidos)), nota);
					String numero = textField_4.getText();
					if (!numero.isEmpty())
						Fachada.criarTelefone(nome, numero);

					label.setText("aluno criado");
					listagem();
				} catch (Exception ex) {
					label.setText(ex.getMessage());
				}
			}
		});
		button_1.setFont(new Font("Tahoma", Font.PLAIN, 11));
		button_1.setBounds(21, 340, 62, 23);
		frame.getContentPane().add(button_1);

		button_5 = new JButton("Atualizar");
		button_5.setToolTipText("atualizar aluno ");
		button_5.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					if (textField_1.getText().trim().isEmpty()) {
						label.setText("nome vazio");
						return;
					}
					String nome = textField_1.getText();
					String nascimento = textField_2.getText();
					String[] apelidos = textField_3.getText().trim().split(",");
					double nota = Double.parseDouble(textField_5.getText().trim());
					Fachada.alterarAluno(nome, nascimento, new ArrayList<>(Arrays.asList(apelidos)), nota);

					String numero = textField_4.getText();
					if (!numero.isEmpty())
						Fachada.criarTelefone(nome, numero);
					label.setText("aluno alterado");
					listagem();
				} catch (Exception ex2) {
					label.setText(ex2.getMessage());
				}
			}
		});
		button_5.setFont(new Font("Tahoma", Font.PLAIN, 11));
		button_5.setBounds(82, 340, 87, 23);
		frame.getContentPane().add(button_5);

		label_6 = new JLabel("Texto:");
		label_6.setBounds(21, 32, 46, 14);
		frame.getContentPane().add(label_6);

		button_3 = new JButton("Limpar");
		button_3.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				textField_1.setText("");
				textField_2.setText("");
				textField_3.setText("");
				textField_4.setText("");
				textField_5.setText("");
			}
		});
		button_3.setBounds(276, 234, 89, 23);
		frame.getContentPane().add(button_3);

		label_7 = new JLabel("novo numero");
		label_7.setHorizontalAlignment(SwingConstants.LEFT);
		label_7.setFont(new Font("Tahoma", Font.PLAIN, 11));
		label_7.setBounds(21, 313, 74, 14);
		frame.getContentPane().add(label_7);

		textField_4 = new JTextField();
		textField_4.setFont(new Font("Dialog", Font.PLAIN, 12));
		textField_4.setColumns(10);
		textField_4.setBounds(101, 309, 86, 20);
		frame.getContentPane().add(textField_4);
		
		label_1 = new JLabel("nota");
		label_1.setHorizontalAlignment(SwingConstants.LEFT);
		label_1.setFont(new Font("Tahoma", Font.PLAIN, 11));
		label_1.setBounds(235, 313, 74, 14);
		frame.getContentPane().add(label_1);
		
		textField_5 = new JTextField();
		textField_5.setFont(new Font("Dialog", Font.PLAIN, 12));
		textField_5.setColumns(10);
		textField_5.setBounds(276, 309, 86, 20);
		frame.getContentPane().add(textField_5);


		frame.setVisible(true);
	}

	public void listagem() {
		try {
			List<Aluno> lista = Fachada.listarAlunos();

			// objeto model contem todas as linhas e colunas da tabela
			DefaultTableModel model = new DefaultTableModel();
			table.setModel(model);

			// criar as colunas (0,1,2) da tabela
			model.addColumn("Nome");
			model.addColumn("Nascimento");
			model.addColumn("Nota");
			model.addColumn("Apelidos");
			model.addColumn("Telefones");

			// criar as linhas da tabela
			String texto1,texto2;
			for (Aluno a : lista) {
				texto1 = String.join(",", a.getApelidos()); // concatena strings

				if (a.getTelefones().size() > 0){
					texto2 = "";
					for (Telefone t : a.getTelefones())
						texto2 +=  t.getNumero() + " ";
				}
				else
					texto2 = "sem telefone";

				model.addRow(new Object[] { a.getNome(), a.getDtNascimento(), a.getNota(), texto1, texto2 });

			}
			label_2.setText("resultados: " + lista.size() + " alunos   - selecione uma linha para editar");

			// redimensionar a coluna 3 e 4
			table.setAutoResizeMode(JTable.AUTO_RESIZE_OFF); // desabilita
			table.getColumnModel().getColumn(3).setMinWidth(200); // coluna dos apelidos
			table.getColumnModel().getColumn(4).setMinWidth(200); // coluna dos telefones
			table.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS); // desabilita

		} catch (Exception erro) {
			label.setText(erro.getMessage());
		}
	}
}
