package de.wwu.sopra.gui.biobankleiter.behaeltertypverwaltung;

import java.util.ArrayList;

import de.wwu.sopra.controller.biobankleitercontroller.behaeltertypverwaltungcontroller.BehaeltertypMenueController;
import de.wwu.sopra.gui.helfer.ButtonRow;
import de.wwu.sopra.gui.helfer.GUIHelfer;
import de.wwu.sopra.gui.helfer.ListViewUndButtons;
import de.wwu.sopra.gui.helfer.UeberschriftUndButton;
import de.wwu.sopra.model.ProbenBehaelterTyp;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

/**
 * GUI Klasse zum erstellen des GUI Fensters Behaeltertyp Menues
 * 
 * @author Alexander Leifhelm, Sebastian Huck, Wael Al Allan
 */
public class BehaeltertypMenueFormular {
	private Stage stage;
	private Scene scene;
	private Scene oldScene;
	private GridPane gp;
	private UeberschriftUndButton kopfUUB;
	private ButtonRow filternBR;
	private ListViewUndButtons<ProbenBehaelterTyp> listeLVUB;

	/**
	 * Konstruktor der Klasse
	 * 
	 * @pre stage darf nicht null sein
	 * @pre oldScene darf nicht null sein
	 * @param stage            bekommt das aktive Fenster uebergeben
	 * @param oldScene         bekommt das zuletzt offene Fenster ebenfalls
	 *                         uebergeben
	 * @param behaelterTypenAL bekommt eine ArrayList an anzuzeigenden Behaeltern
	 */
	public BehaeltertypMenueFormular(Stage stage, Scene oldScene, ArrayList<ProbenBehaelterTyp> behaelterTypenAL) {
		this.stage = stage;
		this.oldScene = oldScene;

		init(behaelterTypenAL);
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
	private void init(ArrayList<ProbenBehaelterTyp> behaelterTypenAL) {
		kopfUUB = new UeberschriftUndButton("Beh\u00E4ltertypen-\u00DCbersicht", "Hauptmen\u00FC", null);
		String[] buttonFilternLabel = { null, "Filtern" };
		filternBR = new ButtonRow(buttonFilternLabel);

		listeLVUB = GUIHelfer.<ProbenBehaelterTyp>getStandardListViewUndButtons(null, null, behaelterTypenAL, true,
				true);

		gp = new GridPane();
		gp.addRow(0, kopfUUB.getGridPane());
		gp.addRow(1, filternBR.getGridPane());
		gp.addRow(2, listeLVUB.getGridPane());

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
		BehaeltertypMenueController controller = new BehaeltertypMenueController();
		kopfUUB.getButton("Hauptmen\u00FC").setOnAction(controller.getActionForHauptmenueButton(stage, oldScene));
		filternBR.getButton("Filtern").setOnAction(controller.getActionForFilternButton(stage, scene));
		listeLVUB.getButton("Weiter").setOnAction(controller.getActionForWeiterButton(stage, scene, listeLVUB));
		listeLVUB.getButton("Hinzuf\u00FCgen").setOnAction(controller.getActionForHinzufuegenButton(stage, scene));
	}
}
