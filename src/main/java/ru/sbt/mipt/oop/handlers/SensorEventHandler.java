package ru.sbt.mipt.oop.handlers;

import ru.sbt.mipt.oop.domain.SmartHome;
import ru.sbt.mipt.oop.events.SensorEvent;

public interface SensorEventHandler {
	void handle(SmartHome smartHome, SensorEvent event);
}
