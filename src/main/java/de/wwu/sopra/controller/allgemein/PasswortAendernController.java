package de.wwu.sopra.controller.allgemein;

import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;

import de.wwu.sopra.gui.helfer.GUIHelfer;
import de.wwu.sopra.gui.helfer.StackedLabelTextFields;
import de.wwu.sopra.steuerung.alleuser.AuthentifizierungSteuerung;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 * Controller Klasse zum verwalten der Funktionen der Buttons des GUI Fensters
 * 
 * @author Alexander Leifhelm, Sebastian Huck, Wael Al Allan
 */
public class PasswortAendernController {

	/**
	 * Methode die EventHandler des Abbrechen Buttons erzeugt
	 * 
	 * @pre stage darf nicht null sein
	 * @pre oldScene darf nicht null sein
	 * @param stage    das auktuelle Fenster wird uebergeben
	 * @param oldScene die zuletzt aufgerufene Scene wird uebergeben
	 * @return der EventHandler fuer den Abbrechen Button
	 */
	public EventHandler<ActionEvent> getActionForAbbrechen(Stage stage, Scene oldScene) {
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
	 * @param stage        das auktuelle Fenster wird uebergeben
	 * @param scene        die zuletzt aufgerufene Scene wird uebergeben
	 * @param eingabenSLTF Stacked Label Textfields Objekt zum auslesen der Eingaben
	 * @return der EventHandler fuer den Abbrechen Button
	 */
	public EventHandler<ActionEvent> getActionForBestaetigen(Stage stage, Scene scene,
			StackedLabelTextFields eingabenSLTF) {

		return new EventHandler<ActionEvent>() {
			/**
			 * Methode zur Beschreibung der Aktion in einem anderen Thread
			 * @param e ActionEvent der Aktion
			 */
			public void handle(ActionEvent e) {

				AuthentifizierungSteuerung authSteu = AuthentifizierungSteuerung.getInstance();
				try {
					authSteu.aenderePasswort(eingabenSLTF.getText("Altes Passwort"),
							eingabenSLTF.getText("Neues Passwort"),
							eingabenSLTF.getText("Neues Passwort best\u00e4tigen"));
					stage.setScene(scene);
				} catch (NoSuchAlgorithmException argu) {
					GUIHelfer.FehlermeldungFormular("Falsche Eingabe", "Falsche Eingabe",
							"Ihr Passwort konnte nicht geaendert werden!");
				} catch (InvalidKeySpecException key) {
					GUIHelfer.FehlermeldungFormular("Falsche Eingabe", "Falsche Eingabe",
							"Ihr Passwort konnte nicht geaendert werden!");
				} catch (IllegalArgumentException e1) {
					GUIHelfer.FehlermeldungFormular("Falsche Eingabe", "Falsche Eingabe",
							"Ihr Passwort konnte nicht geaendert werden!");
				}

			}
		};
	}

}
