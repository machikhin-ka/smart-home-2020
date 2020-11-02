package ru.sbt.mipt.oop.remote.control.command;

import org.springframework.stereotype.Component;
import ru.sbt.mipt.oop.domain.SmartHome;
import ru.sbt.mipt.oop.domain.signaling.Signaling;

@Component
public class AlarmSignalingCommand implements RemoteControlCommand {
	private final SmartHome smartHome;

	public AlarmSignalingCommand(SmartHome smartHome) {
		this.smartHome = smartHome;
	}

	@Override
	public void execute() {
		smartHome.execute(o -> {
			if (o instanceof Signaling) {
				((Signaling) o).alarmSignaling();
			}
		});
	}
}
