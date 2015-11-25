package haw.chiffre1;

import pseudozufallszahlengenerator.LCG;

import java.io.*;
import java.nio.ByteBuffer;

/**
 * Created by Paddy-Gaming on 20.11.2015.
 */
public class HC1 {

    private LCG random;
    private File file;
    private byte[] seed;

    public HC1(long key, File file) {
        this.random = new LCG(key);
        this.file = file;
        this.seed = ByteBuffer.allocate(8).putLong(random.nextValue()).array();
    }

    public void crypt() throws Exception {
        InputStream inputStream = new FileInputStream(file.getAbsoluteFile());
        System.out.println("Out: "+ file.getAbsolutePath()+".crypt");

        OutputStream outputStream = new FileOutputStream(new File(file.getAbsolutePath()+".crypt"));

        int pups = inputStream.read();
        int count = 0;

        while (pups != -1) {
            int result = pups ^ seed[count % 8];
            outputStream.write(result);
            count++;
            pups = inputStream.read();
        }

        inputStream.close();
        outputStream.close();
    }
}
