package de.wwu.sopra.controller.personalleitercontroller;

import java.util.ArrayList;

import de.wwu.sopra.gui.helfer.GUIHelfer;
import de.wwu.sopra.gui.helfer.ListViewUndButtons;
import de.wwu.sopra.gui.helfer.StackedLabelTextFields;
import de.wwu.sopra.gui.personalleiter.BenutzerAnsehenFormular;
import de.wwu.sopra.gui.personalleiter.PersonalLeiterMenueFormular;
import de.wwu.sopra.model.Benutzer;
import de.wwu.sopra.steuerung.personalleiter.BenutzerSteuerung;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 * Controllerklasse implementiert die funktion der Buttons in der Klasse
 * BenutzermenueFormular
 * 
 * @author Alexander Leifhelm, Sebastian Huck, Wael Al Allan
 */
public class BenutzerMenueController {

	/**
	 * Funktion des Hauptmenue Buttons
	 * 
	 * @pre stage darf nicht null sein
	 * @pre oldScene darf nicht null sein
	 * @param stage das aktive Fenster
	 * @param scene Das Personalleitermenueformular
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
	 * @param scene Das Personalleitermenue Fenster
	 * @return der Eventhandler der die gewuenschte Funktionalitaet liefert
	 */
	public EventHandler<ActionEvent> getActionEventForAbbrechen(Stage stage, Scene scene) {
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
	 * Funktion des Suchen Buttons
	 * 
	 * @pre stage darf nicht null sein
	 * @pre oldScene darf nicht null sein
	 * @param stage        das aktive Fenster
	 * @param scene        Das Personalleitermenueformular
	 * @param eingabenSLTF die TextEingabefelder fuer Name und Vorname
	 * @param listeLVUB    das Fenster der Benutzer
	 * @return der EventHandler der die gewuenschte Funktion liefert
	 */
	public EventHandler<ActionEvent> getActionEventForSuchen(Stage stage, Scene scene,
			StackedLabelTextFields eingabenSLTF, ListViewUndButtons listeLVUB) {
		return new EventHandler<ActionEvent>() {
			/**
			 * Methode zur Beschreibung der Aktion in einem anderen Thread
			 * @param e ActionEvent der Aktion
			 */
			public void handle(ActionEvent e) {
				BenutzerSteuerung bs = new BenutzerSteuerung();
				ArrayList<Benutzer> gefundeneBenutzer = bs.benutzerSuchen(eingabenSLTF.getText("Suchen Nachname:"),
						eingabenSLTF.getText("Suchen Vorname:"));
				listeLVUB.updateListe(gefundeneBenutzer);
			}
		};
	}

	/**
	 * Funktion des Weiter Buttons
	 * 
	 * @pre stage darf nicht null sein
	 * @pre oldScene darf nicht null sein
	 * @param stage     das aktive Fenster
	 * @param scene     das PersonalleiterMenueFormular
	 * @param listeLVUB die Liste der Benutzer
	 * @return der EventHandler der die gewuenschte Funktion liefert
	 */
	public EventHandler<ActionEvent> getActionEventForWeiter(Stage stage, Scene scene,
			ListViewUndButtons<Benutzer> listeLVUB) {
		return new EventHandler<ActionEvent>() {
			/**
			 * Methode zur Beschreibung der Aktion in einem anderen Thread
			 * @param e ActionEvent der Aktion
			 */
			public void handle(ActionEvent e) {
				Benutzer benutzer = listeLVUB.getSelektiertesObjekt();
				if (benutzer != null) {
					BenutzerAnsehenFormular baf = new BenutzerAnsehenFormular(stage, scene, benutzer);
				} else {
					GUIHelfer.AutomatischesFehlermeldungFormular("Auswahl", null);
				}
			}
		};
	}

}
