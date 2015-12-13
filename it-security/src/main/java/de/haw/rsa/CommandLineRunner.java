package de.haw.rsa;

import de.haw.rsa.rsaadapter.adapter.RSAKeyCreationAdapter;
import de.haw.rsa.rsaadapter.adapter.RSAKeyReaderAdapter;
import de.haw.rsa.rsakeycreation.accesslayer.RSAKeyCreationFassade;
import de.haw.rsa.rsakeycreation.dataaccesslayer.entities.PrivateKey;
import de.haw.rsa.rsakeycreation.dataaccesslayer.entities.PublicKey;
import de.haw.rsa.sendsecurefile.accesslayer.SendSecureFileFassade;
import de.haw.rsa.sendsecurefile.accesslayer.interfaces.ISendSecureFile;

import java.io.File;
import java.util.Arrays;

/**
 * Created by JanDennis on 25.11.2015.
 */
public class CommandLineRunner {
    private static final String name = "Kryptography Tool";
    private static final String authors = "Patrick Steinhauer & Jan Bartels";
    private static final String version = "v0.0.1";
    private static final String publicFileExtension = ".pub";
    private static final String privateFileExtension = ".prv";

    public static void main(String[] args) {
        String[] arguments = args;
        System.out.println(name + " " + version + " - by " + authors);
        if (args.length == 0) {
            arguments = CommandLineHelper.help();
            System.out.println(Arrays.toString(arguments));
        }
        switch (arguments[0]) {
            case "RSA":
                startRSA(arguments[1]);
                break;
            case "SSF":
                startSSF(arguments);
                break;
            case "RSF":
                startRSF();
                break;
            case "DEBUG":
                startDebug(arguments[1]);
                break;
            default:
                System.out.println("Error, wrong param!");
                return;
        }
    }

    private static void startSSF(String[] args) {
        RSAKeyReaderAdapter readerAdapter = new RSAKeyReaderAdapter();
        ISendSecureFile SSF = new SendSecureFileFassade(readerAdapter);

        byte[] buff = SSF.encryptFileWithAES(args[1],args[2],args[3]);

        System.out.println("OUT: \n====\n"+Arrays.toString(buff));
        System.out.println("SIZE: "+buff.length);
    }

    private static void startRSF() {


    }

    private static void startRSA(String name) {
        RSAKeyCreationFassade RSA = new RSAKeyCreationFassade("RSA");
        File publicKeyFile = new File(name + publicFileExtension);
        File privateKeyFile = new File(name + privateFileExtension);
        PublicKey publicKey = RSA.createPublicKey(name);
        PrivateKey privateKey = RSA.createPrivateKey(name);

        RSAKeyCreationAdapter writerAdapter = new RSAKeyCreationAdapter(privateKeyFile,publicKeyFile,privateKey,publicKey);
        writerAdapter.createFiles();

        System.out.println(publicKeyFile.getAbsolutePath());
        System.out.println(System.getProperty("user.dir"));
        System.out.println("Finished!");
    }
    private static void startDebug(String name) {
        RSAKeyReaderAdapter reader = new RSAKeyReaderAdapter();
        System.out.println(System.getProperty("user.dir") + "\\" +name + ".prv");
        java.security.Key first = reader.readPrivateKey(System.getProperty("user.dir") + "\\" +name + ".prv");
        java.security.Key second = reader.readPublicKey(System.getProperty("user.dir") + "\\" + name + ".pub");

        System.out.println("FIN");
        System.out.println(first.toString());
        System.out.println(second.toString());
    }
}
