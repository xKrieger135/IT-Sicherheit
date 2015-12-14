package de.haw.rsa.receivesecuredfile.businesslogiclayer;

import de.haw.rsa.receivesecuredfile.dataaccesslayer.SendSecureFile;
import de.haw.rsa.rsaadapter.adapter.RSAKeyReaderAdapter;
import de.haw.rsa.rsaadapter.adapter.SSFReaderAdapter;
import de.haw.rsa.rsakeycreation.dataaccesslayer.entities.PrivateKey;
import de.haw.rsa.rsakeycreation.dataaccesslayer.entities.PublicKey;
import de.haw.rsa.sendsecurefile.dataaccesslayer.entities.AESKey;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.SecretKeySpec;
import java.io.*;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.Signature;
import java.security.SignatureException;

/**
 * Created by JanDennis on 11.12.2015.
 */
public class ReceiveSecureFileBusinessLogic {
    RSAKeyReaderAdapter rsaKeyReaderAdapter;
    SSFReaderAdapter ssfReaderAdapter;

    public ReceiveSecureFileBusinessLogic() {
        rsaKeyReaderAdapter = new RSAKeyReaderAdapter();
        ssfReaderAdapter = new SSFReaderAdapter();
    }

    public void decryptFile(File privateKeyFile, File publicKeyFile, File inputFile, File outputFile) {
        PrivateKey privateKey = rsaKeyReaderAdapter.readPrivateKey(privateKeyFile.getAbsolutePath());
        PublicKey publicKey = rsaKeyReaderAdapter.readPublicKey(publicKeyFile.getAbsolutePath());
        SendSecureFile ssfFile = ssfReaderAdapter.readSSFFile(inputFile);

        System.out.println("Decrypt Key\n===========\n");
        AESKey aesKey = decryptAESKey(privateKey);

        System.out.println("Decrypt File\n============\n");
        decryptFile(inputFile, outputFile, aesKey, ssfFile);

        System.out.println("Verify:");
        Boolean r = verifyFileSignature(ssfFile, publicKey);

        System.out.println("v " + r);
    }

    private AESKey decryptAESKey(PrivateKey privateKey) {
        AESKey resultKey = null;
        try {
            Cipher cipher = Cipher.getInstance("RSA");

            cipher.init(Cipher.DECRYPT_MODE, privateKey.getKey());

            resultKey = new AESKey();
            resultKey.setAlgorithmParameters(cipher.getParameters());
            resultKey.setSecretKey(cipher.doFinal());
        } catch (InvalidKeyException e) {
            e.printStackTrace();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (NoSuchPaddingException e) {
            e.printStackTrace();
        } catch (BadPaddingException e) {
            e.printStackTrace();
        } catch (IllegalBlockSizeException e) {
            e.printStackTrace();
        }
        return resultKey;
    }

    private void decryptFile(File encryptedFile, File outputFile, AESKey aesKey, SendSecureFile ssf) {
        try {
            DataInputStream inputStream = new DataInputStream(new FileInputStream(encryptedFile));
            DataOutputStream outputStream = new DataOutputStream(new FileOutputStream(outputFile));

            Cipher cipher = Cipher.getInstance(ssf.getAlgorithmicParameters().toString());

            SecretKeySpec keySpec = new SecretKeySpec(aesKey.getSecretKey(), "AES");
            cipher.init(Cipher.DECRYPT_MODE, keySpec);

            int length = (int) encryptedFile.length();
            byte[] buffer = new byte[length];
            inputStream.read(buffer);
            inputStream.close();

            outputStream.write(cipher.doFinal(buffer));

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (NoSuchPaddingException e) {
            e.printStackTrace();
        } catch (InvalidKeyException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (BadPaddingException e) {
            e.printStackTrace();
        } catch (IllegalBlockSizeException e) {
            e.printStackTrace();
        }
    }

    private Boolean verifyFileSignature(SendSecureFile ssfFile, PublicKey publicKey) {
        Boolean result=false;
        try {
            Signature signature = Signature.getInstance("SHA256withRSA");
            signature.initVerify(publicKey.getKey());
            result = signature.verify(ssfFile.getSignature());
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (InvalidKeyException e) {
            e.printStackTrace();
        } catch (SignatureException e) {
            e.printStackTrace();
        }
        return result;
    }
}
