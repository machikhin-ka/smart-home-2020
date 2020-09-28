package ru.sbt.mipt.oop;

import static ru.sbt.mipt.oop.SensorEventType.DOOR_OPEN;

public class DoorEventHandler implements SensorEventHandler {
	private final SmartHome smartHome;
	private final SensorEvent event;
	private final DoorSetter doorSetter;

	public DoorEventHandler(SmartHome smartHome, SensorEvent event) {
		this.smartHome = smartHome;
		this.event = event;
		this.doorSetter = new DoorSetter(smartHome);
	}

	@Override
	public void toHandel() {
		for (Room room : smartHome.getRooms()) {
			for (Door door : room.getDoors()) {
				if (door.getId().equals(event.getObjectId())) {
					if (event.getType() == DOOR_OPEN) {
						doorSetter.openDoor(room, door);
					} else {
						doorSetter.closeDoor(room, door);
					}
				}
			}
		}
	}
}
