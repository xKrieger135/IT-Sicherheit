package de.haw.rsa.rsakeycreation.dataaccesslayer.entities;

/**
 * Created by Patrick Steinhauer on 09.12.2015.
 */
public class PrivateKey implements Key {
    java.security.PrivateKey privateKey;
    private byte[] keyOwnerName;


    public PrivateKey(java.security.PrivateKey privateKey, byte[] keyOwnerName) {
        this.privateKey = privateKey;
        this.keyOwnerName = keyOwnerName;
    }

    /**
     *
     * @return
     */
    public int getKeyOwnerNameLength() {
        return keyOwnerName.length;
    }

    /**
     *
     * @return
     */
    public byte[] getKeyOwnerName() {
        return keyOwnerName;
    }

    /**
     *
     * @return
     */
    public int getKeyLength() {
        return privateKey.getEncoded().length;
    }

    /**
     *
     * @return
     */
    public byte[] getKeyEncoded() {
        return privateKey.getEncoded();
    }

    /**
     *
     * @return
     */
    public java.security.PrivateKey getKey() {
        return privateKey;
    }
}
