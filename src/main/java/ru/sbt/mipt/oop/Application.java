package ru.sbt.mipt.oop;

public class Application {
    private final SensorEventProcessingCycle sensorEventProcessingCycle;

    public Application(SmartHomeDeserialization deserialization) {
        // считываем состояние дома из файла
        SmartHome smartHome = deserialization.toDeserialize();
        sensorEventProcessingCycle = new SensorEventProcessingCycle(smartHome);
    }

    public static void main(String... args) {
        JsonSmartHomeDeserialization deserialization = new JsonSmartHomeDeserialization("smart-home-1.js");
        Application application = new Application(deserialization);
        // начинаем цикл обработки событий
        application.run();
    }

    public void run() {
        sensorEventProcessingCycle.toStartSensorEventProcessingCycle();
    }
}
