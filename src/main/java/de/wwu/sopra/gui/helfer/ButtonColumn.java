package de.wwu.sopra.gui.helfer;

import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.layout.GridPane;

/**
 * Klasse, die eine Spalte aus Buttons automatisch erstellt.
 * 
 * @author Alexander Leifhelm
 *
 */
public class ButtonColumn {
	private GridPane gp;
	private Button[] buttons;

	/**
	 * Erstellt eine Spalte aus Buttons, in denen die Buttons mit gleicher Breite
	 * verteilt sind.
	 * 
	 * @param labels Array aus Strings, welche den Text in den gewuenschten Buttons
	 *               darstellen.
	 */
	public ButtonColumn(String[] labels) {
		int n = labels.length;
		buttons = new Button[n];
		gp = new GridPane();

		for (int i = 0; i < n; ++i) {
			if (labels[i] != null) {
				buttons[i] = new Button(labels[i]);
				buttons[i].setMaxWidth(Double.MAX_VALUE);
				buttons[i].setMaxHeight(Double.MAX_VALUE);
				gp.add(buttons[i], 1, i);
			}
		}
		float[] gewichte = GUIHelfer.gibGleicheGewichte(n);
		GUIHelfer.setzeZeilenGewichte(gp, gewichte);
		GUIHelfer.setzeSpaltenGewichte(gp, GUIHelfer.gibGleicheGewichte(3));
		gp.setAlignment(Pos.CENTER);
	}

	/**
	 * Gibt aus Basis des angegebenen Label-Strings den zugehoergien Button zurueck.
	 * 
	 * @param label Label des angeforderten Buttons.
	 * @return Button mit dem angegebenen Label; Ist das Label nicht gefunden
	 *         worden, wird null zurueckgegeben.
	 */
	public Button getButton(String label) {
		for (int i = 0; i < buttons.length; ++i) {
			if ((buttons[i] != null) && buttons[i].getText().contentEquals(label))
				return buttons[i];
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