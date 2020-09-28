package ru.sbt.mipt.oop;

public class DoorSetter {
	private final CommandSender commandSender = new CommandSender();
	private final SmartHome smartHome;

	public DoorSetter(SmartHome smartHome) {
		this.smartHome = smartHome;
	}

	public void openDoor(Room room, Door door) {
		door.setOpen(true);
		System.out.println("Door " + door.getId() + " in room " + room.getName() + " was opened.");
	}


	public void closeDoor(Room room, Door door) {
		door.setOpen(false);
		System.out.println("Door " + door.getId() + " in room " + room.getName() + " was closed.");
		// если мы получили событие о закрытие двери в холле - это значит, что была закрыта входная дверь.
		// в этом случае мы хотим автоматически выключить свет во всем доме (это же умный дом!)
		if (room.getName().equals("hall")) {
			setOffLight();
		}
	}

	private void setOffLight() {
		for (Room homeRoom : smartHome.getRooms()) {
			for (Light light : homeRoom.getLights()) {
				light.setOn(false);
				SensorCommand command = new SensorCommand(CommandType.LIGHT_OFF, light.getId());
				commandSender.sendCommand(command);
			}
		}
	}
}
