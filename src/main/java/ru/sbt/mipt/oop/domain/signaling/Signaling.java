package ru.sbt.mipt.oop.domain.signaling;

import ru.sbt.mipt.oop.domain.Action;
import ru.sbt.mipt.oop.domain.Actionable;
import ru.sbt.mipt.oop.domain.signaling.ActivatedSignalingState;
import ru.sbt.mipt.oop.domain.signaling.AlarmSignalingState;
import ru.sbt.mipt.oop.domain.signaling.DeactivatedSignalingState;
import ru.sbt.mipt.oop.domain.signaling.SignalingState;

public class Signaling implements Actionable {
	private SignalingState signalingState;
	private String activationCode;

	boolean checkActivationCode(String activationCode) {
		return activationCode != null && activationCode.equals(this.activationCode);
	}

	void setActivationCode(String activationCode) {
		this.activationCode = activationCode;
	}

	public Signaling() {
		this.signalingState = new DeactivatedSignalingState(this);
	}

	public boolean isActivated() {
		return signalingState instanceof ActivatedSignalingState;
	}

	public boolean isDeactivated() {
		return signalingState instanceof DeactivatedSignalingState;
	}

	public boolean isAlarmed() {
		return signalingState instanceof AlarmSignalingState;
	}

	public void changeState(SignalingState signalingState) {
		this.signalingState = signalingState;
	}

	public void activateSignaling(String activationCode) {
		signalingState.activate(activationCode);
	}

	public void deactivateSignaling(String activationCode) {
		signalingState.deactivate(activationCode);
	}

	public void alarmSignaling() {
		signalingState.alarm();
	}

	@Override
	public void execute(Action action) {
		action.act(this);
	}
}
