package de.haw.rsa;

import java.io.File;

/**
 * Created by JanDennis on 25.11.2015.
 */
public class CommandLineRunner {
    private static File privateKeyFile;
    private static File publicKeyFile;

    public static void main(String[] args) {
        if (args.length!=1) {
            System.out.println("Wrong Parameter count!");
            return;
        }
        String name = args[0];
        RSACrypt RSA = new RSACrypt();

        privateKeyFile = new File(name+".prv");
        publicKeyFile = new File(name+".pub");

    }
}
