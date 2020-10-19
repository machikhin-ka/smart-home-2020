package ru.sbt.mipt.oop.handlers;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.sbt.mipt.oop.domain.Signaling;
import ru.sbt.mipt.oop.domain.SmartHome;
import ru.sbt.mipt.oop.domain.state.ActivatedSignalingState;
import ru.sbt.mipt.oop.domain.state.DeactivatedSignalingState;
import ru.sbt.mipt.oop.events.SensorEvent;
import ru.sbt.mipt.oop.events.SensorEventType;

import static org.junit.jupiter.api.Assertions.*;

class SignalingEventHandlerTest {
	private final SensorEventHandler handler = new SignalingEventHandler();
	private SmartHome smartHome;

	@BeforeEach
	void setUp() {
		smartHome = new SmartHome();
	}

	@Test
	void handle_activateSignaling_whenEventTypeIsAlarmActivate() {
		//given
		SensorEventType eventType = SensorEventType.ALARM_ACTIVATE;
		SensorEvent event = new SensorEvent(eventType, "0");
		event.setCode("0");
		//when
		handler.handle(smartHome, event);
		//then
		smartHome.execute(object -> {
			if (!(object instanceof Signaling)) {
				return;
			}
			Signaling signaling = ((Signaling) object);
			assertEquals(ActivatedSignalingState.class, signaling.getState().getClass());
		});
	}

	@Test
	void handle_deactivateSignaling_whenEventTypeIsAlarmDeactivate() {
		//given
		SensorEventType eventType = SensorEventType.ALARM_DEACTIVATE;
		SensorEvent sensorEvent = new SensorEvent(eventType, "0");
		sensorEvent.setCode("0");
		handler.handle(smartHome, sensorEvent);
		eventType = SensorEventType.ALARM_DEACTIVATE;
		sensorEvent = new SensorEvent(eventType, "0");
		sensorEvent.setCode("0");
		//when
		handler.handle(smartHome, sensorEvent);
		//then
		smartHome.execute(object -> {
			if (!(object instanceof Signaling)) {
				return;
			}
			Signaling signaling = ((Signaling) object);
			assertEquals(DeactivatedSignalingState.class, signaling.getState().getClass());
		});
	}
}