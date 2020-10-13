package ru.sbt.mipt.oop.domain;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.sbt.mipt.oop.domain.state.ActivatedState;
import ru.sbt.mipt.oop.domain.state.AlarmState;
import ru.sbt.mipt.oop.domain.state.DeactivatedState;
import ru.sbt.mipt.oop.domain.state.State;

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
		ActivatedState activatedState = new ActivatedState(signaling);
		//when
		signaling.changeState(activatedState);
		//then
		assertEquals(activatedState, signaling.getState());
	}

	@Test
	void activateSignaling_whenStateIsDeactivatedBefore() {
		//given
		State state = signaling.getState();
		//when
		state.activate("0");
		//then
		assertEquals(ActivatedState.class, signaling.getState().getClass());
	}

	@Test
	void activateSignaling_dontActivateSignaling_whenSignalingIsAlarmedBefore() {
		//given
		State state = signaling.getState();
		state.alarm();
		//when
		state = signaling.getState();
		state.activate("0");
		//then
		assertNotEquals(ActivatedState.class, signaling.getState().getClass());
	}

	@Test
	void deactivateSignaling_whenStateIsActivatedBefore() {
		//given
		State state = signaling.getState();
		state.activate("0");
		//when
		state = signaling.getState();
		state.deactivate("0");
		//then
		assertEquals(DeactivatedState.class, signaling.getState().getClass());
	}

	@Test
	void deactivateSignaling_alarmSignaling_whenActivationCodeNotEqualsDeactivationCode() {
		//given
		State state = signaling.getState();
		state.activate("0");
		//when
		state = signaling.getState();
		state.deactivate("1");
		//then
		assertEquals(AlarmState.class, signaling.getState().getClass());
	}

	@Test
	void deactivateSignaling_dontDeactivateSignaling_whenSignalingIsAlarmedBefore() {
		//given
		State state = signaling.getState();
		state.alarm();
		//when
		state = signaling.getState();
		state.deactivate("0");
		//then
		assertNotEquals(DeactivatedState.class, signaling.getState().getClass());
	}

	@Test
	void execute_changeState_WhenActionChangesStateOfSignaling() {
		//when
		signaling.execute(object -> {
			State state = ((Signaling) object).getState();
			state.activate("0");
		});
		//then
		assertEquals(ActivatedState.class, signaling.getState().getClass());
	}
}