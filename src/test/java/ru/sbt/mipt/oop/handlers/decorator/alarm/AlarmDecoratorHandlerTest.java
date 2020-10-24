package ru.sbt.mipt.oop.handlers.decorator.alarm;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.sbt.mipt.oop.domain.Door;
import ru.sbt.mipt.oop.domain.Room;
import ru.sbt.mipt.oop.domain.SmartHome;
import ru.sbt.mipt.oop.domain.signaling.Signaling;
import ru.sbt.mipt.oop.events.SensorEvent;
import ru.sbt.mipt.oop.events.SensorEventType;
import ru.sbt.mipt.oop.handlers.DoorEventHandler;
import ru.sbt.mipt.oop.handlers.SensorEventHandler;

import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.*;

class AlarmDecoratorHandlerTest {
	private static final SensorEventHandler handler = new AlarmDecoratorHandler(new DoorEventHandler());
	private SmartHome smartHome;
	private final Door door = new Door(false, "1");

	@BeforeEach
	void beforeEach() {
		smartHome = new SmartHome();
		Room kitchen = new Room(null,
				Arrays.asList(door),
				"kitchen");
		smartHome.addRoom(kitchen);
	}

	@Test
	void handle_AlarmSignaling_whenEventTypeIsDoorOpenAndSignalingStateIsActivated() {
		//given
		smartHome.execute(object -> {
			if (!(object instanceof Signaling)) {
				return;
			}
			Signaling signaling = ((Signaling) object);
			signaling.activateSignaling("0");
		});
		SensorEvent event = new SensorEvent(SensorEventType.DOOR_OPEN, "1");
		//when
		handler.handle(smartHome, event);
		//then
		assertAll(
				() -> assertTrue(door.isOpen()),
				() -> smartHome.execute(object -> {
					if (!(object instanceof Signaling)) {
						return;
					}
					Signaling signaling = ((Signaling) object);
					assertTrue(signaling.isAlarmed());
				})
		);
	}

	@Test
	void handle_doNothing_whenEventTypeIsDoorOpenAndSignalingStateIsDeactivated() {
		//given
		smartHome.execute(object -> {
			if (!(object instanceof Signaling)) {
				return;
			}
			Signaling signaling = ((Signaling) object);
			signaling.activateSignaling("0");
			signaling.deactivateSignaling("0");
		});
		SensorEvent event = new SensorEvent(SensorEventType.DOOR_OPEN, "1");
		//when
		handler.handle(smartHome, event);
		//then
		assertAll(
				() -> assertTrue(door.isOpen()),
				() -> smartHome.execute(object -> {
					if (!(object instanceof Signaling)) {
						return;
					}
					Signaling signaling = ((Signaling) object);
					assertTrue(signaling.isDeactivated());
				})
		);
	}

	@Test
	void handle_doNothing_whenSignalingStateIsAlarmed() {
		//given
		smartHome.execute(object -> {
			if (!(object instanceof Signaling)) {
				return;
			}
			Signaling signaling = ((Signaling) object);
			signaling.activateSignaling("0");
			signaling.deactivateSignaling("1");
		});
		SensorEvent event = new SensorEvent(SensorEventType.DOOR_OPEN, "1");
		//when
		handler.handle(smartHome, event);
		//then
		assertFalse(door.isOpen());
	}
}