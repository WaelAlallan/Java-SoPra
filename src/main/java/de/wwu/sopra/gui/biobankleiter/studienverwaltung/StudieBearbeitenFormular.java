package de.wwu.sopra.gui.biobankleiter.studienverwaltung;

import java.util.ArrayList;
import java.util.Optional;

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
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

/**
 * Klasse zum erstellen des GUI-Fensters Studie bearbeiten
 * 
 * @author Alexander Leifhelm, Sebastian Huck, Wael Al Allan, Cedric Vadder
 */

public class StudieBearbeitenFormular {

	private Stage stage;
	private Scene scene;
	private Scene oldScene;
	private GridPane gp;

	private UeberschriftUndButton ueberschrift;
	private StackedLabelTextFields sltf;
	private ListViewUndButtons<VisitenVorlage> list;
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
	 * @param studie   die bearbeitet werden soll
	 */
	public StudieBearbeitenFormular(Stage stage, Scene oldScene, Studie studie) {

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

		ueberschrift = new UeberschriftUndButton("Studie bearbeiten", "Hauptmen\u00FC", null);
		String[] labels = { "Name:", "Gewuenschte Anz. \nTeilnehmer:" };
		sltf = new StackedLabelTextFields(labels);
		sltf.getTextField("Name:").setText(studie.getName());
		sltf.getTextField("Gewuenschte Anz. \nTeilnehmer:").setText(Integer.toString(studie.getAnzahlTeilnehmer()));

		list = GUIHelfer.<VisitenVorlage>getStandardListViewUndButtons("Geplante Visiten", null,
				studie.getVisitenVorlage(), true, true);

		String[] labels2 = { "Abbrechen", "L\u00F6schen", "Best\u00E4tigen" };
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
				StudieAnsehenFormular saf = new StudieAnsehenFormular(stage, scene, studie);
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
					alert.setContentText("Sind sie sicher, dass die Studie gel\u00f6scht werden soll?");

					Optional<ButtonType> result = alert.showAndWait();
					if (result.get() == ButtonType.OK) {
						StudienSteuerung ss = new StudienSteuerung();
						ss.studieLoeschen(studie);
						ArrayList<Studie> studien = ss.getStudien("", "");
						StudienUebersichtFormular suf = new StudienUebersichtFormular(stage, studien);
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
				StudienSteuerung ss = new StudienSteuerung();

				try {
					ss.studieBearbeiten(studie, sltf.getText("Name:"), sltf.getText("Gewuenschte Anz. \nTeilnehmer:"));
					StudieAnsehenFormular saf = new StudieAnsehenFormular(stage, scene, studie);
				} catch (Exception exception) {
					GUIHelfer.AutomatischesFehlermeldungFormular("Bearbeiten", exception);
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
				try {
					StudienSteuerung ss = new StudienSteuerung();
					Studie s = ss.studieBearbeiten(studie, name, anzahl);
					VisitenvorlageHinzufuegenFormular vvhF = new VisitenvorlageHinzufuegenFormular(stage, scene, s);
				} catch (Exception exception) {
					GUIHelfer.AutomatischesFehlermeldungFormular("Hinzufuegen", exception);
				}
			}
		});

		list.getButton("Weiter").setOnAction(new EventHandler<ActionEvent>() {
			/**
			 * Methode zur Beschreibung der Aktion in einem anderen Thread
			 * @param e ActionEvent der Aktion
			 */
			public void handle(ActionEvent e) {
				String name = sltf.getText("Name:");
				String anzahl = sltf.getText("Gewuenschte Anz. \nTeilnehmer:");
				try {
					StudienSteuerung ss = new StudienSteuerung();
					Studie s = ss.studieBearbeiten(studie, name, anzahl);
					if (list.getSelektiertesObjekt() != null) {
						VisitenvorlageAnsehenFormular vvhF = new VisitenvorlageAnsehenFormular(stage, scene,
								list.getSelektiertesObjekt(), s);
					} else {
						GUIHelfer.AutomatischesFehlermeldungFormular("Auswahl", null);
					}
				} catch (Exception exception) {
					GUIHelfer.AutomatischesFehlermeldungFormular("Bearbeiten", exception);
				}

			}
		});

	}

}
