package ru.sbt.mipt.oop.remote.control.command;

import org.springframework.stereotype.Component;
import ru.sbt.mipt.oop.domain.Door;
import ru.sbt.mipt.oop.domain.Room;
import ru.sbt.mipt.oop.domain.SmartHome;

@Component
public class CloseHallDoorCommand implements RemoteControlCommand {
	private final SmartHome smartHome;

	public CloseHallDoorCommand(SmartHome smartHome) {
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
					if (!(c instanceof Door)) {
						return;
					}
					Door door = (Door) c;
					closeDoor(door);
				});
			}
		});
	}

	private void closeDoor(Door door) {
		door.setOpen(false);
		System.out.println("Hall-door was closed.");
	}
}
