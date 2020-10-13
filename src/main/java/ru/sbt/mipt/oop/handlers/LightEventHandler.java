package ru.sbt.mipt.oop.handlers;

import ru.sbt.mipt.oop.domain.Light;
import ru.sbt.mipt.oop.domain.SmartHome;
import ru.sbt.mipt.oop.events.SensorEvent;

import static ru.sbt.mipt.oop.events.SensorEventType.LIGHT_OFF;
import static ru.sbt.mipt.oop.events.SensorEventType.LIGHT_ON;

public class LightEventHandler implements SensorEventHandler {
	@Override
	public void handle(SmartHome smartHome, SensorEvent event) {
		if (event.getType() == LIGHT_ON || event.getType() == LIGHT_OFF) {
			smartHome.execute(object -> {
				if (!(object instanceof Light)) {
					return;
				}
				Light light = (Light) object;
				if (light.getId().equals(event.getObjectId())) {
					if (event.getType() == LIGHT_ON) {
						this.setOnLight(light);
					} else {
						this.setOffLight(light);
					}
				}
			});
		}
	}

	private void setOnLight(Light light) {
		light.setOn(true);
		System.out.println("Light " + light.getId() + " was turned on.");
	}

	private void setOffLight(Light light) {
		light.setOn(false);
		System.out.println("Light " + light.getId() + " was turned off.");
	}
}
