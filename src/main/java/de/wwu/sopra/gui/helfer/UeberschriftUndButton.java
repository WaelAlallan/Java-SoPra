package de.wwu.sopra.gui.helfer;

import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Font;
import javafx.scene.text.TextAlignment;


/**
 * Klasse zur Erstellung und zum Management einer Ueberschrift mit optionalen
 * Button links und rechts.
 * 
 * @author Sebastian Huck
 *
 */
public class UeberschriftUndButton {
	private Label l;
	private Button[] buttons;
	private GridPane gp = null;

	/**
	 * Erstellt in einer Reihe einen Button, eine Ueberschrift und einen Button.
	 * Dient ueblicherweise der Erstellung der Ueberschrift mit Hauptmenue-Button.
	 * 
	 * @param ueberschrift Ueberschrift, die in der Mitte dargestellt werden soll.
	 *                     null wird als leere Ueberschrift angesehen.
	 * @param buttonLabel1 Label des ersten, linken Buttons.
	 * @param buttonLabel2 Label des zweiten, rechten Buttons.
	 */
	public UeberschriftUndButton(String ueberschrift, String buttonLabel1, String buttonLabel2) {
		gp = new GridPane();
		buttons = new Button[2];

		if (buttonLabel1 != null) {
			buttons[0] = new Button(buttonLabel1);
			buttons[0].setMaxWidth(Double.MAX_VALUE);
			gp.add(buttons[0], 0, 0);
		}
		if (buttonLabel2 != null) {
			buttons[1] = new Button(buttonLabel2);
			buttons[1].setMaxWidth(Double.MAX_VALUE);
			gp.add(buttons[1], 2, 0);
		}
		if (ueberschrift == null) {
			ueberschrift = "";
		}
		l = new Label(ueberschrift);
		l.setMaxWidth(Double.MAX_VALUE);
		l.setAlignment(Pos.CENTER);
		l.setTextAlignment(TextAlignment.CENTER);
		Font f = GUIHelfer.getStandardFont(36);
		l.setFont(f);
		gp.add(l, 1, 0);

		float[] gw = { 25.0f, 50.0f, 25.0f };
		GUIHelfer.setzeSpaltenGewichte(gp, gw);
		gp.setAlignment(Pos.CENTER);
	}

	/**
	 * Erstellt in einer Reihe einen Button, eine Ueberschrift und einen Button.
	 * Dient ueblicherweise der Erstellung der Ueberschrift mit Hauptmenue-Button.
	 * 
	 * @param ueberschrift Ueberschrift, die in der Mitte dargestellt werden soll.
	 *                     null wird als leere Ueberschrift angesehen.
	 * @param buttonLabel1 Label des ersten, linken Buttons.
	 * @param buttonLabel2 Label des zweiten, rechten Buttons.
	 * @param groesse      groesse der Ueberschrift
	 */
	public UeberschriftUndButton(String ueberschrift, String buttonLabel1, String buttonLabel2, int groesse) {
		gp = new GridPane();
		buttons = new Button[2];

		if (buttonLabel1 != null) {
			buttons[0] = new Button(buttonLabel1);
			buttons[0].setMaxWidth(Double.MAX_VALUE);
			gp.add(buttons[0], 0, 0);
		}
		if (buttonLabel2 != null) {
			buttons[1] = new Button(buttonLabel2);
			buttons[1].setMaxWidth(Double.MAX_VALUE);
			gp.add(buttons[1], 2, 0);
		}
		if (ueberschrift == null) {
			ueberschrift = "";
		}
		l = new Label(ueberschrift);
		l.setMaxWidth(Double.MAX_VALUE);
		l.setAlignment(Pos.CENTER);
		Font f = GUIHelfer.getStandardFont(groesse);
		l.setFont(f);
		gp.add(l, 1, 0);

		float[] gw = { 25.0f, 50.0f, 25.0f };
		GUIHelfer.setzeSpaltenGewichte(gp, gw);
		gp.setAlignment(Pos.CENTER);
	}

	/**
	 * Gibt den Button des angegebenen Labels zurueck.
	 * 
	 * @param label Label des Buttons, der zurueckgegeben weden soll.
	 * @return Button des angegebenen Labels; Nicht gefundenes Label gibt null
	 *         zurueck.
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
