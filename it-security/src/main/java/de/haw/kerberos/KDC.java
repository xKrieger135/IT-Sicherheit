package de.haw.kerberos;

/* Simulation einer Kerberos-Session mit Zugriff auf einen Fileserver
 /* KDC-Klasse
 */

import de.haw.kerberos.entities.Auth;
import de.haw.kerberos.entities.Ticket;
import de.haw.kerberos.entities.TicketResponse;

import java.util.*;

public class KDC extends Object {

	private final long tenHoursInMillis = 36000000; // 10 Stunden in
	// Millisekunden

	private final long fiveMinutesInMillis = 300000; // 5 Minuten in
	// Millisekunden

	/* *********** Datenbank-Simulation **************************** */

	private String tgsName;

	private String user; // C

	private long userPasswordKey; // K(C)

	private String serverName; // S

	private long serverKey; // K(S)

	private long serverSessionKey; // K(C,S)

	private long tgsKey; // K(TGS)

	private long tgsSessionKey; // K(C,TGS)

	// Konstruktor
	public KDC(String name) {
		tgsName = name;
		// Eigenen Key fuer TGS erzeugen (streng geheim!!!)
		tgsKey = generateSimpleKey();
	}

	public String getName() {
		return tgsName;
	}

	/* *********** Initialisierungs-Methoden **************************** */

	public long serverRegistration(String sName) {
		/*
		 * Server in der Datenbank registrieren. Rueckgabe: ein neuer geheimer
		 * Schluessel fuer den Server
		 */
		serverName = sName;
		// Eigenen Key fuer Server erzeugen (streng geheim!!!)
		serverKey = generateSimpleKey();
		return serverKey;
	}

	public void userRegistration(String userName, char[] password) {
		/* User registrieren --> Eintrag des Usernamens in die Benutzerdatenbank */
		user = userName;
		userPasswordKey = generateSimpleKeyForPassword(password);

		System.out.println("Principal: " + user);
		System.out.println("Password-Key: " + userPasswordKey);
	}

	/* *********** AS-Modul: TGS - Ticketanfrage **************************** */

	public TicketResponse requestTGSTicket(String userName, String tgsServerName, long nonce) {
		/* Anforderung eines TGS-Tickets bearbeiten. Rueckgabe: TicketResponse fuer die Anfrage */

		TicketResponse tgsTicketResp = null;
		Ticket tgsTicket = null;
		long currentTime = 0;

		// TGS-Antwort zusammenbauen
		if (userName.equals(user) && // Usernamen und Userpasswort in der
		// Datenbank suchen!
		tgsServerName.equals(tgsName)) {
			// OK, neuen Session Key fuer Client und TGS generieren
			tgsSessionKey = generateSimpleKey();
			currentTime = (new Date()).getTime(); // Anzahl mSek. seit
			// 1.1.1970

			// Zuerst TGS-Ticket basteln ...
			tgsTicket = new Ticket(user, tgsName, currentTime, currentTime + tenHoursInMillis, tgsSessionKey);

			// ... dann verschluesseln ...
			tgsTicket.encrypt(tgsKey);

			// ... dann Antwort erzeugen
			tgsTicketResp = new TicketResponse(tgsSessionKey, nonce, tgsTicket);

			// ... und verschluesseln
			tgsTicketResp.encrypt(userPasswordKey);
		}
		return tgsTicketResp;
	}

	/*
	 * *********** TGS-Modul: Server - Ticketanfrage
	 * ****************************
	 */

	/**
	 * This method requests a new serverTicket for the given Data.
	 * The message Data (Ticket and Auth are encrypted).
	 * Check that the time is valid, the servername from ticket is the same like the given and, that
	 * The client is the original client.
	 *
	 * @param tgsTicket   This is the ticket from TGS for the client (encrypted).
	 * @param tgsAuth     This is the authentication (encrypted).
	 * @param serverName  This is the name from the server to which we will connect.
	 * @param nonce       A generated "Einmalstempel"
     * @return            Returns a new ticketResponse.
     */
	public TicketResponse requestServerTicket(Ticket tgsTicket, Auth tgsAuth, String serverName, long nonce) {

		TicketResponse ticketResponse = null;
		Ticket serverTicket = null;
		long currentTime = 0;

		// Nur diese Klasse kennt den tgsKey, deswegen hier entschluesseln!
		tgsTicket.decrypt(tgsKey);
		tgsAuth.decrypt(tgsSessionKey);

		// Check if serverName from ticket and given serverName are the same
		// Also we have to check that time from ticket is yet also valid
		// check that auth currenttime is valid (this check is to safe the authentication)
		if (serverName.equals(this.serverName)
				&& timeValid(tgsTicket.getStartTime(), tgsTicket.getEndTime())
				&& timeFresh(tgsAuth.getCurrentTime())
				&& tgsTicket.getClientName().equals(user)) {

			serverSessionKey = generateSimpleKey();

			currentTime = new Date().getTime();

			// Create new serverTicket which will be accepted for 10 hours! Use here ServerSessionKey K(C, S)
			serverTicket = new Ticket(user, serverName, currentTime, currentTime + tenHoursInMillis, serverSessionKey);

			// Now we will encrypt the serverTicket
			serverTicket.encrypt(tgsKey);

			ticketResponse = new TicketResponse(serverSessionKey, nonce, serverTicket);

			ticketResponse.encrypt(tgsSessionKey);
		}

		return ticketResponse;
	}

	/* *********** Hilfsmethoden **************************** */

	private long getServerKey(String sName) {
		// Liefert den zugehoerigen Serverkey fuer den Servernamen zurueck
		// Wenn der Servername nicht bekannt, wird -1 zurueckgegeben
		if (sName.equalsIgnoreCase(serverName)) {
			System.out.println("Serverkey ok");
			return serverKey;
		} else {
			System.out.println("Serverkey unbekannt!!!!");
			return -1;
		}
	}

	private long generateSimpleKeyForPassword(char[] pw) {
		// Liefert einen Schluessel fuer ein Passwort zurueck, hier simuliert als
		// long-Wert
		long pwKey = 0;
		for (int i = 0; i < pw.length; i++) {
			pwKey = pwKey + pw[i];
		}
		return pwKey;
	}

	private long generateSimpleKey() {
		// Liefert einen neuen geheimen Schluessel, hier nur simuliert als
		// long-Wert
		long sKey = (long) (100000000 * Math.random());
		return sKey;
	}

	boolean timeValid(long lowerBound, long upperBound) {
		long currentTime = (new Date()).getTime(); // Anzahl mSek. seit
		// 1.1.1970
		if (currentTime >= lowerBound && currentTime <= upperBound) {
			return true;
		} else {
			System.out.println(
					"-------- Time not valid: " + currentTime + " not in (" + lowerBound + "," + upperBound + ")!");
			return false;
		}
	}

	boolean timeFresh(long testTime) {
		// Wenn die uebergebene Zeit nicht mehr als 5 Minuten von der aktuellen
		// Zeit abweicht,
		// wird true zurueckgegeben
		long currentTime = (new Date()).getTime(); // Anzahl mSek. seit
		// 1.1.1970
		if (Math.abs(currentTime - testTime) < fiveMinutesInMillis) {
			return true;
		} else {
			System.out.println("-------- Time not fresh: " + currentTime + " is current, " + testTime + " is old!");
			return false;
		}
	}
}
