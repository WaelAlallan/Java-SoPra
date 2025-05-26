package de.wwu.sopra.steuerung.mta;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.util.ArrayList;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import de.wwu.sopra.datenhaltung.DataManagement;
import de.wwu.sopra.model.Patient;
import de.wwu.sopra.model.Probe;
import de.wwu.sopra.model.ProbenBehaelterTyp;
import de.wwu.sopra.model.ProbenKategorie;
import de.wwu.sopra.model.Raum;
import de.wwu.sopra.model.Studie;
import de.wwu.sopra.steuerung.alleuser.AuthentifizierungSteuerung;
import de.wwu.sopra.steuerung.biobankleiter.KuehlschrankSteuerung;
import de.wwu.sopra.steuerung.biobankleiter.ProbenBehaeltertypHinzufuegenSteuerung;
import de.wwu.sopra.steuerung.biobankleiter.ProbentypHinzufuegenSteuerung;
import de.wwu.sopra.steuerung.biobankleiter.RaumSteuerung;
import de.wwu.sopra.steuerung.biobankleiter.StudienSteuerung;
import de.wwu.sopra.steuerung.biobankleiter.VisitenvorlagenSteuerung;
import de.wwu.sopra.steuerung.mta.ProbenSteuerung;
import de.wwu.sopra.steuerung.personalleiter.BenutzerSteuerung;
import de.wwu.sopra.steuerung.studynurse.PatientenSteuerung;
import de.wwu.sopra.steuerung.studynurse.VisitenSteuerung;
import javafx.util.Pair;

/**
 * Klasse zum Testen der Steuerungsklasse der Probe.
 * @author Gruppe 1
 *
 */
public class ProbenSteuerungTest {

	private ProbenKategorie kategorie;
	private ProbenBehaelterTyp behaelter;
	private int visitenId;

	/**
	 * Diese Methode stellt die Anfangsbedingungen der folgenden Testmethoden
	 * 
	 * @throws NoSuchAlgorithmException Fehler, wenn genutzter Algorithmus nicht existiert.
	 * @throws InvalidKeySpecException Fehler fuer ungueltige Key-Spezifikation.
	 */
	@BeforeEach
	public void erstellen() throws NoSuchAlgorithmException, InvalidKeySpecException {
		DataManagement.getInstance().reset();

		// Raum erstellen
		RaumSteuerung raumSteuerung = new RaumSteuerung();
		raumSteuerung.raumHinzufuegen("Raum 1", "Gebaeude 1", "16");
		Raum raum = raumSteuerung.getRaeume("Raum 1", "Gebaeude 1", "16").get(0);

		// Kuehlschrank erstellen
		KuehlschrankSteuerung kuehlschrankSteuerung = new KuehlschrankSteuerung();
		kuehlschrankSteuerung.kuehlschrankHinzufuegen("Kuehlschrank 1", "5", raum);

		// Studie erstellen
		StudienSteuerung studienSteuerung = new StudienSteuerung();
		studienSteuerung.studieHinzufuegen("Studie 1", "50");
		Studie studie = studienSteuerung.getStudien("Studie 1", "50").get(0);

		// Visitenvorlage erstellen
		VisitenvorlagenSteuerung visitenVorlagenSteuerung = new VisitenvorlagenSteuerung();
		visitenVorlagenSteuerung.visitenvorlageHinzufuegen(studie, "Visitenvorlage 1", "Blutdruck, Gewicht", "5");

		// Studie mit Datum erstellen
		Pair<Studie, String> studieMitDatum = new Pair<Studie, String>(studie, "25.1.2021");
		ArrayList<Pair<Studie, String>> studien = new ArrayList<>();
		studien.add(studieMitDatum);

		// Benutzer erstellen
		BenutzerSteuerung benutzerSteuerung = new BenutzerSteuerung();
		Pair<String, String> passwortBenutzer = benutzerSteuerung.benutzerHinzufuegen("Anke", "Siemens", true, false,
				false, false);

		// Benutzer anmelden
		AuthentifizierungSteuerung.getInstance().anmelden(passwortBenutzer.getKey(), passwortBenutzer.getValue());

		// Patient hinzufuegen
		PatientenSteuerung patientenSteuerung = new PatientenSteuerung();
		patientenSteuerung.patientHinzufuegen("Lukas", "Holtevert", "Lindenstrasse 3", studien);

		// VisitenId herausfinden
		Patient patient = patientenSteuerung.getPatienten("Lukas", "Holtevert", "Studie 1").get(0);
		visitenId = patient.getVisiten().get(0).getVisitenId();

		// ProbenBehaelterTyp erstellen
		ProbenBehaeltertypHinzufuegenSteuerung behaeltersteuerung = new ProbenBehaeltertypHinzufuegenSteuerung();
		behaeltersteuerung.probenBehaeltertypHinzufuegen("Ampulle", "100", "10", "10", "runder deckel");
		behaelter = behaeltersteuerung.getProbenbehaeltertyp("Ampulle", "100", "10", "", "").get(0);

		// ProbenKategorie erstellen
		ProbentypHinzufuegenSteuerung kategorieSteuerung = new ProbentypHinzufuegenSteuerung();
		kategorieSteuerung.probentypHinzufuegen("Blut");
		kategorie = kategorieSteuerung.getProbenKategorie("Blut").get(0);

	}

	/**
	 * Diese Methode testet die Methode ProbeEintragen im Regelfall
	 */
	@Test
	public void testProbeEintragenImRegelfall() {

		ProbenSteuerung probenSteuerung = new ProbenSteuerung();
		probenSteuerung.probeEintragen(kategorie, behaelter, "" + visitenId, "12345", null);
		assertNotNull(probenSteuerung.getProben("Blut", "Studie 1", "" + visitenId, "", "").get(0));
	}

	/**
	 * Diese Methode testet die Methode ProbeEintragen im Regelfall
	 */
	@Test
	public void testProbeEintragenMitMutterprobe() {

		ProbenSteuerung probenSteuerung = new ProbenSteuerung();
		probenSteuerung.probeEintragen(kategorie, behaelter, "" + visitenId, "123455", null);
		Probe mutterProbe = probenSteuerung.getProben("", "", visitenId + "", "123455", "").get(0);
		probenSteuerung.probeEintragen(kategorie, behaelter, "" + visitenId, "123456", mutterProbe);
		assertNotNull(
				probenSteuerung.getProben("Blut", "Studie 1", "" + visitenId, "123456", "").get(0).getMutterProbe());
	}

	/**
	 * Diese Methode testet die Methode ProbeDauerhaftEntnehmen Im Regelfall
	 */
	@Test
	public void testProbeDauerhaftEntnehmenImRegelfall() {
		ProbenSteuerung probenSteuerung = new ProbenSteuerung();
		probenSteuerung.probeEintragen(kategorie, behaelter, "" + visitenId, "12345", null);
		Probe probe = probenSteuerung.getProben(kategorie.getName(), "Studie 1", "" + visitenId, "", "").get(0);
		probenSteuerung.probeDauerhaftEntnehmen(probe);
		assertNull(probe.getRack());
	}

	/**
	 * Diese Methode testet die Methode ProbeLoeschen im Regelfall
	 */
	@Test
	public void testProbeLoeschenImRegelfall() {
		ProbenSteuerung probenSteuerung = new ProbenSteuerung();
		probenSteuerung.probeEintragen(kategorie, behaelter, "" + visitenId, "12345", null);
		Probe probe = probenSteuerung.getProben(kategorie.getName(), "Studie 1", "" + visitenId, "", "").get(0);
		probenSteuerung.probeDauerhaftEntnehmen(probe);
		probenSteuerung.probeLoeschen(probe);
		boolean geloescht = true;
		for (Probe probeInListe : probe.getVisite().getProben()) {
			if (probeInListe.equals(probe)) {
				geloescht = false;
			}
		}
		assertTrue(geloescht);
	}

}
