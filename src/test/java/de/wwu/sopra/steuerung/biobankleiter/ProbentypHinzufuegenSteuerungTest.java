package de.wwu.sopra.steuerung.biobankleiter;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;

import de.wwu.sopra.datenhaltung.DataFilter;
import de.wwu.sopra.datenhaltung.DataManagement;
import de.wwu.sopra.steuerung.biobankleiter.ProbentypHinzufuegenSteuerung;

/**
 * Klasse zum Testen des Hinzufuegens eines Probentypen
 * @author Jasmin, Pia, Cedric Diese Klasse dient als Testklasse zur Klasse
 *         ProbentypHinzufuegenSteuerung
 */
public class ProbentypHinzufuegenSteuerungTest {
	/**
	 * Diese Methode testet die Methode ProbenytypHinzufuegen und testet, ob die
	 * Methode eine Fehlermeldung ausgibt, wenn der Name leer ist
	 */
	@Test
	public void testProbentypHinzufuegen() {
		String name = "";
		ProbentypHinzufuegenSteuerung verwaltung = new ProbentypHinzufuegenSteuerung();
		assertThrows(IllegalArgumentException.class, () -> {
			verwaltung.probentypHinzufuegen(name);
		});
	}

	/**
	 * Diese Methode testet die Methode ProbentypHinzufuegen und prueft, ob die
	 * Methode eine Fehlermeldung ausgibt, wenn versucht wird eine Kategorie
	 * hinzuzufuegen, wo der Name bereits vorhanden ist
	 */
	@Test
	public void testProbentypHinzufuegenNameDoppelt() {
		DataManagement.reset();
		String name = "Blut";
		ProbentypHinzufuegenSteuerung verwaltung = new ProbentypHinzufuegenSteuerung();
		verwaltung.probentypHinzufuegen(name);
		assertThrows(IllegalArgumentException.class, () -> {
			verwaltung.probentypHinzufuegen(name);
		});
	}

	/**
	 * Diese Methode testet getProbenKategorie im Normalfall
	 */

	@Test
	public void testGetProbenKategorie() {
		DataManagement.reset();
		String name = "Blut";
		ProbentypHinzufuegenSteuerung verwaltung = new ProbentypHinzufuegenSteuerung();
		verwaltung.probentypHinzufuegen(name);
		verwaltung.getProbenKategorie(name).get(0).getName().equals("Blut");

	}

	/**
	 * Diese Methode testet ob die Methode die Kategorie wirklich hinzugefuegt hat
	 */
	@Test
	public void testProbentypHinzufuegen1() {
		String name = "Blut";
		ProbentypHinzufuegenSteuerung verwaltung = new ProbentypHinzufuegenSteuerung();
		verwaltung.probentypHinzufuegen(name);
		assertNotNull(DataFilter.filterProbenKategorien(name));

	}

}
