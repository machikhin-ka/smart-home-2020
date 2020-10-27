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
import ru.sbt.mipt.oop.factories.SensorEventFactory;
import ru.sbt.mipt.oop.handlers.*;
import ru.sbt.mipt.oop.handlers.decorator.AlarmDecoratorHandler;
import ru.sbt.mipt.oop.handlers.decorator.AlarmSendMessageDecoratorHandler;

import java.util.Collection;

@Configuration
@ComponentScan
public class AppConfiguration {
	@Bean
	public SmartHomeDeserialization getSmartHomeDeserialization() {
		return new JsonSmartHomeDeserialization("smart-home-1.js");
	}

	@Bean
	public SmartHome getSmartHome(SmartHomeDeserialization deserialization) {
		return deserialization.deserialize();
	}

	@Bean
	public SensorEventsManager getSensorEventsManager(Collection<SensorEventHandler> handlers, SmartHome smartHome,
													  SensorEventFactory factory) {
		SensorEventsManager sensorEventsManager = new SensorEventsManager();
		handlers.stream().
				map(h -> new EventHandlerAdapter(h, smartHome, factory))
				.forEach(sensorEventsManager::registerEventHandler);
		return sensorEventsManager;
	}

	@Bean
	public SensorEventHandler getDoorDecoratorHandler() {
		return new AlarmSendMessageDecoratorHandler(new AlarmDecoratorHandler(new DoorEventHandler()));
	}

	@Bean
	public SensorEventHandler getLightDecoratorHandler() {
		return new AlarmDecoratorHandler(new LightEventHandler());
	}

	@Bean
	public SensorEventHandler getHallDecoratorHandler(CommandSender sender) {
		return new AlarmDecoratorHandler(new HallEventHandler(sender));
	}
}
