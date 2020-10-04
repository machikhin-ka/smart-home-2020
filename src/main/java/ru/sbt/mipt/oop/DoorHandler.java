package ru.sbt.mipt.oop;

public class DoorHandler {
	private final SmartHome smartHome;
	private final Room room;
	private final Door door;

	public DoorHandler(SmartHome smartHome, Room room, Door door) {
		this.smartHome = smartHome;
		this.room = room;
		this.door = door;
	}

	public void openDoor() {
		door.setOpen(true);
		System.out.println("Door " + door.getId() + " in room " + room.getName() + " was opened.");
	}


	public void closeDoor() {
		door.setOpen(false);
		System.out.println("Door " + door.getId() + " in room " + room.getName() + " was closed.");
		// если мы получили событие о закрытие двери в холле - это значит, что была закрыта входная дверь.
		// в этом случае мы хотим автоматически выключить свет во всем доме (это же умный дом!)
		if (room.getName().equals("hall")) {
			new HomeLightHandler(smartHome).setOffAllLight();
		}
	}
}
