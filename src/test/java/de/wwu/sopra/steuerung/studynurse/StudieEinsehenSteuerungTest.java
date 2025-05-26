package de.wwu.sopra.steuerung.studynurse;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.util.ArrayList;

import org.junit.jupiter.api.Test;

import de.wwu.sopra.datenhaltung.DataManagement;
import de.wwu.sopra.model.Studie;
import de.wwu.sopra.steuerung.alleuser.AuthentifizierungSteuerung;
import de.wwu.sopra.steuerung.biobankleiter.StudienSteuerung;
import de.wwu.sopra.steuerung.biobankleiter.VisitenvorlagenSteuerung;
import de.wwu.sopra.steuerung.personalleiter.BenutzerSteuerung;
import de.wwu.sopra.steuerung.studynurse.PatientenSteuerung;
import de.wwu.sopra.steuerung.studynurse.StudieEinsehenSteuerung;
import javafx.util.Pair;

/**
 * Testklasse fuer StudieEinsehenSteuerung
 * 
 * @author Jasmin, Pia, Cedric
 *
 */
public class StudieEinsehenSteuerungTest {

	/**
	 * testet getStudien stellt sicher, dass nur zustaendige Study Nurses
	 * entsprechende Studien einsehen koennen
	 * 
	 * @throws NoSuchAlgorithmException Fehler, wenn genutzter Algorithmus nicht existiert.
	 * @throws InvalidKeySpecException Fehler fuer ungueltige Key-Spezifikation.
	 */
	@Test
	public void testGetStudienZustaendig() throws NoSuchAlgorithmException, InvalidKeySpecException {
		// Studien anlegen
		DataManagement.reset();
		StudienSteuerung studienSteuerung = new StudienSteuerung();
		Studie studie = studienSteuerung.studieHinzufuegen("Studie1", "40");
		Studie studie2 = studienSteuerung.studieHinzufuegen("Studie2", "75");
		VisitenvorlagenSteuerung vvSteuerung = new VisitenvorlagenSteuerung();
		vvSteuerung.visitenvorlageHinzufuegen(studie, "visitenvorlage1", "Blutdruck,Puls", "0");
		vvSteuerung.visitenvorlageHinzufuegen(studie, "visitenvorlage2", "Blutdruck, Blutzucker", "14");
		PatientenSteuerung pSteuerung = new PatientenSteuerung();
		Pair<Studie, String> studieMitDatum = new Pair<Studie, String>(studie, "25.1.2021");
		Pair<Studie, String> studieMitDatum2 = new Pair<Studie, String>(studie, "25.1.2022");
		ArrayList<Pair<Studie, String>> studien = new ArrayList<>();
		studien.add(studieMitDatum);

		// Patienten hinzufuegen und so StudyNurses fuer ein Studie zustaendig machen
		AuthentifizierungSteuerung authentifizierungSteuerung = AuthentifizierungSteuerung.getInstance();
		BenutzerSteuerung benutzerSteuerung = new BenutzerSteuerung();
		Pair<String, String> passwort = benutzerSteuerung.benutzerHinzufuegen("Max", "Mustermann", true, true, true,
				true);
		Pair<String, String> passwort2 = benutzerSteuerung.benutzerHinzufuegen("Martina", "Mustermann", true, true,
				true, true);
		authentifizierungSteuerung.anmelden(passwort.getKey(), passwort.getValue());
		pSteuerung.patientHinzufuegen("Hendrik", "Meier", "Siekergasse33", studien);
		authentifizierungSteuerung.setAktiverBenutzer(null);
		authentifizierungSteuerung.anmelden(passwort2.getKey(), passwort2.getValue());
		pSteuerung.patientHinzufuegen("Max", "Mustermann", "Schlossallee 42", studien);

		StudieEinsehenSteuerung seS = new StudieEinsehenSteuerung();
		int anzahlSichtbarerStudien = seS.getStudien("", "").size();
		int erwarteteAnzahlSichtbarerStudien = 1;
		assertEquals(erwarteteAnzahlSichtbarerStudien, anzahlSichtbarerStudien);
		int anzahlGesamtStudien = DataManagement.getInstance().getStudien().size();
		int erwarteteAnzahlGesamtStudien = 2;
		assertEquals(erwarteteAnzahlGesamtStudien, anzahlGesamtStudien);
	}

	/**
	 * testet getStudien stellt sicher, dass die Studieliste gemaess den Filtern
	 * gefiltert wird
	 * 
	 * @throws NoSuchAlgorithmException Fehler, wenn genutzter Algorithmus nicht existiert.
	 * @throws InvalidKeySpecException Fehler fuer ungueltige Key-Spezifikation.
	 */
	@Test
	public void testGetStudienFilter() throws NoSuchAlgorithmException, InvalidKeySpecException {
		// Studien anlegen
		DataManagement.reset();
		StudienSteuerung studienSteuerung = new StudienSteuerung();
		Studie studie = studienSteuerung.studieHinzufuegen("Studie1", "40");
		Studie studie2 = studienSteuerung.studieHinzufuegen("Studie2", "75");
		VisitenvorlagenSteuerung vvSteuerung = new VisitenvorlagenSteuerung();
		vvSteuerung.visitenvorlageHinzufuegen(studie, "visitenvorlage1", "Blutdruck,Puls", "0");
		vvSteuerung.visitenvorlageHinzufuegen(studie, "visitenvorlage2", "Blutdruck, Blutzucker", "14");
		PatientenSteuerung pSteuerung = new PatientenSteuerung();
		Pair<Studie, String> studieMitDatum = new Pair<Studie, String>(studie, "25.1.2021");
		Pair<Studie, String> studieMitDatum2 = new Pair<Studie, String>(studie2, "25.1.2022");
		ArrayList<Pair<Studie, String>> studien = new ArrayList<>();
		studien.add(studieMitDatum);
		ArrayList<Pair<Studie, String>> studien2 = new ArrayList<>();
		studien2.add(studieMitDatum2);

		// Patienten hinzufuegen und so StudyNurses fuer ein Studie zustaendig machen
		AuthentifizierungSteuerung authentifizierungSteuerung = AuthentifizierungSteuerung.getInstance();
		BenutzerSteuerung benutzerSteuerung = new BenutzerSteuerung();
		Pair<String, String> passwort = benutzerSteuerung.benutzerHinzufuegen("Max", "Mustermann", true, true, true,
				true);
		Pair<String, String> passwort2 = benutzerSteuerung.benutzerHinzufuegen("Martina", "Mustermann", true, true,
				true, true);
		authentifizierungSteuerung.anmelden(passwort.getKey(), passwort.getValue());
		pSteuerung.patientHinzufuegen("Hendrik", "Meier", "Siekergasse33", studien);
		pSteuerung.patientHinzufuegen("Max", "Mustermann", "Schlossallee 42", studien2);

		StudieEinsehenSteuerung seS = new StudieEinsehenSteuerung();
		int anzahlSichtbarerStudien = seS.getStudien("1", "40").size();
		int erwarteteAnzahlSichtbarerStudien = 1;
		assertEquals(erwarteteAnzahlSichtbarerStudien, anzahlSichtbarerStudien);
		int anzahlGesamtStudien = DataManagement.getInstance().getStudien().size();
		int erwarteteAnzahlGesamtStudien = 2;
		assertEquals(erwarteteAnzahlGesamtStudien, anzahlGesamtStudien);
	}

	/**
	 * testet getStudien stellt sicher, dass eine Exception geworfen wird, wenn
	 * keine Zahl als Anzahl der Teilnehmer angegeben wird
	 * 
	 * @throws NoSuchAlgorithmException Fehler, wenn genutzter Algorithmus nicht existiert.
	 * @throws InvalidKeySpecException Fehler fuer ungueltige Key-Spezifikation.
	 */
	@Test
	public void testGetStudienKeineZahl() throws NoSuchAlgorithmException, InvalidKeySpecException {
		// Studien anlegen
		DataManagement.reset();
		StudienSteuerung studienSteuerung = new StudienSteuerung();
		Studie studie = studienSteuerung.studieHinzufuegen("Studie1", "40");
		Studie studie2 = studienSteuerung.studieHinzufuegen("Studie2", "75");
		VisitenvorlagenSteuerung vvSteuerung = new VisitenvorlagenSteuerung();
		vvSteuerung.visitenvorlageHinzufuegen(studie, "visitenvorlage1", "Blutdruck,Puls", "0");
		vvSteuerung.visitenvorlageHinzufuegen(studie, "visitenvorlage2", "Blutdruck, Blutzucker", "14");
		PatientenSteuerung pSteuerung = new PatientenSteuerung();
		Pair<Studie, String> studieMitDatum = new Pair<Studie, String>(studie, "25.1.2021");
		Pair<Studie, String> studieMitDatum2 = new Pair<Studie, String>(studie2, "25.1.2022");
		ArrayList<Pair<Studie, String>> studien = new ArrayList<>();
		studien.add(studieMitDatum);
		ArrayList<Pair<Studie, String>> studien2 = new ArrayList<>();
		studien2.add(studieMitDatum2);

		// Patienten hinzufuegen und so StudyNurses fuer ein Studie zustaendig machen
		AuthentifizierungSteuerung authentifizierungSteuerung = AuthentifizierungSteuerung.getInstance();
		BenutzerSteuerung benutzerSteuerung = new BenutzerSteuerung();
		Pair<String, String> passwort = benutzerSteuerung.benutzerHinzufuegen("Max", "Mustermann", true, true, true,
				true);
		Pair<String, String> passwort2 = benutzerSteuerung.benutzerHinzufuegen("Martina", "Mustermann", true, true,
				true, true);
		authentifizierungSteuerung.anmelden(passwort.getKey(), passwort.getValue());
		pSteuerung.patientHinzufuegen("Hendrik", "Meier", "Siekergasse33", studien);
		pSteuerung.patientHinzufuegen("Max", "Mustermann", "Schlossallee 42", studien2);

		StudieEinsehenSteuerung seS = new StudieEinsehenSteuerung();
		assertThrows(IllegalArgumentException.class, () -> {
			seS.getStudien("1", "Dies ist keine Zahl.").size();
		});

	}

}
