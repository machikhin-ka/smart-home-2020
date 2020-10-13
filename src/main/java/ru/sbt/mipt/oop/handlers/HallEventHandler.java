package ru.sbt.mipt.oop.handlers;

import ru.sbt.mipt.oop.command.CommandSender;
import ru.sbt.mipt.oop.command.CommandType;
import ru.sbt.mipt.oop.command.SensorCommand;
import ru.sbt.mipt.oop.domain.Door;
import ru.sbt.mipt.oop.domain.Light;
import ru.sbt.mipt.oop.domain.Room;
import ru.sbt.mipt.oop.domain.SmartHome;
import ru.sbt.mipt.oop.events.SensorEvent;

import static ru.sbt.mipt.oop.events.SensorEventType.DOOR_CLOSED;

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
			commandSender.sendCommand(command);
		});
	}
}
