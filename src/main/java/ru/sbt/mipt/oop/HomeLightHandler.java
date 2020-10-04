package ru.sbt.mipt.oop;

public class HomeLightHandler {
	private final SmartHome smartHome;

	public HomeLightHandler(SmartHome smartHome) {
		this.smartHome = smartHome;
	}

	public void setOffAllLight() {
		for (Room homeRoom : smartHome.getRooms()) {
			for (Light light : homeRoom.getLights()) {
				light.setOn(false);
				SensorCommand command = new SensorCommand(CommandType.LIGHT_OFF, light.getId());
				new CommandSender().sendCommand(command);
			}
		}
	}
}
