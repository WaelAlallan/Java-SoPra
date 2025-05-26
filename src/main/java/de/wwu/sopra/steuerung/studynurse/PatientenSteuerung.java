package de.wwu.sopra.steuerung.studynurse;

import java.text.DateFormat;
import java.util.Date;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.NoSuchElementException;

import java.text.ParseException;

import de.wwu.sopra.datenhaltung.DataFilter;
import de.wwu.sopra.datenhaltung.DataManagement;
import de.wwu.sopra.model.Benutzer;
import de.wwu.sopra.model.Patient;
import de.wwu.sopra.model.Probenstatus;
import de.wwu.sopra.model.Studie;
import de.wwu.sopra.model.Visite;
import de.wwu.sopra.model.VisitenVorlage;
import de.wwu.sopra.steuerung.alleuser.AuthentifizierungSteuerung;
import javafx.util.Pair;

/**
 * Die Klasse dient der Organisation von Patienten. Mit ihrer Hilfe koennen
 * Patienten hinzugefuegt, bearbeitet,geloescht und alle Visiten zu einer Studie
 * eines Patienten geloescht werden.
 * 
 * @author pia, Jasmin, Cedric
 *
 */
public class PatientenSteuerung {

	/**
	 * gibt alle den Suchkriterien entsprechenden Patienten zurueck, bei denen die
	 * angemeldete StudyNurse eine Visite hat
	 * 
	 * @param vorname     der vorname nachdem gefiltert werden soll
	 * @param nachname    der nachname nachdem gefiltert werden soll
	 * @param studienname der studienname nachdem gefiltert werden soll
	 * @return eine Liste an Patienten zu denen die eingegebenen Parameter passen
	 */
	public ArrayList<Patient> getPatienten(String vorname, String nachname, String studienname) {
		ArrayList<Studie> studien = null;
		if (!studienname.contentEquals(""))
			studien = DataFilter.filterStudien(studienname, null, null, null);
		return DataFilter.filterPatienten(nachname, vorname, studien, null);
	}

	/**
	 * gibt eine Liste von Paaren mit den Studien des Patienten und dem Datum der
	 * ersten Visite
	 * 
	 * @param patient Patient, dessen Studien mit Anfangsdatum gewuenscht ist
	 * @return Liste von Paaren mit Studien und Anfangsdatum als String
	 */
	public ArrayList<Pair<Studie, String>> getStudienMitDatum(Patient patient) {
		if (patient == null) {
			throw new IllegalArgumentException("Kein Patient uebergeben");
		}
		ArrayList<Visite> visitenDesPatienten = patient.getVisiten();
		ArrayList<Pair<Studie, String>> studienMitDatum = new ArrayList<>();
		ArrayList<Studie> bereitsErfassteStudien = new ArrayList<Studie>();

		for (int i = 0; i < visitenDesPatienten.size(); i++) {
			Visite aktuelleVisite = visitenDesPatienten.get(i);
			Studie aktuelleStudie = aktuelleVisite.getStudie();
			Date aktuellesDatum = aktuelleVisite.getDatum();
			if (!bereitsErfassteStudien.contains(aktuelleStudie)) {
				bereitsErfassteStudien.add(aktuelleStudie);
				DateFormat dateFormat = new SimpleDateFormat("dd.MM.yyyy");
				String datumString = dateFormat.format(aktuellesDatum);
				Pair<Studie, String> neueStudieMitDatum = new Pair<>(aktuelleStudie, datumString);
				studienMitDatum.add(neueStudieMitDatum);
			}

		}

		return studienMitDatum;
	}

	/**
	 * Die Methode emoeglicht das Hinzufuegen von Patienten in das System. Sie
	 * speichert den Patienten mit all seine Attributen.
	 * 
	 * @param vorname   der vorname des Patienten welcher erstellt werden soll
	 * @param nachname  der nachname des Patienten welcher erstellt werden soll
	 * @param anschrift die anschrift des Patienten welcher erstellt werden soll
	 * @param studien   Eine Array list in der Paare von Studie mit ihrem von der
	 *                  Nurse eingegebenen Anfangsdatum der Methode uebergeben
	 *                  werden
	 * @return (Patient) Gibt den neu erstellten Patienten zurueck
	 * @throws IllegalArgumentException Fehler fuer ein ungueltiges Argument.
	 */
	public Patient patientHinzufuegen(String vorname, String nachname, String anschrift,
			ArrayList<Pair<Studie, String>> studien) throws IllegalArgumentException {

		if (vorname.equals("") || nachname.equals("") || anschrift.equals("")) {
			throw new IllegalArgumentException("Fehlende Eingabe");
		}

		// Patient erstellen
		Patient patient = new Patient(0, nachname, vorname);
		patient.setAdresse(anschrift);
		DataManagement.getInstance().speicherePatient(patient);

		// Visiten erstellen
		for (Pair<Studie, String> studieDatumPaar : studien) {
			erstelleVisiten(studieDatumPaar.getKey(), studieDatumPaar.getValue(), patient);
		}

		return patient;
	}

	/**
	 * Diese Methode erstellt die Visiten zu einem Patienten mit einer Studie
	 * 
	 * @param studie      die Studie zu der die Visiten erstellt werden sollen
	 * @param erstesDatum das erste Datum der ersten Visite
	 * @param patient     der Patient zu dem die Visiten erstellt werden soll
	 * @throws IllegalArgumentException wird ausgegeben falls das Datum nicht
	 *                                  gueltig ist
	 */
	void erstelleVisiten(Studie studie, String erstesDatum, Patient patient) throws IllegalArgumentException {
		SimpleDateFormat sdf = new SimpleDateFormat("dd.MM.yyyy");

		Date datum;
		try {
			datum = sdf.parse(erstesDatum);
		} catch (ParseException ex) {
			throw new IllegalArgumentException("Das Datum ist ungueltig");
		}
		for (VisitenVorlage vorlage : studie.getVisitenVorlage()) {
			datum = addiereTageAufDatum(datum, vorlage.getZeitZurLetztenVisiteInTagen());
			int visitenId;
			try {
				visitenId = DataManagement.getInstance().getIdGenerierer().getNeuePatientenId();
			} catch (NoSuchElementException e) {
				throw new IllegalArgumentException("Keine PatientenIds mehr frei");
			}
			Benutzer benutzer = AuthentifizierungSteuerung.getInstance().getAktiverBenutzer();
			Visite visite = new Visite(datum, visitenId, benutzer, patient, studie, vorlage);
			studie.setHatPatienten(true);
		}

	}

	/**
	 * Diese Methode addiert Date auf ein Datum und gibt das Ergebnis als Date
	 * zurueck
	 * 
	 * @param datum                       das datum zu dem Tage addiert werden
	 *                                    sollen
	 * @param zeitZurLetztenVisiteInTagen die Zahl die auf das Datum addiert werden
	 *                                    soll
	 * @return das Ergebnis der Addition im Format Date
	 */

	Date addiereTageAufDatum(Date datum, int zeitZurLetztenVisiteInTagen) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(datum);
		calendar.add(Calendar.DATE, zeitZurLetztenVisiteInTagen);
		return calendar.getTime();
	}

	/**
	 * Die Methode patientBearbeiten ermoeglicht das Bearbeiten von bereits
	 * angelegten Patienten. Die Methode wird aufgerufen, wenn in der GUI im Fenster
	 * Patient Bearbeiten auf Bestaetigen geklickt wird!
	 * 
	 * @param patient   Patient der barbeitet werden soll
	 * @param vorname   Vorname den der Patient hinterher haben soll
	 * @param nachname  Nachname den der Patient hinterher haben soll
	 * @param anschrift Anschrift die der Patient nach den aenderung haben soll
	 * @param studien   studien die der Patient nach der Aenderung zugehoerig sein
	 *                  soll
	 */
	public void patientBearbeiten(Patient patient, String vorname, String nachname, String anschrift,
			ArrayList<Pair<Studie, String>> studien) {
		if (vorname.equals("") || nachname.equals("") || anschrift.equals("")) {
			throw new IllegalArgumentException("fehlende Eingabe");
		}
		if (patient == null) {
			throw new IllegalArgumentException("Kein Patient angegeben");
		}
		patient.setVorname(vorname);
		patient.setNachname(nachname);
		patient.setAdresse(anschrift);

		ArrayList<Studie> studienZuPatient = DataFilter.filterStudien("", null, null, patient);
		for (Pair<Studie, String> studieDatumPaar : studien) {
			if (!studienZuPatient.contains(studieDatumPaar.getKey()))
				erstelleVisiten(studieDatumPaar.getKey(), studieDatumPaar.getValue(), patient);
		}

	}

	/**
	 * Diese Methode ist dafuer verantwortlich einen patient zu Loeschen. Zuerst
	 * wird der richtige Patient herausgesucht. Dann wird kontrolliert, dass keine
	 * Proben des Patienten eingelagert sind, dann werden alle visiten des Patienten
	 * aus dem System entfernt und anschliessend der Patient geloescht
	 * 
	 * @param vorname   der vorname des patienten der geloescht werden soll
	 * @param nachname  der nachname des patienten der geloescht werden soll
	 * @param anschrift die anschrift des patienten der geloescht werden soll
	 */
	public void patientLoeschen(String vorname, String nachname, String anschrift) {
		ArrayList<Patient> patienten = DataFilter.filterPatienten(nachname, vorname, null, null);
		Patient patient = null;
		if (patienten.size() == 0) {
			throw new IllegalArgumentException("Der Patient wurde nicht gefunden");
		}

		if (patienten.size() > 1) {
			for (int i = 0; i < patienten.size(); i++) {
				if (patienten.get(i).getAdresse().equals(anschrift)) {
					patient = patienten.get(i);
					break;
				}
			}
		} else {

			patient = patienten.get(0);
		}

		for (int i = 0; i < patient.getVisiten().size(); i++) {

			Visite visite = patient.getVisiten().get(i);
			for (int j = 0; j < visite.getProben().size(); j++) {
				if (!visite.getProben().get(j).getStatus().equals(Probenstatus.DAUERHAFTENTNOMMEN)) {
					throw new IllegalArgumentException("Es muessen erst alle Proben dauerhaft ausgelagert werden");
				}
			}

		}

		for (int i = 0; i < patient.getVisiten().size(); i++) {
			Studie dieseStudie = patient.getVisiten().get(i).getStudie();
			dieseStudie.removeVisite(patient.getVisiten().get(i));
			DataManagement.getInstance().speichereStudie(dieseStudie);
		}
		DataManagement.getInstance().loeschePatient(patient);
	}

	/**
	 * Diese Methode loescht eine Studie von einem Patienten
	 * 
	 * @param patient der patient bei dem die studie geloescht werden soll
	 * @param studie  die studie die bei dem patienten geloescht werden soll
	 */
	public void loescheStudievonPatient(Patient patient, Studie studie) {
		if (patient == null || studie == null) {
			throw new IllegalArgumentException("Die Daten duerfen nicht leer sein");
		}
		ArrayList<Visite> visitenZuloeschen = new ArrayList<Visite>();

		// Suche alle Visiten zu Patient und Studie
		for (Visite visite : patient.getVisiten()) {
			Studie dieseStudie = visite.getStudie();
			if (dieseStudie.equals(studie)) {
				visitenZuloeschen.add(visite);
			}
		}

		// Pruefe ob noch Proben zum Patienten und der Studie vorhanden sind
		for (int i = 0; i < visitenZuloeschen.size(); i++) {

			Visite visite = visitenZuloeschen.get(i);
			for (int j = 0; j < visite.getProben().size(); j++) {
				if (!visite.getProben().get(j).getStatus().equals(Probenstatus.DAUERHAFTENTNOMMEN)) {
					throw new IllegalArgumentException("Es muessen erst alle Proben dauerhaft ausgelagert werden");
				}
			}
		}

		for (Visite visite : visitenZuloeschen) {
			patient.removeVisite(visite);
			studie.removeVisite(visite);
		}

	}
}
