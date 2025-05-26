package de.wwu.sopra.steuerung.alleuser;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;

import org.junit.jupiter.api.Test;

import de.wwu.sopra.datenhaltung.DataManagement;
import de.wwu.sopra.model.Benutzer;
import de.wwu.sopra.steuerung.alleuser.AuthentifizierungSteuerung;
import de.wwu.sopra.steuerung.personalleiter.BenutzerSteuerung;
import javafx.util.Pair;

/**
 * Diese Testklasse dient zum Testen der Klasse Authentifizierung
 * 
 * @author Jasmin, Pia, Cedric
 *
 */
public class AuthentifizierungSteuerungTest {

	/**
	 * Diese Methode testet die Methode Anmelden und prueft ob der aktive Benutzer
	 * gesetzt wurde
	 * 
	 * @throws NoSuchAlgorithmException Fehler, wenn der genutzte Algorithmus nicht existiert.
	 * @throws InvalidKeySpecException invalidkeyspec
	 */
	@Test

	public void testAnmelden() throws NoSuchAlgorithmException, InvalidKeySpecException {
		AuthentifizierungSteuerung authentifizierungSteuerung = AuthentifizierungSteuerung.getInstance();
		BenutzerSteuerung benutzerSteuerung = new BenutzerSteuerung();
		Pair<String, String> passwort = benutzerSteuerung.benutzerHinzufuegen("Jasmin", "Kersten", false, true, false,
				true);
		authentifizierungSteuerung.anmelden(passwort.getKey(), passwort.getValue());
		assertTrue(authentifizierungSteuerung.getAktiverBenutzer() != null);
	}

	/**
	 * Diese Methode testet die Methode Anmelden und prueft ob der aktive Benutzer
	 * noch null ist, wenn die Anmeldung fehl geschlagen ist
	 * 
	 * @throws NoSuchAlgorithmException Fehler, wenn der genutzte Algorithmus nicht existiert.
	 * @throws InvalidKeySpecException invalidkeyspec
	 */

	@Test
	public void testAnmelden2() throws NoSuchAlgorithmException, InvalidKeySpecException {
		DataManagement.reset();
		AuthentifizierungSteuerung.reset();
		AuthentifizierungSteuerung authentifizierungSteuerung = AuthentifizierungSteuerung.getInstance();
		BenutzerSteuerung benutzerSteuerung = new BenutzerSteuerung();
		Pair<String, String> passwort = benutzerSteuerung.benutzerHinzufuegen("Jasmin", "Kersten", false, true, false,
				true);
		authentifizierungSteuerung.anmelden(passwort.getKey(), "Hallo");
		assertTrue(authentifizierungSteuerung.getAktiverBenutzer() == null);
	}

	/**
	 * Diese Methode testet die Methode anmelden und prueft ob das Melden bei
	 * falscher eingabe des Benutzernamens fehlschlaegt
	 * 
	 * @throws NoSuchAlgorithmException Fehler, wenn der genutzte ALgorithmus nicht existiert.
	 * @throws InvalidKeySpecException Falsche Key-Spezifikationen.
	 */
	@Test
	public void testAnmelden3() throws NoSuchAlgorithmException, InvalidKeySpecException {
		AuthentifizierungSteuerung authentifizierungSteuerung = AuthentifizierungSteuerung.getInstance();
		BenutzerSteuerung benutzerSteuerung = new BenutzerSteuerung();
		Pair<String, String> passwort = benutzerSteuerung.benutzerHinzufuegen("Jasmin", "Kersten", false, true, false,
				true);
		assertThrows(IllegalArgumentException.class, () -> {
			authentifizierungSteuerung.anmelden("Hallo", passwort.getValue());
		});
	}

	/**
	 * Diese methode testet die Methode AenderePasswort und prueft ob das Passwort
	 * geaendert wurde
	 * 
	 * @throws NoSuchAlgorithmException Fehler, wenn der genutzte ALgorithmus nicht existiert.
	 * @throws InvalidKeySpecException Falsche Key-Spezifikationen.
	 */
	@Test
	public void testAenderePasswort() throws NoSuchAlgorithmException, InvalidKeySpecException {
		DataManagement.reset();
		AuthentifizierungSteuerung authentifizierungSteuerung = AuthentifizierungSteuerung.getInstance();
		BenutzerSteuerung benutzerSteuerung = new BenutzerSteuerung();
		Pair<String, String> passwort = benutzerSteuerung.benutzerHinzufuegen("Jasmin", "Kersten", false, true, false,
				true);
		authentifizierungSteuerung.anmelden(passwort.getKey(), passwort.getValue());
		authentifizierungSteuerung.aenderePasswort(passwort.getValue(), "Kaninchen", "Kaninchen");
		String passwortNeu = "Kaninchen";
		assertTrue(authentifizierungSteuerung.validate(
				authentifizierungSteuerung.getAktiverBenutzer().getPasswordHash(), passwortNeu.toCharArray()));
	}

	/**
	 * Diese Methode prueft ob die Methode AenderePasswort eine Fehlermeldung wirft,
	 * wenn man nicht zwei gleiche neue Passwoerter eingibt
	 * 
	 * @throws NoSuchAlgorithmException Fehler, wenn der genutzte ALgorithmus nicht existiert.
	 * @throws InvalidKeySpecException Falsche Key-Spezifikationen.
	 */
	@Test
	public void testAenderePasswort2() throws NoSuchAlgorithmException, InvalidKeySpecException {

		DataManagement.reset();
		AuthentifizierungSteuerung authentifizierungSteuerung = AuthentifizierungSteuerung.getInstance();
		BenutzerSteuerung benutzerSteuerung = new BenutzerSteuerung();
		Pair<String, String> passwort = benutzerSteuerung.benutzerHinzufuegen("Jasmin", "Kersten", false, true, false,
				true);
		authentifizierungSteuerung.anmelden(passwort.getKey(), passwort.getValue());
		assertThrows(IllegalArgumentException.class, () -> {
			authentifizierungSteuerung.aenderePasswort(passwort.getValue(), "Kaninchen", "Kanihen");
		});
	}
}
