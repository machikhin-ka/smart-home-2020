package ru.sbt.mipt.oop.domain.signaling;

public interface SignalingState {
	void activate(String activationCode);
	void deactivate(String activationCode);
	void alarm();
}
