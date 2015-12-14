package de.haw.rsa.rsakeycreation.dataaccesslayer.entities;

/**
 * Created by Patrick Steinhauer on 09.12.2015.
 */
public class PublicKey implements Key {
    java.security.PublicKey publicKey;
    private byte[] keyOwnerName;

    public PublicKey(java.security.PublicKey key, byte[] keyOwnerName) {
        this.publicKey = key;
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
        return publicKey.getEncoded().length;
    }

    /**
     *
     * @return
     */
    public byte[] getKeyEncoded() {
        return publicKey.getEncoded();
    }

    /**
     *
     * @return
     */
    public java.security.PublicKey getKey() {
        return publicKey;
    }
}
