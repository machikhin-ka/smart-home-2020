package ru.sbt.mipt.oop.domain;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.sbt.mipt.oop.domain.signaling.ActivatedSignalingState;
import ru.sbt.mipt.oop.domain.signaling.Signaling;

import static org.junit.jupiter.api.Assertions.*;

class SignalingTest {
	private Signaling signaling;

	@BeforeEach
	void setUp() {
		signaling = new Signaling();
	}

	@Test
	void changeState_changeStateCorrectly_whenChangeStateToActivatedState() {
		//given
		ActivatedSignalingState activatedState = new ActivatedSignalingState(signaling);
		//when
		signaling.changeState(activatedState);
		//then
		assertTrue(signaling.isActivated());
	}

	@Test
	void activateSignaling_whenStateIsDeactivatedBefore() {
		//when
		signaling.activateSignaling("0");
		//then
		assertTrue(signaling.isActivated());
	}

	@Test
	void activateSignaling_dontActivateSignaling_whenSignalingIsAlarmedBefore() {
		//given
		signaling.alarmSignaling();
		//when
		signaling.activateSignaling("0");
		//then
		assertFalse(signaling.isActivated());
	}

	@Test
	void deactivateSignaling_whenStateIsActivatedBefore() {
		//given
		signaling.activateSignaling("0");
		//when
		signaling.deactivateSignaling("0");
		//then
		assertTrue(signaling.isDeactivated());
	}

	@Test
	void deactivateSignaling_alarmSignaling_whenActivationCodeNotEqualsDeactivationCode() {
		//given
		signaling.activateSignaling("0");
		//when
		signaling.deactivateSignaling("1");
		//then
		assertTrue(signaling.isAlarmed());
	}

	@Test
	void deactivateSignaling_dontDeactivateSignaling_whenSignalingIsAlarmedBefore() {
		//given
		signaling.alarmSignaling();
		//when
		signaling.deactivateSignaling("0");
		//then
		assertFalse(signaling.isDeactivated());
	}

	@Test
	void execute_changeState_WhenActionChangesStateOfSignaling() {
		//when
		signaling.execute(object -> {
			signaling.activateSignaling("0");
		});
		//then
		assertTrue(signaling.isActivated());
	}
}