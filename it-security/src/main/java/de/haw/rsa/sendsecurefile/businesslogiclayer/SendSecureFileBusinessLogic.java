package de.haw.rsa.sendsecurefile.businesslogiclayer;

import de.haw.rsa.rsaadapter.adapter.RSAKeyReaderAdapter;
import de.haw.rsa.rsakeycreation.dataaccesslayer.entities.PublicKey;

import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.Signature;

/**
 * Created by Patrick Steinhauer
 * On 11.Dez.2015
 */
public class SendSecureFileBusinessLogic {

    public SendSecureFileBusinessLogic() {

    }

    private SecretKey createSecretAESKey() {
        SecretKey secretAESKey = null;

        try {
            KeyGenerator keyGenerator = KeyGenerator.getInstance("AES");
            keyGenerator.init(128);

            secretAESKey = keyGenerator.generateKey();
        } catch (NoSuchAlgorithmException noSuchAlgorithmException) {
            noSuchAlgorithmException.printStackTrace();
        }

        return secretAESKey;
    }

    private Signature createSignatureForSecretAESKey(java.security.PublicKey publicKey) {
        Signature rsaSignature = null;

        try {
            rsaSignature = Signature.getInstance("SHA256withRSA");
            rsaSignature.initVerify(publicKey);
        } catch (NoSuchAlgorithmException noSuchAlgorithmException) {
            noSuchAlgorithmException.printStackTrace();
        } catch (InvalidKeyException invalidKeyException) {
            invalidKeyException.printStackTrace();
        }

        return rsaSignature;
    }
}
