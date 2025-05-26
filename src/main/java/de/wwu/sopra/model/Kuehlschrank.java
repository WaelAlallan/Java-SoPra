package de.wwu.sopra.model;

import java.util.ArrayList;

/**
 * Probenaufbewahrung: Raum-Kuehlschrank-Segment-Gestell-Schublade-Rack-Probe
 * 
 * Entitaetsklasse fuer Kuehlschraenke. Kuehlschraenke dienen der
 * Probenaufbewahrung und sind Teil eines Raums und beinhalten Segmente.
 */
public class Kuehlschrank implements Cloneable {
	private String name;
	private double eingestellteTemperatur;
	private Raum raum;
	private ArrayList<Segment> segmente;

	/**
	 * Bei der Erstellung des Kuehlschranks muss der Name, die eingestellte
	 * Temperatur und der uebergeordnete Raum uebergeben werden.
	 * 
	 * @param name                   (String) Name des Kuehlschranks
	 * @param eingestellteTemperatur (int) Eingestellte Temperatur des Kuehlschranks
	 * @param raum                   (Raum) Der Raum in dem der Kuehlschrank steht
	 */
	public Kuehlschrank(String name, double eingestellteTemperatur, Raum raum) {
		this.name = name;
		this.eingestellteTemperatur = eingestellteTemperatur;
		this.raum = raum;
		segmente = new ArrayList<Segment>();
		for (int i = 0; i < 4; i++) {
			segmente.add(new Segment("Seg" + (i + 1), this));
		}
		raum.addKuehlschrank(this);
	}

	/**
	 * Gibt den Namen des Kuehlschranks zurueck.
	 * 
	 * @param name (String) Name des Kuehlschranks.
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * Setzt den Namen des Kuehlschranks.
	 * 
	 * @return (String) Name des Kuehlschranks.
	 */
	public String getName() {
		return name;
	}

	/**
	 * Setzt die Temperatur des Kuehlschranks auf den uebergebenen Wert.
	 * 
	 * @param eingestellteTemperatur (int) Kuehlschranktemperatur
	 */
	public void setEingestellteTemperatur(double eingestellteTemperatur) {
		this.eingestellteTemperatur = eingestellteTemperatur;
	}

	/**
	 * Gibt die aktuell eingestellte Kuehlschranktemperatur zurueck.
	 * 
	 * @return (int) aktuelle Kuehlschranktemperatur
	 */
	public double getEingestellteTemperatur() {
		return eingestellteTemperatur;
	}

	/**
	 * Setzt den Raum des Kuehlschranks gleich dem uebergenen Raum.
	 * 
	 * @param raum (Raum) Neuer Raum in den der Kuehlschrank stehen soll
	 */
	public void setRaum(Raum raum) {
		this.raum = raum;
		ArrayList<Kuehlschrank> kuehlschraenke = raum.getKuehlschraenke();
		if (!kuehlschraenke.contains(this))
			raum.addKuehlschrank(this);
	}

	/**
	 * Gibt den aktuellen Raum zurueck in dem der Kuehlschrank steht.
	 * 
	 * @return (Raum) aktueller Raum
	 */
	public Raum getRaum() {
		return raum;
	}

	/**
	 * Setzt dem Kuehlschrank eine Liste von Segmenten.
	 * 
	 * @param segmente (Arraylist) Arraylist von Segmenten, die in dem Kuehlschrank
	 *                 gesetzt werden
	 */
	public void setSegmente(ArrayList<Segment> segmente) {
		this.segmente = segmente;
	}

	/**
	 * Gibt die Liste aller Segmente eines Kuehlschranks zurueck.
	 * 
	 * @return (Arraylist) Liste aller Segmente eines Kuehlschranks
	 */
	public ArrayList<Segment> getSegmente() {
		return segmente;
	}

	/**
	 * Fuegt dem Kuehlschrank ein Segment hinzu.
	 * 
	 * @param segment (Segment) Fuegt der Arraylist der Kuehlschranksegmente ein
	 *                neues Segment hinzu.
	 */
	public void addSegment(Segment segment) {
		this.segmente.add(segment);
		segment.setKuehlschrank(this);
	}

	/**
	 * Entfernt das Segment des Kuehlschranks.
	 * 
	 * @param segment (Segment) Entfernt das Segment aus der Arraylist der
	 *                Kuehlschranksegmente.
	 */
	public void removeSegment(Segment segment) {
		this.segmente.remove(segment);
	}

	/**
	 * Ueberschreibt die toString Methode und gibt einen String zurueck, der den
	 * Namen des Kuehlschranks, die eingestellte Temperatur und den Raum, in dem der
	 * Kuehlschrank steht, konkateniert.
	 */
	public String toString() {
		return getName() + " (" + getEingestellteTemperatur() + "\u00B0C) in " + getRaum().toString();
	}

	/**
	 * Gibt ein geclontes Kuehlschrankobjekt zurueck.
	 */
	public Object clone() throws CloneNotSupportedException {
		return super.clone();
	}
}
