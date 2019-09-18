package org.fse.workoutmanager.workout.controller;

import java.net.URI;
import java.text.MessageFormat;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.fse.workoutmanager.workout.bean.Workout;
import org.fse.workoutmanager.workout.constant.Constants;
import org.fse.workoutmanager.workout.exception.WorkoutManagerException;
import org.fse.workoutmanager.workout.service.CategoryService;
import org.fse.workoutmanager.workout.service.WorkoutService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

@RestController
@CrossOrigin(origins = "http://localhost:4200")
public class WorkoutController {

	private static final Logger LOGGER = LoggerFactory.getLogger(WorkoutController.class);

	private @Resource CategoryService categoryService;
	private @Resource WorkoutService workoutService;

	@GetMapping(path = "/workout/{id}", consumes = MediaType.APPLICATION_JSON_UTF8_VALUE, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
	public ResponseEntity<Workout> retrieveWorkout(@PathVariable("id") final Integer id) {

		LOGGER.info("Retrieving workout by id : {}", id);
		Workout output = workoutService.get(id);
		LOGGER.debug("Output : {}", String.valueOf(output));

		return ResponseEntity.ok(output);
	}

	@GetMapping(path = "/workout", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
	public ResponseEntity<List<Workout>> retrieveWorkouts() {

		LOGGER.debug("Retrieving all workouts...");
		List<Workout> output = workoutService.getAllActiveWorkouts();

		LOGGER.info("Current workouts : {}", output);

		return CollectionUtils.isEmpty(output) ? ResponseEntity.noContent().build() : ResponseEntity.ok(output);
	}
	
	@GetMapping(path = "/workout/start/{start}/end/{end}", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
	public ResponseEntity<List<Workout>> retrieveWorkoutActivitiesForDateRange(
			final @PathVariable @DateTimeFormat(pattern = Constants.DT_FMT_ISO_LOCAL_DT) Date start,
			final @PathVariable @DateTimeFormat(pattern = Constants.DT_FMT_ISO_LOCAL_DT) Date end) {

		if ((start == null || start.toString().equals("")) && (end == null || end.toString().equals(""))) {
			throw new IllegalArgumentException("start and end cannot be null or empty");
		}

		LOGGER.info("Retrieving workout activities created for range {} - {}", start, end);
		List<Workout> output = workoutService.getActivitiesCreatedBetween(start, end);

		LOGGER.info("Workout activities created for range : {}", output);

		return output.isEmpty() ? ResponseEntity.noContent().build() : ResponseEntity.ok(output);
	}
	
	@PostMapping(path = "/workout", consumes = MediaType.APPLICATION_JSON_UTF8_VALUE, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
	public ResponseEntity<?> createWorkout(@RequestBody(required = true) Workout workout) {

		LOGGER.info("Creating a new workout: {}", workout);
		workout.putCategoryid(workout.getCategory() == null ? null : workout.getCategory().getId());

		Workout output = workoutService.create(workout);
		if (output == null || output.getId() == null || output.getId() <= 0) {
			throw new WorkoutManagerException("Failed to create Workout");
		}

		LOGGER.info("Workout [{}] created successfully", output.getId());
		LOGGER.debug(output.toString());

		String location = MessageFormat.format("/workout/{0}", output.getId());
		location = ServletUriComponentsBuilder.fromCurrentContextPath().path(location).toUriString();
		LOGGER.info("Created Workout Location : {}", location);

		return ResponseEntity.created(URI.create(location))
				.header(HttpHeaders.ACCESS_CONTROL_ALLOW_METHODS, HttpMethod.GET.name())
				.header(HttpHeaders.ACCESS_CONTROL_REQUEST_METHOD, HttpMethod.GET.name()).build();
	}

	@PutMapping(path = "/workout/{id}", consumes = MediaType.APPLICATION_JSON_UTF8_VALUE, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
	public ResponseEntity<?> updateWorkout(@PathVariable("id") final Integer id,
			@RequestBody(required = true) Workout workout) {

		LOGGER.info("Updating workout : {}", workout);

		Workout output = workoutService.get(id).putTitle(workout.getTitle()).putCategoryid(workout.getCategoryid());
		output.putNotes(workout.getNotes()).putBurnrate(workout.getBurnrate()).putComment(workout.getComment());
		output.putStartdate(workout.getStartdate()).putStarttime(workout.getStarttime()).putEnddate(workout.getEnddate());
		output.putEndtime(workout.getEndtime()).putStatus(workout.getStatus());
		
		workout.putCategoryid(workout.getCategory() == null ? null : workout.getCategory().getId());
		output.putCategoryid(output.getCategory() == null ? null : output.getCategory().getId());

		if (workout.getCategory() != null && output.getCategory().getId() != workout.getCategory().getId()) {
			output.setCategory(categoryService.get(workout.getCategory().getId()));
			output.setCategoryid(output.getCategory().getId());
		}

		output = workoutService.update(output);
		if (output == null || output.getId() == null || output.getId() <= 0) {
			throw new WorkoutManagerException("Failed to Update Workout");
		}

		LOGGER.debug("Updated workout : {}", output.toString());
		return ResponseEntity.ok(output);
	}
	
	@DeleteMapping(path = "/workout/{id}", consumes = MediaType.APPLICATION_JSON_UTF8_VALUE, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
	public ResponseEntity<?> deleteWorkout(@PathVariable("id") final Integer id) {

		LOGGER.info("Deleting workout id: {}", id);
		workoutService.delete(id);

		return ResponseEntity.ok().build();
	}

}
