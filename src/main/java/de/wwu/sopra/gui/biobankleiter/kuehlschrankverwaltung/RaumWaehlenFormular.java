package de.wwu.sopra.gui.biobankleiter.kuehlschrankverwaltung;

import java.util.ArrayList;

import de.wwu.sopra.gui.helfer.ButtonRow;
import de.wwu.sopra.gui.helfer.GUIHelfer;
import de.wwu.sopra.gui.helfer.ListViewUndButtons;
import de.wwu.sopra.gui.helfer.UeberschriftUndButton;
import de.wwu.sopra.model.Raum;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.control.Dialog;
import javafx.scene.layout.GridPane;

/**
 * Klasse, die der Auswahl und Rueckgabe eines Raumes dient.
 * 
 * @author Alexander Leifhelm
 *
 */
public class RaumWaehlenFormular extends Dialog<Raum> {
	private GridPane gp;
	private UeberschriftUndButton kopfUUB;
	private ListViewUndButtons<Raum> listeLVUB;
	private ButtonRow bodenBR;
	private ArrayList<Raum> raeumeAL;
	private Raum vorauswahlRaum;

	/**
	 * Erstellt ein Formular zur Auswahl des Raumes
	 * 
	 * @param raeumeAL ArrayList mit den anzuzeigenden Raeumen
	 */
	public RaumWaehlenFormular(ArrayList<Raum> raeumeAL) {
		this.raeumeAL = raeumeAL;
		this.vorauswahlRaum = null;

		init();
		beobachteAktionen();

		this.getDialogPane().setPrefSize(1280, 720);
		this.getDialogPane().setContent(gp);
	}

	/**
	 * Erstellt ein Formular zur Auswahl des Raumes mit Vorauswahl
	 * 
	 * @param raeumeAL       ArrayList mit den anzuzeigenden Raeumen
	 * @param vorauswahlRaum Vorauswahl des Raumes (z.B. bei Bearbeitungen)
	 */
	public RaumWaehlenFormular(ArrayList<Raum> raeumeAL, Raum vorauswahlRaum) {
		this.raeumeAL = raeumeAL;
		this.vorauswahlRaum = vorauswahlRaum;

		init();
		beobachteAktionen();

		this.getDialogPane().setPrefSize(1280, 720);
		this.getDialogPane().setContent(gp);
	}

	/**
	 * Initialisierungs-Methode zum initialisieren der GUI-ELemente.
	 */
	private void init() {
		kopfUUB = new UeberschriftUndButton("Raum w\u00E4hlen", null, null);
		String[] buttons = { "Abbrechen", "Best\u00E4tigen" };
		bodenBR = new ButtonRow(buttons);

		gp = new GridPane();
		gp.addRow(0, kopfUUB.getGridPane());
		gp.addRow(2, bodenBR.getGridPane());

		listeLVUB = GUIHelfer.<Raum>getStandardListViewUndButtons(null, null, raeumeAL, false, false);
		if (vorauswahlRaum != null) {
			listeLVUB.setSelektiertesObjekt(vorauswahlRaum);
		}
		gp.addRow(1, listeLVUB.getGridPane());

		GUIHelfer.setzeZeilenGewichte(gp, GUIHelfer.gibGleicheGewichte(gp.getChildren().size()));
		GUIHelfer.setzeSpaltenGewichte(gp, GUIHelfer.gibGleicheGewichte(1));

		gp.setPadding(new Insets(10, 10, 10, 10));
	}

	/**
	 * Methode beobachteAktionen, die je nach Useraction ein neues Formular aufruft.
	 * Bei Abbrechen wird wieder in das vorherige Formular gewechselt. Bei
	 * Bestaetigen wird der Raum dem vorherigen Formular zurueckgegeben.
	 */
	private void beobachteAktionen() {
		bodenBR.getButton("Best\u00E4tigen").setOnAction(new EventHandler<ActionEvent>() {
			@Override
			/**
			 * Methode zur Beschreibung der Aktion in einem anderen Thread
			 * @param e ActionEvent der Aktion
			 */
			public void handle(ActionEvent e) {
				Raum raum = listeLVUB.getSelektiertesObjekt();
				if (raum != null)
					setResult(raum);
				else
					GUIHelfer.AutomatischesFehlermeldungFormular("Auswahl", null);
			}
		});

		bodenBR.getButton("Abbrechen").setOnAction(new EventHandler<ActionEvent>() {
			@Override
			/**
			 * Methode zur Beschreibung der Aktion in einem anderen Thread
			 * @param e ActionEvent der Aktion
			 */
			public void handle(ActionEvent e) {
				setResult(new Raum(null, null));
			}
		});

	}
}
