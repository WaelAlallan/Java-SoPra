package de.wwu.sopra.gui.mta;

import java.util.ArrayList;

import de.wwu.sopra.gui.helfer.ButtonRow;
import de.wwu.sopra.gui.helfer.GUIHelfer;
import de.wwu.sopra.gui.helfer.ListViewUndButtons;
import de.wwu.sopra.gui.helfer.UeberschriftUndButton;
import de.wwu.sopra.model.ProbenBehaelterTyp;
import de.wwu.sopra.model.ProbenKategorie;
import de.wwu.sopra.steuerung.biobankleiter.ProbenBehaeltertypHinzufuegenSteuerung;
import de.wwu.sopra.steuerung.biobankleiter.ProbentypHinzufuegenSteuerung;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Dialog;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import javafx.util.Pair;

/**
 * GUI-Klasse, die dazu dient eine probenKategorie und einen probenbehaeltertyp
 * zu waehlen. das Fenster wird als Dialog angezeigt.
 * 
 * @author Wael Alallan
 */
public class ProbenbehaeltertypUndKategorieWaehlenFormular extends Dialog<Pair<ProbenKategorie, ProbenBehaelterTyp>> {

	private Stage stage;
	private Scene scene;
	private UeberschriftUndButton kopfUUB;
	private ButtonRow buttonsBR;
	private GridPane gp;
	private ListViewUndButtons<ProbenKategorie> listeLVUBKategorie;
	private ListViewUndButtons<ProbenBehaelterTyp> listeLVUBBehaeltertyp;
	private ArrayList<ProbenKategorie> probenKategorieAL;
	private ArrayList<ProbenBehaelterTyp> probenbehaeltertypAL;
	private Pair<ProbenKategorie, ProbenBehaelterTyp> auswahlKategorieBehaeltertyp;

	/**
	 * Bei der Erzeugen dieses Objekts wird die ProbenKategorieliste und die
	 * Probenbehaeltertypliste mit den uebergebenen Parametern erzeugt. Ausserdem
	 * wird die init- und die beabachteAktion-Methode aufgerufen
	 * 
	 * @param probenKategorieAL    - Liste der ProbenKategorien im System
	 * @param probenbehaeltertypAL - Liste der Probenbehaeltertypen im System
	 */
	public ProbenbehaeltertypUndKategorieWaehlenFormular(ArrayList<ProbenKategorie> probenKategorieAL,
			ArrayList<ProbenBehaelterTyp> probenbehaeltertypAL) {
		this.probenKategorieAL = probenKategorieAL;
		this.probenbehaeltertypAL = probenbehaeltertypAL;

		init();
		beobachteAktionen();

		this.getDialogPane().setPrefSize(1280, 720);
		this.getDialogPane().setContent(gp);
	}

	/**
	 * Initialisierungs-Methode zum initialisieren der GUI-ELemente.
	 */
	private void init() {
		kopfUUB = new UeberschriftUndButton("Probenbeh\u00E4ltertyp und Kategorie" + "\n" + "w\u00E4hlen", "Hauptmen\u00FC", null);

		ProbentypHinzufuegenSteuerung phS = new ProbentypHinzufuegenSteuerung();
		listeLVUBKategorie = GUIHelfer.<ProbenKategorie>getStandardListViewUndButtons("Probenkategorie:", null,
				probenKategorieAL, false, false);

		ProbenBehaeltertypHinzufuegenSteuerung pbtS = new ProbenBehaeltertypHinzufuegenSteuerung();
		listeLVUBBehaeltertyp = GUIHelfer.<ProbenBehaelterTyp>getStandardListViewUndButtons("Beh\u00E4ltertyp:", null,
				probenbehaeltertypAL, false, false);

		String[] buttonLabels = { "Abbrechen", null, "Best\u00E4tigen" };
		buttonsBR = new ButtonRow(buttonLabels);

		gp = new GridPane();
		gp.addRow(0, kopfUUB.getGridPane());
		gp.addRow(1, listeLVUBKategorie.getGridPane());
		gp.addRow(2, listeLVUBBehaeltertyp.getGridPane());
		gp.addRow(3, buttonsBR.getGridPane());
		GridPane.setColumnSpan(kopfUUB.getGridPane(), 2);

		GUIHelfer.setzeZeilenGewichte(gp, GUIHelfer.gibGleicheGewichte(gp.getChildren().size()));
		GUIHelfer.setzeSpaltenGewichte(gp, GUIHelfer.gibGleicheGewichte(1));

		gp.setPadding(new Insets(10, 10, 10, 10));
	}

	/**
	 * Methode, die die Anwenderaktionen beobachtet und darauf reagiert. Je nachdem,
	 * was der Benutzer betaetigt, wird man entweder ins MTA-hauptmenue oder in die
	 * vorherige Scene gelangen. oder bei Bestaetigen kann man die Auswahl
	 * bestaetigen.
	 */
	private void beobachteAktionen() {
		buttonsBR.getButton("Best\u00E4tigen").setOnAction(event -> {
			ProbenKategorie probenKategorie = listeLVUBKategorie.getSelektiertesObjekt();
			ProbenBehaelterTyp probenbehaeltertyp = listeLVUBBehaeltertyp.getSelektiertesObjekt();
			try {
				if (probenKategorie != null && probenbehaeltertyp != null) {
					setResult(new Pair<ProbenKategorie, ProbenBehaelterTyp>(probenKategorie, probenbehaeltertyp));
				} else {
					throw new IllegalArgumentException(
							"Eine Kategorie und ein Behaeltertyp m\u00FCssen ausgew\u00E4hlt werden.");
				}
			} catch (IllegalArgumentException fehler) {
				GUIHelfer.FehlermeldungFormular("Fehler", "Die Eingaben sind nicht korrekt.", fehler.getMessage());
			}
		});

		kopfUUB.getButton("Hauptmen\u00FC").setOnAction(event -> {
			MtaMenueFormular mmf = new MtaMenueFormular(stage, scene);
		});

		buttonsBR.getButton("Abbrechen").setOnAction(event -> {
			setResult(new Pair<ProbenKategorie, ProbenBehaelterTyp>(null, null));
		});
	}

}