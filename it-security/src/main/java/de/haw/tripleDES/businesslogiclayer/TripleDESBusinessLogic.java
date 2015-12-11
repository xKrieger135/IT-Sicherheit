package de.haw.tripleDES.businesslogiclayer;

import de.haw.tripleDES.dataaccesslayer.DES;
import de.haw.tripleDES.dataaccesslayer.TripleDES;

import java.io.*;

/**
 * Created by Paddy-Gaming on 10.12.2015.
 */
public class TripleDESBusinessLogic {

    private TripleDES tripleDES = null;
    private DES firstDES = null;
    private DES secondDES = null;
    private DES thirdDES = null;

    public TripleDESBusinessLogic(TripleDES tripleDES) {
        this.tripleDES = tripleDES;
        this.firstDES = this.tripleDES.getFirstDES();
        this.secondDES = this.tripleDES.getSecondDES();
        this.thirdDES = this.tripleDES.getThirdDES();
    }

    /**
     * This method will encrypt the given inputFile and will take the encrypted result
     * into the given outputFile.
     *
     * @param inputFile  The given inputFile which should be encrypted.
     * @param outputFile The given outputFile will contain the encrypted result from the inputFile.
     * @throws Exception This Exception will be thrown, when something went wrong with the FileInputStream or the
     *                   FileOutputStream.
     */
    public void encryptFile(File inputFile, File outputFile) throws Exception {

        InputStream inputStream = new FileInputStream(inputFile);
        OutputStream outputStream = new FileOutputStream(outputFile);

        // Create buffer for the encryption / decryption process.
        byte[] plaintext = new byte[8];
        byte[] encryptedText = new byte[8];

        byte[] initializeVector = tripleDES.getInitializeVector();

        // CFB first step with initialization vector
        // E(IV, K)

        firstDES.encrypt(initializeVector, 0, encryptedText, 0);
        secondDES.decrypt(encryptedText, 0, encryptedText, 0);
        thirdDES.encrypt(encryptedText, 0, encryptedText, 0);

        while ((inputStream.read(plaintext)) > 0) {

            // XOR the encrypted text with the next 8 bytes
            plaintext = xor(encryptedText, plaintext);


            // Next Encryption step.
            encryptDecryptEncrypt(firstDES, secondDES, thirdDES, plaintext, encryptedText);
            outputStream.write(plaintext);

        }

        inputStream.close();
        outputStream.close();
    }

    /**
     * This method will decrypt the given inputFile and will take the decrypted result
     * into the given outputFile.
     *
     * @param inputFile  The given inputFile which should be encrypted.
     * @param outputFile The given outputFile will contain the decrypted result from the inputFile.
     * @throws Exception This Exception will be thrown, when something went wrong with the FileInputStream or the
     *                   FileOutputStream.
     */
    public void decryptFile(File inputFile, File outputFile) throws Exception {

        InputStream inputStream = new FileInputStream(inputFile);
        OutputStream outputStream = new FileOutputStream(outputFile);

        // Create buffer for the encryption / decryption process.
        byte[] plaintext = new byte[8];
        byte[] encryptedText = new byte[8];
        byte[] decryptedText;

        byte[] initializeVector = tripleDES.getInitializeVector();

        // CFB first step with initialization vector
        // E(IV, K)

        firstDES.encrypt(initializeVector, 0, encryptedText, 0);
        secondDES.decrypt(encryptedText, 0, encryptedText, 0);
        thirdDES.encrypt(encryptedText, 0, encryptedText, 0);

        while ((inputStream.read(plaintext)) > 0) {

            // XOR the encrypted text with the next 8 bytes
            decryptedText = xor(encryptedText, plaintext);


            // Next Encryption step.
            encryptDecryptEncrypt(firstDES, secondDES, thirdDES, plaintext, encryptedText);
            outputStream.write(decryptedText);

        }

        inputStream.close();
        outputStream.close();
    }

    /**
     * This method will realize the tripleDES algorithm. Every given DES is used for a special step.
     *
     * @param firstDES  The firstDES is there to encrypt with a given key.
     * @param secondDES The secondDES is used to decrypt with a given key.
     * @param thirdDES  The thirdDES is used to encrypt again with a given key.
     * @param input     The given input is the buffer where the input text is stored.
     * @param output    The output is a buffer where the encrypted/decrypted text will be stored.
     */
    private void encryptDecryptEncrypt(DES firstDES, DES secondDES, DES thirdDES, byte[] input, byte[] output) {
        firstDES.encrypt(input, 0, output, 0);
        secondDES.decrypt(output, 0, output, 0);
        thirdDES.encrypt(output, 0, output, 0);
    }

    /**
     * @param encryptedText The encryptedText is the random generated value from E((IV, C), K).
     * @param plainText     This text is the next text which has to be encrypted / decrypted with the encryptedText.
     * @return The return value of this function is the created Chiffretext.
     */
    private byte[] xor(byte[] encryptedText, byte[] plainText) {
        byte[] chiffreText = new byte[8];
        for (int i = 0; i < 8; i++) {
            chiffreText[i] = (byte) (encryptedText[i] ^ plainText[i]);
        }
        return chiffreText;
    }
}
