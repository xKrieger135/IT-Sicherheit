package de.haw.rsa.rsakeycreation.businesslogiclayer;

import de.haw.rsa.rsakeycreation.dataaccesslayer.entities.PrivateKey;
import de.haw.rsa.rsakeycreation.dataaccesslayer.entities.PublicKey;

import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.NoSuchAlgorithmException;

/**
 * Created by Patrick Steinhauer on 09.12.2015.
 */
public class RSACreationBusinessLogic {
    KeyPair rsaKeyPair;

    public RSACreationBusinessLogic(String name) {
        rsaKeyPair = createRSAKeyPair(name);
    }

    /**
     * @param keyPairCreationAlgorithm
     * @return
     */
    private KeyPair createRSAKeyPair(String keyPairCreationAlgorithm) {
        KeyPair rsaKeyPair = null;

        try {
            if (keyPairCreationAlgorithm.equals("RSA")) {
                KeyPairGenerator keyPairGenerator = KeyPairGenerator.getInstance(keyPairCreationAlgorithm);
                keyPairGenerator.initialize(2048);

                rsaKeyPair = keyPairGenerator.generateKeyPair();
            }
        } catch (NoSuchAlgorithmException e) {
            System.out.println("The given algorithm is not a valid for the keypair creation. Please use RSA as algorithm");
            e.printStackTrace();
        }

        return rsaKeyPair;
    }

    /**
     * @param publicKeyOwner
     * @return
     */
    public PublicKey createPublicKey(String publicKeyOwner) {
        PublicKey publicKey = new PublicKey(rsaKeyPair.getPublic(), publicKeyOwner.getBytes());

        return publicKey;
    }

    /**
     *
     * @param privateKeyOwnerName
     * @return
     */
    public PrivateKey createPrivateKey(String privateKeyOwnerName) {
        PrivateKey privateKey = new PrivateKey(rsaKeyPair.getPrivate(), privateKeyOwnerName.getBytes());

        return privateKey;
    }
}
