package de.haw.tripleDES.accesslayer;

import de.haw.tripleDES.accesslayer.interfaces.ITripleDES;
import de.haw.tripleDES.businesslogiclayer.TripleDESBusinessLogic;

import java.io.File;

/**
 * Created by Paddy-Gaming on 10.12.2015.
 */
public class TripleDESFassade implements ITripleDES{

    private TripleDESBusinessLogic tripleDESBusinessLogic = null;

    public TripleDESFassade(TripleDESBusinessLogic tripleDESBusinessLogic) {
        this.tripleDESBusinessLogic = tripleDESBusinessLogic;
    }

    public void encrypt(File inputFile, File outputFile) throws Exception{
        tripleDESBusinessLogic.encryptFile(inputFile, outputFile);
    }

    public void decrypt(File inputFile, File outputFile) throws Exception{
        tripleDESBusinessLogic.decryptFile(inputFile, outputFile);
    }
}
