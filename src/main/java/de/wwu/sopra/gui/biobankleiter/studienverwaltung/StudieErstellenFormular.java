package de.wwu.sopra.gui.biobankleiter.studienverwaltung;

import java.util.ArrayList;

import de.wwu.sopra.gui.biobankleiter.BiobankLeiterMenueFormular;
import de.wwu.sopra.gui.helfer.ButtonRow;
import de.wwu.sopra.gui.helfer.GUIHelfer;
import de.wwu.sopra.gui.helfer.ListViewUndButtons;
import de.wwu.sopra.gui.helfer.StackedLabelTextFields;
import de.wwu.sopra.gui.helfer.UeberschriftUndButton;
import de.wwu.sopra.model.Studie;
import de.wwu.sopra.model.VisitenVorlage;
import de.wwu.sopra.steuerung.biobankleiter.StudienSteuerung;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

/**
 * Klasse zum erstellen des GUI-Fensters Studie erstellen
 * 
 * @author Alexander Leifhelm, Sebastian Huck, Wael Al Allan, Cedric Vadder
 */
public class StudieErstellenFormular {

	private Stage stage;
	private Scene scene;
	private Scene oldScene;
	private GridPane gp;

	private UeberschriftUndButton ueberschrift;
	private StackedLabelTextFields sltf;
	private ListViewUndButtons<Studie> list;
	private ButtonRow buttons;

	/**
	 * Konstruktor zum erstellen der Klasse, bekommt die Stage, das alte Fenster
	 * uebergeben.
	 * 
	 * @pre stage darf nicht null sein
	 * @pre oldScene darf nicht null sein
	 * @param stage    das aktive Fenster wird als Parameter uebergeben.
	 * @param oldScene das alte Fenster
	 */
	public StudieErstellenFormular(Stage stage, Scene oldScene) {

		this.stage = stage;
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
		ueberschrift = new UeberschriftUndButton("Studie erstellen", "Hauptmen\u00FC", null);

		String[] labels = { "Name:", "Gewuenschte Anz. \nTeilnehmer:" };
		sltf = new StackedLabelTextFields(labels);

		list = GUIHelfer.<VisitenVorlage>getStandardListViewUndButtons("Geplante Visiten", null,
				new ArrayList<VisitenVorlage>(), true, false);

		String[] labels2 = { "Abbrechen", null, "Erstellen" };
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
	 * Funktionalitaet der Buttons wird gesetzt.
	 */
	private void beobachten() {

		ueberschrift.getButton("Hauptmen\u00FC").setOnAction(new EventHandler<ActionEvent>() {
			/**
			 * Methode zur Beschreibung der Aktion in einem anderen Thread
			 * @param e ActionEvent der Aktion
			 */
			public void handle(ActionEvent e) {
				BiobankLeiterMenueFormular bblmf = new BiobankLeiterMenueFormular(stage, scene);
			}
		});

		buttons.getButton("Abbrechen").setOnAction(new EventHandler<ActionEvent>() {
			/**
			 * Methode zur Beschreibung der Aktion in einem anderen Thread
			 * @param e ActionEvent der Aktion
			 */
			public void handle(ActionEvent e) {
				stage.setScene(oldScene);
			}
		});

		buttons.getButton("Erstellen").setOnAction(new EventHandler<ActionEvent>() {
			/**
			 * Methode zur Beschreibung der Aktion in einem anderen Thread
			 * @param e ActionEvent der Aktion
			 */
			public void handle(ActionEvent e) {
				String name = sltf.getText("Name:");
				String anzahl = sltf.getText("Gewuenschte Anz. \nTeilnehmer:");
				StudienSteuerung ss = new StudienSteuerung();

				try {
					Studie s = ss.studieHinzufuegen(name, anzahl);
					StudieAnsehenFormular asf = new StudieAnsehenFormular(stage, scene, s);
				} catch (Exception exception) {
					GUIHelfer.AutomatischesFehlermeldungFormular("Hinzufuegen", exception);
				}

			}
		});

		list.getButton("Hinzuf\u00FCgen").setOnAction(new EventHandler<ActionEvent>() {
			/**
			 * Methode zur Beschreibung der Aktion in einem anderen Thread
			 * @param e ActionEvent der Aktion
			 */
			public void handle(ActionEvent e) {
				String name = sltf.getText("Name:");
				String anzahl = sltf.getText("Gewuenschte Anz. \nTeilnehmer:");
				StudienSteuerung ss = new StudienSteuerung();
				try {
					Studie s = ss.studieHinzufuegen(name, anzahl);
					VisitenvorlageHinzufuegenFormular vvhF = new VisitenvorlageHinzufuegenFormular(stage, scene, s);
				} catch (Exception exception) {
					GUIHelfer.AutomatischesFehlermeldungFormular("Hinzufuegen", exception);
				}

			}
		});

	}

}
