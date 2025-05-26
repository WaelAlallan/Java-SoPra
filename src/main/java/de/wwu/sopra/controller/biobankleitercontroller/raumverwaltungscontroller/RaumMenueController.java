package de.wwu.sopra.controller.biobankleitercontroller.raumverwaltungscontroller;

import java.util.Optional;

import de.wwu.sopra.gui.biobankleiter.BiobankLeiterMenueFormular;
import de.wwu.sopra.gui.biobankleiter.behaeltertypverwaltung.BehaeltertypHinzufuegenFormular;
import de.wwu.sopra.gui.biobankleiter.raumverwaltung.RaumAnsehenFormular;
import de.wwu.sopra.gui.biobankleiter.raumverwaltung.RaumFilternFormular;
import de.wwu.sopra.gui.biobankleiter.raumverwaltung.RaumHinzufuegenFormular;
import de.wwu.sopra.gui.biobankleiter.raumverwaltung.RaumMenueFormular;
import de.wwu.sopra.gui.helfer.GUIHelfer;
import de.wwu.sopra.gui.helfer.ListViewUndButtons;
import de.wwu.sopra.gui.personalleiter.BenutzerAnsehenFormular;
import de.wwu.sopra.gui.personalleiter.PersonalLeiterMenueFormular;
import de.wwu.sopra.model.Benutzer;
import de.wwu.sopra.model.ProbenBehaelterTyp;
import de.wwu.sopra.model.Raum;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 * Controller Klasse zum verwalten der Funktionen der Buttons des GUI Fensters
 * 
 * @author Alexander Leifhelm, Sebastian Huck, Wael Al Allan
 */
public class RaumMenueController {

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
	 * Methode die EventHandler des Raum filtern Buttons erzeugt
	 * 
	 * @pre stage darf nicht null sein
	 * @pre oldScene darf nicht null sein
	 * @param stage das aktuelle Fenster wird uebergeben
	 * @param scene die zuletzt aufgerufene Scene wird uebergeben
	 * @return der Eventhandler fuer den Raum filtern Button wird uebergeben
	 */
	public EventHandler<ActionEvent> getActionForRaumfilternButton(Stage stage, Scene scene) {
		return new EventHandler<ActionEvent>() {
			/**
			 * Methode zur Beschreibung der Aktion in einem anderen Thread
			 * @param e ActionEvent der Aktion
			 */
			public void handle(ActionEvent e) {
				RaumFilternFormular rff = new RaumFilternFormular(stage, scene);
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
	 * @param listeLVUB ListView und Buttons wird uebergeben um zu erkennen welches
	 *                  Raum ausgewaehlt wurde
	 * @return der Eventhandler fuer den Weiter Button wird uebergeben
	 */
	public EventHandler<ActionEvent> getActionForWeiterButton(Stage stage, Scene scene,
			ListViewUndButtons<Raum> listeLVUB) {
		return new EventHandler<ActionEvent>() {
			/**
			 * Methode zur Beschreibung der Aktion in einem anderen Thread
			 * @param e ActionEvent der Aktion
			 */
			public void handle(ActionEvent e) {
				Raum raum = listeLVUB.getSelektiertesObjekt();
				if (raum != null) {
					RaumAnsehenFormular baf = new RaumAnsehenFormular(stage, scene, raum);
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
				RaumHinzufuegenFormular rhf = new RaumHinzufuegenFormular(stage, scene);
			}
		};
	}

}
