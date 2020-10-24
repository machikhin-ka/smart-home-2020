package ru.sbt.mipt.oop.domain.signaling;

public class DeactivatedSignalingState implements SignalingState {
	private transient final Signaling signaling;

	public DeactivatedSignalingState(Signaling signaling) {
		this.signaling = signaling;
	}

	@Override
	public void activate(String activationCode) {
		signaling.changeState(new ActivatedSignalingState(signaling));
		signaling.setActivationCode(activationCode);
		System.out.println("The signaling was activated");
	}

	@Override
	public void deactivate(String activationCode) {
		System.out.println("The signaling is already deactivated");
	}

	@Override
	public void alarm() {
		signaling.changeState(new AlarmSignalingState());
		System.out.println("Signaling was alarmed");
	}
}
