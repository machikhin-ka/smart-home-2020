package ru.sbt.mipt.oop;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import rc.RemoteControl;
import rc.RemoteControlRegistry;
import ru.sbt.mipt.oop.domain.SmartHome;
import ru.sbt.mipt.oop.remote.control.SmartHomeRemoteControl;
import ru.sbt.mipt.oop.remote.control.command.LightCommand;
import ru.sbt.mipt.oop.remote.control.command.RemoteControlCommand;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

@Configuration
public class RemoteControlConfiguration {
	@Bean
	public RemoteControlRegistry remoteControlRegistry(Collection<RemoteControl> remoteControls) {
		RemoteControlRegistry registry = new RemoteControlRegistry();
		remoteControls.forEach(e -> {
			registry.registerRemoteControl(e, e.getId());
		});
		return registry;
	}

	@Bean
	public SmartHomeRemoteControl smartHomeRemoteControl(
			@Qualifier(value = "buttons") Map<String, RemoteControlCommand> buttons) {
		return new SmartHomeRemoteControl("1", buttons);
	}

	@Bean
	public LightCommand turnOnLightCommand(SmartHome smartHome) {
		return new LightCommand(smartHome, true);
	}

	@Bean
	public LightCommand turnOffLightCommand(SmartHome smartHome) {
		return new LightCommand(smartHome, false);
	}

	@Bean
	public Map<String, RemoteControlCommand> buttons(Map<String, RemoteControlCommand> commands) {
		Map<String, String> nameToCode = Map.of(
				"turnOnLightCommand", "A",
				"closeHallDoorCommand", "B",
				"turnOnHallLightCommand", "C",
				"activateSignalingCommand", "D",
				"alarmSignalingCommand", "1",
				"turnOffLightCommand", "2"
		);
		Map<String, RemoteControlCommand> buttons = new HashMap<>();
		commands.forEach((k, v) -> {
			buttons.put(nameToCode.get(k), v);
		});
		return buttons;
	}
}
