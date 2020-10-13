package ru.sbt.mipt.oop;

import org.junit.jupiter.api.*;
import ru.sbt.mipt.oop.command.CommandSender;
import ru.sbt.mipt.oop.domain.Door;
import ru.sbt.mipt.oop.domain.Light;
import ru.sbt.mipt.oop.domain.Room;
import ru.sbt.mipt.oop.domain.SmartHome;
import ru.sbt.mipt.oop.events.SensorEvent;
import ru.sbt.mipt.oop.events.SensorEventType;
import ru.sbt.mipt.oop.handlers.HallEventHandler;
import ru.sbt.mipt.oop.handlers.SensorEventHandler;

import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.*;

class HallEventHandlerTest {
	private static SmartHome smartHome;
	private static SensorEventHandler hallEventHandler;

	@BeforeAll
	static void beforeAll() {
		hallEventHandler = new HallEventHandler(new CommandSender());
	}

	@BeforeEach
	void beforeEach() {
		smartHome = new SmartHome();
	}

	@Test
	void handle_turnedOffAllLights_whenEventTypeIsDoorClosedAndRoomIsHall() {
		//given
		Door door = new Door(true, "1");
		Light lightOne = new Light("1", true);
		Light lightTwo = new Light("2", true);
		Room kitchen = new Room(Arrays.asList(lightOne, lightTwo),
				Arrays.asList(door),
				"hall");
		SensorEvent event = new SensorEvent(SensorEventType.DOOR_CLOSED, "1");
		smartHome.addRoom(kitchen);
		//when
		hallEventHandler.handle(smartHome, event);
		//then
		assertAll(
				() -> assertFalse(lightOne.isOn()),
				() -> assertFalse(lightTwo.isOn())
		);
	}

	@Test
	void handle_nothing_whenEventTypeIsDoorOpen() {
		//given
		Door door = new Door(true, "1");
		Room kitchen = new Room(null,
				Arrays.asList(door),
				"hall");
		SensorEvent event = new SensorEvent(SensorEventType.DOOR_OPEN, "1");
		smartHome.addRoom(kitchen);
		//when
		hallEventHandler.handle(smartHome, event);
		//then
		assertTrue(door.isOpen());
	}
}