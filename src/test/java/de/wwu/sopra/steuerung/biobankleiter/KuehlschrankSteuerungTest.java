package de.wwu.sopra.steuerung.biobankleiter;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import de.wwu.sopra.datenhaltung.DataFilter;
import de.wwu.sopra.datenhaltung.DataManagement;
import de.wwu.sopra.model.Kuehlschrank;
import de.wwu.sopra.model.Raum;
import de.wwu.sopra.steuerung.biobankleiter.KuehlschrankSteuerung;

/**
 * Diese Klasse testet die Klasse KuehlschrarnkSteuerung
 * 
 * @author Jasmin, Pia, Cedric
 *
 */
public class KuehlschrankSteuerungTest {

	/**
	 * Fuehrt einen Reset der aktuellen Datenbank durch.
	 */
	@BeforeEach
	public void dataManagementBereinigen() {
		DataManagement.reset();
	}

	/**
	 * Diese Methode testet die KuehlschrankHinzufuegen Methode und prueft ob der
	 * Kuehlschrank auch wirklich hinzugefuegt wurde
	 */
	@Test
	public void testKuehlschrankHinzufuegen() {
		DataManagement dm = DataManagement.getInstance();
		KuehlschrankSteuerung kS = new KuehlschrankSteuerung();
		Raum r = new Raum("Raum 2", "Gebaeude 2");
		dm.speichereRaum(r);
		kS.kuehlschrankHinzufuegen("Kuehlschrank 1", "5.3", r);
		int anzahlDieserKuehlschrank = DataFilter.filterKuehlschraenke("Kuehlschrank 1", 5.3, r).size();
		int erwarteteAnzahlDieserKuehlschrank = 1;
		assertEquals(erwarteteAnzahlDieserKuehlschrank, anzahlDieserKuehlschrank);
	}

	/**
	 * Diese Methode testet die KuehshrankenHinzufuegen Methode und es wirdgeprueft,
	 * ob die Methode eine Fehlermeldung ausgibt, wenn der Name leer gelassen wird
	 */

	@Test
	public void testKuehlschrankHinzufuegenKeinName() {
		KuehlschrankSteuerung kS = new KuehlschrankSteuerung();

		assertThrows(IllegalArgumentException.class, () -> {
			kS.kuehlschrankHinzufuegen("", "20.8", new Raum("Raum 1", "Gebaeude 1"));

		});
	}

	/**
	 * Diese Methode testet die Hinzufuegen Methode, wenn kein Raum uebergeben wird,
	 * Die Methode sollte dann eine llegalArgumentException werfen
	 */

	@Test
	public void testKuehlschrankHinzufuegenKeinRaum() {
		KuehlschrankSteuerung kS = new KuehlschrankSteuerung();

		assertThrows(IllegalArgumentException.class, () -> {
			kS.kuehlschrankHinzufuegen("Kuehlschrank 1", "20.8", null);

		});
	}

	/**
	 * Diese Methode testet ob die Methode KuehlschrankHinzufuegen eine
	 * IllgealArgumentException wird, fallls beim hinzufuegen keine Zahl uebergeben
	 * wird
	 */

	@Test
	public void testKuehlschrankHinzufuegenKeineZahl() {
		KuehlschrankSteuerung kS = new KuehlschrankSteuerung();

		assertThrows(IllegalArgumentException.class, () -> {
			kS.kuehlschrankHinzufuegen("Kuehlschrank 1", "Dies ist keine Zahl.", new Raum("Raum 2", "Gebaeude 2"));
		});
	}

	/**
	 * Diese Methode testet den Normalfall KuehschrankBearbeiten
	 */

	@Test
	public void testKuehlschrankBearbeiten() {
		DataManagement dm = DataManagement.getInstance();
		Raum rAlt = new Raum("Raum 2", "Gebaeude 2");
		dm.speichereRaum(rAlt);
		Kuehlschrank alt = new Kuehlschrank("Kuehlschrank 2", 4.0, rAlt);
		KuehlschrankSteuerung kS = new KuehlschrankSteuerung();
		Raum rNeu = new Raum("Raum 2", "Gebaeude 2");
		dm.speichereRaum(rNeu);
		kS.kuehlschrankBearbeiten(alt, "Kuehlschrank 1", "5,3", rNeu);

		int anzahlDieserKuehlschrank = DataFilter.filterKuehlschraenke("Kuehlschrank 1", 5.3, rNeu).size();
		int erwarteteAnzahlDieserKuehlschrank = 1;

		assertEquals(erwarteteAnzahlDieserKuehlschrank, anzahlDieserKuehlschrank);

	}

	/**
	 * Diese Methode testet die Methode KuehlschrankBearbeiten und prueft ob die
	 * Methode eine Fehlermeldung ausgibt, falls der Name leer gelassen wird
	 */
	@Test
	public void testKuehlschrankBearbeitenKeinName() {
		KuehlschrankSteuerung kS = new KuehlschrankSteuerung();
		Raum rAlt = new Raum("Raum 2", "Gebaeude 2");
		Kuehlschrank alt = new Kuehlschrank("Kuehlschrank 2", 4.0, rAlt);
		assertThrows(IllegalArgumentException.class, () -> {
			kS.kuehlschrankBearbeiten(alt, "", "20.8", new Raum("Raum 1", "Gebaeude 1"));

		});
	}

	/**
	 * Diese Methode testet die Methode KuehlschrankBearbeiten und prueft ob die
	 * Methode eine Fehlermeldung ausgibt, falls der Raum leer gelassen wird
	 */
	@Test
	public void testKuehlschrankBearbeitenKeinRaum() {
		KuehlschrankSteuerung kS = new KuehlschrankSteuerung();
		Raum rAlt = new Raum("Raum 2", "Gebaeude 2");
		Kuehlschrank alt = new Kuehlschrank("Kuehlschrank 2", 4.0, rAlt);

		assertThrows(IllegalArgumentException.class, () -> {
			kS.kuehlschrankBearbeiten(alt, "Kuehlschrank 1", "20.8", null);

		});
	}

	/**
	 * Diese Methode testet die Methode KuehlschrankBearbeiten und prueft ob die
	 * Methode eine Fehlermeldung ausgibt, falls eine Zahl uebergeben wird
	 */

	@Test
	public void testKuehlschrankBearbeitenKeineZahl() {
		KuehlschrankSteuerung kS = new KuehlschrankSteuerung();
		Raum rAlt = new Raum("Raum 2", "Gebaeude 2");
		Kuehlschrank alt = new Kuehlschrank("Kuehlschrank 2", 4.0, rAlt);

		assertThrows(IllegalArgumentException.class, () -> {
			kS.kuehlschrankBearbeiten(alt, "Kuehlschrank 1", "Dies ist keine Zahl.", new Raum("Raum 2", "Gebaeude 2"));
		});
	}

}
