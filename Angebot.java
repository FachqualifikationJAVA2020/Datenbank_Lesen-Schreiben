package db;

public class Angebot {
	/*
	 * Erstellen Sie dazu eine Angebot-Klasse 
	 * mit den Feldern int angebotid, String bezeichnung, beschreibung und double preis
	 */
	private int angebotid;
	private String bezeichnung, beschreibung;
	private double preis;
	
	public Angebot(int angebotid, String bezeichnung, String beschreibung, double preis) {
		super();
		this.angebotid = angebotid;
		this.bezeichnung = bezeichnung;
		this.beschreibung = beschreibung;
		this.preis = preis;
	}

	public int getAngebotid() {
		return angebotid;
	}
	/*
	 * Um die von der DB vergebene ID setzen zu können, ist ein Setter nötig
	 */
	public void setAngebotid(int angebotid) {
		this.angebotid = angebotid;
	}

	public String getBezeichnung() {
		return bezeichnung;
	}

	public String getBeschreibung() {
		return beschreibung;
	}

	public double getPreis() {
		return preis;
	}
	
	@Override
	public String toString() {
		return bezeichnung+" "+beschreibung+" "+preis;
	}
	
	
}
