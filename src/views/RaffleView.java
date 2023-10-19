package views;

import models.Toy;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import presenters.View;
import presenters.ViewObserver;

import java.io.FileReader;
import java.io.IOException;
import java.util.Scanner;

public class RaffleView implements View {
    private ViewObserver observer;

    @Override
    public void setObserver(ViewObserver observer) {
        this.observer = observer;

    }

    @Override
    public void showToys() {
        System.out.println("Available toys:");
        try (FileReader reader = new FileReader("toys.json");
             Scanner scanner = new Scanner(reader);) {
            JSONParser parser = new JSONParser();
            while (scanner.hasNextLine()) {
                try {
                    String toyJson = scanner.nextLine();
                    if (toyJson.endsWith("}")) {
                        JSONObject toy = (JSONObject) parser.parse(toyJson);
                        System.out.printf("     %s: %s\n", toy.get("name"), toy.get("quantity"));
                    }
                } catch (ParseException e) {
                    System.out.println("@Error");
                }
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void printAdditionToyResult(Toy toy, boolean b) {
        if (b) {
            System.out.printf("There are %d %s added into lottery\n", toy.getQuantity(), toy.getName());
        } else {
            System.out.println("Something wrong!");
        }

    }

    @Override
    public void printLotteryResult(String prize) {
        System.out.printf("You won: %s\n", prize);
    }

    @Override
    public void printChangingWeightResult(String toyName, double newWeight, double oldWeight) {
        if (newWeight == oldWeight){
            System.out.printf("Weight of %s are not changed, current weight %.2f\n", toyName, oldWeight);
        }else if(newWeight > oldWeight){
            System.out.printf("Weight of %s are increased, new weight %.2f\n", toyName, newWeight);
        } else {
            System.out.printf("Weight og %s are decreased, new weight %.2f\n", toyName, newWeight);
        }

    }
}
