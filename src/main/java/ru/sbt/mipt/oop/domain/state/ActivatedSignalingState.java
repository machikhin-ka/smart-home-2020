package ru.sbt.mipt.oop.domain.state;

import ru.sbt.mipt.oop.domain.Signaling;

public class ActivatedSignalingState implements SignalingState {
	private transient final Signaling signaling;

	public ActivatedSignalingState(Signaling signaling) {
		this.signaling = signaling;
	}

	@Override
	public void activate(String activationCode) {
	}

	@Override
	public void deactivate(String activationCode) {
		signaling.changeState(new DeactivatedSignalingState(signaling));
		signaling.deactivateSignaling(activationCode);
	}

	@Override
	public void alarm() {
		signaling.changeState(new AlarmSignalingState());
		signaling.alarmSignaling();
	}
}
