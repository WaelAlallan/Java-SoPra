package de.wwu.sopra.controller.biobankleitercontroller.raumverwaltungscontroller;

import java.util.ArrayList;

import de.wwu.sopra.gui.biobankleiter.BiobankLeiterMenueFormular;
import de.wwu.sopra.gui.biobankleiter.raumverwaltung.RaumMenueFormular;
import de.wwu.sopra.gui.helfer.GUIHelfer;
import de.wwu.sopra.gui.helfer.StackedLabelTextFields;
import de.wwu.sopra.model.Raum;
import de.wwu.sopra.steuerung.biobankleiter.RaumSteuerung;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 * Controller Klasse zum verwalten der Funktionen der Buttons des GUI Fensters
 * 
 * @author Alexander Leifhelm, Sebastian Huck, Wael Al Allan
 */
public class RaumFilternController {

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
	 * Methode die EventHandler des Filtern Buttons erzeugt
	 * 
	 * @pre stage darf nicht null sein
	 * @pre oldScene darf nicht null sein
	 * @param stage        das aktuelle Fenster wird uebergeben
	 * @param scene        die zuletzt aufgerufene Scene wird uebergeben
	 * @param eingabenSLTF StackedLabelTextFields wird uebergeben um eingaben
	 *                     auszulesen
	 * @return der Eventhandler fuer den Filtern Button wird uebergeben
	 */
	public EventHandler<ActionEvent> getActionForFilternButton(Stage stage, Scene scene,
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
					ArrayList<Raum> gefilterteRaeume = rs.getRaeume(name, standort, temp);
					RaumMenueFormular rmf = new RaumMenueFormular(stage, scene, gefilterteRaeume);
				} catch (Exception exception) {
					GUIHelfer.AutomatischesFehlermeldungFormular("Filtern", exception);
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

}
