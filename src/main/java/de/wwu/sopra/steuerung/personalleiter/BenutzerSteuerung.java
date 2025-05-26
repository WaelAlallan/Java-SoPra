package de.wwu.sopra.steuerung.personalleiter;

import javafx.util.*;

import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.util.ArrayList;

import de.wwu.sopra.datenhaltung.DataFilter;
import de.wwu.sopra.datenhaltung.DataManagement;
import de.wwu.sopra.model.Benutzer;
import de.wwu.sopra.model.PasswortHash;
import de.wwu.sopra.steuerung.alleuser.AuthentifizierungSteuerung;

/**
 * Diese Klasse dient der BenutzerSteuerung, es koennen Benutzer hinzugefuegt,
 * bearbeitet und geloescht werden
 * 
 * @author Jasmin, Pia, Cedric
 *
 */
public class BenutzerSteuerung {
	/**
	 * Die Filteroption soll hinzugefuegt werden in Zukunft Pruefen, ob Filter
	 * gueltig
	 * 
	 * @return die Liste mit Benutzern, evtl gefiltert
	 */
	public ArrayList<Benutzer> getBenutzer() {
		DataManagement benutzerverwaltung = DataManagement.getInstance();
		return benutzerverwaltung.getBenutzer();
	}

	/**
	 * Methode zum Hinzufuegen eines Benutzers anhand gegebener Eigenschaften.
	 * @pre vornanme nachname nicht null
	 * @param vorname    der Vorname des Benutzers der hinzugefuegt werden soll
	 * @param nachname  der Nachname des Benutzers der hinzugefuegt werden soll
	 * @param istNurse  ist true falls der Benutzer eine StudyNurse ist, sonst
	 *                   false
	 * @param istMTA    ist true falls der Benutzer eine MTA ist, sonst false
	 * @param istLeiter ist true falls der Benutzer eine BiobankLeiterin ist, sonst
	 *                   false
	 * @param istPerson ist true falls der Benutzer eine Personalleiterin ist,
	 *                   sonst false
	 * @return Paar	von Benutzername und Passwort
	 * @throws InvalidKeySpecException  wenn das Passwort nicht erstellt werden kann
	 * @throws NoSuchAlgorithmException wenn das Passwort nicht erstellt werden kann
	 */
	public Pair<String, String> benutzerHinzufuegen(String vorname, String nachname, boolean istNurse, boolean istMTA,
			boolean istLeiter, boolean istPerson) throws NoSuchAlgorithmException, InvalidKeySpecException {
		long passwortcode = (long) (Math.random() * 1000000000000.0);

		if (vorname.equals("") || nachname.equals("")) {
			throw new IllegalArgumentException("Der Name darf nicht leer sein.");
		}
		String passwort = "" + passwortcode;
		AuthentifizierungSteuerung authentifizierung = AuthentifizierungSteuerung.getInstance();
		PasswortHash verschluesseltesPasswort = authentifizierung.hashSecurely(passwort.toCharArray());
		Benutzer benutzer = new Benutzer(nachname, vorname, verschluesseltesPasswort);
		benutzer.setStudyNurse(istNurse);
		benutzer.setMTA(istMTA);
		benutzer.setBiobankLeiter(istLeiter);
		benutzer.setPersonalLeiter(istPerson);
		if (!(benutzer.isBiobankLeiter() || benutzer.isMTA() || benutzer.isPersonalLeiter()
				|| benutzer.isStudyNurse())) {
			throw new IllegalArgumentException("Der Benutzer muss mindestens eine Rolle haben");
		}
		DataManagement benutzerverwaltung = DataManagement.getInstance();

		int nummer = berechneBenutzerNummer(nachname, vorname);
		String benutzername = vorname + "." + nachname + nummer;
		benutzer.setBenutzername(benutzername);
		benutzerverwaltung.speichereBenutzer(benutzer);
		Pair<String, String> pair = new Pair<>(benutzername, passwort);
		return pair;
	}

	/**
	 * Diese Methode berechnet die Benutzerid, fuer den Benutzer welcher neu
	 * angelegt werden soll
	 * 
	 * @param nachname, des Benutzers der neu angelegt wird
	 * @param vorname,  des Benutzers der neu angelegt wird @return, die berechnete
	 *                  Benutzerid
	 */
	private int berechneBenutzerNummer(String nachname, String vorname) {
		int nummer = 1;
		while (DataFilter.filterBenutzer("", "", vorname + "." + nachname + nummer).size() > 0) {
			nummer++;
		}
		return nummer;
	}

	/**
	 * Diese Methode ist dafuer da um Benutzer zu filtern nach vorname und nachname
	 * 
	 * @param nachname der nachname nachdem die Benutzer gefiltert werden
	 * @param vorname der vorname nachdem die Benutzer gefiltert werden sollen
	 * @return eine Liste an Benuterobjekten die zu den eingegebenen parametern
	 *         passen
	 */
	public ArrayList<Benutzer> benutzerSuchen(String nachname, String vorname) {
		return DataFilter.filterBenutzer(nachname, vorname, "");
	}

	/**
	 * Mithilfe dieser Methode lassen sich Benutzer bearbeiten
	 * 
	 * @pre Der Benutzer ist nicht null, vorname und nachname nicht null
	 * @param benutzer   , der benutzer der Bearbeitet werden soll
	 * @param vorname   der neue/ alte vorname des Benutzers
	 * @param nachname  der neue/alte nachname des Benutzers
	 * @param istNurse  boolean ob der Benutzer StudyNurse ist
	 * @param istMTA    boolean ob der Benutzer MTA ist
	 * @param istLeiter  boolean ob der Benutzer Biobankleiter ist
	 * @param istPerson boolean ob der Benutzer personalleiter ist Diese Methode
	 *                   bearbeitet den uebergebenen Benutzer mit den anderen
	 *                   Parametern Das geaenderte Objekt wird abgespeichert
	 */
	public void benutzerBearbeiten(Benutzer benutzer, String vorname, String nachname, boolean istNurse, boolean istMTA,
			boolean istLeiter, boolean istPerson) {
		if (vorname.equals("") || nachname.equals("")) {
			throw new IllegalArgumentException("kein Vor- oder Nachname angegeben");
		}
		if (!(istNurse || istMTA || istLeiter || istPerson)) {
			throw new IllegalArgumentException("keine Rolle angegeben");
		}
		benutzer.setVorname(vorname);
		benutzer.setNachname(nachname);
		benutzer.setStudyNurse(istNurse);
		benutzer.setMTA(istMTA);
		benutzer.setBiobankLeiter(istLeiter);
		benutzer.setPersonalLeiter(istPerson);
		DataManagement benutzerverwaltung = DataManagement.getInstance();
		benutzerverwaltung.speichereBenutzer(benutzer);
	}

	/**
	 * Mithilfe dieser Methode lassen sich Benutzer loeschen
	 * Benutzer mit Visiten (Study Nurses) duerfen nicht geloescht werden,
	 * da sonst niemand die Visiten einsehen koennte	  
	 * @pre benutzer ist nicht null
	 * @param benutzer Diese Methode loescht den uebergebenen Benutzer
	 */
	public void benutzerLoeschen(Benutzer benutzer) {
		if(benutzer.getVisiten().size()!=0)
		{
			throw new IllegalArgumentException("Study Nurses mit Visiten duerfen nicht geloescht werden.");
		}
		DataManagement benutzerverwaltung = DataManagement.getInstance();
		benutzerverwaltung.loescheBenutzer(benutzer);
	}

}
