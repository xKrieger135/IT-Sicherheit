package de.haw.rsa.accesslayer.interfaces;

import de.haw.rsa.dataaccesslayer.entities.PrivateKey;
import de.haw.rsa.dataaccesslayer.entities.PublicKey;

import java.security.KeyPair;

/**
 * Created by Patrick Steinhauer on 09.12.2015.
 */
public interface IRSACreation {

    /**
     * This method will create a keypair for the given keyPairCreationAlgorithm.
     * The given algorithm should be the RSA Algorithm for this case.
     *
     * @param keyPairCreationAlgorithm This is the algorithm, which will be used to create the keyPair.
     * @return Returns a KeyPair which contains the public and private key for the RSA algorithm.
     */
    KeyPair createRSAKeyPair(String keyPairCreationAlgorithm);

    PublicKey createPublicKey(String publicKeyOwner, KeyPair rsaKeyPair);

    PrivateKey createPrivateKey(String privateKeyOwner, KeyPair rsaKeyPair);
}
