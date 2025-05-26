package de.wwu.sopra.datenhaltung;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.util.ArrayList;

import de.wwu.sopra.model.*;
import de.wwu.sopra.steuerung.alleuser.AuthentifizierungSteuerung;

import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.StreamException;
import com.thoughtworks.xstream.io.xml.StaxDriver;

/**
 * Dies ist die zentrale Datenhaltungsklasse. Sie dient der Persistierung aller
 * Entitaetsobjekte und dem entfernen von Objekten aus der Persistenz.
 * 
 * @author Alex, Lukas
 *
 */
public class DataManagement {
	private static DataManagement dataManagement;
	private static String storePath = "resources/bioBankData.xml";

	private ArrayList<Benutzer> benutzer;
	private ArrayList<Raum> raeume;
	private ArrayList<ProbenKategorie> probenKategorien;
	private ArrayList<ProbenBehaelterTyp> probenBehaelterTypen;
	private ArrayList<Patient> patienten;
	private ArrayList<Studie> studien;

	private IdGenerierer idGenerierer;

	/**
	 * Singleton Methode zum zurueckgeben der Instanz. Dabei wird die
	 * bioBankData.xml vom Standard Pfad eingelesen
	 * 
	 * @return die Instanz
	 */
	public static DataManagement getInstance() {
		return getInstance(storePath);
	}

	/**
	 * Singleton Methode zum zurueckgeben der Instanz. Dabei wird die
	 * bioBankData.xml vom uebergebenen Pfad eingelesen
	 * 
	 * @param storePath der Pfad von dem die Biobank eingelesen wird
	 * @return die Instanz
	 */
	public static DataManagement getInstance(String storePath) {

		if (dataManagement == null) {
			try {
				dataManagement = load(storePath);
				System.out.println("Die Daten von " + storePath + " wurden geladen.");
			} catch (StreamException e) {
				System.out.println("Es wurden keine bestehenden Daten gefunden, eine neue Datenbasis wurde erzeugt.");
				dataManagement = new DataManagement();
				dataManagement.speichereBenutzer(erstelleAdminBenutzer());
			} catch (IOException e) {
				System.out.println("Es wurden keine bestehenden Daten gefunden, eine neue Datenbasis wurde erzeugt.");
				dataManagement = new DataManagement();
				dataManagement.speichereBenutzer(erstelleAdminBenutzer());
			}
		}
		return dataManagement;
	}

	private DataManagement() {
		benutzer = new ArrayList<Benutzer>();
		raeume = new ArrayList<Raum>();
		probenKategorien = new ArrayList<ProbenKategorie>();
		probenBehaelterTypen = new ArrayList<ProbenBehaelterTyp>();
		studien = new ArrayList<Studie>();
		patienten = new ArrayList<Patient>();
		studien = new ArrayList<Studie>();
		idGenerierer = new IdGenerierer(0, 99999);
	}

	/**
	 * Setzt alle Daten in der Biobank zurueck
	 */
	public static void reset() {
		dataManagement = new DataManagement();
	}

	/**
	 * Setzt den Id Generierer der DataManagement Klasse zurueck
	 * 
	 * @param startId Die untere Grenze der Ids
	 * @param endId   Die obere Grenze der Ids
	 */
	public void resetIdGenerierer(int startId, int endId) {
		idGenerierer = new IdGenerierer(startId, endId);
	}

	/**
	 * Laedt ein Datamanagement Objekt vom Dateisystem
	 * 
	 * @param path Der Pfad von dem gelesen wird
	 * @return das geladenene DataManagement Objekt
	 * @throws StreamException wenn kein korrektes XML-Objekt in der Datei steht
	 * @throws IOException     wenn die Datei nicht gefunde wird oder aehnliche
	 *                         IOExceptions
	 */
	public static DataManagement load(String path) throws StreamException, IOException {
		XStream xstream = new XStream(new StaxDriver());
		String xml = readFromFile(path);
		DataManagement dm = (DataManagement) xstream.fromXML(xml);
		return dm;
	}

	/**
	 * Speichert die aktuelle DataManagement Instanz im Dateisystem. Dabei wird der
	 * Standard Pfad verwendet
	 * 
	 * @throws IOException wenn beim Schreiben ein Fehler auftritt
	 */
	public static void persist() throws IOException {
		persist(storePath);
		System.out.println("Die Daten wurden in " + storePath + " gespeichert");
	}

	/**
	 * Speichert die aktuelle DataManagement Instanz im Dateisystem. Dabei wird der
	 * uebergebene Pfad verwendet
	 * 
	 * @param path der Pfad wo die XML gespeichert wird
	 * @throws IOException wenn beim Schreiben ein Fehler auftritt
	 */
	public static void persist(String path) throws IOException {
		File file = new File(path);

		File directory = new File(file.getParent());
		if (!directory.exists()) {
			directory.mkdirs();
		}
		if (!file.exists()) {
			file.createNewFile();
		}

		XStream xstream = new XStream(new StaxDriver());
		String xml = xstream.toXML(dataManagement);
		java.io.FileWriter fw = new java.io.FileWriter(path);
		fw.write(xml);
		fw.close();
	}

	/**
	 * Hilfsmethode zum Lesen einer ganzen Datei vom Dateisystem
	 * 
	 * @param path der Pfad der Datei
	 * @return Der Inhalt des Files
	 * @throws IOException wenn beim Lesen eine IOException auftritt
	 */
	private static String readFromFile(String path) throws IOException {
		StringBuilder resultStringBuilder = new StringBuilder();
		try (BufferedReader br = new BufferedReader(new FileReader(path))) {
			String line;
			while ((line = br.readLine()) != null) {
				resultStringBuilder.append(line).append("\n");
			}
		}
		return resultStringBuilder.toString();
	}

	/**
	 * Erstellen des ersten Benutzers mit Login Daten admin/admin
	 * 
	 * @return der Benutzer
	 */
	private static Benutzer erstelleAdminBenutzer() {
		char[] pwd = { 'a', 'd', 'm', 'i', 'n' };
		AuthentifizierungSteuerung authSteu = AuthentifizierungSteuerung.getInstance();
		PasswortHash ph;
		try { // TODO warum muss hier eine Exception abgefangen werden?
			ph = authSteu.hashSecurely(pwd);
			Benutzer benutzer = new Benutzer("Mustermann", "Max", ph);
			benutzer.setBenutzername("admin");
			benutzer.setPersonalLeiter(true);
			benutzer.setBiobankLeiter(true);
			benutzer.setMTA(true);
			benutzer.setStudyNurse(true);
			return benutzer;
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		} catch (InvalidKeySpecException e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * Gibt eine Liste von allen registrierten Benutzern zurueck
	 * 
	 * @return Die Liste aller im System gespeicherten Benutzer.
	 */

	public ArrayList<Benutzer> getBenutzer() {
		return benutzer;
	}

	/**
	 * Persistiert ein Benutzer Objekt
	 * 
	 * @param benutzer Der zu speichernde Benutzer
	 */
	public void speichereBenutzer(Benutzer benutzer) {
		loescheBenutzer(benutzer);
		this.benutzer.add(benutzer);
	}

	/**
	 * Entfernt ein Benutzer Objekt aus der Persistenz
	 * 
	 * @param benutzer Der zu loeschende Benutzer
	 */
	public void loescheBenutzer(Benutzer benutzer) {
		this.benutzer.remove(benutzer);
	}

	/**
	 * Returnt alle Raeume
	 * 
	 * @return Alle im System gespeicherten Raeume
	 */
	public ArrayList<Raum> getRaeume() {
		return raeume;
	}

	/**
	 * Speichert den angegebenen Raum in der Liste aller Raeume.
	 * 
	 * @param raum der zu speichernde Raum
	 */
	public void speichereRaum(Raum raum) {
		loescheRaum(raum);
		this.raeume.add(raum);

	}

	/**
	 * Loescht den angegebenen Raum aus der Liste aller Raeume.
	 * 
	 * @param raum der zu loeschende Raum
	 */
	public void loescheRaum(Raum raum) {
		this.raeume.remove(raum);
	}

	/**
	 * Returnt alle Probenkategorien
	 * 
	 * @return alle ProbenKategorien
	 */
	public ArrayList<ProbenBehaelterTyp> getProbenBehaelterTypen() {
		return probenBehaelterTypen;
	}

	/**
	 * Fuegt einen ProbenBehaelterTyp der Persistenz hinzu
	 * 
	 * @param probenBehaelterTyp der zu speichernde ProbenBehaelterTyp
	 */
	public void speichereProbenBehaelterTyp(ProbenBehaelterTyp probenBehaelterTyp) {
		loescheProbenBehaelterTyp(probenBehaelterTyp);
		this.probenBehaelterTypen.add(probenBehaelterTyp);
	}

	/**
	 * Loescht einen ProbenBehaelterTyp aus der Persistenz
	 * 
	 * @param probenBehaelterTyp Das zu loeschende Element
	 */
	public void loescheProbenBehaelterTyp(ProbenBehaelterTyp probenBehaelterTyp) {
		this.probenBehaelterTypen.remove(probenBehaelterTyp);
	}

	/**
	 * Returnt alle ProbenKategorien
	 * 
	 * @return die Probenkategorien
	 */
	public ArrayList<ProbenKategorie> getProbenKategorien() {
		return probenKategorien;
	}

	/**
	 * Speichert die uebergebene Probenkategorie.
	 * 
	 * @param probenKategorie die zu speichernde probenKategorie
	 */
	public void speichereProbenKategorie(ProbenKategorie probenKategorie) {
		this.probenKategorien.remove(probenKategorie);
		this.probenKategorien.add(probenKategorie);
	}

	/**
	 * Loescht die uebergebene Probenkategorie.
	 * 
	 * @param probenKategorie die zu loeschende Kategorie
	 */
	public void loescheProbenKategorie(ProbenKategorie probenKategorie) {
		this.probenKategorien.remove(probenKategorie);
	}

	/**
	 * Returnt alle Patienten
	 * 
	 * @return die Patienten
	 */
	public ArrayList<Patient> getPatienten() {
		return patienten;
	}

	/**
	 * Fuegt einen Patienten der Persistenz hinzu
	 * 
	 * @param patient hinzuzufuegendes Objekt
	 */
	public void speicherePatient(Patient patient) {
		loeschePatient(patient);
		patienten.add(patient);
	}

	/**
	 * Loescht einen Patienten aus der Persistenz
	 * 
	 * @param patient Zu loeschendes Objekt
	 */
	public void loeschePatient(Patient patient) {
		patienten.remove(patient);
	}

	/**
	 * Returnt alle Studien
	 * 
	 * @return die Studien
	 */
	public ArrayList<Studie> getStudien() {
		return studien;
	}

	/**
	 * Fuegt eine Studie der Persistenz hinzu
	 * 
	 * @param studie hinzuzufuegendes Objekt
	 */
	public void speichereStudie(Studie studie) {
		loescheStudie(studie);
		studien.add(studie);
	}

	/**
	 * Loescht eine Studie aus der Persistenz
	 * 
	 * @param studie Zu loeschendes Objekt
	 */
	public void loescheStudie(Studie studie) {
		studien.remove(studie);
	}

	/**
	 * Gibt das Objekt zurueck, das sich um die Erzeugung eindeutiger IDs kuemmert
	 * 
	 * @return gibt das eindeutige IdGenerierer Objekt zurueck
	 */
	public IdGenerierer getIdGenerierer() {
		return idGenerierer;
	}

}
