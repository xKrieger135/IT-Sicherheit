package tripleDES;

import java.io.*;

/**
 * Created by Paddy-Gaming on 20.11.2015.
 */
public class TripleDES {

    private File inputfile;
    private File outputfile;

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

    public void encrypt() throws Exception{
        InputStream inputStream = new FileInputStream(inputfile);
        OutputStream outputStream = new FileOutputStream(outputfile);
        byte[] newBuffer = new byte[8];
        byte[] readBuffer = new byte[8];
        byte[] encryptionBuffer = new byte[8];

        // CFB first step with initialization vector
        firstDES.encrypt(initialisierungsVektor, 0, encryptionBuffer, 0);
        secondDES.decrypt(encryptionBuffer, 0, encryptionBuffer, 0);
        thirdDES.encrypt(encryptionBuffer, 0, encryptionBuffer, 0);
        int lenght;

        while((lenght = inputStream.read(readBuffer)) > 0) {
            newBuffer = xor(encryptionBuffer,readBuffer);
            //Todo High von Kaffee, später  lösen
            firstDES.encrypt(newBuffer, lenght, encryptionBuffer, lenght);
            secondDES.decrypt(newBuffer, lenght, encryptionBuffer, lenght);
            thirdDES.encrypt(encryptionBuffer, lenght, encryptionBuffer, lenght);

        }

        inputStream.close();
        outputStream.close();
    }

    public void decrypt() {

    }

    public byte[] xor(byte[] a, byte[] b) {
        byte[] c = new byte[b.length];
        for (int i = 0; i < b.length; i++) {
            c[i] = (byte) (a[i] ^ b[i]);
        }
        return c;
    }

    public byte[] getInitialisierungsVektor() {
        return initialisierungsVektor;
    }

    public void setInitialisierungsVektor(byte[] initialisierungsVektor) {
        this.initialisierungsVektor = initialisierungsVektor;
    }
}
