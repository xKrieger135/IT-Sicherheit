package de.haw.tripleDES.dataaccesslayer;

import java.io.File;

/**
 * Created by Paddy-Gaming on 10.12.2015.
 */
public class TripleDES {

    private DES firstDES;
    private DES secondDES;
    private DES thirdDES;
    private byte[] initializeVector;

    public TripleDES(long firstKey, long secondKey, long thirdKey, byte[] initializeVector, File inputFile) {
        this.firstDES = new DES(firstKey);
        this.secondDES = new DES(secondKey);
        this.thirdDES = new DES(thirdKey);
        this.initializeVector = initializeVector;
    }

    /**
     * @return
     */
    public DES getFirstDES() {
        return firstDES;
    }

    /**
     * @return
     */
    public DES getSecondDES() {
        return secondDES;
    }

    /**
     * @return
     */
    public DES getThirdDES() {
        return thirdDES;
    }

    /**
     * @return
     */
    public byte[] getInitializeVector() {
        return initializeVector;
    }
}
