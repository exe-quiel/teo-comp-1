package ar.edu.unlu.teocomp1.grupo3;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.EventQueue;
import java.awt.Font;
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
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
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
import javax.swing.border.TitledBorder;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

public class VentanaMain extends JFrame {

    private static final List<String> CONSTANT_LITERAL_TOKENS = Arrays.asList("CONST_STRING", "CONST_INT",
            "CONST_FLOAT", "CONST_BIN", "CONST_HEX");
    private static final List<String> TOKENS_TABLA_SIMBOLO = Arrays.asList("ID", "CONST_STRING", "CONST_INT",
            "CONST_FLOAT", "CONST_HEX", "CONST_BIN");
    private JPanel contentPane;
    private Font menuFont = new Font("SansSerif", Font.PLAIN, 15);

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
        menuArchivo.setFont(menuFont);
        menuBar.add(menuArchivo);

        contentPane = new JPanel();
        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
        contentPane.setLayout(new BorderLayout(0, 0));
        setContentPane(contentPane);

        JTextArea textArea = new JTextArea();
        textArea.setFont(new Font("Courier New", Font.PLAIN, 20));
        contentPane.add(new JScrollPane(textArea), BorderLayout.CENTER);

        // Lee el archivo línea por línea y coloca el contenido en el JTextArea.
        JMenuItem menuItemAbrir = new JMenuItem("Abrir");
        menuItemAbrir.setFont(menuFont);
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

        DefaultListModel<Token> tokensListModel = new DefaultListModel<>();
        JList<Token> listaGuiTokens = new JList<>(tokensListModel);
        listaGuiTokens.setVisibleRowCount(15);

        // Cada token se muestra como un JLabel que contiene la información de ese token
        // (línea, columna, lexema y token)
        listaGuiTokens.setCellRenderer(new ListCellRenderer<Token>() {

            private Font labelFont = new Font("SansSerif", Font.PLAIN, 15);

            @Override
            public Component getListCellRendererComponent(JList<? extends Token> list, Token value, int index,
                    boolean isSelected, boolean cellHasFocus) {

                String texto = String.format("linea %d columna %d lexema {%s} token {%s}", value.getLinea(),
                        value.getColumna(), value.getLexema(), value.getToken());
                JLabel label = new JLabel(texto);
                label.setFont(labelFont);
                if (value.isError()) {
                    label.setForeground(Color.red);
                }
                return label;
            }
        });

        // Al hacer clic sobre un token en la lista de resultados,
        // se selecciona el lexema correspondiente en el JTextArea
        listaGuiTokens.addListSelectionListener(new ListSelectionListener() {

            @Override
            public void valueChanged(ListSelectionEvent e) {
                textArea.requestFocus();
                Token resultado = listaGuiTokens.getSelectedValue();
                if (resultado != null) {
                    textArea.select(resultado.getInicio(), resultado.getInicio() + resultado.getTamanio());
                }
            }
        });

        TitledBorder tokensBorder = new TitledBorder("Tokens");
        tokensBorder.setTitleFont(new Font("SansSerif", Font.BOLD, 15));
        JPanel tokensPanel = new JPanel();
        tokensPanel.setLayout(new BorderLayout());
        tokensPanel.setBorder(tokensBorder);
        tokensPanel.add(new JScrollPane(listaGuiTokens), BorderLayout.CENTER);
        contentPane.add(tokensPanel, BorderLayout.SOUTH);

        DefaultListModel<Regla> reglasListModel = new DefaultListModel<>();
        JList<Regla> listaGuiReglas = new JList<>(reglasListModel);
        listaGuiReglas.setVisibleRowCount(20);
        // listaGuiReglas.setSize(50,100);
        TitledBorder reglasBorder = new TitledBorder("Reglas");
        reglasBorder.setTitleFont(new Font("SansSerif", Font.BOLD, 15));
        JPanel reglasPanel = new JPanel();
        reglasPanel.setLayout(new BorderLayout());
        reglasPanel.setBorder(reglasBorder);
        reglasPanel.add(new JScrollPane(listaGuiReglas), BorderLayout.CENTER);
        contentPane.add(reglasPanel, BorderLayout.EAST);

        listaGuiReglas.setCellRenderer(new ListCellRenderer<Regla>() {

            private Font labelFont = new Font("SansSerif", Font.PLAIN, 15);

            @Override
            public Component getListCellRendererComponent(JList<? extends Regla> list, Regla value, int index,
                    boolean isSelected, boolean cellHasFocus) {

                String texto = value.getNumero();
                String antecedente = value.getAntecedente();
                JLabel label = new JLabel(texto + " Antecedente: " + antecedente);
                label.setFont(labelFont);

                /*
                 * if (value.isError()) { label.setForeground(Color.red); }
                 */
                return label;
            }
        });

        JMenu menuEditor = new JMenu("Editor");
        menuEditor.setFont(menuFont);
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
        menuItemAnalizar.setFont(menuFont);
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
                    Sintactico parser = new Sintactico(lexer);
                    // lexer.next_token();
                    try {
                        parser.parse();
                    } catch (Exception e) {
                        System.err.println("Se produjo un error al ejecutar el parsing: " + e);
                    }
                    tokensListModel.clear();
                    reglasListModel.clear();

                    String archivoTexto = "ts.txt";

                    try {
                        // Crear un objeto FileWriter para escribir en el archivo
                        FileWriter writer = new FileWriter(archivoTexto);

                        // Encabezados de la tabla
                        writer.write("NOMBRE          TOKEN           TIPO            VALOR           LONG\n");
                        writer.write("--------------------------------------------------------------------\n");

                        Set<String> tokensYaIncluidos = new HashSet<String>();

                        System.out.println(parser.getTipos());
                        System.out.println(parser.getVariables());

                        Map<String, String> varsToTipos = new HashMap<>();

                        int tamanioLista = parser.getVariables().size();
                        for (int i = 0; i < tamanioLista; i++) {
                            varsToTipos.put(parser.getVariables().get(i), parser.getTipos().get(i));
                        }

                        for (Token token : lexer.getTokens()) {
                            // Actualiza el modelo de la lista con los resultados
                            tokensListModel.addElement(token);

                            if (TOKENS_TABLA_SIMBOLO.contains(token.getToken())
                                    && !tokensYaIncluidos.contains(token.getLexema())) {
                                String nombre = token.getLexema();
                                String valor = null;
                                String tipo = null;
                                tokensYaIncluidos.add(nombre);
                                int longitud = -1;
                                if (CONSTANT_LITERAL_TOKENS.contains(token.getToken())) {
                                    nombre = "_" + nombre;
                                    valor = token.getLexema();
                                }
                                if (token.getToken().equals("CONST_STRING"))
                                    longitud = token.getLexema().length() - 2;
                                if (token.getToken().equals("ID"))
                                    tipo = varsToTipos.get(token.getLexema());

                                String fila = String.format("%-15s %-15s %-15s %-15s %-15s\n", nombre, token.getToken(),
                                        tipo == null ? "---" : tipo, valor == null ? "---" : valor, longitud == -1 ? "---" : longitud);
                                writer.write(fila);
                            }
                        }
                        for (Regla regla : parser.getReglas()) {
                            reglasListModel.addElement(regla);
                        }

                        // Cerrar el FileWriter
                        writer.close();

                        System.out.println("El archivo ts.txt se ha creado exitosamente.");
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
