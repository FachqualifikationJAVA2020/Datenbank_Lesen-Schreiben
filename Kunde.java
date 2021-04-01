package db;

public class Kunde {
	/*
	 * Ein Objekt, das einen Kunden aus der Datenbank enthalten soll
	 * 
	 */
	private int kundennummer;
	private String vorname;
	private String nachname;
	private String strasse;
	private String hausnummer;//kann 4b sein
	private String plz;
	private String stadt;
	
	public Kunde(int kundennummer, String vorname, String nachname, String strasse, 
			String hausnummer, String plz, String stadt) {
		this.kundennummer = kundennummer;
		this.vorname = vorname;
		this.nachname = nachname;
		this.strasse = strasse;
		this.hausnummer = hausnummer;
		this.plz = plz;
		this.stadt = stadt;
	}
	
	
	public Kunde(String vorname, String nachname, String strasse, 
			String hausnummer, String plz, String stadt) {
		this.vorname = vorname;
		this.nachname = nachname;
		this.strasse = strasse;
		this.hausnummer = hausnummer;
		this.plz = plz;
		this.stadt = stadt;
	}
	/*
	 * Weil wir bisher nicht in die DB schreiben können
	 * brauchen wir hier erstmal nur Getter
	 */
	public int getKundennummer() {
		return kundennummer;
	}
	public String getVorname() {
		return vorname;
	}
	public String getNachname() {
		return nachname;
	}
	public String getStrasse() {
		return strasse;
	}
	public String getHausnummer() {
		return hausnummer;
	}
	public String getPlz() {
		return plz;
	}
	public String getStadt() {
		return stadt;
	}
	/*
	 * Die toString()-Methode gibt uns ein Adress-Etikett
	 */
	@Override
	public String toString() {
		return 	vorname+" "+nachname+"\n"+
				strasse+" "+hausnummer+"\n"+
				plz+" "+stadt;
	}
	
	
}
