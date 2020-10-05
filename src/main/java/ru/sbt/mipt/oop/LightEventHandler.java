package ru.sbt.mipt.oop;

import static ru.sbt.mipt.oop.SensorEventType.LIGHT_OFF;
import static ru.sbt.mipt.oop.SensorEventType.LIGHT_ON;

public class LightEventHandler implements SensorEventHandler {
	@Override
	public void handle(SmartHome smartHome, SensorEvent event) {
		if (event.getType() == LIGHT_ON || event.getType() == LIGHT_OFF) {
			for (Room room : smartHome.getRooms()) {
				for (Light light : room.getLights()) {
					if (light.getId().equals(event.getObjectId())) {
						if (event.getType() == LIGHT_ON) {
							setOnLight(room, light);
						} else {
							setOffLight(room, light);
						}
					}
				}
			}
		}
	}

	public void setOnLight(Room room, Light light) {
		light.setOn(true);
		System.out.println("Light " + light.getId() + " in room " + room.getName() + " was turned on.");
	}

	public void setOffLight(Room room, Light light) {
		light.setOn(false);
		System.out.println("Light " + light.getId() + " in room " + room.getName() + " was turned off.");
	}
}
