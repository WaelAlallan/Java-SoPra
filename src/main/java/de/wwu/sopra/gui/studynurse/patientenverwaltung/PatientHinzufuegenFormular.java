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
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import javafx.util.Pair;

/**
 * Klasse zum erstellen des GUI-Fensters Patient hinzufuegen
 * 
 * @author Alexander Leifhelm
 */
public class PatientHinzufuegenFormular {
	private Stage stage;
	private Scene scene;
	private Scene oldScene;
	private GridPane gp;

	private UeberschriftUndButton ueberschrift;
	private StackedLabelTextFields eingabenSLTF;
	private ListViewUndButtons<StudieDatumListeObjekt> listeLVUB;
	private ButtonRow buttons;
	private ArrayList<Pair<Studie, String>> studienDatumAL;

	/**
	 * Konstruktor zum erstellen der Klasse, bekommt die Stage, das alte Fenster und
	 * eine Patient uebergeben.
	 * 
	 * @pre stage darf nicht null sein
	 * @pre oldScene darf nicht null sein
	 * @param stage    das aktive Fenster wird als Parameter uebergeben.
	 * @param oldScene das alte Fenster
	 */
	public PatientHinzufuegenFormular(Stage stage, Scene oldScene) {
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
		ueberschrift = new UeberschriftUndButton("Patient hinzuf\u00FCgen", "Hauptmen\u00FC", null);
		String[] labels = { "Vorname:", "Nachname:", "Adresse:" };
		eingabenSLTF = new StackedLabelTextFields(labels);
		eingabenSLTF.bearbeitenModus();

		studienDatumAL = new ArrayList<Pair<Studie, String>>();
		ArrayList<StudieDatumListeObjekt> listenObjekteAL = StudieDatumListeObjekt
				.getAlsStudieDatumListeObjekte(studienDatumAL);
		listeLVUB = GUIHelfer.<StudieDatumListeObjekt>getStandardListViewUndButtons("Zugeh\u00F6rige Studien", null,
				listenObjekteAL, true, false);

		String[] labels2 = { "Abbrechen", null, "Hinzuf\u00FCgen" };
		buttons = new ButtonRow(labels2);

		gp = new GridPane();
		gp.addRow(0, ueberschrift.getGridPane());
		gp.addRow(1, eingabenSLTF.getGridPane());
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
				ArrayList<Patient> gefundenePatienten = ps.getPatienten("", "", "");
				PatientMenueFormular pmf = new PatientMenueFormular(stage, scene, gefundenePatienten);
			}
		});

		buttons.getButton("Hinzuf\u00FCgen").setOnAction(new EventHandler<ActionEvent>() {
			/**
			 * Methode zur Beschreibung der Aktion in einem anderen Thread
			 * @param e ActionEvent der Aktion
			 */
			public void handle(ActionEvent e) {
				String vorname = eingabenSLTF.getText("Vorname:");
				String nachname = eingabenSLTF.getText("Nachname:");
				String adresse = eingabenSLTF.getText("Adresse:");

				try {
					ps.patientHinzufuegen(vorname, nachname, adresse, studienDatumAL);
					ArrayList<Patient> gefundenePatienten = ps.getPatienten("", "", "");
					PatientMenueFormular pbf = new PatientMenueFormular(stage, scene, gefundenePatienten);
				} catch (Exception exception) {
					GUIHelfer.AutomatischesFehlermeldungFormular("Hinzufuegen", exception);
				}
			}
		});

		listeLVUB.getButton("Hinzuf\u00FCgen").setOnAction(new EventHandler<ActionEvent>() {
			/**
			 * Methode zur Beschreibung der Aktion in einem anderen Thread
			 * @param e ActionEvent der Aktion
			 */
			public void handle(ActionEvent e) {
				ArrayList<Studie> studien = ss.getStudien("", "");
				StudieWaehlenFormular swf = new StudieWaehlenFormular(studien);
				Optional<Pair<Studie, String>> auswahl = swf.showAndWait();

				if (auswahl.isPresent()) {
					Pair<Studie, String> auswahlStudieDatum = auswahl.get();
					if ((auswahlStudieDatum.getKey() != null) && (auswahlStudieDatum.getValue() != null)) {
						studienDatumAL.add(auswahl.get());
						listeLVUB.updateListe(StudieDatumListeObjekt.getAlsStudieDatumListeObjekte(studienDatumAL));
					}
				}
			}
		});

	}
}
