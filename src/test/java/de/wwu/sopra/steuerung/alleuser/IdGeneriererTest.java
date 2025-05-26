package de.wwu.sopra.steuerung.alleuser;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.ArrayList;
import java.util.NoSuchElementException;

import org.junit.jupiter.api.Test;

import de.wwu.sopra.model.IdGenerierer;

/**
 * Testet die Klasse IdGenerierer
 * 
 * @author Alex
 *
 */
public class IdGeneriererTest {
	/**
	 * Testet ob bei einer Range von nur einer Id genau diese Id zurueckgegeben wird
	 */
	@Test
	public void testPatientenIdErzeugenUndAbfragen() {
		IdGenerierer ig = new IdGenerierer(5, 5);
		assertEquals(5, ig.getNeuePatientenId());
	}

	/**
	 * Testet ob bei einer Range von 7-10 alle Ids vorhanden sind
	 */
	@Test
	public void testPatientenIdErzeugenMehrfachEindeutig() {
		IdGenerierer ig = new IdGenerierer(7, 10);

		ArrayList<Integer> ids = new ArrayList<Integer>();
		for (int i = 0; i < 4; i++) {
			ids.add(ig.getNeuePatientenId());
		}

		boolean allIdsExist = true;
		for (int i = 7; i <= 10; i++) {
			if (!ids.contains(i))
				allIdsExist = false;
		}

		assertTrue(allIdsExist);
	}

	/**
	 * Testet ob die korrekte Exception geworfen wird, wenn keine Id mehr verfuegbar
	 * ist
	 */
	@Test
	public void testPatientenIdKeineIdVerfuegbar() {
		IdGenerierer idGenerierer = new IdGenerierer(3, 5);
		for (int i = 0; i < 3; i++)
			idGenerierer.getNeuePatientenId();
		assertThrows(NoSuchElementException.class, () -> idGenerierer.getNeuePatientenId());
	}
}
