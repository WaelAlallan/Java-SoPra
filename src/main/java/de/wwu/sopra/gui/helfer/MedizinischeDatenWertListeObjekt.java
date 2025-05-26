package de.wwu.sopra.gui.helfer;

import java.util.ArrayList;

import javafx.util.Pair;

/**
 * Hilfklasse der GUI dient zur Formatierung 
 * @author Gruppe 01
 */
public class MedizinischeDatenWertListeObjekt {
	
	private Pair<String, String> medDatenWertPaar;

	/**
	 * Konstruktor setzt das uebergebene Paar als das medDatenWertPaar
	 * @param medDatenWertPaar uebergebenes Paar aus Strings mit Medizinischen Werten
	 */
	public MedizinischeDatenWertListeObjekt(Pair<String, String> medDatenWertPaar) {
		this.medDatenWertPaar = medDatenWertPaar;
	}

	/**
	 * Einfacher getter
	 * @return gibt das Pair an Med Daten zurueck
	 */
	public Pair<String, String> getMedDatenWertPaar() {
		return medDatenWertPaar;
	}

	/**
	 * Ueberschreibung der toString Methode zur besseren Ansicht in der GUI
	 */
	public String toString() {
		return medDatenWertPaar.getKey().toString() + ": " + medDatenWertPaar.getValue().toString();
	}

	/**
	 * Formatiert eine Arraylist aus Pairs um in eine Arraylist aus MedDatenWertListeObjekt
	 * @param medDatenWertPaare zur formatierende Datenpaare
	 * @return Das geforderte Array ohne Pair
	 */
	public static ArrayList<MedizinischeDatenWertListeObjekt> getAlsMDWLObjekte(
			ArrayList<Pair<String, String>> medDatenWertPaare) {
		ArrayList<MedizinischeDatenWertListeObjekt> listenMDWLOAL = new ArrayList<MedizinischeDatenWertListeObjekt>();
		for (int i = 0; i < medDatenWertPaare.size(); ++i)
			listenMDWLOAL.add(new MedizinischeDatenWertListeObjekt(medDatenWertPaare.get(i)));
		return listenMDWLOAL;
	}
}
