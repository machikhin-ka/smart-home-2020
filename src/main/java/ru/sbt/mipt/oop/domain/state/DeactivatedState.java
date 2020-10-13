package ru.sbt.mipt.oop.domain.state;

import ru.sbt.mipt.oop.domain.Signaling;

public class DeactivatedState implements State {
	private transient final Signaling signaling;

	public DeactivatedState(Signaling signaling) {
		this.signaling = signaling;
	}

	@Override
	public void activate(String activationCode) {
		signaling.changeState(new ActivatedState(signaling));
		signaling.activateSignaling(activationCode);
	}

	@Override
	public void deactivate(String activationCode) {
	}

	@Override
	public void alarm() {
		signaling.changeState(new AlarmState());
		signaling.alarmSignaling();
	}
}
