package ru.sbt.mipt.oop.handlers;

import ru.sbt.mipt.oop.domain.Door;
import ru.sbt.mipt.oop.domain.SmartHome;
import ru.sbt.mipt.oop.events.SensorEvent;

import static ru.sbt.mipt.oop.events.SensorEventType.DOOR_CLOSED;
import static ru.sbt.mipt.oop.events.SensorEventType.DOOR_OPEN;

public class DoorEventHandler implements SensorEventHandler {
	@Override
	public void handle(SmartHome smartHome, SensorEvent event) {
		if (event.getType() == DOOR_OPEN || event.getType() == DOOR_CLOSED) {
			smartHome.execute(object -> {
				if (!(object instanceof Door)) {
					return;
				}
				Door door = (Door) object;
				if (door.getId().equals(event.getObjectId())) {
					if (event.getType() == DOOR_OPEN) {
						this.openDoor(door);
					} else {
						this.closeDoor(door);
					}
				}
			});
		}
	}

	private void openDoor(Door door) {
		door.setOpen(true);
		System.out.println("Door " + door.getId() + " was opened.");
	}


	private void closeDoor(Door door) {
		door.setOpen(false);
		System.out.println("Door " + door.getId() + " was closed.");
	}
}
