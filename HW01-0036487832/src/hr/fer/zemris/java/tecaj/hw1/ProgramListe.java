/**
 * 
 */
package hr.fer.zemris.java.tecaj.hw1;

/**
 * @author Stjepan
 *	Class that allows some operations on list.
 */
public class ProgramListe {
	/**
	 * One node of list
	 * @author Stjepan
	 *
	 */
	static class CvorListe {
		CvorListe sljedeci;
		String podatak;
	}

	/**
	 * Program to add, write, sort and check size on list.
	 * @param args
	 */
	public static void main(String[] args) {
		CvorListe cvor = null;
		cvor = ubaci(cvor, "Jasna");
		cvor = ubaci(cvor, "Ana");
		cvor = ubaci(cvor, "Ivana");
		System.out.println("Ispisujem listu uz originalni poredak:");
		ispisiListu(cvor);
		cvor = sortirajListu(cvor);
		System.out.println("Ispisujem listu nakon sortiranja:");
		ispisiListu(cvor);
		int vel = velicinaListe(cvor);
		System.out.println("Lista sadrzi elemenata: "+vel);
	}
	
	/**
	 * Gives lists number of elements.
	 * @param cvor first node of list
	 * @return size of list
	 */
	private static int velicinaListe(CvorListe cvor) {
		if (cvor == null) return 0;
		return 1 + velicinaListe(cvor.sljedeci);
	}
	
	/**
	 * Inserts new element in the list.
	 * @param prvi start of the list
	 * @param podatak value of new node
	 * @return new start of the list
	 */
	private static CvorListe ubaci(CvorListe prvi, String podatak) {
		if (prvi == null){
			prvi = new CvorListe();
			prvi.podatak= podatak;
		}
		else if (prvi.sljedeci == null){
			prvi.sljedeci = new CvorListe();
			prvi.sljedeci.podatak = podatak;
		}
		else
			ubaci(prvi.sljedeci, podatak);
		
		return prvi;
	}
	
	/**
	 * Recursively writes all lists elements values
	 * @param cvor first node of the list (root)
	 */
	private static void ispisiListu(CvorListe cvor) {
		if (cvor == null) return;
		
		System.out.println(cvor.podatak);
		ispisiListu(cvor.sljedeci);
	}
	
	/**
	 * Booble sort of list.
	 * @param cvor where to start sorting (root if whole list)
	 * @return new first element of sorted part
	 */
	private static CvorListe sortirajListu(CvorListe cvor) {
		if (velicinaListe(cvor) < 2)
			return cvor;
		boolean isSorted = false;
		while(!isSorted){
			isSorted = true;
			CvorListe cv = cvor;
			while (cv.sljedeci != null){
				if (cv.sljedeci.podatak.compareTo(cv.podatak) < 0){
					String pom = cv.podatak;
					cv.podatak = cv.sljedeci.podatak;
					cv.sljedeci.podatak = pom;
					isSorted = false;
				}
				cv = cv.sljedeci;
			}
		}
		return cvor;
	}

}
