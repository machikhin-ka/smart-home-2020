package ru.sbt.mipt.oop.domain.signaling;

public class AlarmSignalingState implements SignalingState {
	@Override
	public void activate(String activationCode) {
		System.out.println("The signaling is alarmed");
	}

	@Override
	public void deactivate(String activationCode) {
		System.out.println("The signaling is alarmed");
	}

	@Override
	public void alarm() {
		System.out.println("The signaling is alarmed");
	}
}
