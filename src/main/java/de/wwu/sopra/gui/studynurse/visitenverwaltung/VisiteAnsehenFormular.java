package de.wwu.sopra.gui.studynurse.visitenverwaltung;

import java.util.ArrayList;

import de.wwu.sopra.gui.helfer.ButtonRow;
import de.wwu.sopra.gui.helfer.GUIHelfer;
import de.wwu.sopra.gui.helfer.ListViewUndButtons;
import de.wwu.sopra.gui.helfer.MedizinischeDatenWertListeObjekt;
import de.wwu.sopra.gui.helfer.StackedLabelTextFields;
import de.wwu.sopra.gui.helfer.UeberschriftUndButton;
import de.wwu.sopra.gui.studynurse.StudyNurseMenueFormular;
import de.wwu.sopra.model.ProbenVorlage;
import de.wwu.sopra.model.Visite;
import de.wwu.sopra.steuerung.helfer.DatumHelfer;
import de.wwu.sopra.steuerung.studynurse.VisitenSteuerung;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import javafx.util.Pair;

/**
 * Klasse zu erstellen des GUI-Fensters zur Visitenansicht der StudyNurse
 * 
 * @author Wael Alallan
 */
public class VisiteAnsehenFormular {

	private Stage stage;
	private Scene scene;
	private Scene oldScene;
	private GridPane gp;

	private UeberschriftUndButton kopfUUB;
	private ButtonRow buttonsBR;
	private StackedLabelTextFields eingabenSLTF;
	private ListViewUndButtons<ProbenVorlage> probenLVUB;

	private ListViewUndButtons<MedizinischeDatenWertListeObjekt> listeLVUB;
	private Visite visite;
	private ArrayList<Pair<String, String>> medDatenAL;

	/**
	 * Konstruktor zum Erstellen der Klasse. Bekommt die Stage, das alte Fenster,
	 * uebergeben mitsamt der anzuzeigenden Visite und zugehoeriger medizinischer
	 * Daten.
	 * 
	 * @pre stage darf nicht null sein
	 * @pre oldScene darf nicht null sein
	 * @param stage      das aktive Fenster wird als Parameter uebergeben.
	 * @param oldScene   das alte Fenster.
	 * @param visite     Visite, die angezeigt werden soll.
	 * @param medDatenAL ArrayList mit den Paaren aus dem Oberbegriff fuer die
	 *                   medizinischen Daten und dem zugehoerigen Wert.
	 */
	public VisiteAnsehenFormular(Stage stage, Scene oldScene, Visite visite,
			ArrayList<Pair<String, String>> medDatenAL) {
		this.stage = stage;
		this.oldScene = oldScene;
		this.visite = visite;
		this.medDatenAL = medDatenAL;

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
	 * 
	 * @param medDatenAL ArrayList mit den Paaren aus dem Oberbegriff fuer die
	 *                   medizinischen Daten und dem zugehoerigen Wert.
	 */

	private void init() {

		kopfUUB = new UeberschriftUndButton("Visite ansehen", "Hauptmen\u00FC", null);
		String[] buttonLabel = { "Abbrechen", null, "Bearbeiten" };
		buttonsBR = new ButtonRow(buttonLabel);
		String[] labels = { "Patient:", "Datum:", "Visiten ID:" };
		eingabenSLTF = new StackedLabelTextFields(labels);
		eingabenSLTF.getTextField("Patient:").setText(visite.getPatient().toString());
		eingabenSLTF.getTextField("Datum:").setText(DatumHelfer.gibDatumString(visite.getDatum()));
		eingabenSLTF.getTextField("Visiten ID:").setText(Integer.toString(visite.getVisitenId()));
		eingabenSLTF.ansehenModus();

		ArrayList<MedizinischeDatenWertListeObjekt> medDatenListeObjektAL = new ArrayList<>();
		medDatenListeObjektAL = MedizinischeDatenWertListeObjekt.getAlsMDWLObjekte(medDatenAL);
		listeLVUB = GUIHelfer.<MedizinischeDatenWertListeObjekt>getStandardListViewUndButtons("Medizinische Daten:",
				null, medDatenListeObjektAL, false, false);

		ArrayList<ProbenVorlage> arrList = new ArrayList<ProbenVorlage>();
		arrList = visite.getVisitenVorlage().getProbenVorlage();
		probenLVUB = GUIHelfer.<ProbenVorlage>getStandardListViewUndButtons("Proben:", null, arrList, false, false);

		gp = new GridPane();
		gp.addRow(0, kopfUUB.getGridPane());
		gp.addRow(1, eingabenSLTF.getGridPane());
		gp.addRow(2, listeLVUB.getGridPane());
		gp.addRow(3, probenLVUB.getGridPane());
		gp.addRow(4, buttonsBR.getGridPane());

		GUIHelfer.setzeZeilenGewichte(gp, GUIHelfer.gibGleicheGewichte(gp.getChildren().size()));
		GUIHelfer.setzeSpaltenGewichte(gp, GUIHelfer.gibGleicheGewichte(1));

		gp.setPadding(new Insets(10, 10, 10, 10));
		scene = new Scene(gp, 1280, 720);
	}

	/**
	 * funktionalitaet der Buttons wird gesetzt.
	 */
	private void beobachteAktionen() {
		VisitenSteuerung vs = new VisitenSteuerung();

		kopfUUB.getButton("Hauptmen\u00FC").setOnAction(event -> {
			StudyNurseMenueFormular snmf = new StudyNurseMenueFormular(stage, scene);
		});

		buttonsBR.getButton("Abbrechen").setOnAction(event -> {
			ArrayList<Visite> visitenAL = vs.visitenSuchen("", "", "", "", null);
			VisiteMenueFormular vmf = new VisiteMenueFormular(stage, scene, visitenAL);
		});

		buttonsBR.getButton("Bearbeiten").setOnAction(event -> {
			VisiteBearbeitenFormular bmf = new VisiteBearbeitenFormular(stage, scene, visite, medDatenAL);
		});

	}

}
