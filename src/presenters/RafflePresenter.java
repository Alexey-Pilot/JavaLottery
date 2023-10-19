package presenters;

import models.Toy;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;

public class RafflePresenter implements ViewObserver {
    private final Machine machine;
    private final View view;

    public RafflePresenter(Machine machine, View view) {
        this.machine = machine;
        this.view = view;
        view.setObserver(this);
    }

    public void loadMachine() {
        machine.loadMachine();
    }

    public void updateToyUI() {
        loadMachine();
        view.showToys();
    }

    public void addToyUI(Toy toy) throws IOException {

        view.printAdditionToyResult(toy, machine.addToy(toy));
        updateToyUI();
    }

    public void lotteryUI() throws IOException {
        String prize  = machine.lottery();
        view.printLotteryResult(prize);
        machine.getToy(prize);
        updateToyUI();
    }

    public void changeWeightUI(String toyName, int newWeight) throws IOException {
        view.printChangingWeightResult(toyName, newWeight, machine.changeWeight(toyName, newWeight));
    }


}
