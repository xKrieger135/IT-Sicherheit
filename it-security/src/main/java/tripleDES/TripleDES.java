package tripleDES;

import java.io.*;

/**
 * Created by Patrick Steinhauer on 20.11.2015.
 */
public class TripleDES {

    private File inputfile;
    private File outputfile = new File("C:\\Users\\patri\\Desktop\\3DES\\3DES.pdf");

    private DES firstDES;
    private DES secondDES;
    private DES thirdDES;
    private byte[] initialisierungsVektor;

    public TripleDES(long firstKey, long secondKey, long thirdKey, File inputfile) {
        this.firstDES = new DES(firstKey);
        this.secondDES = new DES(secondKey);
        this.thirdDES = new DES(thirdKey);
        this.inputfile = inputfile;
        initialisierungsVektor = new byte[8];
    }

    public TripleDES(long firstKey, long secondKey, long thirdKey, File Input, File Output, byte[] initVector) {
        this.firstDES = new DES(firstKey);
        this.secondDES = new DES(secondKey);
        this.thirdDES = new DES(thirdKey);
        this.inputfile = Input;
        this.outputfile = Output;
        initialisierungsVektor = initVector;
    }

    /**
     *  This function is for the encryption with TripleDES.
     * @throws Exception Exception for streams.
     */
    public void decrypt() throws Exception{
        InputStream inputStream = new FileInputStream(inputfile);
        OutputStream outputStream = new FileOutputStream(outputfile);

        byte[] plaintext = new byte[8];
        byte[] encryptedText = new byte[8];
        byte[] decryptedText;

        // CFB first step with initialization vector
        // E(IV, K)
        firstDES.encrypt(initialisierungsVektor, 0, encryptedText, 0);
        secondDES.decrypt(encryptedText, 0, encryptedText, 0);
        thirdDES.encrypt(encryptedText, 0, encryptedText, 0);

        while((inputStream.read(plaintext)) > 0) {

            // XOR the encrypted text with the next 8 bytes
            decryptedText = xor(encryptedText, plaintext);
            outputStream.write(decryptedText);

            // Next Encryption step.
            firstDES.encrypt(plaintext, 0, encryptedText, 0);
            secondDES.decrypt(encryptedText, 0, encryptedText, 0);
            thirdDES.encrypt(encryptedText, 0, encryptedText, 0);

        }

        inputStream.close();
        outputStream.close();
    }

    public void encrypt() throws Exception{
        InputStream inputStream = new FileInputStream(inputfile);
        OutputStream outputStream = new FileOutputStream(outputfile);

        byte[] plainText = new byte[8];
        byte[] encryptedText = new byte[8];

        // CFB first step with initialization vector
        firstDES.encrypt(initialisierungsVektor, 0, encryptedText, 0);
        secondDES.decrypt(encryptedText, 0, encryptedText, 0);
        thirdDES.encrypt(encryptedText, 0, encryptedText, 0);

        while((inputStream.read(plainText)) > 0) {

            plainText = xor(encryptedText, plainText);

            firstDES.encrypt(plainText, 0, encryptedText, 0);
            secondDES.decrypt(encryptedText, 0, encryptedText, 0);
            thirdDES.encrypt(encryptedText, 0, encryptedText, 0);
            outputStream.write(plainText);
        }

        inputStream.close();
        outputStream.close();
    }

    /**
     *
     * @param encryptedText The encryptedText is the random generated value from E((IV, C), K).
     * @param plainText This text is the next text which has to be encrypted / decrypted with the encryptedText.
     * @return The return value of this function is the created Chiffretext.
     */
    public byte[] xor(byte[] encryptedText, byte[] plainText) {
        byte[] chiffreText = new byte[8];
        for (int i = 0; i < 8; i++) {
            chiffreText[i] = (byte) (encryptedText[i] ^ plainText[i]);
        }
        return chiffreText;
    }

    /**
     *
     * @return The initialize vector.
     */
    public byte[] getInitialisierungsVektor() {
        return initialisierungsVektor;
    }

    /**
     * Sets the initialize vector for the CFB mode.
     * @param initialisierungsVektor Initialize vector for the first encryption / decryption step.
     */
    public void setInitialisierungsVektor(byte[] initialisierungsVektor) {
        this.initialisierungsVektor = initialisierungsVektor;
    }


}
