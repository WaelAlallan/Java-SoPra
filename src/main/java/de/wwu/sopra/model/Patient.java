package de.wwu.sopra.model;

import java.util.ArrayList;

/**
 * Entitaetsklasse fuer Patienten. Patienten sind Menschen, die im medizinischen
 * Institut zur Untersuchung bestimmter Studien stationaer beherbergt werden. In
 * Folge der Untersuchungen werden ihnen Proben entnommen, die in der Biobank
 * eingelagert werden.
 *
 */
public class Patient {
	private int patientenId;
	private String nachname;
	private String vorname;
	private String adresse;

	private ArrayList<Visite> visiten;

	/**
	 * Bei der Erstellung des Patienten muss die PatientenId, der Nachname des
	 * Patienten, sowie der Vorname uebergeben werden.
	 * 
	 * @param patientenId (int) Id des Patienten
	 * @param nachname    (String) Nachname des Patienten
	 * @param vorname     (String) Vorname des Patienten
	 */
	public Patient(int patientenId, String nachname, String vorname) {
		this.patientenId = patientenId;
		this.setNachname(nachname);
		this.setVorname(vorname);
		setVisiten(new ArrayList<Visite>());
	}

	/**
	 * Gibt die PatientenID zurueck.
	 * 
	 * @return (int) eindeutige ID eines Patienten
	 */
	public int getPatientenId() {
		return patientenId;
	}

	/**
	 * Gibt den Nachnamen des Patienten zurueck.
	 * 
	 * @return (String) Nachname des Patienten
	 */
	public String getNachname() {
		return nachname;
	}

	/**
	 * Setzt den Nachnamen des Patienten.
	 * 
	 * @param nachname (String) Nachname des Patienten
	 */
	public void setNachname(String nachname) {
		this.nachname = nachname;
	}

	/**
	 * Gibt den Vornamen des Patienten zurueck.
	 * 
	 * @return (String) Vorname des Patienten
	 */
	public String getVorname() {
		return vorname;
	}

	/**
	 * Setzt den Vornamen des Patienten zurueck.
	 * 
	 * @param vorname (String) Vorname des Patienten
	 */
	public void setVorname(String vorname) {
		this.vorname = vorname;
	}

	/**
	 * Gibt die Adresse des Patienten zurueck.
	 * 
	 * @return (String) Adresse des Patienten
	 */
	public String getAdresse() {
		return adresse;
	}

	/**
	 * Setzt die Adresse des Patienten.
	 * 
	 * @param adresse (String) Adresse des Patienten
	 */
	public void setAdresse(String adresse) {
		this.adresse = adresse;
	}

	/**
	 * Gibt die Liste aller Visiten, die mit dem Patienten verknuepft sind.
	 * 
	 * @return (ArrayList) Liste aller Visiten des Patienten
	 */
	public ArrayList<Visite> getVisiten() {
		return visiten;
	}

	/**
	 * Setzt die uebergebenen Visiten gleich den Visiten des Patienten.
	 * 
	 * @param visiten (ArrayList) Liste der uebergebenen Visiten
	 */
	public void setVisiten(ArrayList<Visite> visiten) {
		this.visiten = visiten;
	}

	/**
	 * Fuegt dem Patienten eine neue Visite hinzu.
	 * 
	 * @param visite (Visite) Neue Visite in der Visitenliste
	 */
	public void addVisite(Visite visite) {
		this.visiten.add(visite);
		visite.setPatient(this);
	}

	/**
	 * Loescht eine Visite aus der Liste der Visiten des Patienten.
	 * 
	 * @param visite (Visite) zuloeschene Visite
	 */
	public void removeVisite(Visite visite) {
		this.visiten.remove(visite);
	}

	public String toString() {
		return nachname + ", " + vorname + " (" + adresse + ")";
	}
}
