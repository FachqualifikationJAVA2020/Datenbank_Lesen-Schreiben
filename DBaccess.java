package db;

import java.sql.*;
import java.util.ArrayList;

public class DBaccess {
	/*
	 * Hier sollen alle Zugriffe auf die Datenbank erfolgen
	 * 
	 * Im MVC-Pattern w�re das eine Klasse auf der Model-Seite,
	 * sie erzeugt die Instanzen, von den das Programm handelt
	 */
	private Statement st;
	/*
	 * Hier oben wollen wir auch die Login-Daten ablegen
	 */
	private String connectstring = "jdbc:mysql://localhost/webshop";
	private String username = "root";
	private String password = "";
	private Connection conn;
	
	public DBaccess() {
		/*
		 * Hier sollen die wichtigen Felder initialisiert werden
		 */
		try {
			conn = DriverManager.getConnection(connectstring,username,password);
			st = conn.createStatement();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	
	public Kunde leseKunde(int kundennummer) {
		/*
		 * Soll einen Kunden anhand seiner Kundennummer laden
		 */
		Kunde k =  null;
		/*
		 * Zuerst ein SQL-Statement schreiben
		 */
		String sql = "SELECT * FROM Kunde "
				+ "JOIN adresse ON adresse.adress_id = kunde.adress_id "
				+ "JOIN plz ON plz.plz = adresse.plz "
				+ "WHERE Kunde_Id = "+kundennummer+";";
		System.out.println(sql);
		
		try {
			ResultSet rs = st.executeQuery(sql);
			/*
			 * Es kann nur einen Kunden geben oder eben nicht
			 * da die Kunde_Id einzigartig sein muss (Prim�rschl�ssel)
			 */
			if(rs.next()) {
				String vorname = rs.getString("vorname");
				String nachname = rs.getString("nachname");
				String strasse = rs.getString("strasse");
				String hausnummer = rs.getString("hausnummer");
				String plz = rs.getString("plz");
				String stadt = rs.getString("stadt");
				
				k = new Kunde(kundennummer, vorname, nachname, strasse, hausnummer, plz, stadt);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		
		return k;
	}
	
//	public Kunde schreibeKunde(Kunde k) {
//
//		String sql = "INSERT INTO plz (plz, stadt)\r\n"
//				+ "VALUES ("+k.getPlz()+",'"+k.getStadt()+"');\r\n"
//				+ "INSERT INTO adresse (strasse, hausnummer, plz)\r\n"
//				+ "VALUES ('"+k.getStrasse()+"','"+k.getHausnummer()+"',"+k.getPlz()+");\r\n"
//				+ "INSERT INTO kunde (vorname, nachname, adress_id)\r\n"
//				+ "VALUES ('"+k.getVorname()+"','"+k.getNachname()+"', LAST_INSERT_ID());\r\n";
//		System.out.println(sql);
//
//		int kundeid;
//		try {
//			int zeilen = st.executeUpdate(sql,Statement.RETURN_GENERATED_KEYS);
//			System.out.println(zeilen +  " Kunden eingef�gt");
//			ResultSet keys = st.getGeneratedKeys();
//			keys.next();
//			kundeid = keys.getInt(1);
//			System.out.println(kundeid);
//		} catch (SQLException e) {
//			e.printStackTrace();
//		}
//		
//		return k;
//	}
	
	public Kunde schreibeKunde(Kunde k) {
		int plz = Integer.parseInt(k.getPlz());
		schreibePLZ(plz, k.getStadt());
		schreibeAdresse(k.getStrasse(), k.getHausnummer(), plz);
		String sql = "INSERT INTO kunde (vorname, nachname, adress_id)\r\n"
				+ "VALUES ('"+k.getVorname()+"','"+k.getNachname()+"', LAST_INSERT_ID());\r\n";
		System.out.println(sql);

		int kundeid;
		try {
			int zeilen = st.executeUpdate(sql,Statement.RETURN_GENERATED_KEYS);
			System.out.println(zeilen +  " Kunden eingef�gt");
			ResultSet keys = st.getGeneratedKeys();
			keys.next();
			kundeid = keys.getInt(1);
			System.out.println(kundeid);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return k;
	}
	
	/*
	 * Schreiben Sie eine Methode leseAngebot(int angebotId), 
	 * die ein einzelnes Angebot anhand der Angebot_Id ausliest 
	 * und als Angebot-Objekt zur�ckgibt. Erstellen Sie dazu eine Angebot-Klasse 
	 * mit den Feldern int angebotid, String bezeichnung, beschreibung und double preis
	 */
	public Angebot leseAngebot(int angebotId) {
		Angebot a = null;
		
		String sql = "SELECT * FROM angebot "
				+ "WHERE angebot_id = "+angebotId+";";
		
		try {
			ResultSet rs = st.executeQuery(sql);
			/*
			 * rs.next() greift auf das n�chste Element zu
			 * 
			 * Zu Beginn sieht mein resultSet z.B. so aus,
			 * der Zeiger zeigt VOR das erste Element
			 *   |1.Angebot|2.Angebot|
			 * ^
			 * |
			 * 
			 * Wenn ich einmal rs.next() mache zeigt der Zeiger AUF
			 * das erste Element (und au�erderm gibt die Methode true zur�ck,
			 * weil ein Element existiert)
			 *   |1.Angebot|2.Angebot|
			 *        ^
			 *        |
			 * Jetzt liest rs.getString() die Werte des 1.Angebots
			 * 
			 * Wenn ich ein zweites mal rs.next() mache
			 * geht der Zeiger auf das n�chste Element und liest dessen Werte
			 * und so weiter
			 */
			if(rs.next()) {
				String bezeichnung = rs.getString("bezeichnung");
				String beschreibung = rs.getString("beschreibung");
				double preis = rs.getDouble("preis");
				
				a = new Angebot(angebotId,bezeichnung,beschreibung,preis);
			}
		} catch(SQLException e) {
			e.printStackTrace();
		}
		
		return a;
	}
	
	/*
	 * b)	Schreiben Sie eine Methode ladeAngebotsliste(String bezeichnung)
	 *  die einen String annimmt und eine ArrayList von Angeboten zur�ckgibt
	 */
	public ArrayList<Angebot> ladeAngebotsliste(String bezeichnung){
		ArrayList<Angebot> al = new ArrayList<>();
		
		/*
		 * Wie muss mein sql aussehen,
		 * um alle Angebote zu bekommen, die den String in Ihrer bezeichnung haben?
		 */
		String sql = "SELECT angebot_id FROM angebot "
				+ "WHERE bezeichnung LIKE '%"+bezeichnung+"%';";
		try {
			ResultSet rs = st.executeQuery(sql);
			/*
			 * Damit ich nicht den ganzen Code aus leseAngebot() duplizieren muss,
			 * kann ich die bestehende Methode nutzen
			 * 
			 * Wie bei unserem FancyArray - bestehende Methoden nutzen macht
			 * den Code k�rzer, lesbarer und auch wartbarer, wenn �nderungen auftreten
			 */
			while(rs.next()) {
				/*
				 * Ich lese hier nur die angebot_id aus
				 * und gebe die weiter an leseAngebot()
				 */
				int angebotid= rs.getInt("angebot_id");
				Angebot a = leseAngebot(angebotid);
				al.add(a);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return al;
	}
	
	public void schliesseDB() {
		try {
			conn.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	/*
	 * Welcher Kunde hat welche Angebote bestellt?
	 * oder
	 * welche Angebote hat ein Kunde bestellt?
	 * 
	 * Dazu brauche ich im Prinzip Informationen aus allen sechs Tabellen,
	 * zwischen Kunde und Angebot
	 * existieren 
	 * Bestellung (FK Kundennummer (also Kunde_id))
	 * Bestellposition(FKs Bestellung_Id und Angebot_Id)
	 */
	public ArrayList<Angebot> ladeAngebotsliste(Kunde k){
		ArrayList<Angebot> al = new ArrayList<>();
		int plz = Integer.parseInt(k.getPlz());
		schreibePLZ(plz, k.getStadt());
		String sql = "SELECT angebot.Angebot_Id FROM `Kunde`\r\n"
				+ "JOIN bestellung ON kunde.Kunde_Id = bestellung.Kundennummer\r\n"
				+ "JOIN bestellposition ON bestellung.Bestellung_id = bestellposition.Bestellung_Id\r\n"
				+ "JOIN angebot ON angebot.Angebot_Id = bestellposition.Angebot_Id\r\n"
				+ "WHERE bestellung.kundennummer = " + k.getKundennummer();
		System.out.println(sql);
		try {
			ResultSet rs = st.executeQuery(sql);
			while(rs.next()) {
				/*
				 * Wenn ich wieder die Infos �ber die Methode auslesen will,
				 * dann brauche ich tats�chlich nur die Angebot_Id
				 */
				int angebot_id = rs.getInt("Angebot_Id");
				Angebot a = leseAngebot(angebot_id);
				al.add(a);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return al;
	}
	/*
	 * Welche Kunden haben ein bestimmtes Angebot bestellt?
	 */
	public ArrayList<Kunde> ladeKundenliste(Angebot ang){
		//1. ArrayList initialisieren
		ArrayList<Kunde> al = new ArrayList<>();
		//2. sql erstellen, das die Kunden_Id zur�ckgibt, die zu dem Angebot passen
		String sql = "SELECT Kunde.kunde_Id FROM `Kunde`\r\n"
				+ "JOIN bestellung ON kunde.Kunde_Id = bestellung.Kundennummer\r\n"
				+ "JOIN bestellposition ON bestellung.Bestellung_id = bestellposition.Bestellung_Id\r\n"
				+ "JOIN angebot ON angebot.Angebot_Id = bestellposition.Angebot_Id\r\n"
				+ "WHERE angebot.Angebot_Id = " + ang.getAngebotid();
		//3. sql ausf�hren
		try {
			ResultSet rs = st.executeQuery(sql);
			//4. ResultSet auswerten
			while(rs.next()) {
				int kundennummer = rs.getInt("kunde_Id");
				//5. Kunden-Info zu Kunden_id auslesen (ladeKunde())
				Kunde k = leseKunde(kundennummer);
				//6. Kunde zu ArrayList hinzuf�gen
				al.add(k);
			}	
		} catch(SQLException e) {
			e.printStackTrace();
		}
		//7. ArrayList zur�ckgeben
		return al;
	}
	
	/*
	 * 	Schreiben Sie eine Methode ladeBestellliste(Kunde besteller)
	 * die ein Kunde-Objekt annimmt und alle dem Kunden zugeordneten Bestellungen zur�ckgibt 
	 */
	public ArrayList<Bestellung> ladeBestelliste(Kunde besteller) {
		ArrayList<Bestellung> al = new ArrayList<>();
		
		ArrayList<Angebot> anglist = ladeAngebotsliste(besteller);
		ArrayList<Bestellposition> neu = new ArrayList<>();
		
		//�bernehmen in Bestellpostionen
		for(Angebot a: anglist) {
			String sql = "SELECT `Anzahl` FROM `bestellposition` "
					+ "JOIN bestellung ON bestellposition.bestellung_Id = bestellung.bestellung_id "
					+ "WHERE Angebot_Id = " +a.getAngebotid()+" "
					+ "AND bestellung.kundennummer = "+ besteller.getKundennummer() +";";
			try {
				ResultSet rs = st.executeQuery(sql);
				while(rs.next()) {
					int menge = rs.getInt("Anzahl");
					neu.add(new Bestellposition(menge, a));
				}
				al.add(new Bestellung(besteller, neu));
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return al;
	}
	
	/*
	 * Schreiben in die Datenbank!
	 * 
	 * 1. Schreiben eines Angebots in die DB
	 * Alle Informationen stehen in einer Tabelle
	 * keine Verkn�pfung zwischen den Tabellen notwendig
	 */
	public void schreibeAngebot(Angebot a) {
		/*
		 * Das �bergebene Angebot-Objekt soll in die DB
		 */
		String sql = "INSERT INTO angebot (beschreibung, bezeichnung, preis)\r\n"
			+ "VALUES ('"+ a.getBeschreibung() +"','"+ a.getBezeichnung() +"', "+ a.getPreis() +");";
		System.out.println(sql);
		/*
		 * Jetzt in die DB schreiben
		 */
		
		try {
			/*
			 * executeUpdate gibt ein int zur�ck, wie viele Zeilen von dieser
			 * �nderung betroffen sind
			 * 
			 * Zus�tzlich soll die DB aber auch die vergebene ID angeben,
			 * dazu gebe ich als zweiten Parameter an Statement.RETURN_GENERATED_KEYS
			 * 
			 * Dadurch kann ich die vergebene ID abrufen
			 */
			int betroffeneZeilen = st.executeUpdate(sql,Statement.RETURN_GENERATED_KEYS);
			System.out.println(betroffeneZeilen+" Zeilen eingef�gt");
			/*
			 * Hier wurde jetzt von der DB ein ID vergeben, aber in unserem
			 * Objekt steht noch 0 drin
			 * 
			 * Das wollen wir noch aktualisieren, damit im Angebot a
			 * auch der richtige Wert drinsteht
			 * 1. Wir m�ssen den Wert, die generierte ID von der DB bekommen
			 */
			ResultSet keys = st.getGeneratedKeys();
			keys.next();
			
			/*
			 * Bei diesem ResultSet habe ich keine Info �ber die Spaltennamen,
			 * aber ich kann auf die Spelten auch nach ihrem Index zugreifen
			 * Hier ist es die Spalte 1, die den generierten Schl�ssel enth�lt
			 */
			int id = keys.getInt(1);
			/* 2. Wir m�ssen die ID in das Angebot eintragen k�nnen,
			 * 	brauchen also einen Setter (erledigt)
			 */
			a.setAngebotid(id);
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
	}
	
	public void schreibePLZ(int plz, String stadt) {
		//1. SQL-Statement mit Insert erzeugen
		String sql = "INSERT INTO plz (PLZ, Stadt)\r\n"
				+ "VALUES ("+plz+",'"+stadt+"');";
		System.out.println(sql);
		//2. mit executeUpdate() ausf�hren
		try {
			int betroffeneZeilen = st.executeUpdate(sql);
			//3. Anzahl neu angelegter PLZs ausgeben
			System.out.println(betroffeneZeilen+" Zeilen eingef�gt");
		} catch (SQLException e) {
			e.printStackTrace();
		}
		//Hier brauchen Sie keine generierten Schl�ssel zur�ckgeben lassen
	}
	
	/*
	 * schreibeAdresse(String strasse, String hausnummer, String PLZ) 
	 * die eine Adresse in die DB eintr�gt und mit einer bestehenden PLZ-Eintragung
	 *  verkn�pft. Lassen Sie die AdressId dabei von der Datenbank generieren.
	 *  
	 *  Dabei sollten in der DB nat�rlich passende PK/FK-Beziehungen definiert sein,
	 *  do dass ich hier keine PLZ eintragen kann, die es in der DB nicht gibt
	 *  
	 *  VORSICHT: Die Aufgabe sagt zwar String, aber die DB h�tte die PLZ gerne als int!
	 */
	
	public void schreibeAdresse(String strasse, String hausnummer, int plz) {
		//SQL-Statement
		String sql = "INSERT INTO adresse (strasse, hausnummer, plz) "
				+ "VALUES('"+strasse+"','"+hausnummer+"',"+plz+");";
		System.out.println(sql);
		//executeUpdate()ausf�hren
		try {
			int betroffeneZeilen = st.executeUpdate(sql);
			//Anzahl angelegter Adressen ausgeben
			System.out.println(betroffeneZeilen+" Zeilen eingef�gt");
		} catch(SQLException e) {
			e.printStackTrace();
		}
	}
	
	/*
	 * Ein bestehender Kunde soll ein oder mehrere bestehende Angebote
	 * bestellen k�nnen.
	 * Die ganzen Informationen dazu sollen in einem Bestellobjekt enthalten sein
	 */
	public void schreibeBestellung(Bestellung b) {
		/*
		 * Die Bestellinformationen sollen aus dem
		 * Bestellobjekt in die DB geschrieben werden
		 * 
		 * Bestellung:
		 *  Kunde
		 *  ArrayList<Bestellpositionen>
		 *  
		 * Bestellposition
		 *  Angebot
		 *  bestellmenge - int
		 *  
		 *   1.Bestellung anlegen
		 *   	(KundeId eintragen, BestellId generieren lassen und auslesen)
		 *   2. Bestellposition anlegen f�r jedes bestellte Angebot
		 *   	(AngebotId eintragen, Bestellmenge eintragen, BestellId eintragen)
		 *   
		 *   1. 
		 */
		String sql = "INSERT INTO bestellung (kundennummer) "
				+ "VALUES ("+ b.getBesteller().getKundennummer() +")";
		System.out.println(sql);
		
		int bestellid;//die brauchen wir f�r die Bestellpositionen
		try {
			int zeilen = st.executeUpdate(sql,Statement.RETURN_GENERATED_KEYS);
			System.out.println(zeilen +  " Bestellungen eingef�gt");
			ResultSet keys = st.getGeneratedKeys();
			keys.next();
			bestellid = keys.getInt(1);
//			System.out.println(bestellid);
			//2. F�r jede Bestellposition die Daten in die Tabelle Bestellung eintragen
			for(Bestellposition bp: b.getListe()) {
				sql = "INSERT INTO bestellposition(Angebot_Id,Bestellung_Id, Anzahl) "
						+ "VALUES ("+bp.getAngebot().getAngebotid()+","
						+ bestellid +"," + bp.getBestellmenge()+");";
				System.out.println(sql);
				zeilen = st.executeUpdate(sql);
				System.out.println(zeilen + " Bestellposition eingef�gt");
			}
		} catch(SQLException e) {
			e.printStackTrace();
		}
	}
	
	
}
