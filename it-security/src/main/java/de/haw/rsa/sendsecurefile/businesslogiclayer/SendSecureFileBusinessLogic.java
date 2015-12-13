package de.haw.rsa.sendsecurefile.businesslogiclayer;

import de.haw.rsa.rsaadapter.adapter.RSAKeyReaderAdapter;
import de.haw.rsa.sendsecurefile.dataaccesslayer.entities.AESKey;
import de.haw.rsa.sendsecurefile.dataaccesslayer.entities.RSASignature;

import javax.crypto.*;
import javax.crypto.spec.SecretKeySpec;
import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.security.*;

/**
 * Created by Patrick Steinhauer
 * On 11.Dez.2015
 */
public class SendSecureFileBusinessLogic {

    private RSAKeyReaderAdapter rsaKeyReaderAdapter = null;

    public SendSecureFileBusinessLogic(RSAKeyReaderAdapter rsaKeyReaderAdapter) {
        this.rsaKeyReaderAdapter = rsaKeyReaderAdapter;
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

    private RSASignature createSignatureForSecretAESKey(PrivateKey privateKey, AESKey secretKey) {
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

    private AESKey encryptSecretAESKey(PublicKey publicKey, AESKey inputAESKey) {
            AESKey aesKey = inputAESKey;
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

    public java.security.PublicKey getPublicKey(String fileName) {
        return rsaKeyReaderAdapter.readPublicKey(fileName);
    }

    public PrivateKey getPrivateKey(String fileName) {
        return rsaKeyReaderAdapter.readPrivateKey(fileName);
    }

    public File encryptFile(PublicKey publicKey, PrivateKey privateKey, String file) {

        AESKey aesKey = createSecretAESKey();

        try {

            DataInputStream dataInputStream = new DataInputStream(new FileInputStream(file));
            Cipher cipher = Cipher.getInstance("AES/CTR");

            SecretKeySpec secretKeySpec = new SecretKeySpec(aesKey.getSecretKey(), "AES");
            cipher.init(Cipher.ENCRYPT_MODE, secretKeySpec);
            

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (NoSuchPaddingException e) {
            e.printStackTrace();
        } catch (InvalidKeyException e) {
            e.printStackTrace();
        }
    }
}
