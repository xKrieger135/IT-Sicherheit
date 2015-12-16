package de.haw.kerberos.entities;

/* Simulation einer Kerberos-Session mit Zugriff auf einen Fileserver
 /* Ticket-Klasse
 */
import java.util.*;

public class Ticket extends Object {

	private String myClientName; // Konstruktor-Parameter

	private String myServerName; // Konstruktor-Parameter

	private long myStartTime; // Konstruktor-Parameter

	private long myEndTime; // Konstruktor-Parameter

	private long mySessionKey; // Konstruktor-Parameter

	// Geheimer Schluessel, mit dem das Ticket (simuliert) verschluesselt ist:
	private long myTicketKey;

	private boolean isEncryptedState; // Aktueller Zustand des Objekts

	// Kalenderobjekt zur Zeitumrechnung (fuer Testausgaben)
	private Calendar cal;

	// Konstruktor
	public Ticket(String clientName, String serverName, long startTime,
			long endTime, long sessionKey) {

		myClientName = clientName;
		myServerName = serverName;
		myStartTime = startTime;
		myEndTime = endTime;
		mySessionKey = sessionKey;

		myTicketKey = -1;
		isEncryptedState = false;
		cal = new GregorianCalendar(); // fuer Testausgaben
	}

	public String getClientName() {
		if (isEncryptedState) {
			printError("Zugriff auf verschl�sseltes Ticket (getClientName)");
		}
		return myClientName;
	}

	public String getServerName() {
		if (isEncryptedState) {
			printError("Zugriff auf verschl�sseltes Ticket (getServerName)");
		}
		return myServerName;
	}

	public long getStartTime() {
		if (isEncryptedState) {
			printError("Zugriff auf verschl�sseltes Ticket (getStartTime)");
		}
		return myStartTime;
	}

	public long getEndTime() {
		if (isEncryptedState) {
			printError("Zugriff auf verschl�sseltes Ticket (getEndTime)");
		}
		return myEndTime;
	}

	public long getSessionKey() {
		if (isEncryptedState) {
			printError("Zugriff auf verschl�sseltes Ticket (getSessionKey)");
		}
		return mySessionKey;
	}

	public boolean encrypt(long key) {
		// Ticket mit dem Key verschluesseln.
		// Falls das Ticket bereits verschluesselt ist, wird false zurueckgegeben.
		boolean encOK = false;
		if (isEncryptedState) {
			printError("Ticket ist bereits verschl�sselt");
		} else {
			myTicketKey = key;
			isEncryptedState = true;
			encOK = true;
		}
		return encOK;
	}

	public boolean decrypt(long key) {
		// Ticket mit dem Key entschluesseln.
		// Falls der Key falsch ist oder
		// falls das Ticket bereits entschluesselt ist, wird false zurueckgegeben.
		boolean decOK = false;
		if (!isEncryptedState) {
			printError("Ticket ist bereits entschl�sselt");
		}
		if (myTicketKey != key) {
			printError("Ticket-Entschl�sselung mit key " + key
					+ " ist fehlgeschlagen");
		} else {
			isEncryptedState = false;
			decOK = true;
		}
		return decOK;
	}

	public boolean isEncrypted() {
		// Aktuellen Zustand zurueckgeben: verschluesselt (true) / entschluesselt
		// (false)
		return isEncryptedState;
	}

	public void print() {
		System.out.println("********* Ticket fuer " + myClientName + " / "
				+ myServerName + " *******");
		System.out.println("StartTime: " + getDateString(myStartTime)
				+ " - EndTime: " + getDateString(myEndTime));
		System.out.println("Session Key: " + mySessionKey);
		System.out.println("Ticket Key: " + myTicketKey);
		if (isEncryptedState) {
			System.out.println("Ticket-Zustand: verschluesselt (encrypted)!");
		} else {
			System.out.println("Ticket-Zustand: entschluesselt (decrypted)!");
		}
	}

	public void printError(String message) {
		System.out.println("+++++++++++++++++++");
		System.out.println("+++++++++++++++++++ Fehler +++++++++++++++++++ "
				+ message + "! Ticket-Key: " + myTicketKey);
		System.out.println("+++++++++++++++++++");
	}

	private String getDateString(long time) {
		// Umrechnung der Zeitangabe time (Millisek. seit 1.1.1970) in einen
		// Datumsstring
		String dateString;

		cal.setTimeInMillis(time);
		dateString = cal.get(Calendar.DAY_OF_MONTH) + "."
				+ (cal.get(Calendar.MONTH) + 1) + "." + cal.get(Calendar.YEAR)
				+ " " + cal.get(Calendar.HOUR_OF_DAY) + ":"
				+ cal.get(Calendar.MINUTE) + ":" + cal.get(Calendar.SECOND)
				+ ":" + cal.get(Calendar.MILLISECOND);
		return dateString;
	}
}
