package de.haw.rsa.sendsecurefile.businesslogiclayer;

import de.haw.rsa.rsaadapter.adapter.RSAKeyReaderAdapter;
import de.haw.rsa.rsakeycreation.dataaccesslayer.entities.PrivateKey;
import de.haw.rsa.rsakeycreation.dataaccesslayer.entities.PublicKey;
import de.haw.rsa.sendsecurefile.dataaccesslayer.entities.AESKey;
import de.haw.rsa.sendsecurefile.dataaccesslayer.entities.RSASignature;

import javax.crypto.*;
import javax.crypto.spec.SecretKeySpec;
import java.io.*;
import java.security.*;
import java.util.Arrays;

/**
 * Created by Patrick Steinhauer
 * On 11.Dez.2015
 */
public class SendSecureFileBusinessLogic {

    private RSAKeyReaderAdapter rsaKeyReaderAdapter = null;
    private byte[] params = null;

    public SendSecureFileBusinessLogic(RSAKeyReaderAdapter rsaKeyReaderAdapter) {
        this.rsaKeyReaderAdapter = rsaKeyReaderAdapter;
    }

    /**
     * This method creates a secretKey for the algorithm AES.
     * The created key has the key length of 128 Bit.
     *
     * @return Returns the secretAESKey.
     */
    public AESKey createSecretAESKey() {
        SecretKey secretAESKey = null;
        AESKey aesKey = new AESKey();

        try {
            KeyGenerator keyGenerator = KeyGenerator.getInstance("AES");
            keyGenerator.init(128);

            secretAESKey = keyGenerator.generateKey();

            // save also the secret key for encrypt aeskey
            aesKey.setsKey(secretAESKey);
            aesKey.setAlgorithm(secretAESKey.getAlgorithm());
            aesKey.setSecretKey(secretAESKey.getEncoded());
        } catch (NoSuchAlgorithmException noSuchAlgorithmException) {
            noSuchAlgorithmException.printStackTrace();
        }

        return aesKey;
    }

    /**
     * This method creates a signature for the secretAESKey. The creation is realized with the
     * private RSA key. To create this signature, there is used the "SHA256withRSA" algorithm.
     *
     * @param privateKey This is the given private RSA key.
     * @param secretKey  This is the given secret AES key.
     * @return           Returns a RSASignature, which contains the signature.
     */
    public RSASignature createSignatureForSecretAESKey(PrivateKey privateKey, AESKey secretKey) {
        Signature signature = null;
        RSASignature rsaSignature = new RSASignature();

        try {
            signature = Signature.getInstance("SHA256withRSA");
            // Signature should be realized with the private key. Also initialize here to sign the signature
            signature.initSign(privateKey.getKey());
            // The given data should be signed
            signature.update(secretKey.getSecretKey());

            // sign the updated data at step before and save into our own datastructure
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

    /**
     * This method encrypts the given secret AES key with a given public RSA key.
     * The used algorithm here is RSA.
     *
     * @param publicKey    The given public RSA key is used to encrypt the secret AES key.
     * @param inputAESKey  The given secret AES key which will be encrypt.
     * @return             Returns an AESKey, which contains now the encrypted AESKey.
     */
    public AESKey encryptSecretAESKey(PublicKey publicKey, AESKey inputAESKey) {
        AESKey aesKey = inputAESKey;
        try {
            Cipher cipher = Cipher.getInstance("RSA");
            cipher.init(Cipher.ENCRYPT_MODE, publicKey.getKey());

            // nun werden die Daten verschluesselt
            // (update wird bei grossen Datenmengen mehrfach aufgerufen werden!)
            byte[] sinnvollerName = cipher.update(aesKey.getSecretKey());

            // mit doFinal abschliessen (Rest inkl. Padding ..)
            aesKey.setSecretKeyEncrypted(concatenate(sinnvollerName , cipher.doFinal()));
            aesKey.setAlgorithmParameters(cipher.getParameters());

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

    /**
     * This method gets the public RSA key from a given file.
     *
     * @param fileName The given fileName as a String.
     * @return         Returns the public RSA key contained in the given file.
     */
    public PublicKey getPublicKey(String fileName) {
        return rsaKeyReaderAdapter.readPublicKey(fileName);
    }

    /**
     * This method gets the private RSA key from a given file.
     *
     * @param fileName The given fileName as a String.
     * @return         Returns the private RSA key contained in the given file.
     */
    public PrivateKey getPrivateKey(String fileName) {
        return rsaKeyReaderAdapter.readPrivateKey(fileName);
    }

    /**
     * This method encrypts a given file with the given keys.
     * There are needed the public RSA key, the private RSA key and the secret AES key.
     *
     * @param publicKey   The public RSA key.
     * @param privateKey  The private RSA key.
     * @param secretKey   The secret AES key.
     * @param inputFile   The inputFile, which should be encrypted.
     * @return            Returns the encrypted data as a byte array.
     */
    public byte[] encryptFile(PublicKey publicKey, PrivateKey privateKey, AESKey secretKey, String inputFile) {

        AESKey aesKey = secretKey;
        File newFile = new File(inputFile);
        byte[] result = null;

        try {

            DataInputStream dataInputStream = new DataInputStream(new FileInputStream(inputFile));
            // NoPadding because CTR does not need a padding
            Cipher cipher = Cipher.getInstance("AES/CTR/NoPadding");

            // Initialisierung zur Verschluesselung mit automatischer
            // Parametererzeugung
            cipher.init(Cipher.ENCRYPT_MODE, secretKey.getsKey());

            params = cipher.getParameters().getEncoded();

            int length = (int) newFile.length();
            byte[] buffer = new byte[length];
            dataInputStream.read(buffer, 0, length);
            dataInputStream.close();

            // mit doFinal abschliessen (Rest inkl. Padding ..)
            result = cipher.doFinal(buffer);


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

        return result;
    }

    /**
     * This method writes all needed data into a file.
     * 1. The length of the encrypted secret key as an integer.
     * 2. The encrypted secret key as a byte array.
     * 3. The length of the signature of the secret key as an integer.
     * 4. The signature of the secret key as an byte array.
     * 5. The length of the algorithm parameters of the secret key as an integer.
     * 6. The algorithm parameters as a byte array.
     * 7. The encrypted file as a byte array.
     *
     * @param inputFile   The given inputFile which will be encrypted.
     * @param outputFile  The outputFile, where the data will be stored.
     * @param publicKey   The public RSA key.
     * @param privateKey  The private RSA key.
     */
    public void writeToFile(String inputFile, String outputFile, PublicKey publicKey, PrivateKey privateKey) {

        AESKey aesKey = createSecretAESKey();
        RSASignature rsaSignature = createSignatureForSecretAESKey(privateKey, aesKey);
        AESKey newAESKey = encryptSecretAESKey(publicKey, aesKey);
        aesKey.setSecretKeyEncrypted(newAESKey.getSecretKeyEncrypted());

        try {
            DataOutputStream output = new DataOutputStream(new FileOutputStream(outputFile));

            byte[] key = aesKey.getSecretKeyEncrypted();
            byte[] signature = rsaSignature.getSignature();
            byte[] encryptedFile = encryptFile(publicKey, privateKey, aesKey, inputFile);
            byte[] parameters = params;

            int lengthOfSecretEncryptedAESKey = aesKey.getSecretKeyEncrypted().length;
            int lengthOfSignature = rsaSignature.getSignature().length;
            int lengthOfAlgorithmParameters = parameters.length;

            output.writeInt(lengthOfSecretEncryptedAESKey);
            output.write(key);
            output.writeInt(lengthOfSignature);
            output.write(signature);
            output.writeInt(lengthOfAlgorithmParameters);
            output.write(parameters);
            output.write(encryptedFile);

            output.close();

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private byte[] concatenate(byte[] ba1, byte[] ba2) {
        int len1 = ba1.length;
        int len2 = ba2.length;
        byte[] result = new byte[len1 + len2];

        // Fill with first array
        System.arraycopy(ba1, 0, result, 0, len1);
        // Fill with second array
        System.arraycopy(ba2, 0, result, len1, len2);

        return result;
    }
}
