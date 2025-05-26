package de.wwu.sopra.gui.biobankleiter.kuehlschrankverwaltung;

import java.util.ArrayList;

import de.wwu.sopra.gui.biobankleiter.BiobankLeiterMenueFormular;
import de.wwu.sopra.gui.helfer.ButtonRow;
import de.wwu.sopra.gui.helfer.GUIHelfer;
import de.wwu.sopra.gui.helfer.StackedLabelTextFields;
import de.wwu.sopra.gui.helfer.UeberschriftUndButton;
import de.wwu.sopra.model.Kuehlschrank;
import de.wwu.sopra.steuerung.biobankleiter.KuehlschrankSteuerung;
import de.wwu.sopra.steuerung.personalleiter.BenutzerSteuerung;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

/**
 * GUI Klasse zum Erstellen des Kuehlschrank ansehen Fensters
 * 
 * @author Alexander Leifhelm, Sebastian Huck, Wael Al Allan
 *
 */
public class KuehlschrankAnsehenFormular {
	private Stage stage;
	private Scene scene, oldScene;
	private GridPane gp;
	private UeberschriftUndButton kopfUUB;
	private ButtonRow buttonsBR;
	private StackedLabelTextFields eingabenSLTF;
	private Kuehlschrank ks;

	/**
	 * Bei der Erzeugen des Objekts wird die Stage, Scene und Kuehlschrank mit den
	 * uebergebenen Parametern erzeugt. Ausserdem wird die init- und die
	 * beabachteAktion-Methode aufgerufen. Des Weiteren wird die Scene gesetzt.
	 * 
	 * @pre stage darf nicht null sein
	 * @pre oldScene darf nicht null sein
	 * @param stage    das aktive Fenster wird uebergeben
	 * @param oldScene das zuletzt geoeffnete Fenster wird uebergeben
	 * @param ks       der kuehlschrank der angesehen werden soll
	 */
	public KuehlschrankAnsehenFormular(Stage stage, Scene oldScene, Kuehlschrank ks) {
		this.stage = stage;
		this.oldScene = oldScene;
		this.ks = ks;

		init();
		beobachteAktionen();
		stage.setScene(scene);
	}

	/**
	 * Initialisierungs-Methode zum initialisieren der GUI-ELemente.
	 */
	void init() {

		kopfUUB = new UeberschriftUndButton("K\u00FChlschrank ansehen", "Hauptmen\u00FC", null);
		String[] labels = { "Name:", "Raum:", "Eingestellte Temperatur [\u00B0C]:" };
		eingabenSLTF = new StackedLabelTextFields(labels);
		eingabenSLTF.ansehenModus();
		eingabenSLTF.getTextField("Name:").setText("" + ks.getName());
		eingabenSLTF.getTextField("Raum:").setText(ks.getRaum().toString());
		eingabenSLTF.getTextField("Eingestellte Temperatur [\u00B0C]:").setText("" + ks.getEingestellteTemperatur());

		String[] buttons = { "Abbrechen", null, "Bearbeiten" };
		buttonsBR = new ButtonRow(buttons);

		gp = new GridPane();
		gp.addRow(0, kopfUUB.getGridPane());
		gp.addRow(1, eingabenSLTF.getGridPane());
		gp.addRow(2, buttonsBR.getGridPane());

		BenutzerSteuerung bs = new BenutzerSteuerung();
		GUIHelfer.setzeZeilenGewichte(gp, GUIHelfer.gibGleicheGewichte(gp.getChildren().size()));
		GUIHelfer.setzeSpaltenGewichte(gp, GUIHelfer.gibGleicheGewichte(1));

		gp.setPadding(new Insets(10, 10, 10, 10));
		scene = new Scene(gp, 1280, 720);
	}

	/**
	 * Methode beobachteAktionen, die je nach Useraction entweder per actionHandler
	 * ein neues BioBankLeiterFormular aufruft, wieder ins Biobankleiter Hauptmenue
	 * zurueckkehrt oder bei Abbrechen wird wieder in den alten Zustand
	 * zurueckkehrt.
	 */
	void beobachteAktionen() {
		KuehlschrankSteuerung kss = new KuehlschrankSteuerung();

		buttonsBR.getButton("Abbrechen").setOnAction(event -> {
			ArrayList<Kuehlschrank> kuehlschrenkeAL = kss.getKuehlschraenke();
			KuehlschrankMenueFormular rmf = new KuehlschrankMenueFormular(stage, scene, kuehlschrenkeAL);
		});

		kopfUUB.getButton("Hauptmen\u00FC").setOnAction(event -> {
			BiobankLeiterMenueFormular bmf = new BiobankLeiterMenueFormular(stage, scene);
		});

		buttonsBR.getButton("Bearbeiten").setOnAction(event -> {
			KuehlschrankBearbeitenFormular kbf = new KuehlschrankBearbeitenFormular(stage, scene, ks);
		});

	}

}