package de.wwu.sopra.gui.biobankleiter.studienverwaltung;

import java.util.ArrayList;

import de.wwu.sopra.gui.biobankleiter.BiobankLeiterMenueFormular;
import de.wwu.sopra.gui.helfer.ButtonRow;
import de.wwu.sopra.gui.helfer.GUIHelfer;
import de.wwu.sopra.gui.helfer.ListViewUndButtons;
import de.wwu.sopra.gui.helfer.UeberschriftUndButton;
import de.wwu.sopra.model.ProbenBehaelterTyp;
import de.wwu.sopra.model.ProbenKategorie;
import de.wwu.sopra.model.Studie;
import de.wwu.sopra.model.VisitenVorlage;
import de.wwu.sopra.steuerung.biobankleiter.ProbenBehaeltertypHinzufuegenSteuerung;
import de.wwu.sopra.steuerung.biobankleiter.ProbenVorlageSteuerung;
import de.wwu.sopra.steuerung.biobankleiter.ProbentypHinzufuegenSteuerung;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

/**
 * GUI Klasse zum erstellen des Fensters ProbenHunzufuegen
 * 
 * @author Alexander Leifhelm, Sebastian Huck, Wael Al Allan, Cedric Vadder
 */
public class ProbenvorlageHinzufuegenFormular {

	private Stage stage;
	private Scene scene;
	private Scene oldScene;
	private VisitenVorlage visitenVorlage;
	private Studie studie;
	private UeberschriftUndButton kopfUUB;
	private UeberschriftUndButton ueberKopfStudienName;

	private ListViewUndButtons<ProbenKategorie> listeLVUBKategorie;
	private ListViewUndButtons<ProbenBehaelterTyp> listeLVUBBehaeltertyp;

	private ButtonRow buttonsBR;
	private GridPane gp;

	/**
	 * Konstruktor der Klasse
	 * 
	 * @pre stage darf nicht null sein
	 * @pre oldScene darf nicht null sein
	 * @param stage          das aktive Fenster wird uebergeben
	 * @param oldScene       die alte Scene wird uebergeben
	 * @param visitenVorlage die zugehoerige Visitenvorlage wird uebergeben
	 * @param studie         die zugehoerige Studie wird uebergeben
	 */
	public ProbenvorlageHinzufuegenFormular(Stage stage, Scene oldScene, VisitenVorlage visitenVorlage, Studie studie) {
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
		kopfUUB = new UeberschriftUndButton("Probenvorlage hinzuf\u00FCgen", "Hauptmen\u00FC", null);

		ProbentypHinzufuegenSteuerung phS = new ProbentypHinzufuegenSteuerung();
		ArrayList<ProbenKategorie> kategorien = phS.getProbenKategorie("");
		listeLVUBKategorie = GUIHelfer.<ProbenKategorie>getStandardListViewUndButtons("Probenkategorie:", null,
				kategorien, false, false);

		ProbenBehaeltertypHinzufuegenSteuerung pbtS = new ProbenBehaeltertypHinzufuegenSteuerung();
		ArrayList<ProbenBehaelterTyp> typen = pbtS.getProbenbehaeltertyp("", "", "", "", "");
		listeLVUBBehaeltertyp = GUIHelfer.<ProbenBehaelterTyp>getStandardListViewUndButtons("Beh\u00E4ltertyp:", null,
				typen, false, false);

		String[] buttonLabels = { "Abbrechen", null, "Hinzuf\u00FCgen" };
		buttonsBR = new ButtonRow(buttonLabels);

		gp = new GridPane();
		gp.addRow(0, ueberKopfStudienName.getGridPane());
		gp.addRow(1, kopfUUB.getGridPane());
		gp.addRow(3, listeLVUBKategorie.getGridPane());
		gp.addRow(4, listeLVUBBehaeltertyp.getGridPane());
		gp.addRow(5, buttonsBR.getGridPane());

		GUIHelfer.setzeZeilenGewichte(gp, new float[] { 5.0f, 15.0f, 15.0f, 40.0f, 10.0f, 15.0f });
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
				VisitenvorlageBearbeitenFormular vvbF = new VisitenvorlageBearbeitenFormular(stage, scene,
						visitenVorlage, studie);
			}
		});

		buttonsBR.getButton("Hinzuf\u00FCgen").setOnAction(new EventHandler<ActionEvent>() {
			/**
			 * Methode zur Beschreibung der Aktion in einem anderen Thread
			 * @param e ActionEvent der Aktion
			 */
			public void handle(ActionEvent e) {
				ProbenVorlageSteuerung pvS = new ProbenVorlageSteuerung();
				try {
					if (listeLVUBBehaeltertyp.getSelektiertesObjekt() != null
							&& listeLVUBKategorie.getSelektiertesObjekt() != null) {
						VisitenVorlage bearbeitet = pvS.probenVorlageHinzufuegen(visitenVorlage,
								listeLVUBBehaeltertyp.getSelektiertesObjekt(),
								listeLVUBKategorie.getSelektiertesObjekt());
						VisitenvorlageBearbeitenFormular vvbF = new VisitenvorlageBearbeitenFormular(stage, scene,
								bearbeitet, studie);
					} else {
						throw new IllegalArgumentException(
								"Eine Kategorie und ein Behaeltertyp muss ausgewaehlt sein.");
					}

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

	}

}
