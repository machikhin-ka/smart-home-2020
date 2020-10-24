package ru.sbt.mipt.oop.domain.signaling;

public class ActivatedSignalingState implements SignalingState {
	private transient final Signaling signaling;

	public ActivatedSignalingState(Signaling signaling) {
		this.signaling = signaling;
	}

	@Override
	public void activate(String activationCode) {
		System.out.println("The signaling is already activated");
	}

	@Override
	public void deactivate(String activationCode) {
		if (signaling.checkActivationCode(activationCode)) {
			signaling.setActivationCode(null);
			signaling.changeState(new DeactivatedSignalingState(signaling));
			System.out.println("The signaling was deactivated");
		} else {
			System.out.println("The activation code is uncorrected");
			alarm();
		}
	}

	@Override
	public void alarm() {
		signaling.changeState(new AlarmSignalingState());
		System.out.println("Signaling was alarmed");
	}
}
