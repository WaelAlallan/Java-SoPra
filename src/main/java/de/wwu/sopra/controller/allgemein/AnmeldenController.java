
package de.wwu.sopra.controller.allgemein;

import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;

import de.wwu.sopra.gui.alleuser.RollenFormular;
import de.wwu.sopra.gui.helfer.GUIHelfer;
import de.wwu.sopra.gui.helfer.StackedLabelTextFields;
import de.wwu.sopra.model.Benutzer;
import de.wwu.sopra.steuerung.alleuser.AuthentifizierungSteuerung;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.stage.Stage;
import javafx.scene.Scene;

/**
 * Controller Klasse zum verwalten der Funktionen der Buttons des GUI Fensters
 * 
 * @author Alexander Leifhelm, Sebastian Huck, Wael Al Allan
 */
public class AnmeldenController {

	/**
	 * Methode, welche die eingaben der GUI erhaelt und einen EventHandler zurueck
	 * gibt
	 * 
	 * @pre stage darf nicht null sein
	 * @pre oldScene darf nicht null sein
	 * @param stage        das Aktive Fenster wird uebergeben
	 * @param scene        die zuletzt sichtbare Scene wird uebergeben
	 * @param eingabenSLTF Stacked label Text Fields um die Werte auszulesen
	 * @return gibt den EventHandler fuer den Login Button zurueck
	 */
	public EventHandler<ActionEvent> getActionForLogin(Stage stage, Scene scene, StackedLabelTextFields eingabenSLTF) {

		return new EventHandler<ActionEvent>() {
			/**
			 * Methode zur Beschreibung der Aktion in einem anderen Thread
			 * @param e ActionEvent der Aktion
			 */
			public void handle(ActionEvent e) {

				AuthentifizierungSteuerung authSteu = AuthentifizierungSteuerung.getInstance();

				try {
					Benutzer ben = authSteu.anmelden(eingabenSLTF.getText("Benutzername:"),
							eingabenSLTF.getText("Passwort:"));
					if (ben == null) {
						GUIHelfer.FehlermeldungFormular("Falsche Eingabe", "Falsche Eingabe",
								"Das Passwort passt nicht zu diesem Benutzer!");
					} else {
						RollenFormular rollenformular = new RollenFormular(stage, scene, ben);
					}
				} catch (IllegalArgumentException e1) {
					GUIHelfer.FehlermeldungFormular("Falsche Eingabe", "Falsche Eingabe",
							"Der Benutzer wurde nicht gefunden");
				} catch (NoSuchAlgorithmException e2) {
					e2.printStackTrace();
				} catch (InvalidKeySpecException e3) {
					e3.printStackTrace();
				}
			}
		};
	}

}
