package de.haw.rsa.rsaadapter.adapter;

import de.haw.rsa.receivesecuredfile.dataaccesslayer.SendSecureFile;

import java.io.*;

/**
 * Created by JanDennis on 14.12.2015.
 */
public class SSFReaderAdapter {
    public SSFReaderAdapter() {
    }

    public SendSecureFile readSSFFile(File file) {
        byte[] encryptedSecretKey = new byte[0];
        byte[] signature = new byte[0];
        byte[] algorithmicParameters = new byte[0];
        byte[] encryptedData = new byte[0];

        try {
            DataInputStream fstream = new DataInputStream(new FileInputStream(file));

            int secretKeyLenght = fstream.readInt();
            encryptedSecretKey = new byte[secretKeyLenght];
            fstream.read(encryptedSecretKey);

            int signatureLength = fstream.readInt();
            signature = new byte[signatureLength];
            fstream.read(signature);

            int algorithmParamLenght = fstream.readInt();
            algorithmicParameters = new byte[algorithmParamLenght];
            fstream.read(algorithmicParameters);

            int dataLength = (int) file.length()-secretKeyLenght-signatureLength-algorithmParamLenght;
            encryptedData = new byte[dataLength];
            fstream.read(encryptedData);

            fstream.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return new SendSecureFile(encryptedSecretKey,signature,algorithmicParameters,encryptedData);
    }
}
