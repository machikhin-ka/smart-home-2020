package ru.sbt.mipt.oop.handlers;

import ru.sbt.mipt.oop.domain.Signaling;
import ru.sbt.mipt.oop.domain.SmartHome;
import ru.sbt.mipt.oop.domain.state.SignalingState;
import ru.sbt.mipt.oop.events.SensorEvent;

import static ru.sbt.mipt.oop.events.SensorEventType.ALARM_ACTIVATE;
import static ru.sbt.mipt.oop.events.SensorEventType.ALARM_DEACTIVATE;

public class SignalingEventHandler implements SensorEventHandler {
	@Override
	public void handle(SmartHome smartHome, SensorEvent event) {
		if (event.getType() == ALARM_ACTIVATE || event.getType() == ALARM_DEACTIVATE) {
			smartHome.execute(object -> {
				if (!(object instanceof Signaling)) {
					return;
				}
				SignalingState signalingState = ((Signaling) object).getState();
				if (event.getType() == ALARM_ACTIVATE) {
					signalingState.activate(event.getCode());
				} else {
					signalingState.deactivate(event.getCode());
				}
			});
		}
	}
}