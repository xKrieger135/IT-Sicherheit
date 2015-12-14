package de.haw.rsa.receivesecuredfile.accesslayer;

import de.haw.rsa.receivesecuredfile.accesslayer.interfaces.IReceiveSecureFile;
import de.haw.rsa.receivesecuredfile.businesslogiclayer.ReceiveSecureFileBusinessLogic;
import de.haw.rsa.rsaadapter.adapter.RSAKeyReaderAdapter;

import java.io.File;

/**
 * Created by JanDennis on 11.12.2015.
 */
public class ReceiveSecureFileFassade implements IReceiveSecureFile {
    ReceiveSecureFileBusinessLogic businessLogic = null;

    public ReceiveSecureFileFassade() {
        businessLogic = new ReceiveSecureFileBusinessLogic();
    }

    @Override
    public void decryptFileWithAES(File privateKeyFile, File publicKeyFile, File inputFile, File outputFile) {
        businessLogic.decryptFile(privateKeyFile,publicKeyFile,inputFile,outputFile);
    }
}
