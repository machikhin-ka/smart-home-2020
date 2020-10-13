package ru.sbt.mipt.oop.handlers.decorator.alarm;

import ru.sbt.mipt.oop.domain.Signaling;
import ru.sbt.mipt.oop.domain.SmartHome;
import ru.sbt.mipt.oop.domain.state.ActivatedState;
import ru.sbt.mipt.oop.domain.state.AlarmState;
import ru.sbt.mipt.oop.domain.state.State;
import ru.sbt.mipt.oop.events.SensorEvent;
import ru.sbt.mipt.oop.events.SensorEventType;
import ru.sbt.mipt.oop.handlers.SensorEventHandler;

public class AlarmSendMessageDecoratorHandler implements SensorEventHandler{
	private final SensorEventHandler handler;
	private final SendMessageDecoratorAction action;

	public AlarmSendMessageDecoratorHandler(SensorEventHandler handler, SendMessageDecoratorAction action) {
		this.handler = handler;
		this.action = action;
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
				if (state instanceof AlarmState || state instanceof ActivatedState) {
					action.sendMessage(event);
				}
			});
		}
		handler.handle(smartHome, event);
	}

	private boolean isAlarmType(SensorEvent event) {
		return event.getType() != SensorEventType.ALARM_ACTIVATE && event.getType() != SensorEventType.ALARM_DEACTIVATE;
	}
}
