package de.wwu.sopra.steuerung.biobankleiter;

import java.util.ArrayList;

import de.wwu.sopra.datenhaltung.DataFilter;
import de.wwu.sopra.datenhaltung.DataManagement;
import de.wwu.sopra.model.ProbenKategorie;

/**
 * Die Klasse ProbentypHinzufuegenSteuerung ermoeglicht es einen neuen Probentyp
 * zu ersellen und zu speichern.
 * 
 * @author pia, Jasmin, Cedric
 *
 */

public class ProbentypHinzufuegenSteuerung {

	/**
	 * Die Methode legt den neuen Probentyp an und speichert ihn. Sie ueberprueft ob
	 * der String leer ist. Bei einem leeren String wirft sie eine IllegalArgument
	 * Exception.
	 * 
	 * @param nameDerKategorie der Name des anzulegenden Probentyps
	 */

	public void probentypHinzufuegen(String nameDerKategorie) {
		if (nameDerKategorie.equals("")) {
			throw new IllegalArgumentException("Sie muessen einen Kategoriennamen hinzufuegen!");
		}
		if (DataFilter.filterProbenKategorien(nameDerKategorie).size() != 0) {
			throw new IllegalArgumentException("Der Name ist bereits vergeben");
		}
		ProbenKategorie neueKategorie = new ProbenKategorie(nameDerKategorie);
		DataManagement verwaltung = DataManagement.getInstance();
		verwaltung.speichereProbenKategorie(neueKategorie);
	}

	/**
	 * Diese Methode gibt alle ProbenKategorien mit dem passenden Namen zurueck
	 * 
	 * @param name der Name nachdem die Kategorien gefiltert werden sollen
	 * @return die Liste an ProbenKategorien zu denen der eingegebene Name passt
	 */

	public ArrayList<ProbenKategorie> getProbenKategorie(String name) {

		return DataFilter.filterProbenKategorien(name);
	}

}
