package presenters;
import models.Toy;

import java.io.IOException;

public interface Machine {
    Toy findAtoy(String toyName);
    void loadMachine();
    boolean addToy(Toy toy) throws IOException;
    String lottery();
    boolean getToy(String toyName) throws IOException;
    public Double changeWeight(String toyName, double newWeight) throws IOException;
}
