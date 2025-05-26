package de.wwu.sopra.gui.biobankleiter.kuehlschrankverwaltung;

import java.util.ArrayList;
import java.util.Optional;

import de.wwu.sopra.gui.biobankleiter.BiobankLeiterMenueFormular;
import de.wwu.sopra.gui.helfer.ButtonRow;
import de.wwu.sopra.gui.helfer.GUIHelfer;
import de.wwu.sopra.gui.helfer.StackedLabelTextFields;
import de.wwu.sopra.gui.helfer.UeberschriftUndButton;
import de.wwu.sopra.model.Kuehlschrank;
import de.wwu.sopra.model.Raum;
import de.wwu.sopra.steuerung.biobankleiter.KuehlschrankSteuerung;
import de.wwu.sopra.steuerung.biobankleiter.RaumSteuerung;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

/**
 * GUI Klasse zum erstellen des GUI Fensters Kuehlschrank hinzufuegen
 * 
 * @author Alexander Leifhelm, Sebastian Huck, Wael Al Allan
 *
 */
public class KuehlschrankHinzufuegenFormular {

	private Stage stage;
	private Scene scene, oldScene;
	private GridPane gp;
	private UeberschriftUndButton kopfUUB;
	private ButtonRow buttonsBR1, buttonsBR2;
	private StackedLabelTextFields eingabenSLTF1, eingabenSLTF2, eingabenSLTF3;
	private Raum raum;

	/**
	 * Bei der Erzeugen des Objekts wird die Stage, Scene und der Kuehlschrank mit
	 * den uebergebenen Parametern erzeugt. Ausserdem wird die init- und die
	 * beabachteAktion-Methode aufgerufen. Des Weiteren wird die Scene gesetzt.
	 * 
	 * @pre stage darf nicht null sein
	 * @pre oldScene darf nicht null sein
	 * @param stage    das aktive Fenster wird uebergeben
	 * @param oldScene das zuletzt geoeffnete Fenster wird uebergeben
	 */
	public KuehlschrankHinzufuegenFormular(Stage stage, Scene oldScene) {
		this.stage = stage;
		this.oldScene = oldScene;
		this.raum = null;

		init();
		beobachteAktionen();
		stage.setScene(scene);
	}

	/**
	 * Initialisierungs-Methode zum initialisieren der GUI-ELemente.
	 */
	private void init() {
		kopfUUB = new UeberschriftUndButton("K\u00FChlschr\u00E4nke hinzuf\u00FCgen", "Hauptmen\u00FC", null);
		String[] label1 = { "Name:" };
		String[] label2 = { "Raum:" };
		String[] label3 = { "Eingestellte Temperatur [\u00B0C]:" };
		eingabenSLTF1 = new StackedLabelTextFields(label1);
		eingabenSLTF2 = new StackedLabelTextFields(label2);
		eingabenSLTF2.ansehenModus();
		eingabenSLTF3 = new StackedLabelTextFields(label3);

		String[] buttons1 = { null, "Raum W\u00E4hlen" };
		buttonsBR1 = new ButtonRow(buttons1);
		String[] buttons2 = { "Abbrechen", null, "Hinzuf\u00FCgen" };
		buttonsBR2 = new ButtonRow(buttons2);

		gp = new GridPane();
		gp.addRow(0, kopfUUB.getGridPane());
		gp.addRow(1, eingabenSLTF1.getGridPane());
		gp.addRow(2, buttonsBR1.getGridPane());
		gp.addRow(3, eingabenSLTF2.getGridPane());
		gp.addRow(4, eingabenSLTF3.getGridPane());
		gp.addRow(5, buttonsBR2.getGridPane());

		GUIHelfer.setzeZeilenGewichte(gp, GUIHelfer.gibGleicheGewichte(gp.getChildren().size()));
		GUIHelfer.setzeSpaltenGewichte(gp, GUIHelfer.gibGleicheGewichte(1));

		gp.setPadding(new Insets(10, 10, 10, 10));
		scene = new Scene(gp, 1280, 720);
	}

	/**
	 * Methode beobachteAktionen, die je nach Useraction entweder per actionHandler
	 * ein neues BioBankLeiterFormular aufruft. Des Weiteren kann sich ein
	 * Raumauswahlmenue und ein Hinzufuegen Fenster. Bei Abbrechen wird wieder in
	 * den alten Zustand zurueckkehrt.
	 */
	private void beobachteAktionen() {
		KuehlschrankSteuerung kss = new KuehlschrankSteuerung();
		RaumSteuerung rs = new RaumSteuerung();

		kopfUUB.getButton("Hauptmen\u00FC").setOnAction(event -> {
			BiobankLeiterMenueFormular bmf = new BiobankLeiterMenueFormular(stage, scene);
		});

		buttonsBR1.getButton("Raum W\u00E4hlen").setOnAction(event -> {
			ArrayList<Raum> raeumeAL = rs.getRaeume();
			RaumWaehlenFormular rwf = new RaumWaehlenFormular(raeumeAL, raum);
			Optional<Raum> auswahl = rwf.showAndWait();

			if (auswahl.isPresent()) {
				if ((auswahl.get().getName() != null) && (auswahl.get().getStandort() != null)) {
					raum = auswahl.get();
					eingabenSLTF2.getTextField("Raum:").setText(raum.toString());
				}
			}
		});

		buttonsBR2.getButton("Abbrechen").setOnAction(event -> {
			stage.setScene(oldScene);
		});

		buttonsBR2.getButton("Hinzuf\u00FCgen").setOnAction(event -> {
			String name = eingabenSLTF1.getText("Name:");
			String temp = eingabenSLTF3.getText("Eingestellte Temperatur [\u00B0C]:");

			try {
				kss.kuehlschrankHinzufuegen(name, temp, raum);
				ArrayList<Kuehlschrank> kuehlschraenkeAL = kss.getKuehlschraenke();
				KuehlschrankMenueFormular rmf = new KuehlschrankMenueFormular(stage, scene, kuehlschraenkeAL);
			} catch (Exception exception) {
				GUIHelfer.AutomatischesFehlermeldungFormular("Hinzufuegen", exception);
			}
		});

	}

}
