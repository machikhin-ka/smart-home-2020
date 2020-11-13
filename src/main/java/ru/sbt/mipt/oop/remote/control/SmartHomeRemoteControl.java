package ru.sbt.mipt.oop.remote.control;

import rc.RemoteControl;
import ru.sbt.mipt.oop.remote.control.command.RemoteControlCommand;

import java.util.*;

public class SmartHomeRemoteControl implements RemoteControl {
	private final String id;
	private final Map<String, RemoteControlCommand> buttons;

	public SmartHomeRemoteControl(String id, Map<String, RemoteControlCommand> buttons) {
		this.id = id;
		this.buttons = buttons;
	}

	@Override
	public String getId() {
		return id;
	}

	@Override
	public void onButtonPressed(String buttonCode, String rcId) {
		if (Objects.equals(id, rcId) && buttons.containsKey(buttonCode)) {
			buttons.get(buttonCode).execute();
		}
	}
}
