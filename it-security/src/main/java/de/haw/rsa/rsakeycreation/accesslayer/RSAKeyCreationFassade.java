package de.haw.rsa.rsakeycreation.accesslayer;

import de.haw.rsa.rsakeycreation.accesslayer.interfaces.IRSACreation;
import de.haw.rsa.rsakeycreation.businesslogiclayer.RSACreationBusinessLogic;
import de.haw.rsa.rsakeycreation.dataaccesslayer.entities.PrivateKey;
import de.haw.rsa.rsakeycreation.dataaccesslayer.entities.PublicKey;

/**
 * Created by Paddy-Gaming on 09.12.2015.
 */
public class RSAKeyCreationFassade implements IRSACreation {
    private RSACreationBusinessLogic rsaCreationBusinessLogic;


    public RSAKeyCreationFassade(String name) {
        rsaCreationBusinessLogic = new RSACreationBusinessLogic(name);
    }

/*
    public KeyPair createRSAKeyPair(String keyPairCreationAlgorithm) {
        KeyPair rsaKeyPair = rsaCreationBusinessLogic.createRSAKeyPair(keyPairCreationAlgorithm);

        return rsaKeyPair;
    }*/

    // TODO: Exceptions wenn keyOwner und / oder rsaKeyPair == null.
    public PublicKey createPublicKey(String publicKeyOwner) {
        PublicKey publicKey = rsaCreationBusinessLogic.createPublicKey(publicKeyOwner);

        return publicKey;
    }

    // TODO: Exceptions wenn keyOwner und / oder rsaKeyPair == null.
    public PrivateKey createPrivateKey(String privateKeyOwner) {
        return rsaCreationBusinessLogic.createPrivateKey(privateKeyOwner);
    }
}
