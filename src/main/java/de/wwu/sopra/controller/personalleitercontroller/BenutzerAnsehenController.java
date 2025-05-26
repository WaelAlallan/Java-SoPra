package de.wwu.sopra.controller.personalleitercontroller;

import de.wwu.sopra.gui.personalleiter.BenutzerBearbeitenFormular;
import de.wwu.sopra.gui.personalleiter.BenutzerMenueFormular;
import de.wwu.sopra.gui.personalleiter.PersonalLeiterMenueFormular;
import de.wwu.sopra.model.Benutzer;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 * Controllerklasse imlementiert funktionalitaet der Buttons der GUI
 * 
 * @author Alexander Leifhelm, Sebastian Huck, Wael Al Allan
 */
public class BenutzerAnsehenController {

	/**
	 * Funktion des Hauptmenue Buttons
	 * 
	 * @pre stage darf nicht null sein
	 * @pre oldScene darf nicht null sein
	 * @param stage das aktive Fenster
	 * @param scene Das Benutzeruebersichtsfenster
	 * @return der Eventhandler der die gewuenschte Funktionalitaet liefert
	 */
	public EventHandler<ActionEvent> getActionEventForHauptmenue(Stage stage, Scene scene) {

		return new EventHandler<ActionEvent>() {
			@Override
			/**
			 * Methode zur Beschreibung der Aktion in einem anderen Thread
			 * @param e ActionEvent der Aktion
			 */
			public void handle(ActionEvent e) {
				PersonalLeiterMenueFormular plmf = new PersonalLeiterMenueFormular(stage, scene);
			}
		};

	}

	/**
	 * Funktion des Abbrechen Buttons
	 * 
	 * @pre stage darf nicht null sein
	 * @pre oldScene darf nicht null sein
	 * @param stage das aktive Fenster
	 * @param scene Das Benutzeruebersichtsfenster
	 * @return der Eventhandler der die gewuenschte Funktionalitaet liefert
	 */
	public EventHandler<ActionEvent> getActionEventForAbbrechen(Stage stage, Scene scene) {
		return new EventHandler<ActionEvent>() {
			/**
			 * Methode zur Beschreibung der Aktion in einem anderen Thread
			 * @param e ActionEvent der Aktion
			 */
			public void handle(ActionEvent e) {
				BenutzerMenueFormular bmf = new BenutzerMenueFormular(stage, scene);
			}
		};
	}

	/**
	 * Funktion des Bearbeiten Buttons
	 * 
	 * @pre stage darf nicht null sein
	 * @pre oldScene darf nicht null sein
	 * @param stage    das aktive Fenster
	 * @param scene    Das Benutzer Ansehen Fenster
	 * @param benutzer der zu bearbeitende benutzer wird uebergeben
	 * @return der Eventhandler der die gewuenschte Funktionalitaet liefert
	 */
	public EventHandler<ActionEvent> getActionEventForBearbeiten(Stage stage, Scene scene, Benutzer benutzer) {
		return new EventHandler<ActionEvent>() {
			/**
			 * Methode zur Beschreibung der Aktion in einem anderen Thread
			 * @param e ActionEvent der Aktion
			 */
			public void handle(ActionEvent e) {
				BenutzerBearbeitenFormular bbf = new BenutzerBearbeitenFormular(stage, scene, benutzer);
			}
		};
	}
}
