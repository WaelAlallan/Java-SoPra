package de.wwu.sopra.steuerung.biobankleiter;

import java.util.ArrayList;

import de.wwu.sopra.datenhaltung.DataFilter;
import de.wwu.sopra.datenhaltung.DataManagement;
import de.wwu.sopra.model.Studie;

/**
 * Diese Klasse ermoeglicht es Studien hinzuzufuegen, zu bearbeiten und zu
 * loeschen
 * 
 * @author Jasmin, Pia, Cedric
 *
 */
public class StudienSteuerung {

	/**
	 * gibt die Liste aller Studien zurueck in Zukunft gefiltert
	 * 
	 * @param name             Der Name der gesuchten Studie
	 * @param anzahlTeilnehmer die genaue Anzahl an Teilnehmern der Studie
	 * @return eine ArrayList an Studien zu denen die eingegebenen Parameter passen
	 */
	public ArrayList<Studie> getStudien(String name, String anzahlTeilnehmer) {

		DataManagement verwaltung = DataManagement.getInstance();

		if (anzahlTeilnehmer.equals("")) {
			return DataFilter.filterStudien(name, null, null, null);
		}
		try {
			int teilnehmer = Integer.parseInt(anzahlTeilnehmer);
			return DataFilter.filterStudien(name, teilnehmer, null, null);

		} catch (Exception e) {
			throw new IllegalArgumentException("Die Anzahl der Teilnehmer muss eine Zahl sein");
		}

	}

	/**
	 * erstellt eine neue Studie und speichert diese
	 * 
	 * @param name             Studienname
	 * @param anzahlTeilnehmer gewuenschte Anzahl der Teilnehmer
	 * @return die hinzugefuegte Studie
	 */
	public Studie studieHinzufuegen(String name, String anzahlTeilnehmer) {
		if (name.equals("") || anzahlTeilnehmer.equals("")) {
			throw new IllegalArgumentException(
					"Der Name und die gewuenschte Anzahl der Teilnehmer darf nicht leer sein.");
		}
		ArrayList<Studie> studie = DataFilter.filterStudien(name, null, null, null);
		if (studie.size() != 0) {
			throw new IllegalArgumentException("Dieser Name ist bereits vergeben");
		}
		int anzahlTeilnehmerInt = 0;
		try {
			anzahlTeilnehmerInt = Integer.parseInt(anzahlTeilnehmer);

			Studie neu = new Studie(name, anzahlTeilnehmerInt);

			DataManagement verwaltung = DataManagement.getInstance();
			verwaltung.speichereStudie(neu);
			return neu;
		} catch (Exception e) {
			throw new IllegalArgumentException("Die gewuenschte Anzahl der Teilnehmer muss eine ganze Zahl sein.");
		}

	}

	/**
	 * speichert die uebergebene Studie mit den neuen Eigenschaften
	 * 
	 * @param aktuelleStudie   zu bearbeitende Studie
	 * @param name             Studienname
	 * @param anzahlTeilnehmer gewuenschte Anzahl der Teilnehmer
	 * @return die hinzugefuegte Studie
	 */
	public Studie studieBearbeiten(Studie aktuelleStudie, String name, String anzahlTeilnehmer) {
		if (name.equals("") || anzahlTeilnehmer.equals("")) {
			throw new IllegalArgumentException(
					"Der Name und die gewuenschte Anzahl der Teilnehmer darf nicht leer sein.");
		}
		int anzahlTeilnehmerInt = 0;
		try {
			anzahlTeilnehmerInt = Integer.parseInt(anzahlTeilnehmer);

			aktuelleStudie.setName(name);
			aktuelleStudie.setAnzahlTeilnehmer(anzahlTeilnehmerInt);

			DataManagement verwaltung = DataManagement.getInstance();
			verwaltung.speichereStudie(aktuelleStudie);
			return aktuelleStudie;
		} catch (Exception e) {
			throw new IllegalArgumentException("Die gewuenschte Anzahl der Teilnehmer muss eine ganze Zahl sein.");
		}

	}

	/**
	 * loescht die uebergebene Studie
	 * 
	 * @param aktuelleStudie zu loeschende Studie
	 */
	public void studieLoeschen(Studie aktuelleStudie) {
		DataManagement verwaltung = DataManagement.getInstance();
		verwaltung.loescheStudie(aktuelleStudie);
	}

}
