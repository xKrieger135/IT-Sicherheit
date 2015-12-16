package de.haw.kerberos.entities;

/* Simulation einer Kerberos-Session mit Zugriff auf einen Fileserver
 /* Auth-Klasse (Authentification)
 */

import java.util.*;

public class Auth extends Object {

	private String myClientName; // Konstruktor-Parameter

	private long myCurrentTime; // Konstruktor-Parameter

	// Geheimer Schluessel, mit dem die Authentifikation (simuliert)
	// verschluesselt ist:
	private long myAuthKey;

	private boolean isEncryptedState; // Aktueller Zustand des Objekts

	// Kalenderobjekt zur Zeitumrechnung (fuerr Testausgaben)
	private Calendar cal;

	// Konstruktor
	public Auth(String clientName, long currentTime) {
		myClientName = clientName;
		myCurrentTime = currentTime;
		myAuthKey = -1;
		isEncryptedState = false;
		cal = new GregorianCalendar(); // fuer Testausgaben
	}

	public String getClientName() {
		if (isEncryptedState) {
			printError("Zugriff auf verschluesselte Authentifikation (getClientName)");
		}
		return myClientName;
	}

	public long getCurrentTime() {
		if (isEncryptedState) {
			printError("Zugriff auf verschluesselte Authentifikation (getCurrentTime)");
		}
		return myCurrentTime;
	}

	public boolean encrypt(long key) {
		// Authentifikation mit dem Key verschluesseln.
		// Falls die Authentifikation bereits verschluesselt ist, wird false
		// zurueckgegeben.
		boolean encOK = false;
		if (isEncryptedState) {
			printError("Auth ist bereits verschluesselt");
		} else {
			myAuthKey = key;
			isEncryptedState = true;
			encOK = true;
		}
		return encOK;
	}

	public boolean decrypt(long key) {
		// Authentifikation mit dem Key entschluesseln.
		// Falls der Key falsch ist oder
		// falls die Authentifikation bereits entschluesselt ist, wird false
		// zurueckgegeben.
		boolean decOK = false;
		if (!isEncryptedState) {
			printError("Auth ist bereits entschluesselt");
		}
		if (myAuthKey != key) {
			printError("Auth-Entschluesselung mit key " + key
					+ " ist fehlgeschlagen");
		} else {
			isEncryptedState = false;
			decOK = true;
		}
		return decOK;
	}

	public boolean isEncrypted() {
		// Aktuellen Zustand zurueckgeben:
		// verschluesselt (true) / entschluesselt (false)
		return isEncryptedState;
	}

	public void printError(String message) {
		System.out.println("+++++++++++++++++++");
		System.out.println("+++++++++++++++++++ Fehler +++++++++++++++++++ "
				+ message + "! Auth-Key: " + myAuthKey);
		System.out.println("+++++++++++++++++++");
	}

	public void print() {
		System.out.println("********* Authentifikation fuer " + myClientName
				+ " *******");
		System.out.println("CurrentTime: " + getDateString(myCurrentTime));
		System.out.println("Auth Key: " + myAuthKey);
		if (isEncryptedState) {
			System.out.println("Auth-Zustand: verschluesselt (encrypted)!");
		} else {
			System.out.println("Auth-Zustand: entschluesselt (decrypted)!");
		}
		System.out.println();
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
