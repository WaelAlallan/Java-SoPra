package de.wwu.sopra.steuerung.studynurse;

import java.util.ArrayList;

import de.wwu.sopra.datenhaltung.DataFilter;
import de.wwu.sopra.model.Benutzer;
import de.wwu.sopra.model.Studie;
import de.wwu.sopra.steuerung.alleuser.AuthentifizierungSteuerung;

/**
 * Diese Klasse ermoeglicht alle Studien ggf. gefiltert abzurufen, an denen die
 * angemeldete StudyNurse beteiligt.
 * 
 * @author Pia, Jasmin, Cedric
 *
 */
public class StudieEinsehenSteuerung {

	/**
	 * Diese Methode gibt alle Studies ggf. gefiltert zurueck, an denen die
	 * angemeldete Study Nurse beteiligt ist.
	 * 
	 * @param name                        Name der Studie
	 * @param gewuenschteAnzahlTeilnehmer gewuenschte Anzahl der Teilnehmer
	 * @return Studienliste bezueglich des aktiven Benutzers, ggf. gefiltert
	 */
	public ArrayList<Studie> getStudien(String name, String gewuenschteAnzahlTeilnehmer) {
		Benutzer aktiverBenutzer = AuthentifizierungSteuerung.getInstance().getAktiverBenutzer();

		if (gewuenschteAnzahlTeilnehmer.equals("")) {
			return DataFilter.filterStudien(name, null, aktiverBenutzer, null);
		}
		Integer anzahlTeilnehmerInt = null;
		try {
			anzahlTeilnehmerInt = Integer.parseInt(gewuenschteAnzahlTeilnehmer);
		} catch (Exception e) {
			throw new IllegalArgumentException("Die gesuchte Anzahl an Teilnehmern muss eine Ganzzahl sein.");
		}

		return DataFilter.filterStudien(name, anzahlTeilnehmerInt, aktiverBenutzer, null);
	}

}
