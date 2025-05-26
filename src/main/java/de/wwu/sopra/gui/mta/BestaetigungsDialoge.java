package de.wwu.sopra.gui.mta;

import java.util.ArrayList;
import java.util.Optional;

import de.wwu.sopra.gui.helfer.ButtonRow;
import de.wwu.sopra.gui.helfer.GUIHelfer;
import de.wwu.sopra.gui.helfer.UeberschriftUndButton;
import de.wwu.sopra.model.Probe;
import de.wwu.sopra.steuerung.mta.ProbenSteuerung;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Dialog;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

/**
 * Eine GUI-Klasse zum Anzeigen von BestaetigungsDialoge bei entnehmen und
 * loeschen. und zum Anzeigen der Probenstandort.
 * @author Wael Alallan
 */
public class BestaetigungsDialoge { 
	private Stage stage;
	private Scene scene;
	private ProbenSteuerung probenSt = new ProbenSteuerung();
	private Probe probe;

	/**
	 * Klassenkonstruktor
	 * @pre stage darf nicht null sein
	 * @pre oldScene darf nicht null sein
	 * @param stage - die aktive Stage
	 * @param scene -  die alte scene
	 * @param probe - Probe, die bearbeitet wird
	 */
	public BestaetigungsDialoge(Stage stage, Scene scene, Probe probe) {
		this.stage = stage;
		this.scene = scene;
		this.probe = probe;	
	}

	/**
	 * bei Aufruf dieser Methode wird ein Dialog angezeigt mit dem Standort der zu entnehmenden Probe.
	 */
	void kurzfristigEntnehmenDialog() {
		Dialog<String> dialog = new Dialog<>();
		dialog.setTitle("Probe kurzfristig entnehmen");
		UeberschriftUndButton bf = new UeberschriftUndButton("Die Probe wurde kurzfristig "+ "\n"+ "ausgelagert",
				null, null);
		Label label = new Label("Der zugeteilte Stellplatz ist: \n" + probenSt.probeKurzfristigEntnehmen(probe));
		String[] buttons = { null, "Probe entnommen", null};
		ButtonRow entnommenButton = new ButtonRow(buttons);

		GridPane grid = new GridPane();
		grid.setPrefSize(1200, 500);
		grid.setHgap(10);
		grid.setVgap(10);
		grid.setPadding(new Insets(20, 35, 20, 35));
		grid.addRow(0, bf.getGridPane());
		grid.addRow(1, label);
		grid.addRow(2, entnommenButton.getGridPane());
		dialog.getDialogPane().setContent(grid);
		
		GUIHelfer.setzeZeilenGewichte(grid, GUIHelfer.gibGleicheGewichte(grid.getChildren().size()));
		GUIHelfer.setzeSpaltenGewichte(grid, GUIHelfer.gibGleicheGewichte(1));

		entnommenButton.getButton("Probe entnommen").setOnAction(event -> {
			
			dialog.setResult("");
			ProbeninformationenKurzEntnommenFormular pif =	new ProbeninformationenKurzEntnommenFormular(stage, scene, probe);
		});
		Optional<String> result = dialog.showAndWait();
	}

	/**
	 * Dialog zur Bestaetigung der dauerhaften Entnahme einer Probe.
	 */
	void dauerhaftEntnehmenDialog() {
		Dialog<String> dialog = new Dialog<>();
		dialog.setTitle("Probe dauerhaft entnehmen");
		
		UeberschriftUndButton bf = new UeberschriftUndButton("M\u00F6chten Sie die Probe wirklich" + "\n" + " dauerhaft entnehmen?",
				null, null);

		String[] buttons = { "Abbrechen", null, "Probe dauerhaft entnehmen"};
		ButtonRow buttonsBR = new ButtonRow(buttons);

		GridPane grid = new GridPane();
		grid.setPrefSize(1200, 500);
		grid.setHgap(10);
		grid.setVgap(10);
		grid.setPadding(new Insets(20, 35, 20, 35));
		grid.addRow(0, bf.getGridPane());
		grid.addRow(1, buttonsBR.getGridPane());
		dialog.getDialogPane().setContent(grid);
		
		float[] gewichte = {60.0f, 40.0f};
		GUIHelfer.setzeZeilenGewichte(grid, gewichte);
		GUIHelfer.setzeSpaltenGewichte(grid, GUIHelfer.gibGleicheGewichte(1));

		buttonsBR.getButton("Probe dauerhaft entnehmen").setOnAction(event -> {
			String stellplatz = probenSt.probeDauerhaftEntnehmen(probe);
			stellplatzDialog(stellplatz);
			dialog.setResult("");
			ProbeninformationenEntferntFormular pif = new ProbeninformationenEntferntFormular(stage, scene, probe);

		});
		buttonsBR.getButton( "Abbrechen").setOnAction(event -> {
			dialog.setResult("");
		});
		Optional<String> result = dialog.showAndWait();
	}

	/**
	 * Dialog zur Bestaetigung der dauerhaften Entnahme und Loeschen einer Probe.
	 */
	void dauerhaftEntnehmenUndLoeschenDialog() {
		Dialog<String> dialog = new Dialog<>();
		dialog.setTitle("Probe dauerhaft entnehmen und l\u00F6schen");
		UeberschriftUndButton bf = new UeberschriftUndButton(
				"M\u00F6chten Sie die Probe wirklich" + "\n" + "dauerhaft entnehmen und die" + "\n" + "Probe vollst\u00E4ndig aus dem" + "\n" + "System l\u00F6schen?",
				null, null);

		String[] buttons = { "Abbrechen", null, "Probe dauerhaft entnehmen und Probendaten l\u00F6schen"};
		ButtonRow buttonsBR = new ButtonRow(buttons);

		GridPane grid = new GridPane();
		grid.setPrefSize(1200, 500);
		grid.setHgap(10);
		grid.setVgap(10);
		grid.setPadding(new Insets(20, 35, 20, 35));
		grid.addRow(0 , bf.getGridPane());
		grid.addRow(1, buttonsBR.getGridPane());
		dialog.getDialogPane().setContent(grid);
		
		float[] gewichte = {60.0f, 40.0f};
		GUIHelfer.setzeZeilenGewichte(grid, gewichte);
		GUIHelfer.setzeSpaltenGewichte(grid, GUIHelfer.gibGleicheGewichte(1));

		buttonsBR.getButton("Probe dauerhaft entnehmen und Probendaten l\u00F6schen").setOnAction(event -> {
			String stellplatz = probenSt.probeDauerhaftEntnehmen(probe);
			probenSt.probeLoeschen(probe);
			stellplatzDialog(stellplatz);
			dialog.setResult("");
			ArrayList<Probe> probenAL = probenSt.getProben("", "", "", "", "");
			BiomaterialprobenSuchergebnisseFormular bsf = new BiomaterialprobenSuchergebnisseFormular(stage, scene, probenAL);
		});

		buttonsBR.getButton("Abbrechen").setOnAction(event -> {
			dialog.setResult("");
		});
		
		Optional<String> result = dialog.showAndWait();
	}

	/**
	 * Dialog zur Anzeige des Standorts der Probe, die wieder eingelagert werden soll.
	 */
	void wiederEinlagernDialog() {
		Dialog<String> dialog = new Dialog<>();
		dialog.setTitle("Probe wieder einlagern");
		
		Label label = new Label("Der zugeteilte Stellplatz ist: \n" + probenSt.probeZurueckLegen(probe));
		String[] buttons = {null, "Probe wieder hinzugef\u00FCgt" , null};
		ButtonRow wiederEingelagert = new ButtonRow(buttons);

		GridPane grid = new GridPane();
		grid.setPrefSize(1200, 500);
		grid.setHgap(10);
		grid.setVgap(10);
		grid.setPadding(new Insets(20, 35, 20, 35));
		grid.addRow(0, label);
		grid.addRow(1, wiederEingelagert.getGridPane());
		dialog.getDialogPane().setContent(grid);
		
		GUIHelfer.setzeZeilenGewichte(grid, GUIHelfer.gibGleicheGewichte(grid.getChildren().size()));
		GUIHelfer.setzeSpaltenGewichte(grid, GUIHelfer.gibGleicheGewichte(1));

		wiederEingelagert.getButton("Probe wieder hinzugef\u00FCgt").setOnAction(event -> {
			dialog.setResult("");
			ProbeninformationenEingelagertFormular pief = new ProbeninformationenEingelagertFormular(stage, scene, probe);
		});
		
		Optional<String> result = dialog.showAndWait();
	}

	/**
	 * Dialog zur Anzeige des Standorts einer Probe.
	 * @param stellplatz der Standort der Probe als String uebergeben
	 */
	void stellplatzDialog(String stellplatz) {
		Dialog<String> dialog = new Dialog<>();
		dialog.setTitle("Stellplatz der Probe");
		Label label = new Label("Der zugeteilte Stellplatz ist: \n" + stellplatz);
		String[] buttons = { null, "Okay", null};
		ButtonRow buttonsBR = new ButtonRow(buttons);
		
		GridPane grid = new GridPane();
		grid.setPrefSize(1200, 500);
		grid.setHgap(10);
		grid.setVgap(10);
		grid.setPadding(new Insets(20, 35, 20, 35));
		grid.addRow(0, label);
		grid.addRow(1, buttonsBR.getGridPane());
		dialog.getDialogPane().setContent(grid);
		
		float[] gewichte = {60.0f, 40.0f};
		GUIHelfer.setzeZeilenGewichte(grid, gewichte );
		GUIHelfer.setzeSpaltenGewichte(grid, GUIHelfer.gibGleicheGewichte(1));

		buttonsBR.getButton("Okay").setOnAction(event -> {
			dialog.setResult("");
		});
		
		Optional<String> result = dialog.showAndWait();
	}

	/**
	 * Dialog zur Anzeige des Standorts einer neu angelegten Probe.
	 * @param nachricht - Stellplatz der neu angelegten Probe.
	 */
	void probenAnlegenDialog(String nachricht) {
		Dialog<String> dialog = new Dialog<>();
		dialog.setTitle("Probe neu anlegen");
		UeberschriftUndButton bf = new UeberschriftUndButton(
				"Die Probe wurde mit den von" + "\n" + "Ihnen eingegebenen Daten neu" + "\n" + "im System angelegt.", null, null);
		
		Label label = new Label("Der zugeteilte Stellplatz ist: \n" + nachricht);
		String[] buttonStrings = {null, "Okay", null};
		ButtonRow eingelagert = new ButtonRow(buttonStrings);

		GridPane grid = new GridPane();
		grid.setPrefSize(1200, 500);
		grid.setHgap(10);
		grid.setVgap(10);
		grid.setPadding(new Insets(20, 35, 20, 35));
		grid.addRow(0, bf.getGridPane());
		grid.addRow(1, label);
		grid.addRow(2, eingelagert.getGridPane());

		float[] gewichte = {30.0f, 60.0f, 10.0f};
		GUIHelfer.setzeZeilenGewichte(grid, gewichte);
		GUIHelfer.setzeSpaltenGewichte(grid, GUIHelfer.gibGleicheGewichte(1));
		
		dialog.getDialogPane().setContent(grid);

		eingelagert.getButton("Okay").setOnAction(event -> {
			dialog.setResult("");
		});

		Optional<String> result = dialog.showAndWait();
	}

}