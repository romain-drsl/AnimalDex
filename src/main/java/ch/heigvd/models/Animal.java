package ch.heigvd.models;

public class Animal {

    private int number;
    private String frenchName;
    private String latinName;
    private AnimalGroup group;

    public Animal() {}

    public Animal(int number, String frenchName, String latinName, AnimalGroup group) {
        this.number = number;
        this.frenchName = frenchName;
        this.latinName = latinName;
        this.group = group;
    }

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    public String getFrenchName() {
        return frenchName;
    }

    public void setFrenchName(String frenchName) {
        this.frenchName = frenchName;
    }

    public String getLatinName() {
        return latinName;
    }

    public void setLatinName(String latinName) {
        this.latinName = latinName;
    }

    public AnimalGroup getGroup() {
        return group;
    }

    public void setGroup(AnimalGroup group) {
        this.group = group;
    }
}

