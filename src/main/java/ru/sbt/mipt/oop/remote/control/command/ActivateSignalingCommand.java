package ru.sbt.mipt.oop.remote.control.command;

import org.springframework.stereotype.Component;
import ru.sbt.mipt.oop.domain.SmartHome;
import ru.sbt.mipt.oop.domain.signaling.Signaling;

@Component
public class ActivateSignalingCommand implements RemoteControlCommand {
	private final SmartHome smartHome;

	public ActivateSignalingCommand(SmartHome smartHome) {
		this.smartHome = smartHome;
	}

	@Override
	public void execute() {
		smartHome.execute(o -> {
			if (o instanceof Signaling) {
				((Signaling) o).activateSignaling("default_code");
			}
		});
	}
}
