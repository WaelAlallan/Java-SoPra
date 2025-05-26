package de.wwu.sopra.controller.personalleitercontroller;

import java.util.Optional;

import de.wwu.sopra.gui.helfer.GUIHelfer;
import de.wwu.sopra.gui.helfer.StackedLabelTextFields;
import de.wwu.sopra.gui.personalleiter.BenutzerAnsehenFormular;
import de.wwu.sopra.gui.personalleiter.BenutzerMenueFormular;
import de.wwu.sopra.gui.personalleiter.PersonalLeiterMenueFormular;
import de.wwu.sopra.model.Benutzer;
import de.wwu.sopra.steuerung.personalleiter.BenutzerSteuerung;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonType;
import javafx.scene.control.CheckBox;
import javafx.stage.Stage;

/**
 * Controllerklasse imlementiert funktionalitaet der Buttons der GUI
 * 
 * @author Alexander Leifhelm, Sebastian Huck, Wael Al Allan
 */
public class BenutzerBearbeitenController {

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
			@Override /**
						 * Enqueues an action to be performed on a different thread than your own
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
	 * @param scene Das Benutzeransehen Fenster
	 * @return der Eventhandler der die gewuenschte Funktionalitaet liefert
	 */
	public EventHandler<ActionEvent> getActionEventForAbbrechen(Stage stage, Scene scene) {
		return new EventHandler<ActionEvent>() {
			/**
			 * Methode zur Beschreibung der Aktion in einem anderen Thread
			 * @param e ActionEvent der Aktion
			 */
			public void handle(ActionEvent e) {
				stage.setScene(scene); // ist Menue und soll ansehen
			}
		};
	}

	/**
	 * Funktion des Loeschen Buttons
	 * 
	 * @pre stage darf nicht null sein
	 * @pre oldScene darf nicht null sein
	 * @param stage    das aktive Fenster
	 * @param scene    das Benutzeransehen Fenster
	 * @param benutzer der zu loeschende Benutzer
	 * @return der Eventhandler der die gewuenschte Funktionalitaet liefert
	 */
	public EventHandler<ActionEvent> getActionEventForLoeschen(Stage stage, Scene scene, Benutzer benutzer) {
		return new EventHandler<ActionEvent>() {
			/**
			 * Methode zur Beschreibung der Aktion in einem anderen Thread
			 * @param e ActionEvent der Aktion
			 */
			public void handle(ActionEvent e) {
				try {
					Alert alert = new Alert(AlertType.CONFIRMATION);
					alert.setHeaderText("Bitte best\u00e4tigen!");
					alert.setContentText("Sind sie sicher, dass der Benutzer gel\u00f6scht werden soll?");

					Optional<ButtonType> result = alert.showAndWait();
					if (result.get() == ButtonType.OK) {
						BenutzerSteuerung benutzerBS = new BenutzerSteuerung();
						benutzerBS.benutzerLoeschen(benutzer);
						BenutzerMenueFormular bmf = new BenutzerMenueFormular(stage, scene);
					}
				} catch (Exception exception) {
					GUIHelfer.AutomatischesFehlermeldungFormular("Loeschen", exception);
				}
			}
		};
	}

	/**
	 * Funktion des Loeschen Buttons
	 * 
	 * @pre stage darf nicht null sein
	 * @pre oldScene darf nicht null sein
	 * @param stage            das aktive Fenster
	 * @param scene            das Benutzeransehen Fenster
	 * @param benutzer         der zu bearbeitende Benutzer
	 * @param eingabenSLTF     Textfelder vorname/nachname
	 * @param studyNurseCB     Checkbox
	 * @param mtaCB            Checkbox
	 * @param bioBankLeiterCB  Checkbox
	 * @param personalLeiterCB Checkbox
	 * @return der Eventhandler der die gewuenschte Funktionalitaet liefert
	 */
	public EventHandler<ActionEvent> getActionEventForBestaetigen(Stage stage, Scene scene, Benutzer benutzer,
			StackedLabelTextFields eingabenSLTF, CheckBox studyNurseCB, CheckBox mtaCB, CheckBox bioBankLeiterCB,
			CheckBox personalLeiterCB) {
		return new EventHandler<ActionEvent>() {
			/**
			 * Methode zur Beschreibung der Aktion in einem anderen Thread
			 * @param e ActionEvent der Aktion
			 */
			public void handle(ActionEvent e) {
				BenutzerSteuerung benutzerBS = new BenutzerSteuerung();
				String vorname = eingabenSLTF.getText("Vorname:");
				String nachname = eingabenSLTF.getText("Nachname:");
				boolean istNurse = studyNurseCB.isSelected();
				boolean istMTA = mtaCB.isSelected();
				boolean istLeiter = bioBankLeiterCB.isSelected();
				boolean istPerson = personalLeiterCB.isSelected();
				try {
					benutzerBS.benutzerBearbeiten(benutzer, vorname, nachname, istNurse, istMTA, istLeiter, istPerson);
					BenutzerAnsehenFormular baf = new BenutzerAnsehenFormular(stage, scene, benutzer);
				} catch (Exception exception) {
					GUIHelfer.AutomatischesFehlermeldungFormular("Bearbeiten", exception);
				}
			}
		};
	}

}
