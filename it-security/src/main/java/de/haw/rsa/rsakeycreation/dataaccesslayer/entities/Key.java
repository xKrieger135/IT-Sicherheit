package de.haw.rsa.rsakeycreation.dataaccesslayer.entities;

/**
 * Created by JanDennis on 11.12.2015.
 */
public interface Key {
    int getKeyOwnerNameLength();

    byte[] getKeyOwnerName();

    void setKeyOwnerName(byte[] keyOwnerName);

    int getKeyLength();

    byte[] getKey();

    void setKey(byte[] key);
}

