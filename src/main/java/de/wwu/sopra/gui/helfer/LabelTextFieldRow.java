package de.wwu.sopra.gui.helfer;

import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;

/**
 * GUI Hilfsklasse, erstellt eine Zeile aus Textfeldern welche in einer Gridpane gespeichert werden.
 * Bietet ebenfalls methoden um die einzelnen Textfelder auslesen zu koennen 
 * 
 * @author Gruppe 01
 */
public class LabelTextFieldRow {
	private Label hauptLabel;
	private String[] eingabenLabelStrings;
	private TextField[] textFieldsTF;
	private GridPane gp;

	/**
	 * Erzeugt ein Gridpane, darin ein uebergebenes Label und horizontal daneben
	 * mehrere Textfields inden die jeweiligen Inputs zum Label eingegeben werden
	 * koennen.
	 * 
	 * @param label          String labelname
	 * @param eingabenLabels textfields zum obigen label
	 */
	public LabelTextFieldRow(String label, String[] eingabenLabels) {
		int n = eingabenLabels.length;
		textFieldsTF = new TextField[n];
		hauptLabel = new Label(label);
		eingabenLabelStrings = eingabenLabels;
		gp = new GridPane();

		float[] gewichte = new float[n + 1];
		gewichte[0] = 20.0f;
		gp.add(hauptLabel, 0, 0);
		for (int i = 0; i < n; ++i) {
			textFieldsTF[i] = new TextField();
			gp.add(textFieldsTF[i], i + 1, 0);
			gewichte[i + 1] = (100.0f - gewichte[0]) / n;
		}

		GUIHelfer.setzeSpaltenGewichte(gp, gewichte);
		gp.setAlignment(Pos.CENTER);
	}

	/**
	 * Setzt alle Eingabefelder auf nicht editierbar und graut sie aus.
	 */
	public void ansehenModus() {
		for (int i = 0; i < textFieldsTF.length; ++i) {
			textFieldsTF[i].setEditable(false);
			textFieldsTF[i].setDisable(true);
		}
	}

	/**
	 * Setzt alle Eingabefelder auf editierbar.
	 */
	public void bearbeitenModus() {
		for (int i = 0; i < textFieldsTF.length; ++i) {
			textFieldsTF[i].setEditable(true);
			textFieldsTF[i].setDisable(false);
		}
	}

	/**
	 * Gibt auf Basis des angebenen String-Labels zurueck, welcher Text im
	 * zugehoerigen Eingabefeld eingegeben wurde.
	 * 
	 * @param label String-Label des angeforderten Eingabefeldes.
	 * @return Text, der im angeforderten Eingabefeld steht.
	 */
	public String getText(String label) {
		for (int i = 0; i < textFieldsTF.length; ++i) {
			if (eingabenLabelStrings[i].contentEquals(label))
				return textFieldsTF[i].getText();
		}
		return null;
	}

	/**
	 * Gibt auf Basis des angebenen String-Labels das zugehoerige TextField zurueck.
	 * 
	 * @param label String-Label des angeforderten Eingabefeldes.
	 * @return das angeforderte Eingabefeld.
	 */
	public TextField getTextField(String label) {
		for (int i = 0; i < textFieldsTF.length; ++i) {
			if (eingabenLabelStrings[i].contentEquals(label))
				return textFieldsTF[i];
		}
		return null;
	}

	/**
	 * Gibt die GridPane mit der Ueberschrift und den beiden Buttons zurueck.
	 * 
	 * @return GridPane mit Button, Ueberschrift, Button in einer Zeile.
	 */
	public GridPane getGridPane() {
		return gp;
	}
}
