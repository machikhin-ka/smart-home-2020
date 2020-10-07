package ru.sbt.mipt.oop;

import org.junit.jupiter.api.Test;

import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.*;

class SmartHomeTest {
	@Test
	void execute_passAllComponentOfSmartHome_whenIterateOverAllComponent() {
		//given
		Door firstDoor = new Door(true, "1");
		Door secondDoor = new Door(true, "2");
		Light firstLight = new Light("1", true);
		Light secondLight = new Light("2", true);
		Room firstRoom = new Room(Arrays.asList(firstLight),
				Arrays.asList(firstDoor),
				"firstRoom");
		Room secondRoom = new Room(Arrays.asList(secondLight),
				Arrays.asList(secondDoor),
				"secondRoom");
		SmartHome smartHome = new SmartHome(Arrays.asList(firstRoom, secondRoom));
		//when
		smartHome.execute(object -> {
			if (object instanceof Light) {
				Light light = (Light) object;
				light.setOn(false);
			}
			if (object instanceof Door) {
				Door door = (Door) object;
				door.setOpen(false);
			}
		});
		//then
		assertAll(
				() -> assertFalse(firstDoor.isOpen()),
				() -> assertFalse(secondDoor.isOpen()),
				() -> assertFalse(firstLight.isOn()),
				() -> assertFalse(secondLight.isOn())
		);
	}
}