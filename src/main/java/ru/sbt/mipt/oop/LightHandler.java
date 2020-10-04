package ru.sbt.mipt.oop;

public class LightHandler {
	private final Room room;
	private final Light light;

	public LightHandler(Room room, Light light) {
		this.room = room;
		this.light = light;
	}

	public void setOnLight() {
		light.setOn(true);
		System.out.println("Light " + light.getId() + " in room " + room.getName() + " was turned on.");
	}

	public void setOffLight() {
		light.setOn(false);
		System.out.println("Light " + light.getId() + " in room " + room.getName() + " was turned off.");
	}
}
