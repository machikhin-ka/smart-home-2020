package ru.sbt.mipt.oop.handlers.decorator.alarm;

import ru.sbt.mipt.oop.events.SensorEvent;

public class SendMessageDecoratorAction {
	private SensorEvent currentEvent;

	public void sendMessage(SensorEvent event) {
		if (!(event != null && event.equals(currentEvent))) {
			System.out.println("Sending sms...");
			currentEvent = event;
		}
	}
}
