package presenters;

import models.Toy;

public interface View {
    void setObserver(ViewObserver observer);
    void showToys();
    void printAdditionToyResult(Toy toy, boolean b);
    void printLotteryResult(String prize);
    void printChangingWeightResult(String toyName, double newWeight, double oldWeight);
}
