package de.wwu.sopra.steuerung.biobankleiter;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Date;
import java.time.LocalDate;

import org.junit.jupiter.api.Test;

import de.wwu.sopra.model.Benutzer;
import de.wwu.sopra.model.Patient;
import de.wwu.sopra.model.Studie;
import de.wwu.sopra.model.Visite;
import de.wwu.sopra.model.VisitenVorlage;
import de.wwu.sopra.steuerung.biobankleiter.VisitenvorlagenSteuerung;

/**
 * Diese Klasse testet die Klasse VisitenvorlagenSteuerung
 * 
 * @author Jasmin, Pia, Cedric
 *
 */

public class VisitenvorlagenSteuerungTest {
	/**
	 * Diese Methode testet die Methode visitenvorlagenHinzufuegen
	 */
	@Test
	public void visitenvorlagenHinzufuegen() {
		Studie studie = new Studie("Studie1", 40);
		VisitenvorlagenSteuerung steuerung = new VisitenvorlagenSteuerung();
		steuerung.visitenvorlageHinzufuegen(studie, "Studie1", "Blutdruck,Puls", "40");
		assertTrue(studie.getVisitenVorlage().size() > 0);
	}

	/**
	 * Diese Methode testet die Methode visitenvorlageBearbeitenTest, es wird
	 * geprueft ob die Methode eine Exception auswirft, falls man eine
	 * Visitenvorlage loeschen will, wozu beeits konkrete visiten erstellt wurden
	 */
	@Test

	public void visitenvorlageBearbeitenTest() {
		Studie studie = new Studie("Studie1", 40);
		VisitenvorlagenSteuerung steuerung = new VisitenvorlagenSteuerung();
		steuerung.visitenvorlageHinzufuegen(studie, "Studie1", "Blutdruck,Puls", "40");
		VisitenVorlage v = studie.getVisitenVorlage().get(0);
		Date date = new Date(2020, 12, 10);
		Visite visite = new Visite(date, 0, null, null, studie, v);
		v.addVisite(visite);
		assertThrows(IllegalArgumentException.class, () -> {
			steuerung.visitenvorlageBearbeiten(studie, v, "Hallo", "Blutdruck,Puls", "38");

		});

	}

	/**
	 * Diese Methode testet die Methode visitenvorlageBearbeiten, es wird getestet
	 * ob der Regelfall funktioniert
	 */

	@Test
	public void visitenvorlageBearbeiten2Test() {
		Studie studie = new Studie("Studie1", 40);
		VisitenvorlagenSteuerung steuerung = new VisitenvorlagenSteuerung();
		steuerung.visitenvorlageHinzufuegen(studie, "Studie1", "Blutdruck,Puls", "40");
		VisitenVorlage v = studie.getVisitenVorlage().get(0);
		steuerung.visitenvorlageBearbeiten(studie, v, "Hallo", "Blutdruck,Puls", "38");
		assertEquals("Hallo", studie.getVisitenVorlage().get(0).getName());

	}

	/**
	 * Diese Methode testet die Methode visitenvorlageLoeschen und prueft ob die
	 * Vorlage wirklich geloescht worden ist
	 */
	@Test
	public void visitenvorlageLoeschenTest() {
		Studie studie = new Studie("Studie1", 40);
		VisitenvorlagenSteuerung steuerung = new VisitenvorlagenSteuerung();
		steuerung.visitenvorlageHinzufuegen(studie, "Studie1", "Blutdruck,Puls", "40");
		VisitenVorlage vorlage = studie.getVisitenVorlage().get(0);
		steuerung.visitenvorlageLoeschen(studie, vorlage);
		assertTrue(studie.getVisitenVorlage().size() == 0);

	}

	/**
	 * Diese Methode testet die Methode visitenvorlageLoeschen und testet ob die
	 * Methode eine Fehlermeldung ausgibt, wenn versucht wird die Vorlage zu
	 * loeschen, obwohl bereits konkrete visiten hinzugefuegt worden sind
	 */
	@Test
	public void visitenvorlageLoeschen2Test() {
		Studie studie = new Studie("Studie1", 40);
		VisitenvorlagenSteuerung steuerung = new VisitenvorlagenSteuerung();
		steuerung.visitenvorlageHinzufuegen(studie, "Studie1", "Blutdruck,Puls", "40");
		VisitenVorlage vorlage = studie.getVisitenVorlage().get(0);
		Date date = new Date(2020, 12, 10);
		Visite visite = new Visite(date, 0, null, null, studie, vorlage);
		vorlage.addVisite(visite);
		assertThrows(IllegalArgumentException.class, () -> {
			steuerung.visitenvorlageLoeschen(studie, vorlage);

		});

	}

}
