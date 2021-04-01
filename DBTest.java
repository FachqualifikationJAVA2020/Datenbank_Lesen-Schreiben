package db;

import java.util.ArrayList;

public class DBTest {

	public static void main(String[] args) {
		DBaccess dba = new DBaccess();
		Kunde k = dba.leseKunde(667);
		System.out.println(k);
		k = dba.leseKunde(0);
		System.out.println(k);
		
		Angebot a = dba.leseAngebot(12);
		System.out.println(a);
		a = dba.leseAngebot(14);
		System.out.println(a);
		
		System.out.println("Angebotsliste laden");
		ArrayList<Angebot> al = dba.ladeAngebotsliste("Harry");
		System.out.println(al);
		
		System.out.println("Bestellte Angebote eines Kunden laden");
		k = dba.leseKunde(12);
		System.out.println(dba.ladeAngebotsliste(k));
		
		System.out.println("Kunden ausgeben, die ein Angebot bestellt haben");
		System.out.println(dba.ladeKundenliste(dba.leseAngebot(12)));
		
		/*
		 * Werte schreiben
		 */
		System.out.println("Angebot eintragen");
		/*
		 * Hier gebe ich dem Konstruktor den Wert 0
		 * um anzuzeigen, dass das kein Wert ist, der mit der DB abgeglichen ist
		 * 
		 * Sobald ich das Angebot eingetragen habe, 
		 * trage ich den korrekten Wert in das Objekt ein
		 */
		Angebot neu = new Angebot(0, "Mohnbrötchen", "Hefeteig 200g", 1.20);
		System.out.println(neu);
		dba.schreibeAngebot(neu);
		//danach sollte neu jetzt die richtige AngebotId bekommen haben
		System.out.println(neu.getAngebotid());
		
//		dba.schreibePLZ(45147, "Essen");
		//Beim zweiten und jedem weiteren Mal, wird der Schlüssel dupliziert :(
		
		ArrayList<Bestellung> albest = dba.ladeBestelliste(k);
		System.out.println(albest);
		
		System.out.println("Adresse schreiben");
//		dba.schreibeAdresse("Hochstraße", "10A", 12347);
		/*
		 * Testdaten: eine Bestellung mit 2 Bestellpositionen
		 */
		ArrayList<Bestellposition> albestp = new ArrayList<>();
		albestp.add(new Bestellposition(3, dba.leseAngebot(12)));
		albestp.add(new Bestellposition(5, dba.leseAngebot(100000)));
		
		Bestellung b = new Bestellung(k, albestp);
		
		dba.schreibeBestellung(b);
		
		//zum Testen
		System.out.println(dba.ladeBestelliste(k));
		
		
		Kunde kNeu = new Kunde("Nicole", "Knoten", "Lalala Straße", "13", "45145", "Essen");
		dba.schreibeKunde(kNeu);
		
		//als letztes Connection schließen
		dba.schliesseDB();
	}

}
