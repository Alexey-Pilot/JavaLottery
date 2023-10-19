import models.RaffleMachine;
import models.Toy;
import org.json.simple.parser.ParseException;
import presenters.RafflePresenter;
import views.RaffleView;

import java.io.IOException;

public class Program {
    public static void main(String[] args) throws IOException, ParseException {
        RafflePresenter presenter = new RafflePresenter(new RaffleMachine(), new RaffleView());
        presenter.updateToyUI();
        Toy car = new Toy("fish", 50, 50);
        presenter.addToyUI(car);
        presenter.lotteryUI();
        presenter.changeWeightUI("fish", 25);





    }
}