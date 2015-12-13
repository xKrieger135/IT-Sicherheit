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

    public int getKeyOwnerNameLength() {
        return keyOwnerName.length;
    }

    public byte[] getKeyOwnerName() {
        return keyOwnerName;
    }

    /*public void setKeyOwnerName(byte[] keyOwnerName) {
        this.keyOwnerName = keyOwnerName;
    }*/

    public int getKeyLength() {
        return publicKey.getEncoded().length;
    }

    public byte[] getKeyEncoded() {
        return publicKey.getEncoded();
    }

    public java.security.PublicKey getKey() {
        return publicKey;
    }

   /* public void setKey(java.security.PublicKey key) {
        this.publicKey = key;
    }*/
}
