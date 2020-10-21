package ru.sbt.mipt.oop.handlers.decorator.alarm;

import ru.sbt.mipt.oop.handlers.SensorEventHandler;
import ru.sbt.mipt.oop.handlers.SignalingEventHandler;
import ru.sbt.mipt.oop.handlers.decorator.DecoratorProvider;

public class AlarmDecoratorProvider implements DecoratorProvider {
	@Override
	public SensorEventHandler decorate(SensorEventHandler handler) {
		if (handler instanceof SignalingEventHandler) {
			return handler;
		}
		return new AlarmDecoratorHandler(handler);
	}
}
