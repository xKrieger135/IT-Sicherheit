package de.haw.rsa.sendsecurefile.dataaccesslayer.entities;

/**
 * Created by Patrick Steinhauer
 * On 13.Dez.2015
 */
public class AESKey {

    private byte[] secretKey;
    private byte[] secretKeyEncrypted;
    private String algorithm;

    public AESKey() {
        this.secretKey = null;
        this.secretKeyEncrypted = null;
    }

    public byte[] getSecretKey() {
        return secretKey;
    }

    public void setSecretKey(byte[] secretKey) {
        this.secretKey = secretKey;
    }

    public byte[] getSecretKeyEncrypted() {
        return secretKeyEncrypted;
    }

    public void setSecretKeyEncrypted(byte[] secretKeyEncrypted) {
        this.secretKeyEncrypted = secretKeyEncrypted;
    }

    public String getAlgorithm() {
        return algorithm;
    }

    public void setAlgorithm(String algorithm) {
        this.algorithm = algorithm;
    }
}
