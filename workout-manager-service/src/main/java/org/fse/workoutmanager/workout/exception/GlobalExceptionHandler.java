package org.fse.workoutmanager.workout.exception;

import java.util.Iterator;
import java.util.NoSuchElementException;

import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import javax.validation.Path.Node;

import org.fse.workoutmanager.workout.bean.ApiResponse;
import org.fse.workoutmanager.workout.constant.Constants;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Lookup;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@Component
@ControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {

	private static final Logger LOGGER = LoggerFactory.getLogger(GlobalExceptionHandler.class);

	@Lookup
	public ApiResponse getApiResponse() {
		return null; // Dynamic prototype bean
	}

	@ExceptionHandler({ WorkoutManagerException.class, Exception.class })
	public final ResponseEntity<ApiResponse> handleApplicationException(final Exception ex) {
		LOGGER.warn(ex.getMessage(), ex.getCause());
		ApiResponse output = getApiResponse().addMessage(ex.getMessage());
		return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(output);
	}

	@ExceptionHandler({ IllegalArgumentException.class })
	public final ResponseEntity<ApiResponse> handleBadRequestException(final RuntimeException ex) {
		LOGGER.warn(ex.getMessage(), ex.getCause());
		ApiResponse output = getApiResponse().addMessage(ex.getMessage());
		return ResponseEntity.badRequest().body(output);
	}

	@ExceptionHandler({ EmptyResultDataAccessException.class, NoSuchElementException.class })
	public final ResponseEntity<ApiResponse> handleNoContentException(final RuntimeException ex) {
		LOGGER.warn(ex.getMessage(), ex.getCause());
		return ResponseEntity.noContent().build();
	}

	@ExceptionHandler(ConstraintViolationException.class)
	public final ResponseEntity<ApiResponse> handleValidationException(final ConstraintViolationException ex) {

		ApiResponse output = getApiResponse();
		LOGGER.error("Data validation Failure :: {}", ex.getMessage());

		for (ConstraintViolation<?> violation : ex.getConstraintViolations()) {

			LOGGER.error(violation.toString());
			Iterator<Node> iterator = violation.getPropertyPath().iterator();
			while (iterator.hasNext()) {

				String fieldname = iterator.next().getName();

				output.addMessage(fieldname + Constants.SPACE + violation.getMessage());
				
			}
		}
		return ResponseEntity.badRequest().body(output);
	}

}
