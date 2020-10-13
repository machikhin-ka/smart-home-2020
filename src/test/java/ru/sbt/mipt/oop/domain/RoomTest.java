package ru.sbt.mipt.oop.domain;

import org.junit.jupiter.api.Test;
import ru.sbt.mipt.oop.domain.Door;
import ru.sbt.mipt.oop.domain.Light;
import ru.sbt.mipt.oop.domain.Room;

import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.*;

class RoomTest {

	@Test
	void execute_passAllComponentOfRoom_whenIterateOverAllComponent() {
		//given
		Door door = new Door(true, "1");
		Light light = new Light("1", true);
		Room room = new Room(Arrays.asList(light),
				Arrays.asList(door),
				"room");
		//when
		room.execute(object -> {
			if (object instanceof Door) {
				Door d = (Door) object;
				d.setOpen(false);
			}
			if (object instanceof Light) {
				Light l = (Light) object;
				l.setOn(false);
			}
		});
		//then
		assertAll(
				() -> assertFalse(door.isOpen()),
				() -> assertFalse(light.isOn())
		);
	}
}