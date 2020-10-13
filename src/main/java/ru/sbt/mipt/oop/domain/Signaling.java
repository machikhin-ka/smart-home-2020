package ru.sbt.mipt.oop.domain;

import ru.sbt.mipt.oop.domain.state.DeactivatedState;
import ru.sbt.mipt.oop.domain.state.State;

public class Signaling implements Actionable {
	private State state;
	private String activationCode;

	public Signaling() {
		this.state = new DeactivatedState(this);
	}

	public void changeState(State state) {
		this.state = state;
	}

	public State getState() {
		return state;
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
			state.alarm();
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
