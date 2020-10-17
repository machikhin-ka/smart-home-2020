package ru.sbt.mipt.oop;

import static ru.sbt.mipt.oop.SensorEventType.DOOR_OPEN;
import static ru.sbt.mipt.oop.SensorEventType.DOOR_CLOSED;

public class DoorEventHandler implements SensorEventHandler {
	@Override
	public void handle(SmartHome smartHome, SensorEvent event) {
		if (event.getType() == DOOR_OPEN || event.getType() == DOOR_CLOSED) {
			for (Room room : smartHome.getRooms()) {
				for (Door door : room.getDoors()) {
					if (door.getId().equals(event.getObjectId())) {
						if (event.getType() == DOOR_OPEN) {
							openDoor(room, door);
						} else {
							closeDoor(smartHome, room, door);
						}
					}
				}
			}
		}
	}

	public void openDoor(Room room, Door door) {
		door.setOpen(true);
		System.out.println("Door " + door.getId() + " in room " + room.getName() + " was opened.");
	}


	public void closeDoor(SmartHome smartHome, Room room, Door door) {
		door.setOpen(false);
		System.out.println("Door " + door.getId() + " in room " + room.getName() + " was closed.");
	}
}
