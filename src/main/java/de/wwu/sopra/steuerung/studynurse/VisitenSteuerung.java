package de.wwu.sopra.steuerung.studynurse;

import java.util.Date;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

import de.wwu.sopra.datenhaltung.DataFilter;
import de.wwu.sopra.model.Benutzer;
import de.wwu.sopra.model.Studie;
import de.wwu.sopra.model.Visite;
import de.wwu.sopra.steuerung.alleuser.AuthentifizierungSteuerung;
import de.wwu.sopra.steuerung.helfer.DatumHelfer;
import javafx.util.Pair;

/**
 * Diese Klasse ist dafuer da um Visiten zu suchen und zu bearbeiten
 * 
 * @author Jasmin, Pia, Cedric
 *
 */
public class VisitenSteuerung {
	/**
	 * Mithilfe dieser Methode lassen sich Visiten suchen
	 * 
	 * @param vorname       (String) Vorname des Patienten
	 * @param nachname      (String) Nachname des Patienten
	 * @param datum         (Date) Datum der Visite
	 * @param studie        (Studie) Studie zur Visite
	 * @param durchgefuehrt (boolean) Status der Durchfuehrung
	 * @return (Arraylist) Arraylist aller Visiten, auf die der Filter zutrifft
	 */

	public ArrayList<Visite> visitenSuchen(String vorname, String nachname, String datum, String studie,
			Boolean durchgefuehrt) {
		Benutzer aktiverBenutzer = AuthentifizierungSteuerung.getInstance().getAktiverBenutzer();

		SimpleDateFormat df = new SimpleDateFormat("dd.MM.yyy");
		df.setLenient(false);

		Date date = null;
		if (!datum.contentEquals("")) {
			try {
				date = df.parse(datum);
			} catch (Exception ex) {
				throw new IllegalArgumentException("Datum ist nicht gueltig");
			}
		}

		ArrayList<Studie> studien = DataFilter.filterStudien(studie, null, aktiverBenutzer, null);

		return DataFilter.filterVisiten(aktiverBenutzer, nachname, vorname, date, studien, durchgefuehrt);
	}

	/**
	 * Die Methode traegt zu der gewunschten Visite die Daten ein, die von der Nurse
	 * gemessen und eingetragen wurden
	 * 
	 * @param visite zu bearbeitende Visite
	 * @param daten  eingegebene Daten
	 * @param datum  Durchfuehrungsdatum
	 * @return (Visite) Gibt die bearbeitete Visite zurueck
	 */
	public Visite visiteBearbeiten(Visite visite, ArrayList<Pair<String, String>> daten, String datum) {

		Date date = null;
		if (!datum.contentEquals("")) {
			date = DatumHelfer.erzeugeDatum(datum);
		}

		visite.setDatum(date);

		for (Pair<String, String> datei : daten) {
			if (datei.getValue().equals("")) {
				throw new IllegalArgumentException("Alle Felder muessen ausgefuellt sein");
			}

		}
		ArrayList<Pair<String, String>> neueDatenListe = new ArrayList<>();

		for (int i = 0; i < daten.size(); i++) {
			String keyOriginal = visite.getMedizinscheDaten().get(i).getKey();
			String keyNeu = daten.get(i).getKey();
			String valueNeu = daten.get(i).getValue();

			if (!keyOriginal.contentEquals(keyNeu))
				throw new IllegalArgumentException("Die medizinischen Daten fuer " + keyNeu
						+ " werden fuer diese Visite nicht unterstuetzt, sondern " + keyOriginal + ".");

			neueDatenListe.add(daten.get(i));
		}

		visite.setMedizinischeDaten(neueDatenListe);
		return visite;
	}

	/**
	 * Diese Methode gibt die Visite anhand der Visitenid zurueck
	 * 
	 * @param visiteId die visitenid nachdem gefiltert werden soll
	 * @return die Visite passend zur Visitenid
	 * @throws IllegalArgumentException Fehler fuer ein ungueltiges Argument.
	 */
	public Visite getVisite(int visiteId) throws IllegalArgumentException {
		return DataFilter.filterVisiteById(visiteId);
	}

}
