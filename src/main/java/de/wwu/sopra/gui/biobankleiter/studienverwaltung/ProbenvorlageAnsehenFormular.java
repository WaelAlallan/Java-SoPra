package de.wwu.sopra.gui.biobankleiter.studienverwaltung;

import de.wwu.sopra.gui.biobankleiter.BiobankLeiterMenueFormular;
import de.wwu.sopra.gui.helfer.ButtonRow;
import de.wwu.sopra.gui.helfer.GUIHelfer;
import de.wwu.sopra.gui.helfer.StackedLabelTextFields;
import de.wwu.sopra.gui.helfer.UeberschriftUndButton;
import de.wwu.sopra.model.ProbenVorlage;
import de.wwu.sopra.model.Studie;
import de.wwu.sopra.model.VisitenVorlage;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

/**
 * GUI-Klasse zum erstellen des GUI Fensters Probenvorlage Ansehen
 * 
 * @author Alexander Leifhelm, Sebastian Huck, Wael Al Allan, Cedric Vadder
 */
public class ProbenvorlageAnsehenFormular {

	private Stage stage;
	private Scene scene;
	private Scene oldScene;
	private GridPane gp;

	private UeberschriftUndButton ueberKopfStudienName;
	private UeberschriftUndButton ueberschrift;
	private StackedLabelTextFields sltf;
	private ButtonRow buttons;

	private Studie studie;
	private VisitenVorlage visitenvorlage;
	private ProbenVorlage vorlage;

	/**
	 * Konstruktor der Klasse
	 * 
	 * @pre stage darf nicht null sein
	 * @pre oldScene darf nicht null sein
	 * @param stage          das aktive Fenster
	 * @param oldScene       die Scene des vorherigen Fensters
	 * @param studie         die Studie in der die Probenvorlage ist
	 * @param visitenvorlage die Visitenvorlage
	 * @param vorlage        Die anzuzeigende Probenvorage
	 */
	public ProbenvorlageAnsehenFormular(Stage stage, Scene oldScene, Studie studie, VisitenVorlage visitenvorlage,
			ProbenVorlage vorlage) {

		this.stage = stage;
		this.oldScene = oldScene;
		this.studie = studie;
		this.visitenvorlage = visitenvorlage;
		this.vorlage = vorlage;

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
		ueberKopfStudienName = new UeberschriftUndButton(studie.getName(), null, null, 20);
		ueberschrift = new UeberschriftUndButton("Probenvorlage Ansehen", "Hauptmen\u00FC", null);

		String[] labels = { "Behaeltertyp:", "Probenkategorie:" };
		sltf = new StackedLabelTextFields(labels);
		sltf.getTextField("Behaeltertyp:").setText(vorlage.getProbenBehaelterTyp().toString());
		sltf.getTextField("Probenkategorie:").setText(vorlage.getKategorie().toString());
		sltf.ansehenModus();

		String[] labels2 = { "Abbrechen", null, "Bearbeiten" };
		buttons = new ButtonRow(labels2);

		gp = new GridPane();
		gp.addRow(0, ueberKopfStudienName.getGridPane());
		gp.addRow(1, ueberschrift.getGridPane());
		gp.addRow(2, sltf.getGridPane());
		gp.addRow(3, buttons.getGridPane());

		GUIHelfer.setzeSpaltenGewichte(gp, GUIHelfer.gibGleicheGewichte(1));
		GUIHelfer.setzeZeilenGewichte(gp, new float[] { 5.0f, 15.0f, 65.0f, 15.0f });
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
				VisitenvorlageBearbeitenFormular vfb = new VisitenvorlageBearbeitenFormular(stage, scene,
						visitenvorlage, studie);
			}
		});

		buttons.getButton("Bearbeiten").setOnAction(new EventHandler<ActionEvent>() {
			/**
			 * Methode zur Beschreibung der Aktion in einem anderen Thread
			 * @param e ActionEvent der Aktion
			 */
			public void handle(ActionEvent e) {
				ProbenvorlageBearbeitenFormular pvf = new ProbenvorlageBearbeitenFormular(stage, scene, vorlage,
						visitenvorlage, studie);
			}
		});

	}

}
