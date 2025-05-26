package de.wwu.sopra.gui.biobankleiter.studienverwaltung;

import de.wwu.sopra.gui.biobankleiter.BiobankLeiterMenueFormular;
import de.wwu.sopra.gui.helfer.ButtonRow;
import de.wwu.sopra.gui.helfer.GUIHelfer;
import de.wwu.sopra.gui.helfer.ListViewUndButtons;
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
 * GUI Klasse zu erstellen des Visitenvorlagen Ansehen Fensters
 * 
 * @author Alexander Leifhelm, Sebastian Huck, Wael Al Allan, Cedric Vadder
 */
public class VisitenvorlageAnsehenFormular {
	private Stage stage;
	private Scene scene;
	private Scene oldScene;
	private VisitenVorlage visitenVorlage;
	private Studie studie;
	private UeberschriftUndButton kopfUUB;
	private UeberschriftUndButton ueberKopfStudienName;
	private StackedLabelTextFields eingabenSLTF;

	private ListViewUndButtons<ProbenVorlage> listeLVUB;

	private ButtonRow buttonsBR;
	private GridPane gp;

	/**
	 * Konstruktor der Klasse
	 * 
	 * @pre stage darf nicht null sein
	 * @pre oldScene darf nicht null sein
	 * @param stage          das aktive FEnster wird uebergeben
	 * @param oldScene       die alte Scene wird mit uebergeben
	 * @param visitenVorlage die zugehoerige Visitenvorlage
	 * @param studie         die zugehoerige Studie
	 */
	public VisitenvorlageAnsehenFormular(Stage stage, Scene oldScene, VisitenVorlage visitenVorlage, Studie studie) {
		this.stage = stage;
		this.oldScene = oldScene;
		this.visitenVorlage = visitenVorlage;
		this.studie = studie;
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
		ueberKopfStudienName = new UeberschriftUndButton(studie.getName(), null, null, 20);
		kopfUUB = new UeberschriftUndButton("Visitenvorlage ansehen", "Hauptmen\u00FC", null);
		String[] labels = { "Name:", "Med. Daten (mit Komma getrennt):", "Zeitabstand in Tagen:" };
		eingabenSLTF = new StackedLabelTextFields(labels);
		eingabenSLTF.ansehenModus();
		eingabenSLTF.getTextField("Name:").setText(visitenVorlage.getName());
		// Eckige Klammer werden entfernt, um Dopplungen zu vermeiden
		String medizinischeDaten = visitenVorlage.getMedizinischeDatenBezeichnung().toString();
		String medizinischeDatenFormatiert = medizinischeDaten.substring(1, medizinischeDaten.length() - 1);
		eingabenSLTF.getTextField("Med. Daten (mit Komma getrennt):").setText(medizinischeDatenFormatiert);
		eingabenSLTF.getTextField("Zeitabstand in Tagen:")
				.setText("" + visitenVorlage.getZeitZurLetztenVisiteInTagen());

		String[] buttonLabels = { "Abbrechen", null, "Bearbeiten" };
		buttonsBR = new ButtonRow(buttonLabels);

		listeLVUB = GUIHelfer.<ProbenVorlage>getStandardListViewUndButtons("Proben:", null,
				visitenVorlage.getProbenVorlage(), false, false);

		gp = new GridPane();
		gp.addRow(0, ueberKopfStudienName.getGridPane());
		gp.addRow(1, kopfUUB.getGridPane());
		gp.addRow(2, eingabenSLTF.getGridPane());
		gp.addRow(3, listeLVUB.getGridPane());
		gp.addRow(4, buttonsBR.getGridPane());

		GUIHelfer.setzeZeilenGewichte(gp, new float[] { 5.0f, 15.0f, 25.0f, 40.0f, 15.0f });
		GUIHelfer.setzeSpaltenGewichte(gp, GUIHelfer.gibGleicheGewichte(1));

		gp.setPadding(new Insets(10, 10, 10, 10));
		scene = new Scene(gp, 1280, 720);
	}

	/**
	 * Buttons erhalten ihre Funktionalitaet
	 */
	private void beobachteAktionen() {
		buttonsBR.getButton("Abbrechen").setOnAction(new EventHandler<ActionEvent>() {
			/**
			 * Methode zur Beschreibung der Aktion in einem anderen Thread
			 * @param e ActionEvent der Aktion
			 */
			public void handle(ActionEvent e) {
				StudieAnsehenFormular vvaF = new StudieAnsehenFormular(stage, scene, studie);
			}
		});

		buttonsBR.getButton("Bearbeiten").setOnAction(new EventHandler<ActionEvent>() {
			/**
			 * Methode zur Beschreibung der Aktion in einem anderen Thread
			 * @param e ActionEvent der Aktion
			 */
			public void handle(ActionEvent e) {
				VisitenvorlageBearbeitenFormular vvbF = new VisitenvorlageBearbeitenFormular(stage, scene,
						visitenVorlage, studie);
			}
		});

		kopfUUB.getButton("Hauptmen\u00FC").setOnAction(new EventHandler<ActionEvent>() {
			/**
			 * Methode zur Beschreibung der Aktion in einem anderen Thread
			 * @param e ActionEvent der Aktion
			 */
			public void handle(ActionEvent e) {
				BiobankLeiterMenueFormular bmf = new BiobankLeiterMenueFormular(stage, scene);
			}
		});

	}

}
