package models;

import org.json.simple.JSONObject;

import java.time.LocalDateTime;

public class Toy {
    protected final String name;
    protected double weight;
    protected int quantity;
    protected final LocalDateTime dateOfShipment;

    public Toy(String name, double weight, int quantity) {
        this.name = name;
        this.weight = weight;
        this.quantity = quantity;
        this.dateOfShipment = LocalDateTime.now();

    }

    public String getName() {
        return name;
    }

    public double getWeight() {
        return weight;
    }

    public LocalDateTime getDateOfShipment() {
        return dateOfShipment;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setWeight(double weight) {
        this.weight = weight;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    @Override
    public String toString() {
        return String.format("%s: %d", name, quantity);
    }

    public JSONObject toJsonObj() {
        JSONObject obj = new JSONObject();
        obj.put("name", name);
        obj.put("quantity", quantity);
        obj.put("weight", weight);
        obj.put("date", dateOfShipment.toString());
        return obj;
    }

    public Toy(JSONObject object) {
        this.name = object.get("name").toString();
        this.quantity = Functions.longToInt((long) object.get("quantity"));
        this.weight = (double) object.get("weight");
        this.dateOfShipment = LocalDateTime.parse(object.get("date").toString());
    }


}
