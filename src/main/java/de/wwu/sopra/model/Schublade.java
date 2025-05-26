package de.wwu.sopra.model;

import java.util.ArrayList;

/**
 * Probenaufbewahrung: Raum-Kuehlschrank-Segment-Gestell-Schublade-Rack-Probe
 * 
 * Entitaetsklasse fuer die Schublade. Schubladen dienen der Probenaufbewahrung.
 * In ihnen befinden sich Racks. Schubladen befinden sich in einem Gestell.
 *
 */
public class Schublade {
	private String name;
	private Gestell gestell;
	private ArrayList<Rack> racks;

	/**
	 * Bei der Erstellung einer Schublade muss ihre Name und das uebergeordnete
	 * Gestell uebergeben werden.
	 * 
	 * @param name    (String) Name der Schublade
	 * @param gestell (Gestell) Uebergeordnetes Gestell
	 */
	public Schublade(String name, Gestell gestell) {
		this.name = name;
		this.gestell = gestell;
		setRacks(new ArrayList<Rack>());
	}

	/**
	 * Setzt den Namen der Schublade.
	 * 
	 * @param name (String) Name der Schublade
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * Gibt den Namen der Schublade zurueck
	 * 
	 * @return (String) Name der Schublade
	 */
	public String getName() {
		return name;
	}

	/**
	 * Setzt das uebergeordnete Gestell der Schublade
	 * 
	 * @param gestell (Gestell) Gestell der Schublade
	 */
	public void setGestell(Gestell gestell) {
		this.gestell = gestell;
	}

	/**
	 * Gibt das Gestell der Schublade zurueck.
	 * 
	 * @return (Gestell) Gestell der Schublade
	 */
	public Gestell getGestell() {
		return gestell;
	}

	/**
	 * Setzt die Racks der Schublade gleich der Liste der uebergebenen Racks.
	 * 
	 * @param racks (ArrayList) Liste der neuen Racks
	 */
	public void setRacks(ArrayList<Rack> racks) {
		this.racks = racks;
	}

	/**
	 * Gibt die Liste aller Racks innerhalb einer Schublade zurueck.
	 * 
	 * @return (ArrayList) Liste aller Rack in der Schublade
	 */
	public ArrayList<Rack> getRacks() {
		return racks;
	}

	/**
	 * Fuegt der Schublade das uebergebene Rack hinzu.
	 * 
	 * @param rack (Rack) Neues Rack innerhalb der Schublade
	 */
	public void addRack(Rack rack) {
		this.racks.add(rack);
		rack.setSchublade(this);
	}

	/**
	 * Entfern das Rack aus der Schublade.
	 * 
	 * @param rack (Rack) Zuentfernenes Rack in der Schublade
	 */
	public void removeRack(Rack rack) {
		this.racks.remove(rack);
	}
}
