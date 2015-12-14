package de.haw.rsa.sendsecurefile.dataaccesslayer.entities;

import java.security.Provider;

/**
 * Created by Patrick Steinhauer
 * On 13.Dez.2015
 */
public class RSASignature {

    private byte[] signature;
    private String algorithm;
    private Provider provider;
    private boolean verified;

    public RSASignature() {
        this.signature = null;
        this.provider = null;
        this.verified = false;
    }

    /**
     *
     * @return
     */
    public byte[] getSignature() {
        return signature;
    }

    /**
     *
     * @param signature
     */
    public void setSignature(byte[] signature) {
        this.signature = signature;
    }

    /**
     *
     * @return
     */
    public String getAlgorithm() {
        return algorithm;
    }

    /**
     *
     * @param algorithm
     */
    public void setAlgorithm(String algorithm) {
        this.algorithm = algorithm;
    }

    /**
     *
     * @return
     */
    public Provider getProvider() {
        return provider;
    }

    /**
     *
     * @param provider
     */
    public void setProvider(Provider provider) {
        this.provider = provider;
    }

    /**
     *
     * @return
     */
    public boolean isVerified() {
        return verified;
    }

    /**
     *
     * @param verified
     */
    public void setVerified(boolean verified) {
        this.verified = verified;
    }
}
