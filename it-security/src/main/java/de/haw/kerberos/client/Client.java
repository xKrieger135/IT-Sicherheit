package de.haw.kerberos.client;

/* Simulation einer Kerberos-Session mit Zugriff auf einen Fileserver
 /* Client-Klasse
 */

import de.haw.kerberos.KDC;
import de.haw.kerberos.server.Server;
import de.haw.kerberos.entities.Ticket;

public class Client extends Object {

	private KDC myKDC; // Konstruktor-Parameter

	private String currentUser; // Speicherung bei Login noetig
	private Ticket tgsTicket = null; // Speicherung bei Login noetig
	private long tgsSessionKey; // K(C,TGS) // Speicherung bei Login noetig

	// Konstruktor
	public Client(KDC kdc) {
		myKDC = kdc;
	}

	public boolean login(String userName, char[] password) {
		/* ToDo : This is only to remove error */
		return false;
	}

	public boolean showFile(Server fileServer, String filePath) {
		/* ToDo : This is only to remove error */
		return false;
	}

	/* *********** Hilfsmethoden **************************** */

	private long generateSimpleKeyFromPassword(char[] passwd) {
		// Liefert einen eindeutig aus dem Passwort abgeleiteten Schluessel
		// zurueck, hier simuliert als long-Wert
		long pwKey = 0;
		if (passwd != null) {
			for (int i = 0; i < passwd.length; i++) {
				pwKey = pwKey + passwd[i];
			}
		}
		return pwKey;
	}

	private long generateNonce() {
		// Liefert einen neuen Zufallswert
		long rand = (long) (100000000 * Math.random());
		return rand;
	}
}
