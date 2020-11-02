package ru.sbt.mipt.oop.remote.control;

import rc.RemoteControl;
import ru.sbt.mipt.oop.remote.control.command.RemoteControlCommand;

import java.util.*;

public class SmartHomeRemoteControl implements RemoteControl {
	private final String id;
	private final Map<String, RemoteControlCommand> buttons = new HashMap<>();
	private final Set<String> codes = Set.of("A", "B", "C", "D", "1", "2", "3", "4");;

	public SmartHomeRemoteControl(String id) {
		this.id = id;
	}

	public void setButton(String buttonCode, RemoteControlCommand command) {
		if (codes.contains(buttonCode)) {
			buttons.put(buttonCode, command);
		}
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
