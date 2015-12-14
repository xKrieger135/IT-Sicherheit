package de.haw.rsa.receivesecuredfile.accesslayer.interfaces;

import java.io.File;

/**
 * Created by JanDennis on 11.12.2015.
 */
public interface IReceiveSecureFile {

    /**
     * Decrypts a File.
     * @param privateKeyFile
     * @param publicKeyFile
     * @param inputFile
     * @param outputFile
     */
    void decryptFileWithAES(File privateKeyFile, File publicKeyFile, File inputFile, File outputFile);
}
