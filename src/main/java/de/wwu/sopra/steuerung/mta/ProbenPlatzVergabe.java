package de.wwu.sopra.steuerung.mta;

import java.util.ArrayList;
import java.util.stream.Collectors;

import de.wwu.sopra.datenhaltung.DataFilter;
import de.wwu.sopra.model.Gestell;
import de.wwu.sopra.model.Kuehlschrank;
import de.wwu.sopra.model.Patient;
import de.wwu.sopra.model.Probe;
import de.wwu.sopra.model.Probenstatus;
import de.wwu.sopra.model.Rack;
import de.wwu.sopra.model.Raum;
import de.wwu.sopra.model.Schublade;
import de.wwu.sopra.model.Segment;
import de.wwu.sopra.model.Studie;

/**
 * findet einen Platz fuer eine Probe genauere Vorgehensweise in
 * Methodenkommentaren
 * 
 * @author Jasmin, Pia, Cedric
 *
 */
public class ProbenPlatzVergabe {

	private boolean platzGefunden;
	private boolean neuesRackBenoetigt;
	private Rack gespeichertInRack;
	private int gespeichertInZeile;
	private int gespeichertInSpalte;
	private Probe zuLagerndeProbe;

	/**
	 * Diese Methode ist Konstrukter der Klasse und initialisiert die Attribute
	 */
	public ProbenPlatzVergabe() {
		platzGefunden = false;
		gespeichertInRack = null;
		neuesRackBenoetigt = false;
		gespeichertInZeile = -1;
		gespeichertInSpalte = -1;
	}

	/**
	 * lagert eine Probe ein Dabei wird diese Prioritaet verwendet Rack, Schublade,
	 * ... Kuehlschrank mit Proben desselben Patienten und derselben Studie Rack,
	 * Schublade, ... Kuehlschrank mit Proben desselben Patienten Rack, Schublade,
	 * ... Kuehlschrank mit Proben derselben Studie erstes Rack in dem Kuehlschrank
	 * mit den wenigsten Proben
	 * 
	 * @param probe neue Probe
	 * @return Lagerplatz als String
	 */
	public String weiseProbePlatzZu(Probe probe) {
		Patient patient = probe.getVisite().getPatient();
		Studie studie = probe.getVisite().getStudie();
		zuLagerndeProbe = probe;

		// Listen relevanter Proben ohne die neue Probe generieren
		ArrayList<Probe> probenDesPatienten = DataFilter.filterProben("", "", null, null, null, patient);
		ArrayList<Probe> probenDerStudie = DataFilter.filterProben("", studie.getName(), null, null, null, null);
		probenDesPatienten.remove(probe);
		probenDerStudie.remove(probe);
		ArrayList<Probe> probenDesPatientenUndDerStudie = (ArrayList<Probe>) probenDesPatienten.stream()
				.filter(p -> probenDerStudie.contains(p)).collect(Collectors.toList());

		// Probe bei anderen Proben desselben Patienten und derselben Studie lagern
		this.findePlatzBeiProben(probenDesPatientenUndDerStudie);
		// Probe bei anderen Proben desselben Patienten
		if (!platzGefunden) {
			this.findePlatzBeiProben(probenDesPatienten);
		}
		// Probe bei anderen Proben derselben Studie
		if (!platzGefunden) {
			this.findePlatzBeiProben(probenDerStudie);
		}

		// keine Probe des Patienten und keine Probe der Studie vorhanden
		if (!platzGefunden) {
			findeNeuenPlatz();
		}
		// gar keinen Platz gefunden (alle Kuehlschraenke belegt)
		if (!platzGefunden) {
			throw new IllegalArgumentException(
					"Es ist kein Lagerplatz fuer diese Probe vorhanden. Es sind alle fuer die Probe geeigneten Racks belegt und es gibt keinen Platz fuer neue Racks.");
		}

		gespeichertInRack.getProben()[gespeichertInZeile][gespeichertInSpalte] = probe;
		probe.setRack(gespeichertInRack);

		return getPlatzAnweisung(gespeichertInRack, gespeichertInZeile, gespeichertInSpalte);
	}

	/**
	 * findet einen Platz in dem Kuehlschrank mit den wenigsten Proben
	 */
	private void findeNeuenPlatz() {
		ArrayList<Kuehlschrank> kuehlschraenke = DataFilter.filterKuehlschraenke("", null, null);
		int minimaleAnzahlAnProben = Integer.MAX_VALUE;
		Kuehlschrank kuehlschrank = null;
		for (Kuehlschrank k : kuehlschraenke) {
			int anzahlProben = DataFilter.filterProben(k).size();
			if (anzahlProben < minimaleAnzahlAnProben) {
				minimaleAnzahlAnProben = anzahlProben;
				kuehlschrank = k;
			}
		}

		findePlatzInKuehlschrank(kuehlschrank);
	}

	/**
	 * findet einen Platz in einem Kuehlschrank und speichert die Platzdaten in den
	 * Attributen der Klasse
	 * 
	 * @param kuehlschrank Kuehlschrank, in dem die Probe gelagert werden soll
	 */
	private void findePlatzInKuehlschrank(Kuehlschrank kuehlschrank) {
		if (kuehlschrank == null) {
			return;
		}
		for (int i = 0; i < kuehlschrank.getSegmente().size(); i++) {
			if (!platzGefunden) {
				findePlatzInSegment(kuehlschrank.getSegmente().get(i));
			}
		}
	}

	/**
	 * findet einen Platz in einem Segment und speichert die Platzdaten in den
	 * Attributen der Klasse
	 * 
	 * @param segment Segment, in dem die Probe gelagert werden soll
	 */
	private void findePlatzInSegment(Segment segment) {
		for (int i = 0; i < segment.getGestelle().size(); i++) {
			if (!platzGefunden) {
				findePlatzInGestell(segment.getGestelle().get(i));
			}
		}
	}

	/**
	 * findet einen Platz in einem Gestell und speichert die Platzdaten in den
	 * Attributen der Klasse
	 * 
	 * @param gestell Gestell, in dem die Probe gelagert werden soll
	 */
	private void findePlatzInGestell(Gestell gestell) {
		for (int i = 0; i < gestell.getSchubladen().size(); i++) {
			if (!platzGefunden) {
				findePlatzInSchublade(gestell.getSchubladen().get(i));
			}
		}
	}

	/**
	 * findet einen Platz in einer Schublade und speichert die Platzdaten in den
	 * Attributen der Klasse
	 * 
	 * @param schublade Schublade, in dem die Probe gelagert werden soll
	 */
	private void findePlatzInSchublade(Schublade schublade) {

		for (int i = 0; i < schublade.getRacks().size(); i++) {
			if (!platzGefunden) {
				findePlatzInRack(schublade.getRacks().get(i));
			}
		}

		if (platzGefunden)
			return;

		// kein Platz mehr in den Racks der Schublade, aber Platz fuer neues Rack
		if (!platzGefunden && schublade.getRacks().size() < 4) {

			Rack neu = new Rack("" + (schublade.getRacks().size() + 1), schublade, zuLagerndeProbe.getTyp(), 10, 15);
			neuesRackBenoetigt = true;

			findePlatzInRack(neu);
		}

	}

	/**
	 * findet einen Platz in einem Rack und speichert die Platzdaten in den
	 * Attributen der Klasse stellt zudem sich, dass der Behaeltertyp der Probe und
	 * des Racks uebereinstimmen
	 * 
	 * @param rack Rack, in dem die Probe gelagert werden soll
	 */
	private void findePlatzInRack(Rack rack) {
		if (rack.getProbenBehaelterTyp() == zuLagerndeProbe.getTyp()) {
			Probe[][] probenImAktuellenRack = rack.getProben();
			for (int zeile = 0; zeile < probenImAktuellenRack.length; zeile++) {
				for (int spalte = 0; spalte < probenImAktuellenRack[zeile].length; spalte++) {
					if (probenImAktuellenRack[zeile][spalte] == null && !platzGefunden) {
						gespeichertInRack = rack;
						gespeichertInZeile = zeile;
						gespeichertInSpalte = spalte;
						platzGefunden = true;
						break;

					}
				}
				if (platzGefunden) {
					break;
				}
			}
		}
	}

	/**
	 * findet Plaetze in der Naehe anderer Proben prueft fuer eine Probe, ob im
	 * Rack, in der Schublade .. ein Platz frei ist dort soll die Probe dann
	 * gelagert werden wenn nichts gefunden werden sollte (alle Plaetze in dem
	 * Kuehlschrank belegt), wird versucht ein Platz in der Naehe der naechsten
	 * Probe zu finden
	 * 
	 * @param proben eine Probe zu der ein Platz vergeben werden soll
	 */
	private void findePlatzBeiProben(ArrayList<Probe> proben) {
		for (int i = 0; i < proben.size(); i++) {
			if (!platzGefunden && proben.get(i).getStatus() != Probenstatus.DAUERHAFTENTNOMMEN) {
				Probe aktuelleProbe = proben.get(i);
				Rack aktuellesRack = aktuelleProbe.getRack();
				Schublade aktuelleSchublade = aktuellesRack.getSchublade();
				Gestell aktuellesGestell = aktuelleSchublade.getGestell();
				Segment aktuellesSegment = aktuellesGestell.getSegment();
				Kuehlschrank aktuellerKuehlschrank = aktuellesSegment.getKuehlschrank();

				findePlatzInRack(aktuellesRack);
				if (!platzGefunden) {
					findePlatzInSchublade(aktuelleSchublade);
				}
				if (!platzGefunden) {
					findePlatzInGestell(aktuellesGestell);
				}
				if (!platzGefunden) {
					findePlatzInSegment(aktuellesSegment);
				}
				if (!platzGefunden) {
					findePlatzInKuehlschrank(aktuellerKuehlschrank);
				}
			}

		}
	}

	/**
	 * gibt eine genaue Lagerbezeichnung zur Einlagerung einer Probe an
	 * 
	 * @param rack   Rack, wo die Probe gelagert werden soll
	 * @param zeile  Zeile, wo die Probe gelagert werden soll
	 * @param spalte Spalte, wo die Probe gelagert werden soll
	 * @return Lagerstandort der neuen Probe und ggf. Hinweis auf neu benoetigtes
	 *         Rack
	 */
	private String getPlatzAnweisung(Rack rack, int zeile, int spalte) {
		Schublade s = rack.getSchublade();
		Gestell g = s.getGestell();
		Segment seg = g.getSegment();
		Kuehlschrank k = seg.getKuehlschrank();
		Raum r = k.getRaum();
		Probe probe = rack.getProbe(zeile, spalte);
		Probe[][] probenArray = rack.getProben();

		String anweisung = "";
		if (neuesRackBenoetigt) {
			anweisung = "Ein neues Rack mit dem Namen: " + rack.getName()
					+ " wird benoetigt. Bitte lagern sie dieses und die Probe der folgenden Angabe entsprechend." + "\n";
		}

		anweisung += "Standort des Raumes: " + r.getStandort() + "\n"+ "Raum: " + r.getName() +  "\n" + "Kuehlschrank: "
				+ k.getName() + "\n"+ "Segment: " + seg.getName()  + "\n" + "Gestell: " + g.getName() + "\n"+ "Schublade: "
				+ s.getName() + "\n" + "Rack: " + rack.getName() + "\n"+ "Stellplatz: " + getStellplatz(zeile, spalte);

		return anweisung;
	}

	/**
	 * Diese Methode gibt den Stellplatz wieder
	 * 
	 * @param zeile,  die zeile der Probe
	 * @param spalte, die Spalte der probe
	 * @return der Stellplatz der Probe als String
	 */

	private String getStellplatz(int zeile, int spalte) {
		char buchstabe = (char) (65 + zeile);
		return "" + buchstabe + (spalte + 1);
	}
}
