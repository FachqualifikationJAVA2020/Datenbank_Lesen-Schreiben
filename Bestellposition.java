package db;

public class Bestellposition {
	private int bestellmenge;
	private Angebot angebot;
	
	public Bestellposition(int bestellmenge, Angebot angebot) {
		this.bestellmenge = bestellmenge;
		this.angebot = angebot;
	}
	
	public int getBestellmenge() {
		return bestellmenge;
	}

	public Angebot getAngebot() {
		return angebot;
	}

	@Override
	public String toString() {
		return angebot.toString()+" "+ bestellmenge+" St.";
	}
	
}
