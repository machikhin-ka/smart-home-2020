package ru.sbt.mipt.oop.factories;

import com.coolcompany.smarthome.events.CCSensorEvent;
import org.springframework.stereotype.Component;
import ru.sbt.mipt.oop.events.SensorEvent;
import ru.sbt.mipt.oop.events.SensorEventType;

import java.util.Map;

@Component
public class SensorEventFactory {
	private final Map<String, SensorEventType> eventTypes;

	public SensorEventFactory(Map<String, SensorEventType> eventTypes) {
		this.eventTypes = eventTypes;
	}

	public SensorEvent createEvent(CCSensorEvent event) {
		return new SensorEvent(eventTypes.get(event.getEventType()), event.getObjectId());
	}
}
