package de.haw.rsa.receivesecuredfile.dataaccesslayer;

/**
 * Created by JanDennis on 14.12.2015.
 */
public class SendSecureFile {
    private byte[] encryptedSecretKey;
    private byte[] signature;
    private byte[] algorithmicParameters;
    private byte[] encryptedData;

    public SendSecureFile(byte[] encryptedSecretKey, byte[] signature, byte[] algorithmicParameters, byte[] encryptedData) {
        this.encryptedSecretKey = encryptedSecretKey;
        this.signature = signature;
        this.algorithmicParameters = algorithmicParameters;
        this.encryptedData = encryptedData;
    }

    public byte[] getEncryptedSecretKey() {
        return encryptedSecretKey;
    }

    public byte[] getSignature() {
        return signature;
    }

    public byte[] getAlgorithmicParameters() {
        return algorithmicParameters;
    }

    public byte[] getEncryptedData() {
        return encryptedData;
    }
}
