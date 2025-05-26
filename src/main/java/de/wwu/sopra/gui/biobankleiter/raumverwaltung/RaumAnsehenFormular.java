package de.wwu.sopra.gui.biobankleiter.raumverwaltung;

import de.wwu.sopra.controller.biobankleitercontroller.raumverwaltungscontroller.RaumAnsehenController;
import de.wwu.sopra.gui.helfer.ButtonRow;
import de.wwu.sopra.gui.helfer.GUIHelfer;
import de.wwu.sopra.gui.helfer.StackedLabelTextFields;
import de.wwu.sopra.gui.helfer.UeberschriftUndButton;
import de.wwu.sopra.model.Raum;
import de.wwu.sopra.steuerung.personalleiter.BenutzerSteuerung;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

/**
 * GUI Klasse zum erstellen des GUI Fensters zum Raum ansehen
 * 
 * @author Alexander Leifhelm, Sebastian Huck, Wael Al Allan
 */
public class RaumAnsehenFormular {
	private Stage stage;
	private Scene scene, oldScene;
	private GridPane gp;
	private UeberschriftUndButton kopfUUB;
	private ButtonRow buttonsBR;
	private StackedLabelTextFields eingabenSLTF;
	private Raum raum;

	/**
	 * Bei der Erzeugen des Objekts wird die Stage, Scene und Raum mit den
	 * uebergebenen Parametern erzeugt. Ausserdem wird die init- und die
	 * beabachteAktion-Methode aufgerufen. Des Weiteren wird die Scene gesetzt
	 * 
	 * @pre stage darf nicht null sein
	 * @pre oldScene darf nicht null sein
	 * @param stage    parent stage
	 * @param oldScene parent scene
	 * @param raum     Raum Objekt
	 */
	public RaumAnsehenFormular(Stage stage, Scene oldScene, Raum raum) {
		this.stage = stage;
		this.oldScene = oldScene;
		this.raum = raum;

		init();
		beobachteAktionen();
		stage.setScene(scene);
	}

	/**
	 * Initialisierungs-Methode zum initialisieren der GUI-ELemente.
	 */
	void init() {

		kopfUUB = new UeberschriftUndButton("Raum ansehen", "Hauptmen\u00FC", null);
		String[] labels = { "Name:", "Standort:", "Eingestellte Temperatur [\u00B0C]:" };
		eingabenSLTF = new StackedLabelTextFields(labels);
		eingabenSLTF.ansehenModus();
		eingabenSLTF.getTextField("Name:").setText("" + raum.getName());
		eingabenSLTF.getTextField("Standort:").setText("" + raum.getStandort());
		eingabenSLTF.getTextField("Eingestellte Temperatur [\u00B0C]:").setText("" + raum.getEinstellteTemperatur());

		String[] buttons = { "Abbrechen", null, "Bearbeiten" };
		buttonsBR = new ButtonRow(buttons);

		gp = new GridPane();
		gp.addRow(0, kopfUUB.getGridPane());
		gp.addRow(1, eingabenSLTF.getGridPane());
		gp.addRow(2, buttonsBR.getGridPane());

		BenutzerSteuerung bs = new BenutzerSteuerung();
		GUIHelfer.setzeZeilenGewichte(gp, GUIHelfer.gibGleicheGewichte(gp.getChildren().size()));
		GUIHelfer.setzeSpaltenGewichte(gp, GUIHelfer.gibGleicheGewichte(1));

		gp.setPadding(new Insets(10, 10, 10, 10));
		scene = new Scene(gp, 1280, 720);
	}

	/**
	 * Erstellt einen neuen RaumAnsehenController und setzt fuer bestimmte
	 * Klassenelemnte Button. Der Controller verarbeitet die jeweiligen Actions.
	 */
	void beobachteAktionen() {
		RaumAnsehenController rac = new RaumAnsehenController();
		kopfUUB.getButton("Hauptmen\u00FC").setOnAction(rac.getActionForHauptmenueButton(stage, oldScene));
		buttonsBR.getButton("Abbrechen").setOnAction(rac.getActionForAbbrechenButton(stage, oldScene));
		buttonsBR.getButton("Bearbeiten").setOnAction(rac.getActionForBearbeitenButton(stage, oldScene, raum));
	}

}