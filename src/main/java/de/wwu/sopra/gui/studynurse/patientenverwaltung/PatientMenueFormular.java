package de.wwu.sopra.gui.studynurse.patientenverwaltung;

import java.util.ArrayList;

import de.wwu.sopra.gui.helfer.ButtonRow;
import de.wwu.sopra.gui.helfer.GUIHelfer;
import de.wwu.sopra.gui.helfer.ListViewUndButtons;
import de.wwu.sopra.gui.helfer.StackedLabelTextFields;
import de.wwu.sopra.gui.helfer.UeberschriftUndButton;
import de.wwu.sopra.gui.studynurse.StudyNurseMenueFormular;
import de.wwu.sopra.model.Patient;
import de.wwu.sopra.steuerung.studynurse.PatientenSteuerung;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

/**
 * Klasse fuer das Formular zur Verwaltung der Patienten
 * 
 * @author Alexander Leifhelm
 *
 */
public class PatientMenueFormular {
	
	private Stage stage;
	private Scene scene, oldScene;

	private TextField suchenTF;
	private GridPane gp;
	private UeberschriftUndButton kopfUUB;
	private ButtonRow filternBR;
	private StackedLabelTextFields eingabenSLTF;
	private ListViewUndButtons<Patient> listeLVUB;

	/**
	 * Konstruktor zum erstellen der Klasse, bekommt die Stage und das alte Fenster
	 * uebergeben.
	 * 
	 * @pre stage darf nicht null sein
	 * @pre oldScene darf nicht null sein
	 * @param stage     das aktive Fenster wird als Parameter uebergeben.
	 * @param oldScene  das alte Fenster
	 * @param patientAL Liste der Patienten
	 */
	public PatientMenueFormular(Stage stage, Scene oldScene, ArrayList<Patient> patientAL) {
		this.stage = stage;
		this.oldScene = oldScene;

		init(patientAL);
		beobachteAktionen();
		stage.setScene(scene);
	}

	/**
	 * Initialisierungsmethode der Scene, hierzu werden die Hilfsklassen benutzt um
	 * die Klassenattribute zu initialisieren, welche dann automatisch GridPanes
	 * erstellen, welche dann in eine weitere GridPane gesetzt werden. Dann werden
	 * Gewichte gesetzt und und die Scene wird initialisiert damit das neue Fenster
	 * angezeigt werden kann.
	 * 
	 * @param patientAL Anzuzeigenden Patienten als Arraylist
	 */
	void init(ArrayList<Patient> patientAL) {

		kopfUUB = new UeberschriftUndButton("Patienten\u00FCbersicht", "Hauptmen\u00FC", null);
		String[] buttonFilternLabel = { null, "Suchen" };
		filternBR = new ButtonRow(buttonFilternLabel);
		String[] labels = { "Suchen Vorname:", "Suchen Nachname:", "Suchen Studienname:" };
		eingabenSLTF = new StackedLabelTextFields(labels);

		gp = new GridPane();
		gp.addRow(0, kopfUUB.getGridPane());
		gp.addRow(1, eingabenSLTF.getGridPane());
		gp.addRow(2, filternBR.getGridPane());

		listeLVUB = GUIHelfer.<Patient>getStandardListViewUndButtons(null, null, patientAL, true, true);
		gp.addRow(3, listeLVUB.getGridPane());

		GUIHelfer.setzeZeilenGewichte(gp, GUIHelfer.gibGleicheGewichte(gp.getChildren().size()));
		GUIHelfer.setzeSpaltenGewichte(gp, GUIHelfer.gibGleicheGewichte(1));

		gp.setPadding(new Insets(10, 10, 10, 10));
		scene = new Scene(gp, 1280, 720);
	}

	/**
	 * Funktionalitaet der Buttons wird gesetzt.
	 */
	void beobachteAktionen() {
		PatientenSteuerung ps = new PatientenSteuerung();

		kopfUUB.getButton("Hauptmen\u00FC").setOnAction(new EventHandler<ActionEvent>() {
			@Override /**
						 * Enqueues an action to be performed on a different thread than your own
						 */
			public void handle(ActionEvent e) {
				StudyNurseMenueFormular blmf = new StudyNurseMenueFormular(stage, scene);
			}
		});

		listeLVUB.getButton("Hinzuf\u00FCgen").setOnAction(new EventHandler<ActionEvent>() {
			@Override /**
						 * Enqueues an action to be performed on a different thread than your own
						 */
			public void handle(ActionEvent e) {
				PatientHinzufuegenFormular paf = new PatientHinzufuegenFormular(stage, scene);
			}
		});

		listeLVUB.getButton("Weiter").setOnAction(new EventHandler<ActionEvent>() {
			@Override /**
						 * Enqueues an action to be performed on a different thread than your own
						 */
			public void handle(ActionEvent e) {
				Patient patient = listeLVUB.getSelektiertesObjekt();
				if (patient != null) {
					PatientAnsehenFormular baf = new PatientAnsehenFormular(stage, scene, patient);
				} else {
					GUIHelfer.AutomatischesFehlermeldungFormular("Auswahl", null);
				}
			}
		});

		filternBR.getButton("Suchen").setOnAction(new EventHandler<ActionEvent>() {
			@Override /**
						 * Enqueues an action to be performed on a different thread than your own
						 */
			public void handle(ActionEvent e) {
				String vorname = eingabenSLTF.getText("Suchen Vorname:");
				String nachname = eingabenSLTF.getText("Suchen Nachname:");
				String studieName = eingabenSLTF.getText("Suchen Studienname:");

				ArrayList<Patient> gefundenePatienten = ps.getPatienten(vorname, nachname, studieName);
				listeLVUB.updateListe(gefundenePatienten);
			}
		});

	}

}
