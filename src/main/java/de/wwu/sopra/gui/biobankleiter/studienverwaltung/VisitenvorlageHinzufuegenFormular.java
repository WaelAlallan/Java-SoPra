package de.wwu.sopra.gui.biobankleiter.studienverwaltung;

import java.util.ArrayList;

import de.wwu.sopra.gui.biobankleiter.BiobankLeiterMenueFormular;
import de.wwu.sopra.gui.helfer.ButtonRow;
import de.wwu.sopra.gui.helfer.GUIHelfer;
import de.wwu.sopra.gui.helfer.ListViewUndButtons;
import de.wwu.sopra.gui.helfer.StackedLabelTextFields;
import de.wwu.sopra.gui.helfer.UeberschriftUndButton;
import de.wwu.sopra.model.ProbenVorlage;
import de.wwu.sopra.model.Studie;
import de.wwu.sopra.model.VisitenVorlage;
import de.wwu.sopra.steuerung.biobankleiter.VisitenvorlagenSteuerung;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

/**
 * GUI Klasse zu erstellen des Visitenvorlagen Hinzufuegen Fensters
 * 
 * @author Alexander Leifhelm, Sebastian Huck, Wael Al Allan, Cedric Vadder
 */
public class VisitenvorlageHinzufuegenFormular {

	private Stage stage;
	private Scene scene;
	private Scene oldScene;
	private Studie studie;
	private UeberschriftUndButton kopfUUB;
	private UeberschriftUndButton ueberKopfStudienName;
	private StackedLabelTextFields eingabenSLTF;
	private ListViewUndButtons<ProbenVorlage> listeLVUB;

	private ButtonRow buttonsBR;
	private GridPane gp;

	/**
	 * Kontruktor der Klasse
	 * 
	 * @pre stage darf nicht null sein
	 * @pre oldScene darf nicht null sein
	 * @param stage    das aktive Fenster wird uebergeben
	 * @param oldScene die alte Scene wir uebergeben
	 * @param studie   die zugehoerige Studie wird uebergeben
	 */
	public VisitenvorlageHinzufuegenFormular(Stage stage, Scene oldScene, Studie studie) {
		this.stage = stage;
		this.oldScene = oldScene;
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
		kopfUUB = new UeberschriftUndButton("Visitenvorlage hinzuf\u00FCgen", "Hauptmen\u00FC", null);
		String[] labels = { "Name:", "Med. Daten (mit Komma getrennt):", "Durchfuehrung nach Beginn in Tagen:" };
		eingabenSLTF = new StackedLabelTextFields(labels);
		eingabenSLTF.bearbeitenModus();

		String[] buttonLabels = { "Abbrechen", null, "Hinzuf\u00FCgen" };
		buttonsBR = new ButtonRow(buttonLabels);

		listeLVUB = GUIHelfer.<ProbenVorlage>getStandardListViewUndButtons("Proben:", null,
				new ArrayList<ProbenVorlage>(), true, true);

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
				StudieBearbeitenFormular sbF = new StudieBearbeitenFormular(stage, scene, studie);

			}
		});

		buttonsBR.getButton("Hinzuf\u00FCgen").setOnAction(new EventHandler<ActionEvent>() {
			/**
			 * Methode zur Beschreibung der Aktion in einem anderen Thread
			 * @param e ActionEvent der Aktion
			 */
			public void handle(ActionEvent e) {
				VisitenvorlagenSteuerung vvS = new VisitenvorlagenSteuerung();
				try {
					vvS.visitenvorlageHinzufuegen(studie, eingabenSLTF.getTextField("Name:").getText(),
							eingabenSLTF.getTextField("Med. Daten (mit Komma getrennt):").getText(),
							eingabenSLTF.getTextField("Durchfuehrung nach Beginn in Tagen:").getText());
					// Auch wenn man dabei ist eine neue Studie anzulegen, kommt man zum
					// StudieBearbeitenFormular
					// Sonst muesste gespeichert werden, ob ein Studie bearbeitet oder angelegt wird
					StudieBearbeitenFormular sbF = new StudieBearbeitenFormular(stage, scene, studie);
				} catch (Exception exception) {
					GUIHelfer.AutomatischesFehlermeldungFormular("Hinzufuegen", exception);
				}

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

		listeLVUB.getButton("Weiter").setOnAction(new EventHandler<ActionEvent>() {
			/**
			 * Methode zur Beschreibung der Aktion in einem anderen Thread
			 * @param e ActionEvent der Aktion
			 */
			public void handle(ActionEvent e) {
				if (listeLVUB.getSelektiertesObjekt() != null) {
//					ProbenvorlageAnsehenFormular pvaF = new ProbenvorlageAnsehenFormular(stage, scene,
//							listeLVUB.getSelektiertesObjekt());
				} else {
					GUIHelfer.AutomatischesFehlermeldungFormular("Auswahl", null);
				}
			}
		});

		listeLVUB.getButton("Hinzuf\u00FCgen").setOnAction(new EventHandler<ActionEvent>() {
			/**
			 * Methode zur Beschreibung der Aktion in einem anderen Thread
			 * @param e ActionEvent der Aktion
			 */
			public void handle(ActionEvent e) {
				VisitenvorlagenSteuerung vvS = new VisitenvorlagenSteuerung();
				try {
					VisitenVorlage neu = vvS.visitenvorlageHinzufuegen(studie,
							eingabenSLTF.getTextField("Name:").getText(),
							eingabenSLTF.getTextField("Med. Daten (mit Komma getrennt):").getText(),
							eingabenSLTF.getTextField("Durchfuehrung nach Beginn in Tagen:").getText());
					ProbenvorlageHinzufuegenFormular phF = new ProbenvorlageHinzufuegenFormular(stage, scene, neu,
							studie);
				} catch (Exception exception) {
					GUIHelfer.AutomatischesFehlermeldungFormular("Hinzufuegen", exception);
				}

			}
		});

	}

}
