package de.wwu.sopra.gui.alleuser;

import de.wwu.sopra.controller.allgemein.AnmeldenController;
import de.wwu.sopra.gui.helfer.ButtonRow;
import de.wwu.sopra.gui.helfer.GUIHelfer;
import de.wwu.sopra.gui.helfer.StackedLabelTextFields;
import de.wwu.sopra.gui.helfer.UeberschriftUndButton;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.PasswordField;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

/**
 * Klasse zum erstellen des GUI-Fensters Anmeldenformular
 * 
 * @author Alexander Leifhelm, Sebastian Huck, Wael Al Allan
 */
public class AnmeldenFormular {

	private Stage stage;
	private Scene s;
	private StackedLabelTextFields eingabenSLTF;
	private UeberschriftUndButton ueberschrift;
	private ButtonRow buttonsBR;
	private GridPane gp;

	/**
	 * Konstruktor zum erstellen der Klasse, bekommt die Stage uebergeben.
	 * 
	 * @pre stage darf nicht null sein
	 * @pre oldScene darf nicht null sein
	 * @param stage das aktive Fenster wird als Parameter uebergeben.
	 */
	public AnmeldenFormular(Stage stage) {
		this.stage = stage;
		init();
		beobachteAktion();
		stage.setScene(s);
	}

	/**
	 * Initialisierungsmethode der Scene, hierzu werden die Hilfsklassen benutzt um
	 * die Klassenattribute zu initialisieren, welche dann automatisch GridPanes
	 * erstellen, welche dann in eine weitere GridPane gesetzt werden. Dann werden
	 * Gewichte gesetzt und und die Scene wird initialisiert damit das neue Fenster
	 * angezeigt werden kann.
	 */
	private void init() {

		ueberschrift = new UeberschriftUndButton("Biobank Login", null, null);
		String[] labels = { "Benutzername:", "Passwort:" };
		eingabenSLTF = new StackedLabelTextFields(labels);
		eingabenSLTF.setTextField(1, new PasswordField());
		String[] buttonLabels = { null, null, "Login" };
		buttonsBR = new ButtonRow(buttonLabels);

		gp = new GridPane();
		gp.addRow(0, ueberschrift.getGridPane());
		gp.addRow(1, eingabenSLTF.getGridPane());
		gp.addRow(2, buttonsBR.getGridPane());

		GUIHelfer.setzeZeilenGewichte(gp, GUIHelfer.gibGleicheGewichte(gp.getChildren().size()));
		GUIHelfer.setzeSpaltenGewichte(gp, GUIHelfer.gibGleicheGewichte(1));

		gp.setPadding(new Insets(10, 10, 10, 10));
		s = new Scene(gp, 1280, 720);
	}

	/**
	 * Erstelen von Buttonfunktion
	 */
	private void beobachteAktion() {

		AnmeldenController controller = new AnmeldenController();
		buttonsBR.getButton("Login").setOnAction(controller.getActionForLogin(stage, s, eingabenSLTF));

	}
}