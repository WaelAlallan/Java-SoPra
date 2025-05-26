package de.wwu.sopra.steuerung.mta;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.util.ArrayList;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

import de.wwu.sopra.datenhaltung.DataFilter;
import de.wwu.sopra.datenhaltung.DataManagement;
import de.wwu.sopra.model.Gestell;
import de.wwu.sopra.model.Kuehlschrank;
import de.wwu.sopra.model.Patient;
import de.wwu.sopra.model.Probe;
import de.wwu.sopra.model.ProbenBehaelterTyp;
import de.wwu.sopra.model.ProbenKategorie;
import de.wwu.sopra.model.Probenstatus;
import de.wwu.sopra.model.Rack;
import de.wwu.sopra.model.Raum;
import de.wwu.sopra.model.Schublade;
import de.wwu.sopra.model.Segment;
import de.wwu.sopra.model.Studie;
import de.wwu.sopra.steuerung.alleuser.AuthentifizierungSteuerung;
import de.wwu.sopra.steuerung.biobankleiter.KuehlschrankSteuerung;
import de.wwu.sopra.steuerung.biobankleiter.ProbenBehaeltertypHinzufuegenSteuerung;
import de.wwu.sopra.steuerung.biobankleiter.ProbentypHinzufuegenSteuerung;
import de.wwu.sopra.steuerung.biobankleiter.RaumSteuerung;
import de.wwu.sopra.steuerung.biobankleiter.StudienSteuerung;
import de.wwu.sopra.steuerung.biobankleiter.VisitenvorlagenSteuerung;
import de.wwu.sopra.steuerung.mta.ProbenPlatzVergabe;
import de.wwu.sopra.steuerung.personalleiter.BenutzerSteuerung;
import de.wwu.sopra.steuerung.studynurse.PatientenSteuerung;
import javafx.util.Pair;

/**
 * Testklasse fuer ProbenPlatzVergabe
 * 
 * @author Pia, Jasmin, Cedric
 *
 */
public class ProbenPlatzVergabeTest {

	private ProbenKategorie kategorie;
	private ProbenBehaelterTyp behaelter;
	private ProbenBehaelterTyp behaelter2;
	private int visitenId;
	private Patient patient;
	private Raum raum;

	/**
	 * Stellt die Objekte fuer die kuenftigen Testfaelle
	 * 
	 * @throws NoSuchAlgorithmException Fehler, wenn genutzter Algorithmus nicht existiert.
	 * @throws InvalidKeySpecException Fehler fuer ungueltige Key-Spezifikation.
	 * @throws IllegalArgumentException Fehler fuer ein ungueltiges Argument.
	 */
	@BeforeEach
	public void init() throws NoSuchAlgorithmException, InvalidKeySpecException, IllegalArgumentException {

		DataManagement.reset();
		AuthentifizierungSteuerung.reset();
		// Raum erstellen
		RaumSteuerung raumSteuerung = new RaumSteuerung();
		raumSteuerung.raumHinzufuegen("Raum 1", "Gebaeude 1", "16");
		raum = raumSteuerung.getRaeume("Raum 1", "Gebaeude 1", "16").get(0);

		// Kuehlschrank erstellen
		KuehlschrankSteuerung kuehlschrankSteuerung = new KuehlschrankSteuerung();
		kuehlschrankSteuerung.kuehlschrankHinzufuegen("Kuehlschrank 1", "5.0", raum);

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
		patient = patientenSteuerung.getPatienten("Lukas", "Holtevert", "Studie 1").get(0);
		visitenId = patient.getVisiten().get(0).getVisitenId();

		// ProbenBehaelterTyp erstellen
		ProbenBehaeltertypHinzufuegenSteuerung behaeltersteuerung = new ProbenBehaeltertypHinzufuegenSteuerung();
		behaeltersteuerung.probenBehaeltertypHinzufuegen("Ampulle", "100", "10", "10", "runder deckel");
		behaelter = behaeltersteuerung.getProbenbehaeltertyp("Ampulle", "100", "10", "", "").get(0);
		behaeltersteuerung.probenBehaeltertypHinzufuegen("Reagenzglas", "10000", "100", "10", "Korken");
		behaelter2 = behaeltersteuerung.getProbenbehaeltertyp("Reagenzglas", "10000", "100", "", "").get(0);

		// ProbenKategorie erstellen
		ProbentypHinzufuegenSteuerung kategorieSteuerung = new ProbentypHinzufuegenSteuerung();
		kategorieSteuerung.probentypHinzufuegen("Blut");
		kategorie = kategorieSteuerung.getProbenKategorie("Blut").get(0);
	}

	/**
	 * Testet die Methode weiseProbePlatz zu wenn noch keine Proben des Patienten
	 * oder der Studie vorhanden sind
	 */

	@Test
	public void testWeiseProbePlatzZuNochKeineProbenZuPatientOderStudie() {
		Probe neueProbe = new Probe(12345566, Probenstatus.EINGELAGERT, behaelter, kategorie,
				patient.getVisiten().get(0));
		ProbenPlatzVergabe ppV = new ProbenPlatzVergabe();
		String erg = ppV.weiseProbePlatzZu(neueProbe);

		DataManagement.getInstance().speichereRaum(raum);

		Kuehlschrank erwarteterKuehlschrank = DataFilter.filterKuehlschraenke("", null, raum).get(0);
		Segment erwartetesSegment = erwarteterKuehlschrank.getSegmente().get(0);
		Gestell erwartetesGestell = erwartetesSegment.getGestelle().get(0);
		Schublade erwarteteSchublade = erwartetesGestell.getSchubladen().get(0);
		Rack erwartetesRack = erwarteteSchublade.getRacks().get(0);

		Rack tatsaechlichesRack = neueProbe.getRack();
		Schublade tatsaechlicheSchublade = tatsaechlichesRack.getSchublade();
		Gestell tatsaechlichesGestell = tatsaechlicheSchublade.getGestell();
		Segment tatsaechlichesSegment = tatsaechlichesGestell.getSegment();
		Kuehlschrank tatsaechlicherKuehlschrank = tatsaechlichesSegment.getKuehlschrank();

		assertEquals(erwarteterKuehlschrank, tatsaechlicherKuehlschrank);
		assertEquals(erwartetesSegment, tatsaechlichesSegment);
		assertEquals(erwartetesGestell, tatsaechlichesGestell);
		assertEquals(erwarteteSchublade, tatsaechlicheSchublade);
		assertEquals(erwartetesRack, tatsaechlichesRack);
	}

	/**
	 * Testet die Methode Weise ProbePlatuZu im Fall eines anderen Behaeltertyps
	 */

	@Test
	public void testWeiseProbePlatzZuAndererBehaeltertyp() {
		Probe neueProbe = new Probe(12345566, Probenstatus.EINGELAGERT, behaelter, kategorie,
				patient.getVisiten().get(0));
		ProbenPlatzVergabe ppV = new ProbenPlatzVergabe();
		String erg = ppV.weiseProbePlatzZu(neueProbe);
		Probe probeAndererBehaelterTyp = new Probe(123412, Probenstatus.EINGELAGERT, behaelter2, kategorie,
				patient.getVisiten().get(0));
		ProbenPlatzVergabe ppV2 = new ProbenPlatzVergabe();
		String erg2 = ppV2.weiseProbePlatzZu(probeAndererBehaelterTyp);
		DataManagement.getInstance().speichereRaum(raum);

		Kuehlschrank erwarteterKuehlschrank = DataFilter.filterKuehlschraenke("", null, raum).get(0);
		Segment erwartetesSegment = erwarteterKuehlschrank.getSegmente().get(0);
		Gestell erwartetesGestell = erwartetesSegment.getGestelle().get(0);
		Schublade erwarteteSchublade = erwartetesGestell.getSchubladen().get(0);
		Rack erwartetesRack = erwarteteSchublade.getRacks().get(1);

		Rack tatsaechlichesRack = probeAndererBehaelterTyp.getRack();
		Schublade tatsaechlicheSchublade = tatsaechlichesRack.getSchublade();
		Gestell tatsaechlichesGestell = tatsaechlicheSchublade.getGestell();
		Segment tatsaechlichesSegment = tatsaechlichesGestell.getSegment();
		Kuehlschrank tatsaechlicherKuehlschrank = tatsaechlichesSegment.getKuehlschrank();

		assertEquals(erwarteterKuehlschrank, tatsaechlicherKuehlschrank);
		assertEquals(erwartetesSegment, tatsaechlichesSegment);
		assertEquals(erwartetesGestell, tatsaechlichesGestell);
		assertEquals(erwarteteSchublade, tatsaechlicheSchublade);
		assertEquals(erwartetesRack, tatsaechlichesRack);

	}

	/**
	 * Diese Methode testet die Methode WeiseProbePlatz zu im Fall, dass das Rack
	 * voll ist
	 */

	@Test
	public void testWeiseProbeZuRackVoll() {
		for (int i = 0; i < 150; i++) {
			ProbenPlatzVergabe ppV = new ProbenPlatzVergabe();
			ppV.weiseProbePlatzZu(
					new Probe(i, Probenstatus.EINGELAGERT, behaelter, kategorie, patient.getVisiten().get(0)));
		}
		ProbenPlatzVergabe ppV = new ProbenPlatzVergabe();
		assertTrue(ppV
				.weiseProbePlatzZu(
						new Probe(151, Probenstatus.EINGELAGERT, behaelter, kategorie, patient.getVisiten().get(0)))
				.contains("Ein neues Rack"));
	}
}
