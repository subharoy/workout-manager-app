package org.fse.workoutmanager.workout.controller;

import java.net.URI;
import java.text.MessageFormat;
import java.util.List;

import javax.annotation.Resource;

import org.fse.workoutmanager.workout.bean.Category;
import org.fse.workoutmanager.workout.exception.WorkoutManagerException;
import org.fse.workoutmanager.workout.service.CategoryService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
public class CategoryController {

	private static final Logger LOGGER = LoggerFactory.getLogger(CategoryController.class);

	private @Resource CategoryService categoryService;

	@PostMapping(path = "/category", consumes = MediaType.APPLICATION_JSON_UTF8_VALUE, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
	public ResponseEntity<?> createCategory(@RequestBody(required = true) Category category) {

		LOGGER.info("Creating a new category: {}", category);

		Category output = categoryService.create(category);
		if (output == null || output.getId() == null || output.getId() <= 0) {
			throw new WorkoutManagerException("Error creating a new category");
		}

		LOGGER.info("Successfully created new category id: {}", output.getId());
		LOGGER.debug(output.toString());

		String location = MessageFormat.format("/category/{0}", output.getId());
		location = ServletUriComponentsBuilder.fromCurrentContextPath().path(location).toUriString();
		LOGGER.info("Created Category Location : {}", location);

		return ResponseEntity.created(URI.create(location))
				.header(HttpHeaders.ACCESS_CONTROL_ALLOW_METHODS, HttpMethod.GET.name())
				.header(HttpHeaders.ACCESS_CONTROL_REQUEST_METHOD, HttpMethod.GET.name()).build();
	}

	@DeleteMapping(path = "/category/{id}", consumes = MediaType.APPLICATION_JSON_UTF8_VALUE, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
	public ResponseEntity<?> deleteCategory(@PathVariable("id") final Integer id) {

		LOGGER.info("Deleting category id: {}", id);
		categoryService.delete(id);

		return ResponseEntity.ok().build();
	}

	@GetMapping(path = "/category", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
	public ResponseEntity<List<Category>> retrieveCategories() {

		LOGGER.debug("Retrieving all categories...");
		List<Category> output = categoryService.getAllCategories();

		LOGGER.info("Current categories : {}", output);

		return CollectionUtils.isEmpty(output) ? ResponseEntity.noContent().build() : ResponseEntity.ok(output);
	}

	@GetMapping(path = "/category/{id}", consumes = MediaType.APPLICATION_JSON_UTF8_VALUE, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
	public ResponseEntity<Category> retrieveCategory(@PathVariable("id") final Integer id) {

		LOGGER.info("Retrieving Category by id :: {}", id);
		Category output = categoryService.get(id);
		LOGGER.debug("Output :: {}", String.valueOf(output));

		return ResponseEntity.ok(output);
	}

	@PutMapping(path = "/category/{id}", consumes = MediaType.APPLICATION_JSON_UTF8_VALUE, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
	public ResponseEntity<?> updateCategory(@PathVariable("id") final Integer id,
			@RequestBody(required = true) Category category) {

		LOGGER.info("Updating category id : {}", id);

		Category output = categoryService.get(id).putCategory(category.getCategory());
		output = categoryService.update(output);
		if (output == null || output.getId() == null || output.getId() <= 0) {
			throw new WorkoutManagerException("Failed to update Category");
		}

		LOGGER.debug("Finished updating category : {}", output.toString());
		return ResponseEntity.ok(output);
	}

}
