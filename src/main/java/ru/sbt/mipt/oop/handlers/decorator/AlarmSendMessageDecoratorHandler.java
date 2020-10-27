package ru.sbt.mipt.oop.handlers.decorator;

import ru.sbt.mipt.oop.domain.signaling.Signaling;
import ru.sbt.mipt.oop.domain.SmartHome;
import ru.sbt.mipt.oop.events.SensorEvent;
import ru.sbt.mipt.oop.events.SensorEventType;
import ru.sbt.mipt.oop.handlers.SensorEventHandler;

public class AlarmSendMessageDecoratorHandler implements SensorEventHandler{
	private final SensorEventHandler handler;

	public AlarmSendMessageDecoratorHandler(SensorEventHandler handler) {
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
				if (signaling.isAlarmed() || signaling.isActivated()) {
					System.out.println("Sending sms...");
				}
			});
		}
		handler.handle(smartHome, event);
	}

	private boolean isAlarmType(SensorEvent event) {
		return event.getType() != SensorEventType.ALARM_ACTIVATE && event.getType() != SensorEventType.ALARM_DEACTIVATE;
	}
}
