package de.wwu.sopra.gui.helfer;

import java.util.ArrayList;

import de.wwu.sopra.model.Studie;
import javafx.util.Pair;

/**
 * Diese Klasse dient nur dazu, eine Kombination aus einer Studie und einem
 * Startdatum vernuenftig in einer ListView darzustellen.
 * 
 * @author Alexander Leifhelm
 *
 */
public class StudieDatumListeObjekt {
	private Pair<Studie, String> studieDatum;

	/**
	 * Erstellt eine StudieDatumListeObjekt
	 * 
	 * @param studieDatum Paar aus Studie und Datum
	 */
	public StudieDatumListeObjekt(Pair<Studie, String> studieDatum) {
		this.studieDatum = studieDatum;
	}

	/**
	 * Gibt eine lesbare Form des hinterlegten Studie-Datum-Paares zurueck.
	 */
	public String toString() {
		return studieDatum.getKey().toString() + " (Start: " + studieDatum.getValue().toString() + ")";
	}

	/**
	 * Gibt das hinterlegte Paar aus Studie und Datum zurueck.
	 * 
	 * @return Paar aus Studie und Datum
	 */
	public Pair<Studie, String> getStudieDatumPaar() {
		return studieDatum;
	}

	/**
	 * Gibt auf Basis der angegebenen Liste aus Paaren an Studie und Datum eine in
	 * der ListView darstellbare Liste aus StudieDatumListeObjekten zurueck.
	 * 
	 * @param studienDatumAL ArraList aus Paaren an Studie und Datum
	 * @return ArrayList aus StudieDatumListeObjekt
	 */
	public static ArrayList<StudieDatumListeObjekt> getAlsStudieDatumListeObjekte(
			ArrayList<Pair<Studie, String>> studienDatumAL) {
		ArrayList<StudieDatumListeObjekt> studienDatumSDLOAL = new ArrayList<StudieDatumListeObjekt>();
		for (int i = 0; i < studienDatumAL.size(); ++i)
			studienDatumSDLOAL.add(new StudieDatumListeObjekt(studienDatumAL.get(i)));
		return studienDatumSDLOAL;
	}
}
