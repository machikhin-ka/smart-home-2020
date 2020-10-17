package ru.sbt.mipt.oop;

import org.junit.jupiter.api.*;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.*;

class LightEventHandlerTest {
	private static SmartHome smartHome;
	private static SensorEventHandler lightEventHandler;
	private static final ByteArrayOutputStream outContent = new ByteArrayOutputStream();
	private static final PrintStream originalOut = System.out;

	@BeforeAll
	static void beforeAll() {
		System.setOut(new PrintStream(outContent));
		lightEventHandler = new LightEventHandler();
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
	void handle_turnOnLight_whenEventTypeIsLightOn() {
		//given
		Light light = new Light("1", false);
		Room kitchen = new Room(Arrays.asList(light),
				null,
				"kitchen");
		SensorEvent event = new SensorEvent(SensorEventType.LIGHT_ON, "1");
		smartHome.addRoom(kitchen);
		//when
		lightEventHandler.handle(smartHome, event);
		//then
		assertTrue(light.isOn());
	}

	@Test
	void handle_printCorrectMessage_whenEventTypeIsLightOn() {
		//given
		Light light = new Light("1", false);
		Room kitchen = new Room(Arrays.asList(light),
				null,
				"kitchen");
		SensorEvent event = new SensorEvent(SensorEventType.LIGHT_ON, "1");
		smartHome.addRoom(kitchen);
		//when
		lightEventHandler.handle(smartHome, event);
		//then
		assertEquals("Light " + light.getId() + " was turned on.\n", outContent.toString());
	}

	@Test
	void handle_turnOffLight_whenEventTypeIsLightOff() {
		//given
		Light light = new Light("1", true);
		Room kitchen = new Room(Arrays.asList(light),
				null,
				"kitchen");
		SensorEvent event = new SensorEvent(SensorEventType.LIGHT_OFF, "1");
		smartHome.addRoom(kitchen);
		//when
		lightEventHandler.handle(smartHome, event);
		//then
		assertFalse(light.isOn());
	}

	@Test
	void handle_printCorrectMessage_whenEventTypeIsLightOff() {
		//given
		Light light = new Light("1", true);
		Room kitchen = new Room(Arrays.asList(light),
				null,
				"kitchen");
		SensorEvent event = new SensorEvent(SensorEventType.LIGHT_OFF, "1");
		smartHome.addRoom(kitchen);
		//when
		lightEventHandler.handle(smartHome, event);
		//then
		assertEquals("Light " + light.getId() + " was turned off.\n", outContent.toString());
	}
}