package de.wwu.sopra.datenhaltung;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.File;
import java.io.IOException;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.thoughtworks.xstream.io.StreamException;

import de.wwu.sopra.datenhaltung.DataManagement;
import de.wwu.sopra.model.Benutzer;
import de.wwu.sopra.model.Kuehlschrank;
import de.wwu.sopra.model.Patient;
import de.wwu.sopra.model.ProbenBehaelterTyp;
import de.wwu.sopra.model.Raum;
import de.wwu.sopra.model.Studie;
import de.wwu.sopra.model.ProbenKategorie;

/**
 * Klasse zum Testen des Datenmanagements
 * @author Gruppe 1
 *
 */
public class DataManagementTest {
	/**
	 * Vor jedem Test soll die Datenbasis zurueckgesetzt werden
	 */
	@BeforeEach
	public void resetDataManagement() {
		DataManagement.reset();
	}

	/**
	 * Testet, ob eine IOException geworfen wird falls das auszulesene File nicht
	 * existiert.
	 */
	/*
	@Test
	public void testReadDataFromNonexistingFile() {
		assertThrows(IOException.class, () -> DataManagement.load("xyx"));
	}
	 */

	/**
	 * Testet, ob die Daten aus dem zulesenden File das richtige Format btw. den
	 * richtigen Inhalt hat.
	 * 
	 * @throws IOException Fehler bei Ein- und Ausgabe der Daten
	 */
	/*
	@Test
	public void testReadDataFromFileWithIncorrectContent() throws IOException {
		File directory = new File("tmp");

		if (!directory.exists()) {
			directory.mkdir();
		}
		File file = new File(directory.getAbsolutePath() + "/junittest.xml");
		if (!file.exists()) {
			file.createNewFile();
		}
		java.io.FileWriter fw = new java.io.FileWriter(file);
		fw.write("abcd");
		fw.close();
		assertThrows(StreamException.class, () -> DataManagement.load("tmp/junittest.xml"));
	}
	 */


	/**
	 * Testet das Speichern und Laden von Objekten in die XML Datei und umgekehrt.
	 * 
	 * @throws IOException Fehler bei Ein- und Ausgabe der Daten
	 */
	/*
	@Test
	public void testStoreAndLoadFile() throws IOException {
		DataManagement dm = DataManagement.getInstance();
		Benutzer benutzer = new Benutzer("Mustermann", "Erika", null);
		dm.speichereBenutzer(benutzer);

		DataManagement.persist("tmp/junittest2.xml");
		assertEquals(dm.getBenutzer().get(0).getNachname(),
				DataManagement.load("tmp/junittest2.xml").getBenutzer().get(0).getNachname());
	}
	 */

	/**
	 * Pruefen ob ein zweimaliges Aufrufen der getInstance Methode auf das gleiche
	 * DataManagement Objekt zeigt
	 */
	@Test
	public void testMehrfachesErstellenDataManagement() {
		Benutzer benutzer = new Benutzer("N4", "V4", null);
		benutzer.setBenutzername("B4");
		DataManagement dm1 = DataManagement.getInstance();
		dm1.speichereBenutzer(benutzer);
		DataManagement dm2 = DataManagement.getInstance();
		assertTrue(dm2.getBenutzer().contains(benutzer));
	}

	/**
	 * Testet das Hinzufuegen eines Benutzers zur Datenbasis
	 */
	@Test
	public void testBenutzerHinzufuegen() {
		Benutzer benutzer = new Benutzer("N3", "V3", null);
		benutzer.setBenutzername("B3");
		DataManagement dm = DataManagement.getInstance();
		dm.speichereBenutzer(benutzer);
		assertTrue(dm.getBenutzer().contains(benutzer));
	}

	/**
	 * Testet das Hinzufuegen und Loeschen eines Benutzers aus der Datenbasis
	 */
	@Test
	public void testBenutzerHinzufuegenUndLoeschen() {
		Benutzer benutzer = new Benutzer("N3", "V3", null);
		benutzer.setBenutzername("B3");
		DataManagement dm = DataManagement.getInstance();
		dm.speichereBenutzer(benutzer);
		dm.loescheBenutzer(benutzer);
		assertFalse(dm.getBenutzer().contains(benutzer));
	}

	/**
	 * Testet das Hinzufuegen eines Raumes zur Datenbasis
	 */
	@Test
	public void testRaumHinzufuegen() {
		DataManagement dm = DataManagement.getInstance();
		Raum raum1 = new Raum("R1", "SRZ1");
		raum1.setEingestellteTemperatur(23.0);
		dm.speichereRaum(raum1);

		assertEquals(raum1, dm.getRaeume().get(0));
	}

	/**
	 * Testet die speichereRaeum, getRaeume und loescheRaum Methoden in der
	 * DataManagement Klasse. Die Testklasse erstellt zwei Raumobjekte und fuegt sie
	 * der DataManagement Instanz und einer temporaeren ArrayList hinzu. Ist die
	 * ArrayList der getRaeume Methode gleich der temporaeren ArrayList, dann ist
	 * der Test erfolgreich.
	 */
	@Test
	public void testRaumHinzufuegenUndLoeschen() {
		DataManagement dm = DataManagement.getInstance();

		Raum raum2 = new Raum("R2", "SRZ2");
		raum2.setEingestellteTemperatur(22.0);
		dm.speichereRaum(raum2);

		Raum raum3 = new Raum("R2", "SRZ2");
		raum3.setEingestellteTemperatur(22.0);
		dm.speichereRaum(raum3);

		assertTrue(dm.getRaeume().contains(raum2));
		dm.loescheRaum(raum2);
		assertFalse(dm.getRaeume().contains(raum2));
	}

	/**
	 * Testet das Hinzufuegen einer ProbenKategorie zur Datenbasis
	 */
	@Test
	public void testProbenKategorieHinzufuegen() {
		DataManagement dm = DataManagement.getInstance();

		ProbenKategorie probenKategorie = new ProbenKategorie("kategorie");
		dm.speichereProbenKategorie(probenKategorie);

		assertTrue(dm.getProbenKategorien().contains(probenKategorie));
	}

	/**
	 * Testet das Hinzufuegen und Loeschen einer ProbenKategorie aus der Datenbasis
	 */
	@Test
	public void testProbenKategorieHinzufuegenUndLoeschen() {
		DataManagement dm = DataManagement.getInstance();

		ProbenKategorie probenKategorie = new ProbenKategorie("kategorie");
		dm.speichereProbenKategorie(probenKategorie);
		dm.loescheProbenKategorie(probenKategorie);

		assertEquals(0, dm.getProbenKategorien().size());
	}

	/**
	 * Testet das Hinzufuegen eines Probenbehaeltertypen zur Datenbasis
	 */
	@Test
	public void testProbenBehaelterTypHinzufuegen() {
		DataManagement dm = DataManagement.getInstance();

		ProbenBehaelterTyp typ = new ProbenBehaelterTyp("typ", 1, 1, 1, "deckel1");
		dm.speichereProbenBehaelterTyp(typ);

		assertTrue(dm.getProbenBehaelterTypen().contains(typ));
	}

	/**
	 * Testet das Hinzufuegen und Loeschen eines ProbenBehaelterTyps aus der
	 * Datenbasis
	 */
	@Test
	public void testProbenBehaelterTypHinzufuegenUndLoeschen() {
		ProbenBehaelterTyp typ = new ProbenBehaelterTyp("typ1", 1, 1, 1, "deckel1");
		DataManagement dm = DataManagement.getInstance();
		dm.speichereProbenBehaelterTyp(typ);
		dm.loescheProbenBehaelterTyp(typ);
		assertFalse(dm.getProbenBehaelterTypen().contains(typ));
	}

	/**
	 * Testet das Hinzufuegen eines Patienten zur Datenbasis
	 */
	@Test
	public void testPatientHinzufuegen() {
		DataManagement dm = DataManagement.getInstance();

		Patient patient = new Patient(1, "Mueller", "Gerd");
		dm.speicherePatient(patient);

		assertTrue(dm.getPatienten().contains(patient));
	}

	/**
	 * Testet das Hinzufuegen und Loeschen eines Patienten aus der Datenbasis
	 */
	@Test
	public void testPatientHinzufuegenUndLoeschen() {
		Patient patient = new Patient(2, "Meyer", "Hans");
		DataManagement dm = DataManagement.getInstance();
		dm.speicherePatient(patient);
		dm.loeschePatient(patient);
		assertFalse(dm.getPatienten().contains(patient));
	}

	/**
	 * Testet das Hinzufuegen einer Studie aus der Datenbasis
	 */
	@Test
	public void testStudieHinzufuegen() {
		DataManagement dm = DataManagement.getInstance();

		Studie studie = new Studie("studie1", 10);
		dm.speichereStudie(studie);

		assertEquals(studie, dm.getStudien().get(0));
	}

	/**
	 * Testet das Hinzufuegen und Loeschen einer Studie aus der Datenbasis
	 */
	@Test
	public void testStudieHinzufuegenUndLoeschen() {
		DataManagement dm = DataManagement.getInstance();

		Studie studie = new Studie("studie1", 10);
		dm.speichereStudie(studie);
		dm.loescheStudie(studie);

		assertEquals(0, dm.getStudien().size());
	}
}
