package de.wwu.sopra.steuerung.studynurse;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;

import org.junit.jupiter.api.Test;

import de.wwu.sopra.datenhaltung.DataFilter;
import de.wwu.sopra.datenhaltung.DataManagement;
import de.wwu.sopra.model.Benutzer;
import de.wwu.sopra.model.Patient;
import de.wwu.sopra.model.Studie;
import de.wwu.sopra.model.Visite;
import de.wwu.sopra.steuerung.alleuser.AuthentifizierungSteuerung;
import de.wwu.sopra.steuerung.biobankleiter.StudienSteuerung;
import de.wwu.sopra.steuerung.biobankleiter.VisitenvorlagenSteuerung;
import de.wwu.sopra.steuerung.personalleiter.BenutzerSteuerung;
import de.wwu.sopra.steuerung.studynurse.PatientenSteuerung;
import javafx.util.Pair;

/**
 * Die Klasse PatientenSteuerungTest testet die Klasse PatientenStuerung
 * 
 * @author pia, Jasmin, Cedric
 *
 */

public class PatientenSteuerungTest {
	/**
	 * Testet die Methode mit dem 29.Feb im Schaltjahr
	 * 
	 * @throws ParseException Fehler beim Parsen einer Variable.
	 */
	@Test
	public void testAddiereDatum() throws ParseException {
		SimpleDateFormat sdf = new SimpleDateFormat("dd.MM.yyyy");
		Date datum = sdf.parse("23.01.2020");
		int zeitZurLetztenVisite = 5;
		Date datumNeu = sdf.parse("28.01.2020");
		PatientenSteuerung patientenSteuerung = new PatientenSteuerung();
		assertTrue(patientenSteuerung.addiereTageAufDatum(datum, zeitZurLetztenVisite).compareTo(datumNeu) == 0);
	}

	/**
	 * Die Methode testPatientHinzufuegen testet die Generierung der VisitenID,
	 * gleichzeitig testet sie das speichern, Aufrufen und hinzufuegen von
	 * Patienten.
	 */
	@Test
	public void testPatientHinzufuegenRegelfall() {
		DataManagement.reset();
		// Studie hinzufuegen
		StudienSteuerung stuSteu = new StudienSteuerung();
		stuSteu.studieHinzufuegen("Studie1", "40");
		Studie studie = stuSteu.getStudien("Studie1", "40").get(0);
		Pair<Studie, String> studieMitDatum = new Pair<Studie, String>(studie, "25.1.2021");
		ArrayList<Pair<Studie, String>> studien = new ArrayList<>();
		studien.add(studieMitDatum);

		// Visitenvorlage hinzufuegen
		VisitenvorlagenSteuerung steuerung = new VisitenvorlagenSteuerung();
		steuerung.visitenvorlageHinzufuegen(studie, "visitenvorlage1", "Blutdruck,Puls", "0");
		steuerung.visitenvorlageHinzufuegen(studie, "visitenvorlage2", "Blutdruck, Blutzucker", "14");

		// Patient hinzufuegen
		PatientenSteuerung steuerung2 = new PatientenSteuerung();
		steuerung2.patientHinzufuegen("Hendrik", "Meier", "Siekergasse33", studien);

		ArrayList<Patient> patient = DataFilter.filterPatienten("Meier", "Hendrik", null, null);
		Patient patientKonkret = patient.get(0);
		assertNotEquals(patientKonkret.getVisiten().get(0).getVisitenId(),
				patientKonkret.getVisiten().get(1).getVisitenId());
	}

	/**
	 * Dieser Test prueft, ob der Patient nach dem loeschen nicht mehr gefunden
	 * werden kann
	 */

	@Test
	public void testPatientLoeschenRegelfall() {
		DataManagement.reset();
		Studie studie = new Studie("Studie1", 40);
		VisitenvorlagenSteuerung steuerung = new VisitenvorlagenSteuerung();
		steuerung.visitenvorlageHinzufuegen(studie, "visitenvorlage1", "Blutdruck,Puls", "0");
		steuerung.visitenvorlageHinzufuegen(studie, "visitenvorlage2", "Blutdruck, Blutzucker", "14");
		PatientenSteuerung steuerung2 = new PatientenSteuerung();
		Pair<Studie, String> studieMitDatum = new Pair<Studie, String>(studie, "25.1.2021");
		ArrayList<Pair<Studie, String>> studien = new ArrayList<>();
		studien.add(studieMitDatum);
		steuerung2.patientHinzufuegen("Hendrik", "Meier", "Siekergasse33", studien);

		steuerung2.patientLoeschen("Hendrik", "Meier", "Siekergasse33");
		assertTrue(DataFilter.filterPatienten("Meier", "Hendrik", null, null).size() == 0);
	}

	/**
	 * Dieser Test prueft, ob auch die Visiten eines Patienten in der entsprechenden
	 * Studie entfernt werden, wenn dieser Patient geloescht wird
	 */

	@Test
	public void testPatientLoeschenVisiten() {
		DataManagement.reset();

		Studie studie = new Studie("Studie1", 40);
		VisitenvorlagenSteuerung steuerung = new VisitenvorlagenSteuerung();
		steuerung.visitenvorlageHinzufuegen(studie, "visitenvorlage1", "Blutdruck,Puls", "0");
		steuerung.visitenvorlageHinzufuegen(studie, "visitenvorlage2", "Blutdruck, Blutzucker", "14");
		PatientenSteuerung steuerung2 = new PatientenSteuerung();
		Pair<Studie, String> studieMitDatum = new Pair<Studie, String>(studie, "25.1.2021");
		ArrayList<Pair<Studie, String>> studien = new ArrayList<>();
		studien.add(studieMitDatum);
		steuerung2.patientHinzufuegen("Hendrik", "Meier", "Siekergasse33", studien);

		steuerung2.patientLoeschen("Hendrik", "Meier", "Siekergasse33");

		ArrayList<Visite> visiten = DataFilter.filterStudien("Studie1", 40, null, null).get(0).getVisiten();

		boolean geloescht = true;

		for (int i = 0; i < visiten.size(); i++) {
			if ((visiten.get(i).getPatient().getVorname().equals("Hendrik")
					&& visiten.get(i).getPatient().getNachname().equals("Meier")
					&& visiten.get(i).getPatient().getAdresse().equals("Siekergasse33"))) {
				geloescht = false;

			}

		}
		assertTrue(geloescht);
	}

	/**
	 * Dieser Test kontrolliert, ob die Methode eine Fehlermeldung anzeigt, falls
	 * ein Patient geloescht werden soll, welcher falsch eingeben wurde oder nicht
	 * existiert
	 */
	@Test
	public void testPatientLoeschenUngueltigeEingabe() {
		DataManagement.reset();

		Studie studie = new Studie("Studie1", 40);
		VisitenvorlagenSteuerung steuerung = new VisitenvorlagenSteuerung();
		steuerung.visitenvorlageHinzufuegen(studie, "visitenvorlage1", "Blutdruck,Puls", "0");
		steuerung.visitenvorlageHinzufuegen(studie, "visitenvorlage2", "Blutdruck, Blutzucker", "14");
		PatientenSteuerung steuerung2 = new PatientenSteuerung();
		Pair<Studie, String> studieMitDatum = new Pair<Studie, String>(studie, "25.1.2021");
		ArrayList<Pair<Studie, String>> studien = new ArrayList<>();
		studien.add(studieMitDatum);
		steuerung2.patientHinzufuegen("Hendrik", "Meier", "Siekergasse33", studien);

		assertThrows(IllegalArgumentException.class, () -> {
			steuerung2.patientLoeschen("Herik", "Meier", "Siekergasse33");
		});

	}

	/**
	 * Die Testmetheode testPAtientBearbeiten testet die Methode patientBearbeiten
	 * und schaut ob die Aenderung des Vornamens tatsaechlich uebernommen wurden.
	 */
	@Test
	public void testPatientBearbeiten() {
		DataManagement.reset();
		Studie studie = new Studie("Studie1", 40);
		VisitenvorlagenSteuerung steuerung = new VisitenvorlagenSteuerung();
		steuerung.visitenvorlageHinzufuegen(studie, "visitenvorlage1", "Blutdruck,Puls", "0");
		steuerung.visitenvorlageHinzufuegen(studie, "visitenvorlage2", "Blutdruck, Blutzucker", "14");
		PatientenSteuerung steuerung2 = new PatientenSteuerung();
		Pair<Studie, String> studieMitDatum = new Pair<Studie, String>(studie, "25.1.2021");
		ArrayList<Pair<Studie, String>> studien = new ArrayList<>();
		studien.add(studieMitDatum);
		steuerung2.patientHinzufuegen("Hendrik", "Meier", "Siekergasse33", studien);
		ArrayList<Patient> patienten = DataFilter.filterPatienten("Meier", "Hendrik", null, null);
		Patient patient = patienten.get(0);
		steuerung2.patientBearbeiten(patient, "Hugo", "Meier", "Siekergasse32", studien);
		assertNotNull(DataFilter.filterPatienten("Meier", "Hugo", null, null).get(0));
	}

	/**
	 * Die Testmethode PatientBearbeiten testet, ob der Patient wie er vor der
	 * Bearbeitung war auch nach der Bearbeitung so nichtmehr existiert
	 */
	@Test
	public void testPatientBearbeitenVorherigerNull() {
		DataManagement.reset();
		Studie studie = new Studie("Studie1", 40);
		VisitenvorlagenSteuerung steuerung = new VisitenvorlagenSteuerung();
		steuerung.visitenvorlageHinzufuegen(studie, "visitenvorlage1", "Blutdruck,Puls", "0");
		steuerung.visitenvorlageHinzufuegen(studie, "visitenvorlage2", "Blutdruck, Blutzucker", "14");
		PatientenSteuerung steuerung2 = new PatientenSteuerung();
		Pair<Studie, String> studieMitDatum = new Pair<Studie, String>(studie, "25.1.2021");
		ArrayList<Pair<Studie, String>> studien = new ArrayList<>();
		studien.add(studieMitDatum);
		steuerung2.patientHinzufuegen("Hendrik", "Meier", "Siekergasse33", studien);
		ArrayList<Patient> patienten = DataFilter.filterPatienten("Meier", "Hendrik", null, null);
		Patient patient = patienten.get(0);
		steuerung2.patientBearbeiten(patient, "Hugo", "Meier", "Siekergasse32", studien);
		assertTrue(DataFilter.filterPatienten("Meier", "Hendrik", null, null).size() == 0);
	}

	/**
	 * Die Testmethode testet die Methode PatientBearbeiten und tetstet, ob nachdem
	 * dem Patienten eine weiteren Visite hinzugefuegt wurde auch Visiten zu dieser
	 * Studie bei dem Patienten angelegt wurden.
	 */
	@Test
	public void testPatientBearbeitenVisitenZuStudienaenerungPassend() {
		DataManagement.reset();

		// Studie 1 erstellen
		StudienSteuerung stuSteu = new StudienSteuerung();
		stuSteu.studieHinzufuegen("Studie1", "40");
		Studie studie = stuSteu.getStudien("Studie1", "40").get(0);
		Pair<Studie, String> studieMitDatum = new Pair<Studie, String>(studie, "25.1.2021");
		ArrayList<Pair<Studie, String>> studien = new ArrayList<>();
		studien.add(studieMitDatum);

		// Visitenvorlage erstellen
		VisitenvorlagenSteuerung visitenVorlageSteuerung = new VisitenvorlagenSteuerung();
		visitenVorlageSteuerung.visitenvorlageHinzufuegen(studie, "visitenvorlage1", "Blutdruck,Puls", "0");
		visitenVorlageSteuerung.visitenvorlageHinzufuegen(studie, "visitenvorlage2", "Blutdruck, Blutzucker", "14");

		// Patient hinzufuegen
		PatientenSteuerung steuerung2 = new PatientenSteuerung();
		steuerung2.patientHinzufuegen("Hendrik", "Meier", "Siekergasse33", studien);
		ArrayList<Patient> patienten = DataFilter.filterPatienten("Meier", "Hendrik", null, null);
		Patient patient = patienten.get(0);

		// Studie 2 erstellen und Studie nachtraeglich hinzufuegen
		stuSteu.studieHinzufuegen("Studie2", "50");
		Studie studie2 = stuSteu.getStudien("Studie2", "50").get(0);
		Pair<Studie, String> studieMitDatum2 = new Pair<Studie, String>(studie2, "25.3.2021");
		studien.add(studieMitDatum2);

		visitenVorlageSteuerung.visitenvorlageHinzufuegen(studie2, "ErsteVisite", "Blutdruck, Gewicht", "0");
		visitenVorlageSteuerung.visitenvorlageHinzufuegen(studie2, "Zweite", "Gewicht", "14");

		steuerung2.patientBearbeiten(patient, "Hendrik", "Meier", "Siekergasse33", studien);

		ArrayList<Visite> visiten = DataFilter.filterStudien("Studie2", 50, null, null).get(0).getVisiten();

		boolean geloescht = false;

		for (int i = 0; i < visiten.size(); i++) {
			if ((visiten.get(i).getPatient().getVorname().equals("Hendrik")
					&& visiten.get(i).getPatient().getNachname().equals("Meier")
					&& visiten.get(i).getPatient().getAdresse().equals("Siekergasse33"))) {
				geloescht = true;

			}

		}
		assertTrue(geloescht);
	}

	/**
	 * testet, ob das Loeschen von Patienten bezueglich einer Studie 
	 * funktioniert.
	 */
	@Test
	public void testLoescheStudievonPatient() {
		DataManagement.reset();

		// Studie anlegen
		StudienSteuerung stuSteu = new StudienSteuerung();
		stuSteu.studieHinzufuegen("Studie1", "40");
		Studie studie = stuSteu.getStudien("Studie1", "40").get(0);
		Pair<Studie, String> studieMitDatum = new Pair<Studie, String>(studie, "25.1.2021");
		ArrayList<Pair<Studie, String>> studien = new ArrayList<>();
		studien.add(studieMitDatum);

		// Visitenvorlage anlegen
		VisitenvorlagenSteuerung steuerung = new VisitenvorlagenSteuerung();
		steuerung.visitenvorlageHinzufuegen(studie, "visitenvorlage1", "Blutdruck,Puls", "0");
		steuerung.visitenvorlageHinzufuegen(studie, "visitenvorlage2", "Blutdruck, Blutzucker", "14");

		// Patient anlegen
		PatientenSteuerung steuerung2 = new PatientenSteuerung();
		steuerung2.patientHinzufuegen("Hendrik", "Meier", "Siekergasse33", studien);
		Patient patient = DataFilter.filterPatienten("Meier", "Hendrik", null, null).get(0);

		ArrayList<Studie> studienList = new ArrayList<Studie>();
		studienList.add(studie);

		steuerung2.loescheStudievonPatient(patient, studie);
		assertEquals(0, DataFilter.filterPatienten("", "", studienList, null).size());
	}

	/**
	 * testet, ob nur Patienten gemaess des Filters angezeigt werden
	 * 
	 * @throws NoSuchAlgorithmException Fehler, wenn genutzter Algorithmus nicht existiert.
	 * @throws InvalidKeySpecException Fehler fuer ungueltige Key-Spezifikation.
	 */
	@Test
	public void testGetPatientenFilter() throws NoSuchAlgorithmException, InvalidKeySpecException {

		DataManagement.reset();
		// Studien erstellen
		StudienSteuerung studienSteuerung = new StudienSteuerung();
		studienSteuerung.studieHinzufuegen("Studie1", "40");
		studienSteuerung.studieHinzufuegen("Studie2", "50");
		Studie studie = studienSteuerung.getStudien("Studie1", "40").get(0);
		Studie studie2 = studienSteuerung.getStudien("Studie2", "50").get(0);

		// Studien mit Datum zusammenfuegen
		Pair<Studie, String> studieMitDatum = new Pair<Studie, String>(studie, "25.1.2021");
		Pair<Studie, String> studieMitDatum2 = new Pair<Studie, String>(studie2, "25.1.2022");
		ArrayList<Pair<Studie, String>> studien = new ArrayList<>();
		studien.add(studieMitDatum);
		ArrayList<Pair<Studie, String>> studien2 = new ArrayList<>();
		studien2.add(studieMitDatum2);

		// Visitenvorlagen hinzufuegen
		VisitenvorlagenSteuerung steuerung = new VisitenvorlagenSteuerung();
		steuerung.visitenvorlageHinzufuegen(studie, "visitenvorlage1", "Blutdruck,Puls", "0");
		steuerung.visitenvorlageHinzufuegen(studie2, "visitenvorlage2", "Blutdruck, Blutzucker", "14");

		// Benutzer erstellen und anmelden
		AuthentifizierungSteuerung authentifizierungSteuerung = AuthentifizierungSteuerung.getInstance();
		BenutzerSteuerung benutzerSteuerung = new BenutzerSteuerung();
		Pair<String, String> passwort = benutzerSteuerung.benutzerHinzufuegen("Max", "Mustermann", true, true, true,
				true);
		authentifizierungSteuerung.anmelden(passwort.getKey(), passwort.getValue());

		// Patienten hinzufuegen
		PatientenSteuerung patientenSteuerung = new PatientenSteuerung();
		patientenSteuerung.patientHinzufuegen("Hendrik", "Meier", "Siekergasse33", studien);
		patientenSteuerung.patientHinzufuegen("Max", "Mustermann", "Schlossallee 42", studien2);

		assertEquals(1, patientenSteuerung.getPatienten("", "", "2").size());
		assertEquals(2, DataManagement.getInstance().getPatienten().size());
	}

	/**
	 * testet getStudienMitDatum Normalfall
	 * 
	 * @throws NoSuchAlgorithmException Fehler, wenn genutzter Algorithmus nicht existiert.
	 * @throws InvalidKeySpecException Fehler fuer ungueltige Key-Spezifikation.
	 * @throws IllegalArgumentException Fehler fuer ein ungueltiges Argument.
	 */

	@Test
	public void getStudienMitDatum()
			throws NoSuchAlgorithmException, InvalidKeySpecException, IllegalArgumentException {
		DataManagement.reset();
		// Studien anlegen
		StudienSteuerung stuSteu = new StudienSteuerung();
		stuSteu.studieHinzufuegen("Studie1", "40");
		stuSteu.studieHinzufuegen("Studie2", "50");
		Studie studie = stuSteu.getStudien("Studie1", "40").get(0);
		Studie studie2 = stuSteu.getStudien("Studie2", "50").get(0);
		Pair<Studie, String> studieMitDatum = new Pair<Studie, String>(studie, "25.01.2021");
		Pair<Studie, String> studieMitDatum2 = new Pair<Studie, String>(studie2, "12.05.2022");
		ArrayList<Pair<Studie, String>> studien = new ArrayList<>();
		studien.add(studieMitDatum);
		studien.add(studieMitDatum2);

		// Visitenvorlagen anlegen
		VisitenvorlagenSteuerung steuerung = new VisitenvorlagenSteuerung();
		steuerung.visitenvorlageHinzufuegen(studie, "visitenvorlage1", "Blutdruck,Puls", "0");
		steuerung.visitenvorlageHinzufuegen(studie2, "visitenvorlage2", "Blutdruck, Blutzucker", "8");

		// Benutzer erstellen
		AuthentifizierungSteuerung authentifizierungSteuerung = AuthentifizierungSteuerung.getInstance();
		BenutzerSteuerung benutzerSteuerung = new BenutzerSteuerung();
		Pair<String, String> passwort = benutzerSteuerung.benutzerHinzufuegen("Max", "Mustermann", true, true, true,
				true);
		authentifizierungSteuerung.anmelden(passwort.getKey(), passwort.getValue());

		// Patient erstellen
		PatientenSteuerung pSteuerung = new PatientenSteuerung();
		Patient neu = pSteuerung.patientHinzufuegen("Hendrik", "Meier", "Siekergasse33", studien);

		ArrayList<Pair<Studie, String>> erg = pSteuerung.getStudienMitDatum(neu);
		assertEquals(studien.get(0).getKey(), erg.get(0).getKey());
	}

}
