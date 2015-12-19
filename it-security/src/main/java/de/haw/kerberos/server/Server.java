package de.haw.kerberos.server;

/* Simulation einer Kerberos-Session mit Zugriff auf einen Fileserver
 /* Server-Klasse
 */

import de.haw.kerberos.entities.Auth;
import de.haw.kerberos.KDC;
import de.haw.kerberos.entities.Ticket;

import java.util.*;
import java.io.*;

public class Server extends Object {

	private final long fiveMinutesInMillis = 300000; // 5 Minuten in
														// Millisekunden

	private String myName; // Konstruktor-Parameter
	private KDC myKDC; // wird bei KDC-Registrierung gespeichert
	private long myKey; // wird bei KDC-Registrierung gespeichert K(S)

	// Konstruktor
	public Server(String name) {
		myName = name;
	}

	public String getName() {
		return myName;
	}

	public void setupService(KDC kdc) {
		// Anmeldung des Servers beim KDC
		myKDC = kdc;
		myKey = myKDC.serverRegistration(myName);
		System.out.println("Server " + myName
				+ " erfolgreich registriert bei KDC " + myKDC.getName()
				+ " mit ServerKey " + myKey);
	}

	public boolean requestService(Ticket serverTicket, Auth serverAuthentication,
								  String command, String parameter) {

		boolean canRequestService = false;
		serverTicket.decrypt(myKey);
		serverTicket.print();

		if (serverTicket.getServerName().equals(myName)
				&& timeValid(serverTicket.getStartTime(), serverTicket.getEndTime())
				&& timeFresh(serverAuthentication.getCurrentTime())
				&& serverAuthentication.getClientName().equals(serverTicket.getClientName())) {

			if (command.equals("showFile")) {
				canRequestService = showFile(parameter);
			} else {
				System.out.println("This command is not a valid command for the server!");
			}
		}

		return canRequestService;
	}

	/* *********** Services **************************** */

	private boolean showFile(String filePath) {
		/*
		 * Angegebene Datei auf der Konsole ausgeben. Rueckgabe: Status der
		 * Operation
		 */
		String lineBuf = null;
		File myFile = new File(filePath);
		boolean status = false;

		if (!myFile.exists()) {
			System.out.println("Datei " + filePath + " existiert nicht!");
		} else {
			try {
				// Datei oeffnen und zeilenweise lesen
				BufferedReader inFile = new BufferedReader(
						new InputStreamReader(new FileInputStream(myFile)));
				lineBuf = inFile.readLine();
				while (lineBuf != null) {
					System.out.println(lineBuf);
					lineBuf = inFile.readLine();
				}
				inFile.close();
				status = true;
			} catch (IOException ex) {
				System.out.println("Fehler beim Lesen der Datei " + filePath
						+ ex);
			}
		}
		return status;
	}

	/* *********** Hilfsmethoden **************************** */

	private boolean timeValid(long lowerBound, long upperBound) {
		/*
		 * Wenn die aktuelle Zeit innerhalb der uebergebenen Zeitgrenzen liegt,
		 * wird true zurueckgegeben
		 */

		long currentTime = (new Date()).getTime(); // Anzahl mSek. seit 1.1.1970
		if (currentTime >= lowerBound && currentTime <= upperBound) {
			return true;
		} else {
			System.out.println("-------- Time not valid: " + currentTime
					+ " not in (" + lowerBound + "," + upperBound + ")!");
			return false;
		}
	}

	boolean timeFresh(long testTime) {
		/*
		 * Wenn die uebergebene Zeit nicht mehr als 5 Minuten von der aktuellen
		 * Zeit abweicht, wird true zurueckgegeben
		 */
		long currentTime = (new Date()).getTime(); // Anzahl mSek. seit 1.1.1970
		if (Math.abs(currentTime - testTime) < fiveMinutesInMillis) {
			return true;
		} else {
			System.out.println("-------- Time not fresh: " + currentTime
					+ " is current, " + testTime + " is old!");
			return false;
		}
	}
}
