package ar.edu.unlu.teocomp1.grupo3;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextArea;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

public class GUIMain extends Application {

	private TextArea textArea;
	private VBox resultPane;

	public static void main(String[] args) {
		launch(args);
	}

	@Override
	public void start(Stage primaryStage) throws Exception {
		primaryStage.setTitle("Grupo3-IDE");
		BorderPane pane = new BorderPane();

		// Editor
		textArea = new TextArea();
		pane.setCenter(textArea);

		// Resultados (lista de tokens)
		resultPane = new VBox();
		ScrollPane resultScrollPane = new ScrollPane(resultPane);
		resultScrollPane.setMaxHeight(200);
		pane.setBottom(resultScrollPane);

		textArea.setOnKeyTyped(event -> {
			resultPane.getChildren().clear();
		});

		MenuBar menuBar = new MenuBar();
		Menu menuArchivo = crearMenuArchivo(primaryStage);
		menuBar.getMenus().add(menuArchivo);
		Menu menuEditor = crearMenuEditor();
		menuBar.getMenus().add(menuEditor);
		pane.setTop(menuBar);

		primaryStage.setScene(new Scene(pane, 1200, 800));
		primaryStage.show();
	}

	private Menu crearMenuArchivo(Stage primaryStage) {
		Menu menu = new Menu("Archivo");
		MenuItem menuItem = new MenuItem("Abrir");
		menuItem.setOnAction(event -> {
			FileChooser fileChooser = new FileChooser();
			fileChooser.setInitialDirectory(new File(System.getProperty("user.dir")));
			File archivoSeleccionado = fileChooser.showOpenDialog(primaryStage);
			if (archivoSeleccionado != null) {
				System.out.println(archivoSeleccionado.getAbsolutePath());
				try (FileInputStream is = new FileInputStream(archivoSeleccionado);
						BufferedReader reader = new BufferedReader(new InputStreamReader(is))) {
					textArea.clear();
					String linea;
					while ((linea = reader.readLine()) != null) {
						textArea.appendText(linea);
						textArea.appendText("\n");
					}
				} catch (Exception exception) {
					System.err.println(exception);
				}
			}
		});
		menu.getItems().add(menuItem);
		return menu;
	}

	private Menu crearMenuEditor() {
		Menu menu = new Menu("Editor");
		MenuItem menuItem = new MenuItem("Analizar");
		menuItem.setOnAction(event -> {
			resultPane.getChildren().clear();
			String codigo = textArea.getText();
			try (ByteArrayInputStream inputStream = new ByteArrayInputStream(codigo.getBytes());
					InputStreamReader inputStreamReader = new InputStreamReader(inputStream)) {
				Lexico lexico = new Lexico(inputStreamReader);
				lexico.next_token();
				System.out.println(lexico.getResultados());
				lexico.getResultados()
					.stream()
					.map(this::mapResultadoToLabel)
					.forEach(resultPane.getChildren()::add);
			} catch (IOException e) {
				System.err.println(e);
			}
		});
		menu.getItems().add(menuItem);
		return menu;
	}

	private Label mapResultadoToLabel(Resultado resultado) {
		Label resultadoLabel = new Label();
		resultadoLabel.setOnMouseClicked(event -> {
			System.out.println(resultado.getLinea() + ":" + resultado.getColumna());
			textArea.requestFocus();
			textArea.selectRange((int) resultado.getInicio(), (int) resultado.getInicio() + resultado.getLargo());
		});
		resultadoLabel.setTextFill(resultado.isError() ? Color.RED : Color.BLACK);
		String texto = String.format("%d:%d\tLexema [%s]\tToken [%s]", resultado.getLinea(), resultado.getColumna(),
				resultado.getLexema(), resultado.getToken());
		resultadoLabel.setText(texto);
		return resultadoLabel;
	}
}
