package de.haw.rsa.rsaadapter.adapter;

import de.haw.rsa.rsakeycreation.dataaccesslayer.entities.Key;
import de.haw.rsa.rsakeycreation.dataaccesslayer.entities.PrivateKey;
import de.haw.rsa.rsakeycreation.dataaccesslayer.entities.PublicKey;

import java.io.DataOutputStream;
import java.io.File;
import java.io.FileOutputStream;

/**
 * Created by Patrick Steinhauer
 * On 11.Dez.2015
 */
public class RSAKeyCreationWriterAdapter {
    private File privateKeyFile;
    private File publicKeyFile;
    private PublicKey publicKey;
    private PrivateKey privateKey;

    public RSAKeyCreationWriterAdapter(File privateKeyFile, File publicKeyFile, PublicKey publicKey, PrivateKey privateKey) {
        this.privateKeyFile = privateKeyFile;
        this.publicKeyFile = publicKeyFile;
        this.publicKey = publicKey;
        this.privateKey = privateKey;
    }

    public void createFiles() {
        writeToFile(privateKeyFile, privateKey);
        writeToFile(publicKeyFile, publicKey);
    }

    private void writeToFile(File file, Key key) {
        int ownerLenght = key.getKeyOwnerNameLength();
        byte[] ownerData = key.getKeyOwnerName();
        int keyLength = key.getKeyLength();
        byte[] keyData = key.getKey();
        DataOutputStream fstream;

        if (file.canWrite()) {
            try {
                fstream = new DataOutputStream(new FileOutputStream(file));
                fstream.write(ownerLenght);
                fstream.write(ownerData);
                fstream.write(keyLength);
                fstream.write(keyData);

                fstream.close();
            } catch (Exception e) {
                System.out.println("Could not write to File " + file.getAbsolutePath());
                e.printStackTrace();
            }
        } else System.out.println("File is set to Read Only!");
    }
}
