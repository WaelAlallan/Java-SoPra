package de.wwu.sopra.gui.biobankleiter.kuehlschrankverwaltung;

import java.util.ArrayList;

import de.wwu.sopra.gui.biobankleiter.BiobankLeiterMenueFormular;
import de.wwu.sopra.gui.helfer.ButtonRow;
import de.wwu.sopra.gui.helfer.GUIHelfer;
import de.wwu.sopra.gui.helfer.ListViewUndButtons;
import de.wwu.sopra.gui.helfer.UeberschriftUndButton;
import de.wwu.sopra.model.Kuehlschrank;
import de.wwu.sopra.model.Raum;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

/**
 * GUI Klasse zum erstellen des GUI Fensters Kuehlschrank Menue
 * 
 * @author Alexander Leifhelm, Sebastian Huck, Wael Al Allan
 */
public class KuehlschrankMenueFormular {

	private Stage stage;
	private Scene scene, oldScene;
	private GridPane gp;
	private UeberschriftUndButton kopfUUB;
	private ButtonRow buttonsBR;
	private ListViewUndButtons<Kuehlschrank> listeLVUB;
	private Raum raum;

	/**
	 * Bei der Erzeugen des Objekts wird die Stage, Scene und der Kuehlschrank mit
	 * den uebergebenen Parametern erzeugt. Ausserdem wird die init- und die
	 * beabachteAktion-Methode aufgerufen. Des Weiteren wird die Scene gesetzt.
	 * 
	 * @pre stage darf nicht null sein
	 * @pre oldScene darf nicht null sein
	 * @param stage            das aktive Fenster wird uebergeben
	 * @param oldScene         das zuletzt geoeffnete Fenster wird uebergeben
	 * @param kuehlschraenkeAL Die Liste der Kuehlschraenke welche angezeigt werden
	 *                         soll
	 */
	public KuehlschrankMenueFormular(Stage stage, Scene oldScene, ArrayList<Kuehlschrank> kuehlschraenkeAL) {
		this.stage = stage;
		this.oldScene = oldScene;

		init(kuehlschraenkeAL);
		beobachteAktionen();
		stage.setScene(scene);
	}

	/**
	 * Initialisierungs-Methode zum initialisieren der GUI-ELemente.
	 * @param kuehlschraenkeAL die anzuzeigenden Kuehlschraenke als Arraylist
	 */
	void init(ArrayList<Kuehlschrank> kuehlschraenkeAL) {

		kopfUUB = new UeberschriftUndButton("K\u00FChlschrank-\u00DCbersicht", "Hauptmen\u00FC", null);
		String[] buttons = { null, "K\u00FChlschrank filtern" };
		buttonsBR = new ButtonRow(buttons);
		listeLVUB = GUIHelfer.<Kuehlschrank>getStandardListViewUndButtons(null, null, kuehlschraenkeAL, true, true);

		gp = new GridPane();
		gp.addRow(0, kopfUUB.getGridPane());
		gp.addRow(1, buttonsBR.getGridPane());
		gp.addRow(2, listeLVUB.getGridPane());

		GUIHelfer.setzeZeilenGewichte(gp, GUIHelfer.gibGleicheGewichte(gp.getChildren().size()));
		GUIHelfer.setzeSpaltenGewichte(gp, GUIHelfer.gibGleicheGewichte(1));

		gp.setPadding(new Insets(10, 10, 10, 10));
		scene = new Scene(gp, 1280, 720);
	}

	/**
	 * Methode beobachteAktionen, die je nach Useraction entweder per actionHandler
	 * ein neues BioBankLeiterFormular aufruft. Des Weiteren kann sich ein
	 * Raumauswahlmenue und ein Hinzufuegen Fenster. Bei Abbrechen wird wieder in
	 * den alten Zustand zurueckkehrt. Bei Weiter wird ein neues
	 * kuehlschrankAnsehenFormular erzeugt.
	 */
	void beobachteAktionen() {

		kopfUUB.getButton("Hauptmen\u00FC").setOnAction(event -> {
			BiobankLeiterMenueFormular bmf = new BiobankLeiterMenueFormular(stage, scene);
		});

		buttonsBR.getButton("K\u00FChlschrank filtern").setOnAction(event -> {
			KuehlschrankFilternFormular kff = new KuehlschrankFilternFormular(stage, scene);
		});

		listeLVUB.getButton("Weiter").setOnAction(event -> {
			Kuehlschrank ks = listeLVUB.getSelektiertesObjekt();
			if (ks != null) {
				KuehlschrankAnsehenFormular baf = new KuehlschrankAnsehenFormular(stage, scene, ks);
			} else {
				GUIHelfer.AutomatischesFehlermeldungFormular("Auswahl", null);
			}
		});

		listeLVUB.getButton("Hinzuf\u00FCgen").setOnAction(event -> {
			KuehlschrankHinzufuegenFormular khf = new KuehlschrankHinzufuegenFormular(stage, scene);
		});

	}

}
