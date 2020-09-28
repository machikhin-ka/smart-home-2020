package ru.sbt.mipt.oop;

import java.io.IOException;

public class Application {
    private final SmartHomeDeserialization smartHomeDeserialization = new SmartHomeDeserialization();
    private final SensorEventProcessingCycle sensorEventProcessingCycle;

    public Application() throws IOException {
        SmartHome smartHome = smartHomeDeserialization.toDeserialize("smart-home-1.js");
        sensorEventProcessingCycle = new SensorEventProcessingCycle(smartHome);
    }

    public static void main(String... args) throws IOException {
        Application application = new Application();
        // считываем состояние дома из файла
        SmartHome smartHome = application.smartHomeDeserialization.toDeserialize("smart-home-1.js");
        // начинаем цикл обработки событий
        application.sensorEventProcessingCycle.toStartSensorEventProcessingCycle();
    }
}
