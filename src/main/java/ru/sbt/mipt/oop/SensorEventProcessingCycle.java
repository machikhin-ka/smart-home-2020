package ru.sbt.mipt.oop;

import static ru.sbt.mipt.oop.SensorEventType.*;
import static ru.sbt.mipt.oop.SensorEventType.DOOR_CLOSED;

public class SensorEventProcessingCycle {
	private final SensorEventGetter eventGetter;
	private final SmartHome smartHome;
	private SensorEventHandler eventHandler;

	public SensorEventProcessingCycle(SmartHome smartHome, SensorEventGetter eventGetter) {
		this.smartHome = smartHome;
		this.eventGetter = eventGetter;
	}

	public void toStartSensorEventProcessingCycle() {
		SensorEvent event = eventGetter.getNextSensorEvent();
		while (event != null) {
			System.out.println("Got event: " + event);
			if (event.getType() == LIGHT_ON || event.getType() == LIGHT_OFF) {
				eventHandler = new LightEventHandler(smartHome, event);
			} else if (event.getType() == DOOR_OPEN || event.getType() == DOOR_CLOSED) {
				eventHandler = new DoorEventHandler(smartHome, event);
			}
			eventHandler.toHandel();
			event = eventGetter.getNextSensorEvent();
		}
	}
}
