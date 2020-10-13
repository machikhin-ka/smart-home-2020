package ru.sbt.mipt.oop.handlers.decorator;

import ru.sbt.mipt.oop.handlers.SensorEventHandler;

public interface Decorator {
	SensorEventHandler decorate(SensorEventHandler handler);
}
