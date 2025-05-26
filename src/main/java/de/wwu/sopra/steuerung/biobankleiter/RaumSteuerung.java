package de.wwu.sopra.steuerung.biobankleiter;

import java.util.ArrayList;

import de.wwu.sopra.datenhaltung.DataFilter;
import de.wwu.sopra.datenhaltung.DataManagement;
import de.wwu.sopra.model.Raum;

/**
 * Diese Klasse kann alle Raeume ausgeben, einen Raum hinzufuegen, bearbeiten
 * oder loeschen
 * 
 * @author Jasmin, Pia, Cedric
 *
 */

public class RaumSteuerung {

	/**
	 * Diese Methode gibt die Raumliste zurueck ggf gefiltert Filter noch
	 * hinzufuegen
	 * 
	 * @return Liste der Raeume ggf. gefiltert
	 */
	public ArrayList<Raum> getRaeume() {
		return DataFilter.filterRaeume("", "", null);
	}

	/**
	 * Diese Methode gibt eine Liste mit allen Raeumen zurueck, die auf die
	 * eingegebenen Paramenter zutreffen
	 * 
	 * @param name       der name des raumes
	 * @param standort   der standort des raumes
	 * @param temperatur die temperatur des raumes
	 * @return eine Liste aller zutreffender Raeume
	 */
	public ArrayList<Raum> getRaeume(String name, String standort, String temperatur) {
		Double temperaturdouble = null;

		if (temperatur.contains(",")) {
			temperatur = temperatur.replace(",", ".");
		}

		try {
			if (!temperatur.equals(""))
				temperaturdouble = Double.parseDouble(temperatur);

		} catch (Exception e) {
			throw new IllegalArgumentException("Die eingestellte Temperatur ist keine Doublezahl");
		}

		return DataFilter.filterRaeume(name, standort, temperaturdouble);
	}

	/**
	 * Diese Methode erzeugt einen Raum und speichert ihn
	 * 
	 * @param name                    der name des Raums welcher erstellt werden
	 *                                solll
	 * @param standort               der standort des Raums welcher erstellt werden
	 *                                soll
	 * @param eingestellteTemperatur die Temperatur des Raums welcher erstellt
	 *                                werden soll
	 * 
	 * @pre name, standort, eingestellteTemperatur nicht null
	 * @post Der neue Raum wurde gespeichert
	 */
	public void raumHinzufuegen(String name, String standort, String eingestellteTemperatur) {
		DataManagement verwaltung = DataManagement.getInstance();

		if (name.equals("") || standort.equals("")) {
			throw new IllegalArgumentException("Der Raumname oder der Standort ist leer.");
		}

		if (eingestellteTemperatur.contains(",")) {
			eingestellteTemperatur = eingestellteTemperatur.replace(",", ".");
		}
		double temperatur;
		try {
			temperatur = Double.parseDouble(eingestellteTemperatur);
			Raum neu = new Raum(name, standort);
			neu.setEingestellteTemperatur(temperatur);
			if (DataFilter.filterRaeume(name, standort, null).size() == 0) {
				verwaltung.speichereRaum(neu);
			} else {
				throw new IllegalArgumentException("Ein Raum mit denselben Eigenschaften existiert schon.");
			}

		} catch (Exception e) {
			throw new IllegalArgumentException("Die eingestellte Temperatur ist keine Kommazahl.");
		}

	}

	/**
	 * Diese Methode speichert den Raum mit den Parametern
	 * 
	 * @param raum                  der raum der bearbeitet werden soll
	 * @param name                  der neue/alte name des raums
	 * @param standort              der neue /alte standort des raums
	 * @param eingestellteTemperatur die neue /alte temperatur des Raums
	 * 
	 * @pre raum, name, standort, eingestellteTemperatur nicht null
	 * @post Der Raum ist mit den neuen Eigenschaften gespeichert
	 */
	public void raumBearbeiten(Raum raum, String name, String standort, String eingestellteTemperatur) {
		DataManagement verwaltung = DataManagement.getInstance();

		if (name.equals("") || standort.equals("")) {
			throw new IllegalArgumentException("Der Raumname oder der Standort ist leer.");
		}

		if (eingestellteTemperatur.contains(",")) {
			eingestellteTemperatur = eingestellteTemperatur.replace(",", ".");
		}
		double temperatur;
		try {
			temperatur = Double.parseDouble(eingestellteTemperatur);
			raum.setName(name);
			raum.setStandort(standort);
			raum.setEingestellteTemperatur(temperatur);
			if (DataFilter.filterRaeume(name, standort, null).size() == 0
					|| DataFilter.filterRaeume(name, standort, null).size() == 1
							&& raum == DataFilter.filterRaeume(name, standort, null).get(0)) {
				verwaltung.speichereRaum(raum);
			} else {
				throw new IllegalArgumentException("Ein Raum mit denselben Eigenschaften existiert schon.");
			}

		} catch (Exception e) {
			throw new IllegalArgumentException("Die eingestellte Temperatur ist keine Kommazahl.");
		}

	}

	/**
	 * Diese Methode loescht den angegebenen Raum
	 * 
	 * @param raum der Raum welcher geloescht werden soll
	 * 
	 * @post raum existiert nicht mehr
	 */
	public void raumLoeschen(Raum raum) {
		DataManagement verwaltung = DataManagement.getInstance();
		verwaltung.loescheRaum(raum);
	}
}
