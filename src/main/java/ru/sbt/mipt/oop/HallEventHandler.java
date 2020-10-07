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
			smartHome.execute(object -> {
				if (!(object instanceof Room)) {
					return;
				}
				Room room = (Room) object;
				if (room.getName().equals("hall")) {
					room.execute(component -> {
						if (!(component instanceof Door)) {
							return;
						}
						Door door = (Door) component;
						if (door.getId().equals(event.getObjectId())) {
							setOffAllLight(smartHome);
						}
					});
				}
			});
		}
	}

	private void setOffAllLight(SmartHome smartHome) {
		smartHome.execute(object -> {
			if (!(object instanceof Light)) {
				return;
			}
			Light light = (Light) object;
			light.setOn(false);
			SensorCommand command = new SensorCommand(CommandType.LIGHT_OFF, light.getId());
			new CommandSender().sendCommand(command);
		});
	}
}
