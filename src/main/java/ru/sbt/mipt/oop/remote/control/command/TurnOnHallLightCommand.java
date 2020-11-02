package ru.sbt.mipt.oop.remote.control.command;

import org.springframework.stereotype.Component;
import ru.sbt.mipt.oop.domain.Light;
import ru.sbt.mipt.oop.domain.Room;
import ru.sbt.mipt.oop.domain.SmartHome;

@Component
public class TurnOnHallLightCommand implements RemoteControlCommand {
	private final SmartHome smartHome;

	public TurnOnHallLightCommand(SmartHome smartHome) {
		this.smartHome = smartHome;
	}

	@Override
	public void execute() {
		smartHome.execute(o -> {
			if (!(o instanceof Room)) {
				return;
			}
			Room room = (Room) o;
			if ("hall".equals(room.getName())) {
				room.execute(c -> {
					if (!(c instanceof Light)) {
						return;
					}
					Light light = (Light) c;
					turnOnLight(light);
				});
			}
		});
	}

	private void turnOnLight(Light light) {
		light.setOn(true);
		System.out.println("Hall-light was turned on.");
	}
}
