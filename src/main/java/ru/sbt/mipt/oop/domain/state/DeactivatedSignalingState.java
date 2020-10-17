package ru.sbt.mipt.oop.domain.state;

import ru.sbt.mipt.oop.domain.Signaling;

public class DeactivatedSignalingState implements SignalingState {
	private transient final Signaling signaling;

	public DeactivatedSignalingState(Signaling signaling) {
		this.signaling = signaling;
	}

	@Override
	public void activate(String activationCode) {
		signaling.changeState(new ActivatedSignalingState(signaling));
		signaling.activateSignaling(activationCode);
	}

	@Override
	public void deactivate(String activationCode) {
	}

	@Override
	public void alarm() {
		signaling.changeState(new AlarmSignalingState());
		signaling.alarmSignaling();
	}
}
