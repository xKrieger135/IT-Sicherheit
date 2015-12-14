package de.haw.rsa.rsakeycreation.dataaccesslayer.entities;

/**
 * Created by JanDennis on 11.12.2015.
 */
public interface Key {
    /**
     *
     * @return
     */
    int getKeyOwnerNameLength();

    /**
     *
     * @return
     */
    byte[] getKeyOwnerName();

    /**
     *
     * @return
     */
    int getKeyLength();

    /**
     *
     * @return
     */
    byte[] getKeyEncoded();

    /**
     *
     * @return
     */
    java.security.Key getKey();
}

