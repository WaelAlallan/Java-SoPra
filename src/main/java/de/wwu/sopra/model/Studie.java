package de.wwu.sopra.model;

import java.util.ArrayList;

/**
 * Entitaetsklasse fuer Studien. Innerhalb der Biobankverwaltung koennen Studien
 * angelegt werden. Innerhalb der Studien werden Visitenvorlagen und die daraus
 * geplanten und durchgefuerhten Visiten, Patienten und Anzahl der Teilnehmer
 * gespeichert werden.
 *
 */
public class Studie {

	private String name;
	private int anzahlTeilnehmer;
	private ArrayList<Visite> visiten;
	private ArrayList<VisitenVorlage> visitenVorlage;
	private boolean hatPatienten;

	/**
	 * Bei der Erzeugung der Studie muss der Name der Studie und die Anzahl der
	 * Teilnehmer angelegt werden.
	 * 
	 * @param name             (String) Name der Studie
	 * @param anzahlTeilnehmer (int) Anzahl der Teilnehmer, die an der Studie
	 *                         teilnehmen werden.
	 */
	public Studie(String name, int anzahlTeilnehmer) {
		this.setName(name);
		this.setAnzahlTeilnehmer(anzahlTeilnehmer);
		this.visitenVorlage = new ArrayList<VisitenVorlage>();
		this.setVisiten(new ArrayList<Visite>());
	}

	/**
	 * Gibt den Namen der Studie zurueck.
	 * 
	 * @return (String) Name der Studie
	 */
	public String getName() {
		return name;
	}

	/**
	 * Setzt den Namen der Studie
	 * 
	 * @param name (String) Name der Studie
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * Gibt die Anazhl der Teilnehmer innerhalb einer Studie zurueck.
	 * 
	 * @return (int) Anzahl der Teilnehmer
	 */
	public int getAnzahlTeilnehmer() {
		return anzahlTeilnehmer;
	}

	/**
	 * Setzt die Anzahl der Teilnehmer der Studie
	 * 
	 * @param anzahlTeilnehmer (int) Anzahl der Teilnehmer
	 */
	public void setAnzahlTeilnehmer(int anzahlTeilnehmer) {
		this.anzahlTeilnehmer = anzahlTeilnehmer;
	}

	/**
	 * Gibt die Liste aller Visiten innerhalb einer Studie zurueck.
	 * 
	 * @return (Arraylist) Arraylist aller Visiten der Studie.
	 */
	public ArrayList<Visite> getVisiten() {
		return visiten;
	}

	/**
	 * Setzt die Liste der Visiten gleich den Visiten einer Studie.
	 * 
	 * @param visiten (ArrayList) Liste der uebergebenen Visiten
	 */
	public void setVisiten(ArrayList<Visite> visiten) {
		this.visiten = visiten;
	}

	/**
	 * Fuegt die neue Visite der Liste von Visiten innerhalb Studie hinzu.
	 * 
	 * @param visite (Visite) Neue Visite
	 */
	public void addVisite(Visite visite) {
		this.visiten.add(visite);
	}

	/**
	 * Entfertn die Visite aus der Liste der Visiten innerhalb einer Studie.
	 * 
	 * @param visite (Visite) Zuentfernene Visite
	 */
	public void removeVisite(Visite visite) {
		this.visiten.remove(visite);
	}

	/**
	 * Gibt die Liste aller Visitenvorlagen der Studie zurueck
	 * 
	 * @return (ArrayList) Liste aller Visitenvorlagen in der Studie
	 */
	public ArrayList<VisitenVorlage> getVisitenVorlage() {
		return visitenVorlage;
	}

	/**
	 * Fuegt der Liste von Visitenvorlagen die neue Visitenvorlage hinzu.
	 * 
	 * @param visitenVorlage (VisitenVorlage) Neue Visitenvorlage
	 */
	public void addVisitenVorlage(VisitenVorlage visitenVorlage) {
		this.visitenVorlage.add(visitenVorlage);
	}

	/**
	 * Entfernt die uebergebene Visitienvorlage aus der Liste Visitenvorlage der
	 * Studie
	 * 
	 * @param visitenVorlage (VisitenVorlage) Zuentfernene Visitenvorlage
	 */
	public void removeVisitenVorlage(VisitenVorlage visitenVorlage) {
		this.visitenVorlage.remove(visitenVorlage);
	}

	/**
	 * Gibt an, ob zu einer Studie schon Patienten hinzugefuegt worden sind
	 * 
	 * @return ein boolean
	 */
	public boolean hatPatienten() {
		return hatPatienten;
	}

	/**
	 * Setzt das Attribut hatPatienten einer Studie
	 * 
	 * @param hatPatienten der Wert
	 */
	public void setHatPatienten(boolean hatPatienten) {
		this.hatPatienten = hatPatienten;
	}

	/**
	 * Ueberschreibt die toString Methode und gibt einen konkatenierten String aus
	 * Studienname und Anzahl der Teilnehmer zurueck.
	 */
	public String toString() {
		return "Studie: " + name + " gewollte Anzahl an Teilnehmern: " + anzahlTeilnehmer;

	}

}
