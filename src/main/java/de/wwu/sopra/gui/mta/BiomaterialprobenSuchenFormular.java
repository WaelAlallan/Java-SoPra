package de.wwu.sopra.gui.mta;

import java.util.ArrayList;

import de.wwu.sopra.gui.helfer.ButtonRow;
import de.wwu.sopra.gui.helfer.GUIHelfer;
import de.wwu.sopra.gui.helfer.StackedLabelTextFields;
import de.wwu.sopra.gui.helfer.UeberschriftUndButton;
import de.wwu.sopra.model.Probe;
import de.wwu.sopra.steuerung.mta.ProbenSteuerung;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

/**
 * Diese Klasse dient zur Suche einer Probe im System, anhand der eingegebenen
 * Parameter.
 * 
 * @author Wael Alallan
 */
public class BiomaterialprobenSuchenFormular {
	private Stage stage;
	private Scene scene, oldScene;
	private GridPane gp;
	private UeberschriftUndButton kopfUUB;
	private ButtonRow buttonsBR;
	private StackedLabelTextFields eingabenSLTF;

	/**
	 * Bei der Erzeugen dieses Objekts wird die Stage, Scene mit den uebergebenen
	 * Parametern erzeugt. Ausserdem wird die init- und die beabachteAktion-Methode
	 * aufgerufen. Des Weiteren wird die Scene gesetzt.
	 * 
	 * @pre stage darf nicht null sein
	 * @pre oldScene darf nicht null sein
	 * @param stage    - parent stage
	 * @param oldScene - parent scene
	 */
	public BiomaterialprobenSuchenFormular(Stage stage, Scene oldScene) {
		this.stage = stage;
		this.oldScene = oldScene;

		init();
		beobachteAktionen();
		stage.setScene(scene);
	}

	/**
	 * Initialisierungs-Methode zum initialisieren der GUI-ELemente.
	 */
	private void init() {

		kopfUUB = new UeberschriftUndButton("Biomaterialproben suchen", "Hauptmen\u00FC", null);
		String[] labels = { "Biomaterialproben-Kategorie:", "Zugeh\u00F6rigeStudie:", "Visitennummer:", "Proben-Barcode",
				"Entnahmedatum" };
		eingabenSLTF = new StackedLabelTextFields(labels);
		String[] buttons = { "Abbrechen", null, "Suchen" };
		buttonsBR = new ButtonRow(buttons);

		gp = new GridPane();
		gp.addRow(0, kopfUUB.getGridPane());
		gp.addRow(1, eingabenSLTF.getGridPane());
		gp.addRow(2, buttonsBR.getGridPane());

		GUIHelfer.setzeZeilenGewichte(gp, GUIHelfer.gibGleicheGewichte(gp.getChildren().size()));
		GUIHelfer.setzeSpaltenGewichte(gp, GUIHelfer.gibGleicheGewichte(1));

		gp.setPadding(new Insets(10, 10, 10, 10));
		scene = new Scene(gp, 1280, 720);
	}

	/**
	 * Je nachdem, was der Benutzer betaetigt, wird man entweder ins MTA-hauptmenue
	 * oder in die vorherige Scene gelangen. Des Weiteren kann man anhand der
	 * eingegebenen Daten (wenn sie richtig sind) zur Suchergebnisseformular
	 * gelangen.
	 */
	private void beobachteAktionen() {

		kopfUUB.getButton("Hauptmen\u00FC").setOnAction(event -> {
			MtaMenueFormular mmf = new MtaMenueFormular(stage, scene);
		});
		buttonsBR.getButton("Abbrechen").setOnAction(event -> {
			stage.setScene(oldScene);
		});
		buttonsBR.getButton("Suchen").setOnAction(event -> {
			ProbenSteuerung probenSt = new ProbenSteuerung();
			
			String biomaterialprobenKategorie= eingabenSLTF.getText("Biomaterialproben-Kategorie:");
			String zugehoerigeStudie = eingabenSLTF.getText("Zugeh\u00F6rigeStudie:");

			String visitennummer = eingabenSLTF.getText("Visitennummer:");
			String probenBarcode = eingabenSLTF.getText("Proben-Barcode");
			String entnahmedatum = eingabenSLTF.getText("Entnahmedatum");

			try {
				ArrayList<Probe> probenAL = probenSt.getProben(biomaterialprobenKategorie, zugehoerigeStudie,
						visitennummer, probenBarcode, entnahmedatum);
				BiomaterialprobenSuchergebnisseFormular rmf = new BiomaterialprobenSuchergebnisseFormular(stage, scene,
						probenAL);
			} catch (Exception exception) {
				GUIHelfer.FehlermeldungFormular("Fehler!", "Fehler beim Suchen einer Biomaterialprobe!",
						"Beim Suchen der Probe ist ein Fehler aufgetreten." + "\n"
								+ "\u00DCberpr\u00DCfen Sie Ihre Eingaben auf G\u00FCltigkeit!");
			}
		});
	}

}