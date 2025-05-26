package de.wwu.sopra.gui.studynurse.patientenverwaltung;

import java.util.ArrayList;

import de.wwu.sopra.gui.helfer.ButtonRow;
import de.wwu.sopra.gui.helfer.GUIHelfer;
import de.wwu.sopra.gui.helfer.ListViewUndButtons;
import de.wwu.sopra.gui.helfer.StackedLabelTextFields;
import de.wwu.sopra.gui.helfer.UeberschriftUndButton;
import de.wwu.sopra.model.Studie;
import de.wwu.sopra.steuerung.helfer.DatumHelfer;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.control.Dialog;
import javafx.scene.layout.GridPane;
import javafx.util.Pair;

/**
 * GUI Klasse zum erstellen des Studie waehlen Fensters 
 * 
 * @author Gruppe 01
 */
public class StudieWaehlenFormular extends Dialog<Pair<Studie, String>> {
	private GridPane gp;
	private UeberschriftUndButton kopfUUB;
	private ListViewUndButtons<Studie> listeLVUB;
	private StackedLabelTextFields eingabenSLTF;
	private ButtonRow bodenBR;
	private ArrayList<Studie> studienAL;
	private Pair<Studie, String> vorauswahlStudieDatum;

	/**
	 * Ein Konstruktor der Klasse bekommmt eine Arraylist von anzuzeigenden Studien uebergeben
	 * @param studienAL die Arraylist der anzuzeigenden Studien
	 */
	public StudieWaehlenFormular(ArrayList<Studie> studienAL) {
		this.studienAL = studienAL;

		init();
		beobachteAktionen();

		this.getDialogPane().setPrefSize(1280, 720);
		this.getDialogPane().setContent(gp);
	}

	/**
	 * Ein Konstruktor der Klasse, bekommt eine Arraylist von Studien uebergeben ebenso wie ein Studie-Datum Pair
	 * @param studienAL die arraylist der anzuzeogenden Studien
	 * @param vorauswahlStudieDatum die vorauswahl als Stude-Datum Pair
	 */
	public StudieWaehlenFormular(ArrayList<Studie> studienAL, Pair<Studie, String> vorauswahlStudieDatum) {
		this.studienAL = studienAL;
		this.vorauswahlStudieDatum = vorauswahlStudieDatum;

		init();
		beobachteAktionen();

		this.getDialogPane().setPrefSize(1280, 720);
		this.getDialogPane().setContent(gp);
	}

	private void init() {
		kopfUUB = new UeberschriftUndButton("Studie w\u00E4hlen", null, null);
		String[] buttons = { "Abbrechen", "Best\u00E4tigen" };
		bodenBR = new ButtonRow(buttons);
		String[] labels = { "Datum\nStudienanfang:" };
		eingabenSLTF = new StackedLabelTextFields(labels);

		gp = new GridPane();
		gp.addRow(0, kopfUUB.getGridPane());
		gp.addRow(1, eingabenSLTF.getGridPane());
		gp.addRow(3, bodenBR.getGridPane());

		listeLVUB = GUIHelfer.<Studie>getStandardListViewUndButtons(null, null, studienAL, false, false);
		if (vorauswahlStudieDatum != null) {
			listeLVUB.setSelektiertesObjekt(vorauswahlStudieDatum.getKey());
			eingabenSLTF.getTextField("Datum\nStudienanfang").setText(vorauswahlStudieDatum.getValue());
		}
		gp.addRow(2, listeLVUB.getGridPane());

		GUIHelfer.setzeZeilenGewichte(gp, GUIHelfer.gibGleicheGewichte(gp.getChildren().size()));
		GUIHelfer.setzeSpaltenGewichte(gp, GUIHelfer.gibGleicheGewichte(1));

		gp.setPadding(new Insets(10, 10, 10, 10));
	}

	private void beobachteAktionen() {
		bodenBR.getButton("Best\u00E4tigen").setOnAction(new EventHandler<ActionEvent>() {
			@Override
			/**
			 * Methode zur Beschreibung der Aktion in einem anderen Thread
			 * @param e ActionEvent der Aktion
			 */
			public void handle(ActionEvent e) {
				Studie studie = listeLVUB.getSelektiertesObjekt();
				String datum = eingabenSLTF.getText("Datum\nStudienanfang:");
				try {
					DatumHelfer.erzeugeDatum(datum);
					if (studie == null)
						GUIHelfer.FehlermeldungFormular("Fehler!", "Es wurde keine Studie angegeben!",
								"Bitte giben Sie eine Studie an, um diese hinzuf\u00FCgen lassen zu koennen.");
					else
						setResult(new Pair<Studie, String>(studie, datum));
				} catch (Exception exception) {
					GUIHelfer.FehlermeldungFormular("Fehler!", "Es wurde kein akzeptiertes Datum angegeben!",
							"Bitte giben Sie ein wohlgeformtes Datum an (DD.MM.YYYY), um dieses hinzuf\u00FCgen lassen zu koennen.");
				}

			}
		});

		bodenBR.getButton("Abbrechen").setOnAction(new EventHandler<ActionEvent>() {
			@Override
			/**
			 * Methode zur Beschreibung der Aktion in einem anderen Thread
			 * @param e ActionEvent der Aktion
			 */
			public void handle(ActionEvent e) {
				setResult(new Pair<Studie, String>(null, null));
			}
		});

	}
}
