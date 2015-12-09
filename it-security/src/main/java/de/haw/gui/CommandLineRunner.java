package de.haw.gui;

import de.haw.tripleDES.TripleDES;
import de.haw.tripleDES.TripleDES;

import java.io.*;
import java.nio.ByteBuffer;

/**
 * Created by JanDennis on 20.11.2015.
 */
public class CommandLineRunner {
    private static File targetFile;
    private static File keyFile;
    private static File outputFile;
    private static String operation;

    private static Long firstKey;
    private static Long secondKey;
    private static Long thirdKey;
    private static Long initVector;
    private static byte[] initVectorBytes;

    public static void main(String[] args) {
        // No Param: view Help
        if (args.length == 0) {
            printHelp();
            return;
        }

        //check if arg count is correct
        if (args.length != 4) {
            System.out.println("Error: Wrong parameter count!");
            return;
        }

        targetFile = new File(args[0]);
        keyFile = new File(args[1]);
        outputFile = new File(args[2]);
        operation = args[3];

        // check files & operation
        if (!(targetFile.exists() && keyFile.exists() && outputFile.exists() && (operation.equals("decrypt") || operation.equals("encrypt")))) {
            System.out.println("Error: A specified file does not exist or operation is invalid!");
            return;
        }

        // get keys & init vector
        try {
            InputStream keyStream = new FileInputStream(keyFile.getAbsoluteFile());
            firstKey = readLong(keyStream);
            secondKey = readLong(keyStream);
            thirdKey = readLong(keyStream);
            //initVector = readLong(keyStream);
            initVectorBytes = readBytes(keyStream);
        } catch (FileNotFoundException e) {
            //e.printStackTrace();
            System.out.println("Error: Key file not found!");
            return;
        } catch (IOException e) {
            //e.printStackTrace();
            System.out.println("Error reading keyfile (File might be too short?)");
            return;
        }

        System.out.println("Results: ");
        System.out.println("    Target:     " + targetFile.getAbsolutePath());
        System.out.println("    Output:     " + outputFile.getAbsolutePath());
        System.out.println("    First Key:  " + firstKey);
        System.out.println("    Second Key: " + secondKey);
        System.out.println("    Third Key:  " + thirdKey);
        //System.out.println("    InitVector: "+initVector);

        TripleDES algo = new TripleDES(firstKey, secondKey, thirdKey, targetFile, outputFile, initVectorBytes);
        try {


            if (operation.equals("encrypt")) {
                algo.encrypt();
            } else {
                algo.decrypt();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return;
    }

    public static void printHelp() {
        System.out.println("TripleDES by Patrick Steinhauer & Jan Bartels");
        System.out.println("Parameter:");
        System.out.println("    [1]: Path to File to be encrypted / decrypted");
        System.out.println("    [2]: Path to Key-File, has to include 3 keys & init-vector (32 byte in total)");
        System.out.println("    [3]: Path to output-file");
        System.out.println("    [4]: Operation 'encrypt' or 'decrypt'");
        System.out.println();
    }

    public static long bytesToLong(byte[] bytes) {
        ByteBuffer buffer = ByteBuffer.allocate(Long.BYTES);
        buffer.put(bytes);
        buffer.flip();//need flip
        return buffer.getLong();
    }

    public static long readLong(InputStream stream) throws IOException {
        byte[] buffer = new byte[8];
        for (int i = 0; i < 8; i++) {
            buffer[i] = (byte) stream.read();
        }
        return bytesToLong(buffer);
    }

    public static byte[] readBytes(InputStream stream) throws IOException {
        byte[] buffer = new byte[8];
        for (int i = 0; i < 8; i++) {
            buffer[i] = (byte) stream.read();
        }
        return buffer;
    }
}
