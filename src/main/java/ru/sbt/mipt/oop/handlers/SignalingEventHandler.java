package ru.sbt.mipt.oop.handlers;

import org.springframework.stereotype.Component;
import ru.sbt.mipt.oop.domain.SmartHome;
import ru.sbt.mipt.oop.domain.signaling.Signaling;
import ru.sbt.mipt.oop.events.SensorEvent;

import static ru.sbt.mipt.oop.events.SensorEventType.ALARM_ACTIVATE;
import static ru.sbt.mipt.oop.events.SensorEventType.ALARM_DEACTIVATE;

@Component
public class SignalingEventHandler implements SensorEventHandler {
	@Override
	public void handle(SmartHome smartHome, SensorEvent event) {
		if (event.getType() == ALARM_ACTIVATE || event.getType() == ALARM_DEACTIVATE) {
			smartHome.execute(object -> {
				if (!(object instanceof Signaling)) {
					return;
				}
				Signaling signaling = ((Signaling) object);
				if (event.getType() == ALARM_ACTIVATE) {
					signaling.activateSignaling(event.getCode());
				} else {
					signaling.deactivateSignaling(event.getCode());
				}
			});
		}
	}
}
