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
							this.closeDoor(smartHome, door);
						}
					});
				}
			});
		}
	}

	private void closeDoor(SmartHome smartHome, Door door) {
		door.setOpen(false);
		System.out.println("Door " + door.getId() + " was closed.");
		// если мы получили событие о закрытие двери в холле - это значит, что была закрыта входная дверь.
		// в этом случае мы хотим автоматически выключить свет во всем доме (это же умный дом!)
		setOffAllLight(smartHome);
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
