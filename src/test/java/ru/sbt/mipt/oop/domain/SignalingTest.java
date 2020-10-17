package ru.sbt.mipt.oop.domain;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.sbt.mipt.oop.domain.state.ActivatedSignalingState;
import ru.sbt.mipt.oop.domain.state.AlarmSignalingState;
import ru.sbt.mipt.oop.domain.state.DeactivatedSignalingState;
import ru.sbt.mipt.oop.domain.state.SignalingState;

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
		assertEquals(activatedState, signaling.getState());
	}

	@Test
	void activateSignaling_whenStateIsDeactivatedBefore() {
		//given
		SignalingState signalingState = signaling.getState();
		//when
		signalingState.activate("0");
		//then
		assertEquals(ActivatedSignalingState.class, signaling.getState().getClass());
	}

	@Test
	void activateSignaling_dontActivateSignaling_whenSignalingIsAlarmedBefore() {
		//given
		SignalingState signalingState = signaling.getState();
		signalingState.alarm();
		//when
		signalingState = signaling.getState();
		signalingState.activate("0");
		//then
		assertNotEquals(ActivatedSignalingState.class, signaling.getState().getClass());
	}

	@Test
	void deactivateSignaling_whenStateIsActivatedBefore() {
		//given
		SignalingState signalingState = signaling.getState();
		signalingState.activate("0");
		//when
		signalingState = signaling.getState();
		signalingState.deactivate("0");
		//then
		assertEquals(DeactivatedSignalingState.class, signaling.getState().getClass());
	}

	@Test
	void deactivateSignaling_alarmSignaling_whenActivationCodeNotEqualsDeactivationCode() {
		//given
		SignalingState signalingState = signaling.getState();
		signalingState.activate("0");
		//when
		signalingState = signaling.getState();
		signalingState.deactivate("1");
		//then
		assertEquals(AlarmSignalingState.class, signaling.getState().getClass());
	}

	@Test
	void deactivateSignaling_dontDeactivateSignaling_whenSignalingIsAlarmedBefore() {
		//given
		SignalingState signalingState = signaling.getState();
		signalingState.alarm();
		//when
		signalingState = signaling.getState();
		signalingState.deactivate("0");
		//then
		assertNotEquals(DeactivatedSignalingState.class, signaling.getState().getClass());
	}

	@Test
	void execute_changeState_WhenActionChangesStateOfSignaling() {
		//when
		signaling.execute(object -> {
			SignalingState signalingState = ((Signaling) object).getState();
			signalingState.activate("0");
		});
		//then
		assertEquals(ActivatedSignalingState.class, signaling.getState().getClass());
	}
}