package de.haw.rsa.accesslayer;

import de.haw.rsa.accesslayer.interfaces.IRSACreation;
import de.haw.rsa.businesslogiclayer.RSACreationBusinessLogic;
import de.haw.rsa.dataaccesslayer.entities.PrivateKey;
import de.haw.rsa.dataaccesslayer.entities.PublicKey;

import java.security.KeyPair;

/**
 * Created by Paddy-Gaming on 09.12.2015.
 */
public class RSAKeyCreationFassade implements IRSACreation{

    private RSACreationBusinessLogic rsaCreationBusinessLogic = new RSACreationBusinessLogic();

    public KeyPair createRSAKeyPair(String keyPairCreationAlgorithm) {
        KeyPair rsaKeyPair = rsaCreationBusinessLogic.createRSAKeyPair(keyPairCreationAlgorithm);

        return rsaKeyPair;
    }

    // TODO: Exceptions wenn keyOwner und / oder rsaKeyPair == null.
    public PublicKey createPublicKey(String publicKeyOwner, KeyPair rsaKeyPair) {
        PublicKey publicKey = rsaCreationBusinessLogic.createPublicKey(publicKeyOwner, rsaKeyPair);

        return publicKey;
    }

    // TODO: Exceptions wenn keyOwner und / oder rsaKeyPair == null.
    public PrivateKey createPrivateKey(String privateKeyOwner, KeyPair rsaKeyPair) {
        return null;
    }
}
