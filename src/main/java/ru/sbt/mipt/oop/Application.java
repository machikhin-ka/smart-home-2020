package ru.sbt.mipt.oop;

import ru.sbt.mipt.oop.command.CommandSender;
import ru.sbt.mipt.oop.deserialization.JsonSmartHomeDeserialization;
import ru.sbt.mipt.oop.domain.SmartHome;
import ru.sbt.mipt.oop.events.RandomSensorEventProvider;
import ru.sbt.mipt.oop.events.SensorEventProvider;
import ru.sbt.mipt.oop.handlers.*;
import ru.sbt.mipt.oop.handlers.decorator.alarm.AlarmDecoratorProvider;
import ru.sbt.mipt.oop.handlers.decorator.DecoratorProvider;
import ru.sbt.mipt.oop.handlers.decorator.alarm.AlarmSendMessageDecoratorProvider;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Application {
    private final SensorEventProvider eventGetter;
    private final List<SensorEventHandler> handlers;
    private final List<DecoratorProvider> decoratorProviders;

    public Application(SensorEventProvider eventGetter, List<SensorEventHandler> handlers, List<DecoratorProvider> decoratorProviders) {
        this.eventGetter = eventGetter;
        this.handlers = handlers;
        this.decoratorProviders = decoratorProviders;
    }

    public static void main(String... args) {
        CommandSender commandSender = new CommandSender();
        List<SensorEventHandler> handlers
                = Arrays.asList(new DoorEventHandler(),
                new LightEventHandler(),
                new HallEventHandler(commandSender),
                new SignalingEventHandler());
        List<DecoratorProvider> decoratorProviders = Arrays.asList(new AlarmDecoratorProvider(), new AlarmSendMessageDecoratorProvider());
        SensorEventProvider randomSensorEventProvider = new RandomSensorEventProvider();
        Application application = new Application(randomSensorEventProvider, handlers, decoratorProviders);
        // начинаем цикл обработки событий
        application.run();
    }

    public void run() {
        JsonSmartHomeDeserialization deserialization = new JsonSmartHomeDeserialization("smart-home-1.js");
        SmartHome smartHome = deserialization.deserialize();
        new SensorEventProcessingCycle(smartHome, eventGetter, decorateHandlers()).start();
    }

    private List<SensorEventHandler> decorateHandlers() {
        List<SensorEventHandler> decoratorHandlers = new ArrayList<>();
        for (SensorEventHandler handler : handlers) {
            for (DecoratorProvider decoratorProvider : decoratorProviders) {
                handler = decoratorProvider.decorate(handler);
            }
            decoratorHandlers.add(handler);
        }
        return decoratorHandlers;
    }
}
