package de.wwu.sopra.gui.biobankleiter.raumverwaltung;

import java.util.ArrayList;

import de.wwu.sopra.controller.biobankleitercontroller.raumverwaltungscontroller.RaumMenueController;
import de.wwu.sopra.gui.helfer.ButtonRow;
import de.wwu.sopra.gui.helfer.GUIHelfer;
import de.wwu.sopra.gui.helfer.ListViewUndButtons;
import de.wwu.sopra.gui.helfer.UeberschriftUndButton;
import de.wwu.sopra.model.Raum;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

/**
 * Klasse fuer das Formular zur Uebersicht der Raeume.
 * 
 * @author Alexander Leifhelm, Sebastian Huck, Wael Al Allan
 */
public class RaumMenueFormular {
	private Stage stage;
	private Scene scene, oldScene;
	private GridPane gp;
	private UeberschriftUndButton kopfUUB;
	private ButtonRow buttonsBR;
	private ListViewUndButtons<Raum> listeLVUB;

	/**
	 * Bei der Erzeugen des Objekts wird die Stage, Scene und Raeume mit den
	 * uebergebenen Parametern erzeugt. Ausserdem wird die init- und die
	 * beabachteAktion-Methode aufgerufen. Des Weiteren wird die Scene gesetzt.
	 * 
	 * @pre stage darf nicht null sein
	 * @pre oldScene darf nicht null sein
	 * @param stage    parent stage
	 * @param oldScene parent scene
	 * @param raeumeAL ArrayList mit den uebergebenen Raeumen
	 */
	public RaumMenueFormular(Stage stage, Scene oldScene, ArrayList<Raum> raeumeAL) {
		this.stage = stage;
		this.oldScene = oldScene;

		init(raeumeAL);
		beobachteAktionen();
		stage.setScene(scene);
	}

	/**
	 * Initialisierungs-Methode zum initialisieren der GUI-ELemente.
	 * @param raeumeAL anzuzeigenden Raeume in einer Arraylist uebergeben
	 */
	void init(ArrayList<Raum> raeumeAL) {

		kopfUUB = new UeberschriftUndButton("Raum-\u00DCbersicht", "Hauptmen\u00FC", null);
		String[] buttons = { null, "Raum filtern" };
		buttonsBR = new ButtonRow(buttons);
		listeLVUB = GUIHelfer.<Raum>getStandardListViewUndButtons(null, null, raeumeAL, true, true);

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
	 * Erstellt einen neuen RaumMenueController und setzt fuer bestimmte
	 * Klassenelemnte Button. Der Controller verarbeitet die jeweiligen Actions.
	 */
	void beobachteAktionen() {
		RaumMenueController rmc = new RaumMenueController();
		kopfUUB.getButton("Hauptmen\u00FC").setOnAction(rmc.getActionForHauptmenueButton(stage, oldScene));
		buttonsBR.getButton("Raum filtern").setOnAction(rmc.getActionForRaumfilternButton(stage, scene));
		listeLVUB.getButton("Weiter").setOnAction(rmc.getActionForWeiterButton(stage, scene, listeLVUB));
		listeLVUB.getButton("Hinzuf\u00FCgen").setOnAction(rmc.getActionForHinzufuegenButton(stage, scene));
	}

}
