package de.wwu.sopra.steuerung.mta;

import java.util.ArrayList;
import java.util.Date;

import de.wwu.sopra.datenhaltung.DataFilter;
import de.wwu.sopra.model.Gestell;
import de.wwu.sopra.model.Kuehlschrank;
import de.wwu.sopra.model.Probe;
import de.wwu.sopra.model.ProbenBehaelterTyp;
import de.wwu.sopra.model.ProbenKategorie;
import de.wwu.sopra.model.Probenstatus;
import de.wwu.sopra.model.Rack;
import de.wwu.sopra.model.Raum;
import de.wwu.sopra.model.Schublade;
import de.wwu.sopra.model.Segment;
import de.wwu.sopra.model.Visite;
import de.wwu.sopra.steuerung.helfer.DatumHelfer;

/**
 * Diese Klasse dient dazu Proben zu suchen, einzulagern, kurzfristig zu
 * entnehmen, dauerhaft zu entnehmen und dem System hinzuzufuegen
 * 
 * @author Jasmin, Pia, Cedric
 *
 */
public class ProbenSteuerung {
	/**
	 * Diese Methode sucht eine Probe
	 * 
	 * @param kategorie     Die Kategorie der Probe welche gesucht wird
	 * @param studienname   Der Name der Studie, zu der die Probe gesucht wird
	 * @param visitenId     Die Nummer des Patienten, zu dem die Probe gesucht wird
	 * @param barcode       der Barcode der Probe, welche gesucht werden soll
	 * @param entnahmedatum das Datum an dem die Probe entnommen wurde, welche
	 *                       gesucht werden soll
	 * @return eine ArrayListe an Proben zu denen die eingegebenen Daten passen
	 */
	public ArrayList<Probe> getProben(String kategorie, String studienname, String visitenId, String barcode,
			String entnahmedatum) {
		Integer barcodeInt = null;
		if (!barcode.equals("")) {
			try {
				barcodeInt = Integer.parseInt(barcode);
			} catch (Exception e) {
				throw new IllegalArgumentException("Der Barcode muss eine Ganzzahl sein.");
			}
		}

		// VisitenId umwandeln
		Integer visitenIdInt = null;
		if (!visitenId.equals("")) {
			try {
				visitenIdInt = Integer.parseInt(visitenId);
			} catch (Exception e) {
				throw new IllegalArgumentException("Die Patientennummer muss eine Ganzzahl sein.");
			}
		}

		// Datum umwandeln
		Date datum = null;
		if (!entnahmedatum.equals("")) {
			try {
				datum = DatumHelfer.erzeugeDatum(entnahmedatum);
			} catch (Exception e) {
				throw new IllegalArgumentException("FehlerhafteEingabe");
			}
		}
		return DataFilter.filterProben(kategorie, studienname, visitenIdInt, barcodeInt, datum, null);
	}

	/**
	 * Diese Probe ist dazu da probenEinzulagen dh dem system hinzuzufuegen
	 * 
	 * @param kategorie  Die Kategorie der zu einlagender Probe
	 * @param behaelter  die Art des Behaelters der Probe welche eingelagert werden
	 *                    soll
	 * @param visitenid  die Visitenid der Visite zu der die Probe entnommen wurde
	 * @param probenid   die Probenid mit der die Probe eindeutig identifiziert
	 *                    wird
	 * @param mutterProbe die Ursprungsprobe, aus der diese Probe hergestellt wurde.
	 *                    Dies ist null, wenn die Probe komplett neu ist
	 * @return String mit stellplatz der Probe
	 */

	public String probeEintragen(ProbenKategorie kategorie, ProbenBehaelterTyp behaelter, String visitenid,
			String probenid, Probe mutterProbe) {

		//Wenn eine Mutterprobe vorhanden ist, darf die VisitenID leer sein 
		if (kategorie == null || behaelter == null || visitenid.equals("") && mutterProbe == null || probenid == null) {

			throw new IllegalArgumentException("Es darf kein Feld leer sein");
		}
		int visiteId;
		if (mutterProbe != null) {
			visiteId = mutterProbe.getVisite().getVisitenId();
		} else {
			try {
				visiteId = Integer.parseInt(visitenid);
			} catch (Exception e) {
				throw new IllegalArgumentException("Die ProbenID muss eine positive ganze Zahl sein.");
			}

		}

		int probeId;
		try {
			probeId = Integer.parseInt(probenid);
		} catch (Exception e) {
			throw new IllegalArgumentException("Die ProbenID muss eine positive ganze Zahl sein");
		}
		// Visite heraussuchen
		Visite visite = DataFilter.filterVisiteById(visiteId);

		if (visite == null) {
			throw new IllegalArgumentException("Die VisitenID ist ungueltig.");
		}

		// Probe erstellen
		Probe probe = new Probe(probeId, Probenstatus.EINGELAGERT, behaelter, kategorie, visite);

		if (mutterProbe != null) {
			probe.setMutterProbe(mutterProbe);
		}

		ProbenPlatzVergabe probenPlatzVergabe = new ProbenPlatzVergabe();
		String platzHinweis;
		try {
		platzHinweis = probenPlatzVergabe.weiseProbePlatzZu(probe);
		}
		catch(Exception e)
		{
			visite.removeProbe(probe);
			throw new IllegalArgumentException(e.getMessage());
		}
		return platzHinweis;
	}

	/**
	 * Diese Methode ist zum KurzfristigEntnehmen einer Probe, der Zustand der Probe
	 * wird auf kurzfristig entnommen gesetzt
	 * 
	 * @param probe diese Probe soll kurzfr entnommen werden
	 * @return den stellplatz der Probe
	 */
	public String probeKurzfristigEntnehmen(Probe probe) {
		if (probe.equals(null) || probe.getStatus().equals(Probenstatus.DAUERHAFTENTNOMMEN)
				|| probe.getStatus().equals(Probenstatus.KURZFRISTIGENTNOMMEN)) {
			throw new IllegalArgumentException("Das kurzfristige Entnehmen ist nicht moeglich");
		}
		probe.setStatus(Probenstatus.KURZFRISTIGENTNOMMEN);
		return getStellplatz(probe);
	}

	/**
	 * Mithilfe dieser Methode lassen sich Proben welche kurzfristig entnommen
	 * wurde, zurueck legen
	 * 
	 * @param probe die Probe welche zurueck gelegt werden soll
	 * @return den Stellplatz als String
	 */

	public String probeZurueckLegen(Probe probe) {
		if (!(probe.getStatus().equals(Probenstatus.KURZFRISTIGENTNOMMEN))) {
			throw new IllegalArgumentException(
					"Die Probe ist nicht kurzfristig entnommen und kann daher nicht zurueck gestellt werden");
		}
		probe.setStatus(Probenstatus.EINGELAGERT);
		return this.getStellplatz(probe);
	}

	/**
	 * Diese Methode dient dazu einen Stellplatz einer Probe herauszufinden
	 * 
	 * @param probe die Probe wovon der Stellplatz angezeigt werden soll @return,
	 *               String mit dem Stellplatz
	 * @return (String) Gibt den Stellplatz der gesuchten Probe zurueck
	 */
	public String getStellplatz(Probe probe) {
		Rack rack = probe.getRack();
		Schublade schublade = rack.getSchublade();
		Gestell gestell = schublade.getGestell();
		Segment segment = gestell.getSegment();
		Kuehlschrank kuehlschrank = segment.getKuehlschrank();
		Raum raum = kuehlschrank.getRaum();
		String standort = raum.getStandort();
		Probe[][] probenArray = rack.getProben();
		String standortProbe = getProbenStandort(probenArray, probe);

		if (standortProbe.equals("")) {
			throw new IllegalArgumentException("Probenstandort nicht gefunden");
		}
		String ausgabe = "Standort des Raumes:" + standort + " Raum:" + raum.getName() + " Kuehlschrank:"
				+ kuehlschrank.getName() + " Segment:" + segment.getName() + " Gestell:" + gestell.getName()
				+ " Schublade:" + schublade.getName() + "Rack: " + rack.getName() + "Stellplatz: " + standortProbe;

		return ausgabe;
	}

	/**
	 * Diese Methode gibt den Standort einer Probe im Rack zurueck
	 * 
	 * @param probenArray   das Rack
	 * @param gesuchteProbe die Probe dessen Standort ermittelt werden soll
	 * @return den Standort der Probe im Rack als String
	 */
	public String getProbenStandort(Probe[][] probenArray, Probe gesuchteProbe) {
		String standortProbe = "";
		for (int i = 0; i < probenArray.length; i++) {
			for (int j = 0; j < probenArray[i].length; j++) {
				if (gesuchteProbe.equals(probenArray[i][j])) {
					standortProbe = "" + (char) (i + 65) + (j + 1);
					break;
				}

			}
		}
		return standortProbe;
	}

	/**
	 * Mithilfe dieser Methode lassen sich Proben dauerhaft entnehmen
	 * 
	 * @param probe die Probe die dauerheft entnommen werden soll
	 * @return Stellplatz der Probe
	 * @throws IllegalArgumentException Fehler fuer ein nicht unterstuetztes Argument.
	 */

	public String probeDauerhaftEntnehmen(Probe probe) throws IllegalArgumentException {
		if (!(probe.getStatus().equals(Probenstatus.EINGELAGERT))) {
			throw new IllegalArgumentException(
					"Die Probe ist nicht eingelagert und kann daher nicht dauerhaft entnommen werden");
		}
		String ret = this.getStellplatz(probe);
		probe.setStatus(Probenstatus.DAUERHAFTENTNOMMEN);
		Rack rack = probe.getRack();
		rack.removeProbe(probe);
		return ret;
	}

	/**
	 * Mithilfe dieser Methode lassen sich Proben loeschen
	 * 
	 * @param probe die Probe die geloescht werden soll
	 * @throws IllegalArgumentException wird ausgegeben, wenn die Probe nicht
	 *                                  dauerhaft entnommen wurde
	 */
	public void probeLoeschen(Probe probe) throws IllegalArgumentException {
		if (!(probe.getStatus().equals(Probenstatus.DAUERHAFTENTNOMMEN))) {
			throw new IllegalArgumentException(
					"Die Probe ist noch nicht dauerhaft entnommen und kann desshalb nicht geloescht werden");
		}
		probe.getVisite().removeProbe(probe);
	}
}
