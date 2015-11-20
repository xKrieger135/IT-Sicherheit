package tripleDES;

/**
 * Created by Paddy-Gaming on 20.11.2015.
 */
public class TripleDES {

    private DES firstDES;
    private DES secondDES;
    private DES thirdDES;
//    private Byte[8] initialisierungsVektor;

    public TripleDES(long firstKey, long secondKey, long thirdKey) {
        this.firstDES = new DES(firstKey);
        this.secondDES = new DES(secondKey);
        this.thirdDES = new DES(thirdKey);
    }

    public void encrypt() {

    }

    public void decrypt() {

    }
}
