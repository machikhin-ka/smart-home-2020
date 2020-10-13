package ru.sbt.mipt.oop;

import ru.sbt.mipt.oop.command.CommandSender;
import ru.sbt.mipt.oop.deserialization.JsonSmartHomeDeserialization;
import ru.sbt.mipt.oop.domain.SmartHome;
import ru.sbt.mipt.oop.events.RandomSensorEventProvider;
import ru.sbt.mipt.oop.events.SensorEventProvider;
import ru.sbt.mipt.oop.handlers.*;
import ru.sbt.mipt.oop.handlers.decorator.alarm.AlarmDecorator;
import ru.sbt.mipt.oop.handlers.decorator.Decorator;
import ru.sbt.mipt.oop.handlers.decorator.alarm.AlarmSendMessageDecorator;

import java.util.Arrays;
import java.util.List;

public class Application {
    private final SensorEventProvider eventGetter;
    private final List<SensorEventHandler> handlers;
    private final List<Decorator> decorators;

    public Application(SensorEventProvider eventGetter, List<SensorEventHandler> handlers, List<Decorator> decorators) {
        this.eventGetter = eventGetter;
        this.handlers = handlers;
        this.decorators = decorators;
    }

    public static void main(String... args) {
        CommandSender commandSender = new CommandSender();
        List<SensorEventHandler> handlers
                = Arrays.asList(new DoorEventHandler(),
                new LightEventHandler(),
                new HallEventHandler(commandSender),
                new SignalingEventHandler());
        List<Decorator> decorators = Arrays.asList(new AlarmDecorator(), new AlarmSendMessageDecorator());
        SensorEventProvider randomSensorEventProvider = new RandomSensorEventProvider();
        Application application = new Application(randomSensorEventProvider, handlers, decorators);
        // начинаем цикл обработки событий
        application.run();
    }

    public void run() {
        JsonSmartHomeDeserialization deserialization = new JsonSmartHomeDeserialization("smart-home-1.js");
        SmartHome smartHome = deserialization.deserialize();
        new SensorEventProcessingCycle(smartHome, eventGetter, handlers, decorators).start();
    }
}
