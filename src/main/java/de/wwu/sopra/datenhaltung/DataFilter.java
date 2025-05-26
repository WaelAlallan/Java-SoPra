package de.wwu.sopra.datenhaltung;

import java.util.Date;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import de.wwu.sopra.model.Benutzer;
import de.wwu.sopra.model.Gestell;
import de.wwu.sopra.model.Kuehlschrank;
import de.wwu.sopra.model.Patient;
import de.wwu.sopra.model.Probe;
import de.wwu.sopra.model.ProbenBehaelterTyp;
import de.wwu.sopra.model.ProbenKategorie;
import de.wwu.sopra.model.Rack;
import de.wwu.sopra.model.Raum;
import de.wwu.sopra.model.Schublade;
import de.wwu.sopra.model.Segment;
import de.wwu.sopra.model.Studie;
import de.wwu.sopra.model.Visite;

/**
 * stellt statische Methoden bereit, um die Daten im DataManagement Objekt zu
 * filtern
 * 
 * @author Alex, Lukas
 *
 */

public class DataFilter {

	private DataFilter() {

	}

	/**
	 * Methode zum Filtern der uebergebenden generischen Arraylist. Die uebergebende
	 * List Predicates enthaelt hierbei die Filterkriterien nach denen gefiltert
	 * wird.
	 * 
	 * @param <T>        Objekttyp der Objekte in der zufilternen Liste
	 * @param predicates ArrayList<Predicate<T>> Filterkriterien
	 * @param list       ungefilterte ArrayList<T>
	 * @return gefilterte ArrayList<T>
	 */
	private static <T> ArrayList<T> filter(ArrayList<Predicate<T>> predicates, ArrayList<T> list) {
		Stream<T> stream = list.stream();
		for (Predicate<T> criteria : predicates) {
			stream = stream.filter(criteria);
		}

		ArrayList<T> ret = (ArrayList<T>) stream.collect(Collectors.<T>toList());
		if (ret == null)
			ret = new ArrayList<T>();
		return ret;

	}

	/**
	 * Filtert die Benutzer nach Nach-, Vor- und Benutzername
	 * 
	 * @param nachname     Der Nachname des Benutzers
	 * @param vorname      Der Vorname des Benutzers
	 * @param benutzername Der Beutzername des Benutzers
	 * @return die gefilterten Benutzer
	 */
	public static ArrayList<Benutzer> filterBenutzer(String nachname, String vorname, String benutzername) {
		DataManagement dm = DataManagement.getInstance();
		ArrayList<Predicate<Benutzer>> predicates = new ArrayList<Predicate<Benutzer>>();

		if (!nachname.equals(""))
			predicates.add(benutzer -> benutzer.getNachname().contains(nachname));
		if (!vorname.equals(""))
			predicates.add(benutzer -> benutzer.getVorname().contains(vorname));
		if (!benutzername.equals(""))
			predicates.add(benutzer -> benutzer.getBenutzername().equals(benutzername));

		return filter(predicates, dm.getBenutzer());
	}

	/**
	 * gibt nach Attributen gefilterte Raumliste zurueck
	 * 
	 * @param name                   Substring des Namens, wird nicht beachtet wenn
	 *                               leer
	 * @param standort               Substring des Standorts, wird nicht beachtet
	 *                               wenn leer
	 * @param eingestellteTemperatur Die Eingestellte Temperatur, wird nicht
	 *                               beachtet wenn null
	 * @return Die gefilterte Liste von Raeumen
	 */
	public static ArrayList<Raum> filterRaeume(String name, String standort, Double eingestellteTemperatur) {
		DataManagement dm = DataManagement.getInstance();
		ArrayList<Predicate<Raum>> predicates = new ArrayList<Predicate<Raum>>();
		if (!name.equals(""))
			predicates.add(raum -> raum.getName().contains(name));
		if (!standort.equals(""))
			predicates.add(raum -> raum.getStandort().contains(standort));
		if (eingestellteTemperatur != null)
			predicates.add(raum -> raum.getEinstellteTemperatur() == eingestellteTemperatur);

		return filter(predicates, dm.getRaeume());
	}

	/**
	 * Gibt den durch die Attribute gefilterten Kuehlschrank aus der Liste der
	 * Kuehlschraenke zurueck.
	 * 
	 * @param name                   Substring des Namens, wird nicht beachtet wenn
	 *                               leer
	 * @param eingestellteTemperatur die eingestellte Temperatur, wird nicht
	 *                               beachtet wenn null
	 * @param raum                   Das Raum Objekt, wird nicht beachtet wenn null
	 * @return Die gefilterte Liste an Kueschraenken
	 */
	public static ArrayList<Kuehlschrank> filterKuehlschraenke(String name, Double eingestellteTemperatur, Raum raum) {
		ArrayList<Kuehlschrank> kuehlschraenke;
		if (raum != null)
			kuehlschraenke = raum.getKuehlschraenke();
		else {
			kuehlschraenke = new ArrayList<Kuehlschrank>();
			for (Raum r : DataManagement.getInstance().getRaeume()) {
				kuehlschraenke.addAll(r.getKuehlschraenke());
			}
		}

		ArrayList<Predicate<Kuehlschrank>> predicates = new ArrayList<Predicate<Kuehlschrank>>();
		if (!name.equals(""))
			predicates.add(kuehlschrank -> kuehlschrank.getName().contains(name));
		if (eingestellteTemperatur != null)
			predicates.add(kuehlschrank -> kuehlschrank.getEingestellteTemperatur() == eingestellteTemperatur);
		if (raum != null) {
			predicates.add(kuehlschrank -> kuehlschrank.getRaum().equals(raum));
		}

		return filter(predicates, kuehlschraenke);
	}

	/**
	 * Gibt es eine Probenkategorie mit dem uebergebenen Namen, dann wird diese
	 * returned. Ansonsten wird null returned.
	 * 
	 * @param name der Teilstring des Namens der Probenkategorie
	 * @return return die Liste
	 */
	public static ArrayList<ProbenKategorie> filterProbenKategorien(String name) {
		DataManagement dm = DataManagement.getInstance();
		ArrayList<ProbenKategorie> temp = new ArrayList<ProbenKategorie>();

		for (ProbenKategorie probe : dm.getProbenKategorien()) {
			if (probe.getName().equals(name)) {
				temp.add(probe);
			}
			if (name.equals("")) {
				return dm.getProbenKategorien();
			}
		}
		return temp;
	}

	/**
	 * Filtert nach einem ProbenBehaelterTyp anhand der angegebenen Parameter. Es
	 * koennen alle, einige oder keine Parameter angegeben werden.
	 * 
	 * @param name        Der Name. Leerer String nicht spezifiert
	 * @param volumen     Das Volumen. Null falls nicht spezifiziert
	 * @param hoehe       Die Hoehe. Null falls nicht spezifiziert
	 * @param durchmesser Der Durchmesser. Null falls nicht spezifiziert
	 * @param deckelTyp   Der DeckelTyp. Leerer String falls nicht spezifiziert
	 * @return Die Liste der gefilterten Probenbehaeltertypen
	 */
	public static ArrayList<ProbenBehaelterTyp> filterProbenBehaelterTypen(String name, Double volumen, Double hoehe,
			Double durchmesser, String deckelTyp) {
		DataManagement dm = DataManagement.getInstance();
		ArrayList<Predicate<ProbenBehaelterTyp>> predicates = new ArrayList<Predicate<ProbenBehaelterTyp>>();

		if (!name.equals(""))
			predicates.add(typ -> typ.getName().equals(name));
		if (volumen != null)
			predicates.add(typ -> typ.getVolumen() == volumen);
		if (hoehe != null)
			predicates.add(typ -> typ.getHoehe() == hoehe);
		if (durchmesser != null)
			predicates.add(typ -> typ.getDurchmesser() == durchmesser);
		if (!deckelTyp.equals(""))
			predicates.add(typ -> typ.getDeckelTyp().contains(deckelTyp));

		return filter(predicates, dm.getProbenBehaelterTypen());
	}

	/**
	 * Sucht einen Patienten in der Persistenz
	 * 
	 * @param nachname kann leerer String sein, wird dann nicht beachtet
	 * @param vorname  kann leerer String sein, wird dann nicht beachtet
	 * 
	 * @param studien  Eine Liste von Studien. kann null sein, wird dann nicht
	 *                 beachtet
	 * @param benutzer Der ueber die Visite zugeordnete Benutzer zu dem Patient
	 * @return Die gefilterte Liste von Patienten
	 */
	public static ArrayList<Patient> filterPatienten(String nachname, String vorname, ArrayList<Studie> studien,
			Benutzer benutzer) {
		DataManagement dm = DataManagement.getInstance();
		ArrayList<Predicate<Patient>> predicates = new ArrayList<Predicate<Patient>>();
		if (!nachname.contentEquals(""))
			predicates.add(patient -> patient.getNachname().contains(nachname));
		if (!vorname.contentEquals(""))
			predicates.add(patient -> patient.getVorname().contains(vorname));
		if (studien != null)
			predicates.add(patient -> patient.getVisiten().stream()
					.anyMatch(v -> studien.stream().anyMatch(s -> v.getStudie().equals(s))));
		if (benutzer != null)
			predicates.add(patient -> patient.getVisiten().stream().anyMatch(v -> v.getBenutzer().equals(benutzer)));

		return filter(predicates, dm.getPatienten());
	}

	/**
	 * Filtert die Studien im DataManagement Objekt nach den uebergebenen Parametern
	 * 
	 * @param name             Der Name der Studie
	 * @param anzahlTeilnehmer Die Anzahl an Teilnehmern der Studie
	 * @param benutzer         Der Benutzer der Studie
	 * @param patient          Der Patient zu dem Studien gesucht werden sollen
	 * @return Die gefilterte Liste von Studien
	 */
	public static ArrayList<Studie> filterStudien(String name, Integer anzahlTeilnehmer, Benutzer benutzer,
			Patient patient) {
		DataManagement dm = DataManagement.getInstance();
		ArrayList<Predicate<Studie>> predicates = new ArrayList<Predicate<Studie>>();

		if (!name.contentEquals(""))
			predicates.add(studie -> studie.getName().contains(name));
		if (anzahlTeilnehmer != null)
			predicates.add(studie -> studie.getAnzahlTeilnehmer() == anzahlTeilnehmer);
		if (benutzer != null)
			predicates.add(studie -> studie.getVisiten().stream().anyMatch(v -> v.getBenutzer().equals(benutzer)));
		if (patient != null)
			predicates.add(studie -> studie.getVisiten().stream().anyMatch(v -> v.getPatient().equals(patient)));

		return filter(predicates, dm.getStudien());
	}

	/**
	 * gibt Liste aller Proben eines Kuehlschranks zurueck (Koennte alternativ auch
	 * in Logik geschehen)
	 * 
	 * @param kuehlschrank gewuenschter Kuehlschrank
	 * @return Proben in gewuenschtem Kuehlschrank
	 */
	public static ArrayList<Probe> filterProben(Kuehlschrank kuehlschrank) {
		ArrayList<Probe> proben = new ArrayList<Probe>();

		for (Segment segment : kuehlschrank.getSegmente()) {
			for (Gestell gestell : segment.getGestelle())
				for (Schublade schublade : gestell.getSchubladen()) {
					for (Rack rack : schublade.getRacks()) {
						proben = (ArrayList<Probe>) Arrays.stream(rack.getProben()).flatMap(Arrays::stream)
								.collect(Collectors.toList());
					}
				}
		}
		return proben;
	}

	/**
	 * filtert Visiten
	 * 
	 * @param benutzer        kann null sein
	 * @param patientNachname kann ein leerer String sein
	 * @param patientVorname  kann ein leerer String sein
	 * @param datum           kann null sein
	 * @param studien         kann null sein
	 * @param isAbgeschlossen kann null sein
	 * @return ArrayList Liste der gefilterten Visiten
	 */
	public static ArrayList<Visite> filterVisiten(Benutzer benutzer, String patientNachname, String patientVorname,
			Date datum, ArrayList<Studie> studien, Boolean isAbgeschlossen) {
		if (studien == null)
			studien = DataManagement.getInstance().getStudien();

		ArrayList<Visite> visiten = new ArrayList<Visite>();
		for (Studie currentStudie : studien) {
			visiten.addAll(currentStudie.getVisiten());
		}

		ArrayList<Predicate<Visite>> predicates = new ArrayList<Predicate<Visite>>();
		if (benutzer != null)
			predicates.add(visite -> visite.getBenutzer().equals(benutzer));
		if (!patientNachname.contentEquals(""))
			predicates.add(visite -> visite.getPatient().getNachname().contains(patientNachname));
		if (!patientVorname.contentEquals(""))
			predicates.add(visite -> visite.getPatient().getVorname().contains(patientVorname));
		if (datum != null)
			predicates.add(visite -> visite.getDatum().compareTo(datum) == 0);
		if (isAbgeschlossen != null)
			predicates.add(visite -> visite.isAbgeschlossen() == isAbgeschlossen);

		visiten = filter(predicates, visiten);
		return (ArrayList<Visite>) visiten.stream().sorted(Comparator.comparing(Visite::getDatum))
				.collect(Collectors.toList());
	}

	/**
	 * Filtert die Visiten nach der eindeutigen visitenId
	 * 
	 * @param visitenId die visitenId der gesuchten Visite
	 * @return die gesuchte Visite
	 * @throws IllegalArgumentException falls keine, oder mehr als eine Visite
	 *                                  gefunden wird
	 */
	public static Visite filterVisiteById(int visitenId) throws IllegalArgumentException {
		ArrayList<Visite> visiten = new ArrayList<Visite>();
		for (Studie studie : DataManagement.getInstance().getStudien())
			visiten.addAll(studie.getVisiten());

		ArrayList<Predicate<Visite>> predicates = new ArrayList<Predicate<Visite>>();
		predicates.add(visite -> visite.getVisitenId() == visitenId);

		ArrayList<Visite> visitenGefiltert = filter(predicates, visiten);
		if (visitenGefiltert.size() == 0)
			throw new IllegalArgumentException("Die VisitenId wurde nicht gefunden");
		else if (visitenGefiltert.size() > 1)
			throw new IllegalArgumentException("Die VisitenId ist nicht eindeutig");
		return visitenGefiltert.get(0);
	}

	/**
	 * Filtert Proben
	 * 
	 * @param kategorie   Ein SubString der ProbenKategorie, kann ein leerer String
	 *                    sein
	 * @param studienName Ein Substring des Studiennamens, kann ein lee
	 * @param visitenId   Eine eindeutige Id
	 * @param barcode     Barcode zur Probe
	 * @param datum       Datum der Abnahme
	 * @param patient     Patient zur Probe
	 * @return (Arraylist) Liste aller Proben auf die der Filter zutrifft
	 */
	public static ArrayList<Probe> filterProben(String kategorie, String studienName, Integer visitenId,
			Integer barcode, Date datum, Patient patient) {
		ArrayList<Probe> relevanteProben = getRelevanteProbenZuStudie(studienName);

		ArrayList<Predicate<Probe>> predicates = new ArrayList<Predicate<Probe>>();
		if (!kategorie.contentEquals(""))
			predicates.add(probe -> probe.getKategorie().getName().contains(kategorie));

		if (visitenId != null)
			predicates.add(probe -> probe.getVisite().getVisitenId() == visitenId);
		if (barcode != null)
			predicates.add(probe -> probe.getBarcode() == barcode);
		if (datum != null)
			predicates.add(probe -> probe.getVisite().getDatum().compareTo(datum) == 0);
		if (patient != null)
			predicates.add(probe -> probe.getVisite().getPatient().equals(patient));

		return filter(predicates, relevanteProben);
	}

	/**
	 * Hilfsmethode um alle Proben, die zu einer Studie gehoeren zu ermitteln
	 * 
	 * @param studienName Der Name der Studie zu der die Proben gehören
	 * @return Eine Liste der Proben, die zu der Studie gehören
	 */
	private static ArrayList<Probe> getRelevanteProbenZuStudie(String studienName) {
		ArrayList<Studie> relevanteStudien;
		if (!studienName.contentEquals(""))
			relevanteStudien = DataFilter.filterStudien(studienName, null, null, null);
		else
			relevanteStudien = DataManagement.getInstance().getStudien();

		ArrayList<Probe> relevanteProben = new ArrayList<Probe>();
		for (Studie studie : relevanteStudien)
			for (Visite visite : studie.getVisiten())
				relevanteProben.addAll(visite.getProben());
		return relevanteProben;
	}
}
