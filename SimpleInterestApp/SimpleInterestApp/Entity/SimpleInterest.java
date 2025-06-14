package Entity;

public class SimpleInterest extends Interest {
    public SimpleInterest(double principal, double rate, int time) {
        super(principal, rate, time);
    }

    @Override
    public double calculateInterest() {
        return (principal * rate * time) / 100;
    }
}