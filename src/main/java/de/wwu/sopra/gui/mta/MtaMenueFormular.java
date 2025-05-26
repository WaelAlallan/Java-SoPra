package de.wwu.sopra.gui.mta;

import de.wwu.sopra.gui.alleuser.AnmeldenFormular;
import de.wwu.sopra.gui.alleuser.PasswortAendernFormular;
import de.wwu.sopra.gui.helfer.ButtonColumn;
import de.wwu.sopra.gui.helfer.GUIHelfer;
import de.wwu.sopra.gui.helfer.UeberschriftUndButton;
import de.wwu.sopra.steuerung.alleuser.AuthentifizierungSteuerung;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

/**
 * Klasse zum erstellen des GUI Fensters MTA Menue
 * 
 * @author Sebastian Huck
 *
 */
public class MtaMenueFormular {

	private Stage stage;
	private Scene scene;
	private Scene oldScene;
	private GridPane gp;
	private UeberschriftUndButton kopfUUB;
	private ButtonColumn menueBC;

	/**
	 * Konstruktor der Methode
	 * 
	 * @pre stage darf nicht null sein
	 * @pre oldScene darf nicht null sein
	 * @param stage    Das aktive Fenster wird uebergeben
	 * @param oldScene Die alte Scene wird als Parameter mit uebergeben
	 */
	public MtaMenueFormular(Stage stage, Scene oldScene) {

		this.stage = stage;
		this.oldScene = scene;

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

		kopfUUB = new UeberschriftUndButton("MTA Men\u00FC", "Passwort \u00E4ndern", "Abmelden");
		String[] menueButtonLabels = { "Probe suchen", "Probe neu anlegen" };
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
	 * funktionalitaet der Buttons wird gesetzt.
	 */
	private void beobachten() {

		menueBC.getButton("Probe suchen").setOnAction(event -> {
			BiomaterialprobenSuchenFormular bsf = new BiomaterialprobenSuchenFormular(stage, scene);
		});

		menueBC.getButton("Probe neu anlegen").setOnAction(event -> {
			Probehinzufuegenformular phf = new Probehinzufuegenformular(stage, scene);
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

	}

}
