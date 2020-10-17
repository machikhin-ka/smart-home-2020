package ru.sbt.mipt.oop;

import static ru.sbt.mipt.oop.SensorEventType.DOOR_CLOSED;

public class HallEventHandler implements SensorEventHandler {
	private final CommandSender commandSender;

	public HallEventHandler(CommandSender commandSender) {
		this.commandSender = commandSender;
	}

	@Override
	public void handle(SmartHome smartHome, SensorEvent event) {
		if (event.getType() == DOOR_CLOSED) {
			for (Room room : smartHome.getRooms()) {
				if (room.getName().equals("hall")) {
					for (Door door : room.getDoors()) {
						if (door.getId().equals(event.getObjectId())) {
							setOffAllLight(smartHome);
						}
					}
				}
			}
		}
	}

	public void setOffAllLight(SmartHome smartHome) {
		for (Room homeRoom : smartHome.getRooms()) {
			for (Light light : homeRoom.getLights()) {
				light.setOn(false);
				SensorCommand command = new SensorCommand(CommandType.LIGHT_OFF, light.getId());
				commandSender.sendCommand(command);
			}
		}
	}
}
