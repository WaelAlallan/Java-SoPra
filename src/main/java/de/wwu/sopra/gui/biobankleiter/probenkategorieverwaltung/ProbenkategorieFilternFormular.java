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
 * Klasse fuer das Formular zum Filtern einer Probenkategorie.
 * 
 * @author Alexander Leifhelm, Sebastian Huck, Wael Al Allan
 *
 */
public class ProbenkategorieFilternFormular {
	private Stage stage;
	private Scene scene, oldScene;
	private UeberschriftUndButton kopfUUB;
	private StackedLabelTextFields eingabenSLTF;
	private ButtonRow bodenBR;
	private GridPane gp;

	/**
	 * Bei der Erzeugen des Objekts wird die Stage und die Scene mit den
	 * uebergebenen Parametern erzeugt. Ausserdem wird die init- und die
	 * beabachteAktion-Methode aufgerufen. Des Weiteren wird die Scene gesetzt.
	 * 
	 * @pre stage darf nicht null sein
	 * @pre oldScene darf nicht null sein
	 * @param stage    das aktuelle Fenster wird uebergeben
	 * @param oldScene das zuletzt offene Fenster wird ebenfalls uebergeben
	 */
	public ProbenkategorieFilternFormular(Stage stage, Scene oldScene) {
		this.stage = stage;
		this.oldScene = oldScene;

		init();
		beobachteAktionen();

		stage.setScene(scene);
	}

	/**
	 * Initialisierungs-Methode zum initialisieren der GUI-ELemente.
	 */
	private void init() {
		kopfUUB = new UeberschriftUndButton("Probenkategorie filtern", "Hauptmen\u00FC", null);
		String[] labels = { "Name:" };
		eingabenSLTF = new StackedLabelTextFields(labels);
		eingabenSLTF.bearbeitenModus();

		String[] buttonLabels = { "Abbrechen", null, "Filtern" };
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
	 * Methode beobachteAktionen, die je nach Useraction entweder per actionHandler
	 * ein neues BioBankLeiterMenueFormular aufruft, ein Filterfenster erzeugt, das
	 * zum Filtern der Probenkategorie dient oder bei Abbrechen wieder in den alten
	 * Zustand zurueckkehrt.
	 */
	private void beobachteAktionen() {
		kopfUUB.getButton("Hauptmen\u00FC").setOnAction(new EventHandler<ActionEvent>() {
			@Override
			/**
			 * Methode zur Beschreibung der Aktion in einem anderen Thread
			 * @param e ActionEvent der Aktion
			 */
			public void handle(ActionEvent e) {
				BiobankLeiterMenueFormular blmf = new BiobankLeiterMenueFormular(stage, scene);
			}
		});

		bodenBR.getButton("Abbrechen").setOnAction(new EventHandler<ActionEvent>() {
			@Override
			/**
			 * Methode zur Beschreibung der Aktion in einem anderen Thread
			 * @param e ActionEvent der Aktion
			 */
			public void handle(ActionEvent e) {
				stage.setScene(oldScene);
			}
		});

		bodenBR.getButton("Filtern").setOnAction(new EventHandler<ActionEvent>() {
			@Override
			/**
			 * Methode zur Beschreibung der Aktion in einem anderen Thread
			 * @param e ActionEvent der Aktion
			 */
			public void handle(ActionEvent e) {
				try {
					ProbentypHinzufuegenSteuerung phs = new ProbentypHinzufuegenSteuerung();
					ArrayList<ProbenKategorie> gefundeneProbenkategorien = phs
							.getProbenKategorie(eingabenSLTF.getText("Name:"));
					ProbenkategorieMenueFormular pmf = new ProbenkategorieMenueFormular(stage, scene,
							gefundeneProbenkategorien);
				} catch (Exception exception) {
					GUIHelfer.AutomatischesFehlermeldungFormular("Filtern", exception);
				}
			}
		});
	}

}
