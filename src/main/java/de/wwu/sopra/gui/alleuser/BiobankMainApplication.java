package de.wwu.sopra.gui.alleuser;

import java.io.IOException;

import de.wwu.sopra.datenhaltung.DataManagement;
import de.wwu.sopra.steuerung.alleuser.AuthentifizierungSteuerung;
import javafx.application.Application;
import javafx.stage.Stage;

/**
 * Hauptklasse der Biobank diese Klasse wird als erstes aufgerufen und erstellt
 * einfach nur ein AnmeldenFormular, welches dann die GUI startet und auf
 * eingaben wartet
 * 
 * @author Alexander Leifhelm, Sebastian Huck, Wael Al Allan
 */
public class BiobankMainApplication extends Application {

	/**
	 * Init Methode laedt die Daten und erstellt ggf. eine neue Datenbasis
	 */
	public void init() {
		DataManagement.getInstance();
	}

	/**
	 * Start Methode wird aus Application ueberschrieben und zeigt das erste AnmeldenFormular
	 * @param primaryStage Das primaere Fenster wird uebergeben
	 * @throws Exception  Exception
	 */
	public void start(Stage primaryStage) throws Exception {
		
		AnmeldenFormular plmf = new AnmeldenFormular(primaryStage);
		primaryStage.show();
	}

	/**
	 * Main Methode ruft die launch Methode auf
	 * @param args String array der Eingabe wird uebergeben
	 */
	public static void main(String[] args) {
		launch(args);
	}

	/**
	 * Stop Methode wird aufgerufen, wenn das Fenster geschlossen wird
	 * Die Methode speichert die Daten
	 */
	public void stop() {
		try {
			AuthentifizierungSteuerung.getInstance().persistAllData();
		} catch (IOException e) {
			System.out.println("Fehler beim Speichern");
		}
	}

}
