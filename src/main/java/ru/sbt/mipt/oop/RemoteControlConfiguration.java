package ru.sbt.mipt.oop;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import rc.RemoteControl;
import rc.RemoteControlRegistry;
import ru.sbt.mipt.oop.domain.SmartHome;
import ru.sbt.mipt.oop.remote.control.SmartHomeRemoteControl;
import ru.sbt.mipt.oop.remote.control.command.LightCommand;
import ru.sbt.mipt.oop.remote.control.command.RemoteControlCommand;

import java.util.Collection;
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
	public SmartHomeRemoteControl smartHomeRemoteControl(Map<String, RemoteControlCommand> commands) {
		Map<String, String> nameToCode = Map.of(
				"turnOnLightCommand", "A",
				"closeHallDoorCommand", "B",
				"turnOnHallLightCommand", "C",
				"activateSignalingCommand", "D",
				"alarmSignalingCommand", "1",
				"turnOffLightCommand", "2"
		);
		SmartHomeRemoteControl remoteControl = new SmartHomeRemoteControl("1");
		commands.forEach((k, v) -> {
			remoteControl.setButton(nameToCode.get(k), v);
		});
		return remoteControl;
	}

	@Bean
	public LightCommand turnOnLightCommand(SmartHome smartHome) {
		return new LightCommand(smartHome, true);
	}

	@Bean
	public LightCommand turnOffLightCommand(SmartHome smartHome) {
		return new LightCommand(smartHome, false);
	}
}
