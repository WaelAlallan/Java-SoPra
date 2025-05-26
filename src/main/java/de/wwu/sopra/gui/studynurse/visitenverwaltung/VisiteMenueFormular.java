package de.wwu.sopra.gui.studynurse.visitenverwaltung;

import java.util.ArrayList;

import de.wwu.sopra.gui.helfer.ButtonRow;
import de.wwu.sopra.gui.helfer.GUIHelfer;
import de.wwu.sopra.gui.helfer.ListViewUndButtons;
import de.wwu.sopra.gui.helfer.StackedLabelTextFields;
import de.wwu.sopra.gui.helfer.UeberschriftUndButton;
import de.wwu.sopra.gui.studynurse.StudyNurseMenueFormular;
import de.wwu.sopra.model.Visite;
import de.wwu.sopra.steuerung.studynurse.VisitenSteuerung;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.CheckBox;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

/**
 * Klasse zu erstellen des GUI-Fensters zum Visitenmenue der StudyNurse
 * 
 * @author Wael Alallan
 */
public class VisiteMenueFormular {
	private Stage stage;
	private Scene scene;
	private Scene oldScene;
	private GridPane gp;
	private UeberschriftUndButton kopfUUB;
	private ButtonRow suchenBR;
	private StackedLabelTextFields eingabenSLTF;
	private CheckBox durchgefuehrt;
	private ListViewUndButtons<Visite> listeLVUB;

	/**
	 * Konstruktor zum Erstellen der Klasse. Bekommt die Stage, das alte Fenster,
	 * uebergeben mitsamt der anzuzeigenden Visite und zugehoeriger medizinischer
	 * Daten.
	 * 
	 * @pre stage darf nicht null sein
	 * @pre oldScene darf nicht null sein
	 * @param stage     das aktive Fenster wird als Parameter uebergeben.
	 * @param oldScene  das alte Fenster.
	 * @param visitenAL ArrayList der Visiten, die angezeigt werden sollen.
	 */
	public VisiteMenueFormular(Stage stage, Scene oldScene, ArrayList<Visite> visitenAL) {
		this.stage = stage;
		this.oldScene = oldScene;

		init(visitenAL);
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
	 * @param visitenAL ArrayList der Visiten, die angezeigt werden sollen.
	 */
	private void init(ArrayList<Visite> visitenAL) {
		kopfUUB = new UeberschriftUndButton("Visitenmen\u00FC", "Hauptmen\u00FC", null);
		String[] buttonSuchenLabel = { null, null, "Visite suchen" };
		suchenBR = new ButtonRow(buttonSuchenLabel);
		String[] labels = { "Vorname:", "Nachname:", "Datum:", "Studie:" };
		eingabenSLTF = new StackedLabelTextFields(labels);
		durchgefuehrt = new CheckBox("Durchgef\u00FChrt");
		durchgefuehrt.setMaxWidth(100);

		listeLVUB = GUIHelfer.<Visite>getStandardListViewUndButtons(null, null, visitenAL, false, true);

		gp = new GridPane();
		gp.addRow(0, kopfUUB.getGridPane());
		gp.addRow(1, durchgefuehrt);
		gp.addRow(3, suchenBR.getGridPane());
		gp.addRow(2, eingabenSLTF.getGridPane());
		gp.addRow(4, listeLVUB.getGridPane());

		float[] zeilenGewichte = { 20f, 5f, 20f, 10f, 45f };
		GUIHelfer.setzeZeilenGewichte(gp, zeilenGewichte);
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

		suchenBR.getButton("Visite suchen").setOnAction(event -> {
			String vorname = eingabenSLTF.getText("Vorname:");
			String nachname = eingabenSLTF.getText("Nachname:");
			String datum = eingabenSLTF.getText("Datum:");
			String studie = eingabenSLTF.getText("Studie:");
			Boolean istdurchgefuehrt = durchgefuehrt.isSelected();

			try {
				ArrayList<Visite> ergebnisseAL = vs.visitenSuchen(vorname, nachname, datum, studie, istdurchgefuehrt);
				VisiteMenueFormular bmf = new VisiteMenueFormular(stage, scene, ergebnisseAL);
			} catch (Exception exception) {
				GUIHelfer.FehlermeldungFormular("Fehler!", "Fehler beim Suchen einer Visite!",
						"Beim Suchen der Visite ist ein Fehler aufgetreten. \u00DCberpruefen Sie Ihre Eingaben auf G\u00FCltigkeit!");
			}
		});

		listeLVUB.getButton("Weiter").setOnAction(event -> {
			Visite visite = listeLVUB.getSelektiertesObjekt();
			if (visite != null) {
				VisiteAnsehenFormular vaf = new VisiteAnsehenFormular(stage, scene, visite,
						visite.getMedizinscheDaten());
			} else {
				GUIHelfer.FehlermeldungFormular("Fehler!", "Es wurde keine Visite angeklickt!",
						"Bitte klicken Sie eine Visite an, um diese ueber Weiter anzeigen lassen zu koennen.");
			}
		});

	}

}
