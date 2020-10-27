package ru.sbt.mipt.oop.adapters;

import com.coolcompany.smarthome.events.CCSensorEvent;
import com.coolcompany.smarthome.events.EventHandler;
import ru.sbt.mipt.oop.domain.SmartHome;
import ru.sbt.mipt.oop.events.SensorEvent;
import ru.sbt.mipt.oop.factories.SensorEventFactory;
import ru.sbt.mipt.oop.handlers.SensorEventHandler;

public class EventHandlerAdapter implements EventHandler {
	private final SensorEventHandler handler;
	private final SmartHome smartHome;
	private final SensorEventFactory factory;

	public EventHandlerAdapter(SensorEventHandler handler, SmartHome smartHome, SensorEventFactory factory) {
		this.handler = handler;
		this.smartHome = smartHome;
		this.factory = factory;
	}

	@Override
	public void handleEvent(CCSensorEvent event) {
		SensorEvent sensorEvent = factory.createEvent(event);
		handler.handle(smartHome, sensorEvent);
	}
}
