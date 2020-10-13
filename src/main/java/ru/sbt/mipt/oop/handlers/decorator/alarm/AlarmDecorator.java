package ru.sbt.mipt.oop.handlers.decorator.alarm;

import ru.sbt.mipt.oop.handlers.SensorEventHandler;
import ru.sbt.mipt.oop.handlers.SignalingEventHandler;
import ru.sbt.mipt.oop.handlers.decorator.Decorator;

public class AlarmDecorator implements Decorator {
	@Override
	public SensorEventHandler decorate(SensorEventHandler handler) {
		if (handler instanceof SignalingEventHandler) {
			return handler;
		}
		return new AlarmDecoratorHandler(handler);
	}
}
