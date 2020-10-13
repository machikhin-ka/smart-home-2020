package ru.sbt.mipt.oop.domain;

import org.junit.jupiter.api.Test;
import ru.sbt.mipt.oop.domain.Light;

import static org.junit.jupiter.api.Assertions.*;

class LightTest {

	@Test
	void execute_turnOnLight_whenActionChangeStateOfLightToOn() {
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