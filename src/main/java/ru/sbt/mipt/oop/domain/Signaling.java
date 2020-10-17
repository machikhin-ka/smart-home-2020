package ru.sbt.mipt.oop.domain;

import ru.sbt.mipt.oop.domain.state.DeactivatedSignalingState;
import ru.sbt.mipt.oop.domain.state.SignalingState;

public class Signaling implements Actionable {
	private SignalingState signalingState;
	private String activationCode;

	public Signaling() {
		this.signalingState = new DeactivatedSignalingState(this);
	}

	public void changeState(SignalingState signalingState) {
		this.signalingState = signalingState;
	}

	public SignalingState getState() {
		return signalingState;
	}

	public void activateSignaling(String activationCode) {
		this.activationCode = activationCode;
		System.out.println("Signaling was activated");
	}

	public void deactivateSignaling(String activationCode) {
		if (activationCode != null && activationCode.equals(this.activationCode)) {
			this.activationCode = null;
			System.out.println("Signaling was deactivated");
		} else {
			signalingState.alarm();
		}
	}

	public void alarmSignaling() {
		System.out.println("Signaling was alarmed");
	}

	@Override
	public void execute(Action action) {
		action.act(this);
	}
}
