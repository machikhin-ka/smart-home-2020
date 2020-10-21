package ru.sbt.mipt.oop;

import ru.sbt.mipt.oop.domain.SmartHome;
import ru.sbt.mipt.oop.events.SensorEvent;
import ru.sbt.mipt.oop.events.SensorEventProvider;
import ru.sbt.mipt.oop.handlers.SensorEventHandler;

import java.util.List;

public class SensorEventProcessingCycle {
	private final SensorEventProvider eventGetter;
	private final SmartHome smartHome;
	private final List<SensorEventHandler> handlers;

	public SensorEventProcessingCycle(SmartHome smartHome, SensorEventProvider eventGetter,
									  List<SensorEventHandler> handlers) {
		this.smartHome = smartHome;
		this.eventGetter = eventGetter;
		this.handlers = handlers;
	}

	public void start() {
		SensorEvent event = eventGetter.getNextSensorEvent();
		while (event != null) {
			System.out.println("Got event: " + event);
			for (SensorEventHandler handler : handlers) {
				handler.handle(smartHome, event);
			}
			event = eventGetter.getNextSensorEvent();
		}
	}
}
