package de.wwu.sopra.gui.personalleiter;

import de.wwu.sopra.controller.personalleitercontroller.BenutzerMenueController;
import de.wwu.sopra.gui.helfer.ButtonRow;
import de.wwu.sopra.gui.helfer.GUIHelfer;
import de.wwu.sopra.gui.helfer.ListViewUndButtons;
import de.wwu.sopra.gui.helfer.StackedLabelTextFields;
import de.wwu.sopra.gui.helfer.UeberschriftUndButton;
import de.wwu.sopra.model.Benutzer;
import de.wwu.sopra.steuerung.personalleiter.BenutzerSteuerung;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

/**
 * Klasse zum erstellen des GUI-Fensters der Benutzeruebersicht
 */
public class BenutzerMenueFormular {
	private Stage stage;
	private Scene scene, oldScene;

	private TextField suchenTF;
	private GridPane gp;
	private UeberschriftUndButton kopfUUB;
	private ButtonRow suchenBR;
	private StackedLabelTextFields eingabenSLTF;

	private ListViewUndButtons<Benutzer> listeLVUB;

	/**
	 * Konstruktor zum erstellen der Klasse, bekommt die Stage und das alte Fenster
	 * uebergeben.
	 * 
	 * @pre stage darf nicht null sein
	 * @pre oldScene darf nicht null sein
	 * @param stage    das aktive Fenster wird als Parameter uebergeben.
	 * @param oldScene das alte Fenster
	 */
	public BenutzerMenueFormular(Stage stage, Scene oldScene) {
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
	void init() {

		kopfUUB = new UeberschriftUndButton("Benutzer\u00FCbersicht", "Hauptmen\u00FC", null);
		String[] labels = { "Suchen Vorname:", "Suchen Nachname:" };
		eingabenSLTF = new StackedLabelTextFields(labels);
		String[] buttonSuchenLabel = { null, "Suchen" };
		suchenBR = new ButtonRow(buttonSuchenLabel);

		gp = new GridPane();
		gp.addRow(0, kopfUUB.getGridPane());
		gp.addRow(1, eingabenSLTF.getGridPane());
		gp.addRow(2, suchenBR.getGridPane());

		BenutzerSteuerung bs = new BenutzerSteuerung();
		listeLVUB = GUIHelfer.<Benutzer>getStandardListViewUndButtons(null, null, bs.getBenutzer(), false, true);
		gp.addRow(3, listeLVUB.getGridPane());

		GUIHelfer.setzeZeilenGewichte(gp, GUIHelfer.gibGleicheGewichte(gp.getChildren().size()));
		GUIHelfer.setzeSpaltenGewichte(gp, GUIHelfer.gibGleicheGewichte(1));

		gp.setPadding(new Insets(10, 10, 10, 10));
		scene = new Scene(gp, 1280, 720);
	}

	/**
	 * Funktionalitaet der Buttons wird in die Controllerklassen ausgelagert.
	 */
	void beobachteAktionen() {
		BenutzerMenueController controller = new BenutzerMenueController();
		kopfUUB.getButton("Hauptmen\u00FC").setOnAction(controller.getActionEventForHauptmenue(stage, scene));

		suchenBR.getButton("Suchen")
				.setOnAction(controller.getActionEventForSuchen(stage, scene, eingabenSLTF, listeLVUB));

		listeLVUB.getButton("Weiter").setOnAction(controller.getActionEventForWeiter(stage, scene, listeLVUB));

	}

}
