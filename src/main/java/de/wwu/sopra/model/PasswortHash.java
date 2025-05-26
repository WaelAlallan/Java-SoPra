package de.wwu.sopra.model;

import java.util.Base64;

/**
 * PasswortHash Klasse zum Verschluesseln von Passwoerten in eindeutige Hashs.
 */
public class PasswortHash {
	private final byte[] hash;
	private final byte[] salt;
	private final String algorithm;
	private final int rounds;

	/**
	 * Beim Erstellen des PasswortHash muss der hash, salt, der
	 * Verschluesselungsmechanismus und die rounds angegeben werden.
	 * 
	 * @param hash      Hash in byte
	 * @param salt      Salt in byte
	 * @param algorithm zu verwendender Verschluesselungsalsgorithmus
	 * @param rounds    Anzahl der rounds
	 */
	public PasswortHash(byte[] hash, byte[] salt, String algorithm, int rounds) {
		this.hash = hash;
		this.salt = salt;
		this.algorithm = algorithm;
		this.rounds = rounds;
	}

	/**
	 * Gibt den Hash als String zurueck.
	 * 
	 * @return (String) Hashstring
	 */
	public String getHashAsString() {
		Base64.Encoder enc = Base64.getEncoder();
		return enc.encodeToString(hash);
	}

	/**
	 * Gibt den Hash zurueck.
	 * 
	 * @return (Array) Array vom Typ byte
	 */
	public byte[] getHash() {
		return hash;
	}

	/**
	 * Gibt den salt als String zurueck
	 * 
	 * @return (String) Saltstring
	 */
	public String getSaltAsString() {
		Base64.Encoder enc = Base64.getEncoder();
		return enc.encodeToString(salt);
	}

	/**
	 * Gibt den salt als byte-Array zurueck
	 * 
	 * @return (Array) byte
	 */
	public byte[] getSalt() {
		return salt;
	}

	/**
	 * Gibt den Algorithmus als String zurueck.
	 * 
	 * @return (String) Algroithmusstring
	 */
	public String getAlgorithm() {
		return algorithm;
	}

	/**
	 * Gibt die Anzahl der Runden zurueck.
	 * 
	 * @return (int) Anzahl der Runden
	 */
	public int getRounds() {
		return rounds;
	}

	/**
	 * Gibt die keybit Laenge zurueck
	 * 
	 * @return (int) keybitlength
	 */
	public int getKeyBitLength() {
		return hash.length * 8;
	}

	/**
	 * Gibt die SaltBit Laenge zurueck.
	 * 
	 * @return (int) Saltbit
	 */
	public int getSaltBitLength() {
		return salt.length * 8;
	}

	/**
	 * Ueberschreibt die toString Methode und gibt einen konkatenierten String aus
	 * Algorithmus, Runden, Salt und Hash zurueck.
	 */
	public String toString() {
		Base64.Encoder enc = Base64.getEncoder();
		return algorithm + "$" + rounds + "$" + enc.encodeToString(salt) + "$" + enc.encodeToString(hash);
	}

}
