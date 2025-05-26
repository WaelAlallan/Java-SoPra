package de.wwu.sopra.gui.personalleiter;

import de.wwu.sopra.controller.personalleitercontroller.BenutzerBearbeitenController;
import de.wwu.sopra.gui.helfer.ButtonRow;
import de.wwu.sopra.gui.helfer.GUIHelfer;
import de.wwu.sopra.gui.helfer.StackedLabelTextFields;
import de.wwu.sopra.gui.helfer.UeberschriftUndButton;
import de.wwu.sopra.model.Benutzer;
import de.wwu.sopra.steuerung.personalleiter.BenutzerSteuerung;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

/**
 * Klasse zum erstellen des GUI-Fensters Benutzer bearbeiten
 */
public class BenutzerBearbeitenFormular {
	private Stage stage;
	private Scene scene;
	private Scene oldScene;
	private Benutzer benutzer;
	private BenutzerSteuerung benutzerBS;
	private UeberschriftUndButton kopfUUB;
	private StackedLabelTextFields eingabenSLTF;
	private Label rollenLabel;
	private CheckBox mtaCB;
	private CheckBox studyNurseCB;
	private CheckBox bioBankLeiterCB;
	private CheckBox personalLeiterCB;
	private GridPane checkBoxesGP;
	private ButtonRow buttonsBR;
	private GridPane gp;

	/**
	 * Konstruktor zum erstellen der Klasse, bekommt die Stage, das alte Fenster und
	 * einen Benutzer uebergeben.
	 * 
	 * @pre stage darf nicht null sein
	 * @pre oldScene darf nicht null sein
	 * @param stage    das aktive Fenster wird als Parameter uebergeben.
	 * @param oldScene das alte Fenster
	 * @param benutzer der angesehen werden soll
	 */
	public BenutzerBearbeitenFormular(Stage stage, Scene oldScene, Benutzer benutzer) {
		this.stage = stage;
		this.oldScene = oldScene;
		this.benutzer = benutzer;

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
		benutzerBS = new BenutzerSteuerung();
		kopfUUB = new UeberschriftUndButton("Benutzer bearbeiten", "Hauptmen\u00FC", null);
		String[] labels = { "Vorname:", "Nachname:" };
		eingabenSLTF = new StackedLabelTextFields(labels);
		eingabenSLTF.getTextField("Vorname:").setText(benutzer.getVorname());
		eingabenSLTF.getTextField("Nachname:").setText(benutzer.getNachname());
		// eingabenSLTF.getTextField("Passwort:").setText(benutzer.getPasswordHash().getHashAsString());

		rollenLabel = new Label("Rollen:");
		mtaCB = new CheckBox("MTA");
		mtaCB.setDisable(false);
		mtaCB.setMaxWidth(Double.MAX_VALUE);
		mtaCB.setSelected(benutzer.isMTA());
		studyNurseCB = new CheckBox("Study Nurse");
		studyNurseCB.setDisable(false);
		studyNurseCB.setMaxWidth(Double.MAX_VALUE);
		studyNurseCB.setSelected(benutzer.isStudyNurse());
		bioBankLeiterCB = new CheckBox("Biobank-Leiter");
		bioBankLeiterCB.setDisable(false);
		bioBankLeiterCB.setMaxWidth(Double.MAX_VALUE);
		bioBankLeiterCB.setSelected(benutzer.isBiobankLeiter());
		personalLeiterCB = new CheckBox("Personal-Leiter");
		personalLeiterCB.setDisable(false);
		personalLeiterCB.setMaxWidth(Double.MAX_VALUE);
		personalLeiterCB.setSelected(benutzer.isPersonalLeiter());

		checkBoxesGP = new GridPane();
		checkBoxesGP.add(rollenLabel, 0, 0);
		checkBoxesGP.add(mtaCB, 1, 0);
		checkBoxesGP.add(studyNurseCB, 2, 0);
		checkBoxesGP.add(bioBankLeiterCB, 3, 0);
		checkBoxesGP.add(personalLeiterCB, 4, 0);
		checkBoxesGP.setAlignment(Pos.CENTER);

		float[] gewichteCB = { 30.0f, 17.5f, 17.5f, 17.5f, 17.5f };
		GUIHelfer.setzeSpaltenGewichte(checkBoxesGP, gewichteCB);

		String[] buttonLabels = { "Abbrechen", "L\u00F6schen", "Best\u00E4tigen" };
		buttonsBR = new ButtonRow(buttonLabels);

		gp = new GridPane();
		gp.addRow(0, kopfUUB.getGridPane());
		gp.addRow(1, eingabenSLTF.getGridPane());
		gp.addRow(2, checkBoxesGP);
		gp.addRow(3, buttonsBR.getGridPane());

		GUIHelfer.setzeZeilenGewichte(gp, GUIHelfer.gibGleicheGewichte(gp.getChildren().size()));
		GUIHelfer.setzeSpaltenGewichte(gp, GUIHelfer.gibGleicheGewichte(1));

		gp.setPadding(new Insets(10, 10, 10, 10));
		scene = new Scene(gp, 1280, 720);
	}

	/**
	 * Funktionalitaet der Buttons wird in die Controllerklassen ausgelagert.
	 */
	void beobachteAktionen() {
		BenutzerBearbeitenController controller = new BenutzerBearbeitenController();

		buttonsBR.getButton("Abbrechen").setOnAction(controller.getActionEventForAbbrechen(stage, oldScene));

		buttonsBR.getButton("L\u00F6schen")
				.setOnAction(controller.getActionEventForLoeschen(stage, oldScene, benutzer));

		buttonsBR.getButton("Best\u00E4tigen").setOnAction(controller.getActionEventForBestaetigen(stage, oldScene,
				benutzer, eingabenSLTF, studyNurseCB, mtaCB, bioBankLeiterCB, personalLeiterCB));

		kopfUUB.getButton("Hauptmen\u00FC").setOnAction(controller.getActionEventForHauptmenue(stage, oldScene));

	}

}
