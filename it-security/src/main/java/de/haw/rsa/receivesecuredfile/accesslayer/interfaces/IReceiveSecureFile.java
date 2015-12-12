package de.haw.rsa.receivesecuredfile.accesslayer.interfaces;

import java.io.File;

/**
 * Created by JanDennis on 11.12.2015.
 */
public interface IReceiveSecureFile {

    File decryptFileWithAES(File inputFile);
}
