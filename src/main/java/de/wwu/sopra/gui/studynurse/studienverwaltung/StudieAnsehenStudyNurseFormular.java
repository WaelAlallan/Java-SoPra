package de.wwu.sopra.gui.studynurse.studienverwaltung;

import java.util.ArrayList;

import de.wwu.sopra.gui.helfer.ButtonRow;
import de.wwu.sopra.gui.helfer.GUIHelfer;
import de.wwu.sopra.gui.helfer.ListViewUndButtons;
import de.wwu.sopra.gui.helfer.StackedLabelTextFields;
import de.wwu.sopra.gui.helfer.UeberschriftUndButton;
import de.wwu.sopra.gui.studynurse.StudyNurseMenueFormular;
import de.wwu.sopra.model.Studie;
import de.wwu.sopra.model.VisitenVorlage;
import de.wwu.sopra.steuerung.biobankleiter.StudienSteuerung;
import de.wwu.sopra.steuerung.studynurse.StudieEinsehenSteuerung;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

/**
 * Klasse zu erstellen des GUI-Fensters Studie einsehen der StudyNurse
 * 
 * @author Sebastian Huck
 */
public class StudieAnsehenStudyNurseFormular {

	private Stage stage;
	private Scene scene;
	private Scene oldScene;
	private GridPane gp;

	private UeberschriftUndButton ueberschrift;
	private StackedLabelTextFields sltf;
	private ListViewUndButtons<Studie> list;
	private ButtonRow buttons;
	private Studie studie;

	/**
	 * Konstruktor zum erstellen der Klasse, bekommt die Stage, das alte Fenster und
	 * eine Studie uebergeben.
	 * 
	 * @pre stage darf nicht null sein
	 * @pre oldScene darf nicht null sein
	 * @param stage    das aktive Fenster wird als Parameter uebergeben.
	 * @param oldScene das alte Fenster
	 * @param studie   die angesehen werden soll
	 */
	public StudieAnsehenStudyNurseFormular(Stage stage, Scene oldScene, Studie studie) {

		this.stage = stage;
		this.studie = studie;
		this.oldScene = oldScene;

		init();
		beobachten();
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

		ueberschrift = new UeberschriftUndButton("Studie ansehen", "Hauptmen\u00FC", null);
		String[] labels = { "Name:", "Gewuenschte Anz. \nTeilnehmer:" };
		sltf = new StackedLabelTextFields(labels);
		sltf.getTextField("Name:").setText(studie.getName());
		sltf.getTextField("Gewuenschte Anz. \nTeilnehmer:").setText(Integer.toString(studie.getAnzahlTeilnehmer()));
		sltf.ansehenModus();

		list = GUIHelfer.<VisitenVorlage>getStandardListViewUndButtons("Geplante Visiten", null,
				studie.getVisitenVorlage(), false, false);

		String[] labels2 = { "Abbrechen", null, null };
		buttons = new ButtonRow(labels2);

		gp = new GridPane();
		gp.addRow(0, ueberschrift.getGridPane());
		gp.addRow(1, sltf.getGridPane());
		gp.addRow(2, list.getGridPane());
		gp.addRow(3, buttons.getGridPane());

		GUIHelfer.setzeZeilenGewichte(gp, GUIHelfer.gibGleicheGewichte(gp.getChildren().size()));
		GUIHelfer.setzeSpaltenGewichte(gp, GUIHelfer.gibGleicheGewichte(1));

		gp.setPadding(new Insets(10, 10, 10, 10));
		scene = new Scene(gp, 1280, 720);
	}

	/**
	 * funktionalitaet der Buttons wird gesetzt.
	 */

	private void beobachten() {

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
				StudieEinsehenSteuerung ses = new StudieEinsehenSteuerung();
				ArrayList<Studie> studien = ses.getStudien("", "");
				StudienUebersichtStudyNurseFormular susnf = new StudienUebersichtStudyNurseFormular(stage, studien);
			}
		});
	}

}
