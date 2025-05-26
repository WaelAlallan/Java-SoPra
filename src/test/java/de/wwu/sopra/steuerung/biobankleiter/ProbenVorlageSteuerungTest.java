package de.wwu.sopra.steuerung.biobankleiter;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.ArrayList;
import java.util.stream.Collectors;

import org.junit.jupiter.api.Test;

import de.wwu.sopra.model.ProbenBehaelterTyp;
import de.wwu.sopra.model.ProbenKategorie;
import de.wwu.sopra.model.ProbenVorlage;
import de.wwu.sopra.model.VisitenVorlage;
import de.wwu.sopra.steuerung.biobankleiter.ProbenVorlageSteuerung;

/**
 * testet ProbenVorlageSteuerung hinzufuegen, bearbeiten und loeschen
 * 
 * @author Pia, Jasmin, Cedric
 *
 */
public class ProbenVorlageSteuerungTest {

	/**
	 * Standardfall ProbenVorlagenHinzufuegen
	 */
	@Test
	public void testProbenVorlagenHinzufuegen() {
		ProbenVorlageSteuerung pVS = new ProbenVorlageSteuerung();

		ArrayList<String> zuMessen = new ArrayList<String>();
		zuMessen.add("Puls");
		zuMessen.add("Blutdruck");
		VisitenVorlage visitenVorlage = new VisitenVorlage("Visite 1", zuMessen, 10);
		ProbenBehaelterTyp behaelterTyp = new ProbenBehaelterTyp("Reagenzglas", 7.2, 5.2, 10.4, "Pfropfen");
		ProbenKategorie kategorie = new ProbenKategorie("Speichel");
		pVS.probenVorlageHinzufuegen(visitenVorlage, behaelterTyp, kategorie);

		ArrayList<ProbenVorlage> vorhandeneProbenVorlagen = visitenVorlage.getProbenVorlage();
		int anzahlDesObjekts = vorhandeneProbenVorlagen.stream()
				.filter(probenVorlage -> probenVorlage.getKategorie() == kategorie)
				.filter(probenVorlage -> probenVorlage.getProbenBehaelterTyp() == behaelterTyp)
				.collect(Collectors.<ProbenVorlage>toList()).size();
		int erwarteteAnzahlDesObjekts = 1;
		assertEquals(erwarteteAnzahlDesObjekts, anzahlDesObjekts);
	}

	/**
	 * Fall kein BehaelterTyp ausgewaehlt
	 */
	@Test
	public void testProbenVorlagenHinzufuegenKeinBehaelterTyp() {
		ProbenVorlageSteuerung pVS = new ProbenVorlageSteuerung();

		ArrayList<String> zuMessen = new ArrayList<String>();
		zuMessen.add("Puls");
		zuMessen.add("Blutdruck");
		VisitenVorlage visitenVorlage = new VisitenVorlage("Visite 1", zuMessen, 10);
		ProbenBehaelterTyp behaelterTyp = new ProbenBehaelterTyp("Reagenzglas", 7.2, 5.2, 10.4, "Pfropfen");
		ProbenKategorie kategorie = new ProbenKategorie("Speichel");
		assertThrows(IllegalArgumentException.class, () -> {
			pVS.probenVorlageHinzufuegen(visitenVorlage, null, kategorie);
		});
	}

	/**
	 * Fall keine Kategorie ausgewaehlt
	 */
	@Test
	public void testProbenVorlagenHinzufuegenKeineKategorie() {
		ProbenVorlageSteuerung pVS = new ProbenVorlageSteuerung();

		ArrayList<String> zuMessen = new ArrayList<String>();
		zuMessen.add("Puls");
		zuMessen.add("Blutdruck");
		VisitenVorlage visitenVorlage = new VisitenVorlage("Visite 1", zuMessen, 10);
		ProbenBehaelterTyp behaelterTyp = new ProbenBehaelterTyp("Reagenzglas", 7.2, 5.2, 10.4, "Pfropfen");
		ProbenKategorie kategorie = new ProbenKategorie("Speichel");
		assertThrows(IllegalArgumentException.class, () -> {
			pVS.probenVorlageHinzufuegen(visitenVorlage, behaelterTyp, null);
		});
	}

	/**
	 * Standardfall ProbenVorlagenBearbeiten
	 */
	@Test
	public void testProbenVorlagenBearbeiten() {
		ProbenVorlageSteuerung pVS = new ProbenVorlageSteuerung();

		ProbenBehaelterTyp behaelterTypAlt = new ProbenBehaelterTyp("anderes Reagenzglas", 7.0, 4.2, 9.4,
				"anderer Pfropfen");
		ProbenKategorie kategorieAlt = new ProbenKategorie("Blut");
		ProbenVorlage alt = new ProbenVorlage(kategorieAlt, behaelterTypAlt);

		ArrayList<String> zuMessen = new ArrayList<String>();
		zuMessen.add("Puls");
		zuMessen.add("Blutdruck");
		VisitenVorlage visitenVorlage = new VisitenVorlage("Visite 1", zuMessen, 10);
		visitenVorlage.addProbenVorlage(alt);

		ProbenBehaelterTyp behaelterTyp = new ProbenBehaelterTyp("Reagenzglas", 7.2, 5.2, 10.4, "Pfropfen");
		ProbenKategorie kategorie = new ProbenKategorie("Speichel");
		ProbenVorlage neu = new ProbenVorlage(kategorie, behaelterTyp);
		pVS.probenVorlageBearbeiten(visitenVorlage, alt, behaelterTyp, kategorie);

		ArrayList<ProbenVorlage> vorhandeneProbenVorlagen = visitenVorlage.getProbenVorlage();
		int anzahlDesObjekts = vorhandeneProbenVorlagen.stream()
				.filter(probenVorlage -> probenVorlage.getKategorie() == kategorie)
				.filter(probenVorlage -> probenVorlage.getProbenBehaelterTyp() == behaelterTyp)
				.collect(Collectors.<ProbenVorlage>toList()).size();
		int erwarteteAnzahlDesObjekts = 1;
		assertEquals(erwarteteAnzahlDesObjekts, anzahlDesObjekts);

	}

	/**
	 * Fall kein BehaelterTyp ausgewaehlt
	 */
	@Test
	public void testProbenVorlagenBearbeitenKeinBehaelterTyp() {
		ProbenVorlageSteuerung pVS = new ProbenVorlageSteuerung();

		ArrayList<String> zuMessen = new ArrayList<String>();
		zuMessen.add("Puls");
		zuMessen.add("Blutdruck");
		ProbenBehaelterTyp behaelterTypAlt = new ProbenBehaelterTyp("anderes Reagenzglas", 7.0, 4.2, 9.4,
				"anderer Pfropfen");
		ProbenKategorie kategorieAlt = new ProbenKategorie("Blut");
		ProbenVorlage alt = new ProbenVorlage(kategorieAlt, behaelterTypAlt);
		VisitenVorlage visitenVorlage = new VisitenVorlage("Visite 1", zuMessen, 10);
		ProbenBehaelterTyp behaelterTyp = new ProbenBehaelterTyp("Reagenzglas", 7.2, 5.2, 10.4, "Pfropfen");
		ProbenKategorie kategorie = new ProbenKategorie("Speichel");
		assertThrows(IllegalArgumentException.class, () -> {
			pVS.probenVorlageBearbeiten(visitenVorlage, alt, null, kategorie);
		});
	}

	/**
	 * Fall keine Kategorie ausgewaehlt
	 */
	@Test
	public void testProbenVorlagenBearbeitenKeineKategorie() {
		ProbenVorlageSteuerung pVS = new ProbenVorlageSteuerung();

		ArrayList<String> zuMessen = new ArrayList<String>();
		zuMessen.add("Puls");
		zuMessen.add("Blutdruck");
		ProbenBehaelterTyp behaelterTypAlt = new ProbenBehaelterTyp("anderes Reagenzglas", 7.0, 4.2, 9.4,
				"anderer Pfropfen");
		ProbenKategorie kategorieAlt = new ProbenKategorie("Blut");
		ProbenVorlage alt = new ProbenVorlage(kategorieAlt, behaelterTypAlt);
		VisitenVorlage visitenVorlage = new VisitenVorlage("Visite 1", zuMessen, 10);
		ProbenBehaelterTyp behaelterTyp = new ProbenBehaelterTyp("Reagenzglas", 7.2, 5.2, 10.4, "Pfropfen");
		ProbenKategorie kategorie = new ProbenKategorie("Speichel");
		assertThrows(IllegalArgumentException.class, () -> {
			pVS.probenVorlageBearbeiten(visitenVorlage, alt, behaelterTyp, null);
		});
	}

	/**
	 * Standardfall ProbenVorlagenLoeschen
	 */
	@Test
	public void testProbenVorlagenLoeschen() {
		ProbenVorlageSteuerung pVS = new ProbenVorlageSteuerung();
		ArrayList<String> zuMessen = new ArrayList<String>();
		zuMessen.add("Puls");
		zuMessen.add("Blutdruck");
		VisitenVorlage visitenVorlage = new VisitenVorlage("Visite 1", zuMessen, 10);
		ProbenBehaelterTyp behaelterTyp = new ProbenBehaelterTyp("Reagenzglas", 7.2, 5.2, 10.4, "Pfropfen");
		ProbenKategorie kategorie = new ProbenKategorie("Speichel");
		ProbenVorlage vorlage = new ProbenVorlage(kategorie, behaelterTyp);
		visitenVorlage.addProbenVorlage(vorlage);
		pVS.probenVorlageLoeschen(visitenVorlage, vorlage);

		ArrayList<ProbenVorlage> vorhandeneProbenVorlagen = visitenVorlage.getProbenVorlage();
		int anzahlDesObjekts = vorhandeneProbenVorlagen.stream()
				.filter(probenVorlage -> probenVorlage.getKategorie() == kategorie)
				.filter(probenVorlage -> probenVorlage.getProbenBehaelterTyp() == behaelterTyp)
				.collect(Collectors.<ProbenVorlage>toList()).size();
		int erwarteteAnzahlDesObjekts = 0;
		assertEquals(erwarteteAnzahlDesObjekts, anzahlDesObjekts);
	}

}
