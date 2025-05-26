package de.wwu.sopra.steuerung.alleuser;

import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.KeySpec;
import java.util.ArrayList;
import java.util.Base64;

import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;

import de.wwu.sopra.datenhaltung.DataFilter;
import de.wwu.sopra.datenhaltung.DataManagement;
import de.wwu.sopra.model.Benutzer;
import de.wwu.sopra.model.PasswortHash;

/**
 * Die Klasse Authentifizierung kuemmert sich um das verschluesseln von
 * Passwoertern, das Aendern von Passwoertern, das Ein- und Ausloggen.
 * 
 * @author Jasmin, Pia, Cedric
 *
 */

public class AuthentifizierungSteuerung {

	private static AuthentifizierungSteuerung authentifizierungSteuerung;
	private String currentAlgorithm = "PBKDF2WithHmacSHA512";
	private int currentRounds = 1 << 16;
	private int currentSaltBitLength = 128;
	private int currentKeyBitLength = 512;
	private final SecureRandom random;
	private Benutzer aktiverBenutzer;

	/**
	 * liefert das AuthentifizierungSteuerung-Objekt zurueck
	 * 
	 * @return Objekt eine Singleton-Instanz der Authentifizierungssteuerung
	 */
	public static AuthentifizierungSteuerung getInstance() {
		if (authentifizierungSteuerung == null)
			authentifizierungSteuerung = new AuthentifizierungSteuerung();
		return authentifizierungSteuerung;
	}

	/**
	 * Setzt das Objekt der Authentifizierungssteuerung auf null
	 */

	public static void reset() {
		authentifizierungSteuerung = null;
	}

	/**
	 * Diese Methode weist dem Attribut ein Objekt zu
	 */
	private AuthentifizierungSteuerung() {
		this.random = new SecureRandom();
	}

	/**
	 * Hasht das gegebene Passwort mit den angegebenen Paramerters, erzeugt also
	 * deterministisch eine Byte-Sequenz aus den eingegebenen Daten, so dass das
	 * Klartextpasswort nich mehr leicht wiederhergestellt werden kann Hash the
	 *
	 * @param password  das zu hashende Passwort
	 * @param salt      Hashsalt
	 * @param algorithm anzuwendender Algorithmus
	 * @param rounds    Anzahl Runden
	 * @param keyLen    Laenge des Schluessels
	 * @return Byte-Array als Hashergebnis
	 * 
	 * @throws NoSuchAlgorithmException
	 * @throws InvalidKeySpecException
	 */

	private static byte[] hash(char[] password, byte[] salt, String algorithm, int rounds, int keyLen)
			throws NoSuchAlgorithmException, InvalidKeySpecException {
		KeySpec spec = new PBEKeySpec(password, salt, rounds, keyLen);
		SecretKeyFactory f = SecretKeyFactory.getInstance(algorithm);
		return f.generateSecret(spec).getEncoded();
	}

	/**
	 * Hasht das uebergebene Klartextpasswort ueber eine sichere kryptografische
	 * Methode, so dass man den Klartext nicht mehr leicht wiederherstellen kann
	 * 
	 * @param password Das zu hashende Passwort
	 * @return der resultierende Passworthash
	 * @throws NoSuchAlgorithmException nosuchalgortihm
	 * @throws InvalidKeySpecException  invalidkeyspec
	 */

	public PasswortHash hashSecurely(char[] password) throws NoSuchAlgorithmException, InvalidKeySpecException {
		byte[] salt = new byte[currentSaltBitLength / 8];
		random.nextBytes(salt);
		String algorithm = currentAlgorithm;
		int rounds = currentRounds;
		byte[] hash = hash(password, salt, algorithm, rounds, currentKeyBitLength);
		return new PasswortHash(hash, salt, algorithm, rounds);
	}

	/**
	 * Validate the given char sequence against a an cryptographically hashed
	 * password, i.e., check whether the currently entered (plaintext!) password(the
	 * char sequence) matches the previously entered and cryptographicallyhashed
	 * password.
	 * 
	 * @param against  Der Passworthash, gegen den die Eingabe ausgewertet wird
	 * @param to_check Die Passworteingabe
	 * @return ist das Passwort korrekt, ist dessen Hash also der uebergebene Hash
	 * @throws NoSuchAlgorithmException nosuchalgorithm
	 * @throws InvalidKeySpecException  invalidkeyspecs
	 */

	public boolean validate(PasswortHash against, char[] to_check)
			throws NoSuchAlgorithmException, InvalidKeySpecException {
		byte[] hash_to_check = hash(to_check, against.getSalt(), against.getAlgorithm(), against.getRounds(),
				against.getKeyBitLength());
		Base64.Encoder enc = Base64.getEncoder();
		AuthentifizierungSteuerung.log("Current hash is: " + enc.encodeToString(hash_to_check));
		boolean all_bytes_equal = true;
		for (int i = 0; i < hash_to_check.length; ++i) {
			all_bytes_equal &= (hash_to_check[i] == against.getHash()[i]);
		}
		return all_bytes_equal;
	}

	/**
	 * Check if the given excrypted password is outdated according to the current
	 * specification of secure key/salt bit lengths and hashing algorithm.
	 * 
	 * @param pw Passworthash, der auf Aktualitaet geprueft werden soll
	 * @return ist der PasswortHash veraltet
	 */

	public boolean outdated(PasswortHash pw) {
		return pw.getKeyBitLength() != currentKeyBitLength || pw.getSaltBitLength() != currentSaltBitLength
				|| !pw.getAlgorithm().equals(currentAlgorithm) || pw.getRounds() != currentRounds;
	}

	/**
	 * Log a message for the administrator (or similar), but not the end user
	 * 
	 * @param msg auszugebender String
	 */
	static void log(String msg) {
		System.out.println(" [ " + msg + " ] ");
	}

	/**
	 * Die Methode ueberprueft ob Nutzername und Passwort richtig sind. Passt die
	 * Eingabe, wird das Benutzerobjekt zurueck gegeben. Sind Benutzername oder
	 * Passwort falsch oder passen sie nicht zusammmen, wird null zurueck gegeben.
	 * 
	 * @param benutzername Benutzername
	 * @param passwort     Passwort
	 * @throws NoSuchAlgorithmException nosuchalgorithm
	 * @throws InvalidKeySpecException  invalidkeyspec
	 * @throws IllegalArgumentException nicht unterstuetztes Argument.
	 * @return benutzer (Benutzer) Benutzer, nach dem gefiltert wird
	 */
	public Benutzer anmelden(String benutzername, String passwort)
			throws NoSuchAlgorithmException, InvalidKeySpecException, IllegalArgumentException {
		ArrayList<Benutzer> benutzerList = DataFilter.filterBenutzer("", "", benutzername);
		if (benutzerList.size() == 0) {
			throw new IllegalArgumentException("Der gesuchte Benutzer wurde nicht gefunden");
		}
		Benutzer benutzer = benutzerList.get(0);
		PasswortHash dieses = benutzer.getPasswordHash();
		if (validate(dieses, passwort.toCharArray())) {
			this.aktiverBenutzer = benutzer;
			return benutzer;
		}
		return null;
	}

	/**
	 * Die Methode ueberprueft ob das alte Passwort richig eingegeben wurde und ob
	 * die Passworteingaben des neuen Passwords übereinstimmen. Wenn dies der fall
	 * ist, speichert sie das neue Passwort als hash beim aktuell angemeldeten
	 * Benutzer
	 * 
	 * @param altesPassword  das alte Passwort des aktiven Benutzers
	 * @param neuesPasswort  das neuee Passwort
	 * @param neuesPasswort2 das neue Passwort als Bestaetigung
	 * @throws NoSuchAlgorithmException nosuchalgorithm
	 * @throws InvalidKeySpecException  invalidkeyspec
	 */
	public void aenderePasswort(String altesPassword, String neuesPasswort, String neuesPasswort2)
			throws NoSuchAlgorithmException, InvalidKeySpecException {
		PasswortHash dieses = aktiverBenutzer.getPasswordHash();
		if (validate(dieses, altesPassword.toCharArray())) {
			if (neuesPasswort.contentEquals(neuesPasswort2)) {
				PasswortHash verschluesseltesPasswort = hashSecurely(neuesPasswort.toCharArray());
				aktiverBenutzer.setPasswordHash(verschluesseltesPasswort);
			} else {
				throw new IllegalArgumentException("fehlerhafte Eingabe");
			}
		} else {
			throw new IllegalArgumentException("fehlerhafte Eingabe");
		}
	}

	/**
	 * Die Methode setzt null als aktives Benutzerobjekt.
	 * 
	 * @throws IOException ioexception
	 */
	public void persistAllData() throws IOException {
		DataManagement.persist();
	}

	/**
	 * Setzt den aktivenBenutzer auf den angemeldeten (uebergebenen) Benutzer
	 * 
	 * @param benutzer Der Benutzer welcher gerade angemeldet ist
	 */
	public void setAktiverBenutzer(Benutzer benutzer) {
		this.aktiverBenutzer = benutzer;
	}

	/**
	 * gibt den aktuellen Benutzer zurueck
	 * 
	 * @return den aktuellen Benutzer
	 */

	public Benutzer getAktiverBenutzer() {
		return this.aktiverBenutzer;
	}

}
