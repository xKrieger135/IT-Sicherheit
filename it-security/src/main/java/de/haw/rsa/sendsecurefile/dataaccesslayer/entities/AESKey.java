package de.haw.rsa.sendsecurefile.dataaccesslayer.entities;

import java.security.AlgorithmParameters;

/**
 * Created by Patrick Steinhauer
 * On 13.Dez.2015
 */
public class AESKey {

    private byte[] secretKey;
    private byte[] secretKeyEncrypted;
    private String algorithm;
    private AlgorithmParameters algorithmParameters;

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

    public AlgorithmParameters getAlgorithmParameters() {
        return algorithmParameters;
    }

    public void setAlgorithmParameters(AlgorithmParameters algorithmParameters) {
        this.algorithmParameters = algorithmParameters;
    }
}
