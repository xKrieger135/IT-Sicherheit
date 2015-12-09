package de.haw.rsa;

import java.security.Key;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.NoSuchAlgorithmException;

/**
 * Created by JanDennis on 25.11.2015.
 */
public class RSACrypt {
    Key publicKey;
    Key privateKey;

    public RSACrypt() {
        KeyPairGenerator kpg = null;
        try {
            kpg = KeyPairGenerator.getInstance("de/haw/chiffre/rsa");
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        kpg.initialize(2048);
        KeyPair kp = kpg.genKeyPair();
        publicKey = kp.getPublic();
        privateKey = kp.getPrivate();
    }

    public Key getPrivateKey() {
        return privateKey;
    }

    public Key getPublicKey() {
        return publicKey;
    }
}
