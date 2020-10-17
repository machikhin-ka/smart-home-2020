package ru.sbt.mipt.oop.handlers.decorator.alarm;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.sbt.mipt.oop.domain.Door;
import ru.sbt.mipt.oop.domain.Room;
import ru.sbt.mipt.oop.domain.Signaling;
import ru.sbt.mipt.oop.domain.SmartHome;
import ru.sbt.mipt.oop.domain.state.AlarmSignalingState;
import ru.sbt.mipt.oop.domain.state.DeactivatedSignalingState;
import ru.sbt.mipt.oop.events.SensorEvent;
import ru.sbt.mipt.oop.events.SensorEventType;
import ru.sbt.mipt.oop.handlers.DoorEventHandler;
import ru.sbt.mipt.oop.handlers.SensorEventHandler;
import ru.sbt.mipt.oop.handlers.decorator.Decorator;

import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.*;

class AlarmDecoratorHandlerTest {
	private static SensorEventHandler handler = new DoorEventHandler();
	private SmartHome smartHome;
	private final Door door = new Door(false, "1");

	@BeforeAll
	static void beforeAll() {
		Decorator decorator = new AlarmDecorator();
		handler = decorator.decorate(handler);
	}

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
			signaling.getState().activate("0");
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
					assertEquals(AlarmSignalingState.class, signaling.getState().getClass());
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
			signaling.getState().activate("0");
			signaling.getState().deactivate("0");
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
					assertEquals(DeactivatedSignalingState.class, signaling.getState().getClass());
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
			signaling.getState().activate("0");
			signaling.getState().deactivate("1");
		});
		SensorEvent event = new SensorEvent(SensorEventType.DOOR_OPEN, "1");
		//when
		handler.handle(smartHome, event);
		//then
		assertFalse(door.isOpen());
	}
}