package ru.sbt.mipt.oop;

import java.io.IOException;
import java.util.Arrays;

public class HomeBuilder {
    private final SmartHomeSerialization serialization = new SmartHomeSerialization();

    public static void main(String[] args) throws IOException {
        HomeBuilder homeBuilder = new HomeBuilder();
        SmartHome smartHome = homeBuilder.createSmartHome();
        homeBuilder.serialization.toSerialize(smartHome, "output.js");
    }

    private SmartHome createSmartHome() {
        Room kitchen = new Room(Arrays.asList(new Light("1", false), new Light("2", true)),
                Arrays.asList(new Door(false, "1")),
                "kitchen");
        Room bathroom = new Room(Arrays.asList(new Light("3", true)),
                Arrays.asList(new Door(false, "2")),
                "bathroom");
        Room bedroom = new Room(Arrays.asList(new Light("4", false), new Light("5", false), new Light("6", false)),
                Arrays.asList(new Door(true, "3")),
                "bedroom");
        Room hall = new Room(Arrays.asList(new Light("7", false), new Light("8", false), new Light("9", false)),
                Arrays.asList(new Door(false, "4")),
                "hall");
        return new SmartHome(Arrays.asList(kitchen, bathroom, bedroom, hall));
    }

}
