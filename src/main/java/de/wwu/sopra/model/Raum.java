package de.wwu.sopra.model;

import java.util.ArrayList;

/**
 * Probenaufbewahrung: Raum-Kuehlschrank-Segment-Gestell-Schublade-Rack-Probe
 * 
 * Entitaetsklasse fuer den Raum. Ein Raum ist in der Probenaufbewahrung das
 * meist uebergeordnete Objekt. Es kann mehrere Raeume im medizischen Institut
 * geben. In Raeumen befinden sich Kuehlschrank in denen die Proben letztlich
 * eingelagert werden.
 * 
 * @author Lukas
 *
 */
public class Raum implements Cloneable {
	private String name;
	private String standort;
	private double eingestellteTemperatur;
	private ArrayList<Kuehlschrank> kuehlschraenke;

	/**
	 * Bei der Erzeugung eines Raums muss sein Name und der Standort uebergeben
	 * werden.
	 * 
	 * @param name     (String) Name des Raums
	 * @param standort (String) Standort des Raums
	 */
	public Raum(String name, String standort) {
		this.name = name;
		this.standort = standort;
		kuehlschraenke = new ArrayList<Kuehlschrank>();
	}

	/**
	 * Setzt den Namen des Raums
	 * 
	 * @param name (String) Name des Raums
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * Gibt den Namen des Raums zurueck.
	 * 
	 * @return (String) Name des Raums
	 */
	public String getName() {
		return name;
	}

	/**
	 * Setzt den Standort des Raums
	 * 
	 * @param standort (String) Standort des Raums
	 */
	public void setStandort(String standort) {
		this.standort = standort;
	}

	/**
	 * Gibt den Standort des Raums zurueck.
	 * 
	 * @return (String) Standort des Raums
	 */
	public String getStandort() {
		return standort;
	}

	/**
	 * Setzt die Raumtemperatur auf den uebergebenen Wert.
	 * 
	 * @param eingestellteTemperatur (double) neue Raumtemperatur
	 */
	public void setEingestellteTemperatur(double eingestellteTemperatur) {
		this.eingestellteTemperatur = eingestellteTemperatur;
	}

	/**
	 * Gibt die Raumtemperatur zurueck.
	 * 
	 * @return (double) Raumtemperatur
	 */
	public double getEinstellteTemperatur() {
		return eingestellteTemperatur;
	}

	/**
	 * Setzt die Kuehschraenke des Raums.
	 * 
	 * @param kuehlschraenke (Arraylist) Liste der Kuehschranke die mit dem Raums
	 *                       verknuepft sind.
	 */
	public void setKuehlschraenke(ArrayList<Kuehlschrank> kuehlschraenke) {
		this.kuehlschraenke = kuehlschraenke;
	}

	/**
	 * Gibt die Liste der im Raum sich befindenen Kuehlschraenke zurueck.
	 * 
	 * @return (Arraylist) Liste aller sich im Raum befindenen Kuehlschraenke
	 */
	public ArrayList<Kuehlschrank> getKuehlschraenke() {
		return this.kuehlschraenke;
	}

	/**
	 * Fuegt dem Raum einen neuen Kuehlschrank hinzu.
	 * 
	 * @param kuehlschrank (Kuehlschrank) Neuer Kuehlschrank im Raum
	 */
	public void addKuehlschrank(Kuehlschrank kuehlschrank) {
		this.kuehlschraenke.add(kuehlschrank);
		kuehlschrank.setRaum(this);
	}

	/**
	 * Entfertn den Kuehlschrank aus der Liste der Kuehlschranke die sich im Raum
	 * befinden.
	 * 
	 * @param kuehlschrank Zu entfernender Kuehlschrank
	 */
	public void removeKuehlschrank(Kuehlschrank kuehlschrank) {
		this.kuehlschraenke.remove(kuehlschrank);
	}

	/**
	 * Ueberschreibt die toString Methode und gibt einen konkatenierten String aus
	 * Kuehlschrankename, Standort und eingestellert Temperatur zurueck.
	 */
	public String toString() {
		return getName() + ", " + getStandort() + ", " + getEinstellteTemperatur() + " \u00B0C";
	}

	/**
	 * Clont das Kuehlschrank Objekt.
	 */
	public Object clone() throws CloneNotSupportedException {
		return super.clone();
	}
}
