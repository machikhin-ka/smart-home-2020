package ru.sbt.mipt.oop.remote.control;

import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import rc.RemoteControl;
import ru.sbt.mipt.oop.AppConfiguration;
import ru.sbt.mipt.oop.RemoteControlConfiguration;
import ru.sbt.mipt.oop.domain.Door;
import ru.sbt.mipt.oop.domain.Light;
import ru.sbt.mipt.oop.domain.Room;
import ru.sbt.mipt.oop.domain.SmartHome;
import ru.sbt.mipt.oop.domain.signaling.Signaling;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(SpringExtension.class)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@ContextConfiguration(classes = {AppConfiguration.class, RemoteControlConfiguration.class})
class SmartHomeRemoteControlTest {
	@Autowired
	private SmartHome smartHome;
	@Autowired
	private RemoteControl remoteControl;

	@Test
	@Order(1)
	void onButtonPressed_turnOnAllLight_whenPressedButtonA() {
		//when
		remoteControl.onButtonPressed("A", "1");
		//then
		smartHome.execute(o -> {
			if (o instanceof Light) {
				Light light = (Light) o;
				assertTrue(light.isOn());
			}
		});
	}

	@Test
	@Order(2)
	void onButtonPressed_closeHallDoor_whenPressedButtonB() {
		//when
		remoteControl.onButtonPressed("B", "1");
		//then
		smartHome.execute(o -> {
			if (!(o instanceof Room)) {
				return;
			}
			Room room = (Room) o;
			if ("hall".equals(room.getName())) {
				room.execute(c -> {
					if (c instanceof Door) {
						Door door = (Door) c;
						assertFalse(door.isOpen());
					}
				});
			}
		});
	}

	@Test
	@Order(3)
	void onButtonPressed_turnOnHallLight_whenPressedButtonC() {
		//when
		remoteControl.onButtonPressed("C", "1");
		//then
		smartHome.execute(o -> {
			if (!(o instanceof Room)) {
				return;
			}
			Room room = (Room) o;
			if ("hall".equals(room.getName())) {
				room.execute(c -> {
					if (c instanceof Light) {
						Light light = (Light) c;
						assertTrue(light.isOn());
					}
				});
			}
		});
	}

	@Test
	@Order(4)
	void onButtonPressed_activateSignaling_whenPressedButtonD() {
		//when
		remoteControl.onButtonPressed("D", "1");
		//then
		smartHome.execute(o -> {
			if (o instanceof Signaling) {
				Signaling signaling = (Signaling) o;
				assertTrue(signaling.isActivated());
			}
		});
	}

	@Test
	@Order(5)
	void onButtonPressed_alarmSignaling_whenPressedButton1() {
		//when
		remoteControl.onButtonPressed("1", "1");
		//then
		smartHome.execute(o -> {
			if (o instanceof Signaling) {
				Signaling signaling = (Signaling) o;
				assertTrue(signaling.isAlarmed());
			}
		});
	}

	@Test
	@Order(6)
	void onButtonPressed_turnOffAllLight_whenPressedButton2() {
		//when
		remoteControl.onButtonPressed("2", "1");
		//then
		smartHome.execute(o -> {
			if (o instanceof Light) {
				Light light = (Light) o;
				assertFalse(light.isOn());
			}
		});
	}
}