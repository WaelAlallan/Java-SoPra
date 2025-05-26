package de.wwu.sopra.steuerung.biobankleiter;

import java.util.ArrayList;

import de.wwu.sopra.model.ProbenBehaelterTyp;
import de.wwu.sopra.model.ProbenKategorie;
import de.wwu.sopra.model.ProbenVorlage;
import de.wwu.sopra.model.VisitenVorlage;

/**
 * Diese Klasse kann ProbenVorlagen hinzufuegen, bearbeiten und loeschen
 * 
 * @author Jasmin, Pia, Cedric
 *
 */
public class ProbenVorlageSteuerung {

	/**
	 * Gibt alle ProbenVorlagen einer VisitenVorlage zurueck
	 * 
	 * @param visitenVorlage aktuelle Visitenvorlage
	 * @return die Liste an ProbenVorlagen der Visitenvorlage
	 */
	public ArrayList<ProbenVorlage> getProbenVorlagen(VisitenVorlage visitenVorlage) {
		return visitenVorlage.getProbenVorlage();
	}

	/**
	 * Fuegt neue ProbenVorlage zur VisitenVorlage hinzu
	 * 
	 * @param visitenVorlage aktuelle Visitenvorlage
	 * @param behaelterTyp   der gewuenschte Probenbehaeltertyp
	 * @param kategorie      die gewuenschte Probenkategorie
	 * @return die visitenvorlagge wo die Probenvorlage hinzugefuegt wurde
	 */
	public VisitenVorlage probenVorlageHinzufuegen(VisitenVorlage visitenVorlage, ProbenBehaelterTyp behaelterTyp,
			ProbenKategorie kategorie) {
		if (behaelterTyp == null || kategorie == null) {
			throw new IllegalArgumentException("Ein Behaeltertyp und eine Kategorie muss ausgewaehlt sein");
		}

		ProbenVorlage neu = new ProbenVorlage(kategorie, behaelterTyp);
		visitenVorlage.addProbenVorlage(neu);
		return visitenVorlage;
	}

	/**
	 * Aendert ProbenVorlage
	 * 
	 * @param visitenVorlage aktuelle VisitenVorlage
	 * @param vorlage        die zu bearbeitende Probenvorlage
	 * @param behaelterTyp   der neue Probenbehaeltertyp
	 * @param kategorie      die neue Probenkategorie
	 * @return die visitenvorlage wo die Probenvorlage geaendert wurde
	 */
	public VisitenVorlage probenVorlageBearbeiten(VisitenVorlage visitenVorlage, ProbenVorlage vorlage,
			ProbenBehaelterTyp behaelterTyp, ProbenKategorie kategorie) {
		if (behaelterTyp == null || kategorie == null) {
			throw new IllegalArgumentException("Ein Behaeltertyp und eine Kategorie muss ausgewaehlt sein");
		}
		visitenVorlage.removeProbenVorlage(vorlage);
		vorlage.setProbenBehaelterTyp(behaelterTyp);
		vorlage.setKategorie(kategorie);
		visitenVorlage.addProbenVorlage(vorlage);
		return visitenVorlage;
	}

	/**
	 * Loescht die ProbenVorlage aus der aktuellen VisitenVorlage
	 * 
	 * @param visitenVorlage die aktuelle VisitenVorlage
	 * @param vorlage        die zu loeschende ProbenVorlage
	 * @return die geloeschte Visitenvorlage
	 */
	public VisitenVorlage probenVorlageLoeschen(VisitenVorlage visitenVorlage, ProbenVorlage vorlage) {
		visitenVorlage.removeProbenVorlage(vorlage);
		return visitenVorlage;
	}
}
