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

    public RSACreationBusinessLogic() {

    }

    /**
     * @param keyPairCreationAlgorithm
     * @return
     */
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

    /**
     * @param publicKeyOwner
     * @param rsaKeyPair
     * @return
     */
    public PublicKey createPublicKey(String publicKeyOwner, KeyPair rsaKeyPair) {
        PublicKey publicKey = new PublicKey();

        publicKey.setKey(rsaKeyPair.getPublic().getEncoded());
        publicKey.setKeyOwnerName(publicKeyOwner.getBytes());

        return publicKey;
    }

    public PrivateKey createPrivateKey(String privateKeyOwnerName, KeyPair rsaKeyPair) {
        PrivateKey privateKey = new PrivateKey();

        privateKey.setKey(rsaKeyPair.getPrivate().getEncoded());
        privateKey.setKeyOwnerName(privateKeyOwnerName.getBytes());

        return privateKey;
    }
}
