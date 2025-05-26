package de.wwu.sopra.gui.biobankleiter.raumverwaltung;

import de.wwu.sopra.controller.biobankleitercontroller.raumverwaltungscontroller.RaumBearbeitenController;
import de.wwu.sopra.gui.helfer.ButtonRow;
import de.wwu.sopra.gui.helfer.GUIHelfer;
import de.wwu.sopra.gui.helfer.StackedLabelTextFields;
import de.wwu.sopra.gui.helfer.UeberschriftUndButton;
import de.wwu.sopra.model.Raum;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

/**
 * Klasse fuer das Formular zum Bearbeiten eines Raumes.
 * 
 * @author Alexander Leifhelm, Sebastian Huck, Wael Al Allan
 *
 */
public class RaumBearbeitenFormular {
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
	public RaumBearbeitenFormular(Stage stage, Scene oldScene, Raum raum) {
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

		kopfUUB = new UeberschriftUndButton("Raum bearbeiten", "Hauptmen\u00FC", null);
		String[] labels = { "Name:", "Standort:", "Eingestellte Temperatur [\u00B0C]:" };
		eingabenSLTF = new StackedLabelTextFields(labels);
		eingabenSLTF.getTextField("Name:").setText("" + raum.getName());
		eingabenSLTF.getTextField("Standort:").setText("" + raum.getStandort());
		eingabenSLTF.getTextField("Eingestellte Temperatur [\u00B0C]:").setText("" + raum.getEinstellteTemperatur());

		String[] buttons = { "Abbrechen", "L\u00F6schen", "Best\u00E4tigen" };
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
	 * Erstellt einen neuen RaumBearbeitenController und setzt fuer bestimmte
	 * Klassenelemnte Button. Der Controller verarbeitet die jeweiligen Actions.
	 */
	void beobachteAktionen() {
		RaumBearbeitenController rbc = new RaumBearbeitenController();
		kopfUUB.getButton("Hauptmen\u00FC").setOnAction(rbc.getActionForHauptmenueButton(stage, oldScene));
		buttonsBR.getButton("Abbrechen").setOnAction(rbc.getActionForAbbrechenButton(stage, oldScene));
		buttonsBR.getButton("L\u00F6schen").setOnAction(rbc.getActionForLoeschenButton(stage, oldScene, raum));
		buttonsBR.getButton("Best\u00E4tigen")
				.setOnAction(rbc.getActionForBestaetigenButton(stage, oldScene, raum, eingabenSLTF));
	}

}
