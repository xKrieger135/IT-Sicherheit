package de.haw.rsa.businesslogiclayer;

import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.NoSuchAlgorithmException;
import java.security.PublicKey;

/**
 * Created by Patrick Steinhauer on 09.12.2015.
 */
public class RSACreationBusinessLogic {

    public RSACreationBusinessLogic() {

    }

    public KeyPair createRSAKeyPair(String keyPairCreationAlgorithm) {
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

    public PublicKey createPublicKey(String publicKeyOwner, KeyPair rsaKeyPair) {
        PublicKey publicKey = null;

        publicKey = rsaKeyPair.getPublic();

        return publicKey;
    }
}
