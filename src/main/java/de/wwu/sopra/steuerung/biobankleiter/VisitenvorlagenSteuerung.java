package de.wwu.sopra.steuerung.biobankleiter;

import java.util.ArrayList;
import java.util.Arrays;

import de.wwu.sopra.model.Studie;
import de.wwu.sopra.model.VisitenVorlage;

/**
 * Diese Klasse sorgt dafuer, dass Visitenvorlagen erstellt, geaendert und
 * geloescht werden koennen
 * 
 * @author Jasmin, Pia, Cedric
 *
 */
public class VisitenvorlagenSteuerung {
	/**
	 * Mit dieser Methode kann man VisitenVorlagenHinzufuegen
	 * 
	 * @param studie             es wird die studie uebergeben, in der die
	 *                           VisitenVorlage hinzugefuegt werden soll
	 * @param name              Name der VisitenVorlage
	 * @param medizinischeDaten alle Medizinischen Daten, welche gemessen werden
	 *                           sollen in einem String mit Komma getrennt
	 * @param zeitabstandInTagen der Zeitabstand in dem die Visiten durchgefuerht
	 *                           werden sollen
	 * @return Die hinzugefuegte Visitenvorlage
	 */

	public VisitenVorlage visitenvorlageHinzufuegen(Studie studie, String name, String medizinischeDaten,
			String zeitabstandInTagen) {
		if (name.equals("")) {
			throw new IllegalArgumentException("Der Name muss eingetragen werden.");
		}
		int zeitAbstandInTagenInt = 0;
		try {
			zeitAbstandInTagenInt = Integer.parseInt(zeitabstandInTagen);
			if (zeitAbstandInTagenInt < 0) {
				throw new IllegalArgumentException("Der Zeitabstand in Tagen muss eine nicht negative Zahl sein");
			}
		} catch (Exception e) {
			throw new IllegalArgumentException("Der Zeitabstand an Tagen muss eine Ganzzahl sein.");
		}
		ArrayList<String> mediDaten = new ArrayList<String>(Arrays.asList(medizinischeDaten.split(",")));
		VisitenVorlage visitenvorlage = new VisitenVorlage(name, mediDaten, zeitAbstandInTagenInt);
		studie.addVisitenVorlage(visitenvorlage);

		return visitenvorlage;
	}

	/**
	 * Mit dieser Methode werden Visitenvorlagen bearbeiten
	 * 
	 * @param studie             Die Studie in der die Visitenvorlage bearbeitet
	 *                           werden soll
	 * @param visitenVorlage     Die alte VisitenVorlage welche geaendert wird
	 * @param name               Der neue oder der alte Name, falls der Name nicht
	 *                           geaendert werden soll
	 * @param medizinischeDaten  alle Medizinischen Daten, welche gemessen werden
	 *                           sollen in einem String mit Komma getrennt
	 * @param zeitabstandInTagen der Zeitabstand in dem die Visiten durchgefuerht
	 *                           werden sollen
	 * @return die bearbeitete Visitenvorlage
	 */

	public VisitenVorlage visitenvorlageBearbeiten(Studie studie, VisitenVorlage visitenVorlage, String name,
			String medizinischeDaten, String zeitabstandInTagen) {
		if (name.equals("")) {
			throw new IllegalArgumentException("Der Name muss eingetragen werden.");
		}

		if (visitenVorlage.getVisiten().size() != 0) {
			throw new IllegalArgumentException(
					"Fuer diese VisitenVorlage wurden bereits konkrete Visiten erstellt, deshalb ist die VisitenVorlage nicht mehr aenderbar");
		}

		studie.removeVisitenVorlage(visitenVorlage);

		int zeitAbstandInTagenInt = 0;
		try {
			zeitAbstandInTagenInt = Integer.parseInt(zeitabstandInTagen);
		} catch (Exception e) {
			throw new IllegalArgumentException("Der Zeitabstand an Tagen muss eine Ganzzahl sein.");
		}
		ArrayList<String> mediDaten = new ArrayList<String>(Arrays.asList(medizinischeDaten.split(",")));

		visitenVorlage.setMedizinischeDatenBezeichnung(mediDaten);
		visitenVorlage.setName(name);
		visitenVorlage.setZeitZurLetztenVisiteInTagen(zeitAbstandInTagenInt);
		studie.addVisitenVorlage(visitenVorlage);
		return visitenVorlage;

	}

	/**
	 * Mit dieser Methode werden visitenVorlagen geloescht
	 * 
	 * @param studie        die studie bei der die visitenvorlage geloescht werden
	 *                       soll
	 * @param visitenVorlage die von der Studie zu entfernende Visitenvorlage
	 */

	public void visitenvorlageLoeschen(Studie studie, VisitenVorlage visitenVorlage) {
		if (visitenVorlage.getVisiten().size() != 0) {
			throw new IllegalArgumentException(
					"Fuer diese VisitenVorlage wurden bereits konkrete Visiten erstellt, deshalb ist die VisitenVorlage nicht mehr aenderbar");

		}
		studie.removeVisitenVorlage(visitenVorlage);
	}

}
