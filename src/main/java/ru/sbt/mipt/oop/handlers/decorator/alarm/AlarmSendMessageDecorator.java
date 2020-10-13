package ru.sbt.mipt.oop.handlers.decorator.alarm;

import ru.sbt.mipt.oop.handlers.SensorEventHandler;
import ru.sbt.mipt.oop.handlers.SignalingEventHandler;
import ru.sbt.mipt.oop.handlers.decorator.Decorator;

public class AlarmSendMessageDecorator implements Decorator {
	private final SendMessageDecoratorAction action = new SendMessageDecoratorAction();

	@Override
	public SensorEventHandler decorate(SensorEventHandler handler) {
		if (handler instanceof SignalingEventHandler) {
			return handler;
		}
		return new AlarmSendMessageDecoratorHandler(handler, action);
	}
}
