package de.wwu.sopra.gui.studynurse.patientenverwaltung;

import java.util.ArrayList;
import java.util.Optional;

import de.wwu.sopra.gui.helfer.ButtonRow;
import de.wwu.sopra.gui.helfer.GUIHelfer;
import de.wwu.sopra.gui.helfer.ListViewUndButtons;
import de.wwu.sopra.gui.helfer.StackedLabelTextFields;
import de.wwu.sopra.gui.helfer.StudieDatumListeObjekt;
import de.wwu.sopra.gui.helfer.UeberschriftUndButton;
import de.wwu.sopra.gui.studynurse.StudyNurseMenueFormular;
import de.wwu.sopra.model.Patient;
import de.wwu.sopra.model.Studie;
import de.wwu.sopra.steuerung.biobankleiter.StudienSteuerung;
import de.wwu.sopra.steuerung.studynurse.PatientenSteuerung;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import javafx.util.Pair;

/**
 * Klasse zum erstellen des GUI-Fensters Patient bearbeiten
 * 
 * @author Alexander Leifhelm
 */
public class PatientBearbeitenFormular {
	private Stage stage;
	private Scene scene;
	private Scene oldScene;
	private GridPane gp;

	private UeberschriftUndButton ueberschrift;
	private StackedLabelTextFields sltf;
	private ListViewUndButtons<StudieDatumListeObjekt> listeLVUB;
	private ArrayList<Pair<Studie, String>> studienDatumAL;
	private ButtonRow buttons;
	private Patient patient;
	private PatientenSteuerung ps;

	/**
	 * Konstruktor zum erstellen der Klasse, bekommt die Stage, das alte Fenster und
	 * eine Patient uebergeben.
	 * 
	 * @pre stage darf nicht null sein
	 * @pre oldScene darf nicht null sein
	 * @param stage    das aktive Fenster wird als Parameter uebergeben.
	 * @param oldScene das alte Fenster
	 * @param patient  der bearbeitet werden soll
	 */
	public PatientBearbeitenFormular(Stage stage, Scene oldScene, Patient patient) {
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
		ueberschrift = new UeberschriftUndButton("Patient bearbeiten", "Hauptmen\u00FC", null);
		String[] labels = { "Vorname:", "Nachname:", "Adresse:" };
		sltf = new StackedLabelTextFields(labels);
		sltf.getTextField("Vorname:").setText(patient.getVorname());
		sltf.getTextField("Nachname:").setText(patient.getNachname());
		sltf.getTextField("Adresse:").setText(patient.getAdresse());
		sltf.bearbeitenModus();

		studienDatumAL = ps.getStudienMitDatum(patient);
		ArrayList<StudieDatumListeObjekt> listenObjekteAL = StudieDatumListeObjekt
				.getAlsStudieDatumListeObjekte(studienDatumAL);
		listeLVUB = GUIHelfer.<StudieDatumListeObjekt>getIndivListViewUndButtons("Zugeh\u00F6rige Studien", null,
				listenObjekteAL, "Studie hinzuf\u00FCgen", "Studie l\u00f6schen");

		String[] labels2 = { "Abbrechen", "L\u00F6schen", "Best\u00E4tigen" };
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
		PatientenSteuerung ps = new PatientenSteuerung();
		StudienSteuerung ss = new StudienSteuerung();

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
				PatientAnsehenFormular pmf = new PatientAnsehenFormular(stage, scene, patient);
			}
		});

		buttons.getButton("L\u00F6schen").setOnAction(new EventHandler<ActionEvent>() {
			/**
			 * Methode zur Beschreibung der Aktion in einem anderen Thread
			 * @param e ActionEvent der Aktion
			 */
			public void handle(ActionEvent e) {
				try {
					Alert alert = new Alert(AlertType.CONFIRMATION);
					alert.setHeaderText("Bitte best\u00e4tigen!");
					alert.setContentText("Sind sie sicher, dass der Patient gel\u00f6scht werden soll?");

					Optional<ButtonType> result = alert.showAndWait();
					if (result.get() == ButtonType.OK) {
						ps.patientLoeschen(patient.getVorname(), patient.getNachname(), patient.getAdresse());
						// TODO: Steuerung hinzufuegen
						ArrayList<Patient> gefundenePatienten = ps.getPatienten("", "", "");
						PatientMenueFormular pbf = new PatientMenueFormular(stage, scene, gefundenePatienten);
					}

				} catch (Exception exception) {
					GUIHelfer.AutomatischesFehlermeldungFormular("Loeschen", exception);
				}

			}
		});

		buttons.getButton("Best\u00E4tigen").setOnAction(new EventHandler<ActionEvent>() {
			/**
			 * Methode zur Beschreibung der Aktion in einem anderen Thread
			 * @param e ActionEvent der Aktion
			 */
			public void handle(ActionEvent e) {
				try {
					String vorname = sltf.getText("Vorname:");
					String nachname = sltf.getText("Nachname:");
					String adresse = sltf.getText("Adresse:");
					ps.patientBearbeiten(patient, vorname, nachname, adresse, studienDatumAL);
					PatientAnsehenFormular pbf = new PatientAnsehenFormular(stage, scene, patient);
				} catch (Exception exception) {
					GUIHelfer.AutomatischesFehlermeldungFormular("Bearbeiten", exception);
				}
			}
		});

		listeLVUB.getButton("Studie hinzuf\u00FCgen").setOnAction(new EventHandler<ActionEvent>() {
			/**
			 * Methode zur Beschreibung der Aktion in einem anderen Thread
			 * @param e ActionEvent der Aktion
			 */
			public void handle(ActionEvent e) {
				ArrayList<Studie> studien = ss.getStudien("", "");
				StudieWaehlenFormular swf = new StudieWaehlenFormular(studien);
				Optional<Pair<Studie, String>> auswahl = swf.showAndWait();

				if (auswahl.isPresent()) {
					if ((auswahl.get().getKey() != null) && (auswahl.get().getValue() != null)) {
						studienDatumAL.add(auswahl.get());
						listeLVUB.updateListe(StudieDatumListeObjekt.getAlsStudieDatumListeObjekte(studienDatumAL));
					}
				}
			}
		});

		listeLVUB.getButton("Studie l\u00f6schen").setOnAction(new EventHandler<ActionEvent>() {
			/**
			 * Methode zur Beschreibung der Aktion in einem anderen Thread
			 * @param e ActionEvent der Aktion
			 */
			public void handle(ActionEvent e) {
				try {
					Studie studie = listeLVUB.getSelektiertesObjekt().getStudieDatumPaar().getKey();
					PatientenSteuerung ps = new PatientenSteuerung();
					ps.loescheStudievonPatient(patient, studie);
					PatientBearbeitenFormular pbf = new PatientBearbeitenFormular(stage, scene, patient);

				} catch (NullPointerException nullE) {

					GUIHelfer.FehlermeldungFormular("Fehler!", "Es wurde keine Studie angeklickt!",
							"Bitte klicken Sie eine Studie an, um diesen \u00FCber 'Studie loeschen' loeschen zu koennen");
				}
					catch (Exception exception) {
						GUIHelfer.AutomatischesFehlermeldungFormular("Loeschen", exception);
					}

			}
		});

	}
}
