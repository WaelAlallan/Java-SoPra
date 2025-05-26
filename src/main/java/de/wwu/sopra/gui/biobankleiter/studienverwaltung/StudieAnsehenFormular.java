package de.wwu.sopra.gui.biobankleiter.studienverwaltung;

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
 * Klasse zum erstellen des GUI-Fensters Studie ansehen
 * 
 * @author Alexander Leifhelm, Sebastian Huck, Wael Al Allan, Cedric Vadder
 */
public class StudieAnsehenFormular {

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
	public StudieAnsehenFormular(Stage stage, Scene oldScene, Studie studie) {

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

		String[] labels2 = { "Abbrechen", null, "Bearbeiten" };
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
				BiobankLeiterMenueFormular bblmf = new BiobankLeiterMenueFormular(stage, scene);
			}
		});

		buttons.getButton("Abbrechen").setOnAction(new EventHandler<ActionEvent>() {
			/**
			 * Methode zur Beschreibung der Aktion in einem anderen Thread
			 * @param e ActionEvent der Aktion
			 */
			public void handle(ActionEvent e) {
				StudienSteuerung ss = new StudienSteuerung();
				StudienUebersichtFormular suf = new StudienUebersichtFormular(stage, ss.getStudien("", ""));
			}
		});

		buttons.getButton("Bearbeiten").setOnAction(new EventHandler<ActionEvent>() {
			/**
			 * Methode zur Beschreibung der Aktion in einem anderen Thread
			 * @param e ActionEvent der Aktion
			 */
			public void handle(ActionEvent e) {
				if (studie.hatPatienten()) {
					GUIHelfer.FehlermeldungFormular("Fehler", "Studie hat begonnen!",
							"Diese Studie hat bereits begonnen und ist deshalb nichtmehr veraenderbar!");
				} else {
					StudieBearbeitenFormular sbf = new StudieBearbeitenFormular(stage, scene, studie);
				}
			}
		});
	}
}
