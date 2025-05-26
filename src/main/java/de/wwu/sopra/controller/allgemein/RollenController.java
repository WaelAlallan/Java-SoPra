package de.wwu.sopra.controller.allgemein;

import de.wwu.sopra.gui.alleuser.AnmeldenFormular;
import de.wwu.sopra.gui.biobankleiter.BiobankLeiterMenueFormular;
import de.wwu.sopra.gui.mta.MtaMenueFormular;
import de.wwu.sopra.gui.personalleiter.PersonalLeiterMenueFormular;
import de.wwu.sopra.gui.studynurse.StudyNurseMenueFormular;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 * Controller Klasse zum verwalten der Funktionen der Buttons des GUI Fensters
 * 
 * @author Alexander Leifhelm, Sebastian Huck, Wael Al Allan
 */
public class RollenController {

	/**
	 * Methode die EventHandler des Personalleiter Buttons erzeugt
	 * 
	 * @pre stage darf nicht null sein
	 * @pre oldScene darf nicht null sein
	 * @param stage das aktuelle Fenster wird uebergeben
	 * @param scene die zuletzt aufgerufene Scene wird uebergeben
	 * @return der Eventhandler fuer den Personalleiter Button wird uebergeben
	 */
	public EventHandler<ActionEvent> getActionForPersonalleiterButton(Stage stage, Scene scene) {
		return new EventHandler<ActionEvent>() {
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
	 * 
	 * Methode die EventHandler des Biobankleiter Buttons erzeugt
	 * 
	 * @pre stage darf nicht null sein
	 * @pre oldScene darf nicht null sein
	 * @param stage das aktuelle Fenster wird uebergeben
	 * @param scene die zuletzt aufgerufene Scene wird uebergeben
	 * @return der Eventhandler fuer den Biobankleiter Button wird uebergeben
	 */
	public EventHandler<ActionEvent> getActionForBiobankleiter(Stage stage, Scene scene) {
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

	/**
	 * Methode die EventHandler des MTA Buttons erzeugt
	 * 
	 * @pre stage darf nicht null sein
	 * @pre oldScene darf nicht null sein
	 * @param stage das aktuelle Fenster wird uebergeben
	 * @param scene die zuletzt aufgerufene Scene wird uebergeben
	 * @return der Eventhandler fuer den MTA Button wird uebergeben
	 */
	public EventHandler<ActionEvent> getActionForMTA(Stage stage, Scene scene) {
		return new EventHandler<ActionEvent>() {
			/**
			 * Methode zur Beschreibung der Aktion in einem anderen Thread
			 * @param e ActionEvent der Aktion
			 */
			public void handle(ActionEvent e) {
				MtaMenueFormular mmf = new MtaMenueFormular(stage, scene);
			}
		};
	}

	/**
	 * Methode die EventHandler des StudyNurse Buttons erzeugt
	 * 
	 * @pre stage darf nicht null sein
	 * @pre oldScene darf nicht null sein
	 * @param stage das aktuelle Fenster wird uebergeben
	 * @param scene die zuletzt aufgerufene Scene wird uebergeben
	 * @return der Eventhandler fuer den StudyNurse Button wird uebergeben
	 */
	public EventHandler<ActionEvent> getActionForStudyNurse(Stage stage, Scene scene) {
		return new EventHandler<ActionEvent>() {
			/**
			 * Methode zur Beschreibung der Aktion in einem anderen Thread
			 * @param e ActionEvent der Aktion
			 */
			public void handle(ActionEvent e) {
				StudyNurseMenueFormular snmf = new StudyNurseMenueFormular(stage, scene);
			}
		};
	}

	/**
	 * Methode die EventHandler des Abmelden Buttons erzeugt
	 * 
	 * @pre stage darf nicht null sein
	 * @pre oldScene darf nicht null sein
	 * @param stage das aktuelle Fenster wird uebergeben
	 * @return der Eventhandler fuer den Abmelden Button wird uebergeben
	 */
	public EventHandler<ActionEvent> getActionForAbmelden(Stage stage) {
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

}