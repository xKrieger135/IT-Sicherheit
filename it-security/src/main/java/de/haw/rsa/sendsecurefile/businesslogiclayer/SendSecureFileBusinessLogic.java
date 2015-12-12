package de.haw.rsa.sendsecurefile.businesslogiclayer;

import de.haw.rsa.rsaadapter.adapter.RSAKeyReaderAdapter;
import de.haw.rsa.rsakeycreation.dataaccesslayer.entities.PublicKey;

import javax.crypto.*;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.Signature;
import java.security.SignatureException;

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

    private Signature createSignatureForSecretAESKey(java.security.PrivateKey privateKey, SecretKey secretKey) {
        Signature rsaSignature = null;

        try {
            rsaSignature = Signature.getInstance("SHA256withRSA");
            // Signature should be realized with the private key
            rsaSignature.initSign(privateKey);
            // The given data should be signed
            rsaSignature.update(secretKey.getEncoded());

            rsaSignature.sign();
        } catch (NoSuchAlgorithmException noSuchAlgorithmException) {
            noSuchAlgorithmException.printStackTrace();
        } catch (InvalidKeyException invalidKeyException) {
            invalidKeyException.printStackTrace();
        } catch (SignatureException signatureException) {
            signatureException.printStackTrace();
        }

        return rsaSignature;
    }

    private byte[] encryptSecretAESKey(java.security.PublicKey publicKey) {
        byte[] encryptedAESKey = null;

        try {
            Cipher cipher = Cipher.getInstance("AES");
            cipher.init(Cipher.ENCRYPT_MODE, publicKey);

            encryptedAESKey = cipher.doFinal();

        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (NoSuchPaddingException e) {
            e.printStackTrace();
        } catch (InvalidKeyException e) {
            e.printStackTrace();
        } catch (BadPaddingException e) {
            e.printStackTrace();
        } catch (IllegalBlockSizeException e) {
            e.printStackTrace();
        }

        return encryptedAESKey;
    }
}
