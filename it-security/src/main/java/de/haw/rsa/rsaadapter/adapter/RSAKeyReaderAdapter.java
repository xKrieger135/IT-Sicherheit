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
import java.util.Arrays;

/**
 * Created by Patrick Steinhauer
 * On 11.Dez.2015
 */
public class RSAKeyReaderAdapter {

    private de.haw.rsa.rsakeycreation.dataaccesslayer.entities.PrivateKey privateKey = null;
    private de.haw.rsa.rsakeycreation.dataaccesslayer.entities.PublicKey publicKey = null;

    public RSAKeyReaderAdapter() {
        this.privateKey = new de.haw.rsa.rsakeycreation.dataaccesslayer.entities.PrivateKey();
        this.publicKey = new de.haw.rsa.rsakeycreation.dataaccesslayer.entities.PublicKey();
    }

    public PrivateKey readPrivateKey(String fileName) {

        PrivateKey pk = null;
        byte[] privateKeyBytes = null;
        byte[] privateKeyOwnerBytes = null;

        try {
            DataInputStream privateKeyFile = new DataInputStream(new FileInputStream(fileName));

            // First read the length of keyOwner from file.
            // And initialize the byte array with this length.
            int length = privateKeyFile.readInt();
            System.out.println("l: "+length);
            privateKeyOwnerBytes = new byte[length];

            int a = privateKeyFile.read(privateKeyOwnerBytes);
            System.out.println("bytes: " + Arrays.toString(privateKeyOwnerBytes));
            privateKey.setKeyOwnerName(privateKeyOwnerBytes);

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

            privateKey.setKey(keyFactory.generatePrivate(pkcs8EncodedKeySpec).getEncoded());

        } catch (NoSuchAlgorithmException noSuchAlgorithmException) {
            Error("The given algorithm is not a valid algorithm!", noSuchAlgorithmException);
        } catch (InvalidKeySpecException invalidKeySpecException) {
            Error("The given KeySpec is not correct!", invalidKeySpecException);
        }

        return pk;
    }

    public PublicKey readPublicKey(String fileName) {

        PublicKey pk = null;
        byte[] publicKeyBytes = null;
        byte[] publicKeyOwnerBytes = null;

        try {
            DataInputStream publicKeyFile = new DataInputStream(new FileInputStream(fileName));

            // First read the length of keyOwner from file.
            // And initialize the byte array with this length.
            int length = publicKeyFile.readInt();
            publicKeyOwnerBytes = new byte[length];
            publicKeyFile.read(publicKeyOwnerBytes);

            publicKey.setKeyOwnerName(publicKeyOwnerBytes);

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

            publicKey.setKey(keyFactory.generatePublic(x509EncodedKeySpec).getEncoded());

        } catch (NoSuchAlgorithmException noSuchAlgorithmException) {
            Error("The given algorithm is not a valid algorithm!", noSuchAlgorithmException);
        } catch (InvalidKeySpecException invalidKeySpecException) {
            Error("The given KeySpec is not correct!", invalidKeySpecException);
        }

        return pk;
    }

    public de.haw.rsa.rsakeycreation.dataaccesslayer.entities.PrivateKey getPrivateKey() {
        return privateKey;
    }

    public de.haw.rsa.rsakeycreation.dataaccesslayer.entities.PublicKey getPublicKey() {
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
        System.out.println(ex.getMessage());
        System.exit(0);
    }

}
