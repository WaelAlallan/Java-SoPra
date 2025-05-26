package de.wwu.sopra.model;

/**
 * Entitaetsklasse fuer Probenbehaeltertyp. Die Probenbehaeltertypen sind die
 * verschiedenen Typen eines Probenbehaelters. Ein Probenbehaeltertyp hat einen
 * Namen, einen Deckel und physische Abmessungen.
 *
 */
public class ProbenBehaelterTyp {
	private String name;
	private final double volumen;
	private final double hoehe;
	private final double durchmesser;
	private String deckelTyp; // TODO ggf eigene Klasse benutzen

	/**
	 * Bei der Erzeugung des Probenbehaeltertyps wird der Name des Behaeltertyps,
	 * seine Abmessungen in cm und sein Deckeltyp angegeben.
	 * 
	 * @param name        (String) Name des Probenbehaeltertyps
	 * @param volumen     (double) Volumen des Probenbehaeltertyps
	 * @param hoehe       (double) Hoehe des Probenbehaeltertyps
	 * @param durchmesser (double) Durchmesser des Probenbehaeltertyps
	 * @param deckelTyp   (String) Name des Deckeltyps
	 */
	public ProbenBehaelterTyp(String name, double volumen, double hoehe, double durchmesser, String deckelTyp) {
		this.setName(name);
		this.volumen = volumen;
		this.hoehe = hoehe;
		this.durchmesser = durchmesser;
		this.setDeckelTyp(deckelTyp);
	}

	/**
	 * Setzt den Namen des ProbenBehaeltertyp
	 * 
	 * @param name (String) Name des ProbenBehaeltertyps
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * Gibt den Namen des Probenbehaeltertyps zurueck.
	 * 
	 * @return (String) Name des Probenbehaeltertyps
	 */
	public String getName() {
		return name;
	}

	/**
	 * Gibt das Volumen des Probenbehaeltertyps zurueck.
	 * 
	 * @return (double) Volumen des Probenbehaeltertyps
	 */
	public double getVolumen() {
		return volumen;
	}

	/**
	 * Gibt die Hoehe des Probenbehaeltertyps zurueck.
	 * 
	 * @return (double) Hoehe des Probenbehaeltertyps
	 */
	public double getHoehe() {
		return hoehe;
	}

	/**
	 * Gibt den Durchmesser des Probenbehaeltertyps zurueck.
	 * 
	 * @return (double) Durchmesser des Probenbehaeltertyps
	 */
	public double getDurchmesser() {
		return durchmesser;
	}

	/**
	 * Gibt den Deckeltyp des Probenbehaeltertyps zurueck.
	 * 
	 * @return (String) Name des Probenbehaeltertyps Deckeltyps
	 */
	public String getDeckelTyp() {
		return deckelTyp;
	}

	/**
	 * Setzt den Deckeltyp des Probenbehaeltertyps als String
	 * 
	 * @param deckelTyp (String) Deckeltyp des Probenbehaeltertyps
	 */
	public void setDeckelTyp(String deckelTyp) {
		this.deckelTyp = deckelTyp;
	}

	/**
	 * Ueberschreibt die toString Method und gibt einen String zurueck, der den
	 * Probenbehaeltertyps Namen, sein Volumen, die Hoehe und den Durchmesser
	 * konkateniert.
	 */
	public String toString() {
		return getName() + ", " + getVolumen() + "cm^3, " + getHoehe() + "cm x " + getDurchmesser() + "cm, "
				+ getDeckelTyp();
	}
}
