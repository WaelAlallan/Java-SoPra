package de.wwu.sopra.gui.studynurse;

import java.util.ArrayList;

import de.wwu.sopra.gui.alleuser.AnmeldenFormular;
import de.wwu.sopra.gui.alleuser.PasswortAendernFormular;
import de.wwu.sopra.gui.helfer.ButtonColumn;
import de.wwu.sopra.gui.helfer.GUIHelfer;
import de.wwu.sopra.gui.helfer.UeberschriftUndButton;
import de.wwu.sopra.gui.studynurse.patientenverwaltung.PatientMenueFormular;
import de.wwu.sopra.gui.studynurse.studienverwaltung.StudienUebersichtStudyNurseFormular;
import de.wwu.sopra.gui.studynurse.visitenverwaltung.VisiteMenueFormular;
import de.wwu.sopra.model.Patient;
import de.wwu.sopra.model.Studie;
import de.wwu.sopra.model.Visite;
import de.wwu.sopra.steuerung.alleuser.AuthentifizierungSteuerung;
import de.wwu.sopra.steuerung.studynurse.PatientenSteuerung;
import de.wwu.sopra.steuerung.studynurse.StudieEinsehenSteuerung;
import de.wwu.sopra.steuerung.studynurse.VisitenSteuerung;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

/**
 * GUI Klasse zum erstellen des Study Nurse Menue-Fensters 
 * @author Gruppe 01
 */
public class StudyNurseMenueFormular {

	private Stage stage;
	private Scene scene;
	private Scene oldScene;
	private GridPane gp;
	private UeberschriftUndButton kopfUUB;
	private ButtonColumn menueBC;

	/**
	 * Konstruktor der Klasse ruft init, beobachte aktion auf und 
	 * setzt danach das fenster
	 * @param stage das aktive Fenster wird uebergeben
	 * @param oldScene die zuletzt offene Scene wird uebergeben
	 */
	public StudyNurseMenueFormular(Stage stage, Scene oldScene) {
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
		kopfUUB = new UeberschriftUndButton("Study Nurse Men\u00FC", "Passwort \u00E4ndern", "Abmelden");
		String[] menueButtonLabels = { "Studien", "Patienten", "Visiten" };
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
		kopfUUB.getButton("Passwort \u00E4ndern").setOnAction(new EventHandler<ActionEvent>() {
			
			/**
			 * Methode zur Beschreibung der Aktion in einem anderen Thread
			 * @param e ActionEvent der Aktion
			 */
			public void handle(ActionEvent e) {
				PasswortAendernFormular paf = new PasswortAendernFormular(stage, scene,
						AuthentifizierungSteuerung.getInstance().getAktiverBenutzer());
			}
		});

		kopfUUB.getButton("Abmelden").setOnAction(new EventHandler<ActionEvent>() {

			/**
			 * Methode zur Beschreibung der Aktion in einem anderen Thread
			 * @param e ActionEvent der Aktion
			 */
			public void handle(ActionEvent e) {
				AnmeldenFormular af = new AnmeldenFormular(stage);
			}
		});

		menueBC.getButton("Patienten").setOnAction(new EventHandler<ActionEvent>() {

			/**
			 * Methode zur Beschreibung der Aktion in einem anderen Thread
			 * @param e ActionEvent der Aktion
			 */
			public void handle(ActionEvent e) {
				PatientenSteuerung ps = new PatientenSteuerung();
				ArrayList<Patient> gefundenePatienten = ps.getPatienten("", "", "");
				PatientMenueFormular pmf = new PatientMenueFormular(stage, scene, gefundenePatienten);
			}
		});

		kopfUUB.getButton("Passwort \u00E4ndern").setOnAction(new EventHandler<ActionEvent>() {

			/**
			 * Methode zur Beschreibung der Aktion in einem anderen Thread
			 * @param e ActionEvent der Aktion
			 */
			public void handle(ActionEvent e) {
				PasswortAendernFormular pwaf = new PasswortAendernFormular(stage, scene,
						AuthentifizierungSteuerung.getInstance().getAktiverBenutzer());
			}
		});

		kopfUUB.getButton("Abmelden").setOnAction(new EventHandler<ActionEvent>() {

			/**
			 * Methode zur Beschreibung der Aktion in einem anderen Thread
			 * @param e ActionEvent der Aktion
			 */
			public void handle(ActionEvent e) {
				AnmeldenFormular amf = new AnmeldenFormular(stage);
			}
		});

		menueBC.getButton("Studien").setOnAction(new EventHandler<ActionEvent>() {

			/**
			 * Methode zur Beschreibung der Aktion in einem anderen Thread
			 * @param e ActionEvent der Aktion
			 */
			public void handle(ActionEvent e) {
				StudieEinsehenSteuerung ses = new StudieEinsehenSteuerung();
				ArrayList<Studie> studien = ses.getStudien("", "");
				StudienUebersichtStudyNurseFormular susnf = new StudienUebersichtStudyNurseFormular(stage, studien);
			}
		});

		menueBC.getButton("Visiten").setOnAction(new EventHandler<ActionEvent>() {

			/**
			 * Methode zur Beschreibung der Aktion in einem anderen Thread
			 * @param e ActionEvent der Aktion
			 */
			public void handle(ActionEvent e) {
				VisitenSteuerung vs = new VisitenSteuerung();
				ArrayList<Visite> visitenAL = vs.visitenSuchen("", "", "", "", null);
				VisiteMenueFormular vmf = new VisiteMenueFormular(stage, scene, visitenAL);
			}
		});

	}

}
