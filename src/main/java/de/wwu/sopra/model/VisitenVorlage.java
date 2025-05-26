package de.wwu.sopra.model;

import java.util.ArrayList;

/**
 * Entitatesklasse fuer die Visitenvorlage. Visitenvorlagen definieren die
 * Rahmenbedingungen zur Visite. Neben ihrem Namen werden die abzunehmenen med.
 * Bezeichungen, ihre Visiten und die Zeit zwischen den selbigen, uebergeben.
 * 
 * @author Lukas
 *
 */
public class VisitenVorlage {
	private String name;
	private ArrayList<String> medizinischeDatenBezeichnung;
	private int zeitZurLetztenVisiteInTagen;
	private ArrayList<Visite> visiten;

	private ArrayList<ProbenVorlage> probenVorlage;

	/**
	 * Bei der Erzeugung einer Visitenvorlage muss ihr Name, eine Liste von
	 * abzunehmenen Daten sowie die Zeit zwischen den einzelnen Visiten uebergeben
	 * werden.
	 * 
	 * @param name                         (String) Name der VisitenVorlage
	 * @param medizinischeDatenBezeichnung (ArrayList) med. Daten, die abgenommen
	 *                                     werden sollen
	 * @param zeitZurLetztenVisiteInTagen  (int) Tage zwischen den Visiten
	 */
	public VisitenVorlage(String name, ArrayList<String> medizinischeDatenBezeichnung,
			int zeitZurLetztenVisiteInTagen) {
		this.setName(name);
		this.medizinischeDatenBezeichnung = medizinischeDatenBezeichnung;
		this.setZeitZurLetztenVisiteInTagen(zeitZurLetztenVisiteInTagen);
		this.setProbenVorlage(new ArrayList<ProbenVorlage>());
		this.visiten = new ArrayList<Visite>();
	}

	/**
	 * Gibt den Namen der Visitenvorlage zurueck.
	 * 
	 * @return (String) Name der Visitenvorlage
	 */
	public String getName() {
		return name;
	}

	/**
	 * Setzt den Namen der Visitenvorlage
	 * 
	 * @param name (String) Name der Visitenvorlage
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * Gibt die Liste der abzunehmenden med. Daten zurueck.
	 * 
	 * @return (Arraylist) med. Daten die im Rahmen der Visite abgenommen werden
	 *         sollen.
	 */
	public ArrayList<String> getMedizinischeDatenBezeichnung() {
		return medizinischeDatenBezeichnung;
	}

	/**
	 * Setzt die med.Daten der Visitenvorlage.
	 * 
	 * @param medDaten (Arraylist) med.Daten zur Visitenvorlage
	 */
	public void setMedizinischeDatenBezeichnung(ArrayList<String> medDaten) {
		this.medizinischeDatenBezeichnung = medDaten;
	}

	/**
	 * Gitb die Tage zwischen den einzelnen Visiten an
	 * 
	 * @return (int) Tage bis zur naechsten Visite
	 */
	public int getZeitZurLetztenVisiteInTagen() {
		return zeitZurLetztenVisiteInTagen;
	}

	/**
	 * Setzt die Zeit bis zur naechsten Visite
	 * 
	 * @param zeitZurLetztenVisiteInTagen (int) Tage bis zur naechsten Visite
	 */
	public void setZeitZurLetztenVisiteInTagen(int zeitZurLetztenVisiteInTagen) {
		this.zeitZurLetztenVisiteInTagen = zeitZurLetztenVisiteInTagen;
	}

	/**
	 * Gibt die Liste aller Probenvorlagen einer Visitenvorlage zurueck.
	 * 
	 * @return (Arraylist) List der Probenvorlagen einer Visitenvorlage.
	 */
	public ArrayList<ProbenVorlage> getProbenVorlage() {
		return probenVorlage;
	}

	/**
	 * Setzt die Liste aller Probenvorlagen einer Visitenvorlage.
	 * 
	 * @param probenVorlage (Arraylist) List der Probenvorlagen einer
	 *                      Visitenvorlage.
	 */
	public void setProbenVorlage(ArrayList<ProbenVorlage> probenVorlage) {
		this.probenVorlage = probenVorlage;
	}

	/**
	 * Fuegt eine Probenvorlage der Liste Probenvorlagen einer Visitenvorlage hinzu
	 * 
	 * @param probenVorlage (ProbenVorlage) Neue Probenvorlage
	 */
	public void addProbenVorlage(ProbenVorlage probenVorlage) {
		this.probenVorlage.add(probenVorlage);
	}

	/**
	 * Entfertn die Probenvorlage aus der Liste der Probenvorlagen der
	 * Visitenvorlage.
	 * 
	 * @param probenVorlage (ProbenVorlage) Die zu entfernende Probenlage
	 */
	public void removeProbenVorlage(ProbenVorlage probenVorlage) {
		this.probenVorlage.remove(probenVorlage);
	}

	/**
	 * Gibt die Liste aller Visiten der Visitenvorlage zurueck.
	 * 
	 * @return (Arraylist) List der Visiten innerhalb der Visitenvorlage
	 */
	public ArrayList<Visite> getVisiten() {
		return this.visiten;
	}

	/**
	 * Fuegt der Liste der Visiten die neue Visite hinzu
	 * 
	 * @param visite (Visite) Neue Visite
	 */
	public void addVisite(Visite visite) {
		this.visiten.add(visite);
	}

	/**
	 * Entfertn die Visite aus der Liste der Visiten innerhalb der Probenvorlage
	 * 
	 * @param visite (Visite) Zuentfernende Visite
	 */
	public void removeVisite(Visite visite) {
		this.visiten.remove(visite);
	}

	/**
	 * Ueberschriebende toString Methode, die den konkatenierten Strin aus
	 * Visitenname, Zeit bis zur naechsten Visiten und der letzten Visite
	 * zurueckgibt.
	 */
	public String toString() {

		return "Visitename: " + name + "; Durchfuehrung nach Studienbeginn: " + zeitZurLetztenVisiteInTagen + " Tage";

	}

}
