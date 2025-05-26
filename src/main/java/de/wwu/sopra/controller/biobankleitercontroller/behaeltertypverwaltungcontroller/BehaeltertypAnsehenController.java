package de.wwu.sopra.controller.biobankleitercontroller.behaeltertypverwaltungcontroller;

import de.wwu.sopra.gui.biobankleiter.BiobankLeiterMenueFormular;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 * Controller Klasse zum verwalten der Funktionen der Buttons des GUI Fensters
 * 
 * @author Alexander Leifhelm, Sebastian Huck, Wael Al Allan
 */
public class BehaeltertypAnsehenController {

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
				BiobankLeiterMenueFormular blmf = new BiobankLeiterMenueFormular(stage, scene);
			}
		};
	}
}
