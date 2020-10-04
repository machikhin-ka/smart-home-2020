package ru.sbt.mipt.oop;

public class Application {
    private final SensorEventProcessingCycle sensorEventProcessingCycle;

    public Application(SmartHomeDeserialization deserialization, SensorEventGetter eventGetter) {
        // считываем состояние дома из файла
        SmartHome smartHome = deserialization.deserialize();
        sensorEventProcessingCycle = new SensorEventProcessingCycle(smartHome, eventGetter);
    }

    public static void main(String... args) {
        JsonSmartHomeDeserialization deserialization = new JsonSmartHomeDeserialization("smart-home-1.js");
        SensorEventGetter sensorEventGetter = new SensorEventGetter();
        Application application = new Application(deserialization, sensorEventGetter);
        // начинаем цикл обработки событий
        application.run();
    }

    public void run() {
        sensorEventProcessingCycle.start();
    }
}
