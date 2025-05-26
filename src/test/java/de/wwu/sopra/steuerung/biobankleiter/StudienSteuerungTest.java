package de.wwu.sopra.steuerung.biobankleiter;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;

import de.wwu.sopra.datenhaltung.DataFilter;
import de.wwu.sopra.datenhaltung.DataManagement;
import de.wwu.sopra.model.Studie;
import de.wwu.sopra.steuerung.biobankleiter.StudienSteuerung;

/**
 * testet die StudienSteuerung
 * 
 * @author Pia, Jasmin, Cedric
 *
 */
public class StudienSteuerungTest {
	/**
	 * testet die StudienHinzufuegen Methode mit einem Studiennamen der bereits
	 * vorhanden ist
	 */

	@Test
	public void testStudieHinzufuegenMitGleichemNamen() {
		DataManagement.reset();
		StudienSteuerung steuerung = new StudienSteuerung();
		steuerung.studieHinzufuegen("Studie 1", "25");
		assertThrows(IllegalArgumentException.class, () -> {
			steuerung.studieHinzufuegen("Studie 1", "2");
		});

	}

	/**
	 * testet studieHinzufuegen Standardfall Der Standardfall arbeitet ohne
	 * Visitenvorlagen, da diese spaeter ueber studieBearbeiten ergaenzt werden
	 */

	@Test
	public void testStudieHinzufuegen() {
		DataManagement.reset();
		StudienSteuerung steuerung = new StudienSteuerung();
		steuerung.studieHinzufuegen("Studie 1", "25");
		int anzahlDieserStudie = DataFilter.filterStudien("Studie 1", 25, null, null).size();
		int erwarteteAnzahlDieserStudie = 1;

		assertEquals(erwarteteAnzahlDieserStudie, anzahlDieserStudie);

	}

	/**
	 * testet studieHinzufuegen Leerer Namensstring
	 */
	@Test
	public void testStudieHinzufuegenKeinName() {
		DataManagement.reset();
		StudienSteuerung steuerung = new StudienSteuerung();
		assertThrows(IllegalArgumentException.class, () -> {
			steuerung.studieHinzufuegen("", "25");
		});

	}

	/**
	 * testet studieHinzufuegen Leerer gewuenschte Teilnehmerstring
	 */
	@Test
	public void testStudieHinzufuegenKeineAnzahlTeilnehmer() {
		DataManagement.reset();
		StudienSteuerung steuerung = new StudienSteuerung();
		assertThrows(IllegalArgumentException.class, () -> {
			steuerung.studieHinzufuegen("Studie 2", "");
		});

	}

	/**
	 * testet studieHinzufuegen Kein int als AnzahlTeilnehmer
	 */
	@Test
	public void testStudieHinzufuegenKeineZahlAlsAnzahlTeilnehmer() {
		DataManagement.reset();
		StudienSteuerung steuerung = new StudienSteuerung();
		assertThrows(IllegalArgumentException.class, () -> {
			steuerung.studieHinzufuegen("Studie 2", "Dies ist keine Zahl");
		});

	}

	/**
	 * testet studieBearbeiten Standardfall VisitenVorlagenaenderung schon zuvor am
	 * Studienobjekt
	 */
	@Test
	public void testStudieBearbeiten() {
		DataManagement.reset();
		StudienSteuerung steuerung = new StudienSteuerung();
		Studie alt = new Studie("alter Name", 20);

		DataManagement verwaltung = DataManagement.getInstance();
		verwaltung.speichereStudie(alt);
		steuerung.studieBearbeiten(alt, "Studie 1", "25");

		int anzahlDieserStudie = DataFilter.filterStudien("Studie 1", 25, null, null).size();
		int erwarteteAnzahlDieserStudie = 1;
		assertEquals(erwarteteAnzahlDieserStudie, anzahlDieserStudie);

	}

	/**
	 * testet studieBearbeiten Fall leerer Namensstring
	 */
	@Test
	public void testStudieBearbeitenKeinName() {
		DataManagement.reset();
		StudienSteuerung steuerung = new StudienSteuerung();
		assertThrows(IllegalArgumentException.class, () -> {
			steuerung.studieBearbeiten(new Studie("", 0), "", "25");
		});

	}

	/**
	 * testet studieBearbeiten Fall leere AnzahlTeilnehmerstring
	 */
	@Test
	public void testStudieBearbeitenKeineAnzahlTeilnehmer() {
		DataManagement.reset();
		StudienSteuerung steuerung = new StudienSteuerung();
		assertThrows(IllegalArgumentException.class, () -> {
			steuerung.studieBearbeiten(new Studie("", 0), "Studie 1", "");
		});

	}

	/**
	 * testet studieBearbeiten Fall AnzahlTeilnehmerstring kann nicht zu int
	 * umgewandelt werden
	 */
	@Test
	public void testStudieBearbeitenKeineZahlAlsAnzahlTeilnehmer() {
		DataManagement.reset();
		StudienSteuerung steuerung = new StudienSteuerung();
		assertThrows(IllegalArgumentException.class, () -> {
			steuerung.studieBearbeiten(new Studie("", 0), "Studie 1", "Dies ist keine Zahl.");
		});

	}

	/**
	 * testet studieLoeschen Standardfall
	 */
	@Test
	public void testStudieLoeschen() {
		DataManagement.reset();
		StudienSteuerung steuerung = new StudienSteuerung();
		Studie studie = new Studie("alter Name", 20);

		DataManagement verwaltung = DataManagement.getInstance();
		verwaltung.speichereStudie(studie);
		steuerung.studieLoeschen(studie);

		int anzahlDieserStudie = DataFilter.filterStudien("Studie 1", 25, null, null).size();
		int erwarteteAnzahlDieserStudie = 0;
		assertEquals(erwarteteAnzahlDieserStudie, anzahlDieserStudie);
	}

}
