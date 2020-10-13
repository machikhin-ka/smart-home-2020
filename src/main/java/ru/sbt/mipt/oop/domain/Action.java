package ru.sbt.mipt.oop.domain;

@FunctionalInterface
public interface Action {
	void act(Actionable object);
}