package de.haw.tripleDES.accesslayer.interfaces;

import java.io.File;

/**
 * Created by Paddy-Gaming on 10.12.2015.
 */
public interface ITripleDES {

    /**
     * This method should decrypt a given file and will save the decrypted file into
     * a new File.
     *
     * @param inputFile  The given inputFile, which should be encrypted.
     * @param outputFile The given outputFile, which represents the encrypted inputFile.
     */
    void encrypt(File inputFile, File outputFile) throws Exception;

    /**
     * This method should decrypt a given file and will save the decrypted file into
     * a new File.
     *
     * @param inputFile  The given inputFile, which should be encrypted.
     * @param outputFile The given outputFile, which represents the encrypted inputFile.
     */
    void decrypt(File inputFile, File outputFile) throws Exception;
}
