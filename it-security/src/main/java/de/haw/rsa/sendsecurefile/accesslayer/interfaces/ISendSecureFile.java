package de.haw.rsa.sendsecurefile.accesslayer.interfaces;

import de.haw.rsa.rsakeycreation.dataaccesslayer.entities.PrivateKey;
import de.haw.rsa.rsakeycreation.dataaccesslayer.entities.PublicKey;

import java.io.File;

/**
 * Created by Patrick Steinhauer
 * On 11.Dez.2015
 */
public interface ISendSecureFile {

    File encryptFileWithAES();

    PublicKey getPublicKey();

    PrivateKey getPrivateKey();

}
