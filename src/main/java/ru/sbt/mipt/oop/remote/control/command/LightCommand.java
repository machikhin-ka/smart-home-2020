package ru.sbt.mipt.oop.remote.control.command;

import ru.sbt.mipt.oop.domain.Light;
import ru.sbt.mipt.oop.domain.SmartHome;

public class LightCommand implements RemoteControlCommand {
	private final SmartHome smartHome;
	private final boolean on;

	public LightCommand(SmartHome smartHome, boolean on) {
		this.smartHome = smartHome;
		this.on = on;
	}

	@Override
	public void execute() {
		smartHome.execute(o -> {
			if (!(o instanceof Light)) {
				return;
			}
			Light light = (Light) o;
			if (on) {
				setOnLight(light);
			} else {
				setOffLight(light);
			}
		});
	}

	private void setOnLight(Light light) {
		light.setOn(true);
		System.out.println("Light " + light.getId() + " was turned on.");
	}

	private void setOffLight(Light light) {
		light.setOn(false);
		System.out.println("Light " + light.getId() + " was turned off.");
	}
}
