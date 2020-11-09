package ru.sbt.mipt.oop.handlers.decorator;

import ru.sbt.mipt.oop.domain.*;
import ru.sbt.mipt.oop.domain.signaling.Signaling;
import ru.sbt.mipt.oop.events.SensorEvent;
import ru.sbt.mipt.oop.events.SensorEventType;
import ru.sbt.mipt.oop.handlers.SensorEventHandler;

public class AlarmDecoratorHandler implements SensorEventHandler {
	private final SensorEventHandler handler;

	public AlarmDecoratorHandler(SensorEventHandler handler) {
		this.handler = handler;
	}

	@Override
	public void handle(SmartHome smartHome, SensorEvent event) {
		if (isAlarmType(event)) {
			smartHome.execute(object -> {
				if (!(object instanceof Signaling)) {
					return;
				}
				Signaling signaling = (Signaling) object;
				if (signaling.isAlarmed()) {
					return;
				}
				handler.handle(smartHome, event);
				if (signaling.isActivated()) {
					signaling.alarmSignaling();
				}
			});
		}
	}

	private boolean isAlarmType(SensorEvent event) {
		return event.getType() != SensorEventType.ALARM_ACTIVATE && event.getType() != SensorEventType.ALARM_DEACTIVATE;
	}
}
