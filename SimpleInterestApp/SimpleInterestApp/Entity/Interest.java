package Entity;

import java.io.Serializable;

public abstract class Interest implements Serializable {
    protected double principal;
    protected double rate;
    protected int time;

    public Interest(double principal, double rate, int time) {
        this.principal = principal;
        this.rate = rate;
        this.time = time;
    }

    public abstract double calculateInterest();

    public double getPrincipal() {
        return principal;
    }

    public double getRate() {
        return rate;
    }

    public int getTime() {
        return time;
    }
}