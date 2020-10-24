package ru.sbt.mipt.oop;

import ru.sbt.mipt.oop.command.CommandSender;
import ru.sbt.mipt.oop.deserialization.JsonSmartHomeDeserialization;
import ru.sbt.mipt.oop.domain.SmartHome;
import ru.sbt.mipt.oop.events.RandomSensorEventProvider;
import ru.sbt.mipt.oop.events.SensorEventProvider;
import ru.sbt.mipt.oop.handlers.*;
import ru.sbt.mipt.oop.handlers.decorator.alarm.AlarmDecoratorHandler;
import ru.sbt.mipt.oop.handlers.decorator.alarm.AlarmSendMessageDecoratorHandler;
import ru.sbt.mipt.oop.handlers.decorator.alarm.SendMessageDecoratorAction;

import java.util.Arrays;
import java.util.List;

public class Application {
    private final SensorEventProvider eventGetter;
    private final List<SensorEventHandler> handlers;

    public Application(SensorEventProvider eventGetter, List<SensorEventHandler> handlers) {
        this.eventGetter = eventGetter;
        this.handlers = handlers;
    }

    public static void main(String... args) {
        CommandSender commandSender = new CommandSender();
        SendMessageDecoratorAction smsAction = new SendMessageDecoratorAction();
        List<SensorEventHandler> handlers
                = Arrays.asList(new AlarmSendMessageDecoratorHandler(new AlarmDecoratorHandler(
                        new DoorEventHandler()), smsAction),
                new AlarmSendMessageDecoratorHandler(new AlarmDecoratorHandler(new LightEventHandler()), smsAction),
                new AlarmSendMessageDecoratorHandler(new AlarmDecoratorHandler(
                        new HallEventHandler(commandSender)), smsAction),
                new SignalingEventHandler());
        SensorEventProvider randomSensorEventProvider = new RandomSensorEventProvider();
        Application application = new Application(randomSensorEventProvider, handlers);
        // начинаем цикл обработки событий
        application.run();
    }

    public void run() {
        JsonSmartHomeDeserialization deserialization = new JsonSmartHomeDeserialization("smart-home-1.js");
        SmartHome smartHome = deserialization.deserialize();
        new SensorEventProcessingCycle(smartHome, eventGetter, handlers).start();
    }
}
