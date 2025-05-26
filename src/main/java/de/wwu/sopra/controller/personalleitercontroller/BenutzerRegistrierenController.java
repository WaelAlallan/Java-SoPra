package de.wwu.sopra.controller.personalleitercontroller;

import de.wwu.sopra.gui.helfer.GUIHelfer;
import de.wwu.sopra.gui.helfer.StackedLabelTextFields;
import de.wwu.sopra.gui.personalleiter.PersonalLeiterMenueFormular;
import de.wwu.sopra.steuerung.personalleiter.BenutzerSteuerung;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.ButtonBar.ButtonData;
import javafx.scene.control.ButtonType;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Dialog;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import javafx.util.Callback;
import javafx.util.Pair;

/**
 * Controllerklasse imlementiert funktionalitaet der Buttons der GUI
 * 
 * @author Alexander Leifhelm, Sebastian Huck, Wael Al Allan
 */
public class BenutzerRegistrierenController {

	/**
	 * Methode die EventHandler des Abbrechen Buttons erzeugt
	 * 
	 * @param stage das aktive Fenster wird uebergeben
	 * @param scene scene die zuletzt aufgerufen wurde wird uebergeben
	 * @return der Eventhandler fuer den Abbrechen Buttons wird uebergeben
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
	 * Funktion des Registrieren Buttons
	 * 
	 * @pre stage darf nicht null sein
	 * @pre oldScene darf nicht null sein
	 * @param stage            das aktive Fenster
	 * @param oldScene         Das PersonalleitermenueFormular
	 * @param eingabenSLTF     Textfeder mit Vor und Nachnamen
	 * @param mtaCB            Checkbox
	 * @param studyNurseCB     Checkbox
	 * @param bioBankLeiterCB  Checkbox
	 * @param personalLeiterCB Checkbox
	 * @param benutzerBS       Checkbox
	 * @return der Eventhandler der die Funktionalitaet liefert
	 */
	public EventHandler<ActionEvent> getActionEventForBenutzerRegistrieren(Stage stage, Scene oldScene,
			StackedLabelTextFields eingabenSLTF, CheckBox mtaCB, CheckBox studyNurseCB, CheckBox bioBankLeiterCB,
			CheckBox personalLeiterCB, BenutzerSteuerung benutzerBS) {
		return new EventHandler<ActionEvent>() {
			@Override /**
						 * Enqueues an action to be performed on a different thread than your own
						 */
			public void handle(ActionEvent e) {
				String vorname = eingabenSLTF.getText("Vorname:");
				String nachname = eingabenSLTF.getText("Nachname:");
				boolean istNurse = studyNurseCB.isSelected();
				boolean istMTA = mtaCB.isSelected();
				boolean istLeiter = bioBankLeiterCB.isSelected();
				boolean istPerson = personalLeiterCB.isSelected();
				Pair<String, String> benutzerInfos = null;
				try {
					benutzerInfos = benutzerBS.benutzerHinzufuegen(vorname, nachname, istNurse, istMTA, istLeiter,
							istPerson);

					GridPane gp = new GridPane();
					initGP(gp, benutzerInfos);

					Dialog<String> dialog = new Dialog<>();
					dialog.getDialogPane().setContent(gp);
					ButtonType buttonTypeOk = new ButtonType("Okay", ButtonData.OK_DONE);
					dialog.setTitle("Benutzer wurde erfolgreich erstellt");
					dialog.getDialogPane().getButtonTypes().add(buttonTypeOk);
					dialog.setResultConverter(new Callback<ButtonType, String>() {

						public String call(ButtonType b) {
							return null;
						}
					});

					dialog.show();
					stage.setScene(oldScene);
				} catch (Exception exception) {
					GUIHelfer.AutomatischesFehlermeldungFormular("Hinzufuegen", exception);
				}
			}

			/**
			 * Initialisierungsmethode der GridPane welche das bestaetigungsfenster
			 * darstellt
			 * 
			 * @param gp            die uebergebene GridPane
			 * @param benutzerInfos werden uebergeben um Nutzername und PW auszulesen
			 */
			private void initGP(GridPane gp, Pair<String, String> benutzerInfos) {
				String[] labels = { "", "Benutzername: ", "Initialpasswort: " };
				StackedLabelTextFields sltf = new StackedLabelTextFields(labels);
				sltf.getTextField("").setText("Benutzer wurde erstellt:");
				sltf.getTextField("").setEditable(false);
				sltf.getTextField("").setDisable(true);
				sltf.getTextField("Benutzername: ").setText(benutzerInfos.getKey());
				sltf.getTextField("Initialpasswort: ").setText(benutzerInfos.getValue());
				sltf.getTextField("Benutzername: ").setEditable(false);
				sltf.getTextField("Initialpasswort: ").setEditable(false);

				gp.addRow(0, sltf.getGridPane());
				GUIHelfer.setzeZeilenGewichte(gp, GUIHelfer.gibGleicheGewichte(gp.getChildren().size()));
				GUIHelfer.setzeSpaltenGewichte(gp, GUIHelfer.gibGleicheGewichte(1));
				gp.setPadding(new Insets(10, 10, 10, 10));

			}
		};
	}

	/**
	 * Funktion des Hauptmenue Buttons
	 * 
	 * @pre stage darf nicht null sein
	 * @pre oldScene darf nicht null sein
	 * @param stage das aktive Fenster
	 * @param scene Das PersonalleiterMenueFormular
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

}