package ru.sbt.mipt.oop.command;

import org.springframework.stereotype.Component;

@Component
public class CommandSender {
	public void sendCommand(SensorCommand command) {
		System.out.println("Pretent we're sending command " + command);
	}
}
