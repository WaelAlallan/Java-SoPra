package de.wwu.sopra.gui.helfer;

import java.util.ArrayList;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Orientation;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.SelectionMode;
import javafx.scene.layout.GridPane;

/**
 * GUI Hilfsklasse zum erstellen von Listen
 * 
 * @author Gruppe 01
 * @param <E> Typ der ANzuzeigenden Objekte in der Liste
 */
public class ListViewUndButtons<E> {
	private GridPane gp;
	private Label label;
	private ListView<E> objekteLV;
	private ButtonRow buttonsBR;
	private ObservableList<E> objekteOL;

	/**
	 * Erstellt eine Kombination aus einem Label an der linken Seite und eine Liste
	 * mit darunter liegenden Buttons an der rechten Seite.
	 * 
	 * @param labelLinks    Zeichenkette welche links neben der Liste stehen soll
	 * @param spaltenLabels Bezeicchningen der Spalten der Liste
	 * @param objekteAL     ArrayList der anzuzeigenden Objekte
	 * @param buttonLabels  Namen der Buttons, welche unter der Liste stehen sollen
	 */
	public ListViewUndButtons(String labelLinks, String[] spaltenLabels, ArrayList<E> objekteAL,
			String[] buttonLabels) {
		label = new Label(labelLinks);

		objekteOL = FXCollections.<E>observableArrayList(objekteAL);
		objekteLV = new ListView<E>(objekteOL);
		objekteLV.setOrientation(Orientation.VERTICAL);
		objekteLV.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
		objekteLV.setMaxWidth(Double.MAX_VALUE);

		buttonsBR = new ButtonRow(buttonLabels);

		gp = new GridPane();
		if (labelLinks != null) {
			gp.add(label, 0, 0);
			gp.add(objekteLV, 1, 0);
			gp.add(buttonsBR.getGridPane(), 1, 1);
			float[] gewichte = { 20.0f, 80.0f };
			GUIHelfer.setzeSpaltenGewichte(gp, gewichte);
		} else {
			gp.add(objekteLV, 1, 0);
			gp.add(buttonsBR.getGridPane(), 1, 1);
			float[] gewichte = { 0.0f, 100.0f };
			GUIHelfer.setzeSpaltenGewichte(gp, gewichte);
		}

		gp.setAlignment(Pos.CENTER);
	}

	/**
	 * Updated die objekteLV Liste mit einer neuen uebergebenen Liste objekteAL.
	 * 
	 * @param objekteAL ArrayList von objekten die geupdatet werden soll
	 */
	public void updateListe(ArrayList<E> objekteAL) {
		objekteOL = FXCollections.<E>observableArrayList(objekteAL);
		objekteLV.setItems(objekteOL);
	}

	/**
	 * Gibt aus Basis des angegebenen Labels den zugehoerigen Button zuerueck.
	 * 
	 * @param label name des gesuchten Buttons
	 * @return Button mit dem angegebenen Label.
	 */
	public Button getButton(String label) {
		return buttonsBR.getButton(label);
	}

	/**
	 * Returnt das Objekt, welches per SelectionModel mit getSelectedItem
	 * ausgewaehlt ist.
	 * 
	 * @return das ausgewaehlte Objekt.
	 */
	public E getSelektiertesObjekt() {
		E benutzer = objekteLV.getSelectionModel().getSelectedItem();
		return benutzer;
	}

	/**
	 * Setzt das ausgewaehlte Objekt und scrollt dorthin
	 * 
	 * @param objekt auszuwaehlendes Objekt
	 */
	public void setSelektiertesObjekt(E objekt) {
		objekteLV.getSelectionModel().select(objekt);
		objekteLV.scrollTo(objekt);
	}

	/**
	 * Gibt die GridPane mit allen angeforderten Elementen zurueck.
	 * 
	 * @return GridPane mit den gewuenschtem Eigenschaften.
	 */
	public GridPane getGridPane() {
		return gp;
	}

}