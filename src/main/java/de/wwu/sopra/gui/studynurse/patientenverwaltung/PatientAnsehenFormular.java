package de.wwu.sopra.gui.studynurse.patientenverwaltung;

import java.util.ArrayList;

import de.wwu.sopra.gui.helfer.ButtonRow;
import de.wwu.sopra.gui.helfer.GUIHelfer;
import de.wwu.sopra.gui.helfer.ListViewUndButtons;
import de.wwu.sopra.gui.helfer.StackedLabelTextFields;
import de.wwu.sopra.gui.helfer.StudieDatumListeObjekt;
import de.wwu.sopra.gui.helfer.UeberschriftUndButton;
import de.wwu.sopra.gui.studynurse.StudyNurseMenueFormular;
import de.wwu.sopra.model.Patient;
import de.wwu.sopra.model.Studie;
import de.wwu.sopra.steuerung.studynurse.PatientenSteuerung;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import javafx.util.Pair;

/**
 * Klasse zum erstellen des GUI-Fensters Patient ansehen
 * 
 * @author Alexander Leifhelm
 */
public class PatientAnsehenFormular {
	private Stage stage;
	private Scene scene;
	private Scene oldScene;
	private GridPane gp;

	private UeberschriftUndButton ueberschrift;
	private StackedLabelTextFields sltf;
	private ListViewUndButtons<StudieDatumListeObjekt> listeLVUB;
	private ButtonRow buttons;
	private Patient patient;
	private PatientenSteuerung ps;

	/**
	 * Konstruktor zum Erstellen der Klasse, bekommt die Stage, das alte Fenster und
	 * einen Patienten uebergeben.
	 * 
	 * @pre stage darf nicht null sein
	 * @pre oldScene darf nicht null sein
	 * @param stage    das aktive Fenster wird als Parameter uebergeben.
	 * @param oldScene das alte Fenster
	 * @param patient  der angesehen werden soll
	 */
	public PatientAnsehenFormular(Stage stage, Scene oldScene, Patient patient) {
		this.stage = stage;
		this.oldScene = oldScene;
		this.patient = patient;

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
		ps = new PatientenSteuerung();
		ueberschrift = new UeberschriftUndButton("Patient ansehen", "Hauptmen\u00FC", null);
		String[] labels = { "Vorname:", "Nachname:", "Adresse:" };
		sltf = new StackedLabelTextFields(labels);
		sltf.getTextField("Vorname:").setText(patient.getVorname());
		sltf.getTextField("Nachname:").setText(patient.getNachname());
		sltf.getTextField("Adresse:").setText(patient.getAdresse());
		sltf.ansehenModus();

		// TODO: Steuerung hinzufuegen
		ArrayList<Pair<Studie, String>> studienDatumAL = ps.getStudienMitDatum(patient);
		ArrayList<StudieDatumListeObjekt> listenObjekteAL = StudieDatumListeObjekt
				.getAlsStudieDatumListeObjekte(studienDatumAL);
		listeLVUB = GUIHelfer.<StudieDatumListeObjekt>getStandardListViewUndButtons("Zugeh\u00F6rige Studien", null,
				listenObjekteAL, false, false);

		String[] labels2 = { "Abbrechen", null, "Bearbeiten" };
		buttons = new ButtonRow(labels2);

		gp = new GridPane();
		gp.addRow(0, ueberschrift.getGridPane());
		gp.addRow(1, sltf.getGridPane());
		gp.addRow(2, listeLVUB.getGridPane());
		gp.addRow(3, buttons.getGridPane());

		GUIHelfer.setzeZeilenGewichte(gp, GUIHelfer.gibGleicheGewichte(gp.getChildren().size()));
		GUIHelfer.setzeSpaltenGewichte(gp, GUIHelfer.gibGleicheGewichte(1));

		gp.setPadding(new Insets(10, 10, 10, 10));
		scene = new Scene(gp, 1280, 720);
	}

	/**
	 * Funktionalitaet der Buttons wird gesetzt.
	 */
	private void beobachteAktionen() {
		ueberschrift.getButton("Hauptmen\u00FC").setOnAction(new EventHandler<ActionEvent>() {
			/**
			 * Methode zur Beschreibung der Aktion in einem anderen Thread
			 * @param e ActionEvent der Aktion
			 */
			public void handle(ActionEvent e) {
				StudyNurseMenueFormular snmf = new StudyNurseMenueFormular(stage, scene);
			}
		});

		buttons.getButton("Abbrechen").setOnAction(new EventHandler<ActionEvent>() {
			/**
			 * Methode zur Beschreibung der Aktion in einem anderen Thread
			 * @param e ActionEvent der Aktion
			 */
			public void handle(ActionEvent e) {
				// TODO: Steuerung hinzufuegen
				ArrayList<Patient> gefundenePatienten = ps.getPatienten("", "", "");
				PatientMenueFormular pmf = new PatientMenueFormular(stage, scene, gefundenePatienten);
			}
		});

		buttons.getButton("Bearbeiten").setOnAction(new EventHandler<ActionEvent>() {
			/**
			 * Methode zur Beschreibung der Aktion in einem anderen Thread
			 * @param e ActionEvent der Aktion
			 */
			public void handle(ActionEvent e) {
				PatientBearbeitenFormular pbf = new PatientBearbeitenFormular(stage, scene, patient);
			}
		});

	}
}
