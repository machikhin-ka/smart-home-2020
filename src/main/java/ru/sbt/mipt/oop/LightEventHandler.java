package ru.sbt.mipt.oop;

import static ru.sbt.mipt.oop.SensorEventType.LIGHT_ON;

public class LightEventHandler implements SensorEventHandler {
	private final SmartHome smartHome;
	private final SensorEvent event;
	private final LightSetter lightSetter = new LightSetter();

	public LightEventHandler(SmartHome smartHome, SensorEvent event) {
		this.smartHome = smartHome;
		this.event = event;
	}

	@Override
	public void toHandel() {
		for (Room room : smartHome.getRooms()) {
			for (Light light : room.getLights()) {
				if (light.getId().equals(event.getObjectId())) {
					if (event.getType() == LIGHT_ON) {
						lightSetter.setOnLight(room, light);
					} else {
						lightSetter.setOffLight(room, light);
					}
				}
			}
		}
	}
}
