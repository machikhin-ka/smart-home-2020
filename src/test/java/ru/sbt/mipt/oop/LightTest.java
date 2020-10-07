package ru.sbt.mipt.oop;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class LightTest {

	@Test
	void execute_returnTrue_whenActionChangeStateOfLightCorrectly() {
		//given
		Light light = new Light("1", false);
		//when
		light.execute(object -> {
			if (object instanceof Light) {
				Light l = (Light) object;
				l.setOn(true);
			}
		});
		//then
		assertTrue(light.isOn());
	}
}