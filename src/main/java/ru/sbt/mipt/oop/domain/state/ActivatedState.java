package ru.sbt.mipt.oop.domain.state;

import ru.sbt.mipt.oop.domain.Signaling;

public class ActivatedState implements State {
	private transient final Signaling signaling;

	public ActivatedState(Signaling signaling) {
		this.signaling = signaling;
	}

	@Override
	public void activate(String activationCode) {
	}

	@Override
	public void deactivate(String activationCode) {
		signaling.changeState(new DeactivatedState(signaling));
		signaling.deactivateSignaling(activationCode);
	}

	@Override
	public void alarm() {
		signaling.changeState(new AlarmState());
		signaling.alarmSignaling();
	}
}
