package de.wwu.sopra.gui.alleuser;

import de.wwu.sopra.controller.allgemein.RollenController;
import de.wwu.sopra.gui.helfer.ButtonRow;
import de.wwu.sopra.gui.helfer.GUIHelfer;
import de.wwu.sopra.gui.helfer.UeberschriftUndButton;
import de.wwu.sopra.model.Benutzer;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

/**
 * Klasse die GUI Fenster der Rollenauswahl erstellt
 * 
 * @author Alexander Leifhelm, Sebastian Huck, Wael Al Allan
 */
public class RollenFormular {

	private Benutzer benutzer;
	private Stage stage;
	private Scene scene;
	private UeberschriftUndButton ueberschrift;
	private ButtonRow buttonsBR1;
	private ButtonRow buttonsBR2;
	private GridPane gp;

	/**
	 * Konstruktor zum erstellen der Klasse, bekommt die Stage uebergeben.
	 * 
	 * @pre stage darf nicht null sein
	 * @pre oldScene darf nicht null sein
	 * @param stage    das aktive Fenster wird als Parameter uebergeben.
	 * @param oldScene die vorherige Scene wird uebergeben
	 * @param b        der aktive Benutzer wird weitergegeben
	 */
	public RollenFormular(Stage stage, Scene oldScene, Benutzer b) {
		this.benutzer = b;
		this.stage = stage;
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
		ueberschrift = new UeberschriftUndButton("Biobank Login", null, "Abmelden");
		String[] buttonRow1 = { null, "MTA", null, "Study Nurse", null };
		String[] buttonRow2 = { null, "Biobankleiter", null, "Personalleiter", null };
		buttonsBR1 = new ButtonRow(buttonRow1);
		buttonsBR2 = new ButtonRow(buttonRow2);
		buttonsBR1.getButton("MTA").setDisable(!(benutzer.isMTA()));
		buttonsBR1.getButton("Study Nurse").setDisable(!(benutzer.isStudyNurse()));
		buttonsBR2.getButton("Biobankleiter").setDisable(!(benutzer.isBiobankLeiter()));
		buttonsBR2.getButton("Personalleiter").setDisable(!(benutzer.isPersonalLeiter()));

		GridPane gp = new GridPane();
		gp.addRow(0, ueberschrift.getGridPane());
		gp.addRow(1, buttonsBR1.getGridPane());
		gp.addRow(2, buttonsBR2.getGridPane());

		GUIHelfer.setzeZeilenGewichte(gp, GUIHelfer.gibGleicheGewichte(gp.getChildren().size()));
		GUIHelfer.setzeSpaltenGewichte(gp, GUIHelfer.gibGleicheGewichte(1));

		gp.setPadding(new Insets(10, 10, 10, 10));
		scene = new Scene(gp, 1280, 720);
	}

	/**
	 * Erstelen von Buttonfunktion wird in die Controllerklasse ausgelagerrt
	 */
	private void beobachteAktion() {

		RollenController controller = new RollenController();

		buttonsBR2.getButton("Personalleiter").setOnAction(controller.getActionForPersonalleiterButton(stage, scene));
		buttonsBR2.getButton("Biobankleiter").setOnAction(controller.getActionForBiobankleiter(stage, scene));
		buttonsBR1.getButton("MTA").setOnAction(controller.getActionForMTA(stage, scene));
		buttonsBR1.getButton("Study Nurse").setOnAction(controller.getActionForStudyNurse(stage, scene));
		ueberschrift.getButton("Abmelden").setOnAction(controller.getActionForAbmelden(stage));
	}

}
