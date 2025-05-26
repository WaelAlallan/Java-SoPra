package de.wwu.sopra.controller.biobankleitercontroller.raumverwaltungscontroller;

import java.util.ArrayList;
import java.util.Optional;

import de.wwu.sopra.gui.biobankleiter.BiobankLeiterMenueFormular;
import de.wwu.sopra.gui.biobankleiter.raumverwaltung.RaumAnsehenFormular;
import de.wwu.sopra.gui.biobankleiter.raumverwaltung.RaumMenueFormular;
import de.wwu.sopra.gui.helfer.GUIHelfer;
import de.wwu.sopra.gui.helfer.StackedLabelTextFields;
import de.wwu.sopra.model.Raum;
import de.wwu.sopra.steuerung.biobankleiter.RaumSteuerung;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Alert.AlertType;
import javafx.stage.Stage;

/**
 * Controller Klasse zum verwalten der Funktionen der Buttons des GUI Fensters
 * 
 * @author Alexander Leifhelm, Sebastian Huck, Wael Al Allan
 */
public class RaumBearbeitenController {

	/**
	 * Methode die EventHandler des Hauptmenue Buttons erzeugt
	 * 
	 * @pre stage darf nicht null sein
	 * @pre oldScene darf nicht null sein
	 * @param stage das aktuelle Fenster wird uebergeben
	 * @param scene die zuletzt aufgerufene Scene wird uebergeben
	 * @return der Eventhandler fuer den Hauptmenue Button wird uebergeben
	 */
	public EventHandler<ActionEvent> getActionForHauptmenueButton(Stage stage, Scene scene) {
		return new EventHandler<ActionEvent>() {
			/**
			 * Methode zur Beschreibung der Aktion in einem anderen Thread
			 * @param e ActionEvent der Aktion
			 */
			public void handle(ActionEvent e) {
				BiobankLeiterMenueFormular bmf = new BiobankLeiterMenueFormular(stage, scene);
			}
		};
	}

	/**
	 * Methode die EventHandler des Loeschen Buttons erzeugt
	 * 
	 * @pre stage darf nicht null sein
	 * @pre oldScene darf nicht null sein
	 * @param stage das aktuelle Fenster wird uebergeben
	 * @param scene die zuletzt aufgerufene Scene wird uebergeben
	 * @param raum  der geloescht werden soll wird uebergeben
	 * @return der Eventhandler fuer den Loeschen Button wird uebergeben
	 */
	public EventHandler<ActionEvent> getActionForLoeschenButton(Stage stage, Scene scene, Raum raum) {
		return new EventHandler<ActionEvent>() {
			/**
			 * Methode zur Beschreibung der Aktion in einem anderen Thread
			 * @param e ActionEvent der Aktion
			 */
			public void handle(ActionEvent e) {

				Alert alert = new Alert(AlertType.CONFIRMATION);
				alert.setHeaderText("Bitte best\u00e4tigen!");
				alert.setContentText("Sind sie sicher, dass der Raum gel\u00f6scht werden soll?");

				Optional<ButtonType> result = alert.showAndWait();
				if (result.get() == ButtonType.OK) {
					try {
						RaumSteuerung rs = new RaumSteuerung();
						rs.raumLoeschen(raum);
						ArrayList<Raum> raeumeAL = rs.getRaeume("", "", "");
						RaumMenueFormular rmf = new RaumMenueFormular(stage, scene, raeumeAL);
					} catch (Exception exception) {
						GUIHelfer.AutomatischesFehlermeldungFormular("Loeschen", exception);
					}
				}
			}
		};
	}

	/**
	 * Methode die EventHandler des Abbrechen Buttons erzeugt
	 * 
	 * @pre stage darf nicht null sein
	 * @pre oldScene darf nicht null sein
	 * @param stage    das aktuelle Fenster wird uebergeben
	 * @param oldScene die zuletzt aufgerufene Scene wird uebergeben
	 * @return der Eventhandler fuer den Abbrechen Button wird uebergeben
	 */
	public EventHandler<ActionEvent> getActionForAbbrechenButton(Stage stage, Scene oldScene) {
		return new EventHandler<ActionEvent>() {
			/**
			 * Methode zur Beschreibung der Aktion in einem anderen Thread
			 * @param e ActionEvent der Aktion
			 */
			public void handle(ActionEvent e) {
				stage.setScene(oldScene);
			}
		};
	}

	/**
	 * Methode die EventHandler des Bestaetigen Buttons erzeugt
	 * 
	 * @pre stage darf nicht null sein
	 * @pre oldScene darf nicht null sein
	 * @param stage        das aktuelle Fenster wird uebergeben
	 * @param scene        die zuletzt aufgerufene Scene wird uebergeben
	 * @param raum         der Raum der bearbeitet wurde wird uebergeben
	 * @param eingabenSLTF StackedLabelTextFields wird uebergeben um eingaben
	 *                     auszulesen
	 * @return der Eventhandler fuer den Bestaetigen Button wird uebergeben
	 */
	public EventHandler<ActionEvent> getActionForBestaetigenButton(Stage stage, Scene scene, Raum raum,
			StackedLabelTextFields eingabenSLTF) {
		return new EventHandler<ActionEvent>() {
			/**
			 * Methode zur Beschreibung der Aktion in einem anderen Thread
			 * @param e ActionEvent der Aktion
			 */
			public void handle(ActionEvent e) {
				String name = eingabenSLTF.getText("Name:");
				String standort = eingabenSLTF.getText("Standort:");
				String temp = eingabenSLTF.getText("Eingestellte Temperatur [\u00B0C]:");
				RaumSteuerung rs = new RaumSteuerung();

				try {
					rs.raumBearbeiten(raum, name, standort, temp);
					RaumAnsehenFormular raf = new RaumAnsehenFormular(stage, scene, raum);
				} catch (Exception exception) {
					GUIHelfer.AutomatischesFehlermeldungFormular("Bearbeiten", exception);
				}
			}
		};
	}

}
