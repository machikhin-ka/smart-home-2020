package ru.sbt.mipt.oop.handlers.decorator.alarm;

import org.junit.jupiter.api.Test;
import ru.sbt.mipt.oop.handlers.DoorEventHandler;
import ru.sbt.mipt.oop.handlers.SensorEventHandler;
import ru.sbt.mipt.oop.handlers.SignalingEventHandler;
import ru.sbt.mipt.oop.handlers.decorator.Decorator;

import static org.junit.jupiter.api.Assertions.*;

class AlarmSendMessageDecoratorTest {
	private final Decorator decorator = new AlarmSendMessageDecorator();

	@Test
	void decorate_decorateHandler_whenHandlerIsNotSignalingEventHandler() {
		//given
		SensorEventHandler handler = new DoorEventHandler();
		//when
		SensorEventHandler decorateHandler = decorator.decorate(handler);
		//then
		assertNotEquals(handler, decorateHandler);
	}

	@Test
	void decorate_decorateHandler_whenHandlerIsSignalingEventHandler() {
		//given
		SensorEventHandler handler = new SignalingEventHandler();
		//when
		SensorEventHandler decorateHandler = decorator.decorate(handler);
		//then
		assertEquals(handler, decorateHandler);
	}
}