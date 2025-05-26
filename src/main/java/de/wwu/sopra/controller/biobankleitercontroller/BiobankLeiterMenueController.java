package de.wwu.sopra.controller.biobankleitercontroller;

import java.util.ArrayList;

import de.wwu.sopra.model.ProbenBehaelterTyp;
import de.wwu.sopra.model.Studie;
import de.wwu.sopra.steuerung.alleuser.AuthentifizierungSteuerung;
import de.wwu.sopra.steuerung.biobankleiter.KuehlschrankSteuerung;
import de.wwu.sopra.steuerung.biobankleiter.ProbenBehaeltertypHinzufuegenSteuerung;
import de.wwu.sopra.steuerung.biobankleiter.ProbentypHinzufuegenSteuerung;
import de.wwu.sopra.steuerung.biobankleiter.RaumSteuerung;
import de.wwu.sopra.steuerung.biobankleiter.StudienSteuerung;
import de.wwu.sopra.gui.alleuser.AnmeldenFormular;
import de.wwu.sopra.gui.alleuser.PasswortAendernFormular;
import de.wwu.sopra.gui.biobankleiter.behaeltertypverwaltung.BehaeltertypMenueFormular;
import de.wwu.sopra.gui.biobankleiter.kuehlschrankverwaltung.KuehlschrankMenueFormular;
import de.wwu.sopra.gui.biobankleiter.probenkategorieverwaltung.ProbenkategorieMenueFormular;
import de.wwu.sopra.gui.biobankleiter.raumverwaltung.RaumMenueFormular;
import de.wwu.sopra.gui.biobankleiter.studienverwaltung.StudienUebersichtFormular;
import de.wwu.sopra.model.Kuehlschrank;
import de.wwu.sopra.model.Raum;
import de.wwu.sopra.model.ProbenKategorie;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 * Controller Klasse zum verwalten der Funktionen der Buttons des GUI Fensters
 * 
 * @author Alexander Leifhelm, Sebastian Huck, Wael Al Allan
 */
public class BiobankLeiterMenueController {

	/**
	 * Methode die EventHandler des PasswortAendern Buttons erzeugt
	 * 
	 * @pre stage darf nicht null sein
	 * @pre oldScene darf nicht null sein
	 * @param stage das aktuelle Fenster wird uebergeben
	 * @param scene die zuletzt aufgerufene Scene wird uebergeben
	 * @return der Eventhandler fuer den PasswortAendern Button wird uebergeben
	 */
	public EventHandler<ActionEvent> getActionForPasswortAendernButton(Stage stage, Scene scene) {
		return new EventHandler<ActionEvent>() {
			/**
			 * Methode zur Beschreibung der Aktion in einem anderen Thread
			 * @param e ActionEvent der Aktion
			 */
			public void handle(ActionEvent e) {
				PasswortAendernFormular paf = new PasswortAendernFormular(stage, scene,
						AuthentifizierungSteuerung.getInstance().getAktiverBenutzer());
			}
		};
	}

	/**
	 * Methode die EventHandler des Abmelden Buttons erzeugt
	 * 
	 * @pre stage darf nicht null sein
	 * @pre oldScene darf nicht null sein
	 * @param stage das aktuelle Fenster wird uebergeben
	 * @param scene die zuletzt aufgerufene Scene wird uebergeben
	 * @return der Eventhandler fuer den Abmelden Button wird uebergeben
	 */
	public EventHandler<ActionEvent> getActionForAbmeldenButton(Stage stage, Scene scene) {
		return new EventHandler<ActionEvent>() {
			/**
			 * Methode zur Beschreibung der Aktion in einem anderen Thread
			 * @param e ActionEvent der Aktion
			 */
			public void handle(ActionEvent e) {
				AnmeldenFormular af = new AnmeldenFormular(stage);
			}
		};
	}

	/**
	 * Methode die EventHandler des Studien Buttons erzeugt
	 * 
	 * @pre stage darf nicht null sein
	 * @pre oldScene darf nicht null sein
	 * @param stage das aktuelle Fenster wird uebergeben
	 * @param scene die zuletzt aufgerufene Scene wird uebergeben
	 * @return der Eventhandler fuer den Studien Button wird uebergeben
	 */
	public EventHandler<ActionEvent> getActionForStudienButton(Stage stage, Scene scene) {
		return new EventHandler<ActionEvent>() {
			/**
			 * Enqueues an action to be performed on a different thread than your own
			 * @param e ActionEvent
			 */
			public void handle(ActionEvent e) {
				StudienSteuerung ss = new StudienSteuerung();
				ArrayList<Studie> studien = ss.getStudien("", "");
				StudienUebersichtFormular suef = new StudienUebersichtFormular(stage, studien);
			}
		};
	}

	/**
	 * Methode die EventHandler des Raeume Buttons erzeugt
	 * 
	 * @pre stage darf nicht null sein
	 * @pre oldScene darf nicht null sein
	 * @param stage das aktuelle Fenster wird uebergeben
	 * @param scene die zuletzt aufgerufene Scene wird uebergeben
	 * @return der Eventhandler fuer den Raeume Button wird uebergeben
	 */
	public EventHandler<ActionEvent> getActionForRaeumeButton(Stage stage, Scene scene) {
		return new EventHandler<ActionEvent>() {
			/**
			 * Methode zur Beschreibung der Aktion in einem anderen Thread
			 * @param e ActionEvent der Aktion
			 */
			public void handle(ActionEvent e) {
				RaumSteuerung rs = new RaumSteuerung();
				ArrayList<Raum> raeumeAL = rs.getRaeume("", "", "");
				RaumMenueFormular rmf = new RaumMenueFormular(stage, scene, raeumeAL);
			}
		};
	}

	/**
	 * Methode die EventHandler des Kuehlschraenke Buttons erzeugt
	 * 
	 * @pre stage darf nicht null sein
	 * @pre oldScene darf nicht null sein
	 * @param stage das aktuelle Fenster wird uebergeben
	 * @param scene die zuletzt aufgerufene Scene wird uebergeben
	 * @return der Eventhandler fuer den Kuehlschraenke Button wird uebergeben
	 */
	public EventHandler<ActionEvent> getActionForKuehlschraenkeButton(Stage stage, Scene scene) {
		return new EventHandler<ActionEvent>() {
			/**
			 * Methode zur Beschreibung der Aktion in einem anderen Thread
			 * @param e ActionEvent der Aktion
			 */
			public void handle(ActionEvent e) {
				KuehlschrankSteuerung kss = new KuehlschrankSteuerung();
				ArrayList<Kuehlschrank> kuehlschraenkeAL = kss.getKuehlschraenke("", "", null);
				KuehlschrankMenueFormular kmf = new KuehlschrankMenueFormular(stage, scene, kuehlschraenkeAL);
			}
		};
	}

	/**
	 * Methode die EventHandler des Probenkategorie Buttons erzeugt
	 * 
	 * @pre stage darf nicht null sein
	 * @pre oldScene darf nicht null sein
	 * @param stage das aktuelle Fenster wird uebergeben
	 * @param scene die zuletzt aufgerufene Scene wird uebergeben
	 * @return der Eventhandler fuer den Probenkategorie Button wird uebergeben
	 */
	public EventHandler<ActionEvent> getActionForProbenkategorieButton(Stage stage, Scene scene) {
		return new EventHandler<ActionEvent>() {
			/**
			 * Methode zur Beschreibung der Aktion in einem anderen Thread
			 * @param e ActionEvent der Aktion
			 */
			public void handle(ActionEvent e) {
				ProbentypHinzufuegenSteuerung phs = new ProbentypHinzufuegenSteuerung();
				ArrayList<ProbenKategorie> gefundeneProbenkategorien = phs.getProbenKategorie("");
				ProbenkategorieMenueFormular pmf = new ProbenkategorieMenueFormular(stage, scene,
						gefundeneProbenkategorien);
			}
		};
	}

	/**
	 * Methode die EventHandler des Behaeltertyp Buttons erzeugt
	 * 
	 * @pre stage darf nicht null sein
	 * @pre oldScene darf nicht null sein
	 * @param stage das aktuelle Fenster wird uebergeben
	 * @param scene die zuletzt aufgerufene Scene wird uebergeben
	 * @return der Eventhandler fuer den Behaeltertyp Button wird uebergeben
	 */
	public EventHandler<ActionEvent> getActionForBehaeltertypButton(Stage stage, Scene scene) {
		return new EventHandler<ActionEvent>() {
			/**
			 * Methode zur Beschreibung der Aktion in einem anderen Thread
			 * @param e ActionEvent der Aktion
			 */
			public void handle(ActionEvent e) {
				ProbenBehaeltertypHinzufuegenSteuerung pbhs = new ProbenBehaeltertypHinzufuegenSteuerung();
				ArrayList<ProbenBehaelterTyp> behaelterTypenAL = pbhs.getProbenbehaeltertyp("", "", "", "", "");
				BehaeltertypMenueFormular bmf = new BehaeltertypMenueFormular(stage, scene, behaelterTypenAL);
			}
		};
	}
}
