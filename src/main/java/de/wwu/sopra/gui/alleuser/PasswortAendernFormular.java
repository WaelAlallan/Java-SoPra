package de.wwu.sopra.gui.alleuser;

import de.wwu.sopra.controller.allgemein.PasswortAendernController;
import de.wwu.sopra.gui.helfer.ButtonRow;
import de.wwu.sopra.gui.helfer.GUIHelfer;
import de.wwu.sopra.gui.helfer.StackedLabelTextFields;
import de.wwu.sopra.gui.helfer.UeberschriftUndButton;
import de.wwu.sopra.model.Benutzer;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.PasswordField;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

/**
 * GUI Klasse zum erstellen des GUI Fensters Passwort aendern
 * 
 * @author Alexander Leifhelm, Sebastian Huck, Wael Al Allan
 */
public class PasswortAendernFormular {

	private Stage stage;
	private Scene scene;
	private Scene oldScene;

	private UeberschriftUndButton ueberschrift;
	private StackedLabelTextFields eingabenSLTF;
	private ButtonRow buttonsBR;

	private GridPane gp;
	private Benutzer benutzer;

	/**
	 * Konstruktor zum erstellen der Klasse, bekommt die Stage uebergeben.
	 * 
	 * @pre stage darf nicht null sein
	 * @pre oldScene darf nicht null sein
	 * @param stage    das aktive Fenster wird als Parameter uebergeben.
	 * @param oldscene Das zuletzt offene Fenster, relevant fuer abbrechen
	 * @param benutzer Der Benutzer zu dem das Passwort geaendert werden soll
	 */
	public PasswortAendernFormular(Stage stage, Scene oldscene, Benutzer benutzer) {

		this.stage = stage;
		this.oldScene = oldscene;
		this.benutzer = benutzer;
		init();
		beobachteAktion();
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
		ueberschrift = new UeberschriftUndButton("Passwort \u00C4ndern", null, null);
		String[] labels = { "Altes Passwort", "Neues Passwort", "Neues Passwort best\u00e4tigen" };
		eingabenSLTF = new StackedLabelTextFields(labels);
		eingabenSLTF.setTextField(0, new PasswordField());
		eingabenSLTF.setTextField(1, new PasswordField());
		eingabenSLTF.setTextField(2, new PasswordField());
		String[] labels2 = { "Abbrechen", null, "Best\u00e4tigen" };
		buttonsBR = new ButtonRow(labels2);

		gp = new GridPane();
		gp.addRow(0, ueberschrift.getGridPane());
		gp.addRow(1, eingabenSLTF.getGridPane());
		gp.addRow(2, buttonsBR.getGridPane());

		GUIHelfer.setzeZeilenGewichte(gp, GUIHelfer.gibGleicheGewichte(gp.getChildren().size()));
		GUIHelfer.setzeSpaltenGewichte(gp, GUIHelfer.gibGleicheGewichte(1));

		gp.setPadding(new Insets(10, 10, 10, 10));
		scene = new Scene(gp, 1280, 720);
	}

	/**
	 * Erstelen von Buttonfunktion wird auf COntroller ausgelagert
	 */
	private void beobachteAktion() {

		PasswortAendernController controller = new PasswortAendernController();
		buttonsBR.getButton("Abbrechen").setOnAction(new EventHandler<ActionEvent>() {
			/**
			 * Methode zur Beschreibung der Aktion in einem anderen Thread
			 * @param e ActionEvent der Aktion
			 */
			public void handle(ActionEvent e) {
				stage.setScene(oldScene);
			}

		});

		buttonsBR.getButton("Best\u00e4tigen")
				.setOnAction(controller.getActionForBestaetigen(stage, oldScene, eingabenSLTF));

	}

}
