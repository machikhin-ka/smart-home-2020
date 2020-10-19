package ru.sbt.mipt.oop.events;

import java.util.concurrent.ThreadLocalRandom;

public class RandomSensorEventProvider implements SensorEventProvider {
	public SensorEvent getNextSensorEvent() {
		// pretend like we're getting the events from physical world, but here we're going to just generate some random events
		if (Math.random() < 0.05) return null; // null means end of event stream
		SensorEventType sensorEventType = SensorEventType.values()[(int) (6 * Math.random())];
		String objectId = "" + ((int) (10 * Math.random()));
		SensorEvent sensorEvent = new SensorEvent(sensorEventType, objectId);
		sensorEvent.setCode(Integer.toString(ThreadLocalRandom.current().nextInt(3)));
		return sensorEvent;
	}
}
