package models;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import presenters.Machine;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;


public class RaffleMachine implements Machine {
    public ArrayList<String> getToysName() {
        return toysName;
    }

    private ArrayList<String> toysName;

    public RaffleMachine() {
        this.toysName = new ArrayList<>();
    }

    public Toy findAtoy(String toyName) throws RuntimeException {
        try (FileReader reader = new FileReader("toys.json");) {
            JSONParser parser = new JSONParser();
            for (Object obj : (JSONArray) parser.parse(reader)) {
                if (obj instanceof JSONObject) {
                    if (((JSONObject) obj).containsValue(toyName)) {
                        return new Toy((JSONObject) obj);
                    }
                }

            }

        } catch (IOException | ParseException e) {
            throw new RuntimeException(e);
        }
        return null;
    }

    @Override
    public boolean addToy(Toy newToy) throws IOException {

        try (FileReader reader = new FileReader("toys.json");
             Scanner scanner = new Scanner(reader);
             FileWriter writer = new FileWriter("temp.json")) {
            JSONParser parser = new JSONParser();
            while (scanner.hasNextLine()) {
                String line = scanner.nextLine();
                if (line.startsWith("{")) {
                    JSONObject object = (JSONObject) parser.parse(line);
                    if (!toysName.contains(object.get("name").toString())) {
                        toysName.add(object.get("name").toString());
                    }
                    if (object.get("name").equals(newToy.getName()) & toysName.contains(newToy.getName())) {
                        object.put("quantity", Functions.longToInt((long) object.get("quantity")) + newToy.getQuantity());
                        object.put("date", newToy.getDateOfShipment().toString());
                        writer.write(object.toJSONString());
                        writer.write("\n");
                    } else {
                        writer.write(line);
                        writer.write("\n");

                    }
                } else if (line.startsWith("]") & !toysName.contains(newToy.getName())) {
                    toysName.add(newToy.getName());
                    writer.write(",\n");
                    writer.write(newToy.toJsonObj().toJSONString());
                    writer.write("\n");
                    writer.write("]");
                } else {
                    writer.write(line);
                    writer.write("\n");
                }

            }
        } catch (ParseException | IOException e) {
            e.printStackTrace();
            return false;
        }
        Files.move(Paths.get("temp.json"), Paths.get("toys.json"), StandardCopyOption.REPLACE_EXISTING);
        return true;
    }


    @Override
    public void loadMachine() {
        File toys = new File("toys.json");
        if (!toys.exists()) {
            try (
                    FileWriter writer = new FileWriter(toys)) {
                Toy[] toyList = new Toy[]{new Toy("Car", 50, 5), new Toy("Doll", 40, 7), new Toy("Ball", 30, 10), new Toy("Surprise", 10, 20)};
                writer.write("[\n");
                for (int i = 0; i < toyList.length; i++) {
                    toysName.add(toyList[i].getName());
                    writer.write(toyList[i].toJsonObj().toJSONString());
                    if (i < toyList.length - 1) {
                        writer.write('\n');
                        writer.write(",");
                    }
                    writer.write('\n');
                }
                writer.write("]");
            } catch (IOException e) {
                throw new RuntimeException(e);
            }

        }
    }


    @Override
    public String lottery() {
        ArrayList<String> raffleList = new ArrayList<>();
        try (FileReader reader = new FileReader("toys.json");
             Scanner scanner = new Scanner(reader)) {
            JSONParser parser = new JSONParser();
            while (scanner.hasNextLine()) {
                String line = scanner.nextLine();
                if (line.startsWith("{")) {
                    JSONObject object = (JSONObject) parser.parse(line);
                    for (int i = 0; i < (double) object.get("weight") * Functions.longToInt((long) object.get("quantity")); i++) {
                        raffleList.add(object.get("name").toString());
                    }
                }
            }

        } catch (IOException | ParseException e) {
            throw new RuntimeException(e);
        }
        Random random = new Random();
        return raffleList.get(random.nextInt(raffleList.size()));
    }

    @Override
    public boolean getToy(String toyName) throws IOException {
        boolean flag = false;
        try (FileReader reader = new FileReader("toys.json");
             Scanner scanner = new Scanner(reader);
             FileWriter writer = new FileWriter("temp.json")
        ) {
            JSONParser parser = new JSONParser();
            while (scanner.hasNextLine()) {
                String line = scanner.nextLine();
                if (line.startsWith("{")) {
                    JSONObject object = (JSONObject) parser.parse(line);
                    if (object.get("name").equals(toyName)) {
                        object.put("quantity", Functions.longToInt((long) object.get("quantity")) - 1);
                        writer.write(object.toJSONString() + "\n");

                        flag = true;
                    } else {
                        writer.write(line + "\n");
                    }
                } else {
                    writer.write(line + "\n");
                }
            }
            reader.close();

        } catch (IOException | ParseException e) {
            throw new RuntimeException(e);
        }

        Files.move(Paths.get("temp.json"), Paths.get("toys.json"), StandardCopyOption.REPLACE_EXISTING);
        return flag;
    }

    public Double changeWeight(String toyName, double newWeight) throws IOException {
        Double weight  = newWeight;
        try (FileReader reader = new FileReader("toys.json");
        Scanner scanner = new Scanner(reader);
        FileWriter writer = new FileWriter("temp.json")){
            JSONParser parser = new JSONParser();
            while (scanner.hasNextLine()){
                String line = scanner.nextLine();
                if (line.startsWith("{")){
                    JSONObject object = (JSONObject) parser.parse(line);
                    if (object.get("name").equals(toyName)){
                        weight = (double) object.get("weight");
                        object.put("weight", newWeight);
                        writer.write(object.toJSONString() + "\n");
                    }else{
                        writer.write(object.toJSONString() + "\n");
                    }
                } else{
                    writer.write(line + "\n");
                }
            }

        } catch (IOException | ParseException e) {
            throw new RuntimeException(e);
        }
        Files.move(Paths.get("temp.json"), Paths.get("toys.json"), StandardCopyOption.REPLACE_EXISTING);
        return weight;
    }




}
