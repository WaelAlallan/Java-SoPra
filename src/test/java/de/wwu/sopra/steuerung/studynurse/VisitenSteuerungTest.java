package de.wwu.sopra.steuerung.studynurse;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.util.ArrayList;

import org.junit.jupiter.api.Test;

import de.wwu.sopra.datenhaltung.DataManagement;
import de.wwu.sopra.model.Patient;
import de.wwu.sopra.model.Studie;
import de.wwu.sopra.model.Visite;
import de.wwu.sopra.model.VisitenVorlage;
import de.wwu.sopra.steuerung.alleuser.AuthentifizierungSteuerung;
import de.wwu.sopra.steuerung.biobankleiter.StudienSteuerung;
import de.wwu.sopra.steuerung.biobankleiter.VisitenvorlagenSteuerung;
import de.wwu.sopra.steuerung.helfer.DatumHelfer;
import de.wwu.sopra.steuerung.personalleiter.BenutzerSteuerung;
import de.wwu.sopra.steuerung.studynurse.PatientenSteuerung;
import de.wwu.sopra.steuerung.studynurse.VisitenSteuerung;
import javafx.util.Pair;

/**
 * Diese Klasse dient als Testklasse fuer die Klasse VisitenSteuerung
 * 
 * @author Jasmin, Cedric, Pia
 *
 */
public class VisitenSteuerungTest {
	/**
	 * Diese Methode testet,ob die Methode eine Fehlermeldung anzeigt, wenn ein
	 * ungueltiges Datum uebergeben wird
	 */
	@Test
	public void testVisitenSuchenUngultigesDatum2() {
		VisitenSteuerung steuerung = new VisitenSteuerung();

		assertThrows(IllegalArgumentException.class, () -> {
			steuerung.visitenSuchen("Hans", "Meier", "10.13.98", "", true);
		});
	}

	/**
	 * Diese Methode testet,ob die Methode eine Fehlermeldung anzeigt, wenn ein
	 * ungueltiges Datum uebergeben wird
	 */

	@Test
	public void testVisitenSuchenUngultigesDatum() {
		VisitenSteuerung steuerung = new VisitenSteuerung();

		assertThrows(IllegalArgumentException.class, () -> {
			steuerung.visitenSuchen("Hans", "Meier", "29.02.1900", "", false);
		});
	}

	/**
	 * Dieser Test testet die Methode visitenSuchen im Regelfall
	 * 
	 * @throws NoSuchAlgorithmException Fehler, wenn genutzter Algorithmus nicht existiert.
	 * @throws InvalidKeySpecException Fehler fuer ungueltige Key-Spezifikation.
	 */
	@Test
	public void visitenSuchenTestRegelfall() throws NoSuchAlgorithmException, InvalidKeySpecException {
		DataManagement.reset();
		AuthentifizierungSteuerung.reset();

		BenutzerSteuerung bSteuerung = new BenutzerSteuerung();
		Pair<String, String> benutzer = bSteuerung.benutzerHinzufuegen("Hugoline", "Kaesekuchen", true, false, false,
				false);
		AuthentifizierungSteuerung.getInstance().anmelden(benutzer.getKey(), benutzer.getValue());

		// Studie erstellen und eintragen
		StudienSteuerung sSteuerung = new StudienSteuerung();
		sSteuerung.studieHinzufuegen("Studie 1", "10");

		// Visitenvorlage erstellen und eintragen
		VisitenvorlagenSteuerung visitenVorlagenSteuerung = new VisitenvorlagenSteuerung();
		Studie studie = sSteuerung.getStudien("Studie 1", "" + 10).get(0);
		visitenVorlagenSteuerung.visitenvorlageHinzufuegen(studie, "visitenvorlage1", "Blutdruck,Puls", "0");

		// Patienten erstellen und eintragen - Dabei werden automatisch die Visiten
		// erstellt
		PatientenSteuerung patientenSteuerung = new PatientenSteuerung();
		Pair<Studie, String> studieMitDatum = new Pair<Studie, String>(studie, "25.1.2020");
		ArrayList<Pair<Studie, String>> studien = new ArrayList<>();
		studien.add(studieMitDatum);
		patientenSteuerung.patientHinzufuegen("Hendrik", "Meier", "Siekergasse33", studien);

		VisitenSteuerung steuerung = new VisitenSteuerung();
		assertEquals(1, (steuerung.visitenSuchen("Hendrik", "Meier", "25.1.2020", "Studie 1", false).size()));
	}

	/**
	 * Die Methode testVisiteBearbeiten testet ob nach Eintragung der Daten durch
	 * die StudyNurse die Daten auch wirklich da sind wo sie hingehoeren.
	 */
	@Test
	public void testVisiteBearbeitenRegelfall() {
		DataManagement.reset();
		// Studie hinzufuegen
		StudienSteuerung studienSteuerung = new StudienSteuerung();
		studienSteuerung.studieHinzufuegen("Studie 1", "10");
		Studie studie = studienSteuerung.getStudien("Studie 1", "10").get(0);

		// Visitenvorlage erstellen
		VisitenvorlagenSteuerung visitenVorlagenSteuerung = new VisitenvorlagenSteuerung();
		visitenVorlagenSteuerung.visitenvorlageHinzufuegen(studie, "visitenvorlage1", "Blutdruck,Puls", "0");

		// Studie zu Datum hinzufuegen
		Pair<Studie, String> studieMitDatum = new Pair<Studie, String>(studie, "25.01.2021");
		ArrayList<Pair<Studie, String>> studien = new ArrayList<>();
		studien.add(studieMitDatum);

		// Patient hinzufuegen
		PatientenSteuerung pateientenSteuerung = new PatientenSteuerung();
		pateientenSteuerung.patientHinzufuegen("Hendrik", "Meier", "Siekergasse33", studien);

		// Visite bearbeiten
		Patient patient = pateientenSteuerung.getPatienten("Hendrik", "Meier", "Studie 1").get(0);
		Visite visite = patient.getVisiten().get(0);
		ArrayList<Pair<String, String>> daten = new ArrayList<>();
		daten.add(new Pair<String, String>("Blutdruck", "180/90"));
		daten.add(new Pair<String, String>("Puls", "60"));
		VisitenSteuerung visitenSteuerung = new VisitenSteuerung();
		visitenSteuerung.visiteBearbeiten(visite, daten, "01.04.2020");

		Visite visiteNachBearbeiten = patient.getVisiten().get(0);
		assertTrue(visiteNachBearbeiten.getMedizinscheDaten().get(1).getValue().equals("60"));
		assertTrue(visiteNachBearbeiten.getDatum().compareTo(DatumHelfer.erzeugeDatum("01.04.2020")) == 0);
		assertTrue(visiteNachBearbeiten.isAbgeschlossen());
	}

}
