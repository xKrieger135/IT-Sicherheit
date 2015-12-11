package de.haw.rsa.rsakeycreation.dataaccesslayer.entities;

/**
 * Created by Patrick Steinhauer on 09.12.2015.
 */
public class PublicKey implements Key {
    private byte[] keyOwnerName;
    private byte[] key;

    public PublicKey() {
        this.keyOwnerName = null;
        this.key = null;
    }

    public int getKeyOwnerNameLength() {
        return keyOwnerName.length;
    }

    public byte[] getKeyOwnerName() {
        return keyOwnerName;
    }

    public void setKeyOwnerName(byte[] keyOwnerName) {
        this.keyOwnerName = keyOwnerName;
    }

    public int getKeyLength() {
        return key.length;
    }

    public byte[] getKey() {
        return key;
    }

    public void setKey(byte[] key) {
        this.key = key;
    }
}
