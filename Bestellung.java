package db;

import java.util.ArrayList;

public class Bestellung {
	private Kunde besteller;
	private ArrayList<Bestellposition> liste;
	
	public Bestellung(Kunde besteller, ArrayList<Bestellposition> liste) {
		super();
		this.besteller = besteller;
		this.liste = liste;
	}
	
	public Kunde getBesteller() {
		return besteller;
	}

	public ArrayList<Bestellposition> getListe() {
		return liste;
	}



	@Override
	public String toString() {
		return besteller+": "+liste;
	}
}
