package de.haw.rsa.sendsecurefile.businesslogiclayer;

import de.haw.rsa.rsaadapter.adapter.RSAKeyReaderAdapter;
import de.haw.rsa.rsakeycreation.dataaccesslayer.entities.PublicKey;
import de.haw.rsa.sendsecurefile.dataaccesslayer.entities.AESKey;
import de.haw.rsa.sendsecurefile.dataaccesslayer.entities.RSASignature;

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

    private AESKey createSecretAESKey() {
        SecretKey secretAESKey = null;
        AESKey aesKey = new AESKey();

        try {
            KeyGenerator keyGenerator = KeyGenerator.getInstance("AES");
            keyGenerator.init(128);

            secretAESKey = keyGenerator.generateKey();

            aesKey.setAlgorithm(secretAESKey.getAlgorithm());
            aesKey.setSecretKey(secretAESKey.getEncoded());
        } catch (NoSuchAlgorithmException noSuchAlgorithmException) {
            noSuchAlgorithmException.printStackTrace();
        }

        return aesKey;
    }

    private RSASignature createSignatureForSecretAESKey(java.security.PrivateKey privateKey, AESKey secretKey) {
        Signature signature = null;
        RSASignature rsaSignature = new RSASignature();

        try {
            signature = Signature.getInstance("SHA256withRSA");
            // Signature should be realized with the private key
            signature.initSign(privateKey);
            // The given data should be signed
            signature.update(secretKey.getSecretKey());

            rsaSignature.setSignature(signature.sign());
            rsaSignature.setAlgorithm(signature.getAlgorithm());
            rsaSignature.setProvider(signature.getProvider());
        } catch (NoSuchAlgorithmException noSuchAlgorithmException) {
            noSuchAlgorithmException.printStackTrace();
        } catch (InvalidKeyException invalidKeyException) {
            invalidKeyException.printStackTrace();
        } catch (SignatureException signatureException) {
            signatureException.printStackTrace();
        }

        return rsaSignature;
    }

    private AESKey encryptSecretAESKey(java.security.PublicKey publicKey, AESKey aesKey) {

        try {
            Cipher cipher = Cipher.getInstance("AES");
            cipher.init(Cipher.ENCRYPT_MODE, publicKey);

            aesKey.setSecretKeyEncrypted(cipher.doFinal());

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

        return aesKey;
    }
}
