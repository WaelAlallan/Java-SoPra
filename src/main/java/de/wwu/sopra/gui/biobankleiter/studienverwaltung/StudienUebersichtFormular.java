package de.wwu.sopra.gui.biobankleiter.studienverwaltung;

import java.util.ArrayList;

import de.wwu.sopra.gui.biobankleiter.BiobankLeiterMenueFormular;
import de.wwu.sopra.gui.helfer.ButtonRow;
import de.wwu.sopra.gui.helfer.GUIHelfer;
import de.wwu.sopra.gui.helfer.ListViewUndButtons;
import de.wwu.sopra.gui.helfer.UeberschriftUndButton;
import de.wwu.sopra.model.Studie;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

/**
 * Klasse zum erstellen des GUI-Fensters Studie ansehen
 */
public class StudienUebersichtFormular {

	private Stage stage;
	private Scene scene;
	private GridPane gp;
	private UeberschriftUndButton ueberschrift;
	private ButtonRow buttonRow;
	private ListViewUndButtons<Studie> lvb;
	private ArrayList<Studie> studien;

	/**
	 * Konstruktor zum erstellen der Klasse, bekommt die Stage, das alte Fenster und
	 * eine Studie uebergeben.
	 * 
	 * @pre stage darf nicht null sein
	 * @pre oldScene darf nicht null sein
	 * @param stage   das aktive Fenster wird als Parameter uebergeben.
	 * @param studien die Liste der Studien
	 */
	public StudienUebersichtFormular(Stage stage, ArrayList<Studie> studien) {

		this.stage = stage;
		init(studien);
		beobachten();
		stage.setScene(scene);
	}

	/**
	 * Initialisierungsmethode der Scene, hierzu werden die Hilfsklassen benutzt um
	 * die Klassenattribute zu initialisieren, welche dann automatisch GridPanes
	 * erstellen, welche dann in eine weitere GridPane gesetzt werden. Dann werden
	 * Gewichte gesetzt und und die Scene wird initialisiert damit das neue Fenster
	 * angezeigt werden kann.
	 * 
	 * @param studien die anzuzeigenden Studien
	 */
	private void init(ArrayList<Studie> studien) {

		ueberschrift = new UeberschriftUndButton("Studien\u00FCbersicht", "Hauptmen\u00FC", null);

		String[] labels = { null, null, "Studien filtern" };
		buttonRow = new ButtonRow(labels);

		lvb = GUIHelfer.<Studie>getStandardListViewUndButtons(null, null, studien, true, true);

		gp = new GridPane();
		gp.addRow(0, ueberschrift.getGridPane());
		gp.addRow(1, buttonRow.getGridPane());
		gp.addRow(2, lvb.getGridPane());

		// float[] gewichte = { 20f, 10f, 50f, 20f };
		// GUIHelfer.setzeZeilenGewichte(gp, gewichte);
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

		lvb.getButton("Hinzuf\u00FCgen").setOnAction(new EventHandler<ActionEvent>() {

			/**
			 * Methode zur Beschreibung der Aktion in einem anderen Thread
			 * @param e ActionEvent der Aktion
			 */
			public void handle(ActionEvent e) {
				StudieErstellenFormular sef = new StudieErstellenFormular(stage, scene);
			}
		});

		buttonRow.getButton("Studien filtern").setOnAction(new EventHandler<ActionEvent>() {
			/**
			 * Methode zur Beschreibung der Aktion in einem anderen Thread
			 * @param e ActionEvent der Aktion
			 */
			public void handle(ActionEvent e) {
				StudienFilternFormular sf = new StudienFilternFormular(stage, scene);
			}
		});

		lvb.getButton("Weiter").setOnAction(new EventHandler<ActionEvent>() {
			/**
			 * Methode zur Beschreibung der Aktion in einem anderen Thread
			 * @param e ActionEvent der Aktion
			 */
			public void handle(ActionEvent e) {
				Studie studie = lvb.getSelektiertesObjekt();
				if (studie != null) {
					StudieAnsehenFormular saf = new StudieAnsehenFormular(stage, scene, studie);
				} else {
					GUIHelfer.AutomatischesFehlermeldungFormular("Auswahl", null);
				}
			}
		});
	}

}
