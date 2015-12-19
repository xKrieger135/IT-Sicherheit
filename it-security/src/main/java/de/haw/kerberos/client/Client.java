package de.haw.kerberos.client;

/* Simulation einer Kerberos-Session mit Zugriff auf einen Fileserver
 /* Client-Klasse
 */

import de.haw.kerberos.KDC;
import de.haw.kerberos.entities.Auth;
import de.haw.kerberos.entities.TicketResponse;
import de.haw.kerberos.server.Server;
import de.haw.kerberos.entities.Ticket;

import java.util.Date;

public class Client extends Object {

	private KDC myKDC = null; // Konstruktor-Parameter

	private String currentUser; // Speicherung bei Login noetig
	private Ticket tgsTicket = null; // Speicherung bei Login noetig
	private long tgsSessionKey; // K(C,TGS) // Speicherung bei Login noetig

	private Ticket tgsServerTicket = null;

	// Konstruktor
	public Client(KDC kdc) {
		this.myKDC = kdc;
	}

	public boolean login(String userName, char[] password) {
		boolean login = false;
		String tgsName = myKDC.getName();
		TicketResponse ticketResponse = null;
		long nonce = generateNonce();
		long pw;

		if (!(userName.isEmpty() && null != password)) {
			ticketResponse = myKDC.requestTGSTicket(userName, tgsName, nonce);
		}

		if (ticketResponse != null) {
			pw = generateSimpleKeyFromPassword(password);
			if (ticketResponse.decrypt(pw)) {
				System.out.println("TicketResponse erfolgreich entschluesselt!");
				ticketResponse.print();
				tgsSessionKey = ticketResponse.getSessionKey();
				tgsTicket = ticketResponse.getResponseTicket();
				currentUser = userName;
				login = true;
			}
		}

		return login;
	}

	public boolean showFile(Server fileServer, String filePath) {
		boolean canShowFile = false;
		TicketResponse ticketResponse = null;
		long nonce = generateNonce();
		Auth auth = null;

		if (tgsTicket != null) {
			if (tgsServerTicket == null) {
				System.out.println("++++++++++ Create new auth and encrypt! ++++++++++");
				auth = new Auth(currentUser, new Date().getTime());
				auth.encrypt(tgsSessionKey);
				auth.print();

				ticketResponse = myKDC.requestServerTicket(tgsTicket, auth, fileServer.getName(), nonce);

				System.out.println("Ticketresponse: " + ticketResponse);
				ticketResponse.print();

				ticketResponse.decrypt(tgsSessionKey);

				fileServer.requestService(ticketResponse.getResponseTicket(), auth, "showFile", filePath);

				canShowFile = true;
			}
		}

		return canShowFile;
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