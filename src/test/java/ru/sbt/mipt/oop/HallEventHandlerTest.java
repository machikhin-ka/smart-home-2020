package ru.sbt.mipt.oop;

import org.junit.jupiter.api.*;

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
	void handle_returnTrue_whenAllLightsAreTurnedOffCorrectly() {
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
	void handle_returnFalse_whenDoorIsClosed() {
		//given
		Door door = new Door(true, "1");
		Room kitchen = new Room(null,
				Arrays.asList(door),
				"hall");
		SensorEvent event = new SensorEvent(SensorEventType.DOOR_CLOSED, "1");
		smartHome.addRoom(kitchen);
		//when
		hallEventHandler.handle(smartHome, event);
		//then
		assertFalse(door.isOpen());
	}
}