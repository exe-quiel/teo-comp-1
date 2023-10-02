package ar.edu.unlu.teocomp1.grupo3;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;

import javax.swing.DefaultListModel;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.ListCellRenderer;
import javax.swing.border.EmptyBorder;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

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
		setBounds(100, 100, 1000, 800);
		
		JMenuBar menuBar = new JMenuBar();
		setJMenuBar(menuBar);
		
		JMenu mnNewMenu = new JMenu("Archivo");
		menuBar.add(mnNewMenu);
		
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(contentPane);
		contentPane.setLayout(new BorderLayout(0, 0));
		
		JTextArea textArea = new JTextArea();
		contentPane.add(textArea, BorderLayout.CENTER);
		
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
						while ((linea = bufferedReader.readLine())!=null) {
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
		
		DefaultListModel<Resultado> listModel = new DefaultListModel<>();
		JList<Resultado> list = new JList<>(listModel);
		list.setVisibleRowCount(20);
		list.setCellRenderer(new ListCellRenderer<Resultado>() {

			@Override
			public Component getListCellRendererComponent(JList<? extends Resultado> list, Resultado value, int index,
					boolean isSelected, boolean cellHasFocus) {
				// TODO Auto-generated method stub
				String texto = String.format("linea %d columna %d lexema {%s} token {%s}", value.getLinea(), value.getColumna(), value.getLexema(), value.getToken());
				JLabel label = new JLabel(texto);
				if (value.isError()) {
					label.setForeground(Color.red);
				}
				return label;
			}
		});
		
		list.addListSelectionListener(new ListSelectionListener() {
			
			@Override
			public void valueChanged(ListSelectionEvent e) {
				// TODO Auto-generated method stub
				textArea.requestFocus();
				Resultado r = list.getSelectedValue();
				textArea.select(r.getInicio(), r.getInicio() + r.getTamanio());
			}
		});
		
		contentPane.add(new JScrollPane(list), BorderLayout.SOUTH);
		
		JMenu mnNewMenu_1 = new JMenu("Editor");
		menuBar.add(mnNewMenu_1);
		
		JMenuItem mntmNewMenuItem_1 = new JMenuItem("Analizar");
		mntmNewMenuItem_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				procesarDatos();
			}
			
			private void procesarDatos() {
		        String inputText = textArea.getText(); // Obtener el texto de la TextArea
		        
		        try (ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(inputText.getBytes());
		        		InputStreamReader inputStreamReader = new InputStreamReader(byteArrayInputStream)) {
					//Usar lexico
		        	 Lexico lexer = new Lexico(inputStreamReader);
		        	 lexer.next_token();
		        	 listModel.clear();
		        	 for (Resultado resultado : lexer.getResultados()) {
		        		 // Actualiza el modelo de la lista con los resultados
		        		 listModel.addElement(resultado);
		        	 } 
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				};


		    }
			
		});
		mnNewMenu_1.add(mntmNewMenuItem_1);
		

	}
	
	
	
	

}
