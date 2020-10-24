package ru.sbt.mipt.oop.handlers.decorator.alarm;

import org.junit.jupiter.api.*;
import ru.sbt.mipt.oop.domain.Door;
import ru.sbt.mipt.oop.domain.Room;
import ru.sbt.mipt.oop.domain.SmartHome;
import ru.sbt.mipt.oop.domain.signaling.Signaling;
import ru.sbt.mipt.oop.events.SensorEvent;
import ru.sbt.mipt.oop.events.SensorEventType;
import ru.sbt.mipt.oop.handlers.DoorEventHandler;
import ru.sbt.mipt.oop.handlers.SensorEventHandler;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

class AlarmSendMessageDecoratorHandlerTest {
	private static SensorEventHandler handler = new DoorEventHandler();
	private SmartHome smartHome;
	private final Door door = new Door(false, "1");
	private static final ByteArrayOutputStream outContent = new ByteArrayOutputStream();
	private static final PrintStream originalOut = System.out;

	@BeforeAll
	static void beforeAll() {
		System.setOut(new PrintStream(outContent));
		SendMessageDecoratorAction smsAction = new SendMessageDecoratorAction();
		handler = new AlarmSendMessageDecoratorHandler(handler, smsAction);
	}

	@AfterAll
	static void afterAll() throws IOException {
		System.setOut(originalOut);
		outContent.close();
	}

	@BeforeEach
	void beforeEach() {
		smartHome = new SmartHome();
		Room kitchen = new Room(null,
				Arrays.asList(door),
				"kitchen");
		smartHome.addRoom(kitchen);
	}

	@AfterEach
	void afterEach() {
		outContent.reset();
	}

	@Test
	void handle_typeSendingSms_whenEventTypeIsDoorOpenAndSignalingStateIsActivated() {
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
		List<String> lines = Arrays.asList(outContent.toString().split("\n"));
		assertEquals("Sending sms...", lines.get(1));
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
		List<String> lines = Arrays.asList(outContent.toString().split("\n"));
		assertNotEquals("Sending sms...", lines.get(2));
	}

	@Test
	void handle_typeSendingSms_whenSignalingStateIsAlarmed() {
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
		List<String> lines = Arrays.asList(outContent.toString().split("\n"));
		assertEquals("Sending sms...", lines.get(3));
	}
}