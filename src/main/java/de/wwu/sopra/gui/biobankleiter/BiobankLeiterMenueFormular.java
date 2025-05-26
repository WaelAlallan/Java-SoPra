package de.wwu.sopra.gui.biobankleiter;

import de.wwu.sopra.controller.biobankleitercontroller.BiobankLeiterMenueController;
import de.wwu.sopra.gui.helfer.ButtonColumn;
import de.wwu.sopra.gui.helfer.GUIHelfer;
import de.wwu.sopra.gui.helfer.UeberschriftUndButton;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

/**
 * GUI Klasse zum erstellen des Biobankleiter Menues
 * 
 * @author Alexander Leifhelm, Sebastian Huck, Wael Al Allan
 */
public class BiobankLeiterMenueFormular {
	private Stage stage;
	private Scene scene, oldScene;
	private GridPane gp;
	private UeberschriftUndButton kopfUUB;
	private ButtonColumn menueBC;

	/**
	 * Kontruktor setzt das aktive Fenster
	 * 
	 * @pre stage darf nicht null sein
	 * @pre oldScene darf nicht null sein
	 * @param stage    das aktive Fenster wir uebergeben
	 * @param oldScene die zuletzt aufgerufene Scene wird uebergeben
	 */
	public BiobankLeiterMenueFormular(Stage stage, Scene oldScene) {
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
	private void init() {
		kopfUUB = new UeberschriftUndButton("Biobank-Leiter*In-Men\u00FC", "Passwort \u00E4ndern", "Abmelden");
		String[] menueButtonLabels = { "Studien", "R\u00E4ume", "K\u00Fchlschr\u00E4nke", "Probenkategorie",
				"Beh\u00E4ltertyp" };
		menueBC = new ButtonColumn(menueButtonLabels);

		gp = new GridPane();
		gp.addRow(0, kopfUUB.getGridPane());
		gp.addRow(1, menueBC.getGridPane());

		float[] zeilenGewichte = { 20.0f, 80.0f };
		GUIHelfer.setzeZeilenGewichte(gp, zeilenGewichte);
		GUIHelfer.setzeSpaltenGewichte(gp, GUIHelfer.gibGleicheGewichte(1));

		gp.setPadding(new Insets(10, 10, 10, 10));
		scene = new Scene(gp, 1280, 720);
	}

	/**
	 * Buttonfunktionalitaet wird auf eine Controllerklasse ausgelagert
	 */
	private void beobachteAktionen() {
		BiobankLeiterMenueController controller = new BiobankLeiterMenueController();
		kopfUUB.getButton("Passwort \u00E4ndern")
				.setOnAction(controller.getActionForPasswortAendernButton(stage, scene));
		kopfUUB.getButton("Abmelden").setOnAction(controller.getActionForAbmeldenButton(stage, scene));
		menueBC.getButton("Studien").setOnAction(controller.getActionForStudienButton(stage, scene));
		menueBC.getButton("R\u00E4ume").setOnAction(controller.getActionForRaeumeButton(stage, scene));
		menueBC.getButton("K\u00Fchlschr\u00E4nke")
				.setOnAction(controller.getActionForKuehlschraenkeButton(stage, scene));
		menueBC.getButton("Probenkategorie").setOnAction(controller.getActionForProbenkategorieButton(stage, scene));
		menueBC.getButton("Beh\u00E4ltertyp").setOnAction(controller.getActionForBehaeltertypButton(stage, scene));
	}
}
