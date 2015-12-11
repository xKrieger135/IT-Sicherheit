package de.haw.pseudozufallszahlengenerator;

import de.haw.chiffre.HC1;

import javax.swing.*;
import java.awt.*;
import java.io.File;

/**
 * Created by Paddy-Gaming on 20.11.2015.
 */
public class Main {
    public static void main(String[] args) {
        testHC1(1234234253);
        System.out.println("FIN");
        System.exit(0);
        return;
    }

    public static void testLCG() {
        LCG lcg = new LCG(0, 214013, 13523655, (long) Math.pow(2, 24));
        for (int i = 0; i < 25; i++) {
            long x = lcg.nextValue();
            System.out.println("Result = " + x);
        }
    }

    public static void testHC1(long key) {
        FileDialog fd = new FileDialog(new JFrame("wurstgeschwader"));
        fd.show();

        File file = new File(fd.getDirectory() + fd.getFile());

        System.out.println("IN: " + file.toString() + "  -E: " + file.exists());

        HC1 hawChiffre = new HC1(key, file);

        try {
            hawChiffre.crypt();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
