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
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

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

	private static final List<String> CONSTANT_LITERAL_TOKENS = Arrays.asList("CONST_STRING", "CONST_INT", "CONST_FLOAT", "CONST_BIN", "CONST_HEX");
	private static final List<String> TOKENS_TABLA_SIMBOLO = Arrays.asList("ID", "CONST_STRING", "CONST_INT", "CONST_FLOAT", "CONST_HEX", "CONST_BIN");
	private JPanel contentPane;

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

	public VentanaMain() {
		setTitle("Grupo3_IDE");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 1000, 800);

		JMenuBar menuBar = new JMenuBar();
		setJMenuBar(menuBar);

		JMenu menuArchivo = new JMenu("Archivo");
		menuBar.add(menuArchivo);

		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setLayout(new BorderLayout(0, 0));
		setContentPane(contentPane);

		JTextArea textArea = new JTextArea();
		contentPane.add(textArea, BorderLayout.CENTER);

		// Lee el archivo línea por línea y coloca el contenido en el JTextArea.
		JMenuItem menuItemAbrir = new JMenuItem("Abrir");
		menuItemAbrir.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent event) {
				JFileChooser fileChooser = new JFileChooser(new File(System.getProperty("user.dir")));
				int estadoSeleccion = fileChooser.showOpenDialog(VentanaMain.this);
				if (estadoSeleccion == JFileChooser.APPROVE_OPTION) {
					File selectedFile = fileChooser.getSelectedFile();
					System.out.println("Archivo seleccionado: " + selectedFile.getAbsolutePath());

					try (FileInputStream fileInputStream = new FileInputStream(selectedFile);
							InputStreamReader inputStreamReader = new InputStreamReader(fileInputStream);
							BufferedReader bufferedReader = new BufferedReader(inputStreamReader)) {

						textArea.setText("");

						String linea;
						while ((linea = bufferedReader.readLine()) != null) {
							textArea.append(linea);
							textArea.append("\n");
						}
					} catch (IOException e) {
						System.err.println("Se produjo un error: " + e);
					}
				}
			}
		});

		menuArchivo.add(menuItemAbrir);

		DefaultListModel<Resultado> listModel = new DefaultListModel<>();
		JList<Resultado> listaGuiResultados = new JList<>(listModel);
		listaGuiResultados.setVisibleRowCount(20);

		// Cada token se muestra como un JLabel que contiene la información de ese token
		// (línea, columna, lexema y token)
		listaGuiResultados.setCellRenderer(new ListCellRenderer<Resultado>() {

			@Override
			public Component getListCellRendererComponent(JList<? extends Resultado> list, Resultado value, int index,
					boolean isSelected, boolean cellHasFocus) {

				String texto = String.format("linea %d columna %d lexema {%s} token {%s}", value.getLinea(),
						value.getColumna(), value.getLexema(), value.getToken());
				JLabel label = new JLabel(texto);
				if (value.isError()) {
					label.setForeground(Color.red);
				}
				return label;
			}
		});

		// Al hacer clic sobre un token en la lista de resultados,
		// se selecciona el lexema correspondiente en el JTextArea
		listaGuiResultados.addListSelectionListener(new ListSelectionListener() {

			@Override
			public void valueChanged(ListSelectionEvent e) {
				textArea.requestFocus();
				Resultado resultado = listaGuiResultados.getSelectedValue();
				textArea.select(resultado.getInicio(), resultado.getInicio() + resultado.getTamanio());
			}
		});

		contentPane.add(new JScrollPane(listaGuiResultados), BorderLayout.SOUTH);

		JMenu menuEditor = new JMenu("Editor");
		menuBar.add(menuEditor);

		/*
		 * Este botón:
		 * 
		 * 1. instancia la clase generada Lexico
		 * 2. analiza el contenido del JTextArea
		 * 3. muestra los resultados en el JList
		 * 4. crea el archivo que contiene la tabla de símbolos
		 */ 
		JMenuItem menuItemAnalizar = new JMenuItem("Analizar");
		menuItemAnalizar.addActionListener(new ActionListener() {



			public void actionPerformed(ActionEvent e) {
				procesarDatos();
			}

			private void procesarDatos() {
				String inputText = textArea.getText(); // Obtener el texto de la TextArea
				try (ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(inputText.getBytes());
						InputStreamReader inputStreamReader = new InputStreamReader(byteArrayInputStream)) {
					// Usar lexico
					Lexico lexer = new Lexico(inputStreamReader);
					lexer.next_token();
					listModel.clear();

					// TODO Acá deberíamos crear el archivo de la tabla de símbolos
					String archivoTexto = "ts.txt";
					
			        try {
			            // Crear un objeto FileWriter para escribir en el archivo
			            FileWriter writer = new FileWriter(archivoTexto);
			            
			            // Encabezados de la tabla
			            writer.write("NOMBRE          TOKEN           TIPO            VALOR           LONG\n");
			            writer.write("--------------------------------------------------------------------\n");
			            
			            Set<String> tokensYaIncluidos = new HashSet<String>();
			            
			            for (Resultado resultado : lexer.getResultados()) {
			            	// Actualiza el modelo de la lista con los resultados
							listModel.addElement(resultado);
							
							if (TOKENS_TABLA_SIMBOLO.contains(resultado.getToken()) && !tokensYaIncluidos.contains(resultado.getLexema())) {
				            	String nombre = resultado.getLexema();
				            	tokensYaIncluidos.add(nombre);
				            	int longitud = -1;
				            	if (CONSTANT_LITERAL_TOKENS.contains(resultado.getToken())) nombre = "_" + nombre;
				            	if (resultado.getToken().equals("CONST_STRING")) longitud = resultado.getLexema().length()-2;
				            	
				            	String fila = String.format("%-15s %-15s %-15s %-15s %-15s\n",
				                        nombre,
				                        resultado.getToken(),
				                        "---",
				                        resultado.getLexema(),
				                        longitud == -1 ? "---" : longitud
				            		);
				                writer.write(fila);
							}
			            	/*String fila = nombre + "\t" + resultado.getToken() + "\t" + "---" + "\t"
			                        + resultado.getLexema() + "\t" + resultado.getTamanio() + "\n";
			                writer.write(fila);
			                */
			            }

			            // Cerrar el FileWriter
			            writer.close();

			            System.out.println("El archivo se ha creado exitosamente.");
			        } catch (IOException e) {
			            e.printStackTrace();
			        }

				} catch (IOException e) {
					System.err.println("Se produjo un error: " + e);
				}
			}
		});
		menuEditor.add(menuItemAnalizar);
	}
}
