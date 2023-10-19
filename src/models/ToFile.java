package models;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

public class ToFile {
    public void toJsonArray(ArrayList<Toy> toys){
        JSONObject toysList = new JSONObject();
        JSONArray list = new JSONArray();
        list.addAll(toys);
        toysList.put("intoMachine", list);

        try (FileWriter file = new FileWriter("toys.json")) {
            file.write(toysList.toJSONString());
        } catch (IOException e) {
            e.printStackTrace();
        } ;

    }

}
