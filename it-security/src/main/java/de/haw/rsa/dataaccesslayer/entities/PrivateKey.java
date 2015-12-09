package de.haw.rsa.dataaccesslayer.entities;

import java.security.*;

/**
 * Created by Patrick Steinhauer on 09.12.2015.
 */
public class PrivateKey {

    private int keyOwnerNameLength;
    private byte[] keyOwnerName;
    private int keyLength;
    private byte[] key;

    public PrivateKey() {
        this.keyOwnerName = null;
        this.key = null;
    }

    public int getKeyOwnerNameLength() {
        return keyOwnerNameLength;
    }

    public void setKeyOwnerNameLength(int keyOwnerNameLength) {
        this.keyOwnerNameLength = keyOwnerNameLength;
    }

    public byte[] getKeyOwnerName() {
        return keyOwnerName;
    }

    public void setKeyOwnerName(byte[] keyOwnerName) {
        this.keyOwnerName = keyOwnerName;
    }

    public int getKeyLength() {
        return keyLength;
    }

    public void setKeyLength(int keyLength) {
        this.keyLength = keyLength;
    }

    public byte[] getKey() {
        return key;
    }

    public void setKey(byte[] key) {
        this.key = key;
    }
}
