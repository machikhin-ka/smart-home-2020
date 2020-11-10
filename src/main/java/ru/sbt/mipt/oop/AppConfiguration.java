package ru.sbt.mipt.oop;

import com.coolcompany.smarthome.events.SensorEventsManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import ru.sbt.mipt.oop.adapters.EventHandlerAdapter;
import ru.sbt.mipt.oop.command.CommandSender;
import ru.sbt.mipt.oop.deserialization.JsonSmartHomeDeserialization;
import ru.sbt.mipt.oop.deserialization.SmartHomeDeserialization;
import ru.sbt.mipt.oop.domain.SmartHome;
import ru.sbt.mipt.oop.events.SensorEventType;
import ru.sbt.mipt.oop.factories.SensorEventFactory;
import ru.sbt.mipt.oop.handlers.*;
import ru.sbt.mipt.oop.handlers.decorator.AlarmDecoratorHandler;
import ru.sbt.mipt.oop.handlers.decorator.AlarmSendMessageDecoratorHandler;

import java.util.Collection;
import java.util.Map;

@Configuration
@ComponentScan
public class AppConfiguration {
	@Bean
	public SmartHomeDeserialization smartHomeDeserialization() {
		return new JsonSmartHomeDeserialization("smart-home-1.js");
	}

	@Bean
	public SmartHome smartHome(SmartHomeDeserialization deserialization) {
		return deserialization.deserialize();
	}

	@Bean
	public SensorEventsManager sensorEventsManager(Collection<EventHandlerAdapter> adapters) {
		SensorEventsManager sensorEventsManager = new SensorEventsManager();
		adapters.forEach(sensorEventsManager::registerEventHandler);
		return sensorEventsManager;
	}

	@Bean
	public EventHandlerAdapter doorHandlerAdapter(SmartHome smartHome, SensorEventFactory factory) {
		return new EventHandlerAdapter(
				new AlarmSendMessageDecoratorHandler(new AlarmDecoratorHandler(new DoorEventHandler())),
				smartHome, factory);
	}

	@Bean
	public EventHandlerAdapter lightHandlerAdapter(SmartHome smartHome, SensorEventFactory factory) {
		return new EventHandlerAdapter(new AlarmDecoratorHandler(new LightEventHandler()), smartHome, factory);
	}

	@Bean
	public EventHandlerAdapter hallHandlerAdapter(CommandSender sender, SmartHome smartHome,
												  SensorEventFactory factory) {
		return new EventHandlerAdapter(new AlarmDecoratorHandler(new HallEventHandler(sender)), smartHome, factory);
	}

	@Bean
	public EventHandlerAdapter signalingHandlerAdapter(SignalingEventHandler handler, SmartHome smartHome,
													   SensorEventFactory factory) {
		return new EventHandlerAdapter(handler, smartHome, factory);
	}

	@Bean
	public Map<String, SensorEventType> eventTypes() {
		return Map.of("DoorIsOpen", SensorEventType.DOOR_OPEN,
				"DoorIsClosed", SensorEventType.DOOR_CLOSED,
				"LightIsOn", SensorEventType.LIGHT_ON,
				"LightIsOff", SensorEventType.LIGHT_OFF,
				"DoorIsLocked", SensorEventType.ALARM_ACTIVATE,
				"DoorIsUnlocked", SensorEventType.ALARM_DEACTIVATE);
	}
}
