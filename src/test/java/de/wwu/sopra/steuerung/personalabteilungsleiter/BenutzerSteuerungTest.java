package de.wwu.sopra.steuerung.personalabteilungsleiter;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.util.ArrayList;

import org.junit.jupiter.api.Test;

import de.wwu.sopra.datenhaltung.DataFilter;
import de.wwu.sopra.datenhaltung.DataManagement;
import de.wwu.sopra.model.Benutzer;
import de.wwu.sopra.model.PasswortHash;
import de.wwu.sopra.model.Patient;
import de.wwu.sopra.model.Studie;
import de.wwu.sopra.model.Visite;
import de.wwu.sopra.model.VisitenVorlage;
import de.wwu.sopra.steuerung.alleuser.AuthentifizierungSteuerung;
import de.wwu.sopra.steuerung.helfer.DatumHelfer;
import de.wwu.sopra.steuerung.personalleiter.BenutzerSteuerung;
import javafx.util.Pair;

/**
 * Die Klasse BenutzerSteuerungTest testet die Klasse BenutzerSteuerung Es
 * werden die Methoden BenutzerHinzufuegen, BenutzerBearbeiten und getBenutzer
 * getestet.
 * 
 * @author Jasmin, Pia, Cedric,
 *
 */

public class BenutzerSteuerungTest {

	/**
	 * Die Methode testBenutzerHinzufuegen testet, ob wenn man zwei Benutzer mit dem
	 * gleichen Vor- und Nachnamen anlegt die Benutzernamen nicht gleich sind. Der
	 * Test schlaegt fehl, wenn die beiden unterschiedlichen Benutzer den gleichen
	 * Nutzernamen zugeteilt bekommen.
	 * @throws NoSuchAlgorithmException Fehler, wenn genutzter Algorithmus nicht existiert.
	 * @throws InvalidKeySpecException Fehler fuer ungueltige Key-Spezifikation.
	 */
	@Test
	public void testBenutzerHinzufuegen() throws NoSuchAlgorithmException, InvalidKeySpecException {
		String vorname = "Hendrik";
		String nachname = "Meier";
		boolean istNurse = true;
		boolean istMTA = false;
		boolean istLeiter = true;
		boolean istPerson = false;
		BenutzerSteuerung steuerung1 = new BenutzerSteuerung();
		Pair<String, String> benutzer1 = steuerung1.benutzerHinzufuegen(vorname, nachname, istNurse, istMTA, istLeiter,
				istPerson);
		BenutzerSteuerung steuerung2 = new BenutzerSteuerung();
		Pair<String, String> benutzer2 = steuerung2.benutzerHinzufuegen(vorname, nachname, false, true, false, true);
		assertNotEquals(benutzer1.getKey(), benutzer2.getKey());
	}

	/**
	 * Die Methode testBenutzerHinzufuegen2 testet, ob auch nach dem loeschen von
	 * Benutzern keine Benutzernamen doppelt vergeben werden
	 * 
	 * @throws NoSuchAlgorithmException Fehler, wenn genutzter Algorithmus nicht existiert.
	 * @throws InvalidKeySpecException Fehler fuer ungueltige Key-Spezifikation.
	 */
	@Test
	public void testBenutzerHinzufuegen2() throws NoSuchAlgorithmException, InvalidKeySpecException {
		DataManagement.reset();
		String vorname = "Hendrik";
		String nachname = "Meier";
		boolean istNurse = true;
		boolean istMTA = false;
		boolean istLeiter = true;
		boolean istPerson = false;
		BenutzerSteuerung steuerung = new BenutzerSteuerung();
		Pair<String, String> benutzer1 = steuerung.benutzerHinzufuegen(vorname, nachname, istNurse, istMTA, istLeiter,
				istPerson);
		Pair<String, String> benutzer2 = steuerung.benutzerHinzufuegen(vorname, nachname, false, true, false, true);
		Pair<String, String> benutzer3 = steuerung.benutzerHinzufuegen(vorname, nachname, false, istMTA, istLeiter,
				istPerson);
		Benutzer benutzer22 = DataFilter.filterBenutzer("", "", benutzer2.getKey()).get(0); // TODO kann man hier davon
																							// ausgehen, dass der
																							// Benutzer eindeutig ist?
		steuerung.benutzerLoeschen(benutzer22);
		Pair<String, String> benutzer4 = steuerung.benutzerHinzufuegen(vorname, nachname, false, true, false,
				istPerson);
		assertNotEquals(benutzer3.getKey(), benutzer4.getKey());
	}

	/**
	 * Die Methode testBenutzerLoeschen testet ob ein geloeschter Benutzer auch
	 * wirklich geloescht wurde
	 * @throws NoSuchAlgorithmException Fehler, wenn genutzter Algorithmus nicht existiert.
	 * @throws InvalidKeySpecException Fehler fuer ungueltige Key-Spezifikation.
	 */
	@Test
	public void testBenutzerLoeschen() throws NoSuchAlgorithmException, InvalidKeySpecException {
		BenutzerSteuerung steuerung = new BenutzerSteuerung();
		Pair<String, String> benutzer1 = steuerung.benutzerHinzufuegen("Hendrik", "Forti", false, false, true, false);
		Benutzer benutzer11 = DataFilter.filterBenutzer("", "", benutzer1.getKey()).get(0); 
		steuerung.benutzerLoeschen(benutzer11);
		assertEquals(0, DataFilter.filterBenutzer("", "", benutzer1.getKey()).size());
	}

	/**
	 * Die Methode testBenutzerLoeschen testet ob ein Benutzer mit Visiten nicht
	 * geloescht werden kann
	 * @throws NoSuchAlgorithmException Fehler, wenn der genutzte Algorithmus nicht existiert.
	 * @throws InvalidKeySpecException Fehler fuer eine ungueltige Key-Spezifikation.
	 */
	@Test
	public void testBenutzerLoeschenStudyNurse() throws NoSuchAlgorithmException, InvalidKeySpecException {
		BenutzerSteuerung steuerung = new BenutzerSteuerung();
		Pair<String, String> benutzer1 = steuerung.benutzerHinzufuegen("Hendrik", "Forti", true, true, true, true);

		Benutzer benutzer11 = DataFilter.filterBenutzer("", "", benutzer1.getKey()).get(0);
		benutzer11.addVisite(
				new Visite(DatumHelfer.erzeugeDatum("20.03.2020"), 0, benutzer11, new Patient(50, "Name1", "Name 2"),
						new Studie("Studie 1", 50), new VisitenVorlage("Visite 1", new ArrayList<String>(), 0)));
		assertThrows(IllegalArgumentException.class, () -> {
			steuerung.benutzerLoeschen(benutzer11);
		});
	}

	/**
	 * die Methode testGetBenutzer testet die Methode getBenutzer und schaut ob
	 * wirklich eine leere Liste zurueck gegeben wird
	 */
	@Test
	public void getBenutzer() {

		BenutzerSteuerung steuerung = new BenutzerSteuerung();
		assertNotNull(steuerung.getBenutzer());
	}

	/**
	 * Die Methode testBenutzerHinzufuegen1 testet die Methode benutzerHinzufuegen.
	 * Es wird ueberprueft ob die Methode die gewuenschte illegalArgumentException
	 * wirft, wenn die Personalabeitlungsleiterin versucht einen Benutzer ohne Rolle
	 * hinzuzufuegen.
	 */
	@Test
	public void testBenutzerHinzufuegen1() {
		BenutzerSteuerung steuerung = new BenutzerSteuerung();
		assertThrows(IllegalArgumentException.class, () -> {
			steuerung.benutzerHinzufuegen("Hugo", "Fischer", false, false, false, false);
		});
	}

	/**
	 * Die Methode testBenutzerBearbeiten1 testet die Methode benutzerBearbeiten.
	 * Die Methode ueberprueft, ob die gewuenste IllegalArgumentException geworfen
	 * wird, wenn bei der Eingabe der neuen Werte das Testfeld Vorname leer bleibt
	 * 
	 * @throws NoSuchAlgorithmException Fehler, wenn genutzter Algorithmus nicht existiert.
	 * @throws InvalidKeySpecException Fehler fuer ungueltige Key-Spezifikation.
	 */
	@Test
	public void testBenutzerBearbeiten1() throws NoSuchAlgorithmException, InvalidKeySpecException {
		String passwort = "diesIstEinSuperSicheresPasswort";
		AuthentifizierungSteuerung authentifizierung = AuthentifizierungSteuerung.getInstance();
		PasswortHash verschluesseltesPasswort = authentifizierung.hashSecurely(passwort.toCharArray());
		Benutzer benutzer1 = new Benutzer("Hogoline", "Freundmann", verschluesseltesPasswort);
		benutzer1.setBiobankLeiter(true);
		BenutzerSteuerung steuerung = new BenutzerSteuerung();
		assertThrows(IllegalArgumentException.class, () -> {
			steuerung.benutzerBearbeiten(benutzer1, "", "Freundmann", false, true, true, false);
		});

	}

	/**
	 * Die Methode testBenutzerBearbeiten testet die Methode benutzerBearbeiten Sie
	 * ueberprueft, ob die Methode die gewuenschte Exception wirft, wenn dem
	 * Benutzer keine Rolle mehr zugeordnet ist
	 * 
	 * @throws NoSuchAlgorithmException Fehler, wenn genutzter Algorithmus nicht existiert.
	 * @throws InvalidKeySpecException Fehler fuer ungueltige Key-Spezifikation.
	 */
	@Test
	public void testBenutzerBearbeiten2() throws NoSuchAlgorithmException, InvalidKeySpecException {
		String passwort = "Passwort";
		AuthentifizierungSteuerung authentifizierung = AuthentifizierungSteuerung.getInstance();
		PasswortHash verschluesseltesPasswort = authentifizierung.hashSecurely(passwort.toCharArray());
		Benutzer benutzer = new Benutzer("Ronaldo", "Schimmelino", verschluesseltesPasswort);
		BenutzerSteuerung steuerung = new BenutzerSteuerung();
		assertThrows(IllegalArgumentException.class, () -> {
			steuerung.benutzerBearbeiten(benutzer, "Ronaldo", "Schimmelino", false, false, false, false);
		});
	}

	/**
	 * Die Methode testBenutzerBearbeiten teste die Methode benutzerBearbeiten. Sie
	 * testet, ob der geaenderte Vorname wirklich geandert wurde.
	 * 
	 * @throws NoSuchAlgorithmException Fehler, wenn genutzter Algorithmus nicht existiert.
	 * @throws InvalidKeySpecException Fehler fuer ungueltige Key-Spezifikation.
	 */
	@Test
	public void testBenutzerBearbeiten() throws NoSuchAlgorithmException, InvalidKeySpecException {
		String passwort = "Passwort";
		AuthentifizierungSteuerung authentifizierung = AuthentifizierungSteuerung.getInstance();
		PasswortHash verschluesseltesPasswort = authentifizierung.hashSecurely(passwort.toCharArray());
		Benutzer benutzer = new Benutzer("Ronaldo", "Schimmelino", verschluesseltesPasswort);
		BenutzerSteuerung steuerung1 = new BenutzerSteuerung();
		steuerung1.benutzerBearbeiten(benutzer, "Ronaldinio", "Schimmelino", false, false, false, true);
		assertEquals(benutzer.getVorname(), "Ronaldinio");
	}

}
