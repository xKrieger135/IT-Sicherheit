package de.haw.rsa.rsaadapter.adapter;

import java.io.DataInputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.security.KeyFactory;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;

/**
 * Created by Patrick Steinhauer
 * On 11.Dez.2015
 */
public class RSAKeyReaderAdapter {

    private PrivateKey privateKey = null;
    private PublicKey publicKey = null;

    public RSAKeyReaderAdapter() {

    }

    public void readPrivateKey(String fileName) {

        byte[] privateKeyBytes = null;
        byte[] privateKeyOwnerBytes = null;

        try {
            DataInputStream privateKeyFile = new DataInputStream(new FileInputStream(fileName));

            // First read the length of keyOwner from file.
            // And initialize the byte array with this length.
            int length = privateKeyFile.readInt();
            privateKeyOwnerBytes = new byte[length];
            privateKeyFile.read(privateKeyOwnerBytes);

            // Second read the length of key from file.
            // Also initialize the byte array here.
            length = privateKeyFile.readInt();
            privateKeyBytes = new byte[length];
            privateKeyFile.read(privateKeyBytes);

            // Close the file.
            privateKeyFile.close();
        } catch (IOException exception) {
            Error("An error occurred while reading the file.", exception);
        }

        try {

            KeyFactory keyFactory = KeyFactory.getInstance("RSA");

            PKCS8EncodedKeySpec pkcs8EncodedKeySpec = new PKCS8EncodedKeySpec(privateKeyBytes);

            privateKey = keyFactory.generatePrivate(pkcs8EncodedKeySpec);

        } catch (NoSuchAlgorithmException noSuchAlgorithmException) {
            Error("The given algorithm is not a valid algorithm!", noSuchAlgorithmException);
        } catch (InvalidKeySpecException invalidKeySpecException) {
            Error("The given KeySpec is not correct!", invalidKeySpecException);
        }
    }

    public void readPublicKey(String fileName) {

        byte[] publicKeyBytes = null;
        byte[] publicKeyOwnerBytes = null;

        try {
            DataInputStream publicKeyFile = new DataInputStream(new FileInputStream(fileName));

            // First read the length of keyOwner from file.
            // And initialize the byte array with this length.
            int length = publicKeyFile.readInt();
            publicKeyOwnerBytes = new byte[length];
            publicKeyFile.read(publicKeyOwnerBytes);

            // Second read the length of key from file.
            // Also initialize the byte array here.
            length = publicKeyFile.readInt();
            publicKeyBytes = new byte[length];
            publicKeyFile.read(publicKeyBytes);

            // Close the file.
            publicKeyFile.close();
        } catch (IOException exception) {
            Error("An error occurred while reading the file.", exception);
        }

        try {

            KeyFactory keyFactory = KeyFactory.getInstance("RSA");

            X509EncodedKeySpec x509EncodedKeySpec = new X509EncodedKeySpec(publicKeyBytes);

            publicKey = keyFactory.generatePublic(x509EncodedKeySpec);

        } catch (NoSuchAlgorithmException noSuchAlgorithmException) {
            Error("The given algorithm is not a valid algorithm!", noSuchAlgorithmException);
        } catch (InvalidKeySpecException invalidKeySpecException) {
            Error("The given KeySpec is not correct!", invalidKeySpecException);
        }
    }

    public PrivateKey getPrivateKey() {
        return privateKey;
    }

    public PublicKey getPublicKey() {
        return publicKey;
    }

    /**
     * Diese Methode gibt eine Fehlermeldung sowie eine Beschreibung der
     * Ausnahme aus. Danach wird das Programm beendet.
     *
     * @param msg eine Beschreibung fuer den Fehler
     * @param ex  die Ausnahme, die den Fehler ausgeloest hat
     *
     */
    private void Error(String msg, Exception ex) {
        System.out.println(msg);
        System.out.println(ex.getMessage());
        System.exit(0);
    }

}
