package ru.sbt.mipt.oop.handlers.decorator.alarm;

import ru.sbt.mipt.oop.domain.*;
import ru.sbt.mipt.oop.domain.state.ActivatedState;
import ru.sbt.mipt.oop.domain.state.AlarmState;
import ru.sbt.mipt.oop.domain.state.State;
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
				State state = signaling.getState();
				if (state instanceof AlarmState) {
					return;
				}
				handler.handle(smartHome, event);
				if (state instanceof ActivatedState) {
					state.alarm();
				}
			});
		}
	}

	private boolean isAlarmType(SensorEvent event) {
		return event.getType() != SensorEventType.ALARM_ACTIVATE && event.getType() != SensorEventType.ALARM_DEACTIVATE;
	}
}
