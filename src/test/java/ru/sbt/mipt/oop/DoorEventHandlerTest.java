package ru.sbt.mipt.oop;

import org.junit.jupiter.api.*;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.*;

class DoorEventHandlerTest {
	private static SmartHome smartHome;
	private static SensorEventHandler doorEventHandler;
	private static final ByteArrayOutputStream outContent = new ByteArrayOutputStream();
	private static final PrintStream originalOut = System.out;

	@BeforeAll
	static void beforeAll() {
		System.setOut(new PrintStream(outContent));
		doorEventHandler = new DoorEventHandler();
	}

	@AfterAll
	static void afterAll() throws IOException {
		System.setOut(originalOut);
		outContent.close();
	}

	@BeforeEach
	void beforeEach() {
		smartHome = new SmartHome();
	}

	@AfterEach
	void afterEach() {
		outContent.reset();
	}

	@Test
	void handle_openDoor_whenEventTypeIsDoorOpen() {
		//given
		Door door = new Door(false, "1");
		Room kitchen = new Room(null,
				Arrays.asList(door),
				"kitchen");
		SensorEvent event = new SensorEvent(SensorEventType.DOOR_OPEN, "1");
		smartHome.addRoom(kitchen);
		//when
		doorEventHandler.handle(smartHome, event);
		//then
		assertTrue(door.isOpen());
		assertEquals("Door " + door.getId() + " was opened.\n", outContent.toString());
	}

	@Test
	void handle_closeDoor_whenEventTypeIsDoorClosed() {
		//given
		Door door = new Door(true, "1");
		Room kitchen = new Room(null,
				Arrays.asList(door),
				"kitchen");
		SensorEvent event = new SensorEvent(SensorEventType.DOOR_CLOSED, "1");
		smartHome.addRoom(kitchen);
		//when
		doorEventHandler.handle(smartHome, event);
		boolean isTrue = !door.isOpen();
		//then
		assertTrue(isTrue);
		assertEquals("Door " + door.getId() + " was closed.\n", outContent.toString());
	}
}