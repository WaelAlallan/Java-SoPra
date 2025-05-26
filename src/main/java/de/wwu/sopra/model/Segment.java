package de.wwu.sopra.model;

import java.util.ArrayList;

/**
 * Probenaufbewahrung: Raum-Kuehlschrank-Segment-Gestell-Schublade-Rack-Probe
 * 
 * Entitaetsklasse fuer Segmente. Ein Segment der Probenaufbewahrung. Ein
 * Segment befindet sich innerhalb eines Kuehlschranks. Ein Segment beinhaltet
 * mehrere Gestelle.
 *
 */
public class Segment {
	private String name;
	private Kuehlschrank kuehlschrank;
	private ArrayList<Gestell> gestelle;

	/**
	 * Bei der Erstellung eines Segments muss der Name des Segments und der
	 * Kuehlschrank indem sich das Segment befinden soll, uebergeben werden.
	 * 
	 * @param name         (String) Name des Kuehlschranks
	 * @param kuehlschrank (Kuehlschrank) Kuehlschrank in dem sich das Segment
	 *                     befindet
	 */
	public Segment(String name, Kuehlschrank kuehlschrank) {
		this.name = name;
		this.kuehlschrank = kuehlschrank;
		gestelle = new ArrayList<Gestell>();
		for (int i = 0; i < 4; i++) {
			gestelle.add(new Gestell("Gest" + (i + 1), this));
		}
	}

	/**
	 * Setzt den Namen des Segements
	 * 
	 * @param name (String) Name des Segments
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * Gibt den Namen des Segments zurueck.
	 * 
	 * @return (String) Name des Segments
	 */
	public String getName() {
		return name;
	}

	/**
	 * Setzt den Kuehlschrank in dem sich das Segment befinden soll.
	 * 
	 * @param kuehlschrank (Kuehschrank) Kuehlschrank des Segments
	 */
	public void setKuehlschrank(Kuehlschrank kuehlschrank) {
		this.kuehlschrank = kuehlschrank;
	}

	/**
	 * Gibt den Kuehlschrank des Segments zurueck.
	 * 
	 * @return (Kuehlschrank) Gibt den Kuehlschrank des Segments zurueck
	 */
	public Kuehlschrank getKuehlschrank() {
		return kuehlschrank;
	}

	/**
	 * Setzt die Gestellt des Segments.
	 * 
	 * @param gestelle (ArrayList) Liste von Gestellen die im Segment neu gesetzt
	 *                 werden sollen.
	 */
	public void setGestelle(ArrayList<Gestell> gestelle) {
		this.gestelle = gestelle;
	}

	/**
	 * Gitb die Liste aller Gestellt innerhalb eines Segments zurueck.
	 * 
	 * @return (ArrayList) ArrayList der Gestelle innerhalb des Segments
	 */
	public ArrayList<Gestell> getGestelle() {
		return gestelle;
	}

	/**
	 * Fuegt der Liste der Gestelle das neue Gestell innerhalb des Segments hinzu.
	 * 
	 * @param gestell (Gestellt) Neues Gestell in der Liste der Segment Gestelle
	 */
	public void addGestell(Gestell gestell) {
		this.gestelle.add(gestell);
		gestell.setSegment(this);
	}

	/**
	 * Entfertn das uebergebene Gestell aus der Liste der Gestelle des Segments.
	 * 
	 * @param gestell (Gestell) Entfernt das Gestell aus der Liste der Gestell.
	 */
	public void removeGestell(Gestell gestell) {
		this.gestelle.remove(gestell);
	}
}
