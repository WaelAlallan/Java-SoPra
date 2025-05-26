package de.wwu.sopra.model;

/**
 * Probenaufbewahrung: Raum-Kuehlschrank-Segment-Gestell-Schublade-Rack-Probe
 * 
 * Entitaetsklasse fuer das Rack. Racks dienen der Probenaufbewahrung. In ihnen
 * sind direkt die Proben eingelagert. Innerhalb einer Schublade befinden sich
 * mehrere Racks.
 */
public class Rack {
	private String name;
	private Schublade schublade;
	private ProbenBehaelterTyp typ;
	private Probe[][] proben;

	/**
	 * Bei der Erstellung eines Racks muss der Name des Racks, die Schublade in der
	 * sich das Rack befinden soll, der Probenbehaeltertyp der Proben im Rack, sowie
	 * die Anzahl der Zeilen und Spalten angegeben werden.
	 * 
	 * @param name          (String) Name des Racks
	 * @param schublade     (Schublade) Schublade in der ich das Rack befindet
	 * @param typ           (ProbenBehaelterTyp) ProbenbehaelterTyp der Proben im
	 *                      Rack
	 * @param anzahlZeilen  (int) Anzahl der Zeilen. Gibt die Zeilen im Rack an
	 * @param anzahlSpalten (int) Anzahl der Spalten. Gibt die Spalten im Rack an
	 */
	public Rack(String name, Schublade schublade, ProbenBehaelterTyp typ, int anzahlZeilen, int anzahlSpalten) {
		if (anzahlSpalten <= 0 || anzahlZeilen <= 0)
			throw new IllegalArgumentException("anzahlZeilen und anzahlSpalten muss groesser als 0 sein!");

		this.name = name;
		this.schublade = schublade;
		this.schublade.addRack(this);
		this.typ = typ;
		proben = new Probe[anzahlZeilen][anzahlSpalten];
	}

	/**
	 * Berechnet die Anzahl der Zeilen anhand der Rackabmessungen und der
	 * Probendurchmesser.
	 * 
	 * @return (int) Anzahl der Zeilen
	 */
	public int anzahlZeilen() {
		return proben.length;
	}

	/**
	 * Berechnet die Anzahl der Spalten anhand der Rackabmessungen und der
	 * Probendurchmesser.
	 * 
	 * @return (int) Anzahl der Rackspalten
	 */
	public int anzahlSpalten() {
		return proben[0].length;
	}

	/**
	 * Setzt den Namen des Racks.
	 * 
	 * @param name (String) Name des Racks
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * Gibt den Namen des Racks zurueck.
	 * 
	 * @return (String) Name des Racks
	 */
	public String getName() {
		return name;
	}

	/**
	 * Setzt die Schublade des Racks.
	 * 
	 * @param schublade (Schublade) Schublade in dem sich das Rack befinden soll
	 */
	public void setSchublade(Schublade schublade) {
		this.schublade = schublade;
	}

	/**
	 * Gibt die Schublade des Rack zurueck, in dem sich das Rack befindet.
	 * 
	 * @return (Schublade) Schublade indem sich das Rack befindet
	 */
	public Schublade getSchublade() {
		return schublade;
	}

	/**
	 * Setzt den Probenbehaeltertyp der fuer das Rack vorgesehen ist.
	 * 
	 * @param typ (ProbenBehaelterTyp) Probenbehaeltertyp fuer die Proben innerhalb
	 *            des Racks
	 */
	public void setProbenBehaelterTyp(ProbenBehaelterTyp typ) {
		this.typ = typ;
	}

	/**
	 * Gibt den Probenbehaltertyp des Racks zurueck.
	 * 
	 * @return (ProbenBehaelterTyp) Gibt den ProbenBehaelterTyp des Racks zurueck.
	 */
	public ProbenBehaelterTyp getProbenBehaelterTyp() {
		return typ;
	}

	/**
	 * Gibt alle Proben des Racks mittels eines Arrays zurueck.
	 * 
	 * @return (Array) Alle Proben innerhalb des Racks
	 */
	public Probe[][] getProben() {
		return proben;
	}

	/**
	 * Gibt die Probe an der uebergebenen Position zurueck.
	 * 
	 * @param zeile  (int) Die Zeile des Racks in der sich die Probe befindet.
	 * @param spalte (int) Die Spalte des Racks in der sich die Probe befindet.
	 * @return (Probe) Probe an der gegebenen Position im Rack
	 */
	public Probe getProbe(int zeile, int spalte) {
		return proben[zeile][spalte];
	}

	/**
	 * Fuegt eine neue Probe an die uebergebene Stelle im Rack ein.
	 * 
	 * @param probe  (Probe) Neueinzufuegene Probe im Rack
	 * @param zeile  (int) Zeile im Rack, in der die neue Probe eingelagert werden
	 *               soll
	 * @param spalte (int) Spalte im Rack, in der die neue Probe eingelagert werden
	 *               soll
	 * @return Gibt zurueck, ob der Platz im Rack belegt ist oder nicht
	 */
	public boolean addProbe(Probe probe, int zeile, int spalte) {
		if (proben[zeile][spalte] == null) {
			proben[zeile][spalte] = probe;
			return true;
		} else {
			return false;
		}
	}

	/**
	 * Erstellt ein digitalen Probenzwilling mit der innerhalb der
	 * Verwaltungssoftware gearbeitet werden kann, falls die Probe lediglich
	 * kurzfristig ausgelagert wird. Die Probe kann ausgelesen werden, ohne das
	 * Rackplatz freigeschrieben wird.
	 * 
	 * @param zeile  (int) Zeile im Rack, in der die Probe eingelagert ist
	 * @param spalte (int) Spalte im Rack, in der die Probe eingelagert ist
	 * @return (Probe) Probenzwilling zur Bearbeitung
	 */
	public Probe removeProbe(int zeile, int spalte) {
		Probe ret = proben[zeile][spalte];
		proben[zeile][spalte] = null;
		return ret;
	}

	/**
	 * Entfernt die uebergebene Probe und entfernt sie aus dem Rack
	 * 
	 * @param probe (Probe) Die zu entfernene Probe
	 * @return (boolean) true, wenn Probe gefunden und entfernt. False wenn Probe
	 *         nicht im Rack gefunden wurde.
	 */
	public boolean removeProbe(Probe probe) {

		for(int row=0;row<proben.length; row++) {
			for(int col=0; col<proben[0].length; col++) {
				if(proben[row][col] == probe)

					proben[row][col].setRack(null);
				proben[row][col] = null;
				return true;
			}
		}
		return false;
	}

}
