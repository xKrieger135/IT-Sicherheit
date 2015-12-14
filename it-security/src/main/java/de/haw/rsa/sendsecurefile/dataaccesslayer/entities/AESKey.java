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

    /**
     * This method will get the secret key.
     *
     * @return Returns the secret key as a byte array.
     */
    public byte[] getSecretKey() {
        return secretKey;
    }

    /**
     * This method will set the secret key.
     *
     * @param secretKey The given secret key which will be set.
     */
    public void setSecretKey(byte[] secretKey) {
        this.secretKey = secretKey;
    }

    /**
     * This method will get the encrypted secret key.
     *
     * @return Returns the encrypted secret key as a byte array.
     */
    public byte[] getSecretKeyEncrypted() {
        return secretKeyEncrypted;
    }

    /**
     * This method will set the encrypted secret key.
     *
     * @param secretKeyEncrypted The given secret key, which will be set.
     */
    public void setSecretKeyEncrypted(byte[] secretKeyEncrypted) {
        this.secretKeyEncrypted = secretKeyEncrypted;
    }

    /**
     * This method will return the algorithm, which is used for the secret key.
     *
     * @return Returns the used algorithm as a String.
     */
    public String getAlgorithm() {
        return algorithm;
    }

    /**
     * This method will set the algorithm, which is used for the key creation.
     *
     * @param algorithm This algorithm, given as a String is used for the key creation.
     */
    public void setAlgorithm(String algorithm) {
        this.algorithm = algorithm;
    }

    /**
     * This method will get the Algorithm Parameters of this secret key.
     *
     * @return Returns the algorithm parameters of this secret key.
     */
    public AlgorithmParameters getAlgorithmParameters() {
        return algorithmParameters;
    }

    /**
     * This method will set the algorithm parameters for the secret key.
     *
     * @param algorithmParameters The algorithm parameters which should be set.
     */
    public void setAlgorithmParameters(AlgorithmParameters algorithmParameters) {
        this.algorithmParameters = algorithmParameters;
    }
}
