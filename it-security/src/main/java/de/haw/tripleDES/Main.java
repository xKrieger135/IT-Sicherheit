package de.haw.tripleDES;

import de.haw.tripleDES.dataaccesslayer.DES;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;

/**
 * Created by patri on 24.11.2015.
 */
public class Main {

    public static void main(String[] args) throws Exception{
        long init;
        long first;
        long second;
        long third;

        File inputfile1 = new File("C:\\Users\\patri\\Desktop\\3DES\\3DESTest.enc");
        File inputfile2 = new File("C:\\Users\\patri\\Desktop\\3DES\\3DES.pdf");
        File keyfile = new File("C:\\Users\\patri\\Desktop\\3DES\\3DESTest.key");
        InputStream keyinput = new FileInputStream(keyfile.getAbsoluteFile());

        first = CommandLineRunner.readLong(keyinput);
        second = CommandLineRunner.readLong(keyinput);
        third = CommandLineRunner.readLong(keyinput);
        init = CommandLineRunner.readLong(keyinput);


        System.out.println(first);
        System.out.println(second);
        System.out.println(third);
        System.out.println(init);
        byte[] initNew = new byte[8];
        DES.writeBytes(init, initNew, 0, 8);

        TripleDES triple = new TripleDES(first, second, third, inputfile1);
        triple.setInitialisierungsVektor(initNew);
        triple.decrypt();
//        TripleDES triple1 = new TripleDES(first, second, third, inputfile2);
//        triple1.setInitialisierungsVektor(initNew);
//        triple1.encrypt();
//        triple1.decrypt();


    }

}
