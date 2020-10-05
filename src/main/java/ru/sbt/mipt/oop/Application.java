package ru.sbt.mipt.oop;

import java.util.ArrayList;
import java.util.List;

public class Application {
    private final SensorEventGetter eventGetter;
    private final List<SensorEventHandler> handlers;

    public Application(SensorEventGetter eventGetter, List<SensorEventHandler> handlers) {
        this.eventGetter = eventGetter;
        this.handlers = handlers;
    }

    public static void main(String... args) {
        List<SensorEventHandler> handlers = new ArrayList<>();
        handlers.add(new DoorEventHandler());
        handlers.add(new LightEventHandler());
        RandomSensorEventGetter randomSensorEventGetter = new RandomSensorEventGetter();
        Application application = new Application(randomSensorEventGetter, handlers);
        // начинаем цикл обработки событий
        application.run();
    }

    public void run() {
        JsonSmartHomeDeserialization deserialization = new JsonSmartHomeDeserialization("smart-home-1.js");
        SmartHome smartHome = deserialization.deserialize();
        new SensorEventProcessingCycle(smartHome, eventGetter, handlers).start();
    }
}
