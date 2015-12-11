package de.haw.rsa;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by JanDennis on 11.12.2015.
 */
public class CommandLineHelper {
    private static BufferedReader reader;
    private static List<String> returnParameter;

    public static String[] help() {
        reader = new BufferedReader(new InputStreamReader(System.in));
        returnParameter = new ArrayList<>();

        System.out.println("Which Tool do you want to Start?");
        System.out.println("(RSAKey[C]reation / [S]SF / [R]SF) or do you need [I]nfo?");

        showToolSpecificHelp(readUserInput());

        return returnParameter.toArray(new String[returnParameter.size()]);
    }

    private static String readUserInput() {
        String result = null;
        try {
            result = reader.readLine();
        } catch (IOException e) {
            e.printStackTrace();
            System.exit(0);
        }
        return result;
    }

    private static void showToolSpecificHelp(String tool) {
        switch (tool.toLowerCase()) {
            case "c":
            case "rsa":
            case "rsakeycreation":
                returnParameter.add("RSA");
                helpRSA();
                break;
            case "s":
            case "ssf":
                returnParameter.add("SSF");
                System.out.println("SendSecureFile tool");
                helpSSFandRSF();
                break;
            case "r":
            case "rsf":
                returnParameter.add("RSF");
                System.out.println("ReceiveSecureFile tool");
                helpSSFandRSF();
                break;
            case "i":
            case "info":
                showInfo();
                break;
            default:
                System.out.println("Input not recognized. Terminating..");
                break;
        }
    }

    private static void showInfo() {
        System.out.println("RSAKeyCreation: Generate a Public & Private RSA-Key File.");
        System.out.println("SSF: validate RSA Keys and encrypt a given File via AES.");
        System.out.println("RSF: Encrypt a SSF crypted File.");
    }

    private static void helpRSA() {
        System.out.println("RSAKeyCreation tool");
        System.out.println("Owner Name: ");
        returnParameter.add(readUserInput());

        System.out.println("Starting tool now.. ");
    }

    private static void helpSSFandRSF() {
        System.out.println("Private Key File: ");
        String prvKeyFile = readUserInput();
        checkIfFileIsValid(prvKeyFile);
        returnParameter.add(prvKeyFile);
        System.out.println("Public Key File: ");
        String pubKeyFile = readUserInput();
        checkIfFileIsValid(pubKeyFile);
        returnParameter.add(pubKeyFile);

        System.out.println("Input File: ");
        String inputFile = readUserInput();
        checkIfFileIsValid(inputFile);
        returnParameter.add(inputFile);
        System.out.println("Output File: ");
        String outputFile = readUserInput();
        checkIfFileIsValid(outputFile);
        returnParameter.add(outputFile);

        System.out.println("Starting Tool..");

    }

    private static void checkIfFileIsValid(String file) {
        if (!(new File(file).exists() && new File(file).canRead())) {
            System.out.println("File is not valid!");
            System.exit(0);
        }
    }
}
