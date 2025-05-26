package de.wwu.sopra.gui.biobankleiter.behaeltertypverwaltung;

import de.wwu.sopra.controller.biobankleitercontroller.behaeltertypverwaltungcontroller.BehaeltertypAnsehenController;
import de.wwu.sopra.gui.helfer.ButtonRow;
import de.wwu.sopra.gui.helfer.GUIHelfer;
import de.wwu.sopra.gui.helfer.StackedLabelTextFields;
import de.wwu.sopra.gui.helfer.UeberschriftUndButton;
import de.wwu.sopra.model.ProbenBehaelterTyp;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

/**
 * GUI Klasse zum erstellen des GUI Fensters Behaeltertyp ansehen
 * 
 * @author Alexander Leifhelm, Sebastian Huck, Wael Al Allan
 */
public class BehaeltertypAnsehenFormular {
	private Stage stage;
	private Scene scene, oldScene;
	private ProbenBehaelterTyp behaelterTyp;
	private UeberschriftUndButton kopfUUB;
	private StackedLabelTextFields eingabenSLTF;
	private ButtonRow buttonsBR;
	private GridPane gp;

	/**
	 * Konstruktor der Klasse mit zugehoerigen Parametern
	 * 
	 * @pre stage darf nicht null sein
	 * @pre oldScene darf nicht null sein
	 * @param stage        das aktive Fenster wird uebergeben
	 * @param oldScene     das zuletzt geoeffnete Fenster wir mit uebergeben
	 * @param behaelterTyp der anzusehende Behaeltertyp wird ebenfalls uebergeben
	 */
	public BehaeltertypAnsehenFormular(Stage stage, Scene oldScene, ProbenBehaelterTyp behaelterTyp) {
		this.stage = stage;
		this.oldScene = oldScene;
		this.behaelterTyp = behaelterTyp;

		init();
		beobachteAktionen();

		stage.setScene(scene);
	}

	/**
	 * Initialisierungsmethode der Scene, hierzu werden die Hilfsklassen benutzt um
	 * die Klassenattribute zu initialisieren, welche dann automatisch GridPanes
	 * erstellen, welche dann in eine weitere GridPane gesetzt werden. Dann werden
	 * Gewichte gesetzt und und die Scene wird initialisiert damit das neue Fenster
	 * angezeigt werden kann.
	 */
	private void init() {
		kopfUUB = new UeberschriftUndButton("Beh\u00E4ltertyp ansehen", "Hauptmen\u00FC", null);
		String[] labels = { "Name:", "Volumen [cm^3]:", "H\u00F6he [cm]:", "Durchmesser [cm]:", "Deckel-Typ:" };
		eingabenSLTF = new StackedLabelTextFields(labels);
		eingabenSLTF.ansehenModus();
		eingabenSLTF.getTextField("Name:").setText(behaelterTyp.getName());
		eingabenSLTF.getTextField("Volumen [cm^3]:").setText("" + behaelterTyp.getVolumen());
		eingabenSLTF.getTextField("H\u00F6he [cm]:").setText("" + behaelterTyp.getHoehe());
		eingabenSLTF.getTextField("Durchmesser [cm]:").setText("" + behaelterTyp.getDurchmesser());
		eingabenSLTF.getTextField("Deckel-Typ:").setText("" + behaelterTyp.getVolumen());

		String[] buttonLabels = { "Abbrechen", null, null };
		buttonsBR = new ButtonRow(buttonLabels);

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
	 * Die Funktionalitaet der Buttons wird auf eine Hilfscontrollerklasse
	 * ausgelagert
	 */
	private void beobachteAktionen() {
		BehaeltertypAnsehenController controller = new BehaeltertypAnsehenController();
		kopfUUB.getButton("Hauptmen\u00FC").setOnAction(controller.getActionForHauptmenueButton(stage, oldScene));
		buttonsBR.getButton("Abbrechen").setOnAction(controller.getActionForAbbrechenButton(stage, oldScene));
	}

}
