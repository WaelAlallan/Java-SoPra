package de.wwu.sopra.gui.helfer;

import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;

/**
 * Klasse, die eine Reihe von Label-/TextField-Kombinationen erstellt und
 * managt.
 * 
 * @author ALexander Leifhelm
 *
 */
public class StackedLabelTextFields {
	private TextField[] textFieldsTF;
	private Label[] labels;
	private GridPane gp;

	/**
	 * Erstellt auf Basis des angegebenen String-Arrays Label-/TextField-Kombis, die
	 * untereinander daregstellt werden.
	 * 
	 * @param labels String-Arrays, welche die Labels der Eingabefelder definieren.
	 */
	public StackedLabelTextFields(String[] labels) {
		int n = labels.length;
		textFieldsTF = new TextField[n];
		this.labels = new Label[n];
		gp = new GridPane();

		for (int i = 0; i < n; ++i) {
			textFieldsTF[i] = new TextField();
			this.labels[i] = new Label(labels[i]);
			gp.add(textFieldsTF[i], 1, i);
			gp.add(this.labels[i], 0, i);
		}

		float[] gewichte = { 20.0f, 80.0f };
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
			if (labels[i].getText().contentEquals(label))
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
			if (labels[i].getText().contentEquals(label))
				return textFieldsTF[i];
		}
		return null;
	}

	/**
	 * Updated ein bestimmtes Textfield innerhalb der Klasse an einer bestimmten
	 * Index Position.
	 * 
	 * @param index     Position in textFields
	 * @param textField neues Textfield
	 */
	public void setTextField(int index, TextField textField) {
		gp.getChildren().remove(textFieldsTF[index]);
		textFieldsTF[index] = textField;
		gp.add(textField, 1, index);
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
