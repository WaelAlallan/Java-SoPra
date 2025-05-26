package de.wwu.sopra.steuerung.biobankleiter;

import java.util.ArrayList;

import de.wwu.sopra.datenhaltung.DataFilter;
import de.wwu.sopra.datenhaltung.DataManagement;
import de.wwu.sopra.model.ProbenBehaelterTyp;

/**
 * Die Klasse ProbenBehaelterTypHinzufuegenSteuerung sorgt dafuer, dass
 * ProbenBehaelterTypen hinzugefuegt werden koennen. Diese werden anschliessend
 * gespeichert
 * 
 * @author Jasmin, Pia, Cedric
 *
 */

public class ProbenBehaeltertypHinzufuegenSteuerung {
	/**
	 * Diese Methode erstellt ein Object vom Typ ProbenBehaelterTyp aus den
	 * uebergebenen Parametern und laesst diese abspeichern Falls eine der
	 * uebergebenen Parametern leer oder falsch sind wird eine Fehlermeldung
	 * ausgeworden
	 * 
	 * @param name         , der name des ProbenBehaeltertyp der hinzugefuegt werden
	 *                     soll
	 * @param volumen     das volumen des ProbenBeharltertyps der hinzugefuegt
	 *                     werden soll
	 * @param hoehe        die hoehe des ProbenBehaeltertyps der hinzugefuegt werden
	 *                     soll
	 * @param durchmesser der durchmsser des ProbenBehaeltertyps der hinzugefuegt
	 *                     werden soll
	 * @param deckelTyp   der deckeltyp des ProbenBehaeltertyps der hinzugefuegt
	 *                     werden soll
	 */
	public void probenBehaeltertypHinzufuegen(String name, String volumen, String hoehe, String durchmesser,
			String deckelTyp) {
		if (name.equals("") || volumen.equals("") || hoehe.equals("") || durchmesser.equals("")
				|| deckelTyp.equals("")) {
			throw new IllegalArgumentException("Sie duerfen kein Feld leer lassen");
		}
		if (volumen.contains(",")) {
			volumen = volumen.replace(",", ".");
		}
		if (hoehe.contains(",")) {
			hoehe = hoehe.replace(",", ".");
		}
		if (durchmesser.contains(",")) {
			durchmesser = durchmesser.replace(",", ".");
		}

		try {
			double volumenNeu = Double.parseDouble(volumen);
			double hoeheNeu = Double.parseDouble(hoehe);
			double durchmesserNeu = Double.parseDouble(durchmesser);
			if (volumenNeu <= 0 || hoeheNeu <= 0 || durchmesserNeu <= 0) {
				throw new IllegalArgumentException("Die eingegebenen Zahlen muessen groesser als 0 sein");
			}
			ProbenBehaelterTyp behaelterTyp = new ProbenBehaelterTyp(name, volumenNeu, hoeheNeu, durchmesserNeu,
					deckelTyp);
			DataManagement verwaltung = DataManagement.getInstance();
			verwaltung.speichereProbenBehaelterTyp(behaelterTyp);

		} catch (Exception ex) {
			throw new IllegalArgumentException("Eine der eingebenen Zahlen ist ungueltig");
		}

	}

	/**
	 * Diese Methode gibt alle Probenbehaeltertypen zurueck, welche zu den
	 * eingegebenen Parametern passen
	 * 
	 * @param name        der Name des Probenbehaeltertyp nachdem gesucht werden
	 *                     soll
	 * @param volumen     das Volumen nachdem gesucht werden soll
	 * @param hoehe       die hoehe nachder gesucht werden soll
	 * @param durchmesser der durchmesser nachdem gesucht werden soll
	 * @param deckelTyp    der deckeltyp nachdem gesucht werden soll
	 * @return eine ArrayList von ProbenbehaelterTypen
	 */

	public ArrayList<ProbenBehaelterTyp> getProbenbehaeltertyp(String name, String volumen, String hoehe,
			String durchmesser, String deckelTyp) {
		Double volumenNeu = null;
		Double hoeheNeu = null;
		Double durchmesserNeu = null;

		if (volumen.contains(",") || hoehe.contains(",") || durchmesser.contains(",")) {
			volumen.replaceAll(",", ".");
			hoehe.replaceAll(",", ".");
			durchmesser.replaceAll(",", ".");
		}
		try {
			if (!volumen.equals(""))
				volumenNeu = Double.parseDouble(volumen);

			if (!hoehe.equals(""))
				hoeheNeu = Double.parseDouble(hoehe);

			if (!durchmesser.equals(""))
				durchmesserNeu = Double.parseDouble(durchmesser);
		} catch (Exception e) {
			throw new IllegalArgumentException("Falsche Eingabe");
		}

		return DataFilter.filterProbenBehaelterTypen(name, volumenNeu, hoeheNeu, durchmesserNeu, deckelTyp);
	}

}
