package ar.edu.unlu.teocomp1.grupo3;

import java.awt.EventQueue;

import javax.swing.DefaultListModel;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.border.EmptyBorder;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.JTextArea;
import javax.swing.ListCellRenderer;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;

import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JMenu;
import javax.swing.JList;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.awt.event.ActionEvent;
import java.awt.Font;

public class VentanaMain extends JFrame {

	private JPanel contentPane;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					VentanaMain frame = new VentanaMain();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public VentanaMain() {
		setTitle("Grupo3_IDE");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 800, 600);
		
		JMenuBar menuBar = new JMenuBar();
		setJMenuBar(menuBar);
		
		JMenu mnNewMenu = new JMenu("Archivo");
		menuBar.add(mnNewMenu);
		
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(contentPane);
		contentPane.setLayout(new BorderLayout(0, 0));
		
		JTextArea textArea = new JTextArea();
		textArea.setFont(new Font("Monospaced", Font.PLAIN, 20));
		JScrollPane scrollPaneTextArea = new JScrollPane(textArea);
		contentPane.add(scrollPaneTextArea, BorderLayout.CENTER);
		
		JMenuItem mntmNewMenuItem = new JMenuItem("Abrir");
		mntmNewMenuItem.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				JFileChooser fileChooser = new JFileChooser(new File(System.getProperty("user.dir")));
				int r = fileChooser.showOpenDialog(VentanaMain.this);
				if (r == JFileChooser.APPROVE_OPTION) {
					File selectedFile = fileChooser.getSelectedFile();
					System.out.println(selectedFile.getAbsolutePath());
					try(FileInputStream fileInputStream = new FileInputStream(selectedFile);
							InputStreamReader inputStreamReader = new InputStreamReader(fileInputStream);
							BufferedReader bufferedReader = new BufferedReader(inputStreamReader)){
						String linea;
						while ((linea = bufferedReader.readLine()) != null) {
							textArea.append(linea);
							textArea.append("\n");
						}
					} catch (IOException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
				}
			}
		});
		mnNewMenu.add(mntmNewMenuItem);
		
		JMenu mnNewMenu_1 = new JMenu("Editor");
		menuBar.add(mnNewMenu_1);
		DefaultListModel<Resultado> listModel = new DefaultListModel<Resultado>();
		JList<Resultado> list = new JList<>(listModel);
		list.setFont(new Font("Tahoma", Font.PLAIN, 10));
		JScrollPane scrollPane = new JScrollPane(list);
		list.setCellRenderer(new ListCellRenderer<Resultado>() {

			@Override
			public Component getListCellRendererComponent(JList<? extends Resultado> list, Resultado value, int index,
					boolean isSelected, boolean cellHasFocus) {
				String lexema = value.getLexema();
				String textoLabel = String.format("Línea %d Columna %d Lexema [%s] Token [%s]",
						value.getLinea(), value.getColumna(), "\n".equals(lexema) ? " " : lexema, value.getToken());
				JLabel label = new JLabel(textoLabel);
				String nombre = label.getFont().getName();
				int estilo = label.getFont().getStyle();
				label.setFont(new Font(nombre, estilo, 20));

				if (value.isError())
					label.setForeground(Color.RED);
				list.add(label);
				return label;
			}
			
		});
		list.addListSelectionListener(new ListSelectionListener() {

			@Override
			public void valueChanged(ListSelectionEvent e) {
				Resultado resultado = list.getSelectedValue();
				System.out.println("CLICK " + resultado.getLexema());
				textArea.requestFocus();
				textArea.select(resultado.getInicio(), resultado.getInicio() + resultado.getTamanio());
			}
		});
		contentPane.add(scrollPane, BorderLayout.SOUTH);
		
		JMenuItem mntmNewMenuItem_1 = new JMenuItem("Analizar");
		mntmNewMenuItem_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				listModel.clear();
				try (ByteArrayInputStream is = new ByteArrayInputStream(textArea.getText().getBytes());
						InputStreamReader reader = new InputStreamReader(is)) {
					Lexico lexico = new Lexico(reader);
					lexico.next_token();
					lexico.getResultados().forEach(listModel::addElement);
				} catch (Exception e2) {
					System.err.println(e2);
				}
			}
		});
		mnNewMenu_1.add(mntmNewMenuItem_1);
	}

}
