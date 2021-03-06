package de.haw.rsa.rsaadapter.adapter;


import de.haw.rsa.rsakeycreation.dataaccesslayer.entities.PrivateKey;
import de.haw.rsa.rsakeycreation.dataaccesslayer.entities.PublicKey;

import java.io.DataInputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.security.KeyFactory;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.Arrays;

/**
 * Created by Patrick Steinhauer
 * On 11.Dez.2015
 */
public class RSAKeyReaderAdapter {

    public RSAKeyReaderAdapter() {

    }

    /**
     * This method will read the private RSA key from a given file.
     *
     * @param fileName The given file, which contains the private RSA key.
     * @return         Returns the private RSA key.
     */
    public PrivateKey readPrivateKey(String fileName) {
        PrivateKey privateKey = null;

        java.security.PrivateKey pk = null;
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

            pk = keyFactory.generatePrivate(pkcs8EncodedKeySpec);

            privateKey = new PrivateKey(pk, privateKeyOwnerBytes);

        } catch (NoSuchAlgorithmException noSuchAlgorithmException) {
            Error("The given algorithm is not a valid algorithm!", noSuchAlgorithmException);
        } catch (InvalidKeySpecException invalidKeySpecException) {
            Error("The given KeySpec is not correct!", invalidKeySpecException);
        }

        return privateKey;
    }

    /**
     * This method will read the public RSA key from a given file.
     *
     * @param fileName The given file, which contains the public RSA key.
     * @return         Returns the public RSA key.
     */
    public PublicKey readPublicKey(String fileName) {
        PublicKey publicKey = null;

        java.security.PublicKey pk = null;
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

            pk = keyFactory.generatePublic(x509EncodedKeySpec);

            publicKey = new PublicKey(pk, publicKeyOwnerBytes);

        } catch (NoSuchAlgorithmException noSuchAlgorithmException) {
            Error("The given algorithm is not a valid algorithm!", noSuchAlgorithmException);
        } catch (InvalidKeySpecException invalidKeySpecException) {
            Error("The given KeySpec is not correct!", invalidKeySpecException);
        }

        return publicKey;
    }

    /**
     * Diese Methode gibt eine Fehlermeldung sowie eine Beschreibung der
     * Ausnahme aus. Danach wird das Programm beendet.
     *
     * @param msg eine Beschreibung fuer den Fehler
     * @param ex  die Ausnahme, die den Fehler ausgeloest hat
     */
    private void Error(String msg, Exception ex) {
        System.out.println(msg);
        ex.printStackTrace();
        System.exit(0);
    }

}
