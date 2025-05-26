package de.wwu.sopra.model;

import java.util.ArrayList;
import java.util.NoSuchElementException;
import java.util.Random;

/**
 * Diese Klasse ist zustaendig fuer die Erzeugung eindeutiger Ids
 * 
 * @author Alex
 *
 */
public class IdGenerierer {
	private ArrayList<Integer> freiePatientenIds = new ArrayList<Integer>();

	/**
	 * erzeugt einen neuen IdGenerierer mit IDs in der Range startId-endId
	 * 
	 * @param startId die kleinste ID (inklusiv)
	 * @param endId   die groesste ID (inklusiv)
	 */
	public IdGenerierer(int startId, int endId) {
		for (int i = startId; i <= endId; i++) {
			freiePatientenIds.add(i);
		}
	}

	/**
	 * Gibt eine eindeutige PatientenId zurueck
	 * 
	 * @return die Id die erstellt wurde
	 * @throws NoSuchElementException falls es keine Id mehr gibt
	 */
	public int getNeuePatientenId() throws NoSuchElementException {
		if (freiePatientenIds.size() == 0)
			throw new NoSuchElementException();
		Random random = new Random();
		return freiePatientenIds.remove(random.nextInt(freiePatientenIds.size()));
	}

}
