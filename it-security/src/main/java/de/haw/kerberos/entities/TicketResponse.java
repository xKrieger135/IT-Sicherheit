package de.haw.kerberos.entities;

/* Simulation einer Kerberos-Session mit Zugriff auf einen Fileserver
 /* TicketResponse-Klasse
 */

import de.haw.kerberos.entities.Ticket;

public class TicketResponse extends Object {

	private long mySessionKey; // Konstruktor-Parameter

	private long myNonce; // Konstruktor-Parameter

	private Ticket myResponseTicket; // Konstruktor-Parameter

	// Geheimer Schluessel, mit dem diese Antwort (Response) (simuliert)
	// verschluesselt ist:
	private long myResponseKey;

	private boolean isEncryptedState; // Aktueller Zustand des Objekts

	// Konstruktor
	public TicketResponse(long sessionKey, long nonce, Ticket responseTicket) {
		mySessionKey = sessionKey;
		myNonce = nonce;
		myResponseTicket = responseTicket;
		myResponseKey = -1;
		isEncryptedState = false;
	}

	public long getSessionKey() {
		if (isEncryptedState) {
			printError("Zugriff auf verschluesselte Ticket-Response (getSessionKey)");
		}
		return mySessionKey;
	}

	public long getNonce() {
		if (isEncryptedState) {
			printError("Zugriff auf verschluesselte Ticket-Response (getNonce)");
		}
		return myNonce;
	}

	public Ticket getResponseTicket() {
		if (isEncryptedState) {
			printError("Zugriff auf verschluesselte Ticket-Response (getResponseTicket)");
		}
		return myResponseTicket;
	}

	public boolean encrypt(long key) {
		// TicketResponse mit dem Key verschluesseln.
		// Falls die TicketResponse bereits verschluesselt ist, wird false
		// zurueckgegeben.
		boolean encOK = false;
		if (isEncryptedState) {
			printError("TicketResponse ist bereits verschluesselt");
		} else {
			myResponseKey = key;
			isEncryptedState = true;
			encOK = true;
		}
		return encOK;
	}

	public boolean decrypt(long key) {
		// TicketResponse mit dem Key entschluesseln.
		// Falls der Key falsch ist oder
		// falls die TicketResponse bereits entschluesselt ist, wird false
		// zurueckgegeben.
		boolean decOK = false;
		if (!isEncryptedState) {
			printError("TicketResponse ist bereits entschluesselt");
		}
		if (myResponseKey != key) {
			printError("TicketResponse-Entschluesselung mit key " + key
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
				+ message + "! TicketResponse-Key: " + myResponseKey);
		System.out.println("+++++++++++++++++++");
	}

	public void print() {
		System.out.println("********* TicketResponse *******");
		System.out.println("Session Key: " + mySessionKey);
		System.out.println("Nonce: " + myNonce);
		myResponseTicket.print();
		System.out.println("Response Key: " + myResponseKey);
		if (isEncryptedState) {
			System.out
					.println("TicketResponse-Zustand: verschluesselt (encrypted)!");
		} else {
			System.out
					.println("TicketResponse-Zustand: entschluesselt (decrypted)!");
		}
		System.out.println();
	}

}
