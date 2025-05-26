package de.wwu.sopra.gui.helfer;

import java.util.ArrayList;

import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.RowConstraints;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;

/**
 * Diese Klasse bietet diverse Methoden zur Vereinfachung und Standardisierung
 * diverser oft notwendiger Methoden.
 * 
 * @author Alexander Leifhelm
 * 
 */
public class GUIHelfer {

	/**
	 * Setzt die Gewichte der Spalten eines GridPanes.
	 * 
	 * @param gp       GridPane, deren Spalten gesetzt werden sollen.
	 * @param gewichte Gewichte pro Spalte (von links nach rechts).
	 */
	public static void setzeSpaltenGewichte(GridPane gp, float[] gewichte) {
		for (int i = 0; i < gewichte.length; ++i) {
			ColumnConstraints cc = new ColumnConstraints();
			cc.setPercentWidth(gewichte[i]);
			gp.getColumnConstraints().add(cc);
		}
	}

	/**
	 * Setzt die Gewichte der Zeilen eines GridPanes.
	 * 
	 * @param gp       GridPane, deren Zeilen gesetzt werden sollen.
	 * @param gewichte Gewichte pro Zeile (von oben nach unten).
	 */
	public static void setzeZeilenGewichte(GridPane gp, float[] gewichte) {
		for (int i = 0; i < gewichte.length; ++i) {
			RowConstraints rc = new RowConstraints();
			rc.setPercentHeight(gewichte[i]);
			gp.getRowConstraints().add(rc);
		}
	}

	/**
	 * Gibt gleiche Gewichte fuer die angegebene Anzahl zurueck. Diese Gewichte
	 * koennen unmittelbar in setzeZeilenGewichte und setzeSpaltenGewichte verwendet
	 * werden, sofern gleiche Gewichte gewuenscht sind.
	 * 
	 * @param n Anzahl an Elementen, fuer die das Gewicht gleichmaessig verteilt
	 *          werden soll.
	 * @return Array mit gleichen Gewichten je Element.
	 */
	public static float[] gibGleicheGewichte(int n) {
		float[] gewichte = new float[n];
		for (int i = 0; i < n; ++i)
			gewichte[i] = 100.0f / n;
		return gewichte;
	}

	/**
	 * Gibt eine Standardschrift mit der angegebenen Schriftgroesse zurueck. Ist die
	 * Schritgroesse keiner als 0, wird eine Standardgroesse genommen.
	 * 
	 * @param fontSize Schriftgroesse; Fuer Zahlen kleiner als 0 wird eine
	 *                 Standardgroesse angenommen.
	 * @return Standardisierte Schriftart mit der gesetzten oder
	 *         Standard-Schriftgroesse.
	 */
	public static Font getStandardFont(int fontSize) {
		if (fontSize < 0)
			fontSize = 17;
		return Font.font("arial", FontWeight.BOLD, fontSize);
	}

	/**
	 * Methode zum ausgeben einer Fehlermeldung
	 * 
	 * @param title  Titel der Fehlermeldung
	 * @param header Kopfzeile der Nachricht
	 * @param text   ausfuehrliche Beschreibung der Fehlermeldung
	 */
	public static void FehlermeldungFormular(String title, String header, String text) {
		Alert alert = new Alert(AlertType.WARNING);
		alert.setTitle(title);
		alert.setWidth(200);
		alert.setHeight(250);
		alert.setHeaderText(header);
		alert.setContentText(text);
		alert.showAndWait();
	}

	/**
	 * Erstellt eine automatische Fehlermeldung auf Basis des Typs des Fehlers und
	 * der Meldung der Exception.
	 * 
	 * @param typ       Typ des anzugebenden Fehlers (Hinzufuegen, Loeschen,
	 *                  Bearbeiten, Auswahl, Filtern) oder den angegebenen Text. Bei
	 *                  Auswahl wird esxception ignoriert.
	 * @param exception Exception, deren Nachricht angezeigt werden soll. Wird bei
	 *                  Typ Auswahl ignoriert.
	 */
	public static void AutomatischesFehlermeldungFormular(String typ, Exception exception) {
		String titel = "Fehler!";
		String header = "";
		String text = "";

		if (typ.contentEquals("Hinzufuegen")) {
			header = "Beim Hinzuf\u00FCgen ist ein Fehler aufgetreten.";
			text = exception.getMessage();
		} else if (typ.contentEquals("Loeschen")) {
			header = "Beim L\u00F6schen ist ein Fehler aufgetreten.";
			text = exception.getMessage();
		} else if (typ.contentEquals("Bearbeiten")) {
			header = "Beim Bearbeiten ist ein Fehler aufgetreten.";
			text = exception.getMessage();
		} else if (typ.contentEquals("Auswahl")) {
			header = "Bei der Auswahl ist ein Fehler aufgetreten.";
			text = "Bitte um Auswahl eines Objektes aus der Liste.";
		} else if (typ.contentEquals("Filtern")) {
			header = "Beim Filtern ist ein Fehler aufgetreten.";
			text = exception.getMessage();
		} else {
			header = typ;
		}

		if (text == null) {
			text = "Es ist ein Fehler aufgetreten.";
		}

		Alert alert = new Alert(AlertType.WARNING);
		alert.setTitle(titel);
		alert.setHeaderText(header);
		alert.setContentText(text);
		alert.showAndWait();
	}

	/**
	 * Gibt eine ListViewUndButtons zurueck, welche die gegebenen Eigenschaften hat
	 * und standardmaessig einen "Weiter"-Button zum Auswaehlen eines Eintrags der
	 * ListView enthaelt.
	 * 
	 * @param <E>           Angabe welchen Typen die anzuzeigenden Objekte haben
	 * @param labelLinks    Label-Text, welcher links von der ListView dargestellt
	 *                      werden soll.
	 * @param spaltenLabels Titel der einzelnen Spalten von den Objekteintraegen
	 * @param objekteAL     ArrayList der anzuzeigenden Objekte
	 * @param hinzufuegen   Gibt an, ob ein weiterer Button zum Anlegen neuer
	 *                      Objekte angelegt werden soll.
	 * @param weiter        Gibt an ob ein weitere Button mit "Weiter" hinzugefuegt
	 *                      werden soll
	 * @return ListViewUndButtons mit der vorgegebenen Standardstruktur.
	 */
	public static <E> ListViewUndButtons getStandardListViewUndButtons(String labelLinks, String[] spaltenLabels,
			ArrayList<E> objekteAL, boolean hinzufuegen, boolean weiter) {
		ListViewUndButtons<E> listeLVUB;
		String[] buttonLabels = new String[2];
		if (hinzufuegen)
			buttonLabels[0] = "Hinzuf\u00FCgen";
		else
			buttonLabels[0] = null;
		if (weiter)
			buttonLabels[1] = "Weiter";
		else
			buttonLabels[1] = null;
		listeLVUB = new ListViewUndButtons(labelLinks, spaltenLabels, objekteAL, buttonLabels);
		return listeLVUB;
	}

	/**
	 * Gibt eine ListViewUndButtons zurueck, welche die gegebenen Eigenschaften hat
	 * und standardmaessig einen "Weiter"-Button zum Auswaehlen eines Eintrags der
	 * ListView enthaelt.
	 * 
	 * @param <E>           Angabe welchen Typen die anzuzeigenden Objekte haben
	 * @param labelLinks    Label-Text, welcher links von der ListView dargestellt
	 *                      werden soll.
	 * @param spaltenLabels Titel der einzelnen Spalten von den Objekteintraegen
	 * @param objekteAL     ArrayList der anzuzeigenden Objekte
	 * @param leftButton    Gibt an, ob ein weiterer Button zum Anlegen neuer
	 *                      Objekte angelegt werden soll.
	 * @param rightButton   Gibt an ob ein weitere Button mit "Weiter" hinzugefuegt
	 *                      werden soll
	 * @return ListViewUndButtons mit der vorgegebenen Standardstruktur.
	 */
	public static <E> ListViewUndButtons getIndivListViewUndButtons(String labelLinks, String[] spaltenLabels,
			ArrayList<E> objekteAL, String leftButton, String rightButton) {
		ListViewUndButtons<E> listeLVUB;
		String[] buttonLabels = new String[2];
		buttonLabels[0] = leftButton;
		buttonLabels[1] = rightButton;

		listeLVUB = new ListViewUndButtons(labelLinks, spaltenLabels, objekteAL, buttonLabels);
		return listeLVUB;
	}

}
