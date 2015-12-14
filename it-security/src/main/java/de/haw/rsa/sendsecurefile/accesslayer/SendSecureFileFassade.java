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

    public SendSecureFileFassade(RSAKeyReaderAdapter rsaKeyReaderAdapter) {
        this.sendSecureFileBusinessLogic = new SendSecureFileBusinessLogic(rsaKeyReaderAdapter);
    }

    public byte[] encryptFileWithAES(String privateKeyFile, String publicKeyFile, String file) {
        PublicKey publicKey = sendSecureFileBusinessLogic.getPublicKey(publicKeyFile);
        PrivateKey privateKey = sendSecureFileBusinessLogic.getPrivateKey(privateKeyFile);
        return sendSecureFileBusinessLogic.encryptFile(publicKey, privateKey, file);
    }

    public void writeToFile(String outputFile, String publicKeyFile, String privateKeyFile) {
        PublicKey publicKey = sendSecureFileBusinessLogic.getPublicKey(publicKeyFile);
        PrivateKey privateKey = sendSecureFileBusinessLogic.getPrivateKey(privateKeyFile);

        sendSecureFileBusinessLogic.writeToFile(outputFile, publicKey, privateKey);

    }
}
