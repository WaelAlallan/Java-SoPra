package de.wwu.sopra.model;

/**
 * Entitaetsklasse fuer die Probenvorlage. Die Probenvorlage verknuepft die
 * Probenkategorie und den Probenbehaeltertyp in einem Container, hier
 * repraesentiert als Probenvorlage.
 *
 */
public class ProbenVorlage {
	private ProbenKategorie kategorie;
	private ProbenBehaelterTyp typ;

	/**
	 * Bei der Erstellung einer Probenvorlage muss die Probenkategorie und der
	 * Probenbehaeltertyp uebergeben werden.
	 * 
	 * @param kategorie (ProbenKategorie) Die Probenkategorie fuer die Probenvorlage
	 * @param typ       (ProbenBehaelterTyp) Der Probenbehaeltertyp fuer die
	 *                  Probenvorlage
	 */
	public ProbenVorlage(ProbenKategorie kategorie, ProbenBehaelterTyp typ) {
		this.setKategorie(kategorie);
		this.setProbenBehaelterTyp(typ);
	}

	/**
	 * Gibt die Probenkategorie der Probenvorlage zurueck.
	 * 
	 * @return (ProbenKategorie)
	 */
	public ProbenKategorie getKategorie() {
		return kategorie;
	}

	/**
	 * Setzt die Probenkategorie der Probenvorlage.
	 * 
	 * @param kategorie (Probenkategorie) Probenkategorie der Probenvorlage
	 */
	public void setKategorie(ProbenKategorie kategorie) {
		this.kategorie = kategorie;
	}

	/**
	 * Gibt den Probenbehaeltertyp der Probenvorlage zurueck.
	 * 
	 * @return (ProbenBehaelterTyp) Probenbehaelteryp der Probenvorlage
	 */
	public ProbenBehaelterTyp getProbenBehaelterTyp() {
		return typ;
	}

	/**
	 * Setzt den Probenbehaeltertyp der Probenvorlage.
	 * 
	 * @param typ (ProbenBehaelerTyp) Probenbehaeltertyp der Probenvorlage
	 */
	public void setProbenBehaelterTyp(ProbenBehaelterTyp typ) {
		this.typ = typ;
	}

	/**
	 * Ueberschreibt die toString Methode und gibt einen konkatenierten String aus
	 * Probenkategorie und Behaeltertyp der Probenvorlage zurueck.
	 */
	@Override
	public String toString() {
		return "Kategorie: " + kategorie + " || Behaeltertyp: " + typ;
	}
}
