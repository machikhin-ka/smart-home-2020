package ru.sbt.mipt.oop;

public class LightSetter {
	public void setOnLight(Room room, Light light) {
		light.setOn(true);
		System.out.println("Light " + light.getId() + " in room " + room.getName() + " was turned on.");
	}

	public void setOffLight(Room room, Light light) {
		light.setOn(false);
		System.out.println("Light " + light.getId() + " in room " + room.getName() + " was turned off.");
	}
}
