package de.wwu.sopra.controller.biobankleitercontroller.behaeltertypverwaltungcontroller;

import de.wwu.sopra.gui.biobankleiter.BiobankLeiterMenueFormular;
import de.wwu.sopra.gui.biobankleiter.behaeltertypverwaltung.BehaeltertypAnsehenFormular;
import de.wwu.sopra.gui.biobankleiter.behaeltertypverwaltung.BehaeltertypFilternFormular;
import de.wwu.sopra.gui.biobankleiter.behaeltertypverwaltung.BehaeltertypHinzufuegenFormular;
import de.wwu.sopra.gui.helfer.GUIHelfer;
import de.wwu.sopra.gui.helfer.ListViewUndButtons;
import de.wwu.sopra.model.ProbenBehaelterTyp;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 * Klasse fuer den Controller vom BehaeltertypMenueFormular.
 * @author Gruppe 1
 *
 */
public class BehaeltertypMenueController {

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

	/**
	 * Methode die EventHandler des Filtern Buttons erzeugt
	 * 
	 * @pre stage darf nicht null sein
	 * @pre oldScene darf nicht null sein
	 * @param stage das aktuelle Fenster wird uebergeben
	 * @param scene die zuletzt aufgerufene Scene wird uebergeben
	 * @return der Eventhandler fuer den Filtern Button wird uebergeben
	 */
	public EventHandler<ActionEvent> getActionForFilternButton(Stage stage, Scene scene) {
		return new EventHandler<ActionEvent>() {
			/**
			 * Methode zur Beschreibung der Aktion in einem anderen Thread
			 * @param e ActionEvent der Aktion
			 */
			public void handle(ActionEvent e) {
				BehaeltertypFilternFormular bff = new BehaeltertypFilternFormular(stage, scene);
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
	 * Methode die EventHandler des Weiter Buttons erzeugt
	 * 
	 * @pre stage darf nicht null sein
	 * @pre oldScene darf nicht null sein
	 * @param stage     das aktuelle Fenster wird uebergeben
	 * @param scene     die zuletzt aufgerufene Scene wird uebergeben
	 * @param listeLVUB ListViewUndButtons wird uebergeben zum auslesen des
	 *                  gewaelten Objekts
	 * @return der Eventhandler fuer den Weiter Button wird uebergeben
	 */
	public EventHandler<ActionEvent> getActionForWeiterButton(Stage stage, Scene scene,
			ListViewUndButtons<ProbenBehaelterTyp> listeLVUB) {
		return new EventHandler<ActionEvent>() {
			/**
			 * Methode zur Beschreibung der Aktion in einem anderen Thread
			 * @param e ActionEvent der Aktion
			 */
			public void handle(ActionEvent e) {
				ProbenBehaelterTyp auswahl = (ProbenBehaelterTyp) listeLVUB.getSelektiertesObjekt();
				if (auswahl != null) {
					BehaeltertypAnsehenFormular baf = new BehaeltertypAnsehenFormular(stage, scene, auswahl);
				} else {
					GUIHelfer.AutomatischesFehlermeldungFormular("Auswahl", null);
				}
			}
		};
	}

	/**
	 * Methode die EventHandler des Hinzufuegen Buttons erzeugt
	 * 
	 * @pre stage darf nicht null sein
	 * @pre oldScene darf nicht null sein
	 * @param stage das aktuelle Fenster wird uebergeben
	 * @param scene die zuletzt aufgerufene Scene wird uebergeben
	 * @return der Eventhandler fuer den Hinzufuegen Button wird uebergeben
	 */
	public EventHandler<ActionEvent> getActionForHinzufuegenButton(Stage stage, Scene scene) {
		return new EventHandler<ActionEvent>() {
			/**
			 * Methode zur Beschreibung der Aktion in einem anderen Thread
			 * @param e ActionEvent der Aktion
			 */
			public void handle(ActionEvent e) {
				BehaeltertypHinzufuegenFormular baf = new BehaeltertypHinzufuegenFormular(stage, scene);
			}
		};
	}
}
