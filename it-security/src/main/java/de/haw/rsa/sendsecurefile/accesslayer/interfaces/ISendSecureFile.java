package de.haw.rsa.sendsecurefile.accesslayer.interfaces;

import java.io.File;

/**
 * Created by Patrick Steinhauer
 * On 11.Dez.2015
 */
public interface ISendSecureFile {

    byte[] encryptFileWithAES(String privateKeyFile, String publicKeyFile, String file);

    File writeToFile(String outputFile, String publicKeyFile, String privateKeyFile);

}
