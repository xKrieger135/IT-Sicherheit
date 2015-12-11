package de.haw.rsa.sendsecurefile.accesslayer;

import de.haw.rsa.rsaadapter.adapter.RSAKeyReaderAdapter;
import de.haw.rsa.rsakeycreation.dataaccesslayer.entities.PrivateKey;
import de.haw.rsa.rsakeycreation.dataaccesslayer.entities.PublicKey;
import de.haw.rsa.sendsecurefile.accesslayer.interfaces.ISendSecureFile;
import de.haw.rsa.sendsecurefile.businesslogiclayer.SendSecureFileBusinessLogic;

import java.io.File;

/**
 * Created by Patrick Steinhauer
 * On 11.Dez.2015
 */
public class SendSecureFileFassade implements ISendSecureFile {

    private SendSecureFileBusinessLogic sendSecureFileBusinessLogic = null;
    private RSAKeyReaderAdapter rsaKeyReaderAdapter = null;

    public SendSecureFileFassade(RSAKeyReaderAdapter rsaKeyReaderAdapter) {
        this.sendSecureFileBusinessLogic = new SendSecureFileBusinessLogic();
        this.rsaKeyReaderAdapter = rsaKeyReaderAdapter;
    }

    public File encryptFileWithAES() {
        return null;
    }

    public PublicKey getPublicKey() {
        return null;
    }

    public PrivateKey getPrivateKey() {
        return null;
    }


}
