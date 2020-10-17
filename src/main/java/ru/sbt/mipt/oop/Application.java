package ru.sbt.mipt.oop;

import java.util.ArrayList;
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
		List<SensorEventHandler> handlers = Arrays.asList(
				new DoorEventHandler(),
				new LightEventHandler(),
				new HallEventHandler(commandSender)
		);
		SensorEventProvider randomSensorEventGetter = new RandomSensorEventProvider();
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
