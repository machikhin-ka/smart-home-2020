package ru.sbt.mipt.oop.domain.state;

public interface State {
	void activate(String activationCode);
	void deactivate(String activationCode);
	void alarm();
}
