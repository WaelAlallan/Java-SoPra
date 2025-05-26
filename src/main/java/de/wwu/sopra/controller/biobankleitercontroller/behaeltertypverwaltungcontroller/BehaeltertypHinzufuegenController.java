package de.wwu.sopra.controller.biobankleitercontroller.behaeltertypverwaltungcontroller;

import java.util.ArrayList;

import de.wwu.sopra.gui.biobankleiter.BiobankLeiterMenueFormular;
import de.wwu.sopra.gui.biobankleiter.behaeltertypverwaltung.BehaeltertypMenueFormular;
import de.wwu.sopra.gui.helfer.GUIHelfer;
import de.wwu.sopra.gui.helfer.StackedLabelTextFields;
import de.wwu.sopra.model.ProbenBehaelterTyp;
import de.wwu.sopra.steuerung.biobankleiter.ProbenBehaeltertypHinzufuegenSteuerung;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 * Controller Klasse zum verwalten der Funktionen der Buttons des GUI Fensters
 * 
 * @author Alexander Leifhelm, Sebastian Huck, Wael Al Allan
 */
public class BehaeltertypHinzufuegenController {

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
	 * Methode die EventHandler des Hinzufuegen Buttons erzeugt
	 * 
	 * @pre stage darf nicht null sein
	 * @pre oldScene darf nicht null sein
	 * @param stage        das aktuelle Fenster wird uebergeben
	 * @param scene        die zuletzt aufgerufene Scene wird uebergeben
	 * @param eingabenSLTF das StackedLabelTextFields zur uebergabe der Eingaben
	 * @return der Eventhandler fuer den Hinzufuegen Button wird uebergeben
	 */
	public EventHandler<ActionEvent> getActionForHinzufuegenButton(Stage stage, Scene scene,
			StackedLabelTextFields eingabenSLTF) {
		return new EventHandler<ActionEvent>() {

			/**
			 * Methode zur Beschreibung der Aktion in einem anderen Thread
			 * @param e ActionEvent der Aktion
			 */
			public void handle(ActionEvent e) {
				String name = eingabenSLTF.getText("Name:");
				String volumen = eingabenSLTF.getText("Volumen [cm^3]:");
				String hoehe = eingabenSLTF.getText("H\u00F6he [cm]:");
				String durchmesser = eingabenSLTF.getText("Durchmesser [cm]:");
				String deckelTyp = eingabenSLTF.getText("Deckel-Typ:");
				ProbenBehaeltertypHinzufuegenSteuerung pbhs = new ProbenBehaeltertypHinzufuegenSteuerung();

				try {
					pbhs.probenBehaeltertypHinzufuegen(name, volumen, hoehe, durchmesser, deckelTyp);
					ArrayList<ProbenBehaelterTyp> behaelterTypenAL = pbhs.getProbenbehaeltertyp("", "", "", "", "");
					BehaeltertypMenueFormular bmf = new BehaeltertypMenueFormular(stage, scene, behaelterTypenAL);
				} catch (Exception exception) {
					GUIHelfer.AutomatischesFehlermeldungFormular("Hinzufuegen", exception);
				}

			}
		};
	}

}
