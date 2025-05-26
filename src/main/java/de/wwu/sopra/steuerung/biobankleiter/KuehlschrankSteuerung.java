package de.wwu.sopra.steuerung.biobankleiter;

import java.util.ArrayList;

import de.wwu.sopra.datenhaltung.DataFilter;
import de.wwu.sopra.datenhaltung.DataManagement;
import de.wwu.sopra.model.Kuehlschrank;
import de.wwu.sopra.model.Raum;

/**
 * Diese Klasse dient zur Verwaltung der Kuehlschranken
 * 
 * @author Jasmin, Pia, Cedric
 *
 */
public class KuehlschrankSteuerung {

	/**
	 * gibt Liste der Kuehlschraenke zurueck in Zukunft auch gefiltert
	 * 
	 * @return Liste von Kuehlschraenken
	 */
	public ArrayList<Kuehlschrank> getKuehlschraenke() {
		return DataFilter.filterKuehlschraenke("", null, null);
	}

	/**
	 * Gibt die Kuehlschraenke mit den angegebenen Eigenschaften zurueck.
	 * @param name                   Kuehlschrankname
	 * @param eingestellteTemperatur eingestellte Temperatur
	 * @param raum                   Raumname
	 * @return eine Liste an Kuehschranken zu denen die eingegebenen Parametern
	 *         passen
	 */
	public ArrayList<Kuehlschrank> getKuehlschraenke(String name, String eingestellteTemperatur, Raum raum) {
		Double temperatur = null;
		if (!eingestellteTemperatur.equals("")) {
			eingestellteTemperatur.replace(".", ",");
			try {
				temperatur = Double.parseDouble(eingestellteTemperatur);
			} catch (Exception e) {
				throw new IllegalArgumentException("Die Temperatur muss eine Kommazahl sein.");
			}
		}
		/*
		 * if (verwaltung.getRaeume(raum.getName(), "", null).size() == 1) { raum =
		 * verwaltung.getRaeume(raum.getName(), "", null).get(0); }
		 */
		return DataFilter.filterKuehlschraenke(name, temperatur, raum);
	}

	/**
	 * erstellt Kuehlschrank und speichert ihn
	 * 
	 * @param name                    der name des Kuehlschranks der hinzugefuegt
	 *                                werden soll
	 * @param eingestellteTemperatur die eingestellteTemperatur des Kuehlschranks
	 *                                der hinzugefuegt werden soll
	 * @param raum                   der raum indem der Kuehlschrank hinzugefuegt
	 *                                werden soll
	 * @return der hinzugefuegte Kuehlschrank
	 */
	public Kuehlschrank kuehlschrankHinzufuegen(String name, String eingestellteTemperatur, Raum raum) {
		DataManagement verwaltung = DataManagement.getInstance();

		if (name.equals("")) {
			throw new IllegalArgumentException("Kein Name angegeben.");
		}
		eingestellteTemperatur = eingestellteTemperatur.replace(',', '.');

		double temperatur;
		try {
			temperatur = Double.parseDouble(eingestellteTemperatur);

		} catch (Exception e) {
			throw new IllegalArgumentException("Die eingestellte Temperatur ist keine Kommazahl.");
		}
		if (raum == null) {
			throw new IllegalArgumentException("Der angegebene Raum existiert nicht");
		}

		if (DataFilter.filterKuehlschraenke(name, null, raum).size() == 0) {
			Kuehlschrank neu = new Kuehlschrank(name, temperatur, raum);
			verwaltung.speichereRaum(raum);
			return neu;
		} else {
			throw new IllegalArgumentException("Dieser Kuehlschrank existiert schon in diesem Raum.");
		}

	}

	/**
	 * speichert den uebergebenen Kuehlschrank mit den neuen Eigenschaften
	 * 
	 * @param kSchrank               der Kuehlschrank der bearbeitet werden soll
	 * @param name                   der neue/ alte name des Kuehlschranks
	 * @param eingestellteTemperatur die neue / alte Temperatur des Kuehlschranks
	 * @param raum                    der neue/alte raum des Kuehlschranks
	 * @return der Kuehlschrank
	 */
	public Kuehlschrank kuehlschrankBearbeiten(Kuehlschrank kSchrank, String name, String eingestellteTemperatur,
			Raum raum) {
		DataManagement verwaltung = DataManagement.getInstance();

		if (name.equals("")) {
			throw new IllegalArgumentException("Kein Name angegeben.");
		}

		if (eingestellteTemperatur.contains(",")) {
			eingestellteTemperatur = eingestellteTemperatur.replace(",", ".");
		}

		double temperatur;
		try {
			temperatur = Double.parseDouble(eingestellteTemperatur);

		} catch (Exception e) {
			throw new IllegalArgumentException("Die eingestellte Temperatur ist keine Kommazahl.");
		}

		if (raum == null) {
			throw new IllegalArgumentException("Der angegebene Raum existiert nicht");
		}
		kSchrank.setName(name);
		kSchrank.setEingestellteTemperatur(temperatur);
		kSchrank.getRaum().removeKuehlschrank(kSchrank);
		raum.addKuehlschrank(kSchrank);

		if (DataFilter.filterKuehlschraenke(name, null, raum).size() == 0
				|| DataFilter.filterKuehlschraenke(name, null, raum).size() == 1
						&& DataFilter.filterKuehlschraenke(name, null, raum).get(0) == kSchrank) {
			verwaltung.speichereRaum(kSchrank.getRaum());
			return kSchrank;
		} else {
			throw new IllegalArgumentException("Dieser Kuehlschrank existiert schon in diesem Raum.");
		}
	}

	/**
	 * loescht den angegebenen Kuehlschrank, wenn dieser leer ist
	 * 
	 * @param kSchrank der Kuehlschrank welcher geloescht werden soll
	 */
	public void kuehlschrankLoeschen(Kuehlschrank kSchrank) {

		DataManagement verwaltung = DataManagement.getInstance();

		// Pruefen, ob Kuehlschrank leer ist
		int anzahlProbenInKuehlschrank = DataFilter.filterProben(kSchrank).size();
		if (anzahlProbenInKuehlschrank == 0) {
			kSchrank.getRaum().removeKuehlschrank(kSchrank);
		} else {
			throw new IllegalArgumentException(
					"Der K\u00FChlschrank ist nicht leer. Er muss zun\u00E4chst geleert werden.");
		}

	}

}
