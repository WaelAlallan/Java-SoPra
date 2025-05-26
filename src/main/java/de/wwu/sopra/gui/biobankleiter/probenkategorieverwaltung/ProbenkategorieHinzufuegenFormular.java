package de.wwu.sopra.gui.biobankleiter.probenkategorieverwaltung;

import java.util.ArrayList;

import de.wwu.sopra.gui.biobankleiter.BiobankLeiterMenueFormular;
import de.wwu.sopra.gui.helfer.ButtonRow;
import de.wwu.sopra.gui.helfer.GUIHelfer;
import de.wwu.sopra.gui.helfer.StackedLabelTextFields;
import de.wwu.sopra.gui.helfer.UeberschriftUndButton;
import de.wwu.sopra.model.ProbenKategorie;
import de.wwu.sopra.steuerung.biobankleiter.ProbentypHinzufuegenSteuerung;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

/**
 * Klasse zum erstellen des GUI - Fensters ProbenkategorieHinzufuegen
 * 
 * @author Alexander Leifhelm, Sebastian Huck, Wael Al Allan
 */
public class ProbenkategorieHinzufuegenFormular {
	private Stage stage;
	private Scene scene, oldScene;
	private UeberschriftUndButton kopfUUB;
	private StackedLabelTextFields eingabenSLTF;
	private ButtonRow bodenBR;
	private GridPane gp;

	/**
	 * Konstruktor der Klasse
	 * 
	 * @pre stage darf nicht null sein
	 * @pre oldScene darf nicht null sein
	 * @param stage    bekommt das aktive Fenster uebergeben
	 * @param oldScene bekommt die alte Scene ebenfalls uebergeben
	 */
	public ProbenkategorieHinzufuegenFormular(Stage stage, Scene oldScene) {
		this.stage = stage;
		this.oldScene = oldScene;

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
		kopfUUB = new UeberschriftUndButton("Probenkategorie Hinzuf\u00FCgen", "Hauptmen\u00FC", null);
		String[] labels = { "Name:" };
		eingabenSLTF = new StackedLabelTextFields(labels);
		eingabenSLTF.bearbeitenModus();

		String[] buttonLabels = { "Abbrechen", null, "Hinzuf\u00FCgen" };
		bodenBR = new ButtonRow(buttonLabels);

		gp = new GridPane();
		gp.addRow(0, kopfUUB.getGridPane());
		gp.addRow(1, eingabenSLTF.getGridPane());
		gp.addRow(2, bodenBR.getGridPane());

		GUIHelfer.setzeZeilenGewichte(gp, GUIHelfer.gibGleicheGewichte(gp.getChildren().size()));
		GUIHelfer.setzeSpaltenGewichte(gp, GUIHelfer.gibGleicheGewichte(1));

		gp.setPadding(new Insets(10, 10, 10, 10));
		scene = new Scene(gp, 1280, 720);
	}

	/**
	 * Initialisierungsmethode der Buttons der GUI Klasse
	 */
	private void beobachteAktionen() {
		kopfUUB.getButton("Hauptmen\u00FC").setOnAction(new EventHandler<ActionEvent>() {
			@Override /**
						 * Enqueues an action to be performed on a different thread than your own
						 */
			public void handle(ActionEvent e) {
				BiobankLeiterMenueFormular blmf = new BiobankLeiterMenueFormular(stage, scene);
			}
		});

		bodenBR.getButton("Abbrechen").setOnAction(new EventHandler<ActionEvent>() {
			@Override /**
						 * Enqueues an action to be performed on a different thread than your own
						 */
			public void handle(ActionEvent e) {
				stage.setScene(oldScene);
			}
		});

		bodenBR.getButton("Hinzuf\u00FCgen").setOnAction(new EventHandler<ActionEvent>() {
			@Override /**
						 * Enqueues an action to be performed on a different thread than your own
						 */
			public void handle(ActionEvent e) {
				try {
					ProbentypHinzufuegenSteuerung phs = new ProbentypHinzufuegenSteuerung();
					phs.probentypHinzufuegen(eingabenSLTF.getText("Name:"));
					ArrayList<ProbenKategorie> gefundeneProbenkategorien = phs.getProbenKategorie("");
					ProbenkategorieMenueFormular pmf = new ProbenkategorieMenueFormular(stage, scene,
							gefundeneProbenkategorien);
				} catch (Exception exception) {
					GUIHelfer.AutomatischesFehlermeldungFormular("Hinzufuegen", exception);
				}
			}
		});
	}

}
