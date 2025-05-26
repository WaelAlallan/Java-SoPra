package de.wwu.sopra.model;

import java.util.Date;
import java.util.ArrayList;
import java.util.HashMap;

import de.wwu.sopra.steuerung.helfer.DatumHelfer;
import javafx.util.Pair;

/**
 * Entitaetsklasse fuer die Visite. Die Visite wird in der Studie definiert. In
 * ihr stehen der Name der Visite, die VisitenID, ihre Patienten und die
 * Mitarbeiter innerhalb des medizinschen Instituts, die die Visiten ausfuehren.
 *
 */
public class Visite {

	private Date datum;
	private HashMap<String, String> medizinischeDaten;
	private int visitenId;
	private boolean isAbgeschlossen;

	private ArrayList<Probe> proben;
	private Benutzer benutzer;
	private Patient patient;
	private Studie studie;
	private VisitenVorlage visitenVorlage;

	/**
	 * Bei der Erstellung einer Visite wird das Datum der Ausfuehrung der Visite,
	 * der Mitarbeiter (Benutzer), die zubehandelnen Patienten, die Studie zu der
	 * Visite und die Visitenvorlage uebergeben.
	 * 
	 * @param date           (Date) Datum der ersten Visite
	 * @param visitenId      (int) Id der Visite
	 * @param benutzer       (Benutzer) Der Visite zugehoerige Study Nurse
	 * @param patient        (Patient) Der Visite zugehoerige Patient
	 * @param studie         (Studie) Studie zur Visite
	 * @param visitenVorlage (VisitenVorlage) Visitenvorlage zur Visite
	 */

	public Visite(Date date, int visitenId, Benutzer benutzer, Patient patient, Studie studie,
			VisitenVorlage visitenVorlage) {

		medizinischeDaten = new HashMap<String, String>();
		for (int i = 0; i < visitenVorlage.getMedizinischeDatenBezeichnung().size(); i++) {
			medizinischeDaten.put(visitenVorlage.getMedizinischeDatenBezeichnung().get(i), "");

		}

		this.datum = date;
		this.visitenId = visitenId;

		this.proben = new ArrayList<Probe>();
		this.benutzer = benutzer;
		this.patient = patient;
		this.studie = studie;
		this.visitenVorlage = visitenVorlage;

		if (benutzer != null)
			benutzer.addVisite(this);
		if (studie != null)
			studie.addVisite(this);
		if (patient != null)
			patient.addVisite(this);
	}

	/**
	 * Gibt das Datum der naechsten Untersuchung zurueck.
	 * 
	 * @return (Datum) Datum zur Visite
	 */
	public Date getDatum() {
		return datum;
	}

	/**
	 * Setzt das Datum fuer die naechste Untersuchung
	 * 
	 * @param datum (Datum) Datum zur Visite
	 */
	public void setDatum(Date datum) {
		this.datum = datum;
	}

	/**
	 * Gibt die medizinischen Daten in Form einer Liste von abzunehmenden Werten und
	 * ihren tatsaechlichen Werten, die bei Initalisierung null sind und spaeter
	 * eingetragen werden koennen zurueck.
	 * 
	 * @return (Arraylist) (Abzunehmender Wert / Tatsaechlicher Wert)
	 */
	public ArrayList<Pair<String, String>> getMedizinscheDaten() {
		return convertHashmapToPair(medizinischeDaten);
	}

	private ArrayList<Pair<String, String>> convertHashmapToPair(HashMap<String, String> medizinischeDaten) {
		ArrayList<Pair<String, String>> tmp = new ArrayList<Pair<String, String>>();
		for (String i : medizinischeDaten.keySet()) {
			tmp.add(new Pair<String, String>(i, medizinischeDaten.get(i)));
		}
		return tmp;
	}

	/**
	 * Setzt die medizinischen Daten einer Liste von abzunehmenden Werten und ihren
	 * tatsaechlichen Werten, die bei Initalisierung null sind und spaeter
	 * eingetragen werden.
	 * 
	 * @param medizinischeDaten (ArrayList) ArrayList mit Paaren aus abzunehmenden
	 *                          med. Daten und den tatsaechlichen Werten
	 */
	public void setMedizinischeDaten(ArrayList<Pair<String, String>> medizinischeDaten) {
		this.medizinischeDaten = convertPairToHashmap(medizinischeDaten);
		isAbgeschlossen = true;
	}

	private HashMap<String, String> convertPairToHashmap(ArrayList<Pair<String, String>> medizinischeDaten) {
		HashMap<String, String> tmp = new HashMap<String, String>();
		for (Pair<String, String> i : medizinischeDaten) {
			tmp.put(i.getKey(), i.getValue());
		}
		return tmp;
	}

	/**
	 * Gibt die Id der Visite zurueck.
	 * 
	 * @return (int) VisitenID
	 */
	public int getVisitenId() {
		return visitenId;
	}

	/**
	 * Setzt die VisitenID
	 * 
	 * @param visitenId (int) VisitenID
	 */
	public void setVisitenId(int visitenId) {
		this.visitenId = visitenId;
	}

	/**
	 * Gibt den Benutzer der Visite zurueck (ausfuehrender Mitarbeiter)
	 * 
	 * @return (Benutzer) Mitarbeiter der die Visite ausfuehrt
	 */
	public Benutzer getBenutzer() {
		return benutzer;
	}

	/**
	 * Setzt den Benutzer der Visite (ausfuehrender Mitarbeiter)
	 * 
	 * @param benutzer (Benutzer) Mitarbeiter der die Visite ausfuehrt
	 */
	public void setBenutzer(Benutzer benutzer) {
		this.benutzer = benutzer;
	}

	/**
	 * Gibt an ob die Visite schon ausgefuehrt wurde oder nicht
	 * 
	 * @return (boolean) ausgefuehrt, nicht ausgefuehrt
	 */
	public boolean isAbgeschlossen() {
		return isAbgeschlossen;
	}

	/**
	 * Setzt den Status der Visite.
	 * 
	 * @param isAbgeschlossen (boolean) ausgefuehrt, nicht ausgefuehrt
	 */
	public void setAbgeschlossen(boolean isAbgeschlossen) {
		this.isAbgeschlossen = isAbgeschlossen;
	}

	/**
	 * Gibt die Liste aller Proben einer Visite zurueck.
	 * 
	 * @return (Arraylist) Liste aller Proben
	 */
	public ArrayList<Probe> getProben() {
		return proben;
	}

	/**
	 * Setzt die Proben einer Visite mit der uebergebeben Liste der Proben
	 * 
	 * @param proben (Arraylist) Uebergebene Probenliste
	 */
	public void setProben(ArrayList<Probe> proben) {
		this.proben = proben;
	}

	/**
	 * Fuegt eine Probe in die Liste der Proben einer Visit hinzu.
	 * 
	 * @param probe (Probe) Neue Probe
	 */
	public void addProbe(Probe probe) {
		this.proben.add(probe);
	}

	/**
	 * Entfernt eine Probe aus der Liste aller Proben einer Visite.
	 * 
	 * @param probe (Probe) Entfernte Probe
	 */
	public void removeProbe(Probe probe) {
		this.proben.remove(probe);
	}

	/**
	 * Gibt den Patienten zurueck, dem die Visite zugeordnet ist.
	 * 
	 * @return (Patient) Patient der Visite
	 */
	public Patient getPatient() {
		return patient;
	}

	/**
	 * Setzt der Visite ihren Patient
	 * 
	 * @param patient (Patient) Patient der Visite
	 */
	public void setPatient(Patient patient) {
		this.patient = patient;
	}

	/**
	 * Gibt die Studie der Visite zurueck.
	 * 
	 * @return (Studie) Studie zu der Visite
	 */
	public Studie getStudie() {
		return studie;
	}

	/**
	 * Setzt die Studie zu der Visite
	 * 
	 * @param studie (Studie) Zugeordnete Studie der Visite
	 */
	public void setStudie(Studie studie) {
		this.studie = studie;
	}

	/**
	 * Gibt die Visitenvorlage zur Visite zurueck.
	 * 
	 * @return (VisitenVorlage) Gibt die Visitenvorlage zur Visite zurueck.
	 */
	public VisitenVorlage getVisitenVorlage() {
		return visitenVorlage;
	}

	/**
	 * Setzt die Visitenvorlage zur Visite
	 * 
	 * @param visitenVorlage (VisitenVorlage) Visitenvorlage zur Visite
	 */
	public void setVisitenVorlage(VisitenVorlage visitenVorlage) {
		this.visitenVorlage = visitenVorlage;
	}

	public String toString() {

		return visitenVorlage.toString() + "; Patient: " + patient.toString() + "; ID: " + visitenId
				+ "; Beginn der Studie: " + DatumHelfer.gibDatumString(datum);

	}

}
