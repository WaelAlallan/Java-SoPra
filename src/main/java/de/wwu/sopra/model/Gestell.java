package de.wwu.sopra.model;

import java.util.ArrayList;

/**
 * Probenaufbewahrung: Raum-Kuehlschrank-Segment-Gestell-Schublade-Rack-Probe
 * 
 * Entitaetsklasse fuer Gestelle. Gestelle dienen der Probenaufbewahrung und
 * sind Teil eines Segments und beinhalten Schubladen.
 */
public class Gestell {
	private String name;
	private Segment segment;
	private ArrayList<Schublade> schubladen;

	/**
	 * Bei der Erstellung des Gestells muss der Name und das uebergeordnete Segment
	 * uebergeben werden.
	 * 
	 * @param name    (String) Name des Gestells
	 * @param segment (Segment) Uebergeordnetes Segment
	 */
	public Gestell(String name, Segment segment) {
		this.name = name;
		this.segment = segment;
		schubladen = new ArrayList<Schublade>();
		for (int i = 0; i < 5; i++) {
			schubladen.add(new Schublade("Schubl" + (i + 1), this));
		}
	}

	/**
	 * Setzt den Name des Racks.
	 * 
	 * @param name (String) Name des Gestells
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * Gibt den Name des Gestells zurueck.
	 * 
	 * @return (String) Name des Gestells
	 */
	public String getName() {
		return name;
	}

	/**
	 * Setzt das Parent- bzw. uebergeordnete Segement des Gestells.
	 * 
	 * @param segment (Segement) Parent Segment
	 */
	public void setSegment(Segment segment) {
		this.segment = segment;
	}

	/**
	 * Gibt das parent Segment des Gestells zurueck.
	 * 
	 * @return (Segment) Parent Segment des Gestells
	 */
	public Segment getSegment() {
		return segment;
	}

	/**
	 * Verknuepft die uebergebenen Schubladen mit dem Gestell
	 * 
	 * @param schubladen (ArrayList) Speichert die neuen Schubladen in einer
	 *                   Arraylist innerhalb des Gestells
	 */
	public void setSchubladen(ArrayList<Schublade> schubladen) {
		this.schubladen = schubladen;
	}

	/**
	 * Gibt die Liste aller Schubladen eines Gestells zurueck.
	 * 
	 * @return (ArrayList) Gibt die ArrayList von Schubladen eines Gestells zurueck.
	 */
	public ArrayList<Schublade> getSchubladen() {
		return schubladen;
	}

	/**
	 * Fuegt dem Gestell eine Schublade hinzu und fuegt sie in die Arraylist ein.
	 * 
	 * @param schublade (Schublade) Neue Schublade wird in die Arraylist der
	 *                  Schubladen hinzugefuegt
	 */
	public void addSchublade(Schublade schublade) {
		this.schubladen.add(schublade);
		schublade.setGestell(this);
	}

	/**
	 * Entfernt von einem Gestell die uebergebene Schublade und entfernt sie aus der
	 * Liste der Schubladen.
	 * 
	 * @param schublade (Schublade) Entfernt die Schublade aus der ArrayList der
	 *                  Schubladen des Gestells.
	 */
	public void removeSchublade(Schublade schublade) {
		this.schubladen.remove(schublade);
	}
}
