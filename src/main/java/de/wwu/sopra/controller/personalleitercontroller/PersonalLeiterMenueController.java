package de.wwu.sopra.controller.personalleitercontroller;

import de.wwu.sopra.gui.alleuser.AnmeldenFormular;
import de.wwu.sopra.gui.alleuser.PasswortAendernFormular;
import de.wwu.sopra.gui.personalleiter.BenutzerMenueFormular;
import de.wwu.sopra.gui.personalleiter.BenutzerRegistrierenFormular;
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
public class PersonalLeiterMenueController {

	/**
	 * Funktionalitaet des Abmelden Buttons
	 * 
	 * @pre stage darf nicht null sein
	 * @pre oldScene darf nicht null sein
	 * @param stage das aktive Fenster
	 * @return Der Eventhandler der die gewuenschte Funktionalitaet liefert
	 */
	public EventHandler<ActionEvent> getActionEventForAbmelden(Stage stage) {
		return new EventHandler<ActionEvent>() {
			/**
			 * Methode zur Beschreibung der Aktion in einem anderen Thread
			 * @param e ActionEvent der Aktion
			 */
			public void handle(ActionEvent e) {
				AnmeldenFormular amf = new AnmeldenFormular(stage);
			}
		};
	}

	/**
	 * Methode die EventHandler des Hauptmenue Buttons erzeugt
	 * 
	 * @pre stage darf nicht null sein
	 * @pre oldScene darf nicht null sein
	 * @param stage    das aktuelle Fenster wird uebergeben
	 * @param oldScene die zuletzt aufgerufene Scene wird uebergeben
	 * @param benutzer der benutzer dessen PW geaendert werden soll
	 * @return Der Eventhandler der die gewuenschte Funktionalitaet liefert
	 */
	public EventHandler<ActionEvent> getActionEventForPWaendern(Stage stage, Scene oldScene, Benutzer benutzer) {
		return new EventHandler<ActionEvent>() {
			/**
			 * Methode zur Beschreibung der Aktion in einem anderen Thread
			 * @param e ActionEvent der Aktion
			 */
			public void handle(ActionEvent e) {
				PasswortAendernFormular pwa = new PasswortAendernFormular(stage, oldScene, benutzer);
			}
		};
	}

	/**
	 * Funktionalitaet des Benutzer registrieren Buttons
	 * 
	 * @pre stage darf nicht null sein
	 * @pre oldScene darf nicht null sein
	 * @param stage das aktive Fenster
	 * @param scene das Rollenauswahlfenster
	 * @return Der Eventhandler der die gewuenschte Funktionalitaet liefert
	 */
	public EventHandler<ActionEvent> getActionEventForBenutzerRegistrieren(Stage stage, Scene scene) {
		return new EventHandler<ActionEvent>() {
			/**
			 * Methode zur Beschreibung der Aktion in einem anderen Thread
			 * @param e ActionEvent der Aktion
			 */
			public void handle(ActionEvent e) {
				BenutzerRegistrierenFormular brf = new BenutzerRegistrierenFormular(stage, scene);
			}
		};
	}

	/**
	 * Funktionalitaet des Benutzermenuebuttons
	 * 
	 * @pre stage darf nicht null sein
	 * @pre oldScene darf nicht null sein
	 * @param stage das aktive Fenster
	 * @param scene das Rollenauswahlfenster
	 * @return Der Eventhandler der die gewuenschte Funktionalitaet liefert
	 */
	public EventHandler<ActionEvent> getActionEventForBenutzerMenue(Stage stage, Scene scene) {
		return new EventHandler<ActionEvent>() {
			/**
			 * Methode zur Beschreibung der Aktion in einem anderen Thread
			 * @param e ActionEvent der Aktion
			 */
			public void handle(ActionEvent e) {
				BenutzerMenueFormular brf = new BenutzerMenueFormular(stage, scene);
			}
		};
	}
}
