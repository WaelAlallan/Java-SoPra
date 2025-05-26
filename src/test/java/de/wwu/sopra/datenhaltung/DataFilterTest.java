package de.wwu.sopra.datenhaltung;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Date;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import de.wwu.sopra.datenhaltung.DataFilter;
import de.wwu.sopra.datenhaltung.DataManagement;
import de.wwu.sopra.model.Benutzer;
import de.wwu.sopra.model.Kuehlschrank;
import de.wwu.sopra.model.Patient;
import de.wwu.sopra.model.ProbenBehaelterTyp;
import de.wwu.sopra.model.ProbenKategorie;
import de.wwu.sopra.model.Raum;
import de.wwu.sopra.model.Studie;
import de.wwu.sopra.model.Visite;
import de.wwu.sopra.model.VisitenVorlage;
import jdk.jfr.DataAmount;

/**
 * Klasse zum Testen der Filtermethoden.
 * @author Gruppe 1
 *
 */
public class DataFilterTest {

	/**
	 * Vor jedem Test soll die Datenbasis zurueckgesetzt werden
	 */
	@BeforeEach
	public void resetDataManagement() {
		DataManagement.reset();
	}

	/**
	 * Testet, ob die filterBenutzer Methode mit keinen ausgewaehlten Filtern die
	 * Benutzer zurueckgibt, die sie zurueckgeben soll.
	 */
	@Test
	public void testBenutzerUnfiltered() {
		DataManagement dm = DataManagement.getInstance();

		Benutzer benutzer1 = new Benutzer("C3", "D3", null);
		benutzer1.setBenutzername("M3");
		Benutzer benutzer2 = new Benutzer("N5", "V5", null);
		benutzer2.setBenutzername("B5");
		dm.speichereBenutzer(benutzer1);
		dm.speichereBenutzer(benutzer2);

		assertTrue(DataFilter.filterBenutzer("", "", "").contains(benutzer1));
		assertTrue(DataFilter.filterBenutzer("", "", "").contains(benutzer2));
	}

	/**
	 * Testet, ob die filterBenutzer Methode mit teilweise ausgewaehlten Filtern die
	 * Benutzer zurueckgibt, die sie zurueckgeben soll.
	 */
	@Test
	public void testBenutzerSemifiltered() {
		DataManagement dm = DataManagement.getInstance();

		Benutzer benutzer1 = new Benutzer("C3", "D3", null);
		benutzer1.setBenutzername("M3");
		Benutzer benutzer2 = new Benutzer("N5", "V5", null);
		benutzer2.setBenutzername("B5");
		Benutzer benutzer3 = new Benutzer("N3", "V5", null);
		dm.speichereBenutzer(benutzer1);
		dm.speichereBenutzer(benutzer2);
		dm.speichereBenutzer(benutzer3);

		assertTrue(DataFilter.filterBenutzer("", "D3", "").contains(benutzer1));
		assertTrue(DataFilter.filterBenutzer("N5", "", "").contains(benutzer2));
		assertTrue(DataFilter.filterBenutzer("", "V5", "").contains(benutzer3));
	}

	/**
	 * Testet, ob die filterBenutzer Methode mit allen moeglichen Filtern die
	 * Benutzer zurueckgibt, die sie zurueckgeben soll.
	 */
	@Test
	public void testBenutzerFullfiltered() {
		DataManagement dm = DataManagement.getInstance();

		Benutzer benutzer1 = new Benutzer("C3", "D3", null);
		benutzer1.setBenutzername("M3");
		dm.speichereBenutzer(benutzer1);

		Benutzer benutzer2 = new Benutzer("N5", "V5", null);
		benutzer2.setBenutzername("B5");
		dm.speichereBenutzer(benutzer2);

		assertTrue(DataFilter.filterBenutzer("N5", "V5", "B5").contains(benutzer2));
	}

	/**
	 * Testet, ob die filterRaeume Methode keinen Filtern die Raeume zurueckgibt,
	 * die sie zurueckgeben soll.
	 */
	@Test
	public void testRaeumeUnfiltered() {
		DataManagement dm = DataManagement.getInstance();

		Raum raum1 = new Raum("R1", "SRZ1");
		dm.speichereRaum(raum1);

		Raum raum2 = new Raum("R2", "SRZ2");
		dm.speichereRaum(raum2);

		assertTrue(DataFilter.filterRaeume("", "", null).contains(raum1));
		assertTrue(DataFilter.filterRaeume("", "", null).contains(raum2));
	}

	/**
	 * Testet, ob die filterRaeume Methode mit teilweise ausgewaehlten Filtern die
	 * Raeume zurueckgibt, die sie zurueckgeben soll.
	 */
	@Test
	public void testRaeumeSemifiltered() {
		DataManagement dm = DataManagement.getInstance();

		Raum raum1 = new Raum("R1", "SRZ1");
		raum1.setEingestellteTemperatur(22.0);
		dm.speichereRaum(raum1);

		Raum raum2 = new Raum("R2", "SRZ2");
		dm.speichereRaum(raum2);

		Raum raum3 = new Raum("R7", "");
		dm.speichereRaum(raum3);

		assertEquals(raum1, DataFilter.filterRaeume("", "", 22.0).get(0));
		assertEquals(raum2, DataFilter.filterRaeume("", "SRZ2", null).get(0));
		assertEquals(raum3, DataFilter.filterRaeume("R7", "", null).get(0));
	}

	/**
	 * Testet, ob die filterRaeume Methode mit allen moeglichen Filtern die Raeume
	 * zurueckgibt, die sie zurueckgeben soll.
	 */
	@Test
	public void testRaeumeFullfiltered() {
		DataManagement dm = DataManagement.getInstance();

		Raum raum1 = new Raum("R1", "SRZ1");
		raum1.setEingestellteTemperatur(22.0);
		dm.speichereRaum(raum1);

		Raum raum2 = new Raum("R2", "SRZ2");
		dm.speichereRaum(raum2);

		assertEquals(raum1, DataFilter.filterRaeume("R1", "SRZ1", 22.0).get(0));
	}

	/**
	 * Testet, ob die ungefilterte getKuehlschraenke Methode alle Kuehlschraenke
	 * zurueck gibt, die sie zurueckgeben soll.
	 */
	@Test
	public void testKuehlschraenkeUnfiltered() {
		DataManagement dm = DataManagement.getInstance();

		ArrayList<Kuehlschrank> temp = new ArrayList<>();

		Raum raum1 = new Raum("R100", "SRZ2");
		dm.speichereRaum(raum1);

		Kuehlschrank kuehlschrank1 = new Kuehlschrank("K1", 23.2, raum1);
		temp.add(kuehlschrank1);

		Raum raum2 = new Raum("R200", "SAA1");
		dm.speichereRaum(raum2);

		Kuehlschrank kuehlschrank2 = new Kuehlschrank("K2", 24.1, raum2);
		temp.add(kuehlschrank2);

		assertEquals(temp, DataFilter.filterKuehlschraenke("", null, null));
	}

	/**
	 * Testet, ob die getKuehlschraenke Methode mit teilweise ausgewaehlten Filtern
	 * die Kuehlschraenke zurueckgibt, die sie zurueckgeben soll.
	 */
	@Test
	public void testKuehlschraenkeSemifiltered() {
		DataManagement dm = DataManagement.getInstance();

		ArrayList<Kuehlschrank> temp = new ArrayList<>();

		Raum raum1 = new Raum("R100", "SRZ2");
		dm.speichereRaum(raum1);
		Kuehlschrank kuehlschrank1 = new Kuehlschrank("K1", 23.2, raum1);

		Raum raum2 = new Raum("R200", "SAA1");
		Kuehlschrank kuehlschrank2 = new Kuehlschrank("K2", 24.1, raum2);
		dm.speichereRaum(raum2);
		temp.add(kuehlschrank2);

		assertEquals(temp, DataFilter.filterKuehlschraenke("K2", null, raum2));
	}

	/**
	 * Testet, ob die getKuehlschraenke Methode mit allen ausgewaehlten Filtern die
	 * Kuehlschraenke zurueckgibt, die sie zurueckgeben soll.
	 */
	@Test
	public void testKuehlschraenkeFullfiltered() {
		ArrayList<Kuehlschrank> temp = new ArrayList<>();
		DataManagement dm = DataManagement.getInstance();

		Raum raum1 = new Raum("R100", "SRZ2");
		Kuehlschrank kuehlschrank1 = new Kuehlschrank("K1", 23.2, raum1);
		dm.speichereRaum(raum1);

		Raum raum2 = new Raum("R200", "SAA1");
		Kuehlschrank kuehlschrank2 = new Kuehlschrank("K2", 24.1, raum2);
		dm.speichereRaum(raum2);
		temp.add(kuehlschrank2);

		assertEquals(temp, DataFilter.filterKuehlschraenke("K2", 24.1, raum2));
	}

	/**
	 * Testet, ob die filterProbenKategorien Methode mit keinen ausgewaehlten
	 * Argumenten die richtigen ProbenKategorien zurueckgibt.
	 */
	@Test
	public void testProbenKategorienUnfiltered() {
		DataManagement dm = DataManagement.getInstance();

		ArrayList<ProbenKategorie> temp = new ArrayList<ProbenKategorie>();
		ProbenKategorie pk1 = new ProbenKategorie("Speichel");
		ProbenKategorie pk2 = new ProbenKategorie("Blut");
		dm.speichereProbenKategorie(pk1);
		dm.speichereProbenKategorie(pk2);
		temp.add(pk1);
		temp.add(pk2);

		assertArrayEquals(temp.toArray(), DataFilter.filterProbenKategorien("").toArray());
	}

	/**
	 * Testet, ob die filterProbenKategorien Methode mit allen ausgewaehlten
	 * Argumenten die richtigen ProbenKategorien zurueckgibt.
	 */
	@Test
	public void testProbenKategorienFullfiltered() {
		DataManagement dm = DataManagement.getInstance();

		ArrayList<ProbenKategorie> temp = new ArrayList<ProbenKategorie>();
		ProbenKategorie pk1 = new ProbenKategorie("Speichel");
		ProbenKategorie pk2 = new ProbenKategorie("Blut");
		dm.speichereProbenKategorie(pk1);
		dm.speichereProbenKategorie(pk2);
		temp.add(pk1);
		temp.add(pk2);

		assertEquals(pk2, DataFilter.filterProbenKategorien("Blut").get(0));
	}

	/**
	 * Testet, ob die filterProbenBehaelterTypen Methode mit keinen ausgewaehlten
	 * Argumenten die richtigen ProbenBehaelterTypen zurueckgibt.
	 */
	@Test
	public void testProbenBehaelterTypenUnfiltered() {
		DataManagement dm = DataManagement.getInstance();
		ProbenBehaelterTyp typ1 = new ProbenBehaelterTyp("typ2", 1, 1, 1, "deckel2");
		dm.speichereProbenBehaelterTyp(typ1);
		ProbenBehaelterTyp typ2 = new ProbenBehaelterTyp("typ5", 3, 3, 3, "deckel5");
		dm.speichereProbenBehaelterTyp(typ2);

		assertTrue(DataFilter.filterProbenBehaelterTypen("", null, null, null, "").contains(typ1));
		assertTrue(DataFilter.filterProbenBehaelterTypen("", null, null, null, "").contains(typ2));
	}

	/**
	 * Testet, ob die filterProbenBehaelterTypen Methode mit teilweise ausgewaehlten
	 * Argumenten die richtigen ProbenBehaelterTypen zurueckgibt.
	 */
	@Test
	public void testProbenBehaelterTypenSemifiltered() {
		DataManagement dm = DataManagement.getInstance();

		ProbenBehaelterTyp typ1 = new ProbenBehaelterTyp("typ3", 1, 1, 1, "deckel3");
		dm.speichereProbenBehaelterTyp(typ1);
		ProbenBehaelterTyp typ2 = new ProbenBehaelterTyp("typ5", 3, 3, 3, "deckel5");
		dm.speichereProbenBehaelterTyp(typ2);

		assertEquals(typ1, DataFilter.filterProbenBehaelterTypen("typ3", 1.0, null, null, "").get(0));
	}

	/**
	 * Testet, ob die filterProbenBehaelterTypen Methode mit allen ausgewaehlten
	 * Argumenten die richtigen ProbenBehaelterTypen zurueckgibt.
	 */
	@Test
	public void testProbenBehaelterTypenFullfiltered() {
		DataManagement dm = DataManagement.getInstance();

		ProbenBehaelterTyp typ1 = new ProbenBehaelterTyp("typ4", 1, 1, 1, "deckel4");
		dm.speichereProbenBehaelterTyp(typ1);
		ProbenBehaelterTyp typ2 = new ProbenBehaelterTyp("typ5", 3, 3, 3, "deckel5");
		dm.speichereProbenBehaelterTyp(typ2);

		assertEquals(typ1, DataFilter.filterProbenBehaelterTypen("typ4", 1.0, 1.0, 1.0, "deckel4").get(0));
	}

	/**
	 * Testet, ob die filterPatienten Methode mit keinen ausgewaehlten Argumenten
	 * die richtigen Patienten zurueckgibt.
	 */
	@Test
	public void testPatientenUnfiltered() {
		DataManagement dm = DataManagement.getInstance();

		Patient patient = new Patient(1, "Mueller", "Gerd");
		dm.speicherePatient(patient);

		assertEquals(DataFilter.filterPatienten("", "", null, null).get(0), patient);
	}

	/**
	 * Testet, ob die filterPatienten Methode mit teilweise ausgewaehlten Argumenten
	 * die richtigen Patienten zurueckgibt.
	 */
	@Test
	public void testPatientenSemifiltered() {
		DataManagement dm = DataManagement.getInstance();

		ArrayList<Studie> tempStudie = new ArrayList<Studie>();

		ArrayList<String> medData = new ArrayList<String>();
		medData.add("SRS");
		Studie studie1 = new Studie("Teststudie1", 2);
		Patient patient1 = new Patient(123, "Wendler", "Michael");
		VisitenVorlage visitenvorlage1 = new VisitenVorlage("vorlage1", medData, 3);
		Benutzer benutzer = new Benutzer("Bushido", "Sido", null);

		Visite visite1 = new Visite(null, 2, benutzer, patient1, studie1, visitenvorlage1);
		tempStudie.add(studie1);
		dm.speichereStudie(studie1);
		dm.speicherePatient(patient1);

		assertEquals(patient1, DataFilter.filterPatienten("Wendler", "", null, null).get(0));
		assertEquals(patient1, DataFilter.filterPatienten("", "Michael", null, null).get(0));
		assertEquals(patient1, DataFilter.filterPatienten("", "", tempStudie, null).get(0));
		assertEquals(patient1, DataFilter.filterPatienten("", "", null, benutzer).get(0));

	}

	/**
	 * Testet, ob die filterPatienten Methode mit allen ausgewaehlten Argumenten die
	 * richtigen Patienten zurueckgibt.
	 */
	@Test
	public void testPatientenFullfiltered() {
		DataManagement dm = DataManagement.getInstance();

		Patient patient = new Patient(3, "Schneider", "Dieter");
		dm.speicherePatient(patient);
		Benutzer benutzer = new Benutzer("Mueller", "Nadine", null);
		dm.speichereBenutzer(benutzer);
		Studie studie = new Studie("studie1", 500);

		VisitenVorlage visitenVorlage = new VisitenVorlage("vorlage1", new ArrayList<String>(), 5);
		Visite visite = new Visite(null, 3, benutzer, patient, studie, visitenVorlage);
		dm.speichereStudie(studie);

		ArrayList<Studie> studien = new ArrayList<Studie>();
		studien.add(studie);

		assertEquals(DataFilter.filterPatienten("Schneider", "Dieter", studien, benutzer).get(0), patient);
	}

	/**
	 * testet das Speichern und Filtern einer Studie ohne Parameter
	 */
	@Test
	public void testStudienUnfiltered() {
		DataManagement dm = DataManagement.getInstance();

		Studie studie = new Studie("studie1", 500);
		dm.speichereStudie(studie);
		Benutzer benutzer = new Benutzer("Meier", "Sieglinde", null);
		dm.speichereBenutzer(benutzer);

		VisitenVorlage visitenVorlage = new VisitenVorlage("vorlage1", new ArrayList<String>(), 5);
		Visite visite = new Visite(null, 1, benutzer, null, studie, visitenVorlage);

		assertTrue(DataFilter.filterStudien("", null, null, null).contains(studie));
	}

	/**
	 * testet das Speichern und Filtern einer Studie mit jedem Parameter einzeln
	 * gesetzt
	 */
	@Test
	public void testStudienSemifiltered() {
		DataManagement dm = DataManagement.getInstance();

		Studie studie = new Studie("studie2", 400);
		dm.speichereStudie(studie);

		Benutzer benutzer = new Benutzer("Meier", "Sieglinde", null);
		dm.speichereBenutzer(benutzer);

		VisitenVorlage visitenVorlage = new VisitenVorlage("vorlage1", new ArrayList<String>(), 5);
		Visite visite = new Visite(null, 1, benutzer, null, studie, visitenVorlage);

		assertTrue(DataFilter.filterStudien("studie2", null, null, null).contains(studie));
		assertTrue(DataFilter.filterStudien("", 400, null, null).contains(studie));
		assertTrue(DataFilter.filterStudien("", null, benutzer, null).contains(studie));
	}

	/**
	 * testet das Speichern und Filtern einer Studie mit allen Parametern gesetzt
	 */
	@Test
	public void testStudienFullfiltered() {
		DataManagement dm = DataManagement.getInstance();

		Studie studie = new Studie("studie3", 300);
		dm.speichereStudie(studie);

		Benutzer benutzer = new Benutzer("Meier", "Sieglinde", null);
		dm.speichereBenutzer(benutzer);

		VisitenVorlage visitenVorlage = new VisitenVorlage("vorlage1", new ArrayList<String>(), 5);
		Visite visite = new Visite(null, 1, benutzer, null, studie, visitenVorlage);

		assertTrue(DataFilter.filterStudien("studie3", 300, benutzer, null).contains(studie));
	}

	/**
	 * Testet die FilterVisiten Methode ohne Argumente
	 */
	@Test
	public void testVisitenUnfiltered() {
		Date datum = new Date(2005, 10, 9);
		Benutzer benutzer = new Benutzer("Fischer", "Nadja", null);
		Patient patient = new Patient(1, "Voeller", "Rudi");

		Studie studie = new Studie("studie1", 500);
		ArrayList<Studie> studien = new ArrayList<Studie>();
		studien.add(studie);

		VisitenVorlage visitenVorlage = new VisitenVorlage("vorlage1", new ArrayList<String>(), 5);
		Visite visite = new Visite(datum, 2, benutzer, patient, studie, visitenVorlage);
		DataManagement.getInstance().speichereStudie(studie);

		assertTrue(DataFilter.filterVisiten(null, "", "", null, null, null).contains(visite));
	}

	/**
	 * Testet die FilterVisiten Methode mit einigen Argumenten
	 */
	@Test
	public void testVisitenSemifiltered() {
		Date datum = new Date(2005, 10, 9);
		Benutzer benutzer = new Benutzer("Fischer", "Nadja", null);
		Patient patient = new Patient(1, "Voeller", "Rudi");

		Studie studie = new Studie("studie1", 500);
		ArrayList<Studie> studien = new ArrayList<Studie>();
		studien.add(studie);

		VisitenVorlage visitenVorlage = new VisitenVorlage("vorlage1", new ArrayList<String>(), 5);
		Visite visite = new Visite(datum, 2, benutzer, patient, studie, visitenVorlage);
		DataManagement.getInstance().speichereStudie(studie);

		assertTrue(DataFilter.filterVisiten(benutzer, "", "", null, null, null).contains(visite));
	}

	/**
	 * Testet die FilterVisiten Methode mit allen Argumenten
	 */
	@Test
	public void testVisitenFullfiltered() {
		Date datum = new Date(2005, 10, 9);
		Benutzer benutzer = new Benutzer("Fischer", "Nadja", null);
		DataManagement.getInstance().speichereBenutzer(benutzer);

		Patient patient = new Patient(1, "Voeller", "Rudi");
		Studie studie = new Studie("studie3", 300);
		DataManagement.getInstance().speichereStudie(studie);

		ArrayList<Studie> studien = new ArrayList<Studie>();
		studien.add(studie);

		VisitenVorlage visitenVorlage = new VisitenVorlage("vorlage1", new ArrayList<String>(), 5);
		Visite visite = new Visite(datum, 2, benutzer, patient, studie, visitenVorlage);

		assertTrue(DataFilter.filterStudien("studie3", 300, benutzer, null).contains(studie));
	}

	/**
	 * Testet in der filterVisiten Methode, ob die Visiten nach Datum sortiert
	 * zurueckgegeben werden
	 * 
	 * @throws ParseException Fehler beim Parsen eines Wertes
	 */
	@Test
	public void testVisitenSorted() throws ParseException {
		Benutzer benutzer = new Benutzer("Fischer", "Nadja", null);
		DataManagement.getInstance().speichereBenutzer(benutzer);

		Patient patient = new Patient(1, "Voeller", "Rudi");
		Studie studie = new Studie("studie3", 300);
		DataManagement.getInstance().speichereStudie(studie);

		ArrayList<Studie> studien = new ArrayList<Studie>();
		studien.add(studie);

		// Visiten mit Datum erstellen
		VisitenVorlage visitenVorlage = new VisitenVorlage("vorlage1", new ArrayList<String>(), 5);
		SimpleDateFormat sdf = new SimpleDateFormat("dd.MM.yyyy");
		Date datumAlt = sdf.parse("10.09.2005");
		Date datumMitte = sdf.parse("16.09.2005");
		Date datumNeu = sdf.parse("28.09.2005");
		Visite visiteMitte = new Visite(datumMitte, 2, benutzer, patient, studie, visitenVorlage);
		Visite visiteNeu = new Visite(datumNeu, 2, benutzer, patient, studie, visitenVorlage);
		Visite visiteAlt = new Visite(datumAlt, 2, benutzer, patient, studie, visitenVorlage);

		ArrayList<Visite> visitenSorted = new ArrayList();
		visitenSorted.add(visiteAlt);
		visitenSorted.add(visiteMitte);
		visitenSorted.add(visiteNeu);

		assertEquals(visitenSorted, DataFilter.filterVisiten(null, "", "", null, null, null));
	}

	/**
	 * Testet in der filterVisitenById Methode, ob eine eindeutige Visite
	 * zurueckgegeben wird
	 */
	@Test
	public void filterVisitenById() {
		DataManagement dm = DataManagement.getInstance();

		Studie studie = new Studie("Studie1", 30);
		dm.speichereStudie(studie);
		Patient patient = new Patient(1, "Mueller", "Gerd");
		dm.speicherePatient(patient);
		Benutzer benutzer = new Benutzer("Rummenigge", "Karl-Heinz", null);

		VisitenVorlage visitenVorlage = new VisitenVorlage("visite1", new ArrayList<String>(), 5);
		Visite visite = new Visite(null, 1, benutzer, patient, studie, visitenVorlage);

		assertEquals(visite, DataFilter.filterVisiteById(1));
	}

}
