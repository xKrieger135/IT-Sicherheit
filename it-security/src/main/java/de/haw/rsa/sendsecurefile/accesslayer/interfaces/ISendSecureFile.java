package de.haw.rsa.sendsecurefile.accesslayer.interfaces;

import java.io.File;

/**
 * Created by Patrick Steinhauer
 * On 11.Dez.2015
 */
public interface ISendSecureFile {

    /**
     * Encrypts a given file.
     *
     * @param privateKeyFile The file, where the private RSA key is stored.
     * @param publicKeyFile  The file, where the public RSA key is stored.
     * @param inputFile      The file which should be encrypted.
     * @return               Returns a byte array with the encrypted file data.
     */
    byte[] encryptFileWithAES(String privateKeyFile, String publicKeyFile, String inputFile);

    /**
     * This method will write all needed data into a file.
     *
     * @param inputFile      The file, which should be encrypted.
     * @param outputFile     The file, where the data will be stored.
     * @param publicKeyFile  The file, which contains the public RSA key.
     * @param privateKeyFile The file, which contains the private RSA key.
     */
    void writeToFile(String inputFile, String outputFile, String publicKeyFile, String privateKeyFile);

}
