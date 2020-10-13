package ru.sbt.mipt.oop.domain;

import org.junit.jupiter.api.Test;
import ru.sbt.mipt.oop.domain.Door;

import static org.junit.jupiter.api.Assertions.*;

class DoorTest {

	@Test
	void execute_closeDoor_whenActionChangeStateOfDoorToClosed() {
		//given
		Door door = new Door(false, "1");
		//when
		door.execute(object -> {
			if (object instanceof Door) {
				Door d = (Door) object;
				d.setOpen(true);
			}
		});
		//then
		assertTrue(door.isOpen());
	}
}