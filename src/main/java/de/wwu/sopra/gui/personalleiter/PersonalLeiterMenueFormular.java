package de.wwu.sopra.gui.personalleiter;

import de.wwu.sopra.controller.personalleitercontroller.PersonalLeiterMenueController;
import de.wwu.sopra.gui.helfer.ButtonColumn;
import de.wwu.sopra.gui.helfer.GUIHelfer;
import de.wwu.sopra.gui.helfer.UeberschriftUndButton;
import de.wwu.sopra.model.Benutzer;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

/**
 * Klasse zum erstellen des GUI-Fensters Hauptmenue des Personalleiters
 */
public class PersonalLeiterMenueFormular {
	private Stage stage;
	private Scene scene, oldScene;
	private GridPane gp;
	private UeberschriftUndButton kopfUUB;
	private ButtonColumn menueButtonsBC;
	private Benutzer benutzer;

	/**
	 * Konstruktor zum erstellen der Klasse, bekommt die Stage und das alte Fenster
	 * uebergeben.
	 * 
	 * @pre stage darf nicht null sein
	 * @pre oldScene darf nicht null sein
	 * @param stage    das aktive Fenster wird als Parameter uebergeben.
	 * @param oldScene das alte Fenster
	 */
	public PersonalLeiterMenueFormular(Stage stage, Scene oldScene) {
		this.stage = stage;
		this.oldScene = oldScene;

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
	public void init() {
		kopfUUB = new UeberschriftUndButton("Personalleiter*In-Men\u00FC", "Passwort \u00E4ndern", "Abmelden");
		String[] bodenStrings = { "Benutzer verwalten", "Benutzer registrieren" };
		menueButtonsBC = new ButtonColumn(bodenStrings);

		gp = new GridPane();
		gp.addRow(0, kopfUUB.getGridPane());
		gp.addRow(1, menueButtonsBC.getGridPane());

		float[] zeilenGewichte = { 20.0f, 80.0f };
		GUIHelfer.setzeZeilenGewichte(gp, zeilenGewichte);
		GUIHelfer.setzeSpaltenGewichte(gp, GUIHelfer.gibGleicheGewichte(1));

		gp.setPadding(new Insets(10, 10, 10, 10));
		scene = new Scene(gp, 1280, 720);
	}

	/**
	 * Funktionalitaet der Buttons wird in die Controllerklassen ausgelagert.
	 */
	void beobachteAktionen() {
		PersonalLeiterMenueController controller = new PersonalLeiterMenueController();
		kopfUUB.getButton("Abmelden").setOnAction(controller.getActionEventForAbmelden(stage));
		kopfUUB.getButton("Passwort \u00E4ndern")
				.setOnAction(controller.getActionEventForPWaendern(stage, scene, benutzer));
		menueButtonsBC.getButton("Benutzer registrieren")
				.setOnAction(controller.getActionEventForBenutzerRegistrieren(stage, scene));
		menueButtonsBC.getButton("Benutzer verwalten")
				.setOnAction(controller.getActionEventForBenutzerMenue(stage, scene));
	}

}
