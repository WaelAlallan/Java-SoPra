package de.wwu.sopra.model;

/**
 * Entitaetsklasse fuer die Probenkategorie. Die Probenkategorie gibt an, von
 * welchen Typ bzw. aus welchem Material (z.B.: Blut, Haut, usw) die Probe
 * besteht
 *
 */
public class ProbenKategorie {
	private String name;

	/**
	 * Bei der Erstellung einer Probenkategorie muss der Name des Probenmaterials
	 * angegeben werden.
	 * 
	 * @param name Name der Probenkategorie
	 */
	public ProbenKategorie(String name) {
		this.setName(name);
	}

	/**
	 * Gibt den Namen der Probenkategorie zurueck.
	 * 
	 * @return (String) Name der Kategorie (z.B.: Blut)
	 */
	public String getName() {
		return name;
	}

	/**
	 * Setzt den Namen der Probenkategorie.
	 * 
	 * @param name (String) Name der Probenkategorie
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * Ueberschreibt die toString Methode und gibt den Namen der Probenkategorie
	 * zurueck.
	 */
	public String toString() {
		return name;
	}
}
