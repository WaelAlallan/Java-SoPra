package de.wwu.sopra.model;
/**
 * Probenaufbewahrung: Raum-Kuehlschrank-Segment-Gestell-Schublade-Rack-Probe
 * 
 * Entitaetsklasse fuer Proben. Proben dienen der Probenaufbewahrung und
 * beinhalten spaeter menschliche Gewebe-, Blut- oder andere Proben von
 * Patienten. Sie sind Teil eines Racks und sieht verknuepft mit Visiten und
 * Probenkategorien sowie Probentypen.
 */

public class Probe {
	private final int barcode;
	private Probenstatus status;

	private Probe mutterProbe;
	private Rack rack;
	private final ProbenBehaelterTyp typ;
	private final ProbenKategorie kategorie;
	private final Visite visite;

	/**
	 * Erstellt eine Probe anhand angegebener Eigenschaften.
	 * @param barcode   Jede Probe hat einen eindeutigen Barcode. Dieser dient zur
	 *                  Identifizierung und Einlagerung.
	 * @param status    Der Status einer Probe gibt an, ob diese eingelagert,
	 *                  kurzfristig oder permanent entnommen ist.
	 * @param typ       Der Probenbehaeltertyp gibt an, welcher Behaeltertyp fuer
	 *                  die Probe vorgesehen ist.
	 * @param kategorie Der Probenkategorie gibt an, von welchem Typ bzw.
	 *                  Bestandteil die Probe ist.
	 * @param visite    Die Visite in der die Probe verwendet wird.
	 */
	public Probe(int barcode, Probenstatus status, ProbenBehaelterTyp typ, ProbenKategorie kategorie, Visite visite) {
		this.barcode = barcode;
		this.status = status;

		this.typ = typ;
		this.kategorie = kategorie;
		this.visite = visite;
		visite.addProbe(this);
	}

	/**
	 * Gibt den eindeutigen Barcode der Probe zurueck.
	 * 
	 * @return (int) Barcode im in Format
	 */
	public int getBarcode() {
		return barcode;
	}

	/**
	 * Gibt den aktuellen Lagerstatus der Probe zurueck.
	 * 
	 * @return (boolean) gelagert oder nicht eingelagert
	 */
	public Probenstatus getStatus() {
		return status;
	}

	/**
	 * Setzt den Lagerstatus der Probe.
	 * 
	 * @param status (boolean) gelagert oder nicht eingelagert
	 */
	public void setStatus(Probenstatus status) {
		this.status = status;
	}

	/**
	 * Wird eine neue Probe geteilt entstehen eine neue Kindprobe und die
	 * Mutterprobe. Es muss dann die Mutterprobe der Kindprobe zugeordnet werden.
	 * Die getMutterProbe gibt die Mutterprobe zurueck.
	 * 
	 * @return (Probe) Gibt die Mutterprobe zurueck
	 */
	public Probe getMutterProbe() {
		return mutterProbe;
	}

	/**
	 * Setzt die Mutterprobe zu der Probe.
	 * 
	 * @param mutterProbe (Probe) Mutterprobe
	 */
	public void setMutterProbe(Probe mutterProbe) {
		this.mutterProbe = mutterProbe;
	}

	/**
	 * Gibt das jeweilige Rack in der die Probe gelagert ist zurueck.
	 * 
	 * @return (Rack) derzeitiges Probenrack
	 */
	public Rack getRack() {
		return rack;
	}

	/**
	 * Setzt der Probe ihr Rack hinzu. In diesem Rack befindet sich die Probe.
	 * 
	 * @param rack (Rack)
	 */
	public void setRack(Rack rack) {
		this.rack = rack;
	}

	/**
	 * Gibt den Behaeltertyp der Probe zurueck.
	 * 
	 * @return (ProbenBehaelterTyp) Behaeltertyp der Probe
	 */
	public ProbenBehaelterTyp getTyp() {
		return typ;
	}

	/**
	 * Gibt die Materialkategorie der Probe zurueck. (z.B.: Blut)
	 * 
	 * @return (ProbenKategorie) Kategorie der Probe.
	 */
	public ProbenKategorie getKategorie() {
		return kategorie;
	}

	/**
	 * Gibt die der Probe zugeordnete Visite zurueck.
	 * 
	 * @return (Visite) Proben zugehoerige Visite
	 */
	public Visite getVisite() {
		return visite;
	}
	
	/**
	 * Gibt einen String zur textuellen Beschreibung der Probe zurueck
	 * @return String mit menschenlesbarer Beschreibung
	 */
	public String toString() {
		return "Probenkategorie: "+kategorie.toString()+", Behaeltertyp: "+typ.toString()+", Barcode: "+barcode+", Status: "+status;
	}

}
