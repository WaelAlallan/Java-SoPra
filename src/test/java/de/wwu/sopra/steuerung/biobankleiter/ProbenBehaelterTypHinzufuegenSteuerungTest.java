package de.wwu.sopra.steuerung.biobankleiter;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;

import de.wwu.sopra.datenhaltung.DataFilter;
import de.wwu.sopra.datenhaltung.DataManagement;
import de.wwu.sopra.steuerung.biobankleiter.ProbenBehaeltertypHinzufuegenSteuerung;

/**
 * Diese Klasse testet die Klasse ProbenBehaelterTypHinzufuegen
 * 
 * @author Jasmin, Pia, Cedric
 *
 */
public class ProbenBehaelterTypHinzufuegenSteuerungTest {
	/**
	 * Diese Methode testet die Methode ProbenBehaelterTypHinzufuegen und testet,ob
	 * der ProbenBehaelterTyp wirklich hinzugefuegt wurde
	 */

	@Test
	public void ProbenBehaelterTypHinzufuegenTest() {
		ProbenBehaeltertypHinzufuegenSteuerung steuerung = new ProbenBehaeltertypHinzufuegenSteuerung();
		steuerung.probenBehaeltertypHinzufuegen("Reagenzglas", "10", "5", "5", "Pfropfen");
		assertNotNull(DataFilter.filterProbenBehaelterTypen("Reagenzglas", 10.0, 5.0, 5.0, "Pfropfen"));
	}

	/**
	 * Diese Methode testet die Methode ProbenBehaelterTypHinzufuegen und prueft, ob
	 * die Methode eine Fehlermeldung ausgibt, wenn man ein Volumen von 0 eingibt
	 */
	@Test
	public void ProbenBehaelterTypHinzufuegen2Test() {
		ProbenBehaeltertypHinzufuegenSteuerung steuerung = new ProbenBehaeltertypHinzufuegenSteuerung();

		assertThrows(IllegalArgumentException.class, () -> {
			steuerung.probenBehaeltertypHinzufuegen("Reagenzglas", "0", "5", "5", "Pfropfen");

		});
	}

	/**
	 * Diese Methode testet die Methode ProbenBehaelterTypHinzufuegen und testet ob
	 * die Methode eine Fehlermeldung ausgibt, falls der Name leer gelassen wird
	 */
	@Test
	public void ProbenBehaelterTypHinzufuegen3Test() {
		ProbenBehaeltertypHinzufuegenSteuerung steuerung = new ProbenBehaeltertypHinzufuegenSteuerung();

		assertThrows(IllegalArgumentException.class, () -> {
			steuerung.probenBehaeltertypHinzufuegen("", "5", "5", "5", "Pfropfen");

		});
	}

	/**
	 * Diese Methode testet die Methode ProbenBehaelterTypHinzufuegen und testet ob
	 * die Methode mit einer Eingabe eines Kommas statt Punktes umgehen kann
	 */
	@Test
	public void ProbenBehaelterTypHinzufuegenTest1() {
		ProbenBehaeltertypHinzufuegenSteuerung steuerung = new ProbenBehaeltertypHinzufuegenSteuerung();
		steuerung.probenBehaeltertypHinzufuegen("Reagenzglas", "10", "5,5", "5", "Pfropfen");
		assertNotNull(DataFilter.filterProbenBehaelterTypen("Reagenzglas", 10.0, 5.5, 5.0, "Pfropfen"));
	}

	/**
	 * Die Methode testet die Methode getProbenbehaelterTyp und schaut ob bei
	 * falsche Eingabe die gewuenste Exception geworfen wird
	 */
	@Test
	public void testGetProbenbehaelterTyp() {
		ProbenBehaeltertypHinzufuegenSteuerung steuerung = new ProbenBehaeltertypHinzufuegenSteuerung();
		assertThrows(IllegalArgumentException.class, () -> {
			steuerung.getProbenbehaeltertyp("Blut", "hallo", "2.5", "3,4", "4,5");
		});
	}

}
