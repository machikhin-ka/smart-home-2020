package ru.sbt.mipt.oop.factories;

import com.coolcompany.smarthome.events.CCSensorEvent;
import org.springframework.stereotype.Component;
import ru.sbt.mipt.oop.events.SensorEvent;
import ru.sbt.mipt.oop.events.SensorEventType;

import java.util.Map;

@Component
public class SensorEventFactory {
	private final Map<String, SensorEventType> eventTypes;

	{
		eventTypes = Map.of("DoorIsOpen", SensorEventType.DOOR_OPEN,
				"DoorIsClosed", SensorEventType.DOOR_CLOSED,
				"LightIsOn", SensorEventType.LIGHT_ON,
				"LightIsOff", SensorEventType.LIGHT_OFF,
				"DoorIsLocked", SensorEventType.ALARM_ACTIVATE,
				"DoorIsUnlocked", SensorEventType.ALARM_DEACTIVATE);
	}

	public SensorEvent createEvent(CCSensorEvent event) {
		return new SensorEvent(eventTypes.get(event.getEventType()), event.getObjectId());
	}
}
