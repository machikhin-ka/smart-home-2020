package ru.sbt.mipt.oop;

import static ru.sbt.mipt.oop.SensorEventType.DOOR_OPEN;

public class DoorEventHandler implements SensorEventHandler {
	private final SmartHome smartHome;
	private final SensorEvent event;

	public DoorEventHandler(SmartHome smartHome, SensorEvent event) {
		this.smartHome = smartHome;
		this.event = event;
	}

	@Override
	public void handle() {
		for (Room room : smartHome.getRooms()) {
			for (Door door : room.getDoors()) {
				if (door.getId().equals(event.getObjectId())) {
					if (event.getType() == DOOR_OPEN) {
						new DoorHandler(smartHome, room, door).openDoor();
					} else {
						new DoorHandler(smartHome, room, door).closeDoor();
					}
				}
			}
		}
	}
}
