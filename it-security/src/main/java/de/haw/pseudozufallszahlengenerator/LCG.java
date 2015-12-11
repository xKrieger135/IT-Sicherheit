package de.haw.pseudozufallszahlengenerator;

/**
 * Created by Patrick Steinhauer on 20.11.2015.
 */
public class LCG {

    private long x;
    private long a;
    private long b;
    private long m;

    /**
     * @param x0 ist der Startwert fuer den Pseudozufallszahlengenerator.
     */
    public LCG(long x0, long a, long b, long m) {
        this.x = x0;
        this.a = a;
        this.b = b;
        this.m = m;
    }

    public LCG(long x) {
        this.x = x;
        this.a = 65539;
        this.b = 0;
        this.m = (long) Math.pow(2, 32);
    }

    public long nextValue() {
        return x = (a * x + b) % m;
    }
}
