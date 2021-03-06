package br.univel;

import java.awt.Color;
import java.awt.EventQueue;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.math.BigDecimal;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.function.Consumer;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;
import javax.swing.table.AbstractTableModel;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;

import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.view.JasperViewer;

public class TelaPrincipal extends JFrame {

	private JPanel contentPane;
	private JTabbedPane tabbedPane;
	private JTable tableCli;
	private JTable tablePro;
	private JTable tableProVda;
	private JTable tableVda;

	private Consumer<Cliente> consumerOnOk;
	private Runnable runnableOnCancel;

	private List<Produto> listaProduto;
	private List<Cliente> listaCliente;
	private List<ProdVenda> listaProdVenda;

	private SqlGenImplementation imp;

	private String ABA_UM = "Produtos";
	private String ABA_DOIS = "Clientes";
	private String ABA_TRES = "Vendas";

	private BigDecimal Vunit;
	private BigDecimal Vtot = new BigDecimal("0");
	private BigDecimal Vpago = new BigDecimal("0");
	private BigDecimal Vtroco = new BigDecimal("0");

	private NumberField tfCodigo;
	private NumberField tfPreco;
	private JTextField tfDesc;
	private JScrollPane PainelProdutos;
	private JScrollPane PainelVenda;
	private JTextField tfNome;
	private JTextField tfEnd;
	private JTextField tfNumero;
	private JTextField tfComp;
	private JTextField tfBairro;
	private JTextField tfCidade;
	private JTextField tfCep;
	private JTextField tfEstado;
	private JTextField tfTel;
	private JTextField tfCel;
	private JTextField tfId;
	private JTextField tfVdaCli;
	private JTextField tfVdaPro;
	private JTextField tfVdaId;
	private JTextField tfVdaQtd;
	private JTextField tfVPago;
	private JTextField tfTroco;
	private JTextField tfTotal;
	private JTextField tfVdaIdPro;

	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					TelaPrincipal frame = new TelaPrincipal();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});

	}

	public TelaPrincipal() {

		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 700, 377);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		GridBagLayout gbl_contentPane = new GridBagLayout();
		gbl_contentPane.columnWidths = new int[] { 0, 0, 0, 0, 0 };
		gbl_contentPane.rowHeights = new int[] { 0, 0, 0, 0 };
		gbl_contentPane.columnWeights = new double[] { 1.0, 1.0, 1.0, 0.0, Double.MIN_VALUE };
		gbl_contentPane.rowWeights = new double[] { 0.0, 1.0, 1.0, Double.MIN_VALUE };
		contentPane.setLayout(gbl_contentPane);

		tabbedPane = new JTabbedPane(JTabbedPane.TOP);
		GridBagConstraints gbc_tabbedPane = new GridBagConstraints();
		gbc_tabbedPane.gridwidth = 3;
		gbc_tabbedPane.insets = new Insets(0, 0, 5, 5);
		gbc_tabbedPane.fill = GridBagConstraints.BOTH;
		gbc_tabbedPane.gridx = 0;
		gbc_tabbedPane.gridy = 1;
		contentPane.add(tabbedPane, gbc_tabbedPane);

		adicionaProdutos();
		adicionaClientes();
		adicionaVendas();

		tabbedPane.setSelectedIndex(0);

	}

	protected void adicionaProdutos() {

		for (int i = 0; i < tabbedPane.getTabCount(); i++) {
			if (tabbedPane.getTitleAt(i).equals(ABA_UM)) {
				tabbedPane.setSelectedIndex(i);
				return;
			}
		}

		JPanel telaProduto = new JPanel();

		tabbedPane.addTab(ABA_UM, telaProduto);
		GridBagLayout gbl_telaProduto = new GridBagLayout();
		gbl_telaProduto.columnWidths = new int[] { 30, 95, 65, 30, 67, 30, 97, 30 };
		gbl_telaProduto.rowHeights = new int[] { 0, 0, 0, 0, 0, 0 };
		gbl_telaProduto.columnWeights = new double[] { 1.0, 1.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0 };
		gbl_telaProduto.rowWeights = new double[] { 0.0, 0.0, 0.0, 0.0, 0.0, 0.0 };
		telaProduto.setLayout(gbl_telaProduto);

		JLabel lblCodigo = new JLabel("Codigo");
		GridBagConstraints gbc_lblCodigo = new GridBagConstraints();
		gbc_lblCodigo.anchor = GridBagConstraints.EAST;
		gbc_lblCodigo.insets = new Insets(0, 0, 5, 5);
		gbc_lblCodigo.gridx = 0;
		gbc_lblCodigo.gridy = 0;
		telaProduto.add(lblCodigo, gbc_lblCodigo);

		tfCodigo = new NumberField();
		GridBagConstraints gbc_tfCodigo = new GridBagConstraints();
		gbc_tfCodigo.insets = new Insets(0, 0, 5, 5);
		gbc_tfCodigo.fill = GridBagConstraints.HORIZONTAL;
		gbc_tfCodigo.gridx = 1;
		gbc_tfCodigo.gridy = 0;
		telaProduto.add(tfCodigo, gbc_tfCodigo);
		tfCodigo.setColumns(10);

		JLabel lblPreoUn = new JLabel("Preço Un");
		GridBagConstraints gbc_lblPreoUn = new GridBagConstraints();
		gbc_lblPreoUn.anchor = GridBagConstraints.EAST;
		gbc_lblPreoUn.insets = new Insets(0, 0, 5, 5);
		gbc_lblPreoUn.gridx = 2;
		gbc_lblPreoUn.gridy = 0;
		telaProduto.add(lblPreoUn, gbc_lblPreoUn);

		tfPreco = new NumberField();
		GridBagConstraints gbc_tfPreco = new GridBagConstraints();
		gbc_tfPreco.gridwidth = 2;
		gbc_tfPreco.insets = new Insets(0, 0, 5, 5);
		gbc_tfPreco.fill = GridBagConstraints.HORIZONTAL;
		gbc_tfPreco.gridx = 3;
		gbc_tfPreco.gridy = 0;
		telaProduto.add(tfPreco, gbc_tfPreco);
		tfPreco.setColumns(10);

		JLabel lblDescrio = new JLabel("Descrição");
		GridBagConstraints gbc_lblDescrio = new GridBagConstraints();
		gbc_lblDescrio.anchor = GridBagConstraints.EAST;
		gbc_lblDescrio.insets = new Insets(0, 0, 5, 5);
		gbc_lblDescrio.gridx = 0;
		gbc_lblDescrio.gridy = 1;
		telaProduto.add(lblDescrio, gbc_lblDescrio);

		tfDesc = new JTextField();
		GridBagConstraints gbc_tfDesc = new GridBagConstraints();
		gbc_tfDesc.insets = new Insets(0, 0, 5, 5);
		gbc_tfDesc.gridwidth = 4;
		gbc_tfDesc.fill = GridBagConstraints.HORIZONTAL;
		gbc_tfDesc.gridx = 1;
		gbc_tfDesc.gridy = 1;
		telaProduto.add(tfDesc, gbc_tfDesc);
		tfDesc.setColumns(10);

		JButton btnNewButton_3 = new JButton("ExportarXML");
		btnNewButton_3.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				SalvarProdutoXML(new File("ListaProdutos.xml"));
			}
		});
		GridBagConstraints gbc_btnNewButton_3 = new GridBagConstraints();
		gbc_btnNewButton_3.fill = GridBagConstraints.HORIZONTAL;
		gbc_btnNewButton_3.insets = new Insets(0, 0, 5, 0);
		gbc_btnNewButton_3.gridx = 7;
		gbc_btnNewButton_3.gridy = 1;
		telaProduto.add(btnNewButton_3, gbc_btnNewButton_3);

		PainelProdutos = new JScrollPane();

		GridBagConstraints gbc_PainelProdutos = new GridBagConstraints();
		gbc_PainelProdutos.gridheight = 4;
		gbc_PainelProdutos.gridwidth = 5;
		gbc_PainelProdutos.insets = new Insets(0, 0, 0, 5);
		gbc_PainelProdutos.fill = GridBagConstraints.BOTH;
		gbc_PainelProdutos.gridx = 0;
		gbc_PainelProdutos.gridy = 2;
		telaProduto.add(PainelProdutos, gbc_PainelProdutos);

		tablePro = new JTable() {

			@Override
			public String getToolTipText(MouseEvent e) {

				String tip = null;

				Point p = e.getPoint();

				int rowIndex = rowAtPoint(p);
				int colIndex = columnAtPoint(p);

				if (rowIndex == -1 || colIndex == -1) {
					return null;
				}

				try {
					tip = getValueAt(rowIndex, colIndex).toString();
				} catch (RuntimeException e1) {

				}

				return tip;

			}

		};

		PainelProdutos.setViewportView(tablePro);

		if (listaProduto == null)
			listaProduto = new ArrayList<>();

		if (listaProduto.size() == 0) {
			listarTodos(new Produto());
		}

		if (listaProduto.size() == 0) {
			importarProdutosTXT();
		}

		SetModel(1);

		JButton btnNewButton = new JButton("Adicionar");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (!tfCodigo.getText().equals("") && !tfDesc.getText().equals("") && !tfPreco.getText().equals("")) {
					Produto p = new Produto();
					p.setId(Integer.parseInt(tfCodigo.getText()));
					p.setDescricao(tfDesc.getText());
					p.setPreco(new BigDecimal(tfPreco.getText()));

					if (buscar(p) > 0) {
						JOptionPane.showMessageDialog(null, "PRODUTO JA CADASTRADO!");
						return;
					}

					if (listaProduto == null)
						listaProduto = new ArrayList<>();

					salvar(p);

					listaProduto.add(p);

					SetModel(1);

					tfCodigo.setText("");
					tfDesc.setText("");
					tfPreco.setText("");
				}
			}
		});

		GridBagConstraints gbc_btnNewButton = new GridBagConstraints();
		gbc_btnNewButton.fill = GridBagConstraints.HORIZONTAL;
		gbc_btnNewButton.insets = new Insets(0, 0, 5, 5);
		gbc_btnNewButton.gridx = 6;
		gbc_btnNewButton.gridy = 2;
		telaProduto.add(btnNewButton, gbc_btnNewButton);

		JButton btnNewButton_4 = new JButton("ImportarXML");
		btnNewButton_4.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				LerProdutoXML(new File("ListaProdutos.xml"));
				SetModel(1);
			}
		});
		GridBagConstraints gbc_btnNewButton_4 = new GridBagConstraints();
		gbc_btnNewButton_4.fill = GridBagConstraints.HORIZONTAL;
		gbc_btnNewButton_4.insets = new Insets(0, 0, 5, 0);
		gbc_btnNewButton_4.gridx = 7;
		gbc_btnNewButton_4.gridy = 2;
		telaProduto.add(btnNewButton_4, gbc_btnNewButton_4);

		JButton btnNewButton_2 = new JButton("Alterar");
		btnNewButton_2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Produto c = getProdutoSelecionadoNaTabela();
				if (c != null) {
					if (!tfCodigo.getText().equals("") && !tfDesc.getText().equals("")
							&& !tfPreco.getText().equals("")) {
						c.setId(Integer.parseInt(tfCodigo.getText()));
						c.setDescricao(tfDesc.getText());
						c.setPreco(new BigDecimal(tfPreco.getText()));
						listaProduto.set(tablePro.getSelectedRow(), c);

						SetModel(1);

						tfCodigo.setText("");
						tfDesc.setText("");
						tfPreco.setText("");
					}
				}
			}
		});
		GridBagConstraints gbc_btnNewButton_2 = new GridBagConstraints();
		gbc_btnNewButton_2.fill = GridBagConstraints.HORIZONTAL;
		gbc_btnNewButton_2.insets = new Insets(0, 0, 5, 5);
		gbc_btnNewButton_2.gridx = 6;
		gbc_btnNewButton_2.gridy = 3;
		telaProduto.add(btnNewButton_2, gbc_btnNewButton_2);

		JButton btnNewButton_6 = new JButton("Serializar");
		btnNewButton_6.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				SalvarProdutoSerializacao(new File("ListaProdutos.ser"));
			}
		});
		GridBagConstraints gbc_btnNewButton_6 = new GridBagConstraints();
		gbc_btnNewButton_6.fill = GridBagConstraints.HORIZONTAL;
		gbc_btnNewButton_6.insets = new Insets(0, 0, 5, 0);
		gbc_btnNewButton_6.gridx = 7;
		gbc_btnNewButton_6.gridy = 3;
		telaProduto.add(btnNewButton_6, gbc_btnNewButton_6);

		JButton btnNewButton_5 = new JButton("Excluir");
		btnNewButton_5.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				removerSelecionado(1);
				tfCodigo.setText("");
				tfDesc.setText("");
				tfPreco.setText("");
				SetModel(1);
			}
		});
		GridBagConstraints gbc_btnNewButton_5 = new GridBagConstraints();
		gbc_btnNewButton_5.fill = GridBagConstraints.HORIZONTAL;
		gbc_btnNewButton_5.insets = new Insets(0, 0, 5, 5);
		gbc_btnNewButton_5.gridx = 6;
		gbc_btnNewButton_5.gridy = 4;
		telaProduto.add(btnNewButton_5, gbc_btnNewButton_5);

		JButton btnNewButton_7 = new JButton("Desseralizar");
		btnNewButton_7.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				LerProdutoSerializacao(new File("ListaProdutos.ser"));
				SetModel(1);
			}
		});
		GridBagConstraints gbc_btnNewButton_7 = new GridBagConstraints();
		gbc_btnNewButton_7.fill = GridBagConstraints.HORIZONTAL;
		gbc_btnNewButton_7.insets = new Insets(0, 0, 5, 0);
		gbc_btnNewButton_7.gridx = 7;
		gbc_btnNewButton_7.gridy = 4;
		telaProduto.add(btnNewButton_7, gbc_btnNewButton_7);

		JButton btnRelatorio = new JButton("Relatorio");
		btnRelatorio.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				JasperPrint relat;

				try {
					relat = gerar(1);
					JasperViewer.viewReport(relat, false);
				} catch (Exception e) {
					JOptionPane.showMessageDialog(null, "Erro: " + e.getMessage());
				}
			}
		});
		GridBagConstraints gbc_btnRelatorio = new GridBagConstraints();
		gbc_btnRelatorio.anchor = GridBagConstraints.NORTH;
		gbc_btnRelatorio.fill = GridBagConstraints.HORIZONTAL;
		gbc_btnRelatorio.gridx = 7;
		gbc_btnRelatorio.gridy = 5;
		telaProduto.add(btnRelatorio, gbc_btnRelatorio);

		mostraUltima();

		configuraTabela(1);
	}

	public JasperPrint gerar(int modelo) {
		JasperPrint rel = null;
		String arquivoJasper = "";
		try {
			HashMap map = new HashMap();
			if (modelo == 1) {
				arquivoJasper = "C:\\Users\\Eduardo\\git\\Trabalho154114\\src\\main\\java\\br\\univel\\RelProdutos.jasper";
			} else if (modelo == 2) {
				arquivoJasper = "C:\\Users\\Eduardo\\git\\Trabalho154114\\src\\main\\java\\br\\univel\\RelClientes.jasper";
			}
			rel = JasperFillManager.fillReport(arquivoJasper, map, getImp().getCon());
		} catch (JRException e) {
			JOptionPane.showMessageDialog(null, e.getMessage());
		}
		return rel;
	}

	protected void adicionaClientes() {

		for (int i = 0; i < tabbedPane.getTabCount(); i++) {
			if (tabbedPane.getTitleAt(i).equals(ABA_DOIS)) {
				tabbedPane.setSelectedIndex(i);
				return;
			}
		}

		JPanel telaCliente = new JPanel();

		tableProVda = new JTable() {

			@Override
			public String getToolTipText(MouseEvent e) {

				String tip = null;

				Point p = e.getPoint();

				int rowIndex = rowAtPoint(p);
				int colIndex = columnAtPoint(p);

				if (rowIndex == -1 || colIndex == -1) {
					return null;
				}

				try {
					tip = getValueAt(rowIndex, colIndex).toString();
				} catch (RuntimeException e1) {

				}

				return tip;

			}

		};


		tabbedPane.addTab("Clientes", telaCliente);
		GridBagLayout gbl_telaCliente = new GridBagLayout();
		gbl_telaCliente.rowHeights = new int[] { 0, 0, 0, 0, 0, 0 };
		gbl_telaCliente.columnWidths = new int[] { 30, 30, 30, 30, 10, 30, 30, 30, 0, 30 };
		gbl_telaCliente.columnWeights = new double[] { 1.0, 1.0, 0.0, 1.0, 1.0, 1.0, 0.0, 0.0, 0.0 };
		gbl_telaCliente.rowWeights = new double[] { 0.0, 0.0, 0.0, 0.0, 0.0, 1.0 };
		telaCliente.setLayout(gbl_telaCliente);

		JLabel lblNewLabel_1 = new JLabel("ID: ");
		GridBagConstraints gbc_lblNewLabel_1 = new GridBagConstraints();
		gbc_lblNewLabel_1.anchor = GridBagConstraints.EAST;
		gbc_lblNewLabel_1.insets = new Insets(0, 0, 5, 5);
		gbc_lblNewLabel_1.gridx = 0;
		gbc_lblNewLabel_1.gridy = 0;
		telaCliente.add(lblNewLabel_1, gbc_lblNewLabel_1);

		tfId = new JTextField();
		GridBagConstraints gbc_tfId = new GridBagConstraints();
		gbc_tfId.insets = new Insets(0, 0, 5, 5);
		gbc_tfId.fill = GridBagConstraints.HORIZONTAL;
		gbc_tfId.gridx = 1;
		gbc_tfId.gridy = 0;
		telaCliente.add(tfId, gbc_tfId);
		tfId.setColumns(10);

		JLabel lblNome = new JLabel("Nome:");
		GridBagConstraints gbc_lblNome = new GridBagConstraints();
		gbc_lblNome.anchor = GridBagConstraints.EAST;
		gbc_lblNome.insets = new Insets(0, 0, 5, 5);
		gbc_lblNome.gridx = 2;
		gbc_lblNome.gridy = 0;
		telaCliente.add(lblNome, gbc_lblNome);

		tfNome = new JTextField();
		GridBagConstraints gbc_tfNome = new GridBagConstraints();
		gbc_tfNome.gridwidth = 4;
		gbc_tfNome.insets = new Insets(0, 0, 5, 5);
		gbc_tfNome.fill = GridBagConstraints.HORIZONTAL;
		gbc_tfNome.gridx = 3;
		gbc_tfNome.gridy = 0;
		telaCliente.add(tfNome, gbc_tfNome);
		tfNome.setColumns(10);

		JButton ExpClienteXml = new JButton("Exportar XML");
		ExpClienteXml.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				SalvarClienteXML(new File("ListaClientes.xml"));
			}
		});
		GridBagConstraints gbc_ExpClienteXml = new GridBagConstraints();
		gbc_ExpClienteXml.fill = GridBagConstraints.HORIZONTAL;
		gbc_ExpClienteXml.anchor = GridBagConstraints.NORTH;
		gbc_ExpClienteXml.insets = new Insets(0, 0, 5, 0);
		gbc_ExpClienteXml.gridx = 9;
		gbc_ExpClienteXml.gridy = 0;
		telaCliente.add(ExpClienteXml, gbc_ExpClienteXml);

		JLabel lblEndereco = new JLabel("Endereco:");
		GridBagConstraints gbc_lblEndereco = new GridBagConstraints();
		gbc_lblEndereco.anchor = GridBagConstraints.EAST;
		gbc_lblEndereco.insets = new Insets(0, 0, 5, 5);
		gbc_lblEndereco.gridx = 0;
		gbc_lblEndereco.gridy = 1;
		telaCliente.add(lblEndereco, gbc_lblEndereco);

		tfEnd = new JTextField();
		GridBagConstraints gbc_tfEnd = new GridBagConstraints();
		gbc_tfEnd.gridwidth = 3;
		gbc_tfEnd.insets = new Insets(0, 0, 5, 5);
		gbc_tfEnd.fill = GridBagConstraints.HORIZONTAL;
		gbc_tfEnd.gridx = 1;
		gbc_tfEnd.gridy = 1;
		telaCliente.add(tfEnd, gbc_tfEnd);
		tfEnd.setColumns(10);

		JButton AddCliente = new JButton("Adcionar");
		AddCliente.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				if (tfNome.getText().equals("")) {
					JOptionPane.showMessageDialog(null, "Nome é obrigatorio");
					return;
				}
				if (tfEnd.getText().equals("")) {
					JOptionPane.showMessageDialog(null, "Endereco é obrigatorio");
					return;
				}
				if (tfNumero.getText().equals("")) {
					JOptionPane.showMessageDialog(null, "Numero é obrigatorio");
					return;
				}

				if (tfBairro.getText().equals("")) {
					JOptionPane.showMessageDialog(null, "Bairro é obrigatorio");
					return;
				}
				if (tfCidade.getText().equals("")) {
					JOptionPane.showMessageDialog(null, "Cidade é obrigatorio");
					return;
				}
				if (tfCep.getText().equals("")) {
					JOptionPane.showMessageDialog(null, "Cep é obrigatorio");
					return;
				}
				if (tfEstado.getText().equals("")) {
					JOptionPane.showMessageDialog(null, "Estado é obrigatorio");
					return;
				}
				if (tfTel.getText().equals("") && tfCel.getText().equals("")) {
					JOptionPane.showMessageDialog(null, "Informa Telefone e/ou Celular");
					return;
				}
				if (tfId.getText().equals("")) {
					JOptionPane.showMessageDialog(null, "ID é obrigatorio");
					return;
				}

				Cliente c = new Cliente();
				c.setId(Integer.parseInt(tfId.getText()));
				c.setNome(tfNome.getText());
				c.setEndereco(tfEnd.getText());
				c.setNumero(Integer.parseInt(tfNumero.getText()));
				c.setComplemento(tfComp.getText());
				c.setBairro(tfBairro.getText());
				c.setCidade(tfCidade.getText());
				c.setEstado(tfEstado.getText());
				c.setCep(Integer.parseInt(tfCep.getText()));
				c.setTelefone(tfTel.getText());
				c.setCelular(tfCel.getText());

				if (buscar(c) > 0) {
					JOptionPane.showMessageDialog(null, "CLIENTE JA CADASTRADO!");
					return;
				}

				if (listaCliente == null)
					listaCliente = new ArrayList<>();

				salvar(c);

				listaCliente.add(c);

				SetModel(2);

				tfId.setText("");
				tfNome.setText("");
				tfEnd.setText("");
				tfNumero.setText("");
				tfComp.setText("");
				tfBairro.setText("");
				tfCidade.setText("");
				tfEstado.setText("");
				tfCep.setText("");
				tfTel.setText("");
				tfCel.setText("");

			}
		});

		JLabel lblNumero = new JLabel("Numero:");
		GridBagConstraints gbc_lblNumero = new GridBagConstraints();
		gbc_lblNumero.anchor = GridBagConstraints.EAST;
		gbc_lblNumero.insets = new Insets(0, 0, 5, 5);
		gbc_lblNumero.gridx = 4;
		gbc_lblNumero.gridy = 1;
		telaCliente.add(lblNumero, gbc_lblNumero);

		tfNumero = new JTextField();
		GridBagConstraints gbc_tfNumero = new GridBagConstraints();
		gbc_tfNumero.gridwidth = 2;
		gbc_tfNumero.insets = new Insets(0, 0, 5, 5);
		gbc_tfNumero.fill = GridBagConstraints.HORIZONTAL;
		gbc_tfNumero.gridx = 5;
		gbc_tfNumero.gridy = 1;
		telaCliente.add(tfNumero, gbc_tfNumero);
		tfNumero.setColumns(10);
		GridBagConstraints gbc_AddCliente = new GridBagConstraints();
		gbc_AddCliente.fill = GridBagConstraints.HORIZONTAL;
		gbc_AddCliente.insets = new Insets(0, 0, 5, 5);
		gbc_AddCliente.gridx = 8;
		gbc_AddCliente.gridy = 1;
		telaCliente.add(AddCliente, gbc_AddCliente);

		JButton ImpClienteXml = new JButton("Importar XML");
		ImpClienteXml.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				LerClienteXML(new File("ListaClientes.xml"));
				SetModel(2);
			}
		});
		GridBagConstraints gbc_ImpClienteXml = new GridBagConstraints();
		gbc_ImpClienteXml.fill = GridBagConstraints.HORIZONTAL;
		gbc_ImpClienteXml.anchor = GridBagConstraints.NORTH;
		gbc_ImpClienteXml.insets = new Insets(0, 0, 5, 0);
		gbc_ImpClienteXml.gridx = 9;
		gbc_ImpClienteXml.gridy = 1;
		telaCliente.add(ImpClienteXml, gbc_ImpClienteXml);

		JLabel lblNewLabel = new JLabel("Comp: ");
		GridBagConstraints gbc_lblNewLabel = new GridBagConstraints();
		gbc_lblNewLabel.anchor = GridBagConstraints.EAST;
		gbc_lblNewLabel.insets = new Insets(0, 0, 5, 5);
		gbc_lblNewLabel.gridx = 0;
		gbc_lblNewLabel.gridy = 2;
		telaCliente.add(lblNewLabel, gbc_lblNewLabel);

		tfComp = new JTextField();
		GridBagConstraints gbc_tfComp = new GridBagConstraints();
		gbc_tfComp.insets = new Insets(0, 0, 5, 5);
		gbc_tfComp.fill = GridBagConstraints.HORIZONTAL;
		gbc_tfComp.gridx = 1;
		gbc_tfComp.gridy = 2;
		telaCliente.add(tfComp, gbc_tfComp);
		tfComp.setColumns(10);

		JLabel lblCep = new JLabel("CEP:");
		GridBagConstraints gbc_lblCep = new GridBagConstraints();
		gbc_lblCep.anchor = GridBagConstraints.EAST;
		gbc_lblCep.insets = new Insets(0, 0, 5, 5);
		gbc_lblCep.gridx = 2;
		gbc_lblCep.gridy = 2;
		telaCliente.add(lblCep, gbc_lblCep);

		JButton SerCliente = new JButton("Serializar");
		SerCliente.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				SalvarClienteSerializacao(new File("ListaClientes.ser"));
			}
		});

		JButton AltCliente = new JButton("Alterar");
		AltCliente.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Cliente c = getClienteSelecionadoNaTabela();
				if (c != null) {
					if (tfNome.getText().equals("")) {
						JOptionPane.showMessageDialog(null, "Nome é obrigatorio");
						return;
					}
					if (tfEnd.getText().equals("")) {
						JOptionPane.showMessageDialog(null, "Endereco é obrigatorio");
						return;
					}
					if (tfNumero.getText().equals("")) {
						JOptionPane.showMessageDialog(null, "Numero é obrigatorio");
						return;
					}
					if (tfBairro.getText().equals("")) {
						JOptionPane.showMessageDialog(null, "Bairro é obrigatorio");
						return;
					}
					if (tfCidade.getText().equals("")) {
						JOptionPane.showMessageDialog(null, "Cidade é obrigatorio");
						return;
					}
					if (tfCep.getText().equals("")) {
						JOptionPane.showMessageDialog(null, "Cep é obrigatorio");
						return;
					}
					if (tfEstado.getText().equals("")) {
						JOptionPane.showMessageDialog(null, "Estado é obrigatorio");
						return;
					}
					if (tfTel.getText().equals("") && tfCel.getText().equals("")) {
						JOptionPane.showMessageDialog(null, "Informa Telefone e/ou Celular");
						return;
					}
					if (tfId.getText().equals("")) {
						JOptionPane.showMessageDialog(null, "ID é obrigatorio");
						return;
					}

					c.setId(Integer.parseInt(tfId.getText()));
					c.setNome(tfNome.getText());
					c.setEndereco(tfEnd.getText());
					c.setNumero(Integer.parseInt(tfNumero.getText()));
					c.setComplemento(tfComp.getText());
					c.setBairro(tfBairro.getText());
					c.setCidade(tfCidade.getText());
					c.setEstado(tfEstado.getText());
					c.setCep(Integer.parseInt(tfCep.getText()));
					c.setTelefone(tfTel.getText());
					c.setCelular(tfCel.getText());

					listaCliente.set(tableCli.getSelectedRow(), c);

					SetModel(2);

					tfId.setText("");
					tfNome.setText("");
					tfEnd.setText("");
					tfNumero.setText("");
					tfComp.setText("");
					tfBairro.setText("");
					tfCidade.setText("");
					tfEstado.setText("");
					tfCep.setText("");
					tfTel.setText("");
					tfCel.setText("");
				}
			}
		});

		tfCep = new JTextField();
		GridBagConstraints gbc_tfCep = new GridBagConstraints();
		gbc_tfCep.insets = new Insets(0, 0, 5, 5);
		gbc_tfCep.fill = GridBagConstraints.HORIZONTAL;
		gbc_tfCep.gridx = 3;
		gbc_tfCep.gridy = 2;
		telaCliente.add(tfCep, gbc_tfCep);
		tfCep.setColumns(10);

		JLabel lblBairro = new JLabel("Bairro: ");
		GridBagConstraints gbc_lblBairro = new GridBagConstraints();
		gbc_lblBairro.anchor = GridBagConstraints.EAST;
		gbc_lblBairro.insets = new Insets(0, 0, 5, 5);
		gbc_lblBairro.gridx = 4;
		gbc_lblBairro.gridy = 2;
		telaCliente.add(lblBairro, gbc_lblBairro);

		tfBairro = new JTextField();
		GridBagConstraints gbc_tfBairro = new GridBagConstraints();
		gbc_tfBairro.gridwidth = 2;
		gbc_tfBairro.insets = new Insets(0, 0, 5, 5);
		gbc_tfBairro.fill = GridBagConstraints.HORIZONTAL;
		gbc_tfBairro.gridx = 5;
		gbc_tfBairro.gridy = 2;
		telaCliente.add(tfBairro, gbc_tfBairro);
		tfBairro.setColumns(10);
		GridBagConstraints gbc_AltCliente = new GridBagConstraints();
		gbc_AltCliente.fill = GridBagConstraints.HORIZONTAL;
		gbc_AltCliente.anchor = GridBagConstraints.ABOVE_BASELINE;
		gbc_AltCliente.insets = new Insets(0, 0, 5, 5);
		gbc_AltCliente.gridx = 8;
		gbc_AltCliente.gridy = 2;
		telaCliente.add(AltCliente, gbc_AltCliente);
		GridBagConstraints gbc_SerCliente = new GridBagConstraints();
		gbc_SerCliente.fill = GridBagConstraints.HORIZONTAL;
		gbc_SerCliente.anchor = GridBagConstraints.NORTH;
		gbc_SerCliente.insets = new Insets(0, 0, 5, 0);
		gbc_SerCliente.gridx = 9;
		gbc_SerCliente.gridy = 2;
		telaCliente.add(SerCliente, gbc_SerCliente);

		JLabel lblCidade = new JLabel("Cidade:");
		GridBagConstraints gbc_lblCidade = new GridBagConstraints();
		gbc_lblCidade.anchor = GridBagConstraints.EAST;
		gbc_lblCidade.insets = new Insets(0, 0, 5, 5);
		gbc_lblCidade.gridx = 0;
		gbc_lblCidade.gridy = 3;
		telaCliente.add(lblCidade, gbc_lblCidade);

		JButton DesCliente = new JButton("Desserializar");
		DesCliente.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) {
				LerClienteSerializacao(new File("ListaCliente.ser"));
				SetModel(2);
			}
		});

		JButton btnExcluir = new JButton("Excluir");
		btnExcluir.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				removerSelecionado(2);
				tfId.setText("");
				tfNome.setText("");
				tfEnd.setText("");
				tfNumero.setText("");
				tfComp.setText("");
				tfBairro.setText("");
				tfCidade.setText("");
				tfEstado.setText("");
				tfCep.setText("");
				tfTel.setText("");
				tfCel.setText("");
				SetModel(2);
			}
		});

		tfCidade = new JTextField();
		GridBagConstraints gbc_tfCidade = new GridBagConstraints();
		gbc_tfCidade.anchor = GridBagConstraints.NORTH;
		gbc_tfCidade.gridwidth = 4;
		gbc_tfCidade.insets = new Insets(0, 0, 5, 5);
		gbc_tfCidade.fill = GridBagConstraints.HORIZONTAL;
		gbc_tfCidade.gridx = 1;
		gbc_tfCidade.gridy = 3;
		telaCliente.add(tfCidade, gbc_tfCidade);
		tfCidade.setColumns(10);

		JLabel lblEstado = new JLabel("Estado:");
		GridBagConstraints gbc_lblEstado = new GridBagConstraints();
		gbc_lblEstado.anchor = GridBagConstraints.EAST;
		gbc_lblEstado.insets = new Insets(0, 0, 5, 5);
		gbc_lblEstado.gridx = 5;
		gbc_lblEstado.gridy = 3;
		telaCliente.add(lblEstado, gbc_lblEstado);

		tfEstado = new JTextField();
		GridBagConstraints gbc_tfEstado = new GridBagConstraints();
		gbc_tfEstado.insets = new Insets(0, 0, 5, 5);
		gbc_tfEstado.fill = GridBagConstraints.HORIZONTAL;
		gbc_tfEstado.gridx = 6;
		gbc_tfEstado.gridy = 3;
		telaCliente.add(tfEstado, gbc_tfEstado);

		tfEstado.setColumns(10);
		GridBagConstraints gbc_btnExcluir = new GridBagConstraints();
		gbc_btnExcluir.fill = GridBagConstraints.HORIZONTAL;
		gbc_btnExcluir.insets = new Insets(0, 0, 5, 5);
		gbc_btnExcluir.gridx = 8;
		gbc_btnExcluir.gridy = 3;
		telaCliente.add(btnExcluir, gbc_btnExcluir);

		GridBagConstraints gbc_DesCliente = new GridBagConstraints();
		gbc_DesCliente.fill = GridBagConstraints.HORIZONTAL;
		gbc_DesCliente.anchor = GridBagConstraints.NORTH;
		gbc_DesCliente.insets = new Insets(0, 0, 5, 0);
		gbc_DesCliente.gridx = 9;
		gbc_DesCliente.gridy = 3;
		telaCliente.add(DesCliente, gbc_DesCliente);

		JLabel lblTel = new JLabel("TEL:");
		GridBagConstraints gbc_lblTel = new GridBagConstraints();
		gbc_lblTel.anchor = GridBagConstraints.EAST;
		gbc_lblTel.insets = new Insets(0, 0, 5, 5);
		gbc_lblTel.gridx = 0;
		gbc_lblTel.gridy = 4;
		telaCliente.add(lblTel, gbc_lblTel);

		tfTel = new JTextField();
		GridBagConstraints gbc_tfTel = new GridBagConstraints();
		gbc_tfTel.gridwidth = 3;
		gbc_tfTel.insets = new Insets(0, 0, 5, 5);
		gbc_tfTel.fill = GridBagConstraints.HORIZONTAL;
		gbc_tfTel.gridx = 1;
		gbc_tfTel.gridy = 4;
		telaCliente.add(tfTel, gbc_tfTel);
		tfTel.setColumns(10);

		JLabel lblCel = new JLabel("CEL:");
		GridBagConstraints gbc_lblCel = new GridBagConstraints();
		gbc_lblCel.anchor = GridBagConstraints.EAST;
		gbc_lblCel.insets = new Insets(0, 0, 5, 5);
		gbc_lblCel.gridx = 4;
		gbc_lblCel.gridy = 4;
		telaCliente.add(lblCel, gbc_lblCel);

		tfCel = new JTextField();
		GridBagConstraints gbc_tfCel = new GridBagConstraints();
		gbc_tfCel.gridwidth = 2;
		gbc_tfCel.insets = new Insets(0, 0, 5, 5);
		gbc_tfCel.fill = GridBagConstraints.HORIZONTAL;
		gbc_tfCel.gridx = 5;
		gbc_tfCel.gridy = 4;
		telaCliente.add(tfCel, gbc_tfCel);
		tfCel.setColumns(10);

		JButton RelCliente = new JButton("Relatorio");
		RelCliente.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				JasperPrint relat;

				try {
					relat = gerar(2);
					JasperViewer.viewReport(relat, false);
				} catch (Exception x) {
					JOptionPane.showMessageDialog(null, "Erro: " + x.getMessage());
				}
			}
		});
		GridBagConstraints gbc_RelCliente = new GridBagConstraints();
		gbc_RelCliente.insets = new Insets(0, 0, 5, 0);
		gbc_RelCliente.fill = GridBagConstraints.HORIZONTAL;
		gbc_RelCliente.anchor = GridBagConstraints.NORTH;
		gbc_RelCliente.gridx = 9;
		gbc_RelCliente.gridy = 4;
		telaCliente.add(RelCliente, gbc_RelCliente);

		JScrollPane PainelClientes = new JScrollPane();
		GridBagConstraints gbc_PainelClientes = new GridBagConstraints();
		gbc_PainelClientes.gridwidth = 10;
		gbc_PainelClientes.fill = GridBagConstraints.BOTH;
		gbc_PainelClientes.gridx = 0;
		gbc_PainelClientes.gridy = 5;
		telaCliente.add(PainelClientes, gbc_PainelClientes);

		tableCli = new JTable() {

			@Override
			public String getToolTipText(MouseEvent e) {

				String tip = null;

				Point p = e.getPoint();

				int rowIndex = rowAtPoint(p);
				int colIndex = columnAtPoint(p);

				if (rowIndex == -1 || colIndex == -1) {
					return null;
				}

				try {
					tip = getValueAt(rowIndex, colIndex).toString();
				} catch (RuntimeException e1) {

				}

				return tip;

			}

		};

		PainelClientes.setViewportView(tableCli);

		if (listaCliente == null)
			listaCliente = new ArrayList<>();

		if (listaCliente.size() == 0) {
			listarTodos(new Cliente());
		}

		if (listaCliente.size() == 0) {
			importarClienteTXT();
		}

		SetModel(2);

		configuraTabela(2);
	}

	protected void adicionaVendas() {

		for (int i = 0; i < tabbedPane.getTabCount(); i++) {
			if (tabbedPane.getTitleAt(i).equals(ABA_TRES)) {
				tabbedPane.setSelectedIndex(i);
				return;
			}
		}

		JPanel telaVenda = new JPanel();
		telaVenda.setAlignmentY(1.0f);
		telaVenda.setAlignmentX(1.0f);

		tabbedPane.addTab("Vendas", telaVenda);
		GridBagLayout gbl_telaVenda = new GridBagLayout();
		gbl_telaVenda.columnWidths = new int[] { 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 };
		gbl_telaVenda.rowHeights = new int[] { 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 };
		gbl_telaVenda.columnWeights = new double[] { 1.0, 1.0, 1.0, 1.0, 0.0, 1.0, 0.0, 1.0, 1.0, 1.0, 1.0, 1.0, 0.0,
				Double.MIN_VALUE };
		gbl_telaVenda.rowWeights = new double[] { 1.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 1.0, 1.0, Double.MIN_VALUE };
		telaVenda.setLayout(gbl_telaVenda);

		JLabel lblCliente = new JLabel("Cliente:");
		GridBagConstraints gbc_lblCliente = new GridBagConstraints();
		gbc_lblCliente.insets = new Insets(0, 0, 5, 5);
		gbc_lblCliente.anchor = GridBagConstraints.EAST;
		gbc_lblCliente.gridx = 1;
		gbc_lblCliente.gridy = 1;
		telaVenda.add(lblCliente, gbc_lblCliente);

		tfVdaCli = new JTextField();
		GridBagConstraints gbc_tfVdaCli = new GridBagConstraints();
		gbc_tfVdaCli.gridwidth = 7;
		gbc_tfVdaCli.insets = new Insets(0, 0, 5, 5);
		gbc_tfVdaCli.fill = GridBagConstraints.HORIZONTAL;
		gbc_tfVdaCli.gridx = 2;
		gbc_tfVdaCli.gridy = 1;
		telaVenda.add(tfVdaCli, gbc_tfVdaCli);
		tfVdaCli.setColumns(10);

		JLabel lblCod = new JLabel("Cod:");
		GridBagConstraints gbc_lblCod = new GridBagConstraints();
		gbc_lblCod.insets = new Insets(0, 0, 5, 5);
		gbc_lblCod.anchor = GridBagConstraints.EAST;
		gbc_lblCod.gridx = 9;
		gbc_lblCod.gridy = 1;
		telaVenda.add(lblCod, gbc_lblCod);

		tfVdaId = new JTextField();
		tfVdaId.setEditable(false);
		GridBagConstraints gbc_tfVdaId = new GridBagConstraints();
		gbc_tfVdaId.fill = GridBagConstraints.HORIZONTAL;
		gbc_tfVdaId.insets = new Insets(0, 0, 5, 5);
		gbc_tfVdaId.gridx = 10;
		gbc_tfVdaId.gridy = 1;
		telaVenda.add(tfVdaId, gbc_tfVdaId);
		tfVdaId.setColumns(10);

		JButton btnAlterar = new JButton("Alterar");
		GridBagConstraints gbc_btnAlterar = new GridBagConstraints();
		gbc_btnAlterar.fill = GridBagConstraints.HORIZONTAL;
		gbc_btnAlterar.insets = new Insets(0, 0, 5, 0);
		gbc_btnAlterar.gridx = 12;
		gbc_btnAlterar.gridy = 1;
		telaVenda.add(btnAlterar, gbc_btnAlterar);

		JLabel lblProduto = new JLabel("Produto:");
		GridBagConstraints gbc_lblProduto = new GridBagConstraints();
		gbc_lblProduto.anchor = GridBagConstraints.EAST;
		gbc_lblProduto.insets = new Insets(0, 0, 5, 5);
		gbc_lblProduto.gridx = 1;
		gbc_lblProduto.gridy = 2;
		telaVenda.add(lblProduto, gbc_lblProduto);

		tfVdaPro = new JTextField();
		GridBagConstraints gbc_tfVdaPro = new GridBagConstraints();
		gbc_tfVdaPro.gridwidth = 5;
		gbc_tfVdaPro.insets = new Insets(0, 0, 5, 5);
		gbc_tfVdaPro.fill = GridBagConstraints.HORIZONTAL;
		gbc_tfVdaPro.gridx = 2;
		gbc_tfVdaPro.gridy = 2;
		telaVenda.add(tfVdaPro, gbc_tfVdaPro);
		tfVdaPro.setColumns(10);

		JLabel label = new JLabel("Cod:");
		GridBagConstraints gbc_label = new GridBagConstraints();
		gbc_label.anchor = GridBagConstraints.EAST;
		gbc_label.insets = new Insets(0, 0, 5, 5);
		gbc_label.gridx = 7;
		gbc_label.gridy = 2;
		telaVenda.add(label, gbc_label);

		tfVdaIdPro = new JTextField();
		tfVdaIdPro.setEnabled(false);
		GridBagConstraints gbc_tfVdaIdPro = new GridBagConstraints();
		gbc_tfVdaIdPro.insets = new Insets(0, 0, 5, 5);
		gbc_tfVdaIdPro.fill = GridBagConstraints.HORIZONTAL;
		gbc_tfVdaIdPro.gridx = 8;
		gbc_tfVdaIdPro.gridy = 2;
		telaVenda.add(tfVdaIdPro, gbc_tfVdaIdPro);
		tfVdaIdPro.setColumns(10);

		JLabel lblQtd = new JLabel("Qtd:");
		GridBagConstraints gbc_lblQtd = new GridBagConstraints();
		gbc_lblQtd.anchor = GridBagConstraints.EAST;
		gbc_lblQtd.insets = new Insets(0, 0, 5, 5);
		gbc_lblQtd.gridx = 9;
		gbc_lblQtd.gridy = 2;
		telaVenda.add(lblQtd, gbc_lblQtd);

		tfVdaQtd = new JTextField();
		GridBagConstraints gbc_tfVdaQtd = new GridBagConstraints();
		gbc_tfVdaQtd.fill = GridBagConstraints.HORIZONTAL;
		gbc_tfVdaQtd.insets = new Insets(0, 0, 5, 5);
		gbc_tfVdaQtd.gridx = 10;
		gbc_tfVdaQtd.gridy = 2;
		telaVenda.add(tfVdaQtd, gbc_tfVdaQtd);
		tfVdaQtd.setColumns(10);

		JButton btnAdcionar = new JButton("Adcionar");
		btnAdcionar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				ProdVenda pv = new ProdVenda();
				pv.setDescPro(tfVdaPro.getText());
				pv.setCodPro(Integer.parseInt(tfVdaIdPro.getText()));
				pv.setQtd(Integer.parseInt(tfVdaQtd.getText()));
				pv.setTotal(Vunit.multiply(new BigDecimal(Integer.toString(pv.getQtd()))));

				tfVdaPro.setText("");
				tfVdaIdPro.setText("");
				tfVdaQtd.setText("");


				if (listaProdVenda == null)
					listaProdVenda = new ArrayList<>();

				listaProdVenda.add(pv);

				Vtot = Vtot.add(pv.getTotal());
				tfTotal.setText(Vtot.toString());

				ProdVendaModel model = new ProdVendaModel(listaProdVenda);
				tableProVda.setModel(model);

			}
		});
		GridBagConstraints gbc_btnAdcionar = new GridBagConstraints();
		gbc_btnAdcionar.fill = GridBagConstraints.HORIZONTAL;
		gbc_btnAdcionar.insets = new Insets(0, 0, 5, 5);
		gbc_btnAdcionar.gridx = 11;
		gbc_btnAdcionar.gridy = 2;
		telaVenda.add(btnAdcionar, gbc_btnAdcionar);

		JButton btnAlterar_1 = new JButton("Alterar");
		GridBagConstraints gbc_btnAlterar_1 = new GridBagConstraints();
		gbc_btnAlterar_1.fill = GridBagConstraints.HORIZONTAL;
		gbc_btnAlterar_1.insets = new Insets(0, 0, 5, 0);
		gbc_btnAlterar_1.gridx = 12;
		gbc_btnAlterar_1.gridy = 2;
		telaVenda.add(btnAlterar_1, gbc_btnAlterar_1);

		JButton btnNewButton_1 = new JButton("ExcluirProduto");
		GridBagConstraints gbc_btnNewButton_1 = new GridBagConstraints();
		gbc_btnNewButton_1.gridwidth = 2;
		gbc_btnNewButton_1.fill = GridBagConstraints.HORIZONTAL;
		gbc_btnNewButton_1.insets = new Insets(0, 0, 5, 0);
		gbc_btnNewButton_1.gridx = 11;
		gbc_btnNewButton_1.gridy = 3;
		telaVenda.add(btnNewButton_1, gbc_btnNewButton_1);

		JScrollPane painelVenda = new JScrollPane();
		GridBagConstraints gbc_painelVenda = new GridBagConstraints();
		gbc_painelVenda.gridheight = 5;
		gbc_painelVenda.gridwidth = 10;
		gbc_painelVenda.insets = new Insets(0, 0, 5, 5);
		gbc_painelVenda.fill = GridBagConstraints.BOTH;
		gbc_painelVenda.gridx = 1;
		gbc_painelVenda.gridy = 3;
		telaVenda.add(painelVenda, gbc_painelVenda);

		JButton btnExportarXml = new JButton("Exportar XML");
		GridBagConstraints gbc_btnExportarXml = new GridBagConstraints();
		gbc_btnExportarXml.fill = GridBagConstraints.HORIZONTAL;
		gbc_btnExportarXml.insets = new Insets(0, 0, 5, 5);
		gbc_btnExportarXml.gridx = 0;
		gbc_btnExportarXml.gridy = 4;
		telaVenda.add(btnExportarXml, gbc_btnExportarXml);

		JButton btnFinalizarCompra = new JButton("Finalizar Compra");
		btnFinalizarCompra.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				Vpago = new BigDecimal(tfVPago.getText());

				if (Vpago.doubleValue() > Vtot.doubleValue())
					Vtroco = Vtroco.add(Vpago.subtract(Vtot));

				tfTroco.setText(Vtroco.toString());

				listaProdVenda.clear();

				ProdVendaModel model = new ProdVendaModel(listaProdVenda);
				tableProVda.setModel(model);

			}
		});
		GridBagConstraints gbc_btnFinalizarCompra = new GridBagConstraints();
		gbc_btnFinalizarCompra.fill = GridBagConstraints.HORIZONTAL;
		gbc_btnFinalizarCompra.gridwidth = 2;
		gbc_btnFinalizarCompra.insets = new Insets(0, 0, 5, 0);
		gbc_btnFinalizarCompra.gridx = 11;
		gbc_btnFinalizarCompra.gridy = 4;
		telaVenda.add(btnFinalizarCompra, gbc_btnFinalizarCompra);

		JButton btnImportarXml = new JButton("Importar XML");
		GridBagConstraints gbc_btnImportarXml = new GridBagConstraints();
		gbc_btnImportarXml.fill = GridBagConstraints.HORIZONTAL;
		gbc_btnImportarXml.insets = new Insets(0, 0, 5, 5);
		gbc_btnImportarXml.gridx = 0;
		gbc_btnImportarXml.gridy = 5;
		telaVenda.add(btnImportarXml, gbc_btnImportarXml);

		JLabel lblVpago = new JLabel("V.PAGO:");
		lblVpago.setBackground(Color.GRAY);
		GridBagConstraints gbc_lblVpago = new GridBagConstraints();
		gbc_lblVpago.anchor = GridBagConstraints.EAST;
		gbc_lblVpago.insets = new Insets(0, 0, 5, 5);
		gbc_lblVpago.gridx = 11;
		gbc_lblVpago.gridy = 5;
		telaVenda.add(lblVpago, gbc_lblVpago);

		tfVPago = new JTextField();
		GridBagConstraints gbc_tfVPago = new GridBagConstraints();
		gbc_tfVPago.insets = new Insets(0, 0, 5, 0);
		gbc_tfVPago.fill = GridBagConstraints.HORIZONTAL;
		gbc_tfVPago.gridx = 12;
		gbc_tfVPago.gridy = 5;
		telaVenda.add(tfVPago, gbc_tfVPago);
		tfVPago.setColumns(10);

		JButton btnSerializar = new JButton("Serializar");
		GridBagConstraints gbc_btnSerializar = new GridBagConstraints();
		gbc_btnSerializar.fill = GridBagConstraints.HORIZONTAL;
		gbc_btnSerializar.insets = new Insets(0, 0, 5, 5);
		gbc_btnSerializar.gridx = 0;
		gbc_btnSerializar.gridy = 6;
		telaVenda.add(btnSerializar, gbc_btnSerializar);

		JLabel lblTroco = new JLabel("TROCO:");
		lblTroco.setBackground(Color.GRAY);
		GridBagConstraints gbc_lblTroco = new GridBagConstraints();
		gbc_lblTroco.anchor = GridBagConstraints.EAST;
		gbc_lblTroco.insets = new Insets(0, 0, 5, 5);
		gbc_lblTroco.gridx = 11;
		gbc_lblTroco.gridy = 6;
		telaVenda.add(lblTroco, gbc_lblTroco);

		tfTroco = new JTextField();
		tfTroco.setEnabled(false);
		GridBagConstraints gbc_tfTroco = new GridBagConstraints();
		gbc_tfTroco.insets = new Insets(0, 0, 5, 0);
		gbc_tfTroco.fill = GridBagConstraints.HORIZONTAL;
		gbc_tfTroco.gridx = 12;
		gbc_tfTroco.gridy = 6;
		telaVenda.add(tfTroco, gbc_tfTroco);
		tfTroco.setColumns(10);

		JButton btnDesserializar = new JButton("Desserializar");
		GridBagConstraints gbc_btnDesserializar = new GridBagConstraints();
		gbc_btnDesserializar.anchor = GridBagConstraints.NORTH;
		gbc_btnDesserializar.fill = GridBagConstraints.HORIZONTAL;
		gbc_btnDesserializar.insets = new Insets(0, 0, 5, 5);
		gbc_btnDesserializar.gridx = 0;
		gbc_btnDesserializar.gridy = 7;
		telaVenda.add(btnDesserializar, gbc_btnDesserializar);

		JLabel lblTotal = new JLabel("TOTAL:");
		lblTotal.setBackground(Color.GRAY);
		GridBagConstraints gbc_lblTotal = new GridBagConstraints();
		gbc_lblTotal.anchor = GridBagConstraints.NORTHEAST;
		gbc_lblTotal.insets = new Insets(0, 0, 5, 5);
		gbc_lblTotal.gridx = 11;
		gbc_lblTotal.gridy = 7;
		telaVenda.add(lblTotal, gbc_lblTotal);

		tfTotal = new JTextField();
		tfTotal.setEnabled(false);
		GridBagConstraints gbc_tfTotal = new GridBagConstraints();
		gbc_tfTotal.anchor = GridBagConstraints.NORTH;
		gbc_tfTotal.insets = new Insets(0, 0, 5, 0);
		gbc_tfTotal.fill = GridBagConstraints.HORIZONTAL;
		gbc_tfTotal.gridx = 12;
		gbc_tfTotal.gridy = 7;
		telaVenda.add(tfTotal, gbc_tfTotal);
		tfTotal.setColumns(10);
		mostraUltima();

		tableVda = new JTable() {

			@Override
			public String getToolTipText(MouseEvent e) {

				String tip = null;

				Point p = e.getPoint();

				int rowIndex = rowAtPoint(p);
				int colIndex = columnAtPoint(p);

				if (rowIndex == -1 || colIndex == -1) {
					return null;
				}

				try {
					tip = getValueAt(rowIndex, colIndex).toString();
				} catch (RuntimeException e1) {

				}

				return tip;

			}

		};

		painelVenda.setViewportView(tableVda);

		JScrollPane tabelaProVda = new JScrollPane();
		GridBagConstraints gbc_tabelaProVda = new GridBagConstraints();
		gbc_tabelaProVda.gridheight = 2;
		gbc_tabelaProVda.gridwidth = 13;
		gbc_tabelaProVda.fill = GridBagConstraints.BOTH;
		gbc_tabelaProVda.gridx = 0;
		gbc_tabelaProVda.gridy = 8;
		telaVenda.add(tabelaProVda, gbc_tabelaProVda);

		configuraTelaVdaCliente();

		tabelaProVda.setViewportView(tableProVda);

		/*ProdVendaModel model = new ProdVendaModel(listaProdVenda);
		tableProVda.setModel(model);*/
	}

	private void mostraUltima() {
		tabbedPane.setSelectedIndex(tabbedPane.getTabCount() - 1);
	}

	protected void importarProdutosTXT() {

		ReaderArquivo reader = new ReaderArquivo();
		List<String> lista = reader.lerArquivo(new File("listaProdutos.txt"));

		ParserProduto parser = new ParserProduto();
		listaProduto = parser.getProduto(lista);

		for (Produto p : listaProduto) {
			salvar(p);
		}
		JOptionPane.showMessageDialog(null, "BANCO DE DADOS VAZIO! Os produtos foram importados do arquivo TXT");
	}

	protected void importarClienteTXT() {

		ReaderArquivo reader = new ReaderArquivo();
		List<String> lista = reader.lerArquivo(new File("ListaClientes.txt"));

		ParserCliente parser = new ParserCliente();
		listaCliente = parser.getCliente(lista);

		for (Cliente c : listaCliente) {
			salvar(c);
		}
		JOptionPane.showMessageDialog(null, "BANCO DE DADOS VAZIO! Os Clientes foram importados do arquivo TXT");
	}

	public void SalvarProdutoXML(File file) {
		try {
			JAXBContext context = JAXBContext.newInstance(ProdutoWrapper.class);
			Marshaller m = context.createMarshaller();
			m.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);

			ProdutoWrapper wrapper = new ProdutoWrapper();
			wrapper.setProduto(listaProduto);

			m.marshal(wrapper, file);

		} catch (Exception e) {
			e.printStackTrace();
		}
		JOptionPane.showMessageDialog(null, "PRODUTOS EXPORTADOS PARA O ARQUIVO " + file);
	}

	public void SalvarProdutoSerializacao(File file) {
		try {

			FileOutputStream fout = new FileOutputStream(file);

			ObjectOutputStream oos = new ObjectOutputStream(fout);

			oos.writeObject(listaProduto);

			oos.close();

		} catch (Exception ex) {
			ex.printStackTrace();
		}
		JOptionPane.showMessageDialog(null, "PRODUTOS EXPORTADOS PARA O ARQUIVO " + file);
	}

	public void LerProdutoXML(File file) {
		try {
			JAXBContext context = JAXBContext.newInstance(ProdutoWrapper.class);
			Unmarshaller um = context.createUnmarshaller();

			ProdutoWrapper wrapper = (ProdutoWrapper) um.unmarshal(file);

			if (listaProduto != null)
				listaProduto.clear();
			else
				listaProduto = new ArrayList<>();

			listaProduto.addAll(wrapper.getProduto());

		} catch (Exception e) {
			JOptionPane.showMessageDialog(null, "Arquivo vazio ou nao encontrado!");
		}
		JOptionPane.showMessageDialog(null, "PRODUTOS IMPORTADOS DO ARQUIVO " + file);
	}

	public void LerProdutoSerializacao(File file) {
		try {

			FileInputStream fin = new FileInputStream(file);

			ObjectInputStream ois = new ObjectInputStream(fin);

			if (listaProduto != null)
				listaProduto.clear();
			else
				listaProduto = new ArrayList<>();

			listaProduto = (List<Produto>) ois.readObject();

			ois.close();
		} catch (Exception ex) {
			JOptionPane.showMessageDialog(null, "Arquivo vazio ou nao encontrado!");
		}
		JOptionPane.showMessageDialog(null, "PRODUTOS IMPORTADOS DO ARQUIVO " + file);
	}

	private Produto getProdutoSelecionadoNaTabela() {
		Produto c = null;
		int index = tablePro.getSelectedRow();
		if (index == -1) {
			JOptionPane.showMessageDialog(null, "Nenhuma linha selecionada!");
		} else {
			c = ((ProdutoModel) tablePro.getModel()).getProdutoNaLinha(index);
		}
		return c;
	}

	private Cliente getClienteSelecionadoNaTabela() {
		Cliente c = null;
		int index = tableCli.getSelectedRow();
		if (index == -1) {
			JOptionPane.showMessageDialog(null, "Nenhuma linha selecionada!");
		} else {
			c = ((ClienteModel) tableCli.getModel()).getClienteNaLinha(index);
		}
		return c;
	}

	private Cliente getClienteSelecionadoNaTabelaVDA() {
		Cliente c = null;
		int index = tableVda.getSelectedRow();
		if (index == -1) {
			JOptionPane.showMessageDialog(null, "Nenhuma linha selecionada!");
		} else {
			c = ((ClienteModel) tableVda.getModel()).getClienteNaLinha(index);
		}
		return c;
	}

	private Produto getProdutoSelecionadoNaTabelaVDA() {
		Produto c = null;
		int index = tableVda.getSelectedRow();
		if (index == -1) {
			JOptionPane.showMessageDialog(null, "Nenhuma linha selecionada!");
		} else {
			c = ((ProdutoModel) tableVda.getModel()).getProdutoNaLinha(index);
		}
		return c;
	}

	protected void importarClientesTXT() {

		ReaderArquivo reader = new ReaderArquivo();
		List<String> lista = reader.lerArquivo(new File("listaClientes.txt"));

		ParserCliente parser = new ParserCliente();
		listaCliente = parser.getCliente(lista);

		for (Cliente p : listaCliente) {
			salvar(p);
		}
		JOptionPane.showMessageDialog(null, "BANCO DE DADOS VAZIO! Os clientes foram importados do arquivo TXT");
	}

	public void SalvarClienteXML(File file) {
		try {
			JAXBContext context = JAXBContext.newInstance(ClienteWrapper.class);
			Marshaller m = context.createMarshaller();
			m.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);

			ClienteWrapper wrapper = new ClienteWrapper();
			wrapper.setListaCliente(listaCliente);

			m.marshal(wrapper, file);

		} catch (Exception e) {
			e.printStackTrace();
		}
		JOptionPane.showMessageDialog(null, "PRODUTOS EXPORTADOS PARA O ARQUIVO " + file);
	}

	public void SalvarClienteSerializacao(File file) {
		try {

			FileOutputStream fout = new FileOutputStream(file);

			ObjectOutputStream oos = new ObjectOutputStream(fout);

			oos.writeObject(listaCliente);

			oos.close();

		} catch (Exception ex) {
			ex.printStackTrace();
		}
		JOptionPane.showMessageDialog(null, "PRODUTOS EXPORTADOS PARA O ARQUIVO " + file);
	}

	public void LerClienteXML(File file) {
		try {
			JAXBContext context = JAXBContext.newInstance(ClienteWrapper.class);
			Unmarshaller um = context.createUnmarshaller();

			ClienteWrapper wrapper = (ClienteWrapper) um.unmarshal(file);

			if (listaCliente != null)
				listaCliente.clear();
			else
				listaCliente = new ArrayList<>();

			listaCliente.addAll(wrapper.getListaCliente());

		} catch (Exception e) {
			JOptionPane.showMessageDialog(null, "Arquivo vazio ou nao encontrado!");
		}
		JOptionPane.showMessageDialog(null, "CLIENTES IMPORTADOS DO ARQUIVO " + file);
	}

	public void LerClienteSerializacao(File file) {
		try {

			FileInputStream fin = new FileInputStream(file);

			ObjectInputStream ois = new ObjectInputStream(fin);

			if (listaCliente != null)
				listaCliente.clear();
			else
				listaCliente = new ArrayList<>();

			listaCliente = (List<Cliente>) ois.readObject();

			ois.close();
		} catch (Exception ex) {
			JOptionPane.showMessageDialog(null, "Arquivo vazio ou nao encontrado!");
		}
		JOptionPane.showMessageDialog(null, "CLIENTES IMPORTADOS DO ARQUIVO " + file);
	}

	public void SetModel(int modelo) {
		if (modelo == 1) {
			ProdutoModel model = new ProdutoModel(listaProduto);
			if (model != null)
				tablePro.setModel(model);
		} else if (modelo == 2) {
			ClienteModel model = new ClienteModel(listaCliente);
			if (model != null)
				tableCli.setModel(model);
		} else if (modelo == 3) {
			ProdVendaModel model = new ProdVendaModel(listaProdVenda);
			if (model != null)
				tableProVda.setModel(model);
		}
	}

	protected void removerSelecionado(int modelo) {
		if (modelo == 1) {
			Produto c = getProdutoSelecionadoNaTabela();
			if (c != null) {
				((ProdutoModel) tablePro.getModel()).removerProduto(c);
				excluir(c);
				listaProduto = ((ProdutoModel) tablePro.getModel()).getLista();
			}
		} else if (modelo == 2) {
			Cliente c = getClienteSelecionadoNaTabela();
			if (c != null) {
				((ClienteModel) tableCli.getModel()).removerCliente(c);
				excluir(c);
				listaCliente = ((ClienteModel) tableCli.getModel()).getLista();
			}
		}
	}

	private void configuraTabela(int modelo) {
		if (modelo == 1) {
			tablePro.addMouseListener(new MouseAdapter() {

				@Override
				public void mouseClicked(MouseEvent e) {

					if (e.getClickCount() == 1) {

						Produto c = getProdutoSelecionadoNaTabela();
						if (c != null) {
							tfCodigo.setEditable(true);
							tfCodigo.setText("");
							tfDesc.setText("");
							tfPreco.setText("");
						}
					} else if (e.getClickCount() == 2) {
						Produto c = getProdutoSelecionadoNaTabela();
						if (c != null) {
							tfCodigo.setEditable(false);
							tfCodigo.setText(Integer.toString(c.getId()));
							tfDesc.setText(c.getDescricao());
							tfPreco.setText(c.getPreco().toString());
						}
					}
				}
			});
		} else if (modelo == 2) {
			tableCli.addMouseListener(new MouseAdapter() {

				@Override
				public void mouseClicked(MouseEvent e) {

					if (e.getClickCount() == 1) {
						Cliente c = getClienteSelecionadoNaTabela();
						if (c != null) {

							tfId.setEditable(true);
							tfId.setText("");
							tfNome.setText("");
							tfEnd.setText("");
							tfNumero.setText("");
							tfComp.setText("");
							tfBairro.setText("");
							tfCidade.setText("");
							tfEstado.setText("");
							tfCep.setText("");
							tfTel.setText("");
							tfCel.setText("");
						}

					} else if (e.getClickCount() == 2) {
						Cliente c = getClienteSelecionadoNaTabela();
						if (c != null) {

							tfId.setEditable(false);
							tfId.setText(Integer.toString(c.getId()));
							tfNome.setText(c.getNome());
							tfEnd.setText(c.getEndereco());
							tfNumero.setText(Integer.toString(c.getNumero()));
							tfComp.setText(c.getComplemento());
							tfBairro.setText(c.getBairro());
							tfCidade.setText(c.getCidade());
							tfEstado.setText(c.getEstado());
							tfCep.setText(Integer.toString(c.getCep()));
							tfTel.setText(c.getTelefone());
							tfCel.setText(c.getCelular());
						}
					}
				}

			});
		}

	}

	private SqlGenImplementation getImp() {
		if (imp == null) {
			try {
				setImp(new SqlGenImplementation());
			} catch (Exception e) {
				e.printStackTrace();
			}
		}

		return imp;
	}

	private void setImp(SqlGenImplementation imp) {
		this.imp = imp;
	}

	public void CriarTabela(Object obj) {
		String sql = getImp().getCreateTable(getImp().getCon(), obj);

		try (PreparedStatement ps = getImp().getCon().prepareStatement(sql)) {
			ps.executeUpdate();
		} catch (Exception e) {
			JOptionPane.showMessageDialog(null, "ERRO NA CONEXAO COM O BANCO!");
		}
	}

	public void salvar(Object obj) {
		PreparedStatement inclusao = getImp().getSqlInsert(getImp().getCon(), obj);

		if (obj.getClass() == Produto.class) {
			Produto p = (Produto) obj;
			try {
				inclusao.setInt(1, p.getId());
				inclusao.setString(2, p.getDescricao());
				inclusao.setBigDecimal(3, p.getPreco());
			} catch (Exception e) {
				JOptionPane.showMessageDialog(null, "ERRO NA CONEXAO COM O BANCO!");
			}

		} else if (obj.getClass() == Cliente.class) {
			Cliente c = (Cliente) obj;
			try {
				inclusao.setInt(1, c.getId());
				inclusao.setString(2, c.getNome());
				inclusao.setString(3, c.getEndereco());
				inclusao.setInt(4, c.getNumero());
				inclusao.setString(5, c.getComplemento());
				inclusao.setString(6, c.getBairro());
				inclusao.setString(7, c.getCidade());
				inclusao.setString(8, c.getEstado());
				inclusao.setInt(9, c.getCep());
				inclusao.setString(10, c.getTelefone());
				inclusao.setString(11, c.getCelular());
			} catch (Exception e) {
				e.printStackTrace();
				JOptionPane.showMessageDialog(null, "ERRO NA CONEXAO COM O BANCO!");
			}
		}

		try {
			inclusao.executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();
			JOptionPane.showMessageDialog(null, "ERRO NA CONEXAO COM O BANCO!");
		}
	}

	public int buscar(Object obj) {
		PreparedStatement buscar = getImp().getSqlSelectById(getImp().getCon(), obj);

		ResultSet exibir = null;
		int cont = 0;

		try {
			exibir = buscar.executeQuery();
			while (exibir.next()) {
				cont++;
			}
		} catch (Exception e) {
			JOptionPane.showMessageDialog(null, "ERRO NA CONEXAO COM O BANCO!");
		}

		return cont;
	}

	public void atualizar(Object obj) {
		PreparedStatement alterar = getImp().getSqlUpdateById(getImp().getCon(), obj);

		Cliente cliente = (Cliente) obj;

		int exibir = 0;

		try {
			alterar.setString(1, cliente.getNome());
			alterar.setString(2, cliente.getEndereco());
			alterar.setString(3, cliente.getTelefone());
			alterar.setInt(5, cliente.getId());
		} catch (Exception e) {
			JOptionPane.showMessageDialog(null, "ERRO NA CONEXAO COM O BANCO!");
		}

		try {
			exibir = alterar.executeUpdate();
		} catch (Exception e) {
			JOptionPane.showMessageDialog(null, "ERRO NA CONEXAO COM O BANCO!");
		}
	}

	public void excluir(Object obj) {
		PreparedStatement excluir = getImp().getSqlDeleteById(getImp().getCon(), obj);

		try {
			excluir.executeUpdate();
		} catch (Exception e) {
			JOptionPane.showMessageDialog(null, "ERRO NA CONEXAO COM O BANCO!");
		}

	}

	public void listarTodos(Object obj) {

		PreparedStatement listar = getImp().getSqlSelectAll(getImp().getCon(), obj);

		if (obj.getClass() == Produto.class) {
			ResultSet retorno = null;

			try {
				retorno = listar.executeQuery();
				while (retorno.next()) {
					Produto aux = new Produto();
					aux.setId(retorno.getInt("id"));
					aux.setDescricao(retorno.getString("descricao"));
					aux.setPreco(new BigDecimal(retorno.getString("preco")));

					listaProduto.add(aux);

				}

			} catch (Exception e) {
				JOptionPane.showMessageDialog(null, "ERRO NA CONEXAO COM O BANCO!");
			}

		} else if (obj.getClass() == Cliente.class) {
			ResultSet retorno = null;

			try {
				retorno = listar.executeQuery();
				while (retorno.next()) {
					Cliente aux = new Cliente();
					aux.setId(retorno.getInt("id"));
					aux.setNome(retorno.getString("nome"));
					aux.setEndereco(retorno.getString("endereco"));
					aux.setNumero(retorno.getInt("numero"));
					aux.setComplemento(retorno.getString("complemento"));
					aux.setBairro(retorno.getString("bairro"));
					aux.setCidade(retorno.getString("cidade"));
					aux.setEstado(retorno.getString("estado"));
					aux.setCep(retorno.getInt("cep"));
					aux.setTelefone(retorno.getString("telefone"));
					aux.setCelular(retorno.getString("celular"));

					listaCliente.add(aux);
				}

			} catch (Exception e) {
				JOptionPane.showMessageDialog(null, "ERRO NA CONEXAO COM O BANCO!");
			}
		}

		try {
			listar.executeQuery();
		} catch (Exception e) {
			JOptionPane.showMessageDialog(null, "ERRO NA CONEXAO COM O BANCO!");
		}

		/*
		 * if (listaProduto.size() > 0 && listaCliente.size() > 0)
		 * JOptionPane.showMessageDialog(null, obj.getClass() +
		 * "IMPORTADOS DO BANCO DE DADOS!");
		 */
	}

	private void configuraTelaVdaCliente() {

		tfVdaCli.addKeyListener(new KeyAdapter() {

			@Override
			public void keyPressed(KeyEvent e) {

				busca(tfVdaCli.getText().trim(), 1);

				if (e.getKeyCode() == KeyEvent.VK_DOWN) {
					tableVda.getSelectionModel().setSelectionInterval(0, 0);
					tfVdaCli.transferFocus();
				}

				if (e.getKeyCode() == KeyEvent.VK_ESCAPE) {
					if (TelaPrincipal.this.runnableOnCancel != null) {
						TelaPrincipal.this.runnableOnCancel.run();
					}
				}
			}
		});

		tableVda.addMouseListener(new MouseAdapter() {

			@Override
			public void mouseClicked(MouseEvent e) {
				if (e.getClickCount() >= 1) {

					if (tableVda.getModel().getClass().equals(ClienteModel.class)) {

						Cliente c = getClienteSelecionadoNaTabelaVDA();
						if (c != null) {
							tfVdaCli.setText(c.getNome());
							tfVdaId.setText(Integer.toString(c.getId()));
							tfVdaCli.enable(false);
							busca(null, 1);

						}
					}
					if (tableVda.getModel().getClass().equals(ProdutoModel.class)) {

						Produto c = getProdutoSelecionadoNaTabelaVDA();
						if (c != null) {
							tfVdaPro.setText(c.getDescricao());
							tfVdaIdPro.setText(Integer.toString(c.getId()));
							Vunit = c.getPreco();
							busca(null, 2);
						}
					}
				}
			}
		});

		tfVdaPro.addKeyListener(new KeyAdapter() {

			@Override
			public void keyPressed(KeyEvent e) {

				busca(tfVdaPro.getText().trim(), 2);

				if (e.getKeyCode() == KeyEvent.VK_DOWN) {
					tableVda.getSelectionModel().setSelectionInterval(0, 0);
					tfVdaPro.transferFocus();
				}

				if (e.getKeyCode() == KeyEvent.VK_ESCAPE) {
					if (TelaPrincipal.this.runnableOnCancel != null) {
						TelaPrincipal.this.runnableOnCancel.run();
					}
				}
			}
		});
	}

	protected void busca(String palavra, int modelo) {

		if (modelo == 1) {

			List<Cliente> lista = buscaClienteNoBanco(palavra);

			ClienteModel model = new ClienteModel(lista);
			tableVda.setModel(model);
		} else if (modelo == 2) {
			List<Produto> lista = buscaProdutoNoBanco(palavra);

			ProdutoModel model = new ProdutoModel(lista);
			tableVda.setModel(model);
		}

	}

	private List<Cliente> buscaClienteNoBanco(String palavra) {

		List<Cliente> lista = new ArrayList<Cliente>();

		PreparedStatement ps;
		try {
			ps = getImp().getCon().prepareStatement("Select * from cliente where nome like '" + palavra + "%';");

			ResultSet retorno = ps.executeQuery();

			try {
				while (retorno.next()) {
					Cliente aux = new Cliente();
					aux.setId(retorno.getInt("id"));
					aux.setNome(retorno.getString("nome"));
					aux.setEndereco(retorno.getString("endereco"));
					aux.setNumero(retorno.getInt("numero"));
					aux.setComplemento(retorno.getString("complemento"));
					aux.setBairro(retorno.getString("bairro"));
					aux.setCidade(retorno.getString("cidade"));
					aux.setEstado(retorno.getString("estado"));
					aux.setCep(retorno.getInt("cep"));
					aux.setTelefone(retorno.getString("telefone"));
					aux.setCelular(retorno.getString("celular"));

					lista.add(aux);
				}

			} catch (Exception e) {

			}
		} catch (SQLException e1) {

		}

		return lista;
	}

	private List<Produto> buscaProdutoNoBanco(String palavra) {

		List<Produto> lista = new ArrayList<Produto>();

		PreparedStatement ps;
		try {
			ps = getImp().getCon().prepareStatement("Select * from produto where descricao LIKE '" + palavra.toUpperCase() + "%';");

			ResultSet retorno = ps.executeQuery();

			try {
				while (retorno.next()) {
					Produto aux = new Produto();
					aux.setId(retorno.getInt("id"));
					aux.setDescricao(retorno.getString("descricao"));
					aux.setPreco(new BigDecimal(retorno.getString("preco")));

					lista.add(aux);
				}

			} catch (Exception e) {

			}
		} catch (SQLException e1) {

		}

		return lista;
	}

	public void setOnOk(Consumer<Cliente> c) {
		this.consumerOnOk = c;
	}

	public void setOnCancel(Runnable r) {
		this.runnableOnCancel = r;
	}

	@Override
	public void setVisible(boolean arg0) {

		super.setVisible(arg0);
		EventQueue.invokeLater(new Runnable() {

			@Override
			public void run() {
				tfVdaCli.requestFocusInWindow();
			}
		});
	}

}
