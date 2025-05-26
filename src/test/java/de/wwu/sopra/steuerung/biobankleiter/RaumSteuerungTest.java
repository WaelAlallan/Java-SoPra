package de.wwu.sopra.steuerung.biobankleiter;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;

import de.wwu.sopra.datenhaltung.DataFilter;
import de.wwu.sopra.datenhaltung.DataManagement;
import de.wwu.sopra.model.Raum;
import de.wwu.sopra.steuerung.biobankleiter.RaumSteuerung;

/**
 * Klasse zum Testen der Raumsteuerung
 * @author Pia, Jasmin, Cedric
 *
 */
public class RaumSteuerungTest {
	/**
	 * Diese Methode testet die Methode RaumHinzufuegen im Normalfall und prueft ib
	 * der Raum auch hinzugefuegt wurde
	 */

//	@Test
//	public void testRaumHinzufuegen() {
//		RaumSteuerung rS = new RaumSteuerung();
//		Raum erwarteterRaum = new Raum("Raum 1", "Gebaeude 1");
//		erwarteterRaum.setEingestellteTemperatur(10.5);
//		
//		rS.raumHinzufuegen("Raum 1", "Gebaeude 1", "10,5");
//		IDataManagement verwaltung = new DataManagement();
//		Raum erzeugterRaum = verwaltung.getRaum("Raum 1", "Gebaeude 1", 10.5);
//		assertEquals(erwarteterRaum, erzeugterRaum);
//		
//	}
	/**
	 * Testet Hinzufuegen im Fall leerer Name
	 */
	@Test
	public void testRaumHinzufuegenLeererName() {
		RaumSteuerung rS = new RaumSteuerung();
		assertThrows(IllegalArgumentException.class, () -> {
			rS.raumHinzufuegen("", "Gebaeude 1", "8.9");
		});
	}

	/**
	 * Testet Hinzufuegen im Fall leerer Standort
	 */
	@Test
	public void testRaumHinzufuegenLeererStandort() {
		RaumSteuerung rS = new RaumSteuerung();
		assertThrows(IllegalArgumentException.class, () -> {
			rS.raumHinzufuegen("Raum 1", "", "8.9");
		});
	}

	/**
	 * Testet Raum hinzufuegen im Fall keine Kommazahl uebergeben
	 */
	@Test
	public void testRaumHinzufuegenKeineKommazahl() {
		RaumSteuerung rS = new RaumSteuerung();
		assertThrows(IllegalArgumentException.class, () -> {
			rS.raumHinzufuegen("Raum 1", "Gebaeude 1", "Keine Kommazahl");
		});
	}

	/**
	 * Testet Raum bearbeiten im Normalfall
	 */
	@Test
	public void testRaumBearbeiten() {
//		RaumSteuerung rS = new RaumSteuerung();
//		Raum raumVorher = new Raum("Raum 1 Etage 1", "Gebaeude 2");
//		raumVorher.setEingestellteTemperatur(9.5);
//		Raum erwarteterRaum = new Raum("Raum 1", "Gebaeude 1");
//		erwarteterRaum.setEingestellteTemperatur(10.5);
//		
//		IDataManagement verwaltung = new DataManagement();
//		
//		rS.raumBearbeiten(raumVorher,"Raum 1", "Gebaeude 1", "10,5");
//		Raum erzeugterRaum = verwaltung.getRaum("Raum 1", "Gebaeude 1", 10.5);
//		assertEquals(erwarteterRaum, erzeugterRaum);

	}

	/**
	 * Testet raum bearbeiten im Fall leerer Name
	 */
	@Test
	public void testRaumBearbeitenLeererName() {
		Raum r = new Raum("M1", "ES64 Gebaeude");

		RaumSteuerung rS = new RaumSteuerung();
		assertThrows(IllegalArgumentException.class, () -> {
			rS.raumBearbeiten(r, "", "Gebaeude 1", "8.9");
		});
	}

	/**
	 * Testet raum bearbeiten im Fall leerer Standort
	 */
	@Test
	public void testRaumBearbeitenLeererStandort() {
		Raum r = new Raum("M1", "ES64 Gebaeude");
		RaumSteuerung rS = new RaumSteuerung();
		assertThrows(IllegalArgumentException.class, () -> {
			rS.raumBearbeiten(r, "Raum 1", "", "8.9");
		});
	}

	/**
	 * Testet raum bearbeitet im Fall keine Kommazahl uebergeben
	 */
	@Test
	public void testRaumBearbeitenKeineKommazahl() {
		Raum r = new Raum("M1", "ES64 Gebaeude");
		RaumSteuerung rS = new RaumSteuerung();
		assertThrows(IllegalArgumentException.class, () -> {
			rS.raumBearbeiten(r, "Raum 1", "Gebaeude 1", "Keine Kommazahl");
		});
	}

	/**
	 * Testet raum loeschen im Normalfall
	 */
	@Test
	public void testRaumLoeschen() {
		Raum r = new Raum("Raum 1", "Gebaeude1");
		r.setEingestellteTemperatur(3.7);

		RaumSteuerung rS = new RaumSteuerung();
		rS.raumLoeschen(r);

		int anzahlGleichesObjekt = DataFilter.filterRaeume("Raum 1", "Gebaeude 1", 3.7).size();
		assertEquals(0, anzahlGleichesObjekt);
	}
}
