
package de.wwu.sopra.steuerung.helfer;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * DIese Klasse wandelt einen String in ein Date um
 * 
 * @author Jasmin, Cedric, Pia
 *
 */

public class DatumHelfer {

	/**
	 * Diese Methode wandelt den String in ein Date um
	 * 
	 * @param datum der String welcher umgewandelt wird @return, der String als
	 *               Date
	 * @return Date erzeugtes Datum
	 */
	public static Date erzeugeDatum(String datum) {
		Date date;
		SimpleDateFormat df = new SimpleDateFormat("dd.MM.yyyy");

		df.setLenient(false);
		try {
			date = df.parse(datum);
		} catch (Exception ex) {
			throw new IllegalArgumentException("Datum ist nicht gueltig");
		}
		return date;
	}

	/**
	 * Diese Methode wandelt ein Datum im Format Date in einen string um
	 * 
	 * @param datum das Datum welches umgewandelt werden soll
	 * @return das Datum als String
	 */
	public static String gibDatumString(Date datum) {
		String s;
		SimpleDateFormat df = new SimpleDateFormat("dd.MM.yyy");

		df.setLenient(false);
		try {
			s = df.format(datum);
		} catch (Exception e) {
			throw new IllegalArgumentException("Datum ist nicht gueltig");
		}
		return s;
	}

}
