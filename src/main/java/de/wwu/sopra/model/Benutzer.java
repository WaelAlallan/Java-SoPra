package de.wwu.sopra.model;

import java.util.ArrayList;

/**
 * Entitaetsklasse fuer Benutzer. Benutzer sind die Anwender der Software in der
 * Biobank.
 */
public class Benutzer {

	private String benutzername;
	private String nachname;
	private String vorname;
	private PasswortHash passwortHash;
	private boolean isBiobankLeiter;
	private boolean isStudyNurse;
	private boolean isMTA;
	private boolean isPersonalLeiter;
	private ArrayList<Visite> visiten;

	/**
	 * Bei der Erstellung des Benutzers muss der Nachname, Vorname und der Hash des
	 * Benutzerpassworts uebergeben werden.
	 * 
	 * @param nachname     (String) Nachname des Benutzers
	 * @param vorname      (String) Vorname des Benutzers
	 * @param passwortHash (PasswortHas) verschluesseltes Passwort des Benutzers
	 */
	public Benutzer(String nachname, String vorname, PasswortHash passwortHash) {
//		id = (int)Math.round(Math.random()*1000000); //TODO brauchen wir die?
		this.setNachname(nachname);
		this.setVorname(vorname);
		this.setPasswordHash(passwortHash);
		this.setVisiten(new ArrayList<Visite>());
	}

	/**
	 * Ueberschriebene toString Methode, die den konkatenierten String aus Nachname,
	 * Vorname und Benutzername zurueckgibt.
	 */
	@Override
	public String toString() {
		return "" + getNachname() + ", " + getVorname() + " (" + getBenutzername() + ")";
	}

	/**
	 * Gibt den Benutzername des Benutzers zurueck.
	 * 
	 * @return (String) Benutzername des Benutzers
	 */
	public String getBenutzername() {
		return benutzername;
	}

	/**
	 * Setzt den Benutzername des Benutzers.
	 * 
	 * @param benutzername (String) Benutzername des Benutzers
	 */
	public void setBenutzername(String benutzername) {
		this.benutzername = benutzername;
	}

	/**
	 * Gibt den Nachname des Benutzers zurueck.
	 * 
	 * @return (String) Nachname des Benutzers
	 */
	public String getNachname() {
		return nachname;
	}

	/**
	 * Setzt den Nachname des Benutzers.
	 * 
	 * @param nachname (String) Benutzername des Benutzers
	 */
	public void setNachname(String nachname) {
		this.nachname = nachname;
	}

	/**
	 * Gibt den Vorname des Benutzers zurueck.
	 * 
	 * @return (String) Vorname des Benutzers
	 */
	public String getVorname() {
		return vorname;
	}

	/**
	 * Setzt den VOrname des Benutzers.
	 * 
	 * @param vorname (String) Vorname des Benutzers
	 */
	public void setVorname(String vorname) {
		this.vorname = vorname;
	}

	/**
	 * Setzt das PasswortHash des Benutzers.
	 * 
	 * @return (PasswortHash) PasswortHash des Benutzers
	 */
	public PasswortHash getPasswordHash() {
		return passwortHash;
	}

	/**
	 * Gibt das PasswortHash des Benutzers zurueck.
	 * 
	 * @param passwordHash (PasswortHash) PasswortHash des Benutzers
	 */
	public void setPasswordHash(PasswortHash passwordHash) {
		this.passwortHash = passwordHash;
	}

	/**
	 * Gibt zurueck, ob der Benutzer Biobankleiter ist.
	 * 
	 * @return (boolean) true oder false
	 */
	public boolean isBiobankLeiter() {
		return isBiobankLeiter;
	}

	/**
	 * Es kann gesetzt werden, ob der Benutzer Biobankleiter ist.
	 * 
	 * @param isBiobankLeiter (boolean) true oder false
	 */
	public void setBiobankLeiter(boolean isBiobankLeiter) {
		this.isBiobankLeiter = isBiobankLeiter;
	}

	/**
	 * Gibt zurueck, ob der Benutzer StudyNurse ist.
	 * 
	 * @return (boolean) true oder false
	 */
	public boolean isStudyNurse() {
		return isStudyNurse;
	}

	/**
	 * Es kann gesetzt werden, ob der Benutzer StudyNurse ist.
	 * 
	 * @param isStudyNurse (boolean) true oder false
	 */
	public void setStudyNurse(boolean isStudyNurse) {
		this.isStudyNurse = isStudyNurse;
	}

	/**
	 * Gibt zurueck, ob der Benutzer Biobankleiter ist.
	 * 
	 * @return (boolean) true oder false
	 */
	public boolean isMTA() {
		return isMTA;
	}

	/**
	 * Es kann gesetzt werden, ob der Benutzer MTA ist.
	 * 
	 * @param isMTA (boolean) true oder false
	 */
	public void setMTA(boolean isMTA) {
		this.isMTA = isMTA;
	}

	/**
	 * Gibt zurueck, ob der Benutzer Personalleiter ist.
	 * 
	 * @return (boolean) true oder false
	 */
	public boolean isPersonalLeiter() {
		return isPersonalLeiter;
	}

	/**
	 * Es kann gesetzt werden, ob der Benutzer Personalleiter ist.
	 * 
	 * @param isPersonalLeiter (boolean) true oder false
	 */
	public void setPersonalLeiter(boolean isPersonalLeiter) {
		this.isPersonalLeiter = isPersonalLeiter;
	}

	/**
	 * Gibt alle Visiten zurueck, die mit einem Benutzer verknuepft sind.
	 * 
	 * @return (ArrayList) Die Liste aller Visiten eines Benutzers
	 */
	public ArrayList<Visite> getVisiten() {
		return visiten;
	}

	/**
	 * Setzt die Liste von Visiten eines Benutzers gleich der uebergebenen Liste von
	 * Visiten.
	 * 
	 * @param visiten (ArrayList) Liste von Visiten, die dem Benutzer zugeordnet
	 *                werden.
	 */
	public void setVisiten(ArrayList<Visite> visiten) {
		this.visiten = visiten;
	}

	/**
	 * Fuegt dem Benutzer eine neue Visite hinzu und speichert sie in der Liste der
	 * Visiten eines Benutzers.
	 * 
	 * @param visite (Visite) Neue Visite, die zu dem Benutzer hinzugefuegt wird.
	 */
	public void addVisite(Visite visite) {
		this.visiten.add(visite);
		visite.setBenutzer(this);
	}

	/**
	 * Entfernt die Visite aus der Liste der Visiten eines Benutzers.
	 * 
	 * @param visite (Visite) Zu entfernene Liste.
	 */
	public void removeVisite(Visite visite) {
		this.visiten.remove(visite);
	}
}
