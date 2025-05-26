package de.wwu.sopra.gui.biobankleiter.raumverwaltung;

import de.wwu.sopra.controller.biobankleitercontroller.raumverwaltungscontroller.RaumHinzufuegenController;
import de.wwu.sopra.gui.helfer.ButtonRow;
import de.wwu.sopra.gui.helfer.GUIHelfer;
import de.wwu.sopra.gui.helfer.StackedLabelTextFields;
import de.wwu.sopra.gui.helfer.UeberschriftUndButton;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

/**
 * Klasse zur Erstellung eines Raumes.
 * 
 * @author Alexander Leifhelm, Sebastian Huck, Wael Al Allan
 *
 */
public class RaumHinzufuegenFormular {

	private Stage stage;
	private Scene scene, oldScene;
	private GridPane gp;
	private UeberschriftUndButton kopfUUB;
	private ButtonRow buttonsBR;
	private StackedLabelTextFields eingabenSLTF;

	/**
	 * Bei der Erzeugen des Objekts wird die Stage, Scene mit den uebergebenen
	 * Parametern erzeugt. Ausserdem wird die init- und die beabachteAktion-Methode
	 * aufgerufen. Des Weiteren wird die Scene gesetzt.
	 * 
	 * @pre stage darf nicht null sein
	 * @pre oldScene darf nicht null sein
	 * @param stage    parent stage
	 * @param oldScene parent scene
	 */
	public RaumHinzufuegenFormular(Stage stage, Scene oldScene) {
		this.stage = stage;
		this.oldScene = oldScene;

		init();
		beobachteAktionen();
		stage.setScene(scene);
	}

	/**
	 * Initialisierungs-Methode zum initialisieren der GUI-ELemente.
	 */
	void init() {

		kopfUUB = new UeberschriftUndButton("Neuen Raum hinzuf\u00FCgen", "Hauptmen\u00FC", null);
		String[] labels = { "Name:", "Standort:", "Eingestellte Temperatur [\u00B0C]:" };
		eingabenSLTF = new StackedLabelTextFields(labels);
		String[] buttons = { "Abbrechen", null, "Best\u00E4tigen" };
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
	 * Erstellt einen neuen RaumHinzufuegenController und setzt fuer bestimmte
	 * Klassenelemnte Button. Der Controller verarbeitet die jeweiligen Actions.
	 */
	void beobachteAktionen() {
		RaumHinzufuegenController rhc = new RaumHinzufuegenController();
		kopfUUB.getButton("Hauptmen\u00FC").setOnAction(rhc.getActionForHauptmenueButton(stage, oldScene));
		buttonsBR.getButton("Abbrechen").setOnAction(rhc.getActionForAbbrechenButton(stage, oldScene));
		buttonsBR.getButton("Best\u00E4tigen")
				.setOnAction(rhc.getActionForBestaetigenButton(stage, oldScene, eingabenSLTF));
	}

}
