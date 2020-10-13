package ru.sbt.mipt.oop;

import ru.sbt.mipt.oop.domain.SmartHome;
import ru.sbt.mipt.oop.events.SensorEvent;
import ru.sbt.mipt.oop.events.SensorEventProvider;
import ru.sbt.mipt.oop.handlers.SensorEventHandler;
import ru.sbt.mipt.oop.handlers.decorator.Decorator;

import java.util.ArrayList;
import java.util.List;

public class SensorEventProcessingCycle {
	private final SensorEventProvider eventGetter;
	private final SmartHome smartHome;
	private final List<SensorEventHandler> handlers;
	private final List<Decorator> decorators;

	public SensorEventProcessingCycle(SmartHome smartHome, SensorEventProvider eventGetter,
									  List<SensorEventHandler> handlers, List<Decorator> decorators) {
		this.smartHome = smartHome;
		this.eventGetter = eventGetter;
		this.handlers = handlers;
		this.decorators = decorators;
	}

	public void start() {
		SensorEvent event = eventGetter.getNextSensorEvent();
		List<SensorEventHandler> decorateHandlers = decorateHandlers();
		while (event != null) {
			System.out.println("Got event: " + event);
			for (SensorEventHandler handler : decorateHandlers) {
				handler.handle(smartHome, event);
			}
			event = eventGetter.getNextSensorEvent();
		}
	}

	private List<SensorEventHandler> decorateHandlers() {
		List<SensorEventHandler> decoratorHandlers = new ArrayList<>();
		for (SensorEventHandler handler : handlers) {
			for (Decorator decorator : decorators) {
				handler = decorator.decorate(handler);
			}
			decoratorHandlers.add(handler);
		}
		return decoratorHandlers;
	}
}
