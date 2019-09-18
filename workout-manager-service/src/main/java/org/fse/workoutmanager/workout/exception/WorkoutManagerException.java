package org.fse.workoutmanager.workout.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
public class WorkoutManagerException extends RuntimeException {

	private static final long serialVersionUID = 3050330395983498145L;

	public WorkoutManagerException(String message) {
		super(message);
	}

	public WorkoutManagerException(String message, Throwable cause) {
		super(message, cause);
	}
}
